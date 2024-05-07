package BUS;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import DAO.HoaDon_DAO;
import DTO.HoaDon_DTO;

public class HoaDon_BUS {
    private HoaDon_DAO hoaDonDAO;

    public HoaDon_BUS(Connection connection) {
        hoaDonDAO = new HoaDon_DAO(connection);
    }

    public boolean themHoaDon(HoaDon_DTO hoaDonDTO) {
        try {
            // Kiểm tra logic kinh doanh trước khi thêm hóa đơn
            // Ví dụ: Kiểm tra xem các trường thông tin hóa đơn có hợp lệ không
            if (isValidHoaDon(hoaDonDTO)) {
                // Nếu hợp lệ, gọi phương thức từ DAO để thêm hóa đơn vào cơ sở dữ liệu
                return HoaDon_DAO.themHoaDon(hoaDonDTO);
            } else {
                // Nếu không hợp lệ, hiển thị thông báo lỗi cho người dùng
                JOptionPane.showMessageDialog(null, "Thông tin hóa đơn không hợp lệ!");
                return false;
            }
        } catch (SQLException e) {
            // Xử lý nếu có lỗi khi thêm hóa đơn vào cơ sở dữ liệu
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm hóa đơn: " + e.getMessage());
            return false;
        }
    }

    private boolean isValidHoaDon(HoaDon_DTO hoaDonDTO) throws HeadlessException, SQLException {
        // Kiểm tra các trường thông tin hóa đơn có bị rỗng không
        if (hoaDonDTO.getMaHoaDon().isEmpty() || hoaDonDTO.getMaKhachHang().isEmpty() || 
            hoaDonDTO.getMaNhanVien().isEmpty() || hoaDonDTO.getMaGiamGia().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin hóa đơn!");
            return false;
        }
        if (hoaDonDAO.kiemTraHoaDonTrungLap(hoaDonDTO)) {
            JOptionPane.showMessageDialog(null, "Hóa đơn đã tồn tại!");
            return false;
        }
        
   
        
        return true;
    }



}
