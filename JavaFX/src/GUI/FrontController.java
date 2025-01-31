/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.analyse;
import Entity.labo;
import Entity.local;
import Entity.rating;
import Services.analyseService;
import Services.laboService;
import Services.localService;
import Services.ratingService;
import static com.itextpdf.text.pdf.PdfName.LOCATION;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class FrontController implements Initializable {

    labo la = new labo();
    laboService sl = new laboService();
    analyseService sa = new analyseService();
    analyse an = new analyse();
    @FXML
    private Pagination pagination;
    @FXML
    private Pane affichagePane;
    @FXML
    private Label nom_labo;
    @FXML
    private Label bloc_labo;
    @FXML
    private Label mail_labo;
    @FXML
    private Label tel_labo;
    @FXML
    private Label med_labo;
    @FXML
    private ImageView img_labo;
    @FXML
    private TreeView<String> treeView_analyse;
    @FXML
    private Label date_analyse;
    @FXML
    private Label resultat_analyse;
    @FXML
    private Label prix_analyse;
    @FXML
    private Label pdf_analyse;
    @FXML
    private Label date;
    @FXML
    private Label resultat;
    @FXML
    private Label prix;
    @FXML
    private Label pdf;
    @FXML
    private Label analyse;
    @FXML
    private Button rate;
    @FXML
    private AnchorPane anchorpane_star;
    @FXML
    private Label id_labo;

    ratingService rs = new ratingService();
    @FXML
    private Label nbr;
    @FXML
    private Button localB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<labo> list_labo = sl.afficher();
        pagination.setPageCount(list_labo.size());
        pagination.setCurrentPageIndex(0);
        pagination();
        showstar();

    }

    private static final int num = 5;
    private static final int taille = 50;

    private ImageView[] starImageViews = new ImageView[num];

    public void showstar() {

        Image starEmpty = new Image("file:\\C:\\Users\\Maryem\\Desktop\\pidevJAVAFX\\images\\star0.png");
        Image starFilled = new Image("file:\\C:\\Users\\Maryem\\Desktop\\pidevJAVAFX\\images\\star1.png");
        

        for (int i = 0; i < num; i++) {
            ImageView starImageView = createImageView(starEmpty, i * taille, 0, Pos.CENTER);
            starImageViews[i] = starImageView;
            anchorpane_star.getChildren().add(starImageView);
        }

        
        for (ImageView starImageView : starImageViews) {
            starImageView.setOnMouseClicked(this::handleStarClick);
        }

    }

    private ImageView createImageView(Image image, double x, double y, Pos alignment) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(taille);
        imageView.setFitHeight(taille);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setPickOnBounds(true);
        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, x);
        return imageView;
    }

    private void handleStarClick(MouseEvent event) {
        int nbstar = 0;

        Image starEmpty = new Image("file:\\C:\\Users\\Maryem\\Desktop\\pidevJAVAFX\\images\\star0.png");
        Image starFilled = new Image("file:\\C:\\Users\\Maryem\\Desktop\\pidevJAVAFX\\images\\star1.png");
        ImageView clickedStarImageView = (ImageView) event.getSource();
        boolean isFilled = clickedStarImageView.getImage().equals(starEmpty);
        for (int i = 0; i < num; i++) {
            ImageView starImageView = starImageViews[i];

            if (isFilled) {
                starImageView.setImage(starEmpty);

            } else {
                starImageView.setImage(starFilled);
            }
            if (starImageView == clickedStarImageView) {
                isFilled = !isFilled;

            }
        }
        for (int i = 0; i < num; i++) {
            ImageView starImageView = starImageViews[i];

            if (starImageView.getImage().equals(starFilled)) {
                nbstar++;
            }
        }
        nbr.setText(nbstar + "");

    }

    @FXML
    private void rateButton(MouseEvent event) throws SQLException {

        Image starFilled = new Image("file:\\C:\\Users\\Maryem\\Desktop\\pidevJAVAFX\\images\\star1.png");

        int nbstar = Integer.parseInt(nbr.getText());

        int id = Integer.parseInt(id_labo.getText());

        labo la = sl.id_labo(id);

        rating l = new rating(nbstar, la);
        rs.ajouter(l);
Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Merci pour votre note !!");
                alert.showAndWait();

        resetStars();
    }

    private void resetStars() {
        Image starEmpty = new Image("file:\\C:\\Users\\Maryem\\Desktop\\pidevJAVAFX\\images\\star0.png");

        for (int i = 0; i < num; i++) {
            starImageViews[i].setImage(starEmpty);
        }
        nbr.setText("0");

    }

    public void pagination() {
        List<labo> list_labo = sl.afficher();
        pagination.setPageFactory((pageIndex) -> {
            if (pageIndex >= 0 && pageIndex < list_labo.size()) {
                labo labo = list_labo.get(pageIndex);
                clear();
                resetStars();
                return affichage(labo);
            } else {
                return null;
            }
        });
    }

    public Node affichage(labo labo) {
        id_labo.setText(labo.getId() + "");
        nom_labo.setText(labo.getNom());
        bloc_labo.setText(labo.getBloc());
        mail_labo.setText(labo.getMail());
        tel_labo.setText(String.valueOf(labo.getTel()));
        med_labo.setText(labo.getMed());
               
String picture = "http://localhost/img/" + labo.getImg();
    System.out.println(picture);
    Image image = new Image(picture, 110, 110, false, true);
        img_labo.setImage(image);

        List<analyse> list_analyse = sa.analyses_labo(labo.getId());
        if (list_analyse.isEmpty()) {
            TreeItem<String> rootItem = new TreeItem<>("Aucun analyse trouver . ");
            treeView_analyse.setRoot(rootItem);
            clear();
            return affichagePane;
        } else {
           
            TreeItem<String> rootItem = new TreeItem<>("Analyses :");

          
            for (analyse analyse : list_analyse) {
                String date = analyse.getDate();
                if (date != null) {
                    TreeItem<String> item = new TreeItem<>(date);
                    rootItem.getChildren().add(item);
                }
            }

           
            treeView_analyse.setRoot(rootItem);

            treeView_analyse.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue == null) {
                          
                            return;
                        }
                        String i = newValue.getValue();
                        analyse a = sa.id_analyse(i);
                        if (a == null) {
                      
                            return;
                        }
                       
                        prix_analyse.setText(a.getPrix() + "");
                        date_analyse.setText(a.getDate());
                        resultat_analyse.setText(a.getResultat());
                        pdf_analyse.setText(a.getImage());

                        date.setText("Date :");
                        resultat.setText("Resultat :");
                        prix.setText("Prix :");
                        pdf.setText("PDF :");
                        analyse.setText("Detail Analyse :");

                    });
        }
        return affichagePane;
    }

    @FXML
    private void open_pdf(MouseEvent event) {

        String pdfFilePath = pdf_analyse.getText();
        File file = new File(pdfFilePath);

        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not found");
            alert.setHeaderText(null);
            alert.setContentText("The specified file does not exist.");
            alert.showAndWait();
        }
    }

    private void clear() {

        prix_analyse.setText("");
        date_analyse.setText("");
        resultat_analyse.setText("");
        pdf_analyse.setText("");

        date.setText("");
        resultat.setText("");
        prix.setText("");
        pdf.setText("");
        analyse.setText("");

    }

    @FXML
    private void comment(ActionEvent event) {
        
     try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/FrontComment.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage newStage = new Stage();
            newStage.setScene(addStudentScene);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    localService ls = new localService();
         private final String OSM_API = "https://www.openstreetmap.org/export/embed.html?bbox=";
     
    @FXML
    private void showLocalisation(MouseEvent event) {
             int id = Integer.parseInt(id_labo.getText());
        local a = ls.id_local(id);
if(a!=null){
             final String LOCATION = a.getLocal(); // replace with your actual location

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        String[] locationParts = LOCATION.split(",");
        double lon = Double.parseDouble(locationParts[0]);
        double lat = Double.parseDouble(locationParts[1]);
        double bboxSize = 0.001; // adjust this value to change the zoom level

String url = OSM_API + (lon - bboxSize) + "," + (lat - bboxSize) + "," + (lon + bboxSize) + "," + (lat + bboxSize) + "&marker=" + lat + "," + lon;
        webEngine.load(url);

        StackPane root = new StackPane(webView);
        Scene scene = new Scene(root, 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Map Example");
        stage.setScene(scene);
        stage.show();
}else{
 Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Localisation n'existe pas!");
                alert.showAndWait();

}
        
    }
    }


