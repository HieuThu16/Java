package GUI ; 
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Database.ConnectionManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Color;

public class BookDetailFrame1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection =  ConnectionManager.openConnection(); 
	private JLabel label_maSach1;
	private JLabel label_MaNXB;
	private JLabel label_MANXB1;
	private JLabel label_tenSach1;
	private JLabel label_namXB1;
	private JLabel label_soLuong1;
	private JLabel label_donGia1;
	private JLabel label_maTheLoai1;
	private QuanLiSach_GUI frame1;
	private JLabel label_hinhAnh11;

	private static String maSach ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	QuanLiSach frame1 = new QuanLiSach();
					BookDetailFrame1 frame = new BookDetailFrame1(maSach);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookDetailFrame1(String maSach) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_maSach = new JLabel("Mã sách");
		label_maSach.setBounds(10, 40, 60, 20);
		contentPane.add(label_maSach);
		
		label_MANXB1 = new JLabel("");
		label_MANXB1.setBounds(112, 70, 51, 29);
		contentPane.add(label_MANXB1);
		
		JLabel label_maTheLoai = new JLabel("Mã Thể Loại");
		label_maTheLoai.setBounds(10, 120, 76, 20);
		contentPane.add(label_maTheLoai);
		
		label_maTheLoai1 = new JLabel("");
		label_maTheLoai1.setBounds(112, 120, 45, 20);
		contentPane.add(label_maTheLoai1);
		
		JLabel lblNewLabel_4_1 = new JLabel("Tên Sách");
		lblNewLabel_4_1.setBounds(10, 160, 60, 21);
		contentPane.add(lblNewLabel_4_1);
		
		label_maSach1 = new JLabel("");
		label_maSach1.setBounds(111, 40, 60, 20);
		contentPane.add(label_maSach1);
		
		label_MaNXB = new JLabel("Mã Nhà Xuất Bản");
		label_MaNXB.setBounds(10, 82, 85, 20);
		contentPane.add(label_MaNXB);
		
		JLabel lblNewLabel_7 = new JLabel("Năm Xuất Bản");
		lblNewLabel_7.setBounds(10, 206, 85, 20);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Số Lượng Còn Trong Kho");
		lblNewLabel_8.setBounds(10, 250, 161, 20);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Giá Sách");
		lblNewLabel_9.setBounds(10, 296, 60, 20);
		contentPane.add(lblNewLabel_9);
		
		label_tenSach1 = new JLabel("Nội dung của label_tenSach1");
		label_tenSach1.setBounds(92, 160, 187, 13); // Thiết lập kích thước của JLabel
		label_tenSach1.setSize(label_tenSach1.getPreferredSize()); // Thiết lập kích thước của JLabel để phù hợp với nội dung
		contentPane.add(label_tenSach1);


		
		label_namXB1 = new JLabel("");
		label_namXB1.setBounds(160, 206, 104, 29);
		contentPane.add(label_namXB1);
		
		label_soLuong1 = new JLabel("");
		label_soLuong1.setBounds(112, 249, 51, 29);
		contentPane.add(label_soLuong1);
		
		label_donGia1 = new JLabel("");
		label_donGia1.setBackground(Color.LIGHT_GRAY);
		label_donGia1.setBounds(112, 287, 96, 29);
		contentPane.add(label_donGia1);
		
		label_hinhAnh11 = new JLabel("New label");
		label_hinhAnh11.setBounds(320, 40, 212, 309);
		contentPane.add(label_hinhAnh11);
		
		loadBookData(maSach);
	}
	public void loadBookData(String maSach) {
            PreparedStatement pstmt = null;
			try {
				pstmt = connection.prepareStatement("SELECT maNXB, maTheLoai ,maTacGia ,"
						+ " tenSach , namXuatBan,soLuong ,donGia , "
						+ "hinhAnh FROM sach WHERE maSach = ?");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				pstmt.setString(1, maSach);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
            ResultSet rs = null;
			try {
				rs = pstmt.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            try {
				if (rs.next()) {
				    // Set JLabel texts with retrieved information
					label_maSach1.setText(maSach);
				    label_MANXB1.setText(rs.getString("maNXB"));
				    label_maTheLoai1.setText(rs.getString("maTheLoai"));
				    label_tenSach1.setText(rs.getString("tenSach"));
				    label_namXB1.setText(rs.getString("NamXuatban"));
				    label_soLuong1.setText(rs.getString("soLuong"));
				    label_donGia1.setText(rs.getString("DonGia"));
				    String imagePath =rs.getString("HinhAnh");
				    System.out.println(imagePath);
	
				    showImageOnLabel1(imagePath);
				  
				    
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
	 private void showImageOnLabel1(String imagePath) {
		 String duongdan= "C:\\Users\\ASUS Vivobook\\Desktop\\image\\";
		 String linkdung = duongdan + imagePath;

		 try {
		     ImageIcon icon = new ImageIcon(linkdung);
		     Image image = icon.getImage();

		     int newWidth = 200; // Thay đổi kích thước theo nhu cầu của bạn
		     int newHeight = 300; // Thay đổi kích thước theo nhu cầu của bạn

		     // Thay đổi kích thước của hình ảnh
		     Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

		     // Tạo một ImageIcon mới từ hình ảnh đã được thay đổi kích thước
		     ImageIcon scaledIcon = new ImageIcon(scaledImage);

		
		     if (label_hinhAnh11 != null) {
		         label_hinhAnh11.setIcon(scaledIcon);
		     } else {
		         System.out.println("label_hinhAnh1 is null. Make sure it's properly initialized.");
		     }
		 } catch (Exception e) {
		     e.printStackTrace();
		     System.out.println("Error loading or resizing image: " + e.getMessage());
		 }
	 }
    }
