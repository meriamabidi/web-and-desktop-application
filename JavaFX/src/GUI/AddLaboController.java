/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.labo;
import Services.laboService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class AddLaboController implements Initializable {

    labo la = new labo();
    laboService sl = new laboService();

    @FXML
    private ImageView imageView;
    @FXML
    private Button insertImage;
    @FXML
    private TextField nom;
    @FXML
    private TextField bloc;
    @FXML
    private TextField email;
    @FXML
    private TextField telephone;
    @FXML
    private TextField medecin;
    @FXML
    private Label file_path;
    @FXML
    private Label identifiant;
    @FXML
    private Button ajouterB;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label page_init;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        effacer();
    }

    @FXML
    private Label mailErreur;
    @FXML
    private Label medErreur;
    @FXML
    private Label nomErreur;
    @FXML
    private Label telErreur;
    @FXML
    private Label imgErreur;
    @FXML
    private Label blocErreur;

    public boolean controleChaine(String str) {
        int length = str.length();
        return length >= 5 && length <= 10;
    }

    @FXML
    private void AddLabo(MouseEvent event) {

        boolean no = true, blo = true, mail = true, tel = true, med = true, img = true;

        nomErreur.setText("");
        blocErreur.setText("");
        telErreur.setText("");
        medErreur.setText("");
        imgErreur.setText("");
        mailErreur.setText("");

        try {
            if (nom.getText().equals("") || !controleChaine(nom.getText())) {
                new animatefx.animation.Shake(nom).play();
                nomErreur.setText("Prénom obligatoire");
                no = false;
            }
            if (bloc.getText().equals("") || !bloc.getText().matches("^[A-Za-z][1-9]$")) {
                new animatefx.animation.Shake(bloc).play();
                blocErreur.setText("Bloc obligatoire");
                blo = false;
            }
            if (telephone.getText().equals("") || !telephone.getText().matches("\\d{8}")) {
                new animatefx.animation.Shake(telephone).play();
                telErreur.setText("Telephone obligatoire");
                tel = false;
            }
            if (medecin.getText().equals("") || !controleChaine(medecin.getText())) {
                new animatefx.animation.Shake(medecin).play();
                medErreur.setText("Medecin obligatoire");
                med = false;
            }
            if (email.getText().equals("") || !email.getText().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                new animatefx.animation.Shake(email).play();
                mailErreur.setText("E-mail obligatoire");
                mail = false;
            }
            if (file_path.getText().equals("")) {
                new animatefx.animation.Shake(imageView).play();
                imgErreur.setText("Image obligatoire");
                img = false;
            }

            if (no && blo && mail && med && tel && img) {

                la.setId(0);
                la.setNom(nom.getText());
                la.setBloc(bloc.getText());
                la.setMail(email.getText());
                la.setTel(Integer.parseInt(telephone.getText()));
                la.setImg(file_path.getText());
                la.setMed(medecin.getText());
                la.setAverage_rating(0);

                sl.ajouter(la);

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
        telephone.setText("");
        email.setText("");
        medecin.setText("");
        nom.setText("");
        bloc.setText("");
        file_path.setText("");
        imageView.setImage(null);

    }

    @FXML
    private void backPage(MouseEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/labo.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(addStudentScene);
            currentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddLaboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void insertImage(MouseEvent event) throws IOException {

        FileChooser open = new FileChooser();

        Stage stage = (Stage) AnchorPane.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if (file != null) {

            String fileName = file.getName();
            String targetPath = "C:/xampp/htdocs/img/" + fileName;
            Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);

            file_path.setText(fileName);
            Image image = new Image(file.toURI().toString(), 200, 200, false, true);
            imageView.setImage(image);

        } else {

            System.out.println("NO DATA EXIST!");

        }
    }

    @FXML
    private void page_initiale(MouseEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/home.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(addStudentScene);
            currentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
