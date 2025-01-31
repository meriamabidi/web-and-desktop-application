/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Services.replyService;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Maryem
 */
public class comment {
    private int id ;
    private String email,comment,auteur;
    private Date date_c;
private List<reply> r;



    public replyService rr = new replyService() ;

    public comment() {

    }


    public List<reply> getR() {
        return r;
    }

    public void setR(List<reply> r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "" +  comment + "" ;
    }

    public comment(int id, String email, String comment, String auteur, Date date_c, List<reply> r) {
        this.id = id;
        this.email = email;
        this.comment = comment;
        this.auteur = auteur;
        this.date_c = date_c;
        this.r = (List<reply>) rr.id_reply(id);
    }

  


    public comment(int id, String email, String comment, String auteur, Date date_c) {
        this.id = id;
        this.email = email;
        this.comment = comment;
        this.auteur = auteur;
        this.date_c = date_c;

    }
    
    

    public comment(String email, String comment, String auteur, Date date_c) {
        this.email = email;
        this.comment = comment;
        this.auteur = auteur;
        this.date_c = date_c;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDate_c() {
        return date_c;
    }

    public void setDate_c(Date date_c) {
        this.date_c = date_c;
    }
    
    
}
