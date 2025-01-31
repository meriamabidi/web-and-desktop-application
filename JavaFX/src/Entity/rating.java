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
public class rating {
    private int id,value;
    private labo labo;

    public rating(int value, labo labo) {
        this.value = value;
        this.labo = labo;
    }
    
    

    public rating(int id, labo l, int value) {
        this.id = id;
        this.value = value;
        this.labo = l;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public labo getLabo() {
        return labo;
    }

    public void setLabo(labo l) {
        this.labo = l;
    }
    
    
}
