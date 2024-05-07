package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DTO.ChiTietHoaDon_DTO;
import DTO.ChiTietPhieuNhap_DTO;
import Database.ConnectionManager;

public class ChiTietPhieuNhap_DAO {
	
	private static Connection connection = ConnectionManager.openConnection(); 
	
    // Phương thức để lấy danh sách các chi tiết phiếu nhập từ cơ sở dữ liệu
    public ArrayList<ChiTietPhieuNhap_DTO> getAllChiTietPhieuNhap() {
        ArrayList<ChiTietPhieuNhap_DTO> danhSachChiTietPhieuNhap = new ArrayList<>();
  
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM ChiTietPhieuNhap";
            preparedStatement = connection.prepareStatement(query);

            // Thực hiện truy vấn
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua kết quả và tạo các đối tượng DTO
            while (resultSet.next()) {
            	String maChiTietPhieuNhap = resultSet.getString("maChiTietPhieuNhap");
                String maPhieuNhap = resultSet.getString("maPhieuNhap");
                String maSach = resultSet.getString("maSach");
           
                int soLuong = resultSet.getInt("soLuong");
                double donGia = resultSet.getDouble("donGia");
                double tongTien = resultSet.getDouble("tongTienNhap");

                ChiTietPhieuNhap_DTO chiTietPhieuNhap = new ChiTietPhieuNhap_DTO
                (maChiTietPhieuNhap, maPhieuNhap, maSach,  soLuong, donGia, tongTien);
                danhSachChiTietPhieuNhap.add(chiTietPhieuNhap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return danhSachChiTietPhieuNhap;
    }

    // Phương thức để thêm một chi tiết phiếu nhập vào cơ sở dữ liệu
    public static boolean addChiTietPhieuNhap(ChiTietPhieuNhap_DTO chiTietPhieuNhap) {
        PreparedStatement preparedStatement = null;
        boolean isSuccess = false;

        try {

            String query = "INSERT INTO ChiTietPhieuNhap (maChiTietPhieuNhap, maPhieuNhap, maSach,"
            		+ " soLuong, donGia, tongTienNhap) "
            		+ "VALUES (?, ?, ?, ?, ?, ? )";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, chiTietPhieuNhap.getMaChiTietPhieuNhap());
            preparedStatement.setString(2, chiTietPhieuNhap.getMaPhieuNhap());
            preparedStatement.setString(3, chiTietPhieuNhap.getMaSach());
          
            preparedStatement.setInt(4, chiTietPhieuNhap.getSoLuong());
            preparedStatement.setDouble(5, chiTietPhieuNhap.getDonGia());
            preparedStatement.setDouble(6, chiTietPhieuNhap.getTongTien());

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
    
    public ArrayList<ChiTietPhieuNhap_DTO> layDanhSachChiTietPhieuNhap(String maPhieuNhap) throws SQLException {
        ArrayList<ChiTietPhieuNhap_DTO> danhSachChiTietPhieuNhap = new ArrayList<>();
        String query = "SELECT * FROM chitietphieunhap WHERE maphieunhap = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, maPhieuNhap);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String machitietPhieuNhap = rs.getString("machitietPhieuNhap");
                    String maSach = rs.getString("maSach");
                  
                    int soLuong = rs.getInt("soLuong");
                    double donGia = rs.getDouble("donGia");
                  
                    double thanhTien = rs.getDouble("tongTiennhap");

                    ChiTietPhieuNhap_DTO chiTietHoaDon = new ChiTietPhieuNhap_DTO(
                    		machitietPhieuNhap , maPhieuNhap, maSach,
                    		 soLuong, donGia, thanhTien);
                    danhSachChiTietPhieuNhap.add(chiTietHoaDon);
                }
            }
        }

        return danhSachChiTietPhieuNhap;
    }
    public int countDuplicateMaCTGG(String maCTGG) throws SQLException {
        String query = "SELECT COUNT(*) FROM chitietphieunhap WHERE machitietphieunhap = ?";
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
    public double getTongTienPhieuNhap(String maPhieuNhap) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        double tongTien = 0;
     
            String query = "SELECT SUM(TongTienNhap) FROM ChiTietPhieuNhap WHERE MaPhieuNhap = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, maPhieuNhap);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tongTien = resultSet.getDouble(1);
            }
        

        return tongTien;
    }
    public boolean updateTongTienPhieuNhap(String maPhieuNhap, double tongTienMoi) throws SQLException {
        PreparedStatement statement = null;
        boolean updateSuccess = false;

        try {
            String query = "UPDATE PhieuNhap SET TongTien = ? WHERE MaPhieuNhap = ?";
            statement = connection.prepareStatement(query);
            statement.setDouble(1, tongTienMoi);
            statement.setString(2, maPhieuNhap);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                updateSuccess = true;
            }
        } finally {
            // Đóng tài nguyên
            if (statement != null) {
                statement.close();
            }
        }

        return updateSuccess;
    }
    public static int getTotalSoldQuantity() throws SQLException {
        int totalSoldQuantity = 0;

        // Thực hiện truy vấn SQL để tính tổng số lượng sách đã bán cho toàn bộ thời gian
        String query = "SELECT SUM(soLuong) AS totalSold FROM ChiTietPhieuNhap";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalSoldQuantity = rs.getInt("totalSold");
            }
        }

        return totalSoldQuantity;
    }
   
}

