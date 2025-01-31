/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.labo;
import Services.laboService;
import Services.ratingService;
import java.net.URL;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class RatingController implements Initializable {

    @FXML
    private ProgressBar bar;
    @FXML
    private ListView<labo> tableview_labo;
    private ProgressIndicator indicator;
    @FXML
    private Label labo;
    @FXML
    private Label average;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showlist();
    }
    laboService ls = new laboService();
    ratingService rs = new ratingService();

    @FXML
    private void select_labo(MouseEvent event) {
        tableview_labo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {

                double averagee = rs.average_rating(newValue.getId());
                average.setText(averagee + " / 5 ");
                bar.setProgress(averagee);
                if (averagee < 2.5) {
                    bar.setStyle("-fx-accent: red;-fx-border-color: #ff0000;");
                } else  if (averagee > 2.5){
                    bar.setStyle("-fx-accent: green; -fx-border-color: #00ff00;");
                }

                labo.setText("" + newValue.getNom() + "  est noter : ");
            } else {

                labo.setText("Choisissez un laboratoire :");
            }

        });

    }

    private void showlist() {
        List<labo> laboList = ls.afficher();
        ObservableList<labo> observableLaboList = FXCollections.observableArrayList(laboList);
        tableview_labo.setItems(observableLaboList);
        tableview_labo.setCellFactory(param -> new ListCell<labo>() {
            @Override
            protected void updateItem(labo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {

                    setText("Laboratoire : " + item.getNom() + " , Bloc "+ item.getBloc());
                          
                }
            }
        });
    }

}
