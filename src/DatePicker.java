import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXDatePicker;

@SuppressWarnings("serial")
public class DatePicker extends JPanel {
	
	public static JTextField wordSearchInput;
	public static JCheckBox untill,wordSearch;
	public static JPanel found = new JPanel(new GridBagLayout());
	public static List<String> pharsedRequest;
	public JXDatePicker picker,picker2;
	public static JScrollPane scroll;
	public static TreeMap<String,Request> foundReqByDate = new TreeMap<String, Request>();
	public static int entriesFound = 0;
    public static JLabel numFound = new JLabel(" ");
    public static JButton printBtn;
    public static JButton exportEXLBtn;
    public static JButton seek;
    public static requestLogMenu menu;
    public static JFrame frame ;
    public static JRadioButton byOpeningDate;
    public static JRadioButton byClosingDate;
	
    public DatePicker(){
    	SwingUtilities.invokeLater(new Runnable() {
  	      @Override
  	      public void run() {
        frame = new JFrame("Request Log Viewer By: The_Dude");
        final JPanel panel = new JPanel();
                   
        frame.setPreferredSize(new Dimension(1380, 720));
        ImageIcon framIcon = new ImageIcon(getClass().getResource("/mtvsiconLOG.jpg"));
    	frame.setIconImage(framIcon.getImage());
    	
    	menu = new requestLogMenu();
    	frame.setJMenuBar(menu.createMenuBar());
        
    	if (scroll != null)
		{
		found.removeAll();
		scroll.remove(found);
		panel.remove(scroll);
		}
    	
        picker = new JXDatePicker();
        picker.setAlignmentX(CENTER_ALIGNMENT);
        picker.setFont(new Font("Ariel", Font.PLAIN, 22));
        picker2 = new JXDatePicker();
        picker2.setAlignmentX(CENTER_ALIGNMENT);
        picker2.setFont(new Font("Ariel", Font.PLAIN, 22));
        picker2.setEnabled(false);
        
        JPanel buttonsPanel = new JPanel();
        JPanel dateSearchPanel = new JPanel();
          
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        dateSearchPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        
        BoxLayout buttonsBoxLayout = new BoxLayout(buttonsPanel,BoxLayout.X_AXIS);
        buttonsPanel.setLayout(buttonsBoxLayout);
        BoxLayout dateSearchBoxLayout = new BoxLayout(dateSearchPanel,BoxLayout.X_AXIS);
        buttonsPanel.setLayout(buttonsBoxLayout);
        dateSearchPanel.setLayout(dateSearchBoxLayout);
        
        byClosingDate = new JRadioButton("סדר תוצאות לפי תאריך סגירה");
        byClosingDate.setHorizontalTextPosition(SwingConstants.LEFT);
        byClosingDate.setSelected(true);
        byOpeningDate = new JRadioButton("סדר תוצאות לפי תאריך פתיחה");
        byOpeningDate.setHorizontalTextPosition(SwingConstants.LEFT);
        
        ButtonGroup group = new ButtonGroup();
        group.add(byClosingDate);
        group.add(byOpeningDate);
                
        // SEEK BUTTON
        seek = new JButton("חיפוש !!!");
        seek.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        seek.setFont(new Font("Ariel", Font.BOLD, 22));
        seek.setBackground(Color.ORANGE);
        seek.setMaximumSize(new Dimension(115,280));
        seek.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String wordToSearch="No$Word";
				  
				if(wordSearch.isSelected()) wordToSearch=wordSearchInput.getText();
				
				if (scroll != null)
					{
					found.removeAll();
					scroll.remove(found);;
					panel.remove(scroll);
					entriesFound = 0;
					(menu._menuExcel).setEnabled(false);
	        		(menu._menuPrint).setEnabled(false);
					}
				
					// Get search query from server via Thread ! and update found Jpanel.
					FindLogsInServer(picker, picker2,wordToSearch);
					
				    numFound.setText(" נמצאו : " + entriesFound + " קריאות המתאימות לחיפוש ");
		        	numFound.setHorizontalAlignment(SwingConstants.CENTER);
		        	numFound.setVisible(true);
		        	numFound.setForeground(Color.DARK_GRAY);
		        	numFound.setFont(new Font("Ariel", Font.PLAIN, 24));
		        	
		        	if (entriesFound>0) 
		        	{
		        		printBtn.setEnabled(true);
		        		exportEXLBtn.setEnabled(true);
		        		(menu._menuExcel).setEnabled(true);
		        		(menu._menuPrint).setEnabled(true);
		        	}
		        	
		        	scroll = new JScrollPane(found);
		        	scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					panel.add(scroll,BorderLayout.CENTER);
					panel.revalidate();
					panel.repaint();
			}
		});
        
        
        // CLEAR BUTTON        
        JButton clear = new JButton("נקה מסך");
        clear.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        clear.setBackground(new Color(130,177,106));
        clear.setMaximumSize(new Dimension(200,150));
        clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
					if (found != null)
					{
					numFound.setVisible(true);
					printBtn.setEnabled(false);
					exportEXLBtn.setEnabled(false);
					found.removeAll();
					(menu._menuExcel).setEnabled(false);
	        		(menu._menuPrint).setEnabled(false);
					panel.revalidate();
					panel.repaint();
					}
			}
		});
        
        // CLOSE BUTTON        
        JButton close = new JButton("סגור חלון");
        close.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        close.setBackground(Color.red);
        close.setMaximumSize(new Dimension(200,150));
        close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();	
				
			}
		});
        
     // PRINT BUTTON        
        printBtn = new JButton("הדפס");
        printBtn.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        printBtn.setBackground(Color.pink);
        printBtn.setMaximumSize(new Dimension(150,150));
        printBtn.setEnabled(false);
        printBtn.setFont(new Font("Ariel", Font.BOLD, 16));
        printBtn.addActionListener(new ActionListener() {
			int type=1;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PrepareArrayForOutput(foundReqByDate,type);	
				
			}
		});
        
        // Excel Export BUTTON        
        exportEXLBtn = new JButton("ייצוא לאקסל");
        exportEXLBtn.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        exportEXLBtn.setBackground(Color.pink);
        exportEXLBtn.setMaximumSize(new Dimension(150,150));
        exportEXLBtn.setEnabled(false);
        exportEXLBtn.setFont(new Font("Ariel", Font.BOLD, 16));
        exportEXLBtn.addActionListener(new ActionListener() {
			int type=2;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PrepareArrayForOutput(foundReqByDate,type);	
				
			}
		});
        
        panel.setLayout(new BorderLayout());
                
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(dim.width/12-frame.getSize().width, dim.height/6-frame.getSize().height/8, 1280, 820);
    	
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        picker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (untill.isSelected() == false) picker2.setDate(picker.getDate());
			}
		});
        
        picker2.setDate(picker.getDate());
        picker2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        wordSearchInput = new JTextField("");
        wordSearchInput.setMinimumSize(new Dimension(150, 0));
        wordSearchInput.setEnabled(false);
        wordSearchInput.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        untill = new JCheckBox("עד");
        untill.setFont(new Font("Ariel", Font.BOLD, 22));
        untill.setHorizontalTextPosition(SwingConstants.LEFT);
        untill.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (untill.isSelected()) picker2.setEnabled(true);
				if (!untill.isSelected())
				{
					picker2.setEnabled(false);
					picker2.setDate(picker.getDate());
				}
			}
		});
        
        wordSearch = new JCheckBox("חפש מילה");
        wordSearch.setFont(new Font("Ariel", Font.BOLD, 22));
        wordSearch.setHorizontalTextPosition(SwingConstants.LEFT);
        wordSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (wordSearch.isSelected()) wordSearchInput.setEnabled(true);
				if (!wordSearch.isSelected())
				{
					wordSearchInput.setEnabled(false);
				}
			}
		});
        
        dateSearchPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        dateSearchPanel.add(seek);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(40, 0)));
        dateSearchPanel.add(wordSearchInput);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        dateSearchPanel.add(wordSearch);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(40, 0)));
        dateSearchPanel.add(byClosingDate);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        dateSearchPanel.add(byOpeningDate);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(40, 0)));
        dateSearchPanel.add(picker2);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        dateSearchPanel.add(untill);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        dateSearchPanel.add(picker);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        
        JLabel me = new JLabel("מ");
        me.setFont(new Font("Ariel", Font.BOLD, 22));
        dateSearchPanel.add(me);
        dateSearchPanel.add(Box.createRigidArea(new Dimension(435, 0)));
        
        numFound.setVisible(true);
        numFound.setOpaque(true);
        numFound.setMinimumSize(new Dimension(390,0));
        numFound.setPreferredSize(new Dimension(390,0));
