/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.labo;
import java.io.File;
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
 * @author Maryem
 */
public class laboService implements ServiceInterface<labo> {

    Connection cnx;
    String sql = "";

    public laboService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(labo l) throws SQLException {

        sql = "insert into labo(nom,bloc,mail,tel,img,med,average_rating) values (?,?,?,?,?,?,?)";
        PreparedStatement ste = cnx.prepareStatement(sql);

        ste.setString(1, l.getNom());
        ste.setString(2, l.getBloc());
        ste.setString(3, l.getMail());
        ste.setInt(4, l.getTel());
        ste.setString(5, l.getImg());
        ste.setString(6, l.getMed());
        ste.setDouble(7, l.getAverage_rating());
        ste.executeUpdate();

        System.out.println("Laboratoire Ajoutée !!");

    }

    @Override
    public List<labo> afficher() {
        List<labo> labos = new ArrayList<>();
        try {
            sql = "select * from labo";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                labo p = new labo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getDouble(8));
                labos.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return labos;
    }

    @Override
    public void supprimer(labo l) {
        sql = "delete from labo where id=" + l.getId() + "";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            System.out.println("Laboratoire supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Modifier(labo l) {
        try {
            String req = "UPDATE labo SET nom=?,bloc=?,mail=?,tel=?,img=?,med=?,average_rating=? WHERE id=" + l.getId() + "";
            PreparedStatement ste = cnx.prepareStatement(req);

            ste.setString(1, l.getNom());
            ste.setString(2, l.getBloc());
            ste.setString(3, l.getMail());
            ste.setInt(4, l.getTel());
            ste.setString(5, l.getImg());
            ste.setString(6, l.getMed());
            ste.setDouble(7, l.getAverage_rating());
            ste.executeUpdate();
            System.out.println("Laboratoire Modifée");

        } catch (SQLException ex) {
            Logger.getLogger(laboService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public labo id_labo(int i) {
        labo l = null;
        try {
            String req = "SELECT id, nom, bloc, mail, tel, img, med , average_rating FROM labo WHERE id = " + i;
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);

            if (rs.next()) {
                l = new labo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getDouble(8));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }

}
