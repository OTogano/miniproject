//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;
//Import GUI components
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Doctor extends JFrame {
    //Initialize doctor interface components
    private JPanel DoctorPanel;
    private JLabel DoctorsLabel;
    private JButton DocExit;
    private JTextField Copyright_message;

    public Doctor(){
        super("Doctor's interface");//title
        setExtendedState(Doctor.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Disposes frame when close is clicked
        setContentPane(DoctorPanel);//sets up frame access for Pharma class
        DocExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//Disposes frame when button is clicked
            }
        });
    }
}