//        numFound.setBackground(Color.BLACK);
        
//        buttonsPanel.add(Box.createRigidArea(new Dimension((((frame.getWidth())-200)/2), 0)));
        buttonsPanel.add(Box.createRigidArea(new Dimension(430, 0)));
        buttonsPanel.add(numFound);
        buttonsPanel.add(Box.createRigidArea(new Dimension(80, 0)));
        buttonsPanel.add(printBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonsPanel.add(exportEXLBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonsPanel.add(clear);
        buttonsPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonsPanel.add(close);
        buttonsPanel.add(Box.createRigidArea(new Dimension(500, 0)));
				
        panel.add(buttonsPanel,BorderLayout.SOUTH);
        panel.add(dateSearchPanel,BorderLayout.NORTH);
        panel.add(found,BorderLayout.CENTER);
        frame.getContentPane().add(panel);
          	
        frame.pack();
        frame.setVisible(true);
    }
		     
  	    void FindLogsInServer (JXDatePicker firstDate,JXDatePicker lastDate,String searchWord)
  		{
  			long firstDatetmp = firstDate.getDate().getTime();
  			long lastDatetmp = lastDate.getDate().getTime();
  			
  			int mode = (byOpeningDate.isSelected() ? 0:1); 
  			
  			Request req = new Request("*RequestLOG*",Long.toString(firstDatetmp),Long.toString(lastDatetmp),searchWord,mode);
  			
  			String logSearchRawData = getLogQuery(req);
  			if (logSearchRawData != null) 
  			{
  			pharseLog(logSearchRawData);
  			}
  	  	}

  		// Sends The Server the Search details - Starting Day + number of days, gets back a string.
  			private String getLogQuery(Request reqQuery) {
  				 Socket smtpSocket = null;  
  			     DataOutputStream os = null;
  			     DataInputStream is = null;
  			     
  			        try {
  			        	smtpSocket = new Socket(Request.getServerIp(),Request.getServerPort());
  			            os = new DataOutputStream(smtpSocket.getOutputStream());
  			            is = new DataInputStream(smtpSocket.getInputStream());
  			        } catch (UnknownHostException e) {
  			            System.err.println("Don't know about host: hostname");
  			        } catch (IOException e) {
  			            System.err.println("Couldn't get I/O for the connection to: hostname");
  			        }
  			 
  				if (smtpSocket != null && os != null) {
  			          
  							try {
  								os.writeUTF(reqQuery.toStringForLog());
  								String rawData = "";
  								rawData = is.readUTF();
  								os.close();
  								is.close();
  								smtpSocket.close();
  								return rawData;
  							} catch (IOException e) {
  								e.printStackTrace();
  							}
  			        }
  						else 
  						{
  							new JOptionPane();
  							JOptionPane.showMessageDialog(null, "<html>אי אפשר היה ליצור קשר עם השרת<br />יש לוודא שהכתובת נכונה או לפנות לטכנאי בטלפון.</html>","השליחה נכשלה",
  							JOptionPane.PLAIN_MESSAGE);
  							InfoPanel.reSetSrvColor(Color.RED);
  							try {
								is.close();
								smtpSocket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
  							return null;
  						}
  				return null;
  			}

  			// take the RAW string returned from server and parse it to lines.
  			private void pharseLog(String logSearchRawData) {
  				  				  				
  				if (logSearchRawData.equalsIgnoreCase("Empty"))
  				{
  					  JLabel notFound = new JLabel("לא קיימות פנוית מיום זה");
  					  notFound.setFont(new Font("Ariel", Font.BOLD, 34));
  					  found.add(notFound);
  					  entriesFound = 0;;
  				}
  				
  				else
  				{
  				List<String> tmpList = new ArrayList<String>(Arrays.asList(logSearchRawData.split("zZz")));
  				foundReqByDate.clear();
  				for (String item : tmpList)
  					{
  					System.out.println(item.toString());
  					List<String> foundLogs = new ArrayList<String>(Arrays.asList(item.split(";;;")));
  					
  					Request newReqonDate = new Request(Integer.parseInt(foundLogs.get(0)), foundLogs.get(1), foundLogs.get(2),
  							  Integer.parseInt(foundLogs.get(3)), foundLogs.get(4), foundLogs.get(5),
  							  Long.parseLong(foundLogs.get(6)), foundLogs.get(7), Long.parseLong(foundLogs.get(8)));
  					 
  					foundReqByDate.put(foundLogs.get(6), newReqonDate);
  					}
  					  GridBagConstraints c = new GridBagConstraints();
  					  c.anchor = GridBagConstraints.NORTHWEST;
  					  c.gridx=0;
  					 		
  					  if (foundReqByDate.isEmpty() == false)
  					  {
  					 
  					  SortedSet<String> keys = new TreeSet<String>(foundReqByDate.keySet());
  					  for (String key : keys) { 
  						 Request value = foundReqByDate.get(key);
  						 found.add(new requestLogLine(value),c);
  					  }
  					  
  					  c.weighty = 1;
  					  c.weightx = 1;
  					  found.add(new JPanel(),c);
  					  found.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
  					  entriesFound = foundReqByDate.size();
  				  }			  
  				}
  			}	
    	});
    	}
}		