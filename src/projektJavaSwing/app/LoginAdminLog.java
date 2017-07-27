package projektJavaSwing.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginAdminLog extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginAdminEmp frame = new LoginAdminEmp();
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
	public LoginAdminLog() {
		initialize();
	}

	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 400, 419, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCoChceszZrobi = new JLabel("What do you want to do?");
		lblCoChceszZrobi.setBounds(31, 34, 172, 31);
		contentPane.add(lblCoChceszZrobi);

		JRadioButton rdbtnPrzekldanieZawartociTabeli = new JRadioButton("Reviewing table logins");
		rdbtnPrzekldanieZawartociTabeli.setToolTipText("Choose one option, what to do");

		rdbtnPrzekldanieZawartociTabeli.setBounds(32, 90, 356, 25);
		contentPane.add(rdbtnPrzekldanieZawartociTabeli);

		JRadioButton rdbtnDodawaniePracownikw = new JRadioButton("Adding/removing logins");
		rdbtnDodawaniePracownikw.setBounds(31, 135, 266, 25);
		rdbtnDodawaniePracownikw.setToolTipText("Choose one option, what to do");
		contentPane.add(rdbtnDodawaniePracownikw);

		JRadioButton rdbtnAktualizacjaDanychPracownikw = new JRadioButton("Updating data");
		rdbtnAktualizacjaDanychPracownikw.setBounds(31, 190, 266, 25);
		rdbtnAktualizacjaDanychPracownikw.setToolTipText("Choose one option, what to do");
		contentPane.add(rdbtnAktualizacjaDanychPracownikw);

		JButton btnWybr = new JButton("Click");
		btnWybr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnPrzekldanieZawartociTabeli.isSelected()) {
					ShowAllLogins allLog = new ShowAllLogins();
					allLog.setVisible(true);
					closeWindow();
				}
				if (rdbtnDodawaniePracownikw.isSelected()) {
					AddRemoveLog addRemove = new AddRemoveLog();
					addRemove.setVisible(true);
					closeWindow();
				}
				if (rdbtnAktualizacjaDanychPracownikw.isSelected()) {
					UpdateLog updateLog = new UpdateLog();
					updateLog.setVisible(true);
					closeWindow();
				}
			}
		});
		btnWybr.setBounds(31, 240, 112, 41);
		contentPane.add(btnWybr);

		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableChoice tb = new TableChoice();
				tb.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(261, 245, 100, 31);
		contentPane.add(btnPrevious);
	}

	public void closeWindow() {
		WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}
}
