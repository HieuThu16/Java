package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import DTO.NhaCungCap_DTO;
import DTO.Sach_DTO;
import Database.ConnectionManager;
public class NhaCungCap_DAO {
	private static Connection connection = ConnectionManager.openConnection(); 

	public NhaCungCap_DAO(Connection connection) {
		NhaCungCap_DAO.connection = connection ;
	} 
	
	public boolean addSach(NhaCungCap_DTO nhaCungCap) {
		boolean result = false;
		try {
       // openConnection();
        String sql = "INSERT INTO NhaCungCap (maNhaCungCap, Ho, Ten, soDienThoai ,diaChi,"
        		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nhaCungCap.getmaNhaCungCap());
        stmt.setString(2, nhaCungCap.getHo());
        stmt.setString(3, nhaCungCap.getTen());
        stmt.setString(4, nhaCungCap.getSoDienThoai());
        stmt.setString(5, nhaCungCap.getdiaChi());
      
        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            result = true;
        }
		} catch (SQLException ex) {
        System.out.println(ex);
		} finally {
    //	ConnectionManager.closeConnection();
		}
    return result;
}

		public static boolean delete(String maNhaCungCap) {
			boolean result = false;
			try {
        //openConnection();
					String sql = "DELETE FROM NhaCungCap WHERE maNhaCungCap=?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, maNhaCungCap);

					int rowsDeleted = stmt.executeUpdate();
					if (rowsDeleted > 0) {
						result = true;
					}
			} catch (SQLException ex) {
					System.out.println(ex);
			} finally {
		//			ConnectionManager.closeConnection();
			}
    return result;
}

public static ArrayList<NhaCungCap_DTO> getAllEmployee() {
    ArrayList<NhaCungCap_DTO> nhaCungCapList = new ArrayList<>();
    try {
        //openConnection();
        String sql = "SELECT * FROM nhaCungCap";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            // Tạo đối tượng Sach_DTO từ dữ liệu trong ResultSet
        	NhaCungCap_DTO nhaCungCap = new NhaCungCap_DTO();
            nhaCungCap.setmaNhaCungCap(rs.getString("maNhaCungCap"));
            nhaCungCap.setHo(rs.getString("ho"));
            nhaCungCap.setTen(rs.getString("ten"));
            nhaCungCap.setSoDienThoai(rs.getString("soDienThoai"));
            nhaCungCap.setdiaChi(rs.getString("diaChi"));
            nhaCungCapList.add(nhaCungCap);
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    } finally {
    //	ConnectionManager.closeConnection();
    }
    return nhaCungCapList;
}

public static boolean updateNhaCungCap(NhaCungCap_DTO nhaCungCap) {
    boolean result = false;
 
    try {
       // connection = ConnectionManager.getConnection(); // Lấy kết nối từ ConnectionManager

        String sql = "UPDATE NhaCungCap SET ho=?, ten=?, soDienThoai=?, diaChi=?,"
                    + " WHERE maNhaCungCap=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nhaCungCap.getHo());
        stmt.setString(2, nhaCungCap.getTen());
        stmt.setString(3, nhaCungCap.getSoDienThoai());
        stmt.setString(4, nhaCungCap.getdiaChi());


        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            result = true;
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    } finally {
     // Đóng kết nối sau khi sử dụng xong
    }
    return result;
}

public static ResultSet searchAndDisplayFromDatabase(String maNhaCungCap, String ho, String ten, String soDienThoai, String diaChi, String sapXepTheo, String tangGiam) {
    // Bắt đầu truy vấn
    String query = "SELECT * FROM nhacungcap WHERE";
    List<String> conditions = new ArrayList<>();

    // Thêm điều kiện vào danh sách nếu giá trị không rỗng
    if (!maNhaCungCap.isEmpty()) {
        conditions.add("maNhaCungCap = '" + maNhaCungCap + "'");
    }
    if (!ho.isEmpty()) {
        conditions.add("ho = '" + ho + "'");
    }
    if (!ten.isEmpty()) {
        conditions.add("ten = '" + ten + "'");
    }
   if (!soDienThoai.isEmpty()) {
       conditions.add("soDienThoai = '" + soDienThoai + "'");
   }
    if (!diaChi.isEmpty()) {
        conditions.add("diaChi = '" + diaChi + "'");
    }

    // Kết hợp các điều kiện bằng AND
    if (!conditions.isEmpty()) {
        query += " " + String.join(" AND ", conditions);
    } else {
        // Nếu không có điều kiện, trả về tất cả dữ liệu
        query = "SELECT * FROM nhacungcap";
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


public static ResultSet getEmployeeByMaNhaCungCap(String maNhaCungCap) {
    // Bắt đầu truy vấn
    String query = "SELECT * FROM nhacungcap WHERE MaNhaCungCap = '" + maNhaCungCap + "'";

    // Thực thi truy vấn và trả về ResultSet
    try {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    } catch (SQLException e) {
        e.printStackTrace();
        return null; // Trả về null nếu có lỗi xảy ra
    }
}
public static int countSuppliers() throws SQLException {
    Statement stmt = null;
    try {
        stmt = connection.createStatement();

        // Thực hiện truy vấn để đếm số dòng trong bảng nhà cung cấp
        String query = "SELECT COUNT(*) AS total_suppliers FROM nhacungcap";
        ResultSet rs = stmt.executeQuery(query);

        // Lấy số lượng nhà cung cấp
        int totalSuppliers = 0;
        if (rs.next()) {
            totalSuppliers = rs.getInt("total_suppliers");
        }

        return totalSuppliers;

    } finally {
        if (stmt != null) stmt.close();
    }
}


}