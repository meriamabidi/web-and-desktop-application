/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.comment;
import java.sql.Connection;
import java.sql.Date;
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
public class commentService implements ServiceInterface<comment> {

    Connection cnx;
    String sql = "";

    public commentService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(comment t) throws SQLException {
        sql = "insert into comment(email ,comment,auteur,date_c) values (?,?,?,?)";
        PreparedStatement ste = cnx.prepareStatement(sql);

        ste.setString(1, t.getEmail());
        ste.setString(2, t.getComment());
        ste.setString(3, t.getAuteur());
        ste.setDate(4, (Date) t.getDate_c());
        ste.executeUpdate();

        System.out.println("Comment Ajoutée !!");
    }

    @Override
    public List<comment> afficher() {
        List<comment> comments = new ArrayList<>();
        try {
            sql = "select * from comment";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                comment p = new comment(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5)
                );
                comments.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }

    public comment id_comment(int i) {
        System.out.println("comment methodd!!!!");

        comment l = null;
        try {
            String req = "SELECT id,email ,comment,auteur,date_c FROM comment  WHERE id = " + i + "";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            System.out.println("test methodd!!!!" + i + "");

            if (rs.next()) {
                l = new comment(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5)
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }

    @Override
    public void supprimer(comment t) {
        sql = "delete from comment where id=" + t.getId() + "";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            System.out.println("Comment supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void Modifier(comment t) {
        try {
            String req = "UPDATE comment SET email=?,comment=?,auteur=?,date_c=? WHERE id=" + t.getId() + "";
            PreparedStatement ste = cnx.prepareStatement(req);

            ste.setString(1, t.getEmail());
            ste.setString(2, t.getComment());
            ste.setString(3, t.getAuteur());
            ste.setDate(4, (Date) t.getDate_c());

            ste.executeUpdate();
            System.out.println("Comment Modifée");

        } catch (SQLException ex) {
            Logger.getLogger(commentService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
