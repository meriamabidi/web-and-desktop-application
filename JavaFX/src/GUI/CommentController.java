package GUI;

import Entity.comment;
import Entity.reply;
import Services.commentService;
import Services.replyService;
import com.mysql.jdbc.Connection;

import java.net.URL;
import java.sql.DriverManager;
import static java.sql.JDBCType.NULL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CommentController implements Initializable {

    @FXML
    private ScrollPane commentsScrollPane;
    @FXML
    private VBox commentsVBox;
    @FXML
    private AnchorPane parent;

    private commentService cc = new commentService();
    private replyService rr = new replyService();
    @FXML
    private ComboBox<comment> coment_box;
    @FXML
    private TextField labelNom;
    @FXML
    private Button saveB;
    @FXML
    private TextField labelR;
    @FXML
    private Label commentErreur;
    @FXML
    private Label nomErreur;
    @FXML
    private Label reponseErreur;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        displayComments();
        box();

    }

    public void box() {

        List<comment> commentsList = cc.afficher();
        List<comment> comments = new ArrayList<comment>();
        for (comment comment : commentsList) {
            reply r = rr.id_reply(comment.getId());
            if (r == null) {
                comments.add(comment);
            }

        }

        ObservableList<comment> list = FXCollections.observableArrayList(comments);
        coment_box.setItems(list);
    }

    @FXML
    public void reponse(MouseEvent event) {
        replyService sr = new replyService();
        reply r = new reply();
        boolean no = true, blo = true, med = true;

        try {
            if (labelNom.getText().equals("")) {
                new animatefx.animation.Shake(labelNom).play();
                nomErreur.setText("Nom obligatoire");
                no = false;
            }

            if (labelR.getText().equals("")) {
                new animatefx.animation.Shake(labelR).play();
                reponseErreur.setText("Reponse obligatoire");
                med = false;
            }

            if (coment_box.getSelectionModel().getSelectedItem() == null) {
                new animatefx.animation.Shake(coment_box).play();
                commentErreur.setText("Commentaire obligatoire");
                blo = false;
            }

            if (no && med && blo) {

                r.setId(0);
                r.setNom(labelNom.getText());
                r.setReponse(labelR.getText());
                r.setC(coment_box.getValue().getId());

                sr.ajouter(r);

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

    public void effacer() {
        labelNom.setText("");
        labelR.setText("");
        commentErreur.setText("");
        nomErreur.setText("");
        reponseErreur.setText("");
        coment_box.setValue(null);
        displayComments();
        box();

    }

    public void displayComments() {

        List<comment> commentsList = cc.afficher();
        commentsVBox.getChildren().clear();

        for (comment comment : commentsList) {

            VBox commentVBox = new VBox();
            commentVBox.setPadding(new Insets(10));
            commentVBox.setSpacing(10);

            Label commentTextLabel = new Label("Commentaire de " + comment.getAuteur() + "(" + comment.getEmail() + ") le "+comment.getDate_c().toString()+" :");
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

                Label replyTextLabel = new Label(r.getReponse() + " ( répondu par " + r.getNom() + ")");
                replyTextLabel.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                replyVBox.getChildren().add(replyTextLabel);

                commentVBox.getChildren().add(replyVBox);
            } else {
                Label noReplyLabel = new Label("Pas encore de réponse.");
                noReplyLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                commentVBox.getChildren().add(noReplyLabel);
            }

            commentsVBox.getChildren().add(commentVBox);
        }
    }

}
