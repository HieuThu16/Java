package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.ChiTietHoaDon_DTO;
import DTO.KhachHang_DTO;
import Database.ConnectionManager;

public class ChiTietHoaDon_DAO {
    private static Connection connection=ConnectionManager.openConnection();  // Đối tượng Connection để thực hiện các thao tác trên cơ sở dữ liệu

    public ChiTietHoaDon_DAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức để thêm một chi tiết hóa đơn vào cơ sở dữ liệu
    public static boolean themChiTietHoaDon(ChiTietHoaDon_DTO chiTietHoaDon) throws SQLException {
        String query = "INSERT INTO chitiethoadon (maChiTietHoaDon,"
        		+ " maHoaDon, maSach, tenSach,"
        		+ " soLuong, donGia, giamGia, thanhTien)"
        		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ? )";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
        	  ps.setString(1, chiTietHoaDon.getMaChiTietHoaDon());
            ps.setString(2, chiTietHoaDon.getMaHoaDon());
            ps.setString(3, chiTietHoaDon.getMaSach());
            ps.setString(4, chiTietHoaDon.getTenSach());
            ps.setInt(5, chiTietHoaDon.getSoLuong());
            ps.setDouble(6, chiTietHoaDon.getDonGia());
            ps.setDouble(7, chiTietHoaDon.getGiamGia());
            ps.setDouble(8, chiTietHoaDon.getThanhTien());

            ps.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    // Phương thức để lấy ra danh sách các chi tiết hóa đơn từ cơ sở dữ liệu dựa trên mã hóa đơn
    public ArrayList<ChiTietHoaDon_DTO> layDanhSachChiTietHoaDon(String maHoaDon) throws SQLException {
        ArrayList<ChiTietHoaDon_DTO> danhSachChiTietHoaDon = new ArrayList<>();
        String query = "SELECT * FROM chitiethoadon WHERE maHoaDon = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, maHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	String maChiTiet =  rs.getString("maChiTietHoaDon");
                    String maSach = rs.getString("maSach");
                    String tenSach = rs.getString("tenSach");
                    int soLuong = rs.getInt("soLuong");
                    double donGia = rs.getDouble("donGia");
                    double giamGia = rs.getDouble("giamGia");
                    double thanhTien = rs.getDouble("thanhTien");

                    ChiTietHoaDon_DTO chiTietHoaDon = new ChiTietHoaDon_DTO(maChiTiet, maHoaDon, maSach, tenSach, soLuong, donGia, giamGia, thanhTien);
                    danhSachChiTietHoaDon.add(chiTietHoaDon);
                }
            }
        }

        return danhSachChiTietHoaDon;
    }
   

     
    public static ArrayList<ChiTietHoaDon_DTO> getAlldetailBills() {
        ArrayList<ChiTietHoaDon_DTO> chitietList = new ArrayList<>();
        try {
            //openConnection();
            String sql = "SELECT * FROM chitiethoadon";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng Sach_DTO từ dữ liệu trong ResultSet
            	ChiTietHoaDon_DTO chitiet = new ChiTietHoaDon_DTO();
            	chitiet.setMaChiTietHoaDon(rs.getString("machitiethoadon"));
                chitiet.setMaHoaDon(rs.getString("mahoadon"));
                chitiet.setMaSach(rs.getString("masach"));
                chitiet.setTenSach(rs.getString("tensach"));
                chitiet.setSoLuong(rs.getInt("soluong"));
                chitiet.setDonGia(rs.getFloat("dongia"));
                chitiet.setGiamGia(rs.getFloat("giamgia"));
                chitiet.setThanhTien(rs.getFloat("thanhtien"));
                chitietList.add(chitiet);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
           // ConnectionManager.closeConnection();
        }
        return chitietList;
    }
    public float tinhTongGiamGia(ArrayList<ChiTietHoaDon_DTO> chiTietList) {
        float tongGiamGia = 0;
        for (ChiTietHoaDon_DTO chiTiet : chiTietList) {
            tongGiamGia += chiTiet.getGiamGia();
        }
        return tongGiamGia;
    }

    public float tinhTongDonGia(ArrayList<ChiTietHoaDon_DTO> chiTietList) {
        float tongDonGia = 0;
        for (ChiTietHoaDon_DTO chiTiet : chiTietList) {
            tongDonGia += chiTiet.getDonGia();
        }
        return tongDonGia;
    }
    public static int getTotalSoldQuantity() throws SQLException {
        int totalSoldQuantity = 0;

        // Thực hiện truy vấn SQL để tính tổng số lượng sách đã bán cho toàn bộ thời gian
        String query = "SELECT SUM(soLuong) AS totalSold FROM ChiTietHoaDon";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalSoldQuantity = rs.getInt("totalSold");
            }
        }

        return totalSoldQuantity;
    }
    public static boolean deleteChiTietHoaDon(String maChiTietHoaDon) throws SQLException {
        PreparedStatement statement = null;
        boolean deleted = false;
            String query = "DELETE FROM chitiethoadon WHERE machitiethoadon = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, maChiTietHoaDon);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                deleted = true;
            }
  
        return deleted;
    }
    public static boolean checkExistingMaChiTiet(String maChiTiet) throws SQLException {    
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean exists = false; 
            String query = "SELECT COUNT(*) AS count FROM chitiethoadon WHERE machitiethoadon = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, maChiTiet);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                exists = (count > 0);
            }
          
        return exists;
    }
}

