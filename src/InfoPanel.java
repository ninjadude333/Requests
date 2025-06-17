import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel{
	JTextField infotxt,infotxt2,pritxt,space;
	static JTextField adminName;
	static JLabel sendersName;
	static JTextField serverIP,serverPort;
	static Properties props = new Properties();
	static JButton send;
	
	@SuppressWarnings("rawtypes")
	static JComboBox priority;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InfoPanel()
	{
		setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(800,40));
		setLayout(new FlowLayout());
		setAlignmentY(CENTER_ALIGNMENT);
		
		try {
			props.load(new FileInputStream("Data/Request.ini"));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		adminName = new JTextField();
		if (props.getProperty("AdminName") != null)
			adminName.setText(props.getProperty("AdminName"));
		else
			adminName.setText("Admin");
		adminName.setHorizontalAlignment(JTextField.CENTER);
		adminName.setBackground(Color.blue);
		adminName.setForeground(Color.YELLOW);
		adminName.setPreferredSize(new Dimension(50,25));
		adminName.setFont(new Font("Ariel",Font.BOLD , 20));
		adminName.setEditable(false);
		
		send = new JButton("שלח הודעה");
		send.setFont(new Font("Ariel",Font.PLAIN , 18));
		send.setBackground((Color.green));
		send.setForeground((Color.black));
		send.setPreferredSize(new Dimension(118,30));
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Request req = null;
//				 final JTextField tf = new JTextField(20);
//		  		    tf.addFocusListener(new FocusAdapter() {
//		  		        public void focusGained(FocusEvent e) {
//		  		            tf.getInputContext().selectInputMethod(new Locale("iw", "IL"));
//		  		        }
//		  		    });

		  		  	MyDialog newStatusDialog = new MyDialog("פרט את תוכן ההודעה","");
	            	
		  		    if (newStatusDialog.status != null && newStatusDialog.okWasPressed.equals(true))
		  		    	{
		  		    	req = new Request(adminName.getText(),newStatusDialog.status,InfoPanel.getPriority());
			 			initConnection(req);
		  		    	}
				}

				private void initConnection(Request req) {
					
					 Socket smtpSocket = null;  
				     DataOutputStream os = null;
				     DataInputStream is = null;
				 
				        try {
				        	smtpSocket = new Socket();
				            smtpSocket.setSoTimeout(500);
				            smtpSocket.connect(new InetSocketAddress(Request.getServerIp(),Request.getServerPort()),500);
				            os = new DataOutputStream(smtpSocket.getOutputStream());
				            is = new DataInputStream(smtpSocket.getInputStream());
				        } catch (UnknownHostException e) {
				            System.err.println("Don't know about host: hostname");
				        } catch (IOException e) {
				            System.err.println("Couldn't get I/O for the connection to: hostname");
				        }
				 
					if (smtpSocket != null && os != null) {
				            try {
				            	try {
									os.writeUTF(req.toString());
								} catch (Exception e1) {
									e1.printStackTrace();
								} 
								os.close();
				                is.close();
				                smtpSocket.close();
				                try {
									playSound(("/sos.wav"));
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} 
						        new JOptionPane();
				    			JOptionPane.showMessageDialog(null, "<html>הקריאה נשלחה בהצלחה</html>","נשלח בהצלחה",
				    				    JOptionPane.PLAIN_MESSAGE);
				    			InfoPanel.reSetSrvColor(Color.GREEN);
				    			InfoPanel.reSetPriority();
				    		} catch (UnknownHostException e) {
				                System.err.println("Trying to connect to unknown host: " + e);
				            } catch (IOException e) {
				                System.err.println("IOException:  " + e);
				            }
				        }
							else 
							{
								try {
									playSound("/sendError.wav");
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								new JOptionPane();
								JOptionPane.showMessageDialog(null, "<html>אי אפשר היה ליצור קשר עם השרת<br />יש לוודא שהכתובת נכונה או לפנות לטכנאי בטלפון.</html>","השליחה נכשלה",
								JOptionPane.PLAIN_MESSAGE);
								InfoPanel.reSetSrvColor(Color.RED);
							}
				}

				private void playSound(String soundFileName) throws LineUnavailableException,
						UnsupportedAudioFileException, IOException {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundFileName));
				     
					clip.open(inputStream);
					clip.start();
				}
		});
		
		JButton refresh = new JButton("רענון");
		refresh.setFont(new Font("Ariel",Font.PLAIN , 18));
		refresh.setBackground((Color.cyan));
		refresh.setForeground((Color.black));
		refresh.setPreferredSize(new Dimension(70,25));
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ClientMainPanel.instance.connectToServer();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		try {
			infotxt = new JTextField("Local IP: " + InetAddress.getLocalHost().getHostAddress());
			infotxt2 = new JTextField("Name: " + InetAddress.getLocalHost().getHostName());
			serverIP = new JTextField("Server IP: "+ Request.getServerIp());
			serverPort = new JTextField("Port: "+ Request.getServerPort());
			pritxt = new JTextField(" דחיפות ");
			space = new JTextField("                   ");
			sendersName = new JLabel("שם השולח:");
			infotxt.setBackground(Color.yellow);
			infotxt.setEditable(false);
			infotxt.setFont(new Font("Ariel", Font.BOLD, 14));
			infotxt2.setBackground(Color.yellow);
			infotxt2.setEditable(false);
			infotxt2.setFont(new Font("Ariel", Font.BOLD, 14));
			serverIP.setBackground(Color.yellow);
			serverIP.setFont(new Font("Ariel", Font.BOLD, 14));
			serverIP.setEditable(false);
			serverPort.setBackground(Color.yellow);
			serverPort.setFont(new Font("Ariel", Font.BOLD, 14));
			serverPort.setEditable(false);
			pritxt.setBackground(Color.RED);
			pritxt.setEditable(false);
			pritxt.setFont(new Font("Ariel", Font.BOLD, 14));
			space.setBackground(Color.ORANGE);
			space.setEditable(false);
			space.setBorder(null);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			}
		
		String[] priorityOption = { "נמוכה", "בינונית", "גבוהה", "דחוף" };
		priority = new JComboBox(priorityOption);
		priority.setSelectedIndex(1);
		priority.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int tmp = getPriority();
				switch (tmp)
				{
						case 0:
						case 1:	
							send.setBackground(Color.GREEN);
							break;
						case 2:	
							send.setBackground(Color.yellow);
							break;
						case 3:	
							send.setBackground(Color.red);
							break;
						default:
							break;
				}
			}
		});

		add(infotxt2);
		add(serverIP);
		add(serverPort);
		add(refresh);
		add(adminName);
		add(sendersName);
		add(send);
		add(priority);
	}
	
	public static int getPriority()
	{
		return (priority.getSelectedIndex()) ;
		
	}
	public static void reSetIp(String ip)
	{
		serverIP.setText(ip);
	}
	public static void reSetPriority()
	{
		priority.setSelectedIndex(1);
		send.setBackground(Color.GREEN);
	}
	public static void reSetPort(String port)
	{
		serverPort.setText(port);
		
	}
	public static void reSetSrvColor(Color c)
	{
		serverIP.setBackground(c);
		
	}
}