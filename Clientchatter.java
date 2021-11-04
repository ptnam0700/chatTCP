package chattcp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Clientchatter extends JFrame {

	private JPanel contentPane;
	private JTextField txtStaff;
	private JTextField txtServerIP;
	private JTextField txtServerPort;

	Socket mngSocket = null;
	String mngIP = "";
	int mngPort = 0;
	String staffName = "";
	BufferedReader bf = null ;
	DataOutputStream os = null;
	OutputThread t=null;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientchatter frame = new Clientchatter();
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
	public Clientchatter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Staff and Server Infor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 7, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Staff:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel);
		
		txtStaff = new JTextField();
		panel.add(txtStaff);
		txtStaff.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("MNG IP:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1);
		
		txtServerIP = new JTextField();
		panel.add(txtServerIP);
		txtServerIP.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Port:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_2);
		
		txtServerPort = new JTextField();
		panel.add(txtServerPort);
		txtServerPort.setColumns(10);
		JFrame thisFrame = this ;
		
		// fuction nut connect (doc ten va doc port )
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mngIP = txtServerIP.getText();
				mngPort = Integer.parseInt(txtServerPort.getText());
				staffName =txtStaff.getText();
				try {
					mngSocket = new Socket(mngIP,mngPort);	
					 if(mngSocket !=null) {
						 // tao panel chat giua client va sever
						 chatpanel p = new chatpanel(mngSocket , staffName , "Manager");
						 thisFrame.getContentPane().add(p);
						 p.getTxtMessages().append("Manager is running");
						 p.updateUI();
						 
						 //2 luong ra vao
						 bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream()));
						 os = new DataOutputStream(mngSocket.getOutputStream());
						 os.writeBytes("Staff :"+ staffName);
						 os.write(13); os.write(10);
						 os.flush();						 
					 }
					
					
				}
				catch (Exception ex) {
					ex.printStackTrace();		
				}
				
			}
		});
		panel.add(btnConnect);
	}

}
