import java.awt.ComponentOrientation;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/* This Class is a generic DialogBox - set to receive input in hebrew locale
 * it is called like this :  MyDialog newStatusDialog = new MyDialog("הכנס סטטוס חדש");
 * when the status will be the input demand text.
 * status will return the input text, and the okWasPressed(Boolean) will tell if 'OK' was pressed or close;
 */

public class MyDialog{
    
    String status = "";
    Boolean okWasPressed = false;

    MyDialog(String title, String textToEdit) {
    	
    	Locale loc = new Locale("iw", "IL");
        String message = "<html><b><font color=\"#8F0000\" size=\"10\" face=\"Ariel\">" + title + "</font></p></html>";

        JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        pane.setWantsInput(true);
//        pane.setInputValue(textToEdit);
        
        JDialog dialog = pane.createDialog(null, UIManager.getString("OptionPane.inputDialogTitle", loc));
        dialog.getInputContext().selectInputMethod(loc); // pane.getInputContext... also works
        pane.setInitialSelectionValue(textToEdit);
        pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        dialog.setVisible(true);
        dialog.dispose();
        

        String response = (String) pane.getInputValue();
        if (response == JOptionPane.UNINITIALIZED_VALUE)
        	okWasPressed = false;
        else
        {
        	okWasPressed = true;
        	status = (String) pane.getInputValue();
        }
    }
}