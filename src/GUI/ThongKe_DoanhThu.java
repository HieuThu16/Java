package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThongKe_DoanhThu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JComboBox<String> comboBoxNam;

    // Database connection parameters (replace with your actual database connection details)
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quanlibansach";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public ThongKe_DoanhThu() {
        setTitle("Thống Kê Doanh Thu");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setBounds(175, 20, 704, 31);
        contentPane.add(panel);

        JLabel lblNewLabel = new JLabel("Chọn Năm:");
        panel.add(lblNewLabel);

        comboBoxNam = new JComboBox<String>();
        comboBoxNam.addItem("2022");
        comboBoxNam.addItem("2023");
        comboBoxNam.addItem("2024");
        panel.add(comboBoxNam);

        JButton btnThongKe = new JButton("Thống Kê");
        btnThongKe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        panel.add(btnThongKe);
        
        

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(175, 70, 715, 481);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
    }

    private void updateTable() {
        String selectedNam = comboBoxNam.getSelectedItem().toString();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Quý");
        model.addColumn("Tổng Thu");
        model.addColumn("Tổng Chi");
        model.addColumn("Tổng Lợi Nhuận");

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            for (int i = 1; i <= 4; i++) {
                String query = "SELECT SUM(tongtien) AS tongthu, " +
                        "(SELECT SUM(tongtien) FROM phieunhap WHERE YEAR(ngaylap) = '" + selectedNam + "' AND QUARTER(ngaylap) = " + i + ") AS tongchi, " +
                        "SUM(tongtien - (SELECT SUM(tongtien) FROM phieunhap WHERE YEAR(ngaylap) = '" + selectedNam + "' AND QUARTER(ngaylap) = " + i + ")) AS tongloinhuan " +
                        "FROM hoadon WHERE YEAR(ngaylap) = '" + selectedNam + "' AND QUARTER(ngaylap) = " + i;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    double tongThu = resultSet.getDouble("tongthu");
                    double tongChi = resultSet.getDouble("tongchi");
                    double tongLoiNhuan = resultSet.getDouble("tongloinhuan");
                    model.addRow(new Object[]{i, tongThu, tongChi, tongLoiNhuan});
                } else {
                    model.addRow(new Object[]{i, 0, 0, 0});
                }
            }
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThongKe_DoanhThu frame = new ThongKe_DoanhThu();
            frame.setVisible(true);
        });
    }
}
