/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_parser;

import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;



/**
 *
 * @author Joseph
 */
public class Database_Parser {

    private static final DatabaseConnection dataConn = new DatabaseConnection();
    private static Vector<Article> articles = new Vector<Article>();
    private static int index;
    
    private static ResultSet results;
    private static String stSQL;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Scanner reader = new Scanner(System.in);
        
        //System.out.print("Enter starting index: ");
        //index = reader.nextInt();
        index = 0;
        
        getArticles();
        //getWords("content");
        //getWords("title");
        //getWords("author");
        matchWords("content");
        matchWords("title");
        matchWords("author");
    }
    
    private static void getArticles(){
        stSQL = "SELECT E.Fname, E.Lname, A.id, A.title, A.content FROM articles A, employees E WHERE A.Employee_ID = E.ID ORDER BY A.id ASC;";
        Article art;
        
        results = dataConn.excuteQuery(stSQL);
        
        int id = -1;
        String title = "";
        String Fauthor = "";
        String Lauthor = "";
        String content = "";
        String tags = "";
        String category = "";
        
        
        try{
            while (results.next()){
                id = results.getInt("id");
                title = results.getString("title");
                Fauthor = results.getString("Fname");
                Lauthor = results.getString("Lname");
                content = results.getString("content");
                
                art = new Article(id, title, content, Fauthor + " " + Lauthor);
                articles.add(art);
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    // moved to creatin the article class because i need to populate the Article JOIN Words table
    
    private static void matchWords(String dataType){
        int artID = -1;
        
        for(int i = 0; i < articles.size(); i++){
            String text = "";
            int wordID = -1;
            String[] words;
            artID = articles.get(i).id;
            
            switch (dataType){
                case "content": text = articles.get(i).content; break;
                case "author": text = articles.get(i).content; break;
                case "title": text = articles.get(i).content; break;
            }
            
            // remove special characters
            text = text.replaceAll("[^a-zA-Z \'\n]+", "");
            text = text.replace("\n", " ");
            text = text.replace("\'", "\\\'");
            
            words = text.split(" "); // assuming there are not spaces in words
            
            System.out.println(i + "/" + articles.size() + " --> "+words.length+" words");
            
            for (String word : words){
                stSQL = "SELECT ID FROM words WHERE Word = \'" + word + "\';";
                results = dataConn.excuteQuery(stSQL);
                
                try{
                    //System.out.println(results.getInt("ID"));
                   results.next();
                    wordID = results.getInt(1);
                    
                    if (wordID == -1)
                        throw new Exception("Incorrect word id");
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
                
                stSQL = "INSERT INTO " + dataType + "words (Article_ID, Word_ID)  VALUES(\"" + artID + "\", \"" + wordID + "\");";
                dataConn.executeUpdate(stSQL, false);    
            }
        }
    }
    
    private static void getWords(String dataType){
        String text = "";
        String wordID = "";
        String[] contWords;
        int artID = -1;
        
        for (int i = index; i < articles.size(); i++){
            artID = articles.get(i).id;
            
            switch (dataType){
                case "content": text = articles.get(i).content; break;
                case "author": text = articles.get(i).author; break;
                case "title": text = articles.get(i).title; break;
            }
            
            
            // remove special characters
            text = text.replaceAll("[^a-zA-Z \'\n]+", "");
            text = text.replace("\n", " ");
            text = text.replace("\'", "\\\'");
            
            contWords = text.split(" "); // assuming there are not spaces in words
            
            System.out.println(i + "/" + articles.size() + " --> "+contWords.length+" words");
            
            for (String contWord : contWords) {
                stSQL = "INSERT INTO Words (word) VALUES(\"" + contWord + "\");";
                wordID = dataConn.executeUpdate(stSQL, true);
                try{
                    if (artID == -1)
                        throw new Exception("Exploded");
                    stSQL = "INSERT INTO " + dataType + "words (Article_ID, Word_ID)  VALUES(\"" + artID + "\", \"" + wordID + "\");";
                    dataConn.executeUpdate(stSQL, false);
                } catch (Exception ex){
                    System.out.println(ex.getMessage() + "\n");
                    //System.out.println(stSQL);
                }
            }
        }
        
    }
    
}
