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

public class LoginAdminEmp extends JFrame {

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
	public LoginAdminEmp() {
		initialize();
	}

	
	public void initialize(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 400, 471, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCoChceszZrobi = new JLabel("What do you want to do?");
		lblCoChceszZrobi.setBounds(47, 32, 172, 31);
		contentPane.add(lblCoChceszZrobi);
		
		JRadioButton rdbtSelect = new JRadioButton("Reviewing table employees");
		rdbtSelect.setToolTipText("Choose one option, what to do");
		rdbtSelect.setBounds(48, 88, 356, 25);
		contentPane.add(rdbtSelect);
		
		JRadioButton rdbtInsert = new JRadioButton("Adding/removing employee");
		rdbtInsert.setToolTipText("Choose one option, what to do");
		rdbtInsert.setBounds(47, 133, 266, 25);
		contentPane.add(rdbtInsert);
		
		JRadioButton rdbtUpdate = new JRadioButton("Updating data");
		rdbtUpdate.setToolTipText("Choose one option, what to do");
		rdbtUpdate.setBounds(47, 188, 266, 25);
		contentPane.add(rdbtUpdate);
		
		JButton btnWybr = new JButton("Click");
		btnWybr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtSelect.isSelected()){
					ShowAllEmp all= new ShowAllEmp();
					all.setVisible(true);
					closeWindow();
				}
				if(rdbtInsert.isSelected()){
					AddRemoveEmp addRemove= new AddRemoveEmp();
					addRemove.setVisible(true);
					closeWindow();
				}
				if(rdbtUpdate.isSelected()){
					UpdateEmp updateE= new UpdateEmp();
					updateE.setVisible(true);
					closeWindow();
				}
			}
		});
		btnWybr.setBounds(47, 246, 133, 48);
		contentPane.add(btnWybr);
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableChoice tch= new TableChoice();
				tch.setVisible(true);
				closeWindow();
			}
		});
		btnPrevious.setBounds(329, 250, 112, 41);
		contentPane.add(btnPrevious);
		
	}
	
	public void closeWindow(){
		WindowEvent winClosingEvent=new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}
}
