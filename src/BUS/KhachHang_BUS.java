package BUS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import DAO.KhachHang_DAO;
import DTO.KhachHang_DTO;
import Database.ConnectionManager;

public class KhachHang_BUS {
	 private Connection connection = ConnectionManager.openConnection(); 
	private  KhachHang_DAO khachHangDAO = new KhachHang_DAO(connection);
	 public boolean addKhachHang(KhachHang_DTO khachHang) {
		 	
	        if (!isMaSachUnique(khachHang.getMaKhachHang())) {
	            JOptionPane.showMessageDialog(null, "Mã khách hàng đã tồn tại");
	            return false;
	        }
	        if (khachHang.getMaKhachHang().isEmpty() 
	        		) {
	            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã khách hàng");
	            return false;
	        }  

	        if (khachHang.getHoKhachHang().isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Vui lòng nhập họ khách hàng");
	            return false;
	        }     
	        if (khachHang.getTenKhachHang().isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên khách hàng");
	            return false;
	        }  
	     
	        if (khachHang.getNgaySinh_KH() == null) {
	            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày sinh khách hàng");
	            return false;
	        }
		
	        if (!khachHang.getSoDienThoai_KH().startsWith("0") || khachHang.getSoDienThoai_KH().length() != 10) {
	            JOptionPane.showMessageDialog(null, "Số điện thoại phải bắt đầu bằng '0' và có đúng 10 số");
	            return false;
	        }
	        if (khachHang.getGioiTinh_KH() == null) {
	            JOptionPane.showMessageDialog(null, "Vui lòng chọn giới tính khách hàng");
	            return false;
	        }
	        
	        return khachHangDAO.addSach(khachHang);
	    }

	    private boolean isMaSachUnique(String maSach) {
	        boolean unique = true;
	        try {
	            String query = "SELECT COUNT(*) FROM KhachHang WHERE maKhachHang = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, maSach);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                if (count > 0) {
	                    unique = false;
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return unique;
	    }
}
