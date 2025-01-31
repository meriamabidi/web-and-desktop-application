/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.analyse;
import Entity.local;
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
public class localService implements ServiceInterface<local> {
    
        laboService ls = new laboService();

    Connection cnx;
    String sql = "";

    public localService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(local t) throws SQLException {
  sql = "insert into local(local,labo_id) values (?,?)";
        PreparedStatement ste = cnx.prepareStatement(sql);

        ste.setString(1, t.getLocal());
        ste.setInt(2, t.getL().getId());
        ste.executeUpdate();

        System.out.println("Local Ajoutée !!");

    }

    @Override
    public List<local> afficher() {
List<local> locals = new ArrayList<>();
        try {
            sql = "select * from local";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                local p = new local(
                        rs.getInt(1),
                        rs.getString(2),
                        ls.id_labo(rs.getInt(3))
                );
                locals.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return locals;

    }

    @Override
    public void supprimer(local t) {
  sql = "delete from local where id=" + t.getId() + "";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            System.out.println("Local supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void Modifier(local t) {

          try {
            String req = "UPDATE local SET local=?,labo_id=? WHERE id=" + t.getId() + "";
            PreparedStatement ste = cnx.prepareStatement(req);

             ste.setString(1, t.getLocal());
        ste.setInt(2, t.getL().getId());
           

            ste.executeUpdate();
            System.out.println("Local Modifée");

        } catch (SQLException ex) {
            Logger.getLogger(localService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
      public local id_local(int i) {

        local s=null;
        try {

            String req = "SELECT id,local ,labo_id FROM local  WHERE labo_id  = " + i + "";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);

            if (rs.next()) {
               s = new local(rs.getInt(1), rs.getString(2),ls.id_labo(rs.getInt(3)));
               

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
    }
    
}
