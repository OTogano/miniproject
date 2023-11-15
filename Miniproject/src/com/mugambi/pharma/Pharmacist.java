//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;
//Import GUI components
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pharmacist extends JFrame {
    //Initialize pharmacist interface components
    private JLabel PharmacistLabel;
    private JButton PharmaExit;
    private JPanel PharmaPanel;
    private JButton stockManagementButton;
    private JButton customerDrugPurchaseButton;
    private JLabel pharmActionSelection;

    public Pharmacist(){
        super("Pharmacist's interface");
        setExtendedState(Pharmacist.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stockManagementButton.addActionListener(e -> Stock_Management());
        customerDrugPurchaseButton.addActionListener(e -> Customer_Drug_Purchase());
        setContentPane(PharmaPanel);
        PharmaExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pharma navigation =new Pharma();
                navigation.getContentPane();
                navigation.setExtendedState(Pharma.MAXIMIZED_BOTH);
                navigation.setVisible(true);
                dispose();
            }
        });
    }
    private void Stock_Management(){
        Stock_Management accessStock=new Stock_Management();
        accessStock.setExtendedState(Stock_Management.MAXIMIZED_BOTH);
        accessStock.getContentPane();
        accessStock.setVisible(true);
        dispose();
    }
    private void Customer_Drug_Purchase(){
        Customer_Drug_Purchase accessPurchase=new Customer_Drug_Purchase();
        accessPurchase.setExtendedState(Customer_Drug_Purchase.MAXIMIZED_BOTH);
        accessPurchase.getContentPane();
        accessPurchase.setVisible(true);
        dispose();
    }
}
