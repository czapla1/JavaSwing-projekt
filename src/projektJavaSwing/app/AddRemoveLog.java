package projektJavaSwing.app;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.ComboBox;
import projektJavaSwing.Entity.Employee;
import projektJavaSwing.Entity.Login;

public class AddRemoveLog extends JFrame {

	private JPanel contentPane;
	JComboBox comboBox;
	private DBConnectionNew dbCon;
	private DefaultTableModel dtm;
	private JTable table;
	private JLabel lblLogin;
	private JLabel lblPassword;
	private JLabel lblPriviliges;
	private JButton btnDodaj;
	private JButton btnUsu;
	private JLabel lblIdToDel;
	private JTextField txtLogin;
	private JTextField txtPassword;
	private JTextField txtPriviliges;
	private JButton btnPrevious;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRemoveLog frame = new AddRemoveLog();
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
	public AddRemoveLog() {
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
		setBounds(800, 200, 640, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 25, 565, 260);
		contentPane.add(scrollPane);

		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		lblLogin = new JLabel("Login");
		lblLogin.setBounds(134, 312, 86, 14);
		contentPane.add(lblLogin);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(250, 312, 84, 14);
		contentPane.add(lblPassword);

		lblPriviliges = new JLabel("Priviliges");
		lblPriviliges.setBounds(361, 312, 78, 14);
		contentPane.add(lblPriviliges);

		btnDodaj = new JButton("Add");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setId((Integer.parseInt(txtId.getText())));
				login.setLogin(txtLogin.getText());
				login.setPass(txtPassword.getText());
				login.setPriviliges(txtPriviliges.getText());
				dbCon.addLogin(login);

				Login loginRetrived = null;
				try {
					loginRetrived = dbCon.getLoginIdByLoginAndPassword(txtLogin.getText(), txtPassword.getText());
				} catch (SQLException e1) {
					System.out.println("Not connected");
					e1.printStackTrace();
				}
				Vector<String> pierwszy = new Vector<>();
				pierwszy.addElement(loginRetrived.getId() + "");
				pierwszy.addElement(loginRetrived.getLogin());
				pierwszy.addElement(loginRetrived.getPass());
				pierwszy.addElement(loginRetrived.getPriviliges());
				dtm.addRow(pierwszy);
				refreshComboBox(comboBox, dbCon);
				clearTextField();
			}
		});
		btnDodaj.setBounds(498, 336, 89, 23);
		contentPane.add(btnDodaj);

		btnUsu = new JButton("Delete");
		btnUsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int markedId = (int) comboBox.getItemAt(comboBox.getSelectedIndex());
				System.out.println(dtm.getRowCount());
				dbCon.removeLoginById(markedId);
				clearTable(dtm);
				populateTable(dtm, dbCon);

				refreshComboBox(comboBox, dbCon);
			}
		});
		btnUsu.setBounds(250, 409, 89, 23);
		contentPane.add(btnUsu);

		lblIdToDel = new JLabel("Id no to delete:");
		lblIdToDel.setBounds(27, 413, 280, 14);
		contentPane.add(lblIdToDel);

		txtLogin = new JTextField();
		txtLogin.setBounds(134, 337, 86, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setBounds(250, 337, 86, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);

		txtPriviliges = new JTextField();
		txtPriviliges.setBounds(361, 337, 86, 20);
		contentPane.add(txtPriviliges);
		txtPriviliges.setColumns(10);

		comboBox = new JComboBox();
		comboBox.setBounds(142, 409, 78, 22);
		contentPane.add(comboBox);
		refreshComboBox(comboBox, dbCon);

		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginAdminLog lal = new LoginAdminLog();
				lal.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(498, 408, 97, 25);
		contentPane.add(btnPrevious);

		txtId = new JTextField();
		txtId.setBounds(22, 337, 86, 22);
		contentPane.add(txtId);
		txtId.setColumns(10);

		JLabel lblId = new JLabel("Id");
		lblId.setBounds(22, 312, 56, 16);
		contentPane.add(lblId);
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

	public void closeWindow() {
		WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}

	public void clearTextField() {
		txtLogin.setText("");
		txtPassword.setText("");
		txtPriviliges.setText("");
	}
}
