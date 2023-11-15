//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;
//Import GUI components
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Pharma extends JFrame{
    //Initialize GUI components
    private JPanel PharmaMainPanel;
    private JLabel login;
    private JButton doc_button;
    private JButton pharm_button;
    private JButton PharmaClose;

    public Pharma(){
        super("Pharma");//frame title
        DatabaseService DB =new DatabaseService();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exits frame when close is clicked
        setContentPane(PharmaMainPanel);//Sets the pharma form GUI design
        //Adds actions when doctor and pharmacist button is clicked
        doc_button.addActionListener(e -> Doctor());
        pharm_button.addActionListener(e -> Pharmacist());
        PharmaClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//closes the frame when close button is clicked
                DB.closeConnection();
            }
        });
    }
private void Doctor(){
        //When doctor button is clicked, Doctor interface is opened
    Doctor accessDoc=new Doctor();
    accessDoc.getContentPane();
    accessDoc.setVisible(true);
    dispose();
}
private void Pharmacist(){
    //When pharmacist button is clicked, Pharmacist interface is opened
        Pharmacist accessPharmacist=new Pharmacist();
        accessPharmacist.getContentPane();
        accessPharmacist.setVisible(true);
        dispose();
}
}
