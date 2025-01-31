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



public class local {

    public local() {
    }

    public local(int id, String local, labo l) {
        this.id = id;
        this.local = local;
        this.l = l;
    }
    
      public local(String local, labo l) {
        this.local = local;
        this.l = l;
    }
    
    
    private int id ;
    private String local;
    private labo l;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public labo getL() {
        return l;
    }

    public void setL(labo l) {
        this.l = l;
    }

  
    
    
}
