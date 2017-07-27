package projektJavaSwing.app;

import java.awt.*;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class TableChoice extends JFrame {

	private JPanel contentPane;
	private Vector<String> tmp1;

	private DBConnectionNew dbCon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableChoice frame = new TableChoice();
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
	public TableChoice() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			dbCon = DBConnectionNew.makeConnection("root", "kwiat23");
			ResultSet rs = dbCon.getTable("employee");
			ResultSet rsDB = dbCon.getCon().getMetaData().getTables(null, null, "%", null);
			tmp1 = new Vector<>();

			while (rsDB.next()) {
				tmp1.add((rsDB.getString(3)));
			}

		} catch (MySuperExc e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setBounds(800, 400, 431, 331);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblKtrTabelChcesz = new JLabel("Which table do you want to choose?");
		lblKtrTabelChcesz.setBounds(55, 62, 235, 19);
		contentPane.add(lblKtrTabelChcesz);

		JComboBox comboBox = new JComboBox(tmp1);
		comboBox.setToolTipText("Choose table and press the button");
		comboBox.setBounds(55, 92, 149, 29);
		contentPane.add(comboBox);

		JButton btnNewButton = new JButton("Choose");
		btnNewButton.setBounds(95, 150, 109, 42);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBox.getItemAt(comboBox.getSelectedIndex()).equals("employee")) {
					LoginAdminEmp logAdm = new LoginAdminEmp();
					logAdm.setVisible(true);
					closeWindow();

				}
				if (comboBox.getItemAt(comboBox.getSelectedIndex()).equals("logins")) {
					LoginAdminLog logAdmL = new LoginAdminLog();
					logAdmL.setVisible(true);
					closeWindow();

				}
			}
		});
		contentPane.add(btnNewButton);

		JLabel lblWitajWPanelu = new JLabel("Welcome in Admin panel!");
		lblWitajWPanelu.setBounds(55, 27, 198, 14);
		contentPane.add(lblWitajWPanelu);

		JButton btnPrevious = new JButton("Log out");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Log_in log = new Log_in();
				log.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(298, 226, 98, 29);
		contentPane.add(btnPrevious);
	}

	public void closeWindow() {
		WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}

}
