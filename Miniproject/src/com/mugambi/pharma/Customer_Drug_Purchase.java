//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Customer_Drug_Purchase extends JFrame {
    private JLabel PurchaseLabel;
    private JButton backButton;
    private JTextField Copyright_message;
    private JPanel Customer_Purchase;

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
}
}
