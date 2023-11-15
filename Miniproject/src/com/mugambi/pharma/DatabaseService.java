//Clyde Mwenda Mugambi , BICS , 166330 , 14/10/2023
package com.mugambi.pharma;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.mysql.cj.log.Log;
import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseService{
    static final String DRIVER="com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL="jdbc:mysql://localhost/db_clyde_mugambi_166330";
    static final String USER="root";
    static final String PASSWORD="clyde166330";
    private static Connection connection;
    public DatabaseService(){
        try{
            //Load the driver class
            Class.forName(DRIVER);
            //Establish connection
            connection=DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);
            System.out.println("Success");
        }catch (ClassNotFoundException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }catch (SQLException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    public ResultSet executeSelectQuery(String query){
        try{
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            return resultSet;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public Vector<String> getMedicineinfo(){
        Vector<String>medicineList=new Vector<>();
        try{
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT Med_ID,MedicineName FROM db_stockmanagement ORDER BY MedicineName");
            while (rs.next()){
                String name=rs.getString("MedicineName");
                medicineList.add(name);
            }
            System.out.println("Obtained medicine name");
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return medicineList;
    }
    public void deleteRowStockTable(int rowId){
        try{
        //String deleteQuery
        String deleteQuery="DELETE FROM db_stockmanagement WHERE Med_ID = ?";

        PreparedStatement preparedStatement=connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1,rowId);
        preparedStatement.executeUpdate();
        System.out.println("Deleted row from Stock Management Table");
        preparedStatement.close();
    }catch (SQLException e){
            e.printStackTrace();
        }}
    public void deleteRowDocTable(int rowId){
        try{
            //String deleteQuery
            String deleteQuery="DELETE FROM db_DoctorPrescription WHERE Prescription_ID = ?";

            PreparedStatement preparedStatement=connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1,rowId);
            preparedStatement.executeUpdate();
            System.out.println("Deleted row from Doctor Prescription Table");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }}
    public void deleteRowPurchaseTable(int rowId){
        try{
            //String deleteQuery
            String deleteQuery="DELETE FROM db_drugpurchase WHERE Prescription_ID = ?";

            PreparedStatement preparedStatement=connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1,rowId);
            preparedStatement.executeUpdate();
            System.out.println("Deleted row from Purchase Table");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }}
    public void UpdateRowStockTable(int med_id, String med_name, int quantity, int batch, double price,Date exp){
        try {
            String updateQuery = "UPDATE db_stockmanagement SET MedicineName=?, QuantityAvailable=?,BatchNumber=?,Price=?,ExpiryDate=? WHERE Med_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, med_name);
            preparedStatement.setInt(2,quantity);
            preparedStatement.setInt(3, batch);
            preparedStatement.setDouble(4, price);
            preparedStatement.setDate(5, exp);
            preparedStatement.setInt(6, med_id);

            preparedStatement.executeUpdate();
            System.out.println("Updated row in db_stockmanagement");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void UpdateRowDocTable(int pat_id, String pat_name, int presid, String medname,String quant,String freq,String dur){
        try {
            String updateQuery = "UPDATE db_DoctorPrescription SET Patient_ID=?, PatientName=?,MedicineName=?,DosageQuantity=?,DosageFrequency=?,DurationOfDosage=? WHERE Prescription_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, pat_id);
            preparedStatement.setString(2,pat_name);
            preparedStatement.setString(3,medname);
            preparedStatement.setString(4, quant);
            preparedStatement.setString(5,freq);
            preparedStatement.setString(6,dur);
            preparedStatement.setInt(7, presid);

            preparedStatement.executeUpdate();
            System.out.println("Updated row in db_DoctorPrescription");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void UpdateRowPurchaseTable(int purpat_id, String purpat_name, int purpresid, String purquan, String purfre,String purdu,double gros,double in){
        try {
            String updateQuery = "UPDATE db_drugpurchase SET Patient_ID=?, PatientName=?,DosageQuantity=?,DosageFrequency=?,DurationOfDosage=?,GrossCost=?,MedicalInsurance=? WHERE Prescription_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, purpat_id);
            preparedStatement.setString(2,purpat_name);
            preparedStatement.setString(3,purquan);
            preparedStatement.setString(4, purfre);
            preparedStatement.setString(5, purdu);
            preparedStatement.setDouble(6,gros);
            preparedStatement.setDouble(7,in);
            preparedStatement.setInt(8, purpresid);

            preparedStatement.executeUpdate();
            System.out.println("Updated row in db_drugpurchase");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void InsertRowStockTable(int med_id, String med_name, int quantity, int batch, double price,Date exp){
        try {
            String insertQuery = "INSERT INTO db_stockmanagement(Med_ID,MedicineName,QuantityAvailable,BatchNumber,Price,ExpiryDate)VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, med_id);
            preparedStatement.setString(2, med_name);
            preparedStatement.setInt(3,quantity);
            preparedStatement.setInt(4, batch);
            preparedStatement.setDouble(5, price);
            preparedStatement.setDate(6, exp);
            preparedStatement.executeUpdate();
            System.out.println("Inserted row in db_stockmanagement");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void InsertRowDocTable(int pat_id,String pat_name,int pres_id,String med_name,String quant,String freq,String dura){
        try{
            String insertQuery="INSERT INTO db_doctorprescription(Patient_ID,PatientName,Prescription_ID,MedicineName,DosageQuantity,DosageFrequency,DurationOfDosage)Values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1,pat_id);
            preparedStatement.setString(2,pat_name);
            preparedStatement.setInt(3,pres_id);
            preparedStatement.setString(4,med_name);
            preparedStatement.setString(5,quant);
            preparedStatement.setString(6,freq);
            preparedStatement.setString(7,dura);
            preparedStatement.executeUpdate();
            System.out.println("Inserted row in db_doctorprescription");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void closeConnection(){
        try{
            if(connection != null){
                connection.close();
                System.out.println("Closing");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
