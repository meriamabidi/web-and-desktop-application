/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.labo;
import Entity.rating;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.MaConnexion;

/**
 *
 * @author Maryem
 */
public class ratingService implements ServiceInterface<rating>{

   
    Connection cnx;
    String sql = "";

    public ratingService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(rating l) throws SQLException {
        sql = "insert into rating(labo_id,value) values (?,?)";
        PreparedStatement ste = cnx.prepareStatement(sql);

        ste.setInt(1, l.getLabo().getId());
        ste.setInt(2, l.getValue());
    
        ste.executeUpdate();
        
        System.out.println("Rating Ajoutée !!");
        
    }
laboService ls=new laboService();
    @Override
    public List<rating> afficher() {
        List<rating> ratings = new ArrayList<>();
        try {
            sql = "select * from rating";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                rating p = new rating(
                        rs.getInt(1),
                       ls.id_labo(rs.getInt(2)) ,
                        rs.getInt(3));
                       
                ratings.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ratings;
    }

    @Override
    public void supprimer(rating l) {
        sql = "delete from rating where id=" + l.getId() + "";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            System.out.println("rating supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Modifier(rating l) {
        try {
            String req = "UPDATE rating SET labo_id=?,value=?WHERE id="+ l.getId() + "";
            PreparedStatement ste = cnx.prepareStatement(req);

            ste.setInt(1, l.getId());
            ste.setInt(2, l.getLabo().getId());
            ste.setInt(3, l.getValue());
            

            ste.executeUpdate();
            System.out.println("Rating Modifée");

        } catch (SQLException ex) {
            Logger.getLogger(laboService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public double average_rating(int i) {
    int s = 0;
    int x = 0;

    try {
        String sql = "SELECT * FROM rating WHERE labo_id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(sql);
        pstmt.setInt(1, i);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            rating p = new rating(
                    rs.getInt(1),
                    ls.id_labo(rs.getInt(2)),
                    rs.getInt(3));
            x++;
            s += p.getValue();
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    if (x == 0) {
        System.out.println("Aucun note trouver pour labo_id: " + i);
        return 0.0;
    }

    return (double) s / x;
}
    
    
}
