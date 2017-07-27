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

/*import projektJavaSwing.DBConnectionNew;
import projektJavaSwing.MySuperExc;*/

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class LoginUser extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel dtm;
	DBConnectionNew dbCon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUser frame = new LoginUser();
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
	public LoginUser() {
		initilize();
	}

	public void initilize() {

		try {

			dbCon = DBConnectionNew.makeConnection("root", "kwiat23");

			ResultSet rs1 = dbCon.getTable("logins");
			int logColQty = rs1.getMetaData().getColumnCount();

			ResultSet rs = dbCon.getUser(Log_in.textLogin.getText());
			Vector<String> names = new Vector<>();

			for (int i = 1; i < rs.getMetaData().getColumnCount() - logColQty; i++) {
				names.add(rs.getMetaData().getColumnName(i));
			}

			Vector<Vector<String>> data = new Vector<>();
			while (rs.next()) {
				Vector<String> tem = new Vector<>();
				for (int i = 1; i < rs.getMetaData().getColumnCount() - logColQty; i++) {
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
		setBounds(800, 300, 668, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWitajWPanelu = new JLabel("Welcome to User panel " + Log_in.textLogin.getText() + "!");
		lblWitajWPanelu.setBounds(53, 23, 307, 45);
		contentPane.add(lblWitajWPanelu);

		JLabel lblTwojeDane = new JLabel("Your data:");
		lblTwojeDane.setBounds(74, 80, 201, 16);
		contentPane.add(lblTwojeDane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(74, 109, 471, 54);
		contentPane.add(scrollPane);

		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		JButton btnPrevious = new JButton("Log out");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Log_in log = new Log_in();
				log.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(511, 187, 105, 40);
		contentPane.add(btnPrevious);
	}

	public void closeWindow() {
		WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}
}
