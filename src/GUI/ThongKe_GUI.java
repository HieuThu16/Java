package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.DateUtil;
import com.toedter.calendar.JDateChooser;

import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.NhanVien_DAO;
import DAO.PhieuNhap_DAO;
import DAO.Sach_DAO;
import Database.ConnectionManager;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ThongKe_GUI extends JFrame {
	 private static Connection connection=ConnectionManager.openConnection(); 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JPanel panel_1;
	private JFreeChart lineChart;
	private JTextField textField;
	private JDateChooser dateChooserStart;
	private JDateChooser dateChooserEnd;
	private JLabel label_soSach;
	private JLabel label_soNhanVien;
	private JLabel label_soKhachHang;
	private JLabel label_tienNhap;
	private JLabel label_tienBan;
	private double numberOfBooks;
	private double numberOfBooks1;
	private JLabel label_doanhThu;
	private double change;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_4_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThongKe_GUI frame = new ThongKe_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ThongKe_GUI() throws SQLException {
		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1246, 781);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(424, 54, 360, 126);
		contentPane.add(panel_1);
		panel_1.setLayout(null); // Vì bạn đã sử dụng layout null
		panel_1.setBackground(new Color(255, 255, 255)); // Màu cam nhạt
		
		JLabel lblNewLabel = new JLabel("THỐNG KÊ TỔNG QUÁT");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(453, 14, 277, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_3_1 = new JLabel("Nhân viên hoạt động");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_1.setBounds(137, 82, 157, 30);
		panel_1.add(lblNewLabel_3_1);
		
		label_soNhanVien = new JLabel("");
		label_soNhanVien.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_soNhanVien.setBounds(137, 35, 184, 37);
		panel_1.add(label_soNhanVien);
		label_soNhanVien.setOpaque(true); // Kích hoạt tính năng vẽ màu nền
		label_soNhanVien.setBackground(new Color(220, 220, 220));
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		ImageIcon iconã = new ImageIcon("src\\icon\\nhanvienthongkethongke.png");
		Image imageã = iconã.getImage();
		Image scaledImageã = imageã.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconã = new ImageIcon(scaledImageã);
		panel_1.setLayout(null);
		lblNewLabel_1_1.setIcon(scaledIconã);
		lblNewLabel_1_1.setBounds(10, 10, 110, 92);
		panel_1.add(lblNewLabel_1_1);
		ImageIcon iconãã1 = new ImageIcon("src\\icon\\money_nhap.png");
		Image imageãã1 = iconãã1.getImage();
		Image scaledImageãã1 = imageãã1.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã1 = new ImageIcon(scaledImageãã1);
		ImageIcon iconãã12 = new ImageIcon("src\\icon\\money_ban.png");
		Image imageãã12 = iconãã12.getImage();
		Image scaledImageãã12 = imageãã12.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã12 = new ImageIcon(scaledImageãã12);
		ImageIcon iconãã122 = new ImageIcon("src\\icon\\doanhthu.png");
		Image imageãã122 = iconãã122.getImage();
		Image scaledImageãã122 = imageãã122.getScaledInstance(100, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã122 = new ImageIcon(scaledImageãã122);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 390, 383, 266);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBounds(21, 54, 339, 126);
		panel.setLayout(null); // Vì bạn đã sử dụng layout null
		panel.setBackground(Color.WHITE); // Màu trắng
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(0, 13, 116, 102);
		ImageIcon iconãã123 = new ImageIcon("src\\icon\\bookthongkethongke.png");
		Image imageãã123 = iconãã123.getImage();
		Image scaledImageãã123 = imageãã123.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã123 = new ImageIcon(scaledImageãã123);
		lblNewLabel_1.setIcon(scaledIconãã123);
		panel.add(lblNewLabel_1);
		
		label_soSach = new JLabel("");
		label_soSach.setBounds(126, 34, 177, 40);
		label_soSach.setOpaque(true);
		label_soSach.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_soSach.setBackground(new Color(220, 220, 220));
		panel.add(label_soSach);
		
		JLabel lblNewLabel_3 = new JLabel("Số sách còn trong kho");
		lblNewLabel_3.setBounds(126, 84, 177, 25);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblNewLabel_3);
		
		
		lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(271, 676, 819, 30);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Nhận Xét");
		lblNewLabel_5.setForeground(new Color(255, 0, 0));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_5.setBounds(58, 692, 169, 30);
		contentPane.add(lblNewLabel_5);
		
		lblNewLabel_4_1 = new JLabel("New label");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4_1.setBounds(271, 714, 837, 30);
		contentPane.add(lblNewLabel_4_1);
		
		dateChooserStart = new JDateChooser();
		dateChooserStart.setBounds(498, 350, 150, 30);
		dateChooserEnd = new JDateChooser();
		dateChooserEnd.setBounds(772, 350, 150, 30);
		contentPane.add(dateChooserStart);
		contentPane.add(dateChooserEnd);
		// Lấy ngày hiện tại
		Calendar today = Calendar.getInstance();
	

		// Đặt ngày này làm giá trị mặc định cho dateChooserEnd
		dateChooserEnd.setDate(today.getTime());

		// Giảm 10 ngày từ ngày hiện tại
		today.add(Calendar.DATE, -5);

		// Đặt ngày này làm giá trị mặc định cho dateChooserStart
		dateChooserStart.setDate(today.getTime());
		dateChooserStart.addPropertyChangeListener(new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent evt) {
		        if ("date".equals(evt.getPropertyName())) {
		            try {
		                // Xóa biểu đồ cũ nếu có
		                Component[] components = contentPane.getComponents();
		                for (Component component : components) {
		                    if (component instanceof ChartPanel) {
		                        contentPane.remove(component);
		                    }
		                }

		                // Tạo biểu đồ mới và thêm vào contentPane
		                JFreeChart chart = createLineChart();
		                ChartPanel chartPanel = new ChartPanel(chart);
		                chartPanel.setBounds(424, 364, 750, 292);
		                contentPane.add(chartPanel);

		                // Cập nhật giao diện
		                contentPane.revalidate();
		                contentPane.repaint();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		});

		dateChooserEnd.addPropertyChangeListener(new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent evt) {
		        if ("date".equals(evt.getPropertyName())) {
		            try {
		                // Xóa biểu đồ cũ nếu có
		                Component[] components = contentPane.getComponents();
		                for (Component component : components) {
		                    if (component instanceof ChartPanel) {
		                        contentPane.remove(component);
		                    }
		                }

		                // Tạo biểu đồ mới và thêm vào contentPane
		                JFreeChart chart = createLineChart();
		                ChartPanel chartPanel = new ChartPanel(chart);
		                chartPanel.setBounds(424, 364, 750, 292);
		                contentPane.add(chartPanel);

		                // Cập nhật giao diện
		                contentPane.revalidate();
		                contentPane.repaint();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		});


	
		JFreeChart chart = createLineChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(453, 390, 750, 266);
		contentPane.add(chartPanel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(849, 54, 360, 126);
		panel_2.setBackground(Color.WHITE); // Màu trắng
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1_2 = new JLabel("New label");
		lblNewLabel_1_2.setBounds(10, 10, 93, 92);
		panel_2.add(lblNewLabel_1_2);
		ImageIcon iconãã123ê12 = new ImageIcon("src\\icon\\customerthongke.png");
		Image imageãã123ê12 = iconãã123ê12.getImage();
		Image scaledImageãã123ê12 = imageãã123ê12.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã123ê12 = new ImageIcon(scaledImageãã123ê12);
		lblNewLabel_1_2.setIcon(scaledIconãã123ê12);
		
		JLabel lblNewLabel_3_2 = new JLabel("Khách hàng Mua Hàng");
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_2.setBounds(134, 86, 216, 30);
		panel_2.add(lblNewLabel_3_2);
		
		label_soKhachHang = new JLabel("");
		label_soKhachHang.setOpaque(true);
		label_soKhachHang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_soKhachHang.setBackground(new Color(220, 220, 220));
		label_soKhachHang.setBounds(134, 40, 187, 36);
		panel_2.add(label_soKhachHang);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(21, 205, 339, 126);
		panel_3.setBackground(Color.WHITE); // Đặt màu nền là trắng
		contentPane.add(panel_3);
		panel_3.setLayout(null); // Thiết lập layout cho panel

		
		JLabel lblNewLabel_1_3 = new JLabel("");
		lblNewLabel_1_3.setBounds(10, 19, 109, 92);
		panel_3.add(lblNewLabel_1_3);
		ImageIcon iconãã123ê = new ImageIcon("src\\icon\\money_nhap.png");
		Image imageãã123ê = iconãã123ê.getImage();
		Image scaledImageãã123ê = imageãã123ê.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã123ê = new ImageIcon(scaledImageãã123ê);
		lblNewLabel_1_3.setIcon(scaledIconãã123ê);
		
		JLabel lblNewLabel_3_3 = new JLabel("Tiền nhập sách");
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_3.setBounds(129, 81, 210, 30);
		panel_3.add(lblNewLabel_3_3);
		
		label_tienNhap = new JLabel("");
		label_tienNhap.setOpaque(true);
		label_tienNhap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_tienNhap.setBackground(new Color(220, 220, 220));
		label_tienNhap.setBounds(129, 29, 189, 40);
		panel_3.add(label_tienNhap);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBackground(Color.WHITE); 
		panel_2_1.setBounds(424, 205, 360, 126);
		contentPane.add(panel_2_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("New label");
		lblNewLabel_1_2_1.setBounds(10, 10, 114, 106);
		panel_2_1.add(lblNewLabel_1_2_1);
		ImageIcon iconãã123ê1 = new ImageIcon("src\\icon\\money_ban.png");
		Image imageãã123ê1 = iconãã123ê1.getImage();
		Image scaledImageãã123ê1 = imageãã123ê1.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã123ê1 = new ImageIcon(scaledImageãã123ê1);
		lblNewLabel_1_2_1.setIcon(scaledIconãã123ê1);
		
		JLabel lblNewLabel_3_2_1 = new JLabel("Tiền bán sách");
		lblNewLabel_3_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_2_1.setBounds(134, 86, 216, 30);
		panel_2_1.add(lblNewLabel_3_2_1);
		
		label_tienBan = new JLabel("");
		label_tienBan.setOpaque(true);
		label_tienBan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_tienBan.setBackground(new Color(220, 220, 220));
		label_tienBan.setBounds(134, 33, 191, 37);
		panel_2_1.add(label_tienBan);
		
		JPanel panel_2_2 = new JPanel();
		panel_2_2.setLayout(null);
		panel_2_2.setBackground(Color.WHITE); 
		panel_2_2.setBounds(849, 202, 360, 126);
		contentPane.add(panel_2_2);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("New label");
		lblNewLabel_1_2_2.setBounds(10, 10, 114, 92);
		panel_2_2.add(lblNewLabel_1_2_2);
		ImageIcon iconãã123ê11 = new ImageIcon("src\\icon\\doanhthu.png");
		Image imageãã123ê11 = iconãã123ê11.getImage();
		Image scaledImageãã123ê11 = imageãã123ê11.getScaledInstance(110, 92, Image.SCALE_SMOOTH);
		ImageIcon scaledIconãã123ê11 = new ImageIcon(scaledImageãã123ê11);
		lblNewLabel_1_2_2.setIcon(scaledIconãã123ê11);
		
		JLabel lblNewLabel_3_2_2 = new JLabel("Tổng Doanh Thu");
		lblNewLabel_3_2_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_2_2.setBounds(134, 86, 216, 30);
		panel_2_2.add(lblNewLabel_3_2_2);
		
		label_doanhThu = new JLabel("");
		label_doanhThu.setOpaque(true);
		label_doanhThu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_doanhThu.setBackground(new Color(220, 220, 220));
		label_doanhThu.setBounds(135, 38, 188, 38);
		panel_2_2.add(label_doanhThu);
		
		JLabel lblNewLabel_2 = new JLabel("Doanh Thu Từ Ngày");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(282, 350, 177, 30);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Đến Ngày");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(674, 350, 88, 30);
		contentPane.add(lblNewLabel_2_1);

		// Cập nhật giao diện
		contentPane.revalidate();
		contentPane.repaint();
		
		updateBookCount();
		updateNhanVien();
		updateKhachHang();
		TienNhap()	;
		TienBan();
		double doanhthu = numberOfBooks - numberOfBooks1;
		label_doanhThu.setText(doanhthu+"");
		
		JButton btnNewButton = new JButton("Tổng Quát");
		btnNewButton.setBounds(21, 0, 109, 30);
		contentPane.add(btnNewButton);
		
		JButton btnTngQut = new JButton("Chi Tiết");
		btnTngQut.setBounds(178, 0, 125, 30);
		contentPane.add(btnTngQut);
		btnTngQut.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {      
		        HomePage.panelCustomer.removeAll();
		        // Tạo một instance của ThongKe_GUI2_Panel và thêm vào panelCustomer
				ThongKeSach2_JPanel thongKePanel = null;
				try {
					thongKePanel = new ThongKeSach2_JPanel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HomePage.panelCustomer.add(thongKePanel);
		        // Yêu cầu panel cập nhật lại giao diện
		        HomePage.panelCustomer.revalidate();
		        HomePage.panelCustomer.repaint();
		    }
		});
		
}
	private JFreeChart createLineChart() throws SQLException {
	    // Lấy ngày bắt đầu và ngày kết thúc từ JDateChooser
	    java.util.Date startDate = dateChooserStart.getDate();
	    java.util.Date endDate = dateChooserEnd.getDate();

	    // Tạo mô hình dữ liệu mới
	    DefaultTableModel model = new DefaultTableModel(new Object[]{"Ngày", "Tiền Nhập", "Tiền Bán", "Doanh Thu"}, 0);

	    // Tạo một lịch và thiết lập ngày bắt đầu
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(startDate);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	    // Lặp qua mỗi ngày từ ngày bắt đầu đến ngày kết thúc
	    while (!calendar.getTime().after(endDate)) {
	        java.util.Date currentDate = calendar.getTime();
	        HoaDon_DAO hoaDon = new HoaDon_DAO(connection);
	        double totalInput = PhieuNhap_DAO.getTotalSalesForDay(currentDate);
	        double totalSales = hoaDon.getTotalInputForDay(currentDate);
	        double totalRevenue = totalSales - totalInput;

	        // Thêm dữ liệu vào mô hình
	        model.addRow(new Object[]{dateFormat.format(currentDate), totalInput, totalSales, totalRevenue});

	        calendar.add(Calendar.DATE, 1);
	    }

	    // Đặt mô hình dữ liệu mới vào bảng
	    table.setModel(model);

	    // Tạo dataset từ dữ liệu trong mô hình
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    for (int i = 0; i < model.getRowCount(); i++) {
	        String date = (String) model.getValueAt(i, 0);
	        double totalInput = (double) model.getValueAt(i, 1);
	        double totalSales = (double) model.getValueAt(i, 2);
	        double totalRevenue = (double) model.getValueAt(i, 3);

	        dataset.addValue(totalInput, "Tiền Nhập", date);
	        dataset.addValue(totalSales, "Tiền Bán", date);
	        dataset.addValue(totalRevenue, "Doanh Thu", date);
	    }
	    double threshold = 3000; // Ngưỡng thay đổi
	    String trend = ""; // Biến lưu trữ xu hướng
	    String significantChange = ""; // Biến lưu trữ thông tin về sự thay đổi đáng chú ý
	    double initialRevenue = (double) model.getValueAt(0, 3); // Doanh thu ban đầu
	    double finalRevenue = (double) model.getValueAt(model.getRowCount() - 1, 3); // Doanh thu cuối cùng

	    // Tính sự thay đổi trong doanh thu
	    double revenueChange = finalRevenue - initialRevenue;
	    if (revenueChange == 0 ) {
	    	trend = "Không thay đổi";
	    }
	    // Xác định xu hướng
	    else if (revenueChange > 0) {
	        trend = "Tăng";
	        if (revenueChange > threshold) {
	            trend += " mạnh";
	        }
	    } else {
	        trend = "Giảm";
	        if (Math.abs(revenueChange) > threshold) {
	            trend += " mạnh";
	        }
	    }

	    // Xây dựng thông tin về sự thay đổi đáng chú ý
	    significantChange = "Từ ngày " + model.getValueAt(0, 0) + " đến ngày " + model.getValueAt(model.getRowCount() - 1, 0) 
	                        + ", ";
	    if (revenueChange > 0) {
	        significantChange += "doanh thu tăng ";
	    } else {
	        significantChange += "doanh thu giảm ";
	    }
	    significantChange += "đã đạt " + Math.abs(revenueChange) + " đơn vị";

	    // In ra kết quả
	    String startDate1 = (String) model.getValueAt(0, 0); // Ngày đầu tiên
	    String endDate1 = (String) model.getValueAt(model.getRowCount() - 1, 0); // Ngày cuối cùng

	    lblNewLabel_4.setText("Trend của doanh thu từ ngày " + startDate1 + " đến ngày " + endDate1 + ": " + trend);

	    lblNewLabel_4_1.setText("Sự thay đổi: " + significantChange);

	    return ChartFactory.createLineChart("Biểu Đồ", "Ngày", "Số Tiền", dataset);
	}

	 private void updateBookCount() throws SQLException {
	        // Gọi DAO để lấy số sách
	        Sach_DAO bookDAO = new Sach_DAO(connection);
	        int numberOfBooks = bookDAO.countTotalQuantityOfBooks();

	        // Hiển thị số sách trên label
	        label_soSach.setText(numberOfBooks+"");
	}
	 private void updateNhanVien() throws SQLException {	       
	        NhanVien_DAO bookDAO = new NhanVien_DAO(connection);
	        int numberOfBooks = bookDAO.countTotalEmployees();
	        label_soNhanVien.setText(numberOfBooks+"");
	}
	 private void updateKhachHang() throws SQLException {	       
		 	KhachHang_DAO bookDAO = new KhachHang_DAO(connection);
	        int numberOfBooks = bookDAO.countTotalKhachHang();
	        label_soKhachHang.setText(numberOfBooks+"");
	}
	 private void TienNhap() throws SQLException {	       
		 	PhieuNhap_DAO bookDAO = new PhieuNhap_DAO();
	        numberOfBooks1 = bookDAO.calculateTotalInput();
	        label_tienNhap.setText(numberOfBooks1+"");
	}
	 private void TienBan() throws SQLException {	       
		 	HoaDon_DAO bookDAO = new HoaDon_DAO(connection);
	        numberOfBooks = bookDAO.calculateTotalInput();
	        label_tienBan.setText(numberOfBooks+"");
	}
}
