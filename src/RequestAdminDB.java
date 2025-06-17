import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class RequestAdminDB extends JFrame{
	
	static Properties props = new Properties();
	
	public static void main(String[] args){
		
//		 ServerSplashScreen splash =  new ServerSplashScreen();
//		 splash.disposeWindow();
		 
		SwingUtilities.invokeLater(new Runnable() {
		      @Override
		public void run() {
		JFrame f = new JFrame("Meimad \"Request\" ADMIN Client v1.2 By:The_Dude");
		f.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		f.setLayout(new BorderLayout());
		
		try {
			readAndUpdateIpAndPort();
		} catch (FileNotFoundException e) {
			System.out.println("Ini file not found !");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exeption");
			e.printStackTrace();
		}
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	f.setLocation(dim.width/4-f.getSize().width/4, dim.height/9-f.getSize().height/9);
    	
    	menu menu = new menu();
    	f.setJMenuBar(menu.createMenuBar());
    	
		ImageIcon frameIcon = new ImageIcon(getClass().getResource("/mtvsicon.jpg"));
    	f.setIconImage(frameIcon.getImage());
    		    	
    	JPanel main = new ClientMainPanel().getInstance();
		f.getContentPane().add(main,BorderLayout.CENTER);
		f.add(new InfoPanel(),BorderLayout.SOUTH);
		
		f.setResizable(false);
		f.pack();
		f.setVisible(true);
		
		WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(null, "Really Quit ?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) 
                {
                	if (ListeningThread.isConnected())
                	{
            			try {
							ClientMainPanel.instance.disConnectFromServer();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                   System.exit(0);
                	}
                	else
                		System.exit(0);
                }
                System.exit(0);
            }
        };
        f.addWindowListener(exitListener);
				
		try {
			ClientMainPanel.instance.connectToServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread listenForUpdates = new Thread(new ListeningThread());
		listenForUpdates.start();
	
	}
		
	});
}
	
	private static void readAndUpdateIpAndPort() throws IOException, FileNotFoundException {
		props.load(new FileInputStream("Data/Request.ini"));
		String tmp = props.getProperty("ServerIP");
		Request.setServerIp(tmp);
		tmp = props.getProperty("comPort");
		Request.setServerPort(Integer.parseInt(tmp));
		}
}