//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Pharma_main {
    public static void main(String [] args){
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        //this is the main class that has an instance of Pharma class
        //This allows Pharma class to be used
        Pharma main_menu = new Pharma();
        main_menu.getContentPane();//Gets the pharma form GUI design
        main_menu.setExtendedState(Pharma.MAXIMIZED_BOTH);//makes the GUI open in full screen
        main_menu.setVisible(true);//allows GUI to be displayed
        DatabaseService DB1=new DatabaseService();

    }

}
