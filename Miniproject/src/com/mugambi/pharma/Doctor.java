//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;
//Import GUI components
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Doctor extends JFrame{
    //Initialize doctor interface components
    private JPanel DoctorPanel;
    private JLabel DoctorsLabel;
    private JButton DocExit;
    private JTable Medicine_Prescription;
    private JTextField SearchField;
    private JLabel search;
    private JButton Add;
    private DefaultTableModel DocTableModel;
    private JComboBox<String> nameComboBox;
    DatabaseService connector=new DatabaseService();
    public Doctor(){
        super("Doctor's interface");//title
        setExtendedState(Doctor.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Disposes frame when close is clicked
        setContentPane(DoctorPanel);//sets up frame access for Pharma class
        DocExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pharma navigation =new Pharma();
                navigation.getContentPane();
                navigation.setExtendedState(Pharma.MAXIMIZED_BOTH);
                navigation.setVisible(true);
                dispose();//Disposes frame when button is clicked
            }
        });
        DocTableModel=new DefaultTableModel();
        Medicine_Prescription.setModel(DocTableModel);
        try {
            String SelectDoctorTableQuery="SELECT * FROM db_DoctorPrescription";
            ResultSet rs= connector.executeSelectQuery(SelectDoctorTableQuery);
            int columnCount=rs.getMetaData().getColumnCount();
            for(int i=1; i<= columnCount; i++){
                DocTableModel.addColumn(rs.getMetaData().getColumnName(i));
            }
            while (rs.next()){
                Object[] rowData =new Object[columnCount];
                for(int i=1; i<= columnCount; i++){
                    rowData[i - 1]=rs.getObject(i);
                }
                DocTableModel.addRow(rowData);
            }
        }catch (SQLException e){
            System.out.println("Select DOC ERROR");
        }

        SearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String searchQuery =SearchField.getText();
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
                        int patient_ID=(int) DocTableModel.getValueAt(selectedModelRow,0);
                        String patient_name=(String) DocTableModel.getValueAt(selectedModelRow,1);
                        int prescription_ID=(int) DocTableModel.getValueAt(selectedModelRow,2);
                        int med_ID=(int) DocTableModel.getValueAt(selectedModelRow,3);
                        String medicine_name=(String) DocTableModel.getValueAt(selectedModelRow,4);
                        int DosageQuantity=(int) DocTableModel.getValueAt(selectedModelRow,5);
                        String DosageType=(String)DocTableModel.getValueAt(selectedModelRow,6);
                        String DosageFrequency=(String)DocTableModel.getValueAt(selectedModelRow,7);
                        String DosageDuration=(String)DocTableModel.getValueAt(selectedModelRow,8);

                        JDialog inputDialog= new JDialog();
                        inputDialog.setTitle("Edit information for "+patient_name);

                        JPanel inputPanel = new JPanel();
                        List<String>medicinename=connector.getMedicineinfo();
                        medicinename.add(0,"Select a medicine");
                        inputPanel.setLayout(new GridLayout(12,2));

                        JTextField patientID=new JTextField(String.valueOf(patient_ID));
                        JTextField patientname=new JTextField(patient_name,20);
                        JTextField prescriptionID=new JTextField(String.valueOf(prescription_ID));
                        JTextField medID=new JTextField(String.valueOf(med_ID));
                        JLabel currentmedicine=new JLabel(medicine_name);
                        nameComboBox=new JComboBox<>(connector.getMedicineinfo());
                        JTextField dosagequant=new JTextField(String.valueOf(DosageQuantity));
                        JTextField dosagetype=new JTextField(DosageType,20);
                        JTextField dosaagefreq=new JTextField(DosageFrequency,20);
                        JTextField dosageduration=new JTextField(DosageDuration,20);
                        JButton deleteButton =new JButton("Delete");
                        JButton SaveChanges =new JButton("Save Changes");
                        JButton CancelButton =new JButton("Cancel");

                        inputPanel.add(new JLabel("Patient ID"));
                        inputPanel.add(patientID);
                        inputPanel.add(new JLabel("Patient Name"));
                        inputPanel.add(patientname);
                        inputPanel.add(new JLabel("Prescription ID"));
                        inputPanel.add(prescriptionID);
                        inputPanel.add(new JLabel("Med ID"));
                        inputPanel.add(medID);
                        inputPanel.add(new JLabel("Current Medicine: "));
                        inputPanel.add(currentmedicine);
                        inputPanel.add(new JLabel("Updated Medicine: "));
                        inputPanel.add(nameComboBox);
                        inputPanel.add(new JLabel("Dosage Quantity"));
                        inputPanel.add(dosagequant);
                        inputPanel.add(new JLabel("Dosage Type"));
                        inputPanel.add(dosagetype);
                        inputPanel.add(new JLabel("Dosage Frequency"));
                        inputPanel.add(dosaagefreq);
                        inputPanel.add(new JLabel("Duration of Dosage"));
                        inputPanel.add(dosageduration);
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
                                connector.deleteRowDocTable(prescription_ID);
                                connector.deleteRowPurchaseTable(prescription_ID);
                                DocTableModel.removeRow(selectedModelRow);
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
                                int PAT_ID=Integer.parseInt(patientID.getText());
                                String PAT_NAME=patientname.getText();
                                int PRES_ID=Integer.parseInt(prescriptionID.getText());
                                int MED_ID=Integer.parseInt(medID.getText());
                                String MED_NAME=(String) nameComboBox.getSelectedItem();
                                int QUANT= Integer.parseInt(dosagequant.getText());
                                String TYPE=dosagetype.getText();
                                String FREQ=dosaagefreq.getText();
                                String DUR=dosageduration.getText();
                                connector.UpdateRowDocTable(PAT_ID,PAT_NAME,MED_ID,MED_NAME,QUANT,TYPE,FREQ,DUR,PRES_ID);
                                connector.getMedicinecost(MED_ID);
                                connector.UpdateRowPurchaseTableFromDoc(PAT_ID,PAT_NAME,MED_NAME,QUANT,TYPE,FREQ,DUR,PRES_ID);
                                DocTableModel.setValueAt(PAT_ID,selectedModelRow,0);
                                DocTableModel.setValueAt(PAT_NAME,selectedModelRow,1);
                                DocTableModel.setValueAt(PRES_ID,selectedModelRow,2);
                                DocTableModel.setValueAt(MED_ID,selectedModelRow,3);
                                DocTableModel.setValueAt(MED_NAME,selectedModelRow,4);
                                DocTableModel.setValueAt(QUANT,selectedModelRow,5);
                                DocTableModel.setValueAt(TYPE,selectedModelRow,6);
                                DocTableModel.setValueAt(FREQ,selectedModelRow,7);
                                DocTableModel.setValueAt(DUR,selectedModelRow,8);
                                Window window=SwingUtilities.getWindowAncestor(inputPanel);
                                if(window!=null){
                                    window.dispose();}}catch (Exception A){
                                    JOptionPane.showMessageDialog(null,"Please Ensure all Fields are filled in their correct format");
                            }
                            }
                        });
                        inputDialog.add(inputPanel);
                        inputDialog.pack();
                        inputDialog.setLocationRelativeTo(null);
                        inputDialog.setVisible(true);
                    }
                }
            }
        });
        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {createEditDialog("","","","","","","","",-1);
            }
        });
    }
    private void createEditDialog(String patid,String patname,String presid,String medid,String quant,String type,String freq,String dura,int rowIndex ){
        JDialog AddDialog=new JDialog();
        AddDialog.setTitle("Prescribe patient");

        JPanel prescribepanel=new JPanel();

        List<String>medicinename=connector.getMedicineinfo();
        medicinename.add(0,"Select a medicine");
        prescribepanel.setLayout(new GridLayout(12,2));

        JTextField patID=new JTextField(patid,20);
        JTextField patNAME=new JTextField(patname,20);
        JTextField presID=new JTextField(presid,20);
        JTextField medID=new JTextField(medid,2);
        nameComboBox=new JComboBox<>(connector.getMedicineinfo());
        JTextField Quant=new JTextField(quant,20);
        JTextField Type=new JTextField(type,20);
        JTextField Freq=new JTextField(freq,20);
        JTextField Dura=new JTextField(dura,20);

        prescribepanel.add(new JLabel("Patient ID"));
        prescribepanel.add(patID);
        prescribepanel.add(new JLabel("Patient Name"));
        prescribepanel.add(patNAME);
        prescribepanel.add(new JLabel("Prescription ID"));
        prescribepanel.add(presID);
        prescribepanel.add(new JLabel("Med ID"));
        prescribepanel.add(medID);
        prescribepanel.add(new JLabel("Medicine Name"));
        prescribepanel.add(nameComboBox);
        prescribepanel.add(new JLabel("Dosage Quantity"));
        prescribepanel.add(Quant);
        prescribepanel.add(new JLabel("Dosage Type"));
        prescribepanel.add(Type);
        prescribepanel.add(new JLabel("Dosage Frequency"));
        prescribepanel.add(Freq);
        prescribepanel.add(new JLabel("Duration of Dosage"));
        prescribepanel.add(Dura);

        JButton saveButton=new JButton("Prescribe");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                int addpatID=Integer.parseInt(patID.getText());
                String addpatNAME=patNAME.getText();
                int addpresID=Integer.parseInt(presID.getText());
                int addmedid=Integer.parseInt(medID.getText());
                String addMedname=(String) nameComboBox.getSelectedItem();
                int addQuant= Integer.parseInt(Quant.getText());
                String addType=Type.getText();
                String addFreq=Freq.getText();
                String addDura=Dura.getText();
                 if (rowIndex==-1){
                     connector.InsertRowDocTable(addpatID,addpatNAME,addpresID,addmedid,addMedname,addQuant,addType,addFreq,addDura);
                     connector.getMedicinecost(addmedid);
                     connector.quantitySubtraction(addmedid,addQuant);
                     connector.InsertRowPurchaseTable(addpatID,addpatNAME,addpresID,addMedname,addQuant,addType,addFreq,addDura);
                     int columnIndexToCheck=0;
                     Set<Object> uniqueValues=new HashSet<>();
                     boolean duplicatesFound=false;
                     for(int row=0;row<DocTableModel.getRowCount(); row++){
                         Object value=DocTableModel.getValueAt(row,columnIndexToCheck);
                         if(!uniqueValues.add(value)){
                             duplicatesFound=true;
                             break;
                         }
                     }
                     if (duplicatesFound){
                         DocTableModel.addRow(new Object[]{addpatID,addpatNAME,addpresID,addmedid,addMedname,addQuant,addType,addFreq,addDura});

                     }else {System.out.println("Duplicate Primary key detected!");
                     }
                 }
                 AddDialog.dispose();}catch (Exception A){
                    JOptionPane.showMessageDialog(null,"Please Ensure all Fields are filled in their correct format");
            }}
        });
        prescribepanel.add(saveButton);
        AddDialog.add(prescribepanel);
        AddDialog.pack();
        AddDialog.setLocationRelativeTo(null);
        AddDialog.setVisible(true);
    }
}
