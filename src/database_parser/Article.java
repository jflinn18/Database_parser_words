/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_parser;

/**
 *
 * @author Joseph
 */
public class Article {
    protected int id = -1;
    protected String title = "";
    protected String content = "";
    protected String author = "";
    protected String tags = "";
    protected String category = "";
    
    
    public Article(int id, String title, String con, String auth, String tags, String cat){
        this.id = id;
        this.title = title;
        this.content = con;
        this.author = auth;
        this.tags = tags;
        this.category = cat;
    }
}
