/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.comment;
import Entity.reply;
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
public class replyService implements ServiceInterface<reply> {
    commentService cc;

    Connection cnx;
    String sql = "";

    public replyService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(reply t) throws SQLException {
        sql = "insert into reply(nom ,reponse,comment_id) values (?,?,?)";
        PreparedStatement ste = cnx.prepareStatement(sql);

        ste.setString(1, t.getNom());
        ste.setString(2, t.getReponse());
        ste.setInt(3, t.getC());
        ste.executeUpdate();

        System.out.println("reponse Ajoutée !!");

    }

    @Override
    public List<reply> afficher() {
        List<reply> replys = new ArrayList<>();
        try {
            sql = "select * from reply";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                reply p = new reply(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                );
                replys.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return replys;
    }

    @Override
    public void supprimer(reply t) {
        sql = "delete from reply where id=" + t.getId() + "";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            System.out.println("reponse supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Modifier(reply t) {

        try {
            String req = "UPDATE reply SET nom=? ,reponse=?,comment_id=? WHERE id=" + t.getId() + "";
            PreparedStatement ste = cnx.prepareStatement(req);

            ste.setString(1, t.getNom());
            ste.setString(2, t.getReponse());
            ste.setInt(3, t.getC());

            ste.executeUpdate();
            System.out.println("reponse Modifée");

        } catch (SQLException ex) {
            Logger.getLogger(replyService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public reply id_reply(int i) {

        reply s=null;
        try {

            String req = "SELECT id,nom ,reponse,comment_id  FROM reply  WHERE comment_id  = " + i + "";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);

            if (rs.next()) {
                int replyId = rs.getInt(1);
                String replyEmail = rs.getString("nom");
                String replyContent = rs.getString("reponse");
                int replyCommentId = rs.getInt("comment_id");

                System.out.println("test" + replyId + " " + replyEmail + " " + replyContent + " " + replyCommentId + " ");
                s = new reply(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4));
                System.out.println(s.toString());

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
    }

}
