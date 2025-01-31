/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class HomeController implements Initializable {

    @FXML
    private Button analyse;
    @FXML
    private Button laboratoire;
    @FXML
    private TabPane anchorPane;
    @FXML
    private Tab ajouter;
    @FXML
    private Tab consulter;
    @FXML
    private Button rating;
    @FXML
    private Button stat;
    @FXML
    private Button comment;
    @FXML
    private Button local;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void ajouter_labo(ActionEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/addLabo.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(addStudentScene);
            currentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void consulter_labo(ActionEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/labo.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(addStudentScene);
            currentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ajouter_analyse(ActionEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/addAnalyse.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(addStudentScene);
            currentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void consulter_analyse(ActionEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/analyses.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(addStudentScene);
            currentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void analyseButton(MouseEvent event) {

    try {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/GUI/addAnalyse.fxml"));
        AnchorPane fileRoot1 = fxmlLoader1.load();
        ajouter.setContent(fileRoot1);

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/GUI/analyses.fxml"));
        AnchorPane fileRoot2 = fxmlLoader2.load();
        consulter.setContent(fileRoot2);

        anchorPane.getTabs().clear();
        anchorPane.getTabs().add(ajouter);
        anchorPane.getTabs().add(consulter);

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void laboratoireButton(MouseEvent event) {
        
         try {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/GUI/addLabo.fxml"));
        AnchorPane fileRoot1 = fxmlLoader1.load();
        ajouter.setContent(fileRoot1);

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/GUI/labo.fxml"));
        AnchorPane fileRoot2 = fxmlLoader2.load();
        consulter.setContent(fileRoot2);

        anchorPane.getTabs().clear();
        anchorPane.getTabs().add(ajouter);
        anchorPane.getTabs().add(consulter);

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void ratingButton(MouseEvent event) {
           try {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/GUI/rating.fxml"));
        AnchorPane fileRoot1 = fxmlLoader1.load();

        Tab tab1 = new Tab("Rating");
        tab1.setContent(fileRoot1);

        anchorPane.getTabs().clear();

        anchorPane.getTabs().add(tab1);

    } catch (IOException e) {
        e.printStackTrace();
    }
        
    }

    @FXML
    private void statButton(MouseEvent event) {
            try {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/GUI/stat.fxml"));
        AnchorPane fileRoot1 = fxmlLoader1.load();

        Tab tab1 = new Tab("Statistiques");
        tab1.setContent(fileRoot1);

        anchorPane.getTabs().clear();

        anchorPane.getTabs().add(tab1);

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void commentButton(MouseEvent event) {
            try {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/GUI/comment.fxml"));
        AnchorPane fileRoot1 = fxmlLoader1.load();

        Tab tab1 = new Tab("Commentaires");
        tab1.setContent(fileRoot1);

        anchorPane.getTabs().clear();

        anchorPane.getTabs().add(tab1);

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void localButton(MouseEvent event) {
               try {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/GUI/local.fxml"));
        AnchorPane fileRoot1 = fxmlLoader1.load();

        Tab tab1 = new Tab("Commentaires");
        tab1.setContent(fileRoot1);

        anchorPane.getTabs().clear();

        anchorPane.getTabs().add(tab1);

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    


}
