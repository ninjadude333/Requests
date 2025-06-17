

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

public class Request {
	String _computerName;												// Stores computer Name
	long _TimeStamp;		   											// Stores Time stamp in a long Millisecond Format
	Date _DateTimeStamp;												// Stores Time stamp in a Date Object Format
	Date _DateClosingTimeStamp;
	Calendar _CalTimeStamp;												// Stores Time stamp in a Calendar Object Format
	int _urgency = 0; 													// 0=Low, 1=Medium 2=High 3=Critical
	String _computerIp;													// Stores computer IP
	static String _serverIp = "192.168.1.202";							// Stores ServerSide IP
	static int _serverPort = 25000;										// Stores Communication Port with server;
	String _comment;													// Stores comment
	String _techComment = "";											// Stores techComment for RqstLOG
	String _requestName = "";											// Stores the request name "TYPE"
	boolean _isActive;													// Marks The Request Status Open=true, Closed=false
	int _searchMode;
	String _searchWord;
	long _closingTimeStamp;
	int _id;
	
	
	public Request(String name, String comment,int urgency)
	{
		_computerName = getComputerName();
		name = name.replace("<html>","");
		name = name.replace("</html>","");
		_requestName = name.replace("<br />"," ");
		setTimeStamp();
		
			try {
				_computerIp =  getIpAddress();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		_comment = comment;
		_urgency = urgency;
		_isActive = true;
		
		System.out.print("request name: ");
		printName();
		System.out.println("Time stamp in MilliSec:  " +getTimeStampMilli());
		System.out.println("Time stamp in Date Format:  " +getTimeStampDate());
		System.out.println("Computer name that sent the request:  " +getComputerName());
		
			try {
				System.out.println("IP that sent the request: " + getIpAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			}
	
		System.out.println("User's Comment:  " +comment);
		System.out.println("Server IP:  " + getServerIp());
		System.out.println("Server Port:  " + getServerPort());
		System.out.println("Request Priority:  " + urgency);
		
//		_requestManager.addRequest(this);
//		System.out.println(_requestManager.allReqToPrint());
	}
	

//	public Request(String name, String comment,int urgency,int searchMode, String requestWord)
//	{
//		_computerName = getComputerName();
//		name = name.replace("<html>","");
//		name = name.replace("</html>","");
//		_requestName = name.replace("<br />"," ");
//		setTimeStamp();
//		
//			try {
//				_computerIp =  getIpAddress();
//			} catch (UnknownHostException e1) {
//				e1.printStackTrace();
//			} catch (SocketException e1) {
//				e1.printStackTrace();
//			}
//				
//		_searchMode = searchMode;
//		_searchWord = requestWord;
//		_comment = comment;
//		_urgency = urgency;
//		_isActive = true;
//		
//		System.out.print("request name: ");
//		printName();
//		System.out.println("Time stamp in MilliSec:  " +getTimeStampMilli());
//		System.out.println("Time stamp in Date Format:  " +getTimeStampDate());
//		System.out.println("Computer name that sent the request:  " +getComputerName());
//		
//			try {
//				System.out.println("IP that sent the request: " + getIpAddress());
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//		
//		System.out.println("User's Comment:  " +comment);
//		System.out.println("Server IP:  " + getServerIp());
//		System.out.println("Server Port:  " + getServerPort());
//		System.out.println("Request Priority:  " + urgency);
//	}
	
	// RequestLog Constructor
	public Request(int iD,String name, String comment,int urgency,String compName, String compIp,long timeStamp,String techComm,long closingTimeStamp)
	{
		_computerName = compName;
		_requestName = name;
		_computerIp = compIp;
		_techComment = techComm;
		_id = iD;
		_comment = comment;
		_urgency = urgency;
		_isActive = false;
		_TimeStamp = timeStamp;
		_closingTimeStamp = closingTimeStamp;
		addTimeFormats();
	}
	
	// Request Type for RequestLog Query
	public Request(String specialWord, String firstDate, String lastDate,String searchWord, int searchMode) {
		_requestName = specialWord;
		_comment = firstDate;
		_computerName = lastDate;
		_searchWord = searchWord;
		_searchMode = searchMode;
		}

	/*
	 * The method returns the local current computer Name
	 */
	public String getComputerName()
	{
		String hostname = "Unknown";

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    return "Hostname can not be resolved";
		}
		
		return hostname;
	}
	
	/*
	 * The method returns the local current computer Name
	 */
	public void setTimeStamp()
	{
		_DateTimeStamp = new Date();
		_TimeStamp = System.currentTimeMillis();
		_CalTimeStamp =  Calendar.getInstance();
	}
	
	/*
	 * The method returns the local computer IP Address
	 */
	@SuppressWarnings("rawtypes")
	public static String getIpAddress() throws UnknownHostException, SocketException
	{
		Enumeration en = NetworkInterface.getNetworkInterfaces();
		while(en.hasMoreElements()){
		    NetworkInterface ni=(NetworkInterface) en.nextElement();
		    Enumeration ee = ni.getInetAddresses();
		    while(ee.hasMoreElements()) {
		        InetAddress ia= (InetAddress) ee.nextElement();
		        if (ia.getHostAddress().startsWith(Request.getServerIp().substring(0, 5)))
		        	return ia.getHostAddress(); 
		    }
		 }
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	public static int getPort() throws UnknownHostException
	{
		return _serverPort;
	}	
	
	public String getComment()
	{
		return _comment;
	}
	
	public String toString()
	{
	return (_requestName + ";;;" + _comment + ";;;" + _urgency + ";;;" + _computerName + ";;;" + _computerIp);
	}
	
	public String toStringForLog()
	{ 
		     //Special word        //start date        //end date              //search word
	return (_requestName + ";;;" + _comment + ";;;" + _computerName + ";;;" + _searchWord + ";;;" + _searchMode);
	}
	
	private void addTimeFormats() {
		_DateTimeStamp = new Date(_TimeStamp);
		_DateClosingTimeStamp = new Date(_closingTimeStamp);
	}
	
	/*
	 * The method returns the Time Request created MiliSeconds Format
	 */
	public long getTimeStampMilli()
	{
		return _TimeStamp;
	}
	
	/*
	 * The method returns the Time Request created as a Date Object
	 */
	public Date getTimeStampDate()
	{
		return _DateTimeStamp;
	}
	
	public String getHourTimeFormat()
	{
		SimpleDateFormat ft = new SimpleDateFormat ("kk:mm:ss");
		return ft.format(_DateTimeStamp);
	}
	
	public String getDayTimeFormat()
	{
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM");
		return ft.format(_DateTimeStamp);
	}
	
	public String getTechComment()
	{
		return _techComment;
	}
	
	public String calculateTimePassed()
	{
		long estimatedTime = System.currentTimeMillis() - _TimeStamp;
		long x = estimatedTime / 1000;
		long seconds = x % 60;
		x /= 60;
		long minutes = x % 60;
		x /= 60;
		long hours = x % 24;
		x /= 24;
		long days = x;
		String timePassed = "<html><b><font color=\"#000\" " +
				"size=\"10\" face=\"Ariel\">הקריאה פתוחה כבר<br />"+ days + ":" + hours + ":" + minutes + ":" + seconds +"</font></p></html>";
		return timePassed;
	}
	
	public String getFullDate()
	{
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		return ft.format(_DateTimeStamp);
	}
	
	public String calculateOpenFor(long opened, long closed)
	{
		long tmp = closed - opened;
		long x = tmp / 1000;
		long seconds = x % 60;
		x /= 60;
		long minutes = x % 60;
		x /= 60;
		long hours = x % 24;
		x /= 24;
		long days = x;
		String timeUntillClose = "<html><b><font color=\"#000\" " +
				"size=\"10\" face=\"Ariel\">הקריאה נסגרה אחרי<br />"+ days + ":" + hours + ":" + minutes + ":" + seconds +"</font></p></html>";
		return timeUntillClose;
	}
	
	/*
	 * The method returns the Time Request created as a Calendar Object
	 */
	public Calendar getTimeStampCal()
	{
		return _CalTimeStamp;
	}
	
	public void printName()
	{
		System.out.println(_requestName);
	}
	
	public void printComment()
	{
		System.out.println(_comment);
	}
	
	/*
	 * The method returns the request Status
	 */
	
	public boolean getStatus()
	{
		return _isActive;
	}
	
	public static void setServerIp(String ip)
	{
		_serverIp = ip;
	}
	
	public static void setServerPort(int port)
	{
		_serverPort = port;
	}
	
	public static String getServerIp()
	{
		return _serverIp;
	}
	
	public static int getServerPort()
	{
		return _serverPort;
	}
	
	public void setActive(boolean b)
	{
		_isActive = b;
	}
	
	public String getID()
	{
		return String.valueOf(_id);
	}
}