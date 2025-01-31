/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Maryem
 */
public class reply {
    private int id,c;
    private String nom,reponse;
 

    @Override
    public String toString() {
        return "reply{" + "id=" + id + ", nom=" + nom + ", reponse=" + reponse + '}';
    }

    public reply() {
    }

    public reply(int id, String nom, String reponse, int c) {
        this.id = id;
        this.nom = nom;
        this.reponse = reponse;
        this.c=c;
    }
    


    public reply(String nom, String reponse,int c) {
        this.nom = nom;
        this.reponse = reponse;
         this.c=c;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

   
    
    
}
