package DAO;

import DTO.Sach_DTO;
import Database.ConnectionManager;

import java.awt.Image;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;


public class Sach_DAO {
	
	private static Connection connection = ConnectionManager.openConnection(); 

    public Sach_DAO(Connection connection) {
        Sach_DAO.connection = connection ;
    } 
    
    public boolean addSach(Sach_DTO sach) {
        boolean result = false;
        try {
           // openConnection();
            String sql = "INSERT INTO Sach (maSach, maNXB, maTheLoai, maTacGia ,"
            		+ " tenSach, namXuatBan, soLuong,"
            		+ " donGia, hinhAnh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, sach.getMaSach());
            stmt.setString(2, sach.getMaNXB());
            stmt.setString(3, sach.getMaTheLoai());
            stmt.setString(4, sach.getMaTacGia());
            stmt.setString(5, sach.getTenSach());
            stmt.setInt(6, sach.getNamXuatBan());
            stmt.setInt(7, sach.getSoLuong());
            stmt.setDouble(8, sach.getDonGia());
          
            stmt.setString(9, sach.getHinhAnh());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
        	//ConnectionManager.closeConnection();
        }
        return result;
    }

    public static boolean deleteSach(String maSach) {
        boolean result = false;
        try {
            //openConnection();
            String sql = "DELETE FROM Sach WHERE maSach=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maSach);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
        	//ConnectionManager.closeConnection();
        }
        return result;
    }
    public static boolean updateSach(Sach_DTO sach) {
        boolean result = false;
        try {
            //openConnection();
            String sql = "UPDATE Sach SET maNXB=?, maTheLoai=?, maTacGia=?, tenSach=?, namXuatBan=?, soLuong=?, donGia=?, hinhAnh=? WHERE maSach=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, sach.getMaNXB());
            stmt.setString(2, sach.getMaTheLoai());
            stmt.setString(3, sach.getMaTacGia());
            stmt.setString(4, sach.getTenSach());
            stmt.setInt(5, sach.getNamXuatBan());
            stmt.setInt(6, sach.getSoLuong());
            stmt.setDouble(7, sach.getDonGia());
            stmt.setString(8, sach.getHinhAnh());
            stmt.setString(9, sach.getMaSach());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
    //    	ConnectionManager.closeConnection();
        }
        return result;
    }
    public static ArrayList<Sach_DTO> getAllBooks() {
        ArrayList<Sach_DTO> sachList = new ArrayList<>();
        try {
            //openConnection();
            String sql = "SELECT * FROM Sach";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng Sach_DTO từ dữ liệu trong ResultSet
                Sach_DTO sach = new Sach_DTO();
                sach.setMaSach(rs.getString("maSach"));
                sach.setMaNXB(rs.getString("maNXB"));
                sach.setMaTheLoai(rs.getString("maTheLoai"));
                sach.setMaTacGia(rs.getString("maTacGia"));
                sach.setTenSach(rs.getString("tenSach"));
                sach.setNamXuatBan(rs.getInt("namXuatBan"));
                sach.setSoLuong(rs.getInt("soLuong"));
                sach.setDonGia(rs.getDouble("donGia"));
                sach.setHinhAnh(rs.getString("hinhAnh"));
                // Thêm sách vào danh sách
                sachList.add(sach);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
        //	ConnectionManager.closeConnection();
        }
        return sachList;
    }
    public static ResultSet searchAndDisplayFromDatabase
	 (String maSach, String maNXB, String maTheLoai,
			 String maTacGia, String tenSach, String donGiaTu, String donGiaDen) {
		    
		        // Bắt đầu truy vấn
		        String query = "SELECT * FROM sach WHERE";
		        List<String> conditions = new ArrayList<>();

		        // Thêm điều kiện vào danh sách nếu giá trị không rỗng
		        if (!maSach.isEmpty()) {
		            conditions.add("MaSach = '" + maSach + "'");
		        }
		        if (!maNXB.isEmpty()) {
		            conditions.add("MaNXB = '" + maNXB + "'");
		        }
		        if (!maTheLoai.isEmpty()) {
		            conditions.add("MaTheLoai = '" + maTheLoai + "'");
		        }
		        if (!maTacGia.isEmpty()) {
		            conditions.add("MaTacGia = '" + maTacGia + "'");
		        }
		        if (!tenSach.isEmpty()) {
		            conditions.add("TenSach LIKE '%" + tenSach + "%'");
		        }


		        if (!donGiaTu.isEmpty()) {
		            conditions.add("DonGia >= " + donGiaTu); // Giá từ
		        }
		        if (!donGiaDen.isEmpty()) {
		            conditions.add("DonGia <= " + donGiaDen); // Giá đến
		        }

		        // Kết hợp các điều kiện bằng AND
		        if (!conditions.isEmpty()) {
		            query += " " + String.join(" AND ", conditions);
		        } else {
		            // Nếu không có điều kiện, trả về tất cả dữ liệu
		            query = "SELECT * FROM sach";
		        }

		        // Thực thi truy vấn
		        Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			return resultSet ;
    }
    public static Sach_DTO getSachByMaSach(String maSach) {
        Sach_DTO sach = null;
        try {
            String sql = "SELECT * FROM Sach WHERE maSach = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maSach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sach = new Sach_DTO(
                    rs.getString("maSach"),
                    rs.getString("maNXB"),
                    rs.getString("maTheLoai"),
                    rs.getString("maTacGia"),
                    rs.getString("tenSach"),
                    rs.getInt("namXuatBan"),
                    rs.getInt("soLuong"),
                    rs.getDouble("donGia"),
                    rs.getString("hinhAnh")
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
          //  ConnectionManager.closeConnection();
        }
        return sach;
    }
    public static int getSoLuongByMaSach(String maSach) throws SQLException {
        int soLuong = 0;
        String query = "SELECT soLuong FROM Sach WHERE maSach = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    soLuong = rs.getInt("soLuong");
                }
            }
        }
        return soLuong;
    }

    // Phương thức để cập nhật số lượng mới của sách trong cơ sở dữ liệu dựa trên mã sách
    public static void updateSoLuongByMaSach(String maSach, int soLuongMoi) throws SQLException {
        String query = "UPDATE Sach SET soLuong = ? WHERE maSach = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, soLuongMoi);
            ps.setString(2, maSach);
            ps.executeUpdate();
        }
    }
    public static int countTotalQuantityOfBooks() throws SQLException {
        int totalQuantity = 0;

        // Kết nối đến cơ sở dữ liệu
      
            // Chuẩn bị truy vấn
            String query = "SELECT SUM(soLuong) AS total_quantity FROM sach";
            PreparedStatement statement = connection.prepareStatement(query);

            // Thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            // Xử lý kết quả
            if (resultSet.next()) {
                totalQuantity = resultSet.getInt("total_quantity");
            }

        return totalQuantity;
    }

    public static List<String> getSimilarBookTitles(String inputTitle) throws SQLException {
        List<String> similarTitles = new ArrayList<>();

        // Tạo điều kiện cho câu truy vấn SQL dựa trên inputTitle
        String condition = "";
        if (!inputTitle.isEmpty()) {
            condition = "WHERE TenSach LIKE '" + inputTitle + "%'";
        }

        // Tạo câu truy vấn SQL với điều kiện đã xây dựng
        String sql = "SELECT TenSach FROM sach " + condition;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        // Lặp qua kết quả truy vấn và thêm các tên sách vào danh sách gợi ý
        while (resultSet.next()) {
            String title = resultSet.getString("TenSach");
            similarTitles.add(title);
        }

        return similarTitles;
    }

    public static String getBestSellingBookId() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            // Thực hiện truy vấn để lấy mã sách có số lượng bán nhiều nhất
            String query = "SELECT masach FROM chitiethoadon ORDER BY soluong DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);

            // Lấy thông tin về mã sách có số lượng bán nhiều nhất
            String bestSellingBookId = "";
            if (rs.next()) {
                bestSellingBookId = rs.getString("masach");
            }

            return bestSellingBookId;

        } finally {
            if (stmt != null) stmt.close();
        }
    }
    public static int getBestSellingQuantity() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            // Thực hiện truy vấn để lấy số lượng sách được bán của mã sách có số lượng bán nhiều nhất
            String query = "SELECT soluong FROM chitiethoadon ORDER BY soluong DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);

            // Lấy số lượng sách được bán
            int bestSellingQuantity = 0;
            if (rs.next()) {
                bestSellingQuantity = rs.getInt("soluong");
            }

            return bestSellingQuantity;

        } finally {
            if (stmt != null) stmt.close();
        }
    }

    
}
