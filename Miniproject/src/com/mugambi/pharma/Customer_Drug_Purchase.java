//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;

public class Customer_Drug_Purchase extends JFrame {
    private JLabel PurchaseLabel;
    private JButton backButton;
    private JTextField Copyright_message;
    private JPanel Customer_Purchase;
    private JTable PurchaseTable;
    private JButton searchButton;
    private JTextField SearchField;
    private JLabel search;
    private DefaultTableModel PurchaseModel;

    public Customer_Drug_Purchase(){
        super("Pharma");//title
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Disposes frame when close is clicked
        setContentPane(Customer_Purchase);//sets up frame access for Pharma class
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//Disposes frame when button is clicked
            }
        });
        PurchaseModel=new DefaultTableModel();
        PurchaseModel.setColumnIdentifiers(new Object[]{"Medicine name","Dosage quantity","Duration of dosage","Dosage price"});
        PurchaseTable.setModel(PurchaseModel);
        //to update connection with database

        PurchaseModel.addRow(new Object[]{"pain killers","3","1 week","750$"});
        PurchaseModel.addRow(new Object[]{"sekrol","3","5 days","100$"});
        PurchaseModel.addRow(new Object[]{"ozempic","3","7 days","200$"});
        PurchaseModel.addRow(new Object[]{"panadol","12","10 days","900$"});

        JTableHeader header=PurchaseTable.getTableHeader();
        PurchaseTable.setTableHeader(header);

        SearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String searchQuery =SearchField.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter= new TableRowSorter<>(PurchaseModel);
                PurchaseTable.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(searchQuery));
            }
        });

//
//        PurchaseTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if(e.getClickCount()==1){
//                    int selectedRow =PurchaseTable.getSelectedRow();
//                    if(selectedRow>=0){
//                    JPanel inputPanel =new JPanel();
//                    inputPanel.setLayout(new GridLayout)
//                }
//
//
//                }
//            }
//        });
    }
}
