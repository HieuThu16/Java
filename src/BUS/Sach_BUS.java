package BUS;

import DAO.Sach_DAO;
import DTO.Sach_DTO;
import Database.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Sach_BUS {
    private Sach_DAO sachDAO;
    private Connection connection = ConnectionManager.openConnection(); 
    public Sach_BUS(Connection connection) {
        this.sachDAO = new Sach_DAO(connection);
    }

    public boolean addBook(Sach_DTO sach) {
        // Kiểm tra tính hợp lệ của mã sách là duy nhất
        if (!isMaSachUnique(sach.getMaSach())) {
        	 JOptionPane.showMessageDialog(null, "Mã sách đã tồn tại");
            return false;
        }
        
        // Kiểm tra các ô nhập hình ảnh không được rỗng
        if (sach.getHinhAnh().equals("Hình Ảnh Sách")) {
        	JOptionPane.showMessageDialog(null, "Vui lòng thêm hình ảnh cho sách");
            return false;
        }

        // Nếu sách hợp lệ, gọi đến DAO để thêm vào cơ sở dữ liệu
        return sachDAO.addSach(sach);
    }


    public boolean deleteBook(String maSach) {
        // Kiểm tra và xử lý logic xóa sách ở đây nếu cần
        return sachDAO.deleteSach(maSach);
    }

    public boolean updateBook(Sach_DTO sach) {
        // Kiểm tra và xử lý logic cập nhật sách ở đây nếu cần
        return sachDAO.updateSach(sach);
    }

    public ResultSet searchBooks(String maSach, String maNXB, String maTheLoai, String maTacGia, String tenSach, String donGiaTu, String donGiaDen) {
        // Thực hiện tìm kiếm sách và trả về ResultSet
        return sachDAO.searchAndDisplayFromDatabase(maSach, maNXB, maTheLoai, maTacGia, tenSach, donGiaTu, donGiaDen);
    }

//    public void exportBooksToExcel(String fileName) throws SQLException {
//        // Thực hiện xuất dữ liệu về sách ra Excel
//        sachDAO.exportBooksToExcel(fileName);
//    }
    private boolean isMaSachUnique(String maSach) {
        boolean unique = true;
        try {
            // Tạo câu truy vấn SQL để kiểm tra mã sách trong cơ sở dữ liệu
            String query = "SELECT COUNT(*) FROM Sach WHERE maSach = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maSach);
            ResultSet resultSet = statement.executeQuery();

            // Nếu có bất kỳ dòng nào trả về từ truy vấn, mã sách đã tồn tại và không duy nhất
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    unique = false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Xử lý ngoại lệ nếu cần
        }
        return unique;
    }

}
