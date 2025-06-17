import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class About extends JDialog {
	
  public About(JFrame parent) {
    super(parent, "About Dialog", true);
	
    Box b = Box.createVerticalBox();
    
    b.add(Box.createGlue());
    b.add(new JLabel("ניהול קריאות שירות - קליינט מנהל"));   // A line of text in the Box
    b.add(new JLabel("המחלקה הטכנית - אולפני מימד"));
    b.add(new JLabel("25/08/2014"));
    b.add(new JLabel("גרסא זו נכתבה על ידי דודו גדעוני לשימוש אולפני מימד"));
    b.add(new JLabel("השימוש מותר עד 12/2014"));
    b.add(new JLabel("  "));
    b.add(new JLabel("כל זכויות השימוש וקוד המקור שמורות לדודו גדעוני ©"));
    b.add(new JLabel("אין לבצע כל שינוי בקוד או בתוכנה עצמה ללא אישור בכתב מדודו גדעוני"));
    b.add(new JLabel("  "));
    b.add(new JLabel("גרסא 1.2"));
    b.add(new JLabel("  "));
    b.add(new JLabel("© All Usage/SourceCode Rights Reserved to David Gidony"));
    b.add(new JLabel("Contact: david.gidony@gmail.com"));
    b.add(Box.createGlue());
    getContentPane().add(b, "Center");
    b.setBackground(Color.YELLOW);
    JPanel p2 = new JPanel();             // A Panel for the OK button
    JButton ok = new JButton("Ok");
    ok.setMnemonic(KeyEvent.VK_K);
    p2.add(ok);
    p2.setOpaque(true);
    getContentPane().add(p2, "South");

    b.setOpaque(true);
    
    ok.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent evt) {
        setVisible(false);
    }
    }
    );
    setSize(370, 310);
 }
}    