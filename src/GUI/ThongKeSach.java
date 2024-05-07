package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import DAO.ChiTietHoaDon_DAO;
import DAO.ChiTietPhieuNhap_DAO;
import DAO.NhaCungCap_DAO;
import DAO.NhanVien_DAO;
import DAO.Sach_DAO;
import DAO.TacGia_DAO;
import DAO.TheLoai_DAO;
import Database.ConnectionManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ThongKeSach extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection connection = ConnectionManager.openConnection(); 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThongKeSach frame = new ThongKeSach();
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
	public ThongKeSach() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1353, 777);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Thống Kê Chi Tiết");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(505, 10, 289, 54);
		contentPane.add(lblNewLabel);
		
		JFreeChart chart = createBarChart();
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 66, 597, 314);
		contentPane.add(panel);
		panel.setLayout(null);
		 Color backgroundColor = new Color(255, 204, 153); // Màu cam nhạt
	        panel.setBackground(backgroundColor);
		
		ChartPanel chartPanel_1 = new ChartPanel(chart);
		chartPanel_1.setBounds(64, 58, 451, 215);
		panel.add(chartPanel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Thống Kê Chi Tiết Sách");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 7, 403, 28);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		//Color backgroundColor1 = new Color(255, 204, 153); // Màu cam nhạt
		panel_1.setBackground(new Color(77, 179, 176));
		panel_1.setBounds(675, 66, 607, 314);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		 Map<String, Double> salaryData = null;
	        try {
	            salaryData = retrieveSalaryData(connection);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        JFreeChart pieChart = createPieChart(salaryData);
			ChartPanel chartPanel = new ChartPanel(pieChart);
	
		    chartPanel.setPreferredSize(new Dimension(500, 300)); // Thiết lập kích thước của chartPanel
		    chartPanel.setBounds(97, 48, 500, 259); // Thiết lập vị trí và kích thước của chartPanel trong panel_1

		    // Thêm chartPanel vào panel_1
		    panel_1.add(chartPanel);
		    
		    JLabel lblNewLabel_1_1 = new JLabel("Thống Kê Chi Tiết Nhân Viên");
		    lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_1_1.setBounds(10, 10, 403, 28);
		    panel_1.add(lblNewLabel_1_1);
		    
		    
			JButton btnNewButton = new JButton("Tổng Quát");
			btnNewButton.setBounds(21, 0, 109, 30);
			contentPane.add(btnNewButton);
			btnNewButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {      
			        HomePage.panelCustomer.removeAll();
			        // Tạo một instance của ThongKe_GUI2_Panel và thêm vào panelCustomer
					ThongKe_GUI2_JPanel thongKePanel = null;
					try {
						thongKePanel = new ThongKe_GUI2_JPanel();
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
		    
		    
		    
		    
		    JButton btnNewButton_1 = new JButton("Chi Tiết");
		    btnNewButton_1.setBounds(178, 0, 125, 30);
		    contentPane.add(btnNewButton_1);
		    
		    
		    Map<String, Double> tongchikhachahng = null;
	        try {
	        	tongchikhachahng = TongChiKhachHang(connection);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        
	        JPanel panel_khachHang = new JPanel();
	        panel_khachHang.setBackground(new Color(128, 128, 0));
		    panel_khachHang.setLayout(null);
		    panel_khachHang.setBounds(0, 412, 607, 318);
		    contentPane.add(panel_khachHang);
		    
	        JFreeChart pieChart1 = createPieChartKhachHang(tongchikhachahng);
			ChartPanel chartPanel_khachHang = new ChartPanel(pieChart1);
		    chartPanel_khachHang.setPreferredSize(new Dimension(500, 300));
		    chartPanel_khachHang.setBounds(97, 48, 500, 259);
		    panel_khachHang.add(chartPanel_khachHang);
		    
		   
		    
		
		    chartPanel_khachHang.setPreferredSize(new Dimension(500, 300));
		    chartPanel_khachHang.setBounds(97, 48, 500, 259);
		    panel_khachHang.add(chartPanel_khachHang);
		    
		    JLabel lblNewLabel_1_1_1 = new JLabel("Thống Kê Chi Tiết Khách Hàng");
		    lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_1_1_1.setBounds(10, 10, 403, 28);
		    panel_khachHang.add(lblNewLabel_1_1_1);
		    
		    JPanel panel_2 = new JPanel();
		    panel_2.setBounds(675, 406, 607, 324);
		    contentPane.add(panel_2);
		    panel_2.setLayout(null);
		    Color backgroundColor11 = new Color(255, 204, 153); // Màu cam nhạt
		    panel_2.setBackground(backgroundColor11);
		    
		    
		    JLabel lblNewLabel_1_1_1_1 = new JLabel("Thống Kê Một Số Thông Tin Của Cửa Hàng Sách");
		    lblNewLabel_1_1_1_1.setBounds(10, 20, 452, 28);
		    lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		    panel_2.add(lblNewLabel_1_1_1_1);
		    
		    JLabel lblNewLabel_2 = new JLabel("Mã Sách Bán Chạy Nhất");
		    lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_2.setBounds(62, 75, 198, 34);
		    panel_2.add(lblNewLabel_2);
		    
		    JLabel lblNewLabel_3 = new JLabel("");
		    lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		    lblNewLabel_3.setBounds(267, 75, 57, 39);
		    panel_2.add(lblNewLabel_3);
		    lblNewLabel_3.setOpaque(true);
		    lblNewLabel_3.setBackground(new Color(220, 220, 220)); 
		    lblNewLabel_3.setText(Sach_DAO.getBestSellingBookId());
		  
		    
		    JLabel lblNewLabel_2_1 = new JLabel("Mã Nhân Viên Bán Được Nhiều Doanh Thu Nhất:");
		    lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_2_1.setBounds(62, 129, 426, 34);
		    panel_2.add(lblNewLabel_2_1);
		    
		    JLabel lblNewLabel_2_1_1 = new JLabel("Số Lượng Nhà Cung Cấp");
		    lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_2_1_1.setBounds(62, 178, 400, 34);
		    panel_2.add(lblNewLabel_2_1_1);
		    
		    JLabel lblNewLabel_3_1 = new JLabel("");
		    lblNewLabel_3_1.setOpaque(true);
		    lblNewLabel_3_1.setBackground(new Color(220, 220, 220));
		    lblNewLabel_3_1.setBounds(482, 124, 96, 39);
		    panel_2.add(lblNewLabel_3_1);
		   
		    lblNewLabel_3_1.setText(NhanVien_DAO.getMaxTotalSalesByEmployee());
		    
		    
		    JLabel label_soNCC = new JLabel("");
		    label_soNCC.setOpaque(true);
		    label_soNCC.setBackground(new Color(220, 220, 220));
		    label_soNCC.setBounds(482, 173, 96, 39);
		    panel_2.add(label_soNCC);
		    label_soNCC.setText(NhaCungCap_DAO.countSuppliers()+"");
		    
		    JLabel lblNewLabel_3_3 = new JLabel("0001");
		    lblNewLabel_3_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		    lblNewLabel_3_3.setOpaque(true);
		    lblNewLabel_3_3.setBackground(new Color(220, 220, 220));
		    lblNewLabel_3_3.setBounds(482, 75, 96, 39);
		    panel_2.add(lblNewLabel_3_3);
		    lblNewLabel_3_3.setText(Sach_DAO.getBestSellingQuantity()+"");
		    
		    JLabel lblNewLabel_2_2 = new JLabel("Số Lượng Bán");
		    lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_2_2.setBounds(334, 75, 138, 34);
		    panel_2.add(lblNewLabel_2_2);
		    
		    JLabel lblNewLabel_2_1_1_1 = new JLabel("Số Lượng Tác Giả Sách");
		    lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_2_1_1_1.setBounds(62, 233, 400, 34);
		    panel_2.add(lblNewLabel_2_1_1_1);
		    
		    JLabel label_soTacGia = new JLabel("");
		    label_soTacGia.setOpaque(true);
		    label_soTacGia.setBackground(new Color(220, 220, 220));
		    label_soTacGia.setBounds(482, 228, 96, 39);
		    panel_2.add(label_soTacGia);
		    label_soTacGia.setText(TacGia_DAO.demtacgia()+"");
		    
		    JLabel lblNewLabel_2_1_1_2 = new JLabel("Số Lượng Thể Loại Sách");
		    lblNewLabel_2_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		    lblNewLabel_2_1_1_2.setBounds(62, 280, 400, 34);
		    panel_2.add(lblNewLabel_2_1_1_2);
		    
		    JLabel label_soTheLoai = new JLabel("");
		    label_soTheLoai.setOpaque(true);
		    label_soTheLoai.setBackground(new Color(220, 220, 220));
		    label_soTheLoai.setBounds(482, 275, 96, 39);
		    panel_2.add(label_soTheLoai);
		    label_soTheLoai.setText(TheLoai_DAO.demtheloai()+"");
		}
	
	private Map<String, Double> TongChiKhachHang(Connection connection) throws SQLException {
	    Map<String, Double> salaryData = new HashMap<>();
	    String sql = "SELECT makhachhang, tongchi FROM khachhang";
	    try (PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {
	        while (resultSet.next()) {
	            String employeeId = resultSet.getString("makhachhang");
	            double salary = resultSet.getDouble("tongchi");
	            salaryData.put(employeeId, salary);
	        }
	    }
	    return salaryData;
	}
	   public static JFreeChart createPieChartKhachHang(Map<String, Double> data) {
	        // Tạo một dataset cho biểu đồ tròn
	        DefaultPieDataset dataset = new DefaultPieDataset();

	        // Thêm dữ liệu lương và mã nhân viên từ map vào dataset
	        for (Map.Entry<String, Double> entry : data.entrySet()) {
	            String employeeId = entry.getKey();
	            double salary = entry.getValue();
	            String label = String.format("%s - %.2f", employeeId, salary); // Kết hợp mã nhân viên và lương
	            dataset.setValue(label, salary);
	        }

	        // Tạo biểu đồ tròn từ dataset và trả về
	        JFreeChart chart = ChartFactory.createPieChart(
	                "Biểu Đồ Tổng Chi Khách Hàng",  // Tiêu đề biểu đồ
	                dataset,  // Dataset chứa dữ liệu
	                true,     // Có hiển thị hướng giải thích không
	                true,     // Có hiển thị nhãn dữ liệu không
	                false);   // Có hiển thị URL không (chỉ dành cho biểu đồ web)

	        return chart;
	    }
	
	
	
	
	

	private Map<String, Double> retrieveSalaryData(Connection connection) throws SQLException {
	    Map<String, Double> salaryData = new HashMap<>();
	    String sql = "SELECT manhanvien, luong FROM nhanvien";
	    try (PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {
	        while (resultSet.next()) {
	            String employeeId = resultSet.getString("manhanvien");
	            double salary = resultSet.getDouble("luong");
	            salaryData.put(employeeId, salary);
	        }
	    }
	    return salaryData;
	}
	
	
	
	   public static JFreeChart createPieChart(Map<String, Double> data) {
	        // Tạo một dataset cho biểu đồ tròn
	        DefaultPieDataset dataset = new DefaultPieDataset();

	        // Thêm dữ liệu lương và mã nhân viên từ map vào dataset
	        for (Map.Entry<String, Double> entry : data.entrySet()) {
	            String employeeId = entry.getKey();
	            double salary = entry.getValue();
	            String label = String.format("%s - %.2f", employeeId, salary); // Kết hợp mã nhân viên và lương
	            dataset.setValue(label, salary);
	        }

	        // Tạo biểu đồ tròn từ dataset và trả về
	        JFreeChart chart = ChartFactory.createPieChart(
	                "Biểu Đồ Lương Nhân Viên",  // Tiêu đề biểu đồ
	                dataset,  // Dataset chứa dữ liệu
	                true,     // Có hiển thị hướng giải thích không
	                true,     // Có hiển thị nhãn dữ liệu không
	                false);   // Có hiển thị URL không (chỉ dành cho biểu đồ web)

	        return chart;
	    }

	private JFreeChart createBarChart() throws SQLException {
	    // Tạo mô hình dữ liệu mới
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    // Lấy tổng số lượng sách đã bán và tổng số lượng sách trong kho cho toàn bộ thời gian
	    int totalSoldQuantity = ChiTietHoaDon_DAO.getTotalSoldQuantity();
	    int totalAvailableQuantity = ChiTietPhieuNhap_DAO.getTotalSoldQuantity();
	    dataset.addValue(totalSoldQuantity, "Số lượng sách đã bán", "Tổng");
	    dataset.addValue(totalAvailableQuantity, "Số lượng sách đã nhập", "Tổng");

	    // Tạo biểu đồ cột từ dataset
	    JFreeChart chart = ChartFactory.createBarChart("Biểu Đồ Số Lượng Sách Đã Bán/Đã Nhập", "Thời Gian", "Số Lượng Sách", dataset, PlotOrientation.VERTICAL, true, true, false);

	    // Thiết lập màu sắc cho các cột
	    CategoryPlot plot = chart.getCategoryPlot();
	    BarRenderer renderer = (BarRenderer) plot.getRenderer();
	    renderer.setSeriesPaint(0, new Color(0, 0, 255).brighter()); // Màu xanh dương nhạt cho cột "Số lượng sách đã bán"
// Màu xanh nhạt cho cột "Số lượng sách đã bán"
	    renderer.setSeriesPaint(1, new Color(255, 102, 102)); // Màu đỏ nhạt cho cột "Số lượng sách trong kho"

	    return chart;
	}
}
