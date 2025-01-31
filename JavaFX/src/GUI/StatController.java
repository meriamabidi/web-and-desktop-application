/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.analyse;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import tools.MaConnexion;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class StatController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private BarChart<String, Integer> barChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chart(barChart);

    }

    Connection cnx;
    String sql = "";

    public void chart(BarChart<String, Integer> barChart) {
        cnx = MaConnexion.getInstance().getCnx();
        sql = "select date, SUM(prix) From analyse GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 10";

        try {
            XYChart.Series chartData = new XYChart.Series();
            PreparedStatement pstmt = cnx.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                chartData.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
            }
            barChart.getData().add(chartData);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
