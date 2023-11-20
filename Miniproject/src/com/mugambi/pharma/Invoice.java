//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Invoice extends JFrame {
    private JLabel Title;
    private JLabel patientID;
    private JPanel PatientInvoice;
    private JLabel PatName;
    private JLabel INpatID;
    private JLabel INpatName;
    private JLabel INgrossCost;
    private JLabel INmedInsurance;
    private JLabel INnetCost;
    private JButton print;

    public Invoice(String id,String name,String gross,String ins,String net){
        super("Pharma");
        setExtendedState(Invoice.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PatientInvoice);
        INpatID.setText(id);
        INpatName.setText(name);
        INgrossCost.setText(gross);
        INmedInsurance.setText(ins);
        INnetCost.setText(net);
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Invoice Printed");
                dispose();
            }
        });
    }

}
