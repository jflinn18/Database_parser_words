/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_parser;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ccolegrove17
 */
public class DatabaseConnection {

    Connection conn = null;

    public DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://cs1/whitworthian", "ccolegrove", "whitworthian1");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ResultSet executeQuery(String stSQL) {
        try {
            PreparedStatement stmt = conn.prepareStatement(stSQL);
            return stmt.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void executeUpdate(String stSQL) {
        try {
            PreparedStatement stmt = conn.prepareStatement(stSQL);
            System.out.println(stmt.executeUpdate());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void cleanup(){
        try{
            conn.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}


