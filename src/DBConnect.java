
import java.sql.*;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Nickolas
 */
public class DBConnect {
    
    private Connection con;
    private Statement s;
    private String password = "W1a2C3h4";
    
    /**
     * This method creates a database connection.
     */
    public DBConnect(){
        try{
            // Database path with password decoder
            String path = "jdbc:ucanaccess://GameTrakker_DB.accdb;jackcessOpener=" +
                    "CryptCodecOpener"; 
            // Database connection
            con = DriverManager.getConnection(path, "", password); 
             // Allow SQL statements to be executed
            s = con.createStatement();
        }catch(Exception ex){ // Print error if there is a problem with database
            System.out.println("ERROR: " + ex);
            JOptionPane.showMessageDialog(null, "No Connection");
        }
    }
    
    /**
     * This method returns the database connection
     * @return 
     */
    public Connection getConnection(){
        return con;
    }
    
    /**
     * This method returns the statement of the database connection
     * @return 
     */
    public Statement getStatement(){
        return s;
    }
}
