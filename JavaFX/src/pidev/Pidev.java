/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import Entity.labo;
import GUI.*;
import Services.laboService;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Maryem
 */
public class Pidev extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
           //    root = FXMLLoader.load(getClass().getResource("/GUI/map.fxml"));

              // root = FXMLLoader.load(getClass().getResource("/GUI/home.fxml"));
       root = FXMLLoader.load(getClass().getResource("/GUI/front.fxml"));

        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
