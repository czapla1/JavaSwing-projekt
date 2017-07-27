package projektJavaSwing.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import projektJavaSwing.Entity.Employee;
import projektJavaSwing.Entity.Login;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class UpdateLog extends JFrame {

	private JPanel contentPane;
	private JTextField txtLogin;
	private JTextField txtPass;
	private JTextField txtPriviliges;
	private DBConnectionNew dbCon;
	private JTable table;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateLog frame = new UpdateLog();
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
	public UpdateLog() {
		initialize();
	}

	public void initialize() {
		try {
			dbCon = DBConnectionNew.makeConnection("root", "kwiat23");
			ResultSet rs = dbCon.getTable("logins");
			Vector<String> names = new Vector<>();

			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				names.add(rs.getMetaData().getColumnName(i));
			}

			Vector<Vector<String>> data = new Vector<>();
			while (rs.next()) {
				Vector<String> tem = new Vector<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
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
		setBounds(800, 200, 787, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWybierzIdPracownika = new JLabel("Logins data update : ");
		lblWybierzIdPracownika.setBounds(22, 13, 275, 22);
		contentPane.add(lblWybierzIdPracownika);

		JLabel lblId = new JLabel("Id no of login to update:");
		lblId.setBounds(20, 327, 200, 14);
		contentPane.add(lblId);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(193, 327, 69, 14);
		contentPane.add(lblLogin);

		JLabel lblPass = new JLabel("Password");
		lblPass.setBounds(303, 327, 80, 14);
		contentPane.add(lblPass);

		JLabel lblPriviliges = new JLabel("Priviliges");
		lblPriviliges.setBounds(404, 327, 61, 14);
		contentPane.add(lblPriviliges);

		txtLogin = new JTextField();
		txtLogin.setBounds(193, 356, 90, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		txtPass = new JTextField();
		txtPass.setBounds(303, 356, 90, 20);
		contentPane.add(txtPass);
		txtPass.setColumns(10);

		txtPriviliges = new JTextField();
		txtPriviliges.setBounds(403, 356, 90, 20);
		contentPane.add(txtPriviliges);
		txtPriviliges.setColumns(10);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(20, 354, 86, 22);
		contentPane.add(comboBox);
		refreshComboBox(comboBox, dbCon);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Vector<String> pierwszy = new Vector<>();
				Login log = new Login();
				log.setId(Integer.parseInt(comboBox.getSelectedItem().toString()));
				log.setLogin(txtLogin.getText());
				log.setPass(txtPass.getText());
				log.setPriviliges(txtPriviliges.getText());
				dbCon.updateLogin(log);
				Login loginRetrived = null;
				try {
					loginRetrived = dbCon.getLoginIdByLoginAndPassword(txtLogin.getText(), txtPass.getText());
				} catch (SQLException e1) {
					System.out.println("Cannot get results");
					e1.printStackTrace();
				}
				pierwszy.addElement(loginRetrived.getId() + "");
				pierwszy.addElement(loginRetrived.getLogin());
				pierwszy.addElement(loginRetrived.getPass());
				pierwszy.addElement(loginRetrived.getPriviliges());
				clearTable(dtm);
				populateTable(dtm, dbCon);
				refreshComboBox(comboBox, dbCon);
				clearTextField();
			}
		});
		btnUpdate.setBounds(534, 354, 97, 25);
		contentPane.add(btnUpdate);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 72, 747, 233);
		contentPane.add(scrollPane);
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginAdminEmp logAE = new LoginAdminEmp();
				logAE.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(668, 355, 89, 23);
		contentPane.add(btnPrevious);
	}

	public static void refreshComboBox(JComboBox<Integer> comboBox, DBConnectionNew con) {
		ArrayList<Integer> singleRecord;
		try {
			ResultSet rs = con.getTable("logins");
			singleRecord = new ArrayList<>();
			while (rs.next()) {
				singleRecord.add(rs.getInt(1));
			}
			comboBox.removeAllItems();
			for (Integer v : singleRecord) {
				comboBox.addItem(v);
			}
		} catch (SQLException e) {
			System.out.println("Cannot get results");
			e.printStackTrace();
		}

	}

	public static void clearTable(DefaultTableModel dtm) {
		for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
			dtm.removeRow(i);
		}
	}

	public static void populateTable(DefaultTableModel dtm, DBConnectionNew con) {
		try {
			ResultSet rs = con.getTable("logins");
			while (rs.next()) {
				Vector<String> singleRecord = new Vector<>();
				singleRecord.addElement(rs.getInt(1) + "");
				singleRecord.addElement(rs.getString(2));
				singleRecord.addElement(rs.getString(3));
				singleRecord.addElement(rs.getString(4));
				dtm.addRow(singleRecord);
			}
		} catch (SQLException e) {
			System.out.println("Cannot get results");
			e.printStackTrace();
		}
	}

	public void closeWindow() {
		WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}

	public void clearTextField() {
		txtLogin.setText("");
		txtPass.setText("");
		txtPriviliges.setText("");
	}
}
