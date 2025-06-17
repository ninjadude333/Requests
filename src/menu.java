import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

// This Class Creates the menu bar
@SuppressWarnings("serial")
public class menu extends JPanel implements ActionListener{
	JTextArea output;
    JScrollPane scrollPane;
    JMenu menu;
    static JMenuBar menuBar;
    static Properties props = new Properties();
    OutputStream out = null;
    
    public JMenuBar createMenuBar() {
        
        JMenuItem menuItem;
        
        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription("File Menu");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Set Server IP",KeyEvent.VK_I);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Set Server Ip");
        menuItem.setEnabled(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Set Communication Port");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Set Server Port");
        menuItem.setEnabled(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Retry Connecting To Server");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Connect To Server");
        menuItem.setEnabled(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Launch RequestLog");
        menuItem.setMnemonic(KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Open RequestLog");
        menuItem.setEnabled(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
                
        menuItem = new JMenuItem("Change Admin Name");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Change Admin Name");
        menuItem.setEnabled(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Exit",KeyEvent.VK_X);
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //Build second menu in the menu bar.
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menu.getAccessibleContext().setAccessibleDescription(
                "Help Menu");
        menuItem = new JMenuItem("View ChangeLog",KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "ChangeLog");
        menuItem.setEnabled(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("About",KeyEvent.VK_B);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "About Window");
        menuItem.setEnabled(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuBar.add(menu);

        return menuBar;
    }

    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }
	public void actionPerformed(ActionEvent m) {
	String buttonTYPE = m.getActionCommand();
	
		switch (buttonTYPE) {
		case "Exit":
			int confirm = JOptionPane.showOptionDialog(null, "Really Quit ?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == 0) 
            {
            	if (ListeningThread.isConnected())
            	{
        			try {
						ClientMainPanel.instance.disConnectFromServer();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
               System.exit(0);
            	}
            	else
            		System.exit(0);
            }
            System.exit(0);
			break;
		case "About":
			JDialog about = new About(new JFrame());
		    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			about.setLocation(dim.width/2-about.getSize().width/2, dim.height/2-about.getSize().height/2);
			about.setResizable(false);
		    about.setVisible(true);
			break;
		case "Set Server IP":
		    setServerIP();
		    try {
				ClientMainPanel.instance.connectToServer();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "Set Communication Port":
		    setServerPort();
			break;
		case "Change Admin Name":
		    changeAdminName();
		    break;
		case "Retry Connecting To Server":
		    try {
				ClientMainPanel.instance.connectToServer();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "Launch RequestLog":
			new DatePicker();
			break;
		case "View ChangeLog":
			File changelog = new File("ChangeLogv1-2.txt");
			try {
				if (System.getProperty("os.name").toLowerCase().contains("windows")) {
					  String cmd = "rundll32 url.dll,FileProtocolHandler " + (changelog.getCanonicalPath());
					  Runtime.getRuntime().exec(cmd);
					} 
					else {
					  Desktop.getDesktop().edit(changelog);
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default:
			break;
		}
	}
	
	private void setServerIP() 
	{
	 Icon icon = null;
	 String ip = (String)JOptionPane.showInputDialog(
             this,
             ("כתובת השרת:\n"), 
             "Server Ip Settings",
             JOptionPane.PLAIN_MESSAGE,
             icon,
             null,
             Request._serverIp);

	 	//If a string was returned....
	 		if ((ip != null) && (ip.length() > 3))
	 		{
	 			try {
					props.load(new FileInputStream("Data/Request.ini"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 			Request.setServerIp(ip);
	 			InfoPanel.reSetIp("Server IP: "+ip);
	 			props.setProperty("ServerIP",ip);
				try {
					out = new FileOutputStream("Data/Request.ini");
					props.store(out, "");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		}
//	 		else {
//	 			try {
//					props.load(new FileInputStream("Data/Request.ini"));
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//	 			Request.setServerIp("192.168.1.202");
//	 			InfoPanel.reSetIp("Server IP: 192.168.1.202");
//	 			props.setProperty("ServerIP","192.168.1.202");
//				try {
//					out = new FileOutputStream("Data/Request.ini");
//					props.store(out, "");
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	 			}
	}

	private void changeAdminName() 
	{
		 Icon icon = null;
		 String newName = (String)JOptionPane.showInputDialog(
	             this,
	             ("שם השולח: \n"), 
	             "שם השולח",
	             JOptionPane.PLAIN_MESSAGE,
	             icon,
	             null,
	             InfoPanel.adminName.getText());

			try {
				props.load(new FileInputStream("Data/Request.ini"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
		 	//If a string was returned....
			if ((newName != null) && (newName.length() > 0))
			{	
				InfoPanel.adminName.setText(newName);
				props.setProperty("AdminName",newName);
				
				try {
					out = new FileOutputStream("Data/Request.ini");
					props.store(out, "");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			else
//			{
////				InfoPanel.adminName.setText("Admin");
////				props.setProperty("AdminName","Admin");
////				
////				try {
////					out = new FileOutputStream("Data/Request.ini");
////					props.store(out, "");
////				} catch (FileNotFoundException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				} catch (IOException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//			}
			
			
		
			}
		
	private void setServerPort() 
	{
	 Icon icon = null;
	 String port = (String)JOptionPane.showInputDialog(
             this,
             ("Port Number: (Default:25000)\n"), 
             "Server Communication Port",
             JOptionPane.PLAIN_MESSAGE,
             icon,
             null,
             "25000");

	 	//If a string was returned....
		if ((port != null) && (port.length() > 0))
		{	
			try {
				props.load(new FileInputStream("Data/Request.ini"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Request.setServerPort(Integer.parseInt(port));
			InfoPanel.reSetPort("Port: "+Integer.parseInt(port));
			props.setProperty("comPort",port);
			try {
				
				out = new FileOutputStream("Data/Request.ini");
				props.store(out, "");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			try {
				props.load(new FileInputStream("Data/Request.ini"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Request.setServerPort(25000);
			InfoPanel.reSetPort("Port: "+(25000));
			props.setProperty("comPort","25000");
			try {
				out = new FileOutputStream("Data/Request.ini");
				props.store(out, "");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

