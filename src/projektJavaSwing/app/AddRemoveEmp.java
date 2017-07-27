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

public class AddRemoveEmp extends JFrame {

	private JPanel contentPane;
	JComboBox comboBox;
	private DBConnectionNew dbCon;
	private DefaultTableModel dtm;
	private JTable table;
	private JLabel lblImie;
	private JLabel lblNazwisko;
	private JLabel lblPesel;
	private JLabel lblMiejsceZamieszkania;
	private JLabel lblPensja;
	private JLabel lblSta;
	private JButton btnDodaj;
	private JButton btnUsu;
	private JLabel lblZaznaczUytkownikaKtrego;
	private JTextField txtImie;
	private JTextField txtNazwisko;
	private JTextField txtPesel;
	private JTextField txtMiasto;
	private JTextField txtPensja;
	private JTextField txtStaz;
	private JButton btnPrevious;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRemoveEmp frame = new AddRemoveEmp();
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
	public AddRemoveEmp() {

		initialize();
	}

	public void initialize() {
		try {

			dbCon = DBConnectionNew.makeConnection("root", "kwiat23");
			ResultSet rs = dbCon.getTable("employee");
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
		setBounds(800, 200, 680, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 25, 615, 310);
		contentPane.add(scrollPane);

		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		lblImie = new JLabel("Firstname");
		lblImie.setBounds(22, 348, 86, 14);
		contentPane.add(lblImie);

		lblNazwisko = new JLabel("Lastname");
		lblNazwisko.setBounds(118, 348, 84, 14);
		contentPane.add(lblNazwisko);

		lblPesel = new JLabel("PESEL no");
		lblPesel.setBounds(216, 348, 78, 14);
		contentPane.add(lblPesel);

		lblMiejsceZamieszkania = new JLabel("City");
		lblMiejsceZamieszkania.setBounds(314, 348, 123, 14);
		contentPane.add(lblMiejsceZamieszkania);

		lblPensja = new JLabel("Salary");
		lblPensja.setBounds(449, 348, 46, 14);
		contentPane.add(lblPensja);

		lblSta = new JLabel("Seniority");
		lblSta.setBounds(551, 348, 99, 14);
		contentPane.add(lblSta);

		btnDodaj = new JButton("Add");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = "";

				Employee employee = new Employee();
				employee.setFirstName(txtImie.getText());
				employee.setLastName(txtNazwisko.getText());
				employee.setPesel(txtPesel.getText());
				employee.setCity(txtMiasto.getText());
				employee.setSalary(Integer.parseInt(txtPensja.getText()));
				employee.setSeniority(Integer.parseInt(txtStaz.getText()));
				dbCon.addEmployee(employee);

				Employee employeeRetrived = null;
				try {
					employeeRetrived = dbCon.getEmployeeIdByNameAndSurname(txtImie.getText(), txtNazwisko.getText());
				} catch (SQLException e1) {
					System.out.println("Not connected");
					e1.printStackTrace();
				}
				Vector<String> pierwszy = new Vector<>();
				pierwszy.addElement(employeeRetrived.getId() + "");
				pierwszy.addElement(employeeRetrived.getFirstName());
				pierwszy.addElement(employeeRetrived.getLastName());
				pierwszy.addElement(employeeRetrived.getPesel());
				pierwszy.addElement(employeeRetrived.getCity());
				pierwszy.addElement(employeeRetrived.getSalary() + "");
				pierwszy.addElement(employeeRetrived.getSeniority() + "");
				dtm.addRow(pierwszy);
				refreshComboBox(comboBox, dbCon);
				clearTextField();
			}
		});
		btnDodaj.setBounds(22, 408, 89, 23);
		contentPane.add(btnDodaj);

		btnUsu = new JButton("Delete");
		btnUsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int markedId = (int) comboBox.getItemAt(comboBox.getSelectedIndex());
				System.out.println(dtm.getRowCount());
				dbCon.removeEmployeeById(markedId);
				clearTable(dtm);
				populateTable(dtm, dbCon);

				refreshComboBox(comboBox, dbCon);
			}
		});
		btnUsu.setBounds(314, 472, 89, 23);
		contentPane.add(btnUsu);

		lblZaznaczUytkownikaKtrego = new JLabel("Id no of employee to delete:");
		lblZaznaczUytkownikaKtrego.setBounds(22, 476, 280, 14);
		contentPane.add(lblZaznaczUytkownikaKtrego);

		txtImie = new JTextField();
		txtImie.setBounds(22, 375, 86, 20);
		contentPane.add(txtImie);
		txtImie.setColumns(10);

		txtNazwisko = new JTextField();
		txtNazwisko.setBounds(118, 375, 86, 20);
		contentPane.add(txtNazwisko);
		txtNazwisko.setColumns(10);

		txtPesel = new JTextField();
		txtPesel.setBounds(216, 375, 86, 20);
		contentPane.add(txtPesel);
		txtPesel.setColumns(10);

		txtMiasto = new JTextField();
		txtMiasto.setBounds(314, 375, 123, 20);
		contentPane.add(txtMiasto);
		txtMiasto.setColumns(10);

		txtPensja = new JTextField();
		txtPensja.setBounds(449, 375, 86, 20);
		contentPane.add(txtPensja);
		txtPensja.setColumns(10);

		txtStaz = new JTextField();
		txtStaz.setBounds(551, 375, 86, 20);
		contentPane.add(txtStaz);
		txtStaz.setColumns(10);

		comboBox = new JComboBox();
		comboBox.setBounds(200, 472, 78, 22);
		contentPane.add(comboBox);
		refreshComboBox(comboBox, dbCon);

		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginAdminEmp lae = new LoginAdminEmp();
				lae.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(540, 471, 97, 25);
		contentPane.add(btnPrevious);
	}

	public static void clearTable(DefaultTableModel dtm) {

		for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
			dtm.removeRow(i);
		}
	}

	public static void populateTable(DefaultTableModel dtm, DBConnectionNew con) {
		try {
			ResultSet rs = con.getTable("employee");
			while (rs.next()) {
				Vector<String> singleRecord = new Vector<>();
				singleRecord.addElement(rs.getInt(1) + "");
				singleRecord.addElement(rs.getString(2));
				singleRecord.addElement(rs.getString(3));
				singleRecord.addElement(rs.getString(4));
				singleRecord.addElement(rs.getString(5));
				singleRecord.addElement(rs.getString(6));
				singleRecord.addElement(rs.getString(7));

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
			ResultSet rs = con.getTable("employee");
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
		txtImie.setText("");
		txtNazwisko.setText("");
		txtPesel.setText("");
		txtMiasto.setText("");
		txtPensja.setText("");
		txtStaz.setText("");
	}
}
