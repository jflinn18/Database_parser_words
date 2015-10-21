/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_parser;

import java.sql.ResultSet;
import java.util.Vector;



/**
 *
 * @author Joseph
 */
public class Database_Parser {

    private static DatabaseConnection dataConn = new DatabaseConnection();
    private static Vector<String> articles = new Vector<String>();
    
    private static ResultSet results;
    private static String stSQL;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        getTitles();
        

        
        
    }
    
    private static void getTitles(){
        stSQL = "SELECT title FROM articles;";
        
        results = dataConn.excuteQuery(stSQL);
        
        String title = "";
        try{
            while (results.next()){
                title = results.getString("title");
                articles.add(title);
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    private static void ContentWords(String table){
        String content = "";
        
        for (int i = 0; i < articles.size(); i++){
            stSQL = "SELECT content FROM articles WHERE " + table + " = " + articles.elementAt(i)+ ";";
            
            results = dataConn.executeQuery(stSQL);
            
            try{
                if(results.next())
                    content = results.getString(0);
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            String[] contWords = content.split(" ");
            
            for(int j = 0; j < contWords.length; j++){
                stSQL = "INSERT INTO Words (word) Value(\'"+ contWords[j] + "\');";
                dataConn.executeUpdate(stSQL);
            }
        }
        
    }
    
}
