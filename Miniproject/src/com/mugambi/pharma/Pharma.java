//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;
//Import GUI components
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Stack;

public class Pharma extends JFrame {
    //Initialize GUI components
    private JPanel PharmaMainPanel;
    private JLabel login;
    private JButton doc_button;
    private JButton pharm_button;
    private JButton PharmaClose;

    public Pharma() {
        super("Pharma");//frame title
        DatabaseService DB = new DatabaseService();
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

    private void Doctor() {
        //When doctor button is clicked, Doctor interface is opened
        JDialog DoctorLogin = new JDialog();
        DoctorLogin.setTitle("DOCTOR || LOGIN");

        JPanel doclogin = new JPanel();
        doclogin.setLayout(new GridLayout(3, 2));
        JTextField docusername = new JTextField(20);
        JPasswordField docpassword = new JPasswordField(20);

        doclogin.add(new JLabel("USERNAME"));
        doclogin.add(docusername);
        doclogin.add(new JLabel("PASSWORD"));
        doclogin.add(docpassword);
        JButton Login = new JButton("login");
        JButton Cancel = new JButton("cancel");

        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(doclogin);
                if (window != null) {
                    window.dispose();
                }
            }
        });
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String logUser = "doctor";
                String logPass = "1234";
                String verifyusername = docusername.getText();
                String verifypassword = new String(docpassword.getPassword());
                if (verifyusername.equals(logUser)&&verifypassword.equals(logPass)) {
                        JOptionPane.showMessageDialog(null, "Welcome " + verifyusername);
                        Doctor accessDoc = new Doctor();
                        accessDoc.getContentPane();
                        accessDoc.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
            }
        });
        doclogin.add(Cancel);
        doclogin.add(Login);
        DoctorLogin.add(doclogin);
        DoctorLogin.pack();
        DoctorLogin.setLocationRelativeTo(null);
        DoctorLogin.setVisible(true);
    }

    private void Pharmacist() {
        //When pharmacist button is clicked, Pharmacist interface is opened
        JDialog PharmacistLogin = new JDialog();
        PharmacistLogin.setTitle("PHARMACIST || LOGIN");

        JPanel parmacistlogin = new JPanel();
        parmacistlogin.setLayout(new GridLayout(3, 2));
        JTextField pharmusername = new JTextField(20);
        JPasswordField pharmpassword = new JPasswordField(20);

        parmacistlogin.add(new JLabel("USERNAME"));
        parmacistlogin.add(pharmusername);
        parmacistlogin.add(new JLabel("PASSWORD"));
        parmacistlogin.add(pharmpassword);
        JButton Login = new JButton("login");
        JButton Cancel = new JButton("cancel");

        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(parmacistlogin);
                if (window != null) {
                    window.dispose();
                }
            }
        });
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String logUser = "pharmacist";
                String logPass = "5678";
                String verifyusername = pharmusername.getText();
                String verifypassword = new String(pharmpassword.getPassword());
                if (verifyusername.equals(logUser)&&verifypassword.equals(logPass)) {
                        JOptionPane.showMessageDialog(null, "Welcome " + verifyusername);
                        Pharmacist accessPharmacist = new Pharmacist();
                        accessPharmacist.getContentPane();
                        accessPharmacist.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
            }
        });
        parmacistlogin.add(Cancel);
        parmacistlogin.add(Login);
        PharmacistLogin.add(parmacistlogin);
        PharmacistLogin.pack();
        PharmacistLogin.setLocationRelativeTo(null);
        PharmacistLogin.setVisible(true);
    }
}
