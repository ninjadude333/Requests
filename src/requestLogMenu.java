import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

// This Class Creates the menu bar
@SuppressWarnings("serial")
public class requestLogMenu extends JPanel implements ActionListener{
	JTextArea output;
    JScrollPane scrollPane;
    JMenuItem _menuSearch,_menuPrint,_menuExcel,_menuExit;
                
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
                
        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription("File Menu");
        menuBar.add(menu);

        //a group of JMenuItems
        _menuSearch = new JMenuItem("Search");
        _menuSearch.setMnemonic(KeyEvent.VK_S);
        _menuSearch.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        _menuSearch.getAccessibleContext().setAccessibleDescription(
                "Search for Logs");
        _menuSearch.setEnabled(true);
        _menuSearch.addActionListener(this);
        menu.add(_menuSearch);
        
        _menuPrint = new JMenuItem("Print");
        _menuPrint.setMnemonic(KeyEvent.VK_P);
        _menuPrint.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        _menuPrint.getAccessibleContext().setAccessibleDescription(
                "Print");
        _menuPrint.setEnabled(false);
        _menuPrint.addActionListener(this);
        menu.add(_menuPrint);
        
        _menuExcel = new JMenuItem("Explort to Excel");
        _menuExcel.setMnemonic(KeyEvent.VK_X);
        _menuExcel.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        _menuExcel.getAccessibleContext().setAccessibleDescription(
        		"Launch RequestLog");
        _menuExcel.setEnabled(false);
        _menuExcel.addActionListener(this);
        menu.add(_menuExcel);
        
        _menuExit = new JMenuItem("Exit",KeyEvent.VK_X);
        _menuExit.setMnemonic(KeyEvent.VK_X);
        _menuExit.addActionListener(this);
        menu.add(_menuExit);
              
        
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
			DatePicker.frame.dispose();
			break;
		case "Search":
			DatePicker.seek.doClick();
			break;
		case "Print":
			DatePicker.printBtn.doClick();
			break;
		case "Explort to Excel":
			DatePicker.exportEXLBtn.doClick();
			break;
		default:
			break;
		}
	}
	

}