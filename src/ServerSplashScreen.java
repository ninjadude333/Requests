import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class ServerSplashScreen extends Frame {
	
	JWindow window;
		
	public ServerSplashScreen() throws HeadlessException {
		super();
				
		window = new JWindow();
		
		JLabel splashLabel = new JLabel(new ImageIcon(getClass().getResource("/RequestClientSplash.gif")),SwingConstants.CENTER);
		splashLabel.setOpaque(true);
        window.getContentPane().add(splashLabel);
        window.toFront();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	
    	
        window.setBounds(dim.width/3-window.getSize().width/10, dim.height/3-window.getSize().height/5, 500, 321);
        window.setVisible(true);
        window.setEnabled(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
     
    }
		 public void disposeWindow()
			{
				window.dispose();
			}
}
