package BUS;


import java.sql.SQLException;
import java.text.ParseException;


import javax.swing.JOptionPane;

import DAO.PhieuNhap_DAO;
import DTO.PhieuNhap_DTO;

public class PhieuNhap_BUS {
	  private static PhieuNhap_DAO phieuNhapDAO = new PhieuNhap_DAO();
	    
	    public PhieuNhap_BUS() {
	    	phieuNhapDAO = new PhieuNhap_DAO();
	    }

	    public static boolean addPhieuNhap(PhieuNhap_DTO phieuNhap_DTO) throws ParseException, SQLException {
	    	 if (phieuNhap_DTO.getMaPhieuNhap().isEmpty() ) {
	    		  JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phiếu nhập");
		          return false;
	         }
	    	 if (isDuplicateMaCTGG(phieuNhap_DTO.getMaPhieuNhap())) {
	   		  JOptionPane.showMessageDialog(null, "Mã Phiếu nhập Đã Tồn Tại");
		          return false;
	    	 }
	    	 if (phieuNhap_DTO.getMaNhanVien().isEmpty() ) {
	   		  JOptionPane.showMessageDialog(null, "Vui lòng chọn mã nhân viên");
	          return false;
	    	 }
	    	 if (phieuNhap_DTO.getMaNhaCungCap().isEmpty() ) {
		   		  JOptionPane.showMessageDialog(null, "Vui lòng chọn mã nhà cung cấp");
		          return false;
		    	 }
	    	
	    	 if (phieuNhap_DTO.getNgayNhap()== null ) {
	      		  JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày lập");
	             return false;
	       	 }
	    	 
	    	return phieuNhapDAO.addPhieuNhap(phieuNhap_DTO);
	    }

		private static boolean isDuplicateMaCTGG(String maPhieuNhap) throws SQLException {
			  return phieuNhapDAO.countDuplicateMaCTGG(maPhieuNhap) > 0;
		}
		   public static boolean xoaNhapKho(String maCTGG) throws SQLException {
		        // Kiểm tra chi tiết CTGG
		        boolean hasDetails = PhieuNhap_DAO.hasDetails(maCTGG);
		        if (hasDetails) {
		            return false; // Trả về false nếu có chi tiết CTGG liên quan
		        }

		       
		        boolean deleted = PhieuNhap_DAO.deleteCTGG(maCTGG);
		        
		        return deleted;
		    }
    
	  
}
