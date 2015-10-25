/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_parser;

import java.sql.*;
/**
 *
 * @author jflinn18
 */
public class DatabaseConnection {
    
    protected static Connection conn = null;

    public DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://cs1/whitworthian", "ccolegrove", "whitworthian1");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String executeUpdate(String stSQL, boolean getID) {
        try {
            PreparedStatement stmt = conn.prepareStatement(stSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            
            if(getID){
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                String auto_id = Integer.toString(rs.getInt(1));
                
                //System.out.println(auto_id);
                return auto_id;
            }  
            else return null;
            
            /*stmt = conn.prepareStatement("last_id");
            return Integer.toString(stmt.executeUpdate());*/
        } catch (Exception ex) {
            //System.out.println(ex.getMessage() + "\n");
            //System.out.println(stSQL + "\n\n");
        }
        
        return null;
    }
    
    public void cleanup(){
        try{
            conn.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public ResultSet excuteQuery(String stSQL) {
        try {
            //System.out.println("Using me.");
            PreparedStatement stmt = conn.prepareStatement(stSQL);
            return stmt.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    // Why???? I don't understand.
    // BUT DO NOT DELETE DUPLICATE CODE!!!
    // It will break the package.
    public ResultSet executeQuery(String stSQL) {
        try {
            //System.out.println("No!!! Using me.");
            PreparedStatement stmt = conn.prepareStatement(stSQL);
            return stmt.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
