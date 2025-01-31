/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Entity.labo;
/**
 *
 * @author Maryem
 */
public class analyse {
    
    private int id,prix;//,laboo_id;
    private String image,resultat,date;
private labo l;



    public analyse() {
    }

    public labo getL() {
        return l;
    }

    public void setL(labo l) {
        this.l = l;
    }

    @Override
    public String toString() {
        return "analyse{" + "id=" + id + ", prix=" + prix + ", image=" + image + ", resultat=" + resultat + ", date=" + date + ", l=" + l + '}';
    }

    public analyse(int id, String date,String resultat,String image,int prix,    labo l) {
        this.id = id;
        this.prix = prix;
        this.image = image;
        this.resultat = resultat;
        this.date = date;
        this.l = l;
    }
  

    

    
   

    public void setId(int id) {
        this.id = id;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

  /*  public void setLaboo_id(int laboo_id) {
        this.laboo_id = laboo_id;
    }
*/
    public void setImage(String image) {
        this.image = image;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
    public int getId() {
        return id;
    }

    public int getPrix() {
        return prix;
    }

  /*  public int getLaboo_id() {
        return laboo_id;
    }*/

    public String getImage() {
        return image;
    }

    public String getResultat() {
        return resultat;
    }

    public String getDate() {
        return date;
    }

    
    
    
}
