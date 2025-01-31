/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.comment;
import Entity.reply;
import Services.commentService;
import Services.replyService;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class FrontCommentController implements Initializable {

    @FXML
    private ScrollPane commentsScrollPane;
    @FXML
    private VBox commentsVBox;
    @FXML
    private TextField auteur;
    @FXML
    private TextField mail;
    @FXML
    private TextField comment;
    @FXML
    private Label date;
    @FXML
    private Button save;

    /**
     * Initializes the controller class.
     */
    private commentService cc = new commentService();
    private replyService rr = new replyService();

    public void displayComments() {

        List<comment> commentsList = cc.afficher();
        commentsVBox.getChildren().clear();

        for (comment comment : commentsList) {

            VBox commentVBox = new VBox();
            commentVBox.setPadding(new Insets(10));
            commentVBox.setSpacing(10);

            Label commentTextLabel = new Label("Commentaire de " + comment.getAuteur() + " le " + comment.getDate_c().toString() + " :");
            Label commentLabel = new Label(comment.getComment());

            commentVBox.getChildren().add(commentTextLabel);
            commentVBox.getChildren().add(commentLabel);
            commentLabel.setStyle("-fx-font-weight: bold;");

            System.out.println(comment.toString());

            reply r = rr.id_reply(comment.getId());
            if (r != null) {
                VBox replyVBox = new VBox();
                replyVBox.setPadding(new Insets(10));
                replyVBox.setSpacing(5);

                Label replyTextLabel = new Label("réponse : " + r.getReponse());
                replyTextLabel.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                replyVBox.getChildren().add(replyTextLabel);

                commentVBox.getChildren().add(replyVBox);
            } else {
                Label noReplyLabel = new Label("Nous vous répondrons bientôt :) ");
                noReplyLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                commentVBox.getChildren().add(noReplyLabel);
            }

            commentsVBox.getChildren().add(commentVBox);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDate currentDate = LocalDate.now();
        date.setText(currentDate.toString());
        displayComments();
    }

    @FXML
    private void addComment(ActionEvent event) {

        comment r = new comment();
        boolean no = true, blo = true, med = true;

        try {
            if (comment.getText().equals("")) {
                new animatefx.animation.Shake(comment).play();
                no = false;
            }

            if (mail.getText().equals("")) {
                new animatefx.animation.Shake(mail).play();
                med = false;
            }

            if (auteur.getText().equals("")) {
                new animatefx.animation.Shake(auteur).play();
                blo = false;
            }

            if (no && med && blo) {
                LocalDate currentDate = LocalDate.now();
                r.setAuteur(auteur.getText());
                r.setEmail(mail.getText());
                String commentText = comment.getText();
                String[] restrictedWords = {"bad", "offensive", "inappropriate"};
                String regex = "\\b(" + String.join("|", restrictedWords) + ")\\b";
                String sanitizedComment = commentText.replaceAll(regex, "***");
                r.setComment(sanitizedComment);

                Date date = java.sql.Date.valueOf(currentDate);
                r.setDate_c(date);
                cc.ajouter(r);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Ajout avec succee !!");
                alert.showAndWait();
                effacer();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("ERREUR");
                alert.setHeaderText(null);
                alert.setContentText("Inserer donnee !!");
                alert.showAndWait();
                System.out.println("inserer donnee!!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void effacer() {
        comment.setText("");
        mail.setText("");
        auteur.setText("");
        displayComments();

    }

}
