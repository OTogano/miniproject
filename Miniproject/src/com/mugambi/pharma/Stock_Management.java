//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Stock_Management extends JFrame {
    private JPanel Stock_Management;
    private JButton backButton;
    private JLabel StockLabel;
    private JTable stock_table;
    private JTextField SearchField;
    private JScrollPane StockTablePane;
    private JButton AddButton;
    private JLabel search;
    private DefaultTableModel StockTableModel;
    DatabaseService connector =new DatabaseService();
    public Stock_Management(){
        super("Pharma");//title
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Disposes frame when close is clicked
        setContentPane(Stock_Management);//sets up frame access for Pharma class
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
        StockTableModel=new DefaultTableModel();
        stock_table.setModel(StockTableModel);
        try {
            String SelectStockTableQuery="SELECT * FROM db_stockmanagement";
            ResultSet rs = connector.executeSelectQuery(SelectStockTableQuery);
        int columnCount=rs.getMetaData().getColumnCount();
        for(int i=1; i<= columnCount; i++){
            StockTableModel.addColumn(rs.getMetaData().getColumnName(i));
        }
        while (rs.next()){
            Object[] rowData =new Object[columnCount];
            for(int i=1; i<= columnCount; i++){
                rowData[i - 1]=rs.getObject(i);
            }
            StockTableModel.addRow(rowData);
        }
        }catch (SQLException e){
            System.out.println("Select STOCK ERROR");
        }
        SearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String searchQuery =SearchField.getText();
                TableRowSorter<DefaultTableModel> sorter= new TableRowSorter<>(StockTableModel);
                sorter.setRowFilter(RowFilter.regexFilter(searchQuery));
                stock_table.setRowSorter(sorter);

            }
        });

        stock_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int selectedRow = stock_table.getSelectedRow();
                if (e.getClickCount()==1 && selectedRow>= 0){
                    int selectedModelRow =stock_table.convertRowIndexToModel(selectedRow);
                    int med_id = (int) StockTableModel.getValueAt(selectedModelRow,0);
                    String med_name=(String)StockTableModel.getValueAt(selectedModelRow,1);
                    int med_quantity=(int)StockTableModel.getValueAt(selectedModelRow,2);
                    int med_batch=(int)StockTableModel.getValueAt(selectedModelRow,3);
                    double med_price=(double)StockTableModel.getValueAt(selectedModelRow,4);
                    Date med_exp=(Date) StockTableModel.getValueAt(selectedModelRow,5);

                    JDialog inputDialog = new JDialog();
                    inputDialog.setTitle("Edit information");

                    JPanel inputPanel =new JPanel();
                    inputPanel.setLayout(new GridLayout(9,2));

                    JTextField medID= new JTextField(String.valueOf(med_id));
                    JTextField medname =new JTextField(med_name,20);
                    JTextField quantity=new JTextField(String.valueOf(med_quantity));
                    JTextField batch=new JTextField(String.valueOf(med_batch));
                    JTextField price=new JTextField(String.valueOf(med_price));
                    JTextField exp=new JTextField(String.valueOf(med_exp));
                    JButton deleteButton =new JButton("Delete");
                    JButton SaveChanges =new JButton("Save Changes");
                    JButton CancelButton =new JButton("Cancel");

                    inputPanel.add(new JLabel("Medicine ID"));
                    inputPanel.add(medID);
                    inputPanel.add(new JLabel("Medicine name"));
                    inputPanel.add(medname);
                    inputPanel.add(new JLabel("Quantity available"));
                    inputPanel.add(quantity);
                    inputPanel.add(new JLabel("Batch number"));
                    inputPanel.add(batch);
                    inputPanel.add(new JLabel("Price"));
                    inputPanel.add(price);
                    inputPanel.add(new JLabel("Expiry date"));
                    inputPanel.add(exp);
                    inputPanel.add(deleteButton);
                    inputPanel.add(SaveChanges);
                    inputPanel.add(CancelButton);

                    CancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Window window=SwingUtilities.getWindowAncestor(inputPanel);
                            if(window!=null){
                                window.dispose();}
                        }
                    });

                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            connector.deleteRowStockTable(med_id);
                            StockTableModel.removeRow(selectedModelRow);
                            Window window = SwingUtilities.getWindowAncestor(inputPanel);
                            if (window != null) {
                                window.dispose();
                            }
                        }
                    });

                    SaveChanges.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                            int MED_ID= Integer.parseInt(medID.getText());
                            String MEDNAME=medname.getText();
                            int QUANTITY= Integer.parseInt(quantity.getText());
                            int BATCH=Integer.parseInt(batch.getText());
                            double PRICE=Double.parseDouble(price.getText());
                            Date EXP=Date.valueOf(exp.getText());
                            connector.UpdateRowStockTable(MED_ID,MEDNAME,QUANTITY,BATCH,PRICE,EXP);
                            StockTableModel.setValueAt(MED_ID,selectedModelRow,0);
                            StockTableModel.setValueAt(MEDNAME,selectedModelRow,1);
                            StockTableModel.setValueAt(QUANTITY,selectedModelRow,2);
                            StockTableModel.setValueAt(BATCH,selectedModelRow,3);
                            StockTableModel.setValueAt(PRICE,selectedModelRow,4);
                            StockTableModel.setValueAt(EXP,selectedModelRow,5);
                            Window window=SwingUtilities.getWindowAncestor(inputPanel);
                            if(window!=null){
                                window.dispose();}}catch (Exception A){
                                JOptionPane.showMessageDialog(null,"Please Ensure all Fields are filled in their correct format");
                            }
                        }
                    });
                    inputDialog.add(inputPanel);
                    inputDialog.setLocationRelativeTo(null);
                    inputDialog.pack();
                    inputDialog.setVisible(true);
                }
            }
        });

        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEditDialog("","","","","","",-1);
            }
        });

    }
    private void createEditDialog(String medid,String medicineName, String medicineQuantity, String medicineBatchNumber,String medprice ,String medexp,int rowIndex){
        JDialog AddDialog =new JDialog();
        AddDialog.setTitle("Add new Item");

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(8,2));

        JTextField medID= new JTextField(medid,20);
        JTextField medname =new JTextField(medicineName,20);
        JTextField quantity=new JTextField(medicineQuantity,20);
        JTextField batch=new JTextField(medicineBatchNumber,20);
        JTextField price=new JTextField(medprice,20);
        JTextField exp=new JTextField(medexp);

        addPanel.add(new JLabel("Medicine ID"));
        addPanel.add(medID);
        addPanel.add(new JLabel("Medicine name"));
        addPanel.add(medname);
        addPanel.add(new JLabel("Quantity"));
        addPanel.add(quantity);
        addPanel.add(new JLabel("Batch number"));
        addPanel.add(batch);
        addPanel.add(new JLabel("Price"));
        addPanel.add(price);
        addPanel.add(new JLabel("Expiry date"));
        addPanel.add(exp);

        JButton saveButton= new JButton("Save details");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                int updateID= Integer.parseInt(medID.getText());
                String updateName= medname.getText();
                int updateQuantity= Integer.parseInt(quantity.getText());
                int upatebatch= Integer.parseInt(batch.getText());
                double updatePrice= Double.parseDouble(price.getText());
                Date updateexp= Date.valueOf(exp.getText());

                if (rowIndex==-1){
                    //Add new row to table
                    connector.InsertRowStockTable(updateID,updateName,updateQuantity,upatebatch,updatePrice,updateexp);
                    int columnIndexToCheck=0;
                    Set<Object> uniqueValues=new HashSet<>();
                    boolean duplicatesFound=false;
                    for(int row=0;row<StockTableModel.getRowCount(); row++){
                        Object value=StockTableModel.getValueAt(row,columnIndexToCheck);
                        if(!uniqueValues.add(value)){
                            duplicatesFound=true;
                            break;
                        }
                    }
                    if (duplicatesFound){
                        StockTableModel.addRow(new Object[]{updateID,updateName,updateQuantity,upatebatch,upatebatch,updateexp});

                    }else {System.out.println("Duplicate Primary key detected!");
                    }
                }
                AddDialog.dispose();
                }catch (Exception A) {
                    JOptionPane.showMessageDialog(null, "Please Ensure all Fields are filled in their correct format");
                }
            }
        });
        addPanel.add(saveButton);
        AddDialog.add(addPanel);
        AddDialog.pack();
        AddDialog.setLocationRelativeTo(null);
        AddDialog.setVisible(true);
    }
}
