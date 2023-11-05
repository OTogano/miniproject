package com.mugambi.pharma;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.sun.tools.javac.Main;

import java.sql.*;
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
