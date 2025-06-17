import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;


@SuppressWarnings("serial")
public class requestLogLine extends JPanel implements MouseListener{

	Request _thisLineRequest;
	JButton closeButton;
	Dimension ipdim = new Dimension(160, 30);
	Dimension compdim = new Dimension(160, 30);
	Dimension commentDim = new Dimension(500, 30);
	
	public requestLogLine(Request srvReq) {
		super(new BorderLayout());
		_thisLineRequest = srvReq;
		
		JPanel centerPanel = new JPanel();
		JPanel requestNameAndTime = new JPanel();
		JPanel requestTime = new JPanel();
		JLabel requestCompName= new JLabel(_thisLineRequest._computerName);
		JLabel requestCompIp = new JLabel(_thisLineRequest._computerIp);
		JLabel techComment = new JLabel("הערה");
		JLabel requestID = new JLabel("ID");
		JLabel requestName = new JLabel(_thisLineRequest._requestName);
		JLabel requestHour = new JLabel(_thisLineRequest.getHourTimeFormat());
		JLabel requestDay = new JLabel(_thisLineRequest.getDayTimeFormat());
		JLabel requestComment;
			
		UIManager.put("ToolTip.background", Color.RED);
		
		this.setMinimumSize(new Dimension(1355,30));
		this.setPreferredSize(new Dimension(1355,30));
		this.setMaximumSize(new Dimension(1655,30));
		this.setOpaque(true);
		this.setVisible(true);
		
		if (srvReq._comment.length() < 3) requestComment = new JLabel("ללא הערה");
		else
			{
			requestComment = new JLabel(srvReq._comment);
			requestComment.setToolTipText("<html><b><font color=\"#000\"size=\"6\" face=\"Ariel\">" + srvReq._comment + "</font></p></html>");
			}

		requestName.setForeground(Color.pink);
//		requestName.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		requestName.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		requestName.setFont(new Font("Ariel", Font.BOLD, 22));
		requestName.setMinimumSize( new Dimension(200, 30));
		requestName.setPreferredSize( new Dimension(200, 30));
		requestName.setMaximumSize( new Dimension(200, 30));
		
		requestCompIp.setBorder(BorderFactory.createLineBorder(Color.black));
		requestCompIp.setAlignmentX(CENTER_ALIGNMENT);
		requestCompIp.setFont(new Font("Ariel", Font.PLAIN, 20));
		requestCompIp.setMinimumSize(ipdim);
		requestCompIp.setPreferredSize(ipdim);
		requestCompIp.setMaximumSize(ipdim);
		
		requestID.setBorder(BorderFactory.createLineBorder(Color.black));
		requestID.setForeground(Color.BLACK);
		requestID.setFont(new Font("Ariel", Font.PLAIN, 20));
		requestID.setMinimumSize( new Dimension(60, 30));
		requestID.setPreferredSize( new Dimension(60, 30));
		requestID.setMaximumSize( new Dimension(60, 30));
		requestID.setText(srvReq.getID());
		
		requestComment.setBorder(BorderFactory.createLineBorder(Color.black));
		requestComment.setAlignmentX(RIGHT_ALIGNMENT);
		requestComment.setFont(new Font("Ariel", Font.PLAIN, 22));
		requestComment.setMinimumSize(commentDim);
		requestComment.setPreferredSize(commentDim);
		requestComment.setMaximumSize(commentDim);
		requestComment.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				
		techComment.setBorder(BorderFactory.createLineBorder(Color.black));
		techComment.setAlignmentX(LEFT_ALIGNMENT);
		techComment.setFont(new Font("Ariel", Font.PLAIN, 18));
		techComment.setMinimumSize(new Dimension(45, 30));
		techComment.setPreferredSize(new Dimension(45, 30));
		techComment.setMaximumSize(new Dimension(45, 30));
		techComment.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		techComment.setToolTipText("<html><b><font color=\"#000\" " +
				"size=\"10\" face=\"Ariel\">"+ _thisLineRequest.getTechComment() +"</font></p></html>");
		if (_thisLineRequest.getTechComment().length() > 3) techComment.setForeground(Color.red);
		else techComment.setForeground(Color.GRAY);

		requestCompName.setBorder(BorderFactory.createLineBorder(Color.black));
		requestCompName.setAlignmentX(LEFT_ALIGNMENT);
		requestCompName.setMinimumSize(compdim);
		requestCompName.setPreferredSize(compdim);
		requestCompName.setMaximumSize(compdim);
		requestCompName.setFont(new Font("Ariel", Font.BOLD, 18));
		
		BoxLayout nameAndTimeLayout = new BoxLayout(requestNameAndTime,BoxLayout.X_AXIS);
		
		requestNameAndTime.add(requestName);
		requestNameAndTime.add(Box.createRigidArea(new Dimension(10, 0)));		
		requestNameAndTime.add(createTimeBox(requestTime, requestHour,requestDay,_thisLineRequest));
		requestNameAndTime.add(requestID);
		
		requestNameAndTime.setLayout(nameAndTimeLayout);
		requestNameAndTime.setBackground(Color.white);
		requestNameAndTime.setOpaque(true);
		requestNameAndTime.setToolTipText(_thisLineRequest.calculateTimePassed());
		requestNameAndTime.addMouseListener(this);
		requestNameAndTime.setMinimumSize( new Dimension(410, 30));
		requestNameAndTime.setPreferredSize( new Dimension(410, 30));
		requestNameAndTime.setMaximumSize( new Dimension(410, 30));
		
		if (_thisLineRequest._urgency == 2)
		{ 
			centerPanel.setBackground(Color.ORANGE);	
		}
		else if (_thisLineRequest._urgency < 2)
		{
//			centerPanel.setBackground(Color.GREEN);
		}
		else if (_thisLineRequest._urgency == 3)
		{
			centerPanel.setBackground(Color.RED);
		}
		
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.X_AXIS));
		centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		centerPanel.add(requestCompName);
		centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		centerPanel.add(requestCompIp);
		centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		centerPanel.add(requestComment);
		centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
				
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(techComment,BorderLayout.WEST);
		this.add(centerPanel,BorderLayout.CENTER);
		this.add(requestNameAndTime,BorderLayout.EAST);
		this.setVisible(true);
	}
	private JPanel createTimeBox(JPanel requestTime, JLabel requestHour,
			JLabel requestDay,Request newRequest) {
		
			requestHour.setAlignmentY(CENTER_ALIGNMENT);
			requestHour.setAlignmentX(CENTER_ALIGNMENT);
			requestHour.setFont(new Font("Ariel", Font.BOLD, 22));
			requestHour.setForeground(Color.PINK);
			requestDay.setAlignmentY(CENTER_ALIGNMENT);
			requestDay.setAlignmentX(CENTER_ALIGNMENT);
			requestDay.setFont(new Font("Ariel", Font.PLAIN, 20));
			requestDay.setForeground(Color.pink);
			
			requestTime.setLayout(new BoxLayout(requestTime,BoxLayout.X_AXIS));
			requestTime.setBackground(Color.WHITE);
			requestTime.add(requestDay);
			requestTime.add(Box.createRigidArea(new Dimension(10, 0)));
			requestTime.add(requestHour);
			requestTime.setBorder(BorderFactory.createLineBorder(Color.black));
			requestTime.setMinimumSize( new Dimension(155, 30));
			requestTime.setPreferredSize( new Dimension(155, 30));
			requestTime.setMaximumSize( new Dimension(155, 30));
			
			return requestTime;
					
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		((JPanel)(arg0.getSource())).setToolTipText(getToolTipText());
		
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getToolTipText()
	{
		System.out.println(_thisLineRequest.calculateTimePassed());
		return _thisLineRequest.calculateOpenFor(_thisLineRequest._TimeStamp, _thisLineRequest._closingTimeStamp);
		}
}