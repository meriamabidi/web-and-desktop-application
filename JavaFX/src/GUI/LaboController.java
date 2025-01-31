/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.labo;
import Services.laboService;
import com.mysql.jdbc.Connection;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Services.laboService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class LaboController implements Initializable {

    private ComboBox<String> choix;

    /**
     *******************controle de saisir ******************************
     */
    String controleTel = "\\d{8}";
    String controleEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String controlebloc = "^[A-Za-z][1-9]$";
    @FXML
    private Button rechercher_imprimer;

    public boolean controleChaine(String str) {
        int length = str.length();
        return length >= 5 && length <= 10;
    }

    @FXML
    private Label clearBloc;
    @FXML
    private Label clearMail;
    @FXML
    private Label clearMed;
    @FXML
    private Label clearNom;
    @FXML
    private Label clearTel;

    @FXML
    private void ClearBloc(javafx.scene.input.MouseEvent event) {
        bloc.setText("");
    }

    @FXML
    private void CleaNom(javafx.scene.input.MouseEvent event) {
        nom.setText("");
    }

    @FXML
    private void ClearMed(javafx.scene.input.MouseEvent event) {
        medecin.setText("");
    }

    @FXML
    private void CleaMail(javafx.scene.input.MouseEvent event) {
        email.setText("");
    }

    @FXML
    private void CleaTel(javafx.scene.input.MouseEvent event) {
        telephone.setText("");
    }

    /**
     *******************labo service and labo
     * entities******************************
     */
    labo la = new labo();
    laboService sl = new laboService();

    /**
     *******************FXML FXML ******************************
     */
    @FXML
    private Label file_path;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label identifiant;
    @FXML
    private TextField bloc;
    @FXML
    private TextField nom;
    @FXML
    private TextField medecin;
    @FXML
    private TextField telephone;
    @FXML
    private TextField email;
    @FXML
    private Label erreur;
    /**
     ******************* BUTTON ******************************
     */
    @FXML
    private Button insertImage;
    @FXML
    private Button modifierB;
    @FXML
    private Button supprimerB;

    /**
     ******************* TABLE VIEW ******************************
     */
    @FXML
    private TableView<labo> laboTable;
    @FXML
    private TableColumn<labo, Integer> id_col;
    @FXML
    private TableColumn<labo, String> nom_col;
    @FXML
    private TableColumn<labo, String> bloc_col;
    @FXML
    private TableColumn<labo, String> email_col;
    @FXML
    private TableColumn<labo, String> med_col;
    @FXML
    private TableColumn<labo, Integer> tel_col;

    /**
     ******************* ACTIONS FXML ******************************
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (event.getSource() == modifierB) {

            update();
        }

        if (event.getSource() == supprimerB) {
            delete();
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        showLabo();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/desktop", "root", "");
            return conn;
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            return null;

        }
    }

    public ObservableList<labo> getLaboList() {
        ObservableList<labo> laboList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM labo";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            labo l;
            while (rs.next()) {
                l = new labo(rs.getInt("id"), rs.getString("nom"), rs.getString("bloc"), rs.getString("mail"), rs.getInt("tel"), rs.getString("img"), rs.getString("med"), rs.getDouble("average_rating"));
                laboList.add(l);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return laboList;
    }

    public void showLabo() {
        ObservableList<labo> list = getLaboList();
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        bloc_col.setCellValueFactory(new PropertyValueFactory<>("bloc"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tel_col.setCellValueFactory(new PropertyValueFactory<>("tel"));
        med_col.setCellValueFactory(new PropertyValueFactory<>("med"));

        laboTable.setItems(list);

    }

    @FXML
    public void insertImage() throws IOException {

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

    private void update() {
        if (nom.getText().equals("") || bloc.getText().equals("") || email.getText().equals("") || medecin.getText().equals("") || telephone.getText().equals("")) {
            erreur.setText("Entrez tous les champs vides!");

            Alert alert = new Alert(AlertType.ERROR);

            alert.setTitle("ERREUR");
            alert.setHeaderText(null);
            alert.setContentText("Entrez tous les champs vides!");
            alert.showAndWait();

        } else {

            if (controleChaine(nom.getText()) && bloc.getText().matches(controlebloc) && email.getText().matches(controleEmail) && controleChaine(medecin.getText()) && telephone.getText().matches(controleTel)) {

                String path = file_path.getText();

                path = path.replace("\\", "\\\\");

                la.setId(Integer.parseInt(identifiant.getText()));
                //new animatefx.animation.Shake(nom).play();
                la.setNom(nom.getText());
                la.setBloc(bloc.getText());
                la.setMail(email.getText());
                la.setTel(Integer.parseInt(telephone.getText()));
                la.setImg(path);
                la.setMed(medecin.getText());

                try {
                    sl.Modifier(la);
                    showLabo();
                    Alert alert = new Alert(AlertType.INFORMATION);

                    alert.setTitle("SUCCES");
                    alert.setHeaderText(null);
                    alert.setContentText("Mettez à jour les données avec succès !");
                    alert.showAndWait();
                    erreur.setText("");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());

                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez vérifier vos informations!");
                alert.showAndWait();
                erreur.setText("Veuillez vérifier vos informations!");
            }
        }
    }

    private void delete() {

        la.setId(Integer.parseInt(identifiant.getText()));
        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Message de Confirmation ");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir le supprimer?");

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get() == ButtonType.OK) {
                sl.supprimer(la);
            } else {

                return;
            }
            showLabo();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void MouseAction(javafx.scene.input.MouseEvent event) {

        labo l = laboTable.getSelectionModel().getSelectedItem();

        identifiant.setText("" + l.getId());
        nom.setText(l.getNom());
        email.setText(l.getMail());
        bloc.setText(l.getBloc());
        medecin.setText(l.getMed());
        file_path.setText(l.getImg());
        telephone.setText("" + l.getTel());

        String picture = "http://localhost/img/" + l.getImg();
        System.out.println(picture);
        Image image = new Image(picture, 110, 110, false, true);

        imageView.setImage(image);

        imageView.setImage(image);

        String path = l.getImg();

        file_path.setText(path);

    }

    @FXML
    private void page_rechercher(javafx.scene.input.MouseEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/recherche_Labo.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage newStage = new Stage();
            newStage.setScene(addStudentScene);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
