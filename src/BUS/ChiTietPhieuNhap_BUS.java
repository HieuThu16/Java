package BUS;

import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import DAO.CTGG_DAO;
import DAO.ChiTietPhieuNhap_DAO;
import DTO.ChiTietPhieuNhap_DTO;

public class ChiTietPhieuNhap_BUS {
	 private ChiTietPhieuNhap_DAO chitietphieunhapDAO = new ChiTietPhieuNhap_DAO();
    public boolean addChiTietPhieuNhap(ChiTietPhieuNhap_DTO chiTietPhieuNhap) throws HeadlessException, SQLException {
    	 if (chiTietPhieuNhap.getMaChiTietPhieuNhap().isEmpty() ) {
   		  JOptionPane.showMessageDialog(null, "Vui lòng nhập mã chi tiết phiếu nhập");
	          return false;
    	 }
    	 if (isDuplicateMaCTGG(chiTietPhieuNhap.getMaChiTietPhieuNhap())) {
  		  JOptionPane.showMessageDialog(null, "mã chi tiết phiếu nhập Đã Tồn Tại");
	          return false; 
    	 }
    	 if (chiTietPhieuNhap.getMaPhieuNhap().isEmpty() ) {
  		  JOptionPane.showMessageDialog(null, "Vui lòng chọn mã phiếu nhập");
         return false;
    	 }
    	 if (chiTietPhieuNhap.getDonGia()== 0) {
     		  JOptionPane.showMessageDialog(null, "Vui lòng nhập đơn giá");
            return false;
      	 }
    	 if (chiTietPhieuNhap.getSoLuong()== 0) {
    		  JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng" );
           return false;
     	 }
    	
        // Gọi phương thức từ lớp DAO để thêm chi tiết phiếu nhập
		boolean success = ChiTietPhieuNhap_DAO.addChiTietPhieuNhap(chiTietPhieuNhap);
		
		return success; 
    }
    public boolean isDuplicateMaCTGG(String maCTGG) throws SQLException {
        return chitietphieunhapDAO.countDuplicateMaCTGG(maCTGG) > 0;
    }
    
    public double getTongTienPhieuNhap(String maPhieuNhap) throws SQLException {
        return chitietphieunhapDAO.getTongTienPhieuNhap(maPhieuNhap);
    }
    public boolean updateTongTienPhieuNhap(String maPhieuNhap, double tongTienMoi) {
        boolean updateSuccess = false;
        try {
            updateSuccess = chitietphieunhapDAO.updateTongTienPhieuNhap(maPhieuNhap, tongTienMoi);
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần thiết
        }
        return updateSuccess;
    }
    
}
