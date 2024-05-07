package BUS;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import DAO.CTGG_DAO;
import DTO.CTGG_DTO;
import Database.ConnectionManager;


public class CTGG_BUS {
    private CTGG_DAO ctggDAO;
    private Connection connection = ConnectionManager.openConnection(); 
    public CTGG_BUS() {
        ctggDAO = new CTGG_DAO(connection);
    }

    // Phương thức để lấy tất cả CTGG
    public List<CTGG_DTO> getAllCTGG() throws SQLException {
        return ctggDAO.getAllCTGG();
    }


    public boolean addCTGG(CTGG_DTO ctgg_DTO) throws ParseException, SQLException {
    	 if (ctgg_DTO.getMaCTGG().isEmpty() ) {
    		  JOptionPane.showMessageDialog(null, "Vui lòng nhập mã chương trình giảm giá");
	          return false;
         }
    	 if (isDuplicateMaCTGG(ctgg_DTO.getMaCTGG())) {
   		  JOptionPane.showMessageDialog(null, "Mã Chương Trình Giảm Giá Đã Tồn Tại");
	          return false;
    	 }
    	 if (ctgg_DTO.getTenCTGG().isEmpty() ) {
   		  JOptionPane.showMessageDialog(null, "Vui lòng nhập tên chương trình giảm giá");
          return false;
    	 }
    	 if (ctgg_DTO.getThoiGianBatDau()== null ) {
      		  JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu của  chương trình giảm giá");
             return false;
       	 }
    	 if (ctgg_DTO.getThoiGianKetThuc()== null ) {
      		  JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày kết thúc chương trình giảm giá");
             return false;
       	 }
    	 if (!isValidDateRange(ctgg_DTO.getThoiGianBatDau(), ctgg_DTO.getThoiGianKetThuc())) {
            JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày bắt đầu!");
        } 	
    	return CTGG_DAO.addCTGG(ctgg_DTO);
    }
 
    public boolean isValidDateRange(Date startDate, Date endDate) {
        return endDate.after(startDate);
    }

    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
    
    public boolean isDateEmpty(Date startDate, Date endDate) {
        return startDate == null && endDate == null;
    }
    
    public boolean isDuplicateMaCTGG(String maCTGG) throws SQLException {
        return ctggDAO.countDuplicateMaCTGG(maCTGG) > 0;
    }
}
