import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class ListeningThread implements Runnable {
	
	static ServerSocket srvsocket;
	
	
	public void run() {
	
		Socket socket = null;
		
			try {
				srvsocket = new ServerSocket(Request.getServerPort());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
           while (true) {
        	         	 
        	 try {
        		 socket = srvsocket.accept();
			     InputStream in = socket.getInputStream();
				 OutputStream out = new FileOutputStream("recv.jpg");
				 copy(in, out);
				 out.close();
		         in.close();
		         socket.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	 addToMainPanel();
        	 playSound();
           } 
     }

private void playSound() {
		try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("water.wav"));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
	}

	static void copy(InputStream in, OutputStream out) throws IOException {
	        byte[] buf = new byte[8192];
	        int len = 0;
	        while ((len = in.read(buf)) != -1) {
	            out.write(buf, 0, len);
	        }
	       
	    }
	
	public static boolean isConnected()
	{
		if (srvsocket.isClosed())
			return false;
		else
			return true;
	}

	private static void addToMainPanel() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("recv.jpg"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		
		ImageIcon imageIcon = new ImageIcon(dimg);
		
		JLabel label = new JLabel("", imageIcon, JLabel.CENTER);
		
		ClientMainPanel.instance.refreshPanel(label);
	}
}