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
import java.util.List;

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
    DatabaseService  connector=new DatabaseService();
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
                        String medicine_name=(String) DocTableModel.getValueAt(selectedModelRow,3);
                        String DosageQuantity=(String) DocTableModel.getValueAt(selectedModelRow,4);
                        String DosageFrequency=(String)DocTableModel.getValueAt(selectedModelRow,5);
                        String DosageDuration=(String)DocTableModel.getValueAt(selectedModelRow,6);


                        JDialog inputDialog= new JDialog();
                        inputDialog.setTitle("Edit information for "+patient_name);

                        JPanel inputPanel = new JPanel();
                        List<String>medicinename=connector.getMedicineinfo();
                        medicinename.add(0,"Select a medicine");
                        inputPanel.setLayout(new GridLayout(11,2));

                        JTextField patientID=new JTextField(String.valueOf(patient_ID));
                        JTextField patientname=new JTextField(patient_name,20);
                        JTextField prescriptionID=new JTextField(String.valueOf(prescription_ID));
                        JLabel currentmedicine=new JLabel(medicine_name);
                        nameComboBox=new JComboBox<>(connector.getMedicineinfo());
                        JTextField dosagequant=new JTextField(String.valueOf(DosageQuantity));
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
                        inputPanel.add(new JLabel("Current Medicine: "));
                        inputPanel.add(currentmedicine);
                        inputPanel.add(new JLabel("Updated Medicine: "));
                        inputPanel.add(nameComboBox);
                        inputPanel.add(new JLabel("Dosage Quantity"));
                        inputPanel.add(dosagequant);
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
                                int PAT_ID=Integer.parseInt(patientID.getText());
                                String PAT_NAME=patientname.getText();
                                int PRES_ID=Integer.parseInt(prescriptionID.getText());
                                String MED_NAME=(String) nameComboBox.getSelectedItem();
                                String QUANT=dosagequant.getText();
                                String FREQ=dosaagefreq.getText();
                                String DUR=dosageduration.getText();
                                connector.UpdateRowDocTable(PAT_ID,PAT_NAME,PRES_ID,MED_NAME,QUANT,FREQ,DUR);
                                DocTableModel.setValueAt(PAT_ID,selectedModelRow,0);
                                DocTableModel.setValueAt(PAT_NAME,selectedModelRow,1);
                                DocTableModel.setValueAt(PRES_ID,selectedModelRow,2);
                                DocTableModel.setValueAt(MED_NAME,selectedModelRow,3);
                                DocTableModel.setValueAt(QUANT,selectedModelRow,4);
                                DocTableModel.setValueAt(FREQ,selectedModelRow,5);
                                DocTableModel.setValueAt(DUR,selectedModelRow,6);
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
        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {createEditDialog("","","","","","",-1);
            }
        });
    }
    private void createEditDialog(String patid,String patname,String presid,String quant,String freq,String dura,int rowIndex ){
        JDialog AddDialog=new JDialog();
        AddDialog.setTitle("Prescribe patient");

        JPanel prescribepanel=new JPanel();

        List<String>medicinename=connector.getMedicineinfo();
        medicinename.add(0,"Select a medicine");
        prescribepanel.setLayout(new GridLayout(11,2));

        JTextField patID=new JTextField(patid,20);
        JTextField patNAME=new JTextField(patname,20);
        JTextField presID=new JTextField(presid,20);
        nameComboBox=new JComboBox<>(connector.getMedicineinfo());
        JTextField Quant=new JTextField(quant,20);
        JTextField Freq=new JTextField(freq,20);
        JTextField Dura=new JTextField(dura,20);

        prescribepanel.add(new JLabel("Patient ID"));
        prescribepanel.add(patID);
        prescribepanel.add(new JLabel("Patient Name"));
        prescribepanel.add(patNAME);
        prescribepanel.add(new JLabel("Prescription ID"));
        prescribepanel.add(presID);
        prescribepanel.add(new JLabel("Medicine Name"));
        prescribepanel.add(nameComboBox);
        prescribepanel.add(new JLabel("Dosage Quantity"));
        prescribepanel.add(Quant);
        prescribepanel.add(new JLabel("Dosage Frequency"));
        prescribepanel.add(Freq);
        prescribepanel.add(new JLabel("Duration of Dosage"));
        prescribepanel.add(Dura);

        JButton saveButton=new JButton("Prescribe");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int addpatID=Integer.parseInt(patID.getText());
                String addpatNAME=patNAME.getText();
                int addpresID=Integer.parseInt(presID.getText());
                String addMedname=(String) nameComboBox.getSelectedItem();
                String addQuant=Quant.getText();
                String addFreq=Freq.getText();
                String addDura=Dura.getText();
                 if (rowIndex==-1){
                     connector.InsertRowDocTable(addpatID,addpatNAME,addpresID,addMedname,addQuant,addFreq,addDura);
                     DocTableModel.addRow(new Object[]{addpatID,addpatNAME,addpresID,addMedname,addQuant,addFreq,addDura});
                 }
                 AddDialog.dispose();
            }
        });
        prescribepanel.add(saveButton);
        AddDialog.add(prescribepanel);
        AddDialog.pack();
        AddDialog.setVisible(true);
    }
}
