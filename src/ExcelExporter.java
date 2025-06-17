

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelExporter {

static File dirLocationSave = new java.io.File(".");
static JTable _table;

    public ExcelExporter(JTable table) {
    	_table = table;
    	      
            	 JFileChooser chooser = new JFileChooser();
            	 chooser.setCurrentDirectory(dirLocationSave);
            	 chooser.setDialogTitle("select folder");
            	 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            	 chooser.setAcceptAllFileFilterUsed(false);
            	 int xxx = chooser.showDialog(null, "Save to Dir");
            	 
            	 if (xxx == JFileChooser.APPROVE_OPTION)
            	 {	 
            	 dirLocationSave =  chooser.getSelectedFile();
            	 SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy HHmmss");
            	 Date date = new Date();
            	 System.out.println(chooser.getCurrentDirectory());
            	 
                try {
                    fillData(_table, new File(chooser.getSelectedFile() + "\\RequestLog - " + dateFormat.format(date) + ".xls"));
                    JOptionPane.showMessageDialog(null, "Data saved at " +
                    		chooser.getSelectedFile() + "\\RequestLog - " + dateFormat.format(date) + ".xls successfully", "Message",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            	 }
            }
        
    void fillData(JTable table, File file) {

        try {

            WritableWorkbook workbook1 = Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0); 
            TableModel model = table.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                Label column = new Label(i, 0, model.getColumnName(i));
                sheet1.addCell(column);
            }
            int j = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                for (j = 0; j < model.getColumnCount(); j++) {
                    Label row = new Label(j, i + 1, 
                            model.getValueAt(i, j).toString());
                    sheet1.addCell(row);
                }
            }
            workbook1.write();
            workbook1.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
