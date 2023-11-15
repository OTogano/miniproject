//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer_Drug_Purchase extends JFrame {
    private JLabel PurchaseLabel;
    private JButton backButton;
    private JPanel Customer_Purchase;
    private JTable PurchaseTable;
    private JButton searchButton;
    private JTextField SearchField;
    private JLabel search;
    private DefaultTableModel PurchaseModel;

    DatabaseService connector=new DatabaseService();

    public Customer_Drug_Purchase(){
        super("Pharma");//title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Disposes frame when close is clicked
        setContentPane(Customer_Purchase);//sets up frame access for Pharma class
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pharmacist navigation =new Pharmacist();
                navigation.getContentPane();
                navigation.setExtendedState(Pharmacist.MAXIMIZED_BOTH);
                navigation.setVisible(true);
                dispose();//Disposes frame when button is clicked
            }
        });
        PurchaseModel=new DefaultTableModel();
        PurchaseTable.setModel(PurchaseModel);

        try{
            String SelectPurchaseTableQuery="SELECT * FROM db_drugpurchase";
            ResultSet rs=connector.executeSelectQuery(SelectPurchaseTableQuery);
            int columnCount=rs.getMetaData().getColumnCount();
            for(int i=1; i<= columnCount; i++){
                PurchaseModel.addColumn(rs.getMetaData().getColumnName(i));
            }
            while (rs.next()){
                Object[] rowData =new Object[columnCount];
                for(int i=1; i<= columnCount; i++){
                    rowData[i - 1]=rs.getObject(i);
                }
                PurchaseModel.addRow(rowData);
        }
        }catch (SQLException e) {
            System.out.println("Select PURCHASE ERROR");
        }
        SearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String searchQuery =SearchField.getText();
                TableRowSorter<DefaultTableModel> sorter= new TableRowSorter<>(PurchaseModel);
                PurchaseTable.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(searchQuery));
            }
        });

        PurchaseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = PurchaseTable.getSelectedRow();
                if (e.getClickCount() == 1 && selectedRow >= 0) {
                    int selectedModelRow = PurchaseTable.convertRowIndexToModel(selectedRow);
                    int patient_id = (int) PurchaseModel.getValueAt(selectedModelRow, 0);
                    String patient_name = (String) PurchaseModel.getValueAt(selectedModelRow, 1);
                    int prescription_id = (int) PurchaseModel.getValueAt(selectedModelRow, 2);
                    String dosage_quantity = (String) PurchaseModel.getValueAt(selectedModelRow, 3);
                    String dosage_frequency = (String) PurchaseModel.getValueAt(selectedModelRow, 4);
                    String dosage_duration = (String) PurchaseModel.getValueAt(selectedModelRow, 5);
                    double grosscost = (double) PurchaseModel.getValueAt(selectedModelRow, 7);
                    double insurance = (double) PurchaseModel.getValueAt(selectedModelRow, 8);


                    JDialog inputDialog = new JDialog();
                    inputDialog.setTitle("Edit information");

                    JPanel inputPanel = new JPanel();
                    inputPanel.setLayout(new GridLayout(11, 2));

                    JTextField patid = new JTextField(String.valueOf(patient_id));
                    JTextField patname = new JTextField(patient_name, 20);
                    JTextField preid = new JTextField(String.valueOf(prescription_id));
                    JTextField dosquant = new JTextField(dosage_quantity, 20);
                    JTextField dosfreq = new JTextField(dosage_frequency, 20);
                    JTextField dosdur = new JTextField(dosage_duration, 20);
                    JTextField grossprice = new JTextField(String.valueOf(grosscost));
                    JTextField ins = new JTextField(String.valueOf(insurance));

                    JButton deleteButton = new JButton("Delete");
                    JButton SaveChanges = new JButton("Save Changes");
                    JButton CancelButton = new JButton("Cancel");

                    inputPanel.add(new JLabel("Patient ID"));
                    inputPanel.add(patid);
                    inputPanel.add(new JLabel("Patient Name"));
                    inputPanel.add(patname);
                    inputPanel.add(new JLabel("Prescription ID"));
                    inputPanel.add(preid);
                    inputPanel.add(new JLabel("Dosage Quantity"));
                    inputPanel.add(dosquant);
                    inputPanel.add(new JLabel("Dosage Frequency"));
                    inputPanel.add(dosfreq);
                    inputPanel.add(new JLabel("Duration Of Dosage"));
                    inputPanel.add(dosdur);
                    inputPanel.add(new JLabel("Gross Cost"));
                    inputPanel.add(grossprice);
                    inputPanel.add(new JLabel("Medical Insurance"));
                    inputPanel.add(ins);
                    inputPanel.add(deleteButton);
                    inputPanel.add(SaveChanges);
                    inputPanel.add(CancelButton);

                    CancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Window window = SwingUtilities.getWindowAncestor(inputPanel);
                            if (window != null) {
                                window.dispose();
                            }
                        }
                    });

                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            connector.deleteRowPurchaseTable(prescription_id);
                            PurchaseModel.removeRow(selectedModelRow);
                            Window window = SwingUtilities.getWindowAncestor(inputPanel);
                            if (window != null) {
                                window.dispose();
                            }
                        }
                    });
                    SaveChanges.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int purPAT_ID = Integer.parseInt(patid.getText());
                            String purPAT_NAME = patname.getText();
                            int purPRES_ID = Integer.parseInt(preid.getText());
                            String purquant = dosquant.getText();
                            String purfreq = dosfreq.getText();
                            String purdur = dosdur.getText();
                            double gross = Double.parseDouble(grossprice.getText());
                            double insu = Double.parseDouble(ins.getText());
                            connector.UpdateRowPurchaseTable(purPAT_ID, purPAT_NAME, purPRES_ID, purquant, purfreq, purdur, gross, insu);
                            PurchaseModel.setValueAt(purPAT_ID, selectedModelRow, 0);
                            PurchaseModel.setValueAt(purPAT_NAME, selectedModelRow, 1);
                            PurchaseModel.setValueAt(purPRES_ID, selectedModelRow, 2);
                            PurchaseModel.setValueAt(purquant, selectedModelRow, 3);
                            PurchaseModel.setValueAt(purfreq, selectedModelRow, 4);
                            PurchaseModel.setValueAt(purdur, selectedModelRow, 5);
                            PurchaseModel.setValueAt(gross, selectedModelRow, 7);
                            PurchaseModel.setValueAt(insu, selectedModelRow, 8);
                            Window window = SwingUtilities.getWindowAncestor(inputPanel);
                            if (window != null) {
                                window.dispose();
                            }
                        }
                    });
                    inputDialog.add(inputPanel);
                    inputDialog.pack();
                    inputDialog.setVisible(true);
                }
            }
        });
        }
    }

