package GUI;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class TrangChu extends JFrame {
	
	// nênne

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrangChu frame = new TrangChu();
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
	public TrangChu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1229, 731);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		 ;
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 10, 1195, 96);
		  String imagePath = "src//image//logo.jpg";
		  lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
	        lblNewLabel.setVerticalAlignment(JLabel.CENTER);
	        // Tạo một ImageIcon từ tập tin ảnh
	        ImageIcon icon = new ImageIcon(imagePath);
	        // Đặt ImageIcon vào JLabel
	        lblNewLabel.setIcon(icon);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 127, 1195, 557);
		contentPane.add(panel);
	}
}
