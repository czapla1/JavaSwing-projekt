package projektJavaSwing.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Log_in extends JFrame implements ActionListener {

	private JPanel contentPane;
	public static JTextField textLogin;
	private JPasswordField pwdHaslo;
	private DBConnectionNew dbCon;
	private JButton btnLogin;
	private JLabel lblLabel;
	ResultSet rs;
	String po;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Log_in frame = new Log_in();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Log_in() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 300, 309, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(30, 31, 173, 40);
		contentPane.add(lblUser);

		textLogin = new JTextField();
		textLogin.setToolTipText("Enter your login");
		textLogin.setBounds(30, 70, 116, 22);
		contentPane.add(textLogin);
		textLogin.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 115, 100, 16);
		contentPane.add(lblPassword);

		pwdHaslo = new JPasswordField();
		pwdHaslo.setToolTipText("Enter your Password");
		pwdHaslo.setBounds(30, 144, 116, 22);
		pwdHaslo.addActionListener(this);
		contentPane.add(pwdHaslo);

		JButton btnLogin = new JButton("Click");
		btnLogin.setBounds(30, 202, 79, 25);
		btnLogin.addActionListener(this);
		contentPane.add(btnLogin);

		try {
			dbCon = DBConnectionNew.makeConnection("root", "kwiat23");

		} catch (MySuperExc e) {
			e.coSieStalo();
		}
	}

	private boolean checkUser(String login, String password) {
		boolean isCorrect = false;
		try {
			ResultSet rs = dbCon.getTable("logins");
			while (rs.next()) {
				if (rs.getString(2).equals(login) && rs.getString(3).equals(password)) {
					isCorrect = true;
					break;
				} else {
					isCorrect = false;
				}
			}
		} catch (SQLException e) {
			System.out.println("Cannot get results");
			e.printStackTrace();
		}
		return isCorrect;
	}

	private boolean isAdmin(String login) {
		boolean isAdmin = false;
		try {
			ResultSet rs = dbCon.getUserPriviliges(login);
			rs.next();
			if (rs.getString(1).equals("admin")) {
				isAdmin = true;
			} else {
				isAdmin = false;
			}
		} catch (SQLException e) {
			System.out.println("Cannot get results");
			e.printStackTrace();
		}
		return isAdmin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String loginFromUser = textLogin.getText();
		String passwordFromUser = String.valueOf(pwdHaslo.getPassword());

		if (checkUser(loginFromUser, passwordFromUser)) {
			JOptionPane.showMessageDialog(lblLabel, "Correct!");
			if (isAdmin(loginFromUser)) {
				TableChoice tabChoice = new TableChoice();
				tabChoice.setVisible(true);
				closeWindow();

			} else {

				LoginUser logUs = new LoginUser();
				logUs.setVisible(true);
				closeWindow();
			}

		} else {
			JOptionPane.showMessageDialog(lblLabel, "Incorrect, try again!s");
			Log_in log = new Log_in();
			log.setVisible(true);
			closeWindow();
		}
	}

	public void closeWindow() {
		WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}

}
