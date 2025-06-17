

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

        @SuppressWarnings("serial")
        public class CreateTableForExcel extends JFrame {

            private JTable gradesTable;
            static Object[][] requestPrintData ;
        
            public CreateTableForExcel(String[][] results) {
                super("Table Printing Demo 1");

                requestPrintData = results;
                gradesTable = createTable(new GradesModel());
                gradesTable.setFillsViewportHeight(true);
                gradesTable.setRowHeight(24);
                gradesTable.setAutoCreateRowSorter(true);
                adjustColumnPreferredWidths(gradesTable);
                
                new ExcelExporter(gradesTable);
            }
            
            public static void adjustColumnPreferredWidths(JTable table) {
            	
            	TableColumnModel columnModel = table.getColumnModel();
            	for (int col = 0; col < table.getColumnCount(); col++) {

            	int maxwidth = 0;
            	for (int row = 0; row < table.getRowCount(); row++) {
            	TableCellRenderer rend = table.getCellRenderer(row, col);
            	Object value = table.getValueAt(row, col);
            	Component comp = rend.getTableCellRendererComponent(table,
            	value, false, false, row, col);
            	maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
            	} 

            	TableColumn column = columnModel.getColumn(col);
            	column.setPreferredWidth(maxwidth);
            	} 
            	}
            
            protected JTable createTable(TableModel model) {
            	JTable tmp = new JTable(model);
            	
            	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            	rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
            	tmp.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );
            	tmp.getColumnModel().getColumn(4).setCellRenderer( rightRenderer );
            	tmp.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );
            	
                return tmp;
            }

            private static class GradesModel implements TableModel {
                
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
                public void addTableModelListener(TableModelListener l) {}
                public void removeTableModelListener(TableModelListener l) {}
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                public Class<?> getColumnClass(int col) {
                    switch(col) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        	return String.class;                        
                    }

                    throw new AssertionError("invalid column");
                }
                
                public int getRowCount() {
                    return requestPrintData.length;
                }
                
                public int getColumnCount() {
                    return 7;
                }
                
                public String getColumnName(int col) {
                    switch(col) {
                    	case 0: return "ID";
                        case 1: return "Date";
                        case 2: return "Time";
                        case 3: return "Sender";
                        case 4: return "Type";
                        case 5: return "Comment";
                        case 6: return "Closing Comment";     
                    }
                   
                    throw new AssertionError("invalid column");
                }
                
                public Object getValueAt(int row, int col) {
                    switch(col) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            return requestPrintData[row][col];
                        
                    }

                    throw new AssertionError("invalid column");
                }
            }
        }