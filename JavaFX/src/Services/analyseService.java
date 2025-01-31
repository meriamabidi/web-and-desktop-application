/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Services.laboService;
import Entity.analyse;
import Entity.labo;
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
public class analyseService implements ServiceInterface<analyse> {
    laboService ls = new laboService();

    Connection cnx;
    String sql = "";

    public analyseService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(analyse t) throws SQLException {
        sql = "insert into analyse(date,resultat,image,prix,laboo_id) values (?,?,?,?,?)";
        PreparedStatement ste = cnx.prepareStatement(sql);

        ste.setString(1, t.getDate());
        ste.setString(2, t.getResultat());
        ste.setString(3, t.getImage());
        ste.setInt(4, t.getPrix());
        ste.setInt(5, t.getL().getId());
        ste.executeUpdate();

        System.out.println("Analyse Ajoutée !!");
    }

    @Override
    public List<analyse> afficher() {
        List<analyse> analyses = new ArrayList<>();
        try {
            sql = "select * from analyse";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                analyse p = new analyse(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        ls.id_labo(rs.getInt(6))
                );
                analyses.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return analyses;
    }
    @Override
    public void supprimer(analyse t) {
  sql = "delete from analyse where id=" + t.getId() + "";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            System.out.println("Analyse supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void Modifier(analyse t) {
        try {
            String req = "UPDATE analyse SET date=?,resultat=?,image=?,prix=?,laboo_id=? WHERE id=" + t.getId() + "";
            PreparedStatement ste = cnx.prepareStatement(req);

            ste.setString(1, t.getDate());
            ste.setString(2, t.getResultat());
            ste.setString(3, t.getImage());
            ste.setInt(4, t.getPrix());
            ste.setInt(5, t.getL().getId());

            ste.executeUpdate();
            System.out.println("Analyse Modifée");

        } catch (SQLException ex) {
            Logger.getLogger(analyseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public analyse id_analyse(String i)
    {
      analyse p = null;
    try {
        String sql = "select id,date,resultat,image,prix,laboo_id FROM analyse WHERE date=?";
        PreparedStatement pstmt = cnx.prepareStatement(sql);
        pstmt.setString(1, i);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            p = new analyse(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    ls.id_labo(rs.getInt(6))
            );
        }
        rs.close();
        pstmt.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return p;
    }
    
    
    
   public List<analyse> analyses_labo(int i)
    {
         List<analyse> analyses = new ArrayList<>();
        try {
            sql = "select * from analyse WHERE laboo_id="+i+"";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                analyse p = new analyse(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        ls.id_labo(rs.getInt(6))
                );
                analyses.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return analyses;
    
    }

}


