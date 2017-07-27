package projektJavaSwing.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class ShowAllEmp extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DBConnectionNew dbCon;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowAllEmp frame = new ShowAllEmp();
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
	public ShowAllEmp() {
		initialize();
	}

	public void initialize() {

		try {

			dbCon = DBConnectionNew.makeConnection("root", "kwiat23");

			ResultSet rs = dbCon.getTable("employee");
			Vector<String> names = new Vector<>();

			for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
				names.add(rs.getMetaData().getColumnName(i));
			}

			Vector<Vector<String>> data = new Vector<>();
			while (rs.next()) {
				Vector<String> tem = new Vector<>();
				for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
					tem.add(rs.getString(i));
				}
				data.add(tem);
			}

			dtm = new DefaultTableModel(data, names);
		} catch (MySuperExc e) {

			e.coSieStalo();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 400, 598, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		getContentPane().setLayout(null);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(59, 98, 487, 237);
		getContentPane().add(scrollPane);

		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Employees data:");
		lblNewLabel.setBounds(61, 35, 322, 30);
		getContentPane().add(lblNewLabel);

		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginAdminEmp logAE = new LoginAdminEmp();
				logAE.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(445, 368, 89, 23);
		contentPane.add(btnPrevious);
	}

	public void closeWindow() {
		WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}

}
