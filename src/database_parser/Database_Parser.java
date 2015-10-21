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
    private static Vector<Article> articles = new Vector<Article>();
    
    private static ResultSet results;
    private static String stSQL;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        getArticles();
        

        
        
    }
    
    private static void getArticles(){
        stSQL = "SELECT * FROM articles;";
        Article art;
        
        results = dataConn.excuteQuery(stSQL);
        
        int id = -1;
        String title = "";
        String author = "";
        String content = "";
        String tags = "";
        String category = "";
        
        
        try{
            while (results.next()){
                id = results.getInt("id");
                title = results.getString("title");
                //author = results.getString("employee_id");   JOIN
                content = results.getString("content");
                //tags = results.getString("tags_id");         JOIN
                //category = results.getString("cat_id");      JOIN
                
                art = new Article(id, title, content, author, tags, category);
                articles.add(art);
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
