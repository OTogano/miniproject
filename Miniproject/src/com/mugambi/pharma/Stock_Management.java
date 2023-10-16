//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stock_Management extends JFrame {
    private JPanel Stock_Management;
    private JButton backButton;
    private JLabel StockLabel;
    private JTextField Copyright_message;

    public Stock_Management(){
        super("Pharma");//title
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Disposes frame when close is clicked
        setContentPane(Stock_Management);//sets up frame access for Pharma class
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//Disposes frame when button is clicked
            }
        });
}
}
