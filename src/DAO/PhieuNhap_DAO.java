package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DTO.PhieuNhap_DTO;
import Database.ConnectionManager;

public class PhieuNhap_DAO {
	 private static Connection connection = ConnectionManager.openConnection(); 
    public static ArrayList <PhieuNhap_DTO> getAllPhieuNhap() {
    	ArrayList<PhieuNhap_DTO> danhSachPhieuNhap = new ArrayList<>();
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
      
            String query = "SELECT * FROM PhieuNhap";
            preparedStatement = connection.prepareStatement(query);

           
            resultSet = preparedStatement.executeQuery();

            
            while (resultSet.next()) {
                String maPhieuNhap = resultSet.getString("maPhieuNhap");
                String maNhaCungCap = resultSet.getString("maNhaCungCap");
                String maNhanVien = resultSet.getString("maNhanVien");
                Date ngayNhap = resultSet.getDate("ngaylap");
                double tongTien = resultSet.getDouble("tongTien");

                PhieuNhap_DTO phieuNhap = new PhieuNhap_DTO(maPhieuNhap, maNhaCungCap, maNhanVien, ngayNhap, tongTien);
                danhSachPhieuNhap.add(phieuNhap);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        
        }

        return danhSachPhieuNhap;
    }

  
    public boolean addPhieuNhap(PhieuNhap_DTO phieuNhap) {
        PreparedStatement preparedStatement = null;
        boolean isSuccess = false;
        try {
            String query = "INSERT INTO PhieuNhap (maPhieuNhap, maNhaCungCap, maNhanVien, ngaylap, tongTien) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phieuNhap.getMaPhieuNhap());
            preparedStatement.setString(2, phieuNhap.getMaNhaCungCap());
            preparedStatement.setString(3, phieuNhap.getMaNhanVien());
            preparedStatement.setDate(4, new java.sql.Date(phieuNhap.getNgayNhap().getTime()));
            preparedStatement.setDouble(5, phieuNhap.getTongTien());

            // Thực hiện câu lệnh SQL
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return isSuccess;
    }

	  public int countDuplicateMaCTGG(String maCTGG) throws SQLException {
	        String query = "SELECT COUNT(*) FROM phieunhap WHERE maPhieuNhap = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, maCTGG);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getInt(1);
	                }
	            }
	        }
	        return 0;
	    }
	   public static boolean hasDetails(String maCTGG) throws SQLException {
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        boolean hasDetails = false;
	        
	            String query = "SELECT COUNT(*) FROM phieunhap WHERE maphieuNhap = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, maCTGG);

	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                hasDetails = count > 0;
	            }     
	        return hasDetails;
	   }
	   public static boolean deleteCTGG(String maCTGG) throws SQLException {
	     
	        PreparedStatement statement = null;

	            String query = "DELETE FROM PhieuNhap WHERE maPhieuNhap = ?";
	            statement = connection.prepareStatement(query);
	            statement.setString(1, maCTGG);

	            // Thực thi truy vấn
	            int rowsDeleted = statement.executeUpdate();

	            // Trả về true nếu có dòng bị xóa, ngược lại trả về false
	            return rowsDeleted > 0;
	        } 
	   public static ResultSet searchAndDisplayFromDatabase(String maNhanVien,
	    		String ho, String ten, 
	    		String gioiTinh) {
	        // Bắt đầu truy vấn
	        String query = "SELECT * FROM khachHang WHERE";
	        List<String> conditions = new ArrayList<>();

	        // Thêm điều kiện vào danh sách nếu giá trị không rỗng
	        if (!maNhanVien.isEmpty()) {
	            conditions.add("MaKhachHang = '" + maNhanVien + "'");
	        }
	        if (!ho.isEmpty()) {
	            conditions.add("hoKhachHang = '" + ho + "'");
	        }
	        if (!ten.isEmpty()) {
	            conditions.add("tenKhachHang = '" + ten + "'");
	        }
	        if (!gioiTinh.isEmpty()) {
	            conditions.add("gioiTinh = '" + gioiTinh + "'");
	        }
	        // Kết hợp các điều kiện bằng AND
	        if (!conditions.isEmpty()) {
	            query += " " + String.join(" AND ", conditions);
	        } else {
	            // Nếu không có điều kiện, trả về tất cả dữ liệu
	            query = "SELECT * FROM khachhang";
	        }

	        // Thực thi truy vấn và trả về ResultSet
	        try {
	            Statement statement = connection.createStatement();
	            return statement.executeQuery(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null; // Trả về null nếu có lỗi xảy ra
	        }
	    }
	   public static ResultSet searchAndDisplayFromDatabase(String maPhieuNhap, 
				 String maNhaCungCap, String maNhanVien,
				 Date ngayBatDau, Double giaTu, Double giaDen) {
		    // Bắt đầu truy vấn
		    String query = "SELECT * FROM phieunhap WHERE";
		    List<String> conditions = new ArrayList<>();

		    // Thêm điều kiện vào danh sách nếu giá trị không rỗng
		    if (!maPhieuNhap.isEmpty()) {
		        conditions.add("maPhieuNhap = '" + maPhieuNhap + "'");
		    }
		    if (!maNhaCungCap.isEmpty() ) {
		        conditions.add("maNhaCungCap = '" + maNhaCungCap + "'");
		    }
		    if (!maNhanVien.isEmpty()) {
		        conditions.add("maNhanVien = '" + maNhanVien + "'");
		    }
		    if (ngayBatDau != null) {
		        conditions.add("ngaylap >= '" + ngayBatDau + "'");
		    }
		    if (giaTu != null && giaTu >= 0) {
		        conditions.add("TongTien >= " + giaTu);
		    }
		    if (giaDen != null && giaDen >= 0) {
		        conditions.add("TongTien <= " + giaDen);
		    }
		    // Kết hợp các điều kiện bằng AND
		    if (!conditions.isEmpty()) {
		        query += " " + String.join(" AND ", conditions);
		    } else {
		        // Nếu không có điều kiện, trả về tất cả dữ liệu
		        query = "SELECT * FROM phieunhap";
		    }

		    // Thực thi truy vấn và trả về ResultSet
		    try {
		        Statement statement = connection.createStatement();
		        return statement.executeQuery(query);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return null; // Trả về null nếu có lỗi xảy ra
		    }
		}

	    public static double getTotalSalesForDay(Date date) throws SQLException {
	        double totalInput = 0.0;

	      
	            String sql = "SELECT SUM(tongtien) AS total_input FROM phieunhap WHERE ngaylap = ?";
	            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	                stmt.setDate(1, new java.sql.Date(date.getTime()));

	                try (ResultSet rs = stmt.executeQuery()) {
	                    if (rs.next()) {
	                        totalInput = rs.getDouble("total_input");
	                    }
	                }
	            }
	 

	        return totalInput;
	    }
	    public double calculateTotalInput() throws SQLException {
	        double totalInput = 0;

	            String query = "SELECT SUM(tongTien) AS total_input FROM phieunhap";
	            PreparedStatement statement = connection.prepareStatement(query);

	            // Thực thi truy vấn
	            ResultSet resultSet = statement.executeQuery();

	            // Xử lý kết quả
	            if (resultSet.next()) {
	                totalInput = resultSet.getDouble("total_input");
	            }

	        return totalInput;
	    }

	    
}
