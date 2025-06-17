import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ClientMainPanel extends JPanel {
	static ClientMainPanel instance = null;
	final int WIDTH=800;
	final int HEIGHT=600;
	
	public ClientMainPanel getInstance() {
		  
			if(instance == null)
			{
				instance = new ClientMainPanel();
				instance.setPreferredSize(new Dimension(WIDTH,HEIGHT));
				instance.setMinimumSize(new Dimension(WIDTH, HEIGHT));
				instance.setLayout(new BorderLayout());
				instance.setBackground(Color.ORANGE);
				
				ImageIcon imageIcon = new ImageIcon(getClass().getResource("/openingScrn.jpg"));
				
				JLabel label = new JLabel("", imageIcon, JLabel.CENTER);
								
				instance.add(label);
				instance.setOpaque(true);
				
				return instance;
			}
			else
			{
			return instance;
			}
		}	
		
		
	public  void connectToServer() throws IOException {
		   Request req = new Request("Login","Login",5);
		 		
			 Socket smtpSocket = null;  
		     DataOutputStream os = null;
		     DataInputStream is = null;
		     OutputStream out = null;
		 
		        try {
		            smtpSocket = new Socket();
		            smtpSocket.setSoTimeout(500);
		            smtpSocket.connect(new InetSocketAddress(Request.getServerIp(),Request.getServerPort()),400);
		            os = new DataOutputStream(smtpSocket.getOutputStream());
		            is = new DataInputStream(smtpSocket.getInputStream());
		            out = new FileOutputStream("recv.jpg");
		            
		        } catch (UnknownHostException e) {
		            System.err.println("Don't know about host: hostname");
		        } catch (IOException e) {
		            System.err.println("Couldn't get I/O for the connection to: hostname");
		        }
		 
			if (smtpSocket != null && os != null)
			{
		            os.writeUTF(req.toString()); 
		            copy(is, out);
					os.close();
					is.close();
					out.close();
					smtpSocket.close();
					addToMainPanel();
			}
					else 
					{
						ImageIcon imageIcon = new ImageIcon(getClass().getResource("/openingScrn.jpg"));
						JLabel label = new JLabel("", imageIcon, JLabel.CENTER);
						ClientMainPanel.instance.refreshPanel(label);
						
						Object[] options = {"Try Again","Exit"};

						int n = JOptionPane.showOptionDialog(null, "<html>אי אפשר היה ליצור קשר עם השרת<br />יש לוודא שהכתובת נכונה או לפנות לטכנאי בטלפון.</html>",
								"Can't Find Server", 
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null, options, options[1]);
						switch (n){
						case 0:
							connectToServer();
							break;
						default:
							break;
						}
//						JOptionPane.showMessageDialog(null, "<html>אי אפשר היה ליצור קשר עם השרת<br />יש לוודא שהכתובת נכונה או לפנות לטכנאי בטלפון.</html>",
//						"Can't Find Server",
//						JOptionPane.PLAIN_MESSAGE);
						}
						
//						InfoPanel.reSetSrvColor(Color.RED);
	}
	
	public  void disConnectFromServer() throws IOException {
		   Request req = new Request("Logout","Logout",5);
		 		
			 Socket smtpSocket = null;  
		     DataOutputStream os = null;
		     		 
		        try {
		            smtpSocket = new Socket();
		            smtpSocket.setSoTimeout(500);
		            smtpSocket.connect(new InetSocketAddress(Request.getServerIp(),Request.getServerPort()),400);
		            os = new DataOutputStream(smtpSocket.getOutputStream());
		            		            
		        } catch (UnknownHostException e) {
		            System.err.println("Don't know about host: hostname");
		        } catch (IOException e) {
		            System.err.println("Couldn't get I/O for the connection to: hostname");
		        }
		 
			if (smtpSocket != null && os != null)
			{
		            os.writeUTF(req.toString()); 
		           	os.close();
					smtpSocket.close();
			}
					else 
					{
						ImageIcon imageIcon = new ImageIcon(getClass().getResource("/openingScrn.jpg"));
						JLabel label = new JLabel("", imageIcon, JLabel.CENTER);
						ClientMainPanel.instance.refreshPanel(label);
						
						Object[] options = {"Exit"};

						JOptionPane.showOptionDialog(null, "<html>אי אפשר היה ליצור קשר עם השרת<br />יש לוודא שהכתובת נכונה או לפנות לטכנאי בטלפון.</html>",
								"Can't Find Server", 
								JOptionPane.OK_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null, options, options[1]);
						
//						JOptionPane.showMessageDialog(null, "<html>אי אפשר היה ליצור קשר עם השרת<br />יש לוודא שהכתובת נכונה או לפנות לטכנאי בטלפון.</html>",
//						"Can't Find Server",
//						JOptionPane.PLAIN_MESSAGE);
						}
						
//						InfoPanel.reSetSrvColor(Color.RED);
	}
			
		
		
	static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
       
    }
	
	private static void addToMainPanel() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("recv.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Did not recieve Image Correctly !");
		}
		
		Image dimg = img.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		
		ImageIcon imageIcon = new ImageIcon(dimg);
		
		JLabel label = new JLabel("", imageIcon, JLabel.CENTER);
		
		ClientMainPanel.instance.refreshPanel(label);
		
	}
	
	public void refreshPanel(JLabel label) {
		
		removeAll();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		label.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		label.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		add(label, BorderLayout.CENTER );
		revalidate();
		repaint();
		System.out.println("REPLAINT");		
	}		
	
}


