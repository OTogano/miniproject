//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;
//Import GUI components
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.IllegalFormatConversionException;
import java.util.InputMismatchException;
import java.util.Locale;

public class Doctor extends JFrame{
    //Initialize doctor interface components
    private JPanel DoctorPanel;
    private JLabel DoctorsLabel;
    private JButton DocExit;
    private JTextField Copyright_message;
    private JTable Medicine_Prescription;
    private JTextField SearchField;
    private JLabel search;
    private DefaultTableModel DocTableModel;

    public Doctor(){
        super("Doctor's interface");//title
        setExtendedState(Doctor.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Disposes frame when close is clicked
        setContentPane(DoctorPanel);//sets up frame access for Pharma class
        DocExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//Disposes frame when button is clicked
            }
        });
        DocTableModel=new DefaultTableModel();
        DocTableModel.setColumnIdentifiers(new Object[]{"Available Medicine","Quantity available"});
        Medicine_Prescription.setModel(DocTableModel);
        //to update connection with database

        DocTableModel.addRow(new Object[]{"pain killers","3"});
        DocTableModel.addRow(new Object[]{"sekrol","3"});
        DocTableModel.addRow(new Object[]{"ozempic","3"});
        DocTableModel.addRow(new Object[]{"panadol","12"});

        JTableHeader header =Medicine_Prescription.getTableHeader();
        Medicine_Prescription.setTableHeader(header);

        SearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String searchQuery =SearchField.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter= new TableRowSorter<>(DocTableModel);
                Medicine_Prescription.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(searchQuery));
            }
        });

        Medicine_Prescription.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    int selectedRow =Medicine_Prescription.getSelectedRow();
                    if (selectedRow>=0){
                        int selectedModelRow = Medicine_Prescription.convertRowIndexToModel(selectedRow);
                        String name = (String) DocTableModel.getValueAt(selectedModelRow,0);
                        String medquantity=(String) DocTableModel.getValueAt(selectedModelRow,1);

                        JDialog inputDialog= new JDialog();
                        inputDialog.setTitle("Add information for "+name);

                        JPanel inputPanel = new JPanel();
                        inputPanel.setLayout(new GridLayout(5,2));

                        JLabel Note =new JLabel("Available quantity: "+medquantity);
                        JTextField frequency =new JTextField(20);
                        JTextField quantity =new JTextField(20);
                        JTextField duration =new JTextField(20);
                        JButton CancelButton =new JButton("Cancel");
                        JButton PurchaseReq =new JButton("Send Purchase Request");

                        inputPanel.add(new JLabel("Dosage quantity"));
                        inputPanel.add(quantity);
                        inputPanel.add(new JLabel("Dosage frequency"));
                        inputPanel.add(frequency);
                        inputPanel.add(new JLabel("Duration of dosage"));
                        inputPanel.add(duration);
                        inputPanel.add(CancelButton);
                        inputPanel.add(PurchaseReq);
                        inputPanel.add(Note);

                        CancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Window window=SwingUtilities.getWindowAncestor(inputPanel);
                                if(window!=null){
                                    window.dispose();}
                            }
                        });

                        PurchaseReq.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    int medQuantity = Integer.parseInt(quantity.getText());
                                    int medMax=Integer.parseInt(medquantity);
                                    int result=medMax-medQuantity;
                                    if (result<0){
                                        JOptionPane.showMessageDialog(null,"Quantity cannot be more than "+medquantity+" ! ");
                                    }else if(result==0){
                                        JOptionPane.showMessageDialog(null,"Request sent to pharmacy");
                                        DocTableModel.removeRow(selectedModelRow);
                                    }
                                    else{String medresult=String.valueOf(result);
                                        JOptionPane.showMessageDialog(null,"Request sent to pharmacy");
                                        DocTableModel.setValueAt(medresult,selectedModelRow,1);
                                    }
                                }catch (NumberFormatException medQuantity){
                                    JOptionPane.showMessageDialog(null,"Quantity is null or not a number!");
                                }
                                Window window=SwingUtilities.getWindowAncestor(inputPanel);
                                if(window!=null){
                                    window.dispose();}
                            }
                        });
                        inputDialog.add(inputPanel);
                        inputDialog.pack();
                        inputDialog.setVisible(true);
                    }
                }
            }
        });
    }
}
