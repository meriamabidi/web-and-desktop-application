/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.analyse;
import Entity.comment;
import Entity.labo;
import Entity.local;
import Entity.reply;
import Services.laboService;
import Services.localService;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class LocalController implements Initializable {

    laboService sl = new laboService();
    localService la = new localService();
    local lo = new local();

    @FXML
    private TextField latitude;
    @FXML
    private TextField longitude;
    @FXML
    private ComboBox<labo> laboratoires;
    @FXML
    private TableColumn<local, String> local_col;
    @FXML
    private TableColumn<local, String> labo_col;
    @FXML
    private Button ajouterB;
    @FXML
    private Button modifierB;
    @FXML
    private Button supprimerB;
    @FXML
    private Label id_local;
    @FXML
    private TableView<local> localTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
     
        box();

        laboratoires.setCellFactory(param -> new ListCell<labo>() {
            @Override
            protected void updateItem(labo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.toString() == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    }
    
      public void box() {

          List<labo> labosList = new ArrayList<labo>();
        labosList = sl.afficher();
       List<labo> labos = new ArrayList<labo>();
        
        for (labo labo : labosList) {
            local r = la.id_local(labo.getId());
            if (r == null) {
                labos.add(labo);
            }

        }

       ObservableList<labo> list = FXCollections.observableArrayList(labos);
        laboratoires.setItems(list);
    }

    @FXML
    private void ajouter(ActionEvent event) {

        boolean no = true, blo = true, med = true;
        String pattern = "^\\d+(\\.\\d+)?$";

        try {
            if (longitude.getText().equals("") || !longitude.getText().matches(pattern)) {
                new animatefx.animation.Shake(longitude).play();
                no = false;
            }

            if (latitude.getText().equals("") || !latitude.getText().matches(pattern)) {
                new animatefx.animation.Shake(latitude).play();
                med = false;
            }
            if (laboratoires.getSelectionModel().getSelectedItem() == null) {
                new animatefx.animation.Shake(laboratoires).play();
                blo = false;
            }

            if (no && med && blo) {
                //String LOCATION = longitudeValue + "," + latitudeValue;
                String s = longitude.getText() + "," + latitude.getText();
                lo.setId(0);
                lo.setLocal(s);

                lo.setL(laboratoires.getValue());

                la.ajouter(lo);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Ajout avec succee !!");
                alert.showAndWait();
                effacer();
                        box();

                show();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez vérifier vos informations!");
                alert.showAndWait();

                System.out.println("Veuillez vérifier vos informations!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void effacer() {

        longitude.setText("");
        latitude.setText("");
        laboratoires.setValue(null);
    }

    public void show() {
        List<local> list = la.afficher();
        ObservableList<local> localList = FXCollections.observableArrayList(list);

        local_col.setCellValueFactory(new PropertyValueFactory<>("local"));

        labo_col.setCellValueFactory(cellData -> {
            labo la = cellData.getValue().getL();
            return new SimpleStringProperty(la.getNom());
        });
        localTable.setItems(localList);

    }

    @FXML
    private void MouseAction(MouseEvent event) {
        local a = localTable.getSelectionModel().getSelectedItem();
        id_local.setText("" + a.getId());
        int commaIndex = a.getLocal().indexOf(",");
        latitude.setText(a.getLocal().substring(0, commaIndex));
        longitude.setText(a.getLocal().substring(commaIndex + 1));

        laboratoires.setValue(a.getL());
    }

    @FXML
    private void modifier(ActionEvent event) {

        boolean no = true, blo = true, med = true;
        String pattern = "^\\d+(\\.\\d+)?$";

        if (longitude.getText().equals("") || !longitude.getText().matches(pattern)) {
            new animatefx.animation.Shake(longitude).play();
            no = false;
        }

        if (latitude.getText().equals("") || !latitude.getText().matches(pattern)) {
            new animatefx.animation.Shake(latitude).play();
            med = false;
        }
        if (laboratoires.getSelectionModel().getSelectedItem() == null) {
            new animatefx.animation.Shake(laboratoires).play();
            blo = false;
        }

        if (no && med && blo) {
            lo.setId(Integer.parseInt(id_local.getText()));

            String s = longitude.getText() + "," + latitude.getText();
            
            lo.setLocal(s);
            lo.setL(laboratoires.getValue());

            try {
                la.Modifier(lo);
                show();
                        box();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("SUCCES");
                alert.setHeaderText(null);
                alert.setContentText("Mettez à jour les données avec succès !");
                alert.showAndWait();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("ERREUR");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier vos informations!");
            alert.showAndWait();

        }

    }

    @FXML
    private void supprimer(ActionEvent event) {

        lo.setId(Integer.parseInt(id_local.getText()));
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Message de Confirmation ");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir le supprimer?");

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get() == ButtonType.OK) {
                        

                la.supprimer(lo);
                show();
                box();
            } else {

                return;
            }
            show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
