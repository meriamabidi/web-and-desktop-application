/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.analyse;
import Entity.labo;
import Services.analyseService;
import java.io.IOException;
import java.net.URL;
import static java.sql.JDBCType.NULL;
import java.util.EnumSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Services.laboService;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class AddAnalyseController implements Initializable {

    laboService sl = new laboService();
    analyseService sa = new analyseService();
    analyse la = new analyse();
    @FXML
    private TextField resultat;
    @FXML
    private TextField prix;
    @FXML
    private Label identifiant;
    @FXML
    private Button ajouterB;
    @FXML
    private ComboBox<labo> id_labo;
    @FXML
    private DatePicker date;
    @FXML
    private Label date_string;

    private List<labo> labos;
    private Label select_pdf;
    @FXML
    private Label label_ajouter_pdf;
    @FXML
    private Label laboErreur;
    @FXML
    private Label resultatErreur;
    @FXML
    private Label prixErreur;
    @FXML
    private Label pdfErreur;
    @FXML
    private Label dateErreur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        date.valueProperty().addListener((observable, oldValue, newValue) -> {
            date_string.setText(newValue.toString());
        });

        List<labo> labos = new ArrayList<labo>();
        labos = sl.afficher();
        ObservableList<labo> list = FXCollections.observableArrayList(labos);
        id_labo.setItems(list);

        id_labo.setCellFactory(param -> new ListCell<labo>() {
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

        // TODO
    }

    public boolean controleChaine(String str) {
        int length = str.length();
        return length >= 5 && length <= 10;
    }

    @FXML
    private void AddAnalyse(MouseEvent event) {

        boolean no = true, blo = true, mail = true, med = true, img = true;

        dateErreur.setText("");
        resultatErreur.setText("");
        pdfErreur.setText("");
        prixErreur.setText("");
        laboErreur.setText("");

        try {
            if (resultat.getText().equals("") || !controleChaine(resultat.getText())) {
                new animatefx.animation.Shake(resultat).play();
                resultatErreur.setText("Resultat obligatoire");
                no = false;
            }

            if (date_string.getText().equals("") || !controleChaine(date_string.getText())) {
                new animatefx.animation.Shake(date).play();
                dateErreur.setText("Date obligatoire");
                med = false;
            }
            if (prix.getText().equals("") || Integer.parseInt(prix.getText()) < 0) {
                new animatefx.animation.Shake(prix).play();
                prixErreur.setText("Prix obligatoire");
                mail = false;
            }
            if (!label_ajouter_pdf.getText().matches("^C.*\\.pdf$")) {
                new animatefx.animation.Shake(label_ajouter_pdf).play();
                pdfErreur.setText("PDF obligatoire");
                img = false;
            }
            if (id_labo.getSelectionModel().getSelectedItem() == null) {
                new animatefx.animation.Shake(id_labo).play();
                laboErreur.setText("Laboratoire obligatoire");
                blo = false;
            }

            if (no && mail && med && img && blo) {

                la.setId(0);
                la.setDate(date_string.getText());
                la.setResultat(resultat.getText());
                la.setImage(label_ajouter_pdf.getText());
                la.setPrix(Integer.parseInt(prix.getText()));
                la.setL(id_labo.getValue());

                sa.ajouter(la);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Ajout avec succee !!");
                alert.showAndWait();
                effacer();

            } else {
         
                System.out.println("inserer donnee!!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void Clear(MouseEvent event) {
        effacer();
    }

    private void effacer() {

        resultat.setText("");
        prix.setText("");
        date.setValue(null);
        id_labo.setValue(null);
        select_pdf.setText("aucun pdf selectionnee");
        label_ajouter_pdf.setText("Ajouter PDF");
    }

    @FXML
    private void addPDF(MouseEvent event) {
        // Ouvrir une boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier PDF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(label_ajouter_pdf.getScene().getWindow());

        // Si un fichier a été sélectionné, mettre à jour le label avec son chemin d'accès
        if (selectedFile != null) {
            label_ajouter_pdf.setText(selectedFile.getAbsolutePath());
        }
    }

}
