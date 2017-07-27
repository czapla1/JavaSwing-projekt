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

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class UpdateEmp extends JFrame {

	private JPanel contentPane;
	private JTextField txtImie;
	private JTextField txtNazwisko;
	private JTextField txtPesel;
	private JTextField txtMiasto;
	private JTextField txtPensja;
	private DBConnectionNew dbCon;
	private JTable table;
	private DefaultTableModel dtm;
	private JTextField txtStaz;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateEmp frame = new UpdateEmp();
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
	public UpdateEmp() {
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
		setBounds(800, 200, 787, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWybierzIdPracownika = new JLabel("Employee data update : ");
		lblWybierzIdPracownika.setBounds(22, 13, 275, 22);
		contentPane.add(lblWybierzIdPracownika);

		JLabel lblId = new JLabel("Id no of employee to update:");
		lblId.setBounds(15, 428, 200, 14);
		contentPane.add(lblId);

		JLabel lblImie = new JLabel("Firstname");
		lblImie.setBounds(20, 460, 69, 14);
		contentPane.add(lblImie);

		JLabel lblNazwisko = new JLabel("Lastname");
		lblNazwisko.setBounds(125, 460, 80, 14);
		contentPane.add(lblNazwisko);

		JLabel lblPesel = new JLabel("PESEL no");
		lblPesel.setBounds(225, 460, 61, 14);
		contentPane.add(lblPesel);

		JLabel lblMiasto = new JLabel("City");
		lblMiasto.setBounds(335, 461, 46, 14);
		contentPane.add(lblMiasto);

		JLabel lblPensja = new JLabel("Salary");
		lblPensja.setBounds(445, 460, 46, 14);
		contentPane.add(lblPensja);

		txtImie = new JTextField();
		txtImie.setBounds(15, 480, 90, 20);
		contentPane.add(txtImie);
		txtImie.setColumns(10);

		txtNazwisko = new JTextField();
		txtNazwisko.setBounds(125, 480, 90, 20);
		contentPane.add(txtNazwisko);
		txtNazwisko.setColumns(10);

		txtPesel = new JTextField();
		txtPesel.setBounds(225, 480, 90, 20);
		contentPane.add(txtPesel);
		txtPesel.setColumns(10);

		txtMiasto = new JTextField();
		txtMiasto.setBounds(335, 480, 90, 20);
		contentPane.add(txtMiasto);
		txtMiasto.setColumns(10);

		txtPensja = new JTextField();
		txtPensja.setBounds(445, 480, 90, 20);
		contentPane.add(txtPensja);
		txtPensja.setColumns(10);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(224, 424, 86, 22);
		contentPane.add(comboBox);
		refreshComboBox(comboBox, dbCon);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector<String> pierwszy = new Vector<>();

				Employee employee = new Employee();
				employee.setId(Integer.parseInt(comboBox.getSelectedItem().toString()));
				employee.setFirstName(txtImie.getText());
				employee.setLastName(txtNazwisko.getText());
				employee.setPesel(txtPesel.getText());
				employee.setCity(txtMiasto.getText());
				employee.setSalary(Integer.parseInt(txtPensja.getText()));
				employee.setSeniority(Integer.parseInt(txtStaz.getText()));
				dbCon.updateEmployee(employee);

				Employee employeeRetrived = null;
				try {
					employeeRetrived = dbCon.getEmployeeIdByNameAndSurname(txtImie.getText(), txtNazwisko.getText());
				} catch (SQLException e1) {
					System.out.println("Cannot get results");
					e1.printStackTrace();
				}

				pierwszy.addElement(employeeRetrived.getId() + "");
				pierwszy.addElement(employeeRetrived.getFirstName());
				pierwszy.addElement(employeeRetrived.getLastName());
				pierwszy.addElement(employeeRetrived.getPesel());
				pierwszy.addElement(employeeRetrived.getCity());
				pierwszy.addElement(employeeRetrived.getSalary() + "");
				pierwszy.addElement(employeeRetrived.getSeniority() + "");
				clearTable(dtm);
				populateTable(dtm, dbCon);
				refreshComboBox(comboBox, dbCon);
				clearTextField();
			}
		});
		btnUpdate.setBounds(660, 478, 97, 25);
		contentPane.add(btnUpdate);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 72, 747, 326);
		contentPane.add(scrollPane);

		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		JLabel lblStaz = new JLabel("Staz");
		lblStaz.setBounds(555, 459, 56, 16);
		contentPane.add(lblStaz);

		txtStaz = new JTextField();
		txtStaz.setBounds(555, 480, 90, 22);
		contentPane.add(txtStaz);
		txtStaz.setColumns(10);

		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginAdminEmp logAE = new LoginAdminEmp();
				logAE.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(660, 516, 99, 23);
		contentPane.add(btnPrevious);
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
