/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Objects;

/**
 *
 * @author Maryem
 */
public class labo {
    
    private int id,tel;
    private String nom,bloc,mail,img,med;
    private double average_rating;

    public labo(int id,  String nom, String bloc, String mail,int tel, String img, String med, double average_rating) {
        this.id = id;
        this.tel = tel;
        this.nom = nom;
        this.bloc = bloc;
        this.mail = mail;
        this.img = img;
        this.med = med;
        this.average_rating = average_rating;
    }
    
    
    

    public labo(int id,  String nom, String bloc, String mail,int tel, String img, String med) {
        this.id = id;
        this.tel = tel;
        this.nom = nom;
        this.bloc = bloc;
        this.mail = mail;
        this.img = img;
        this.med = med;
        this.average_rating=0;
    }

    public labo(String nom, String bloc, String mail, int tel, String img, String med) {
        this.tel = tel;
        this.nom = nom;
        this.bloc = bloc;
        this.mail = mail;
        this.img = img;
        this.med = med;
        average_rating=0;
    }

    public labo() {
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }

   

  

    
    
    
    public void setId(int id) {
        this.id = id;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public int getId() {
        return id;
    }

    public int getTel() {
        return tel;
    }

    public String getNom() {
        return nom;
    }

    public String getBloc() {
        return bloc;
    }

    public String getMail() {
        return mail;
    }

    public String getImg() {
        return img;
    }

    public String getMed() {
        return med;
    }

    @Override
    public String toString() {
        return  "" + nom +"";
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + this.tel;
        hash = 23 * hash + Objects.hashCode(this.nom);
        hash = 23 * hash + Objects.hashCode(this.bloc);
        hash = 23 * hash + Objects.hashCode(this.mail);
        hash = 23 * hash + Objects.hashCode(this.img);
        hash = 23 * hash + Objects.hashCode(this.med);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final labo other = (labo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.tel != other.tel) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.bloc, other.bloc)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.img, other.img)) {
            return false;
        }
        if (!Objects.equals(this.med, other.med)) {
            return false;
        }
        return true;
    }
    
    
    
}
