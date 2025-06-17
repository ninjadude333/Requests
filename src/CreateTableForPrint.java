

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

        @SuppressWarnings("serial")
        public class CreateTableForPrint extends JFrame {
        	
            private JPanel contentPane;
            private JLabel gradesLabel;
            private JTable gradesTable;
            private JScrollPane scroll;
            private JCheckBox showPrintDialogBox;
            private JCheckBox interactiveBox;
            private JCheckBox fitWidthBox;
            private JButton printButton;
            static Object[][] requestPrintData ;

            /* Protected so that they can be modified/disabled by subclasses */
            protected JCheckBox headerBox;
            protected JCheckBox footerBox;
            protected JTextField headerField;
            protected JTextField footerField;
        
            public CreateTableForPrint(String[][] results) {
                super("Print RequestLog");

                gradesLabel = new JLabel("RequestLOG Output");
                gradesLabel.setFont(new Font("Dialog", Font.BOLD, 16));
                requestPrintData = results;
                gradesTable = createTable(new GradesModel());
                gradesTable.setFillsViewportHeight(true);
                gradesTable.setRowHeight(24);
                gradesTable.setAutoCreateRowSorter(true);
                scroll = new JScrollPane(gradesTable);
                
                adjustColumnPreferredWidths(gradesTable);

                String tooltipText;

                tooltipText = "Include a page header";
                headerBox = new JCheckBox("Header:", true);
                headerBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        headerField.setEnabled(headerBox.isSelected());
                    }
                });
                headerBox.setToolTipText(tooltipText);
                tooltipText = "Page Header (Use {0} to include page number)";
                headerField = new JTextField("RequestLOG Output");
                headerField.setToolTipText(tooltipText);

                tooltipText = "Include a page footer";
                footerBox = new JCheckBox("Footer:", true);
                footerBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        footerField.setEnabled(footerBox.isSelected());
                    }
                });
                footerBox.setToolTipText(tooltipText);
                tooltipText = "Page Footer (Use {0} to Include Page Number)";
                footerField = new JTextField("Page {0}");
                footerField.setToolTipText(tooltipText);

                tooltipText = "Show the Print Dialog Before Printing";
                showPrintDialogBox = new JCheckBox("Show print dialog", true);
                showPrintDialogBox.setToolTipText(tooltipText);
                showPrintDialogBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        if (!showPrintDialogBox.isSelected()) {
                            JOptionPane.showMessageDialog(
                                CreateTableForPrint.this,
                                "If the Print Dialog is not shown,"
                                    + " the default printer is used.",
                                "Printing Message",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                
                tooltipText = "Keep the GUI Responsive and Show a Status Dialog During Printing";
                interactiveBox = new JCheckBox("Interactive (Show status dialog)", true);
                interactiveBox.setToolTipText(tooltipText);
                interactiveBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        if (!interactiveBox.isSelected()) {
                            JOptionPane.showMessageDialog(
                                CreateTableForPrint.this,
                                "If non-interactive, the GUI is fully blocked"
                                    + " during printing.",
                                "Printing Message",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                tooltipText = "Shrink the Table to Fit the Entire Width on a Page";
                fitWidthBox = new JCheckBox("Fit width to printed page", true);
                fitWidthBox.setToolTipText(tooltipText);

                tooltipText = "Print the Table";
                printButton = new JButton("Print");
                printButton.setToolTipText(tooltipText);
                
                printButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        printLogTable();
                    }
                });

                contentPane = new JPanel();
                addComponentsToContentPane();
                setContentPane(contentPane);

                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSize(1024, 860);
                setLocationRelativeTo(null);
                
                this.setVisible(true);   
            }
            
            public static void adjustColumnPreferredWidths(JTable table) {
            	// strategy - get max width for cells in column and
            	// make that the preferred width
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

            /**
             * Adds to and lays out all GUI components on the {@code contentPane} panel.
             * <p>
             * It is recommended that you <b>NOT</b> try to understand this code. It was
             * automatically generated by the NetBeans GUI builder.
             * 
             */
            private void addComponentsToContentPane() {
                JPanel bottomPanel = new JPanel();
                bottomPanel.setBorder(BorderFactory.createTitledBorder("Printing"));

                GroupLayout bottomPanelLayout = new GroupLayout(bottomPanel);
                bottomPanel.setLayout(bottomPanelLayout);
                bottomPanelLayout.setHorizontalGroup(
                    bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(bottomPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(headerBox)
                            .addComponent(footerBox))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(footerField)
                            .addComponent(headerField, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(bottomPanelLayout.createSequentialGroup()
                                .addComponent(fitWidthBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(printButton))
                            .addGroup(bottomPanelLayout.createSequentialGroup()
                                .addComponent(showPrintDialogBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(interactiveBox)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                bottomPanelLayout.setVerticalGroup(
                    bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(bottomPanelLayout.createSequentialGroup()
                        .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(headerBox)
                            .addComponent(headerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(interactiveBox)
                            .addComponent(showPrintDialogBox))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(footerBox)
                            .addComponent(footerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(fitWidthBox)
                            .addComponent(printButton))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                GroupLayout layout = new GroupLayout(contentPane);
                contentPane.setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                            .addComponent(gradesLabel)
                            .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(gradesLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );
            }

            /**
             * Create and return a table for the given model.
             * <p>
             * This is protected so that a subclass can return an instance
             * of a different {@code JTable} subclass. This is interesting
             * only for {@code TablePrintDemo3} where we want to return a
             * subclass that overrides {@code getPrintable} to return a
             * custom {@code Printable} implementation.
             */
            protected JTable createTable(TableModel model) {
            	JTable tmp = new JTable(model);
            	
            	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            	rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
            	tmp.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );
            	tmp.getColumnModel().getColumn(4).setCellRenderer( rightRenderer );
            	tmp.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );
            	
                return tmp;
            }
            
            /**
             * Print the grades table.
             */
            private void printLogTable() {
                /* Fetch printing properties from the GUI components */

                MessageFormat header = null;
                
                /* if we should print a header */
                if (headerBox.isSelected()) {
                    /* create a MessageFormat around the header text */
                    header = new MessageFormat(headerField.getText());
                }

                MessageFormat footer = null;
                
                /* if we should print a footer */
                if (footerBox.isSelected()) {
                    /* create a MessageFormat around the footer text */
                    footer = new MessageFormat(footerField.getText());
                }

                boolean fitWidth = fitWidthBox.isSelected();
                boolean showPrintDialog = showPrintDialogBox.isSelected();
                boolean interactive = interactiveBox.isSelected();

                /* determine the print mode */
                JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH
                                                 : JTable.PrintMode.NORMAL;

                try {
                    /* print the table */
                    boolean complete = gradesTable.print(mode, header, footer,
                                                         showPrintDialog, null,
                                                         interactive, null);

                    /* if printing completes */
                    if (complete) {
                        /* show a success message */
                        JOptionPane.showMessageDialog(this,
                                                      "Printing Complete",
                                                      "Printing Result",
                                                      JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        /* show a message indicating that printing was cancelled */
                        JOptionPane.showMessageDialog(this,
                                                      "Printing Cancelled",
                                                      "Printing Result",
                                                      JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (PrinterException pe) {
                    /* Printing failed, report to the user */
                    JOptionPane.showMessageDialog(this,
                                                  "Printing Failed: " + pe.getMessage(),
                                                  "Printing Result",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }

            /**
             * A table model containing student grades.
             */
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