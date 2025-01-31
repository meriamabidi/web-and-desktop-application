/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.analyse;
import Entity.labo;
import Services.analyseService;
import Services.laboService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mysql.jdbc.Connection;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class AnalysesController implements Initializable {

    @FXML
    private TableView<analyse> analyseTable;
    @FXML
    private ComboBox<labo> laboratoire;
    @FXML
    private Label pdf;
    @FXML
    private Button modifierB;
    @FXML
    private Button supprimerB;

    laboService sl = new laboService();
    labo la = new labo();
    analyse an = new analyse();
    analyseService sa = new analyseService();
    @FXML
    private Label identifiant;
    @FXML
    private TableColumn<analyse, String> resultat_col;
    @FXML
    private TableColumn<analyse, String> date_col;
    @FXML
    private TableColumn<analyse, Integer> prix_col;
    @FXML
    private TableColumn<analyse, String> labo_col;
    @FXML
    private TableColumn<analyse, String> pdf_col;
    @FXML
    private TextField resultat;
    @FXML
    private TextField prix;
    @FXML
    private Label new_pdf;
    @FXML
    private DatePicker date_picker;
    private Label idL;
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
    @FXML
    private Label date_string;
    @FXML
    private ComboBox<String> choix_tri;
    @FXML
    private CheckBox croiCheckBox;
    @FXML
    private CheckBox decroiCheckBox;
    @FXML
    private Button trierB;
    @FXML
    private Label choixT_label;
    @FXML
    private Button QRcodeB;
    @FXML
    private Label ouvrire;
    @FXML
    private TextField searchField;
 
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list_choix2 = FXCollections.observableArrayList("date", "resultat", "prix");
        choix_tri.setItems(list_choix2);

        showAnalyse();
rechercherAvance();
        date_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
            date_string.setText(newValue.toString());
        });
        List<labo> labos = new ArrayList<labo>();
        labos = sl.afficher();
        ObservableList<labo> list = FXCollections.observableArrayList(labos);
        laboratoire.setItems(list);

        laboratoire.setCellFactory(param -> new ListCell<labo>() {
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

    public ObservableList<analyse> getAnalyseList() {
        ObservableList<analyse> analyseList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM analyse";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            analyse l;
            while (rs.next()) {
                l = new analyse(rs.getInt("id"), rs.getString("date"), rs.getString("resultat"), rs.getString("image"), rs.getInt("prix"), sl.id_labo(rs.getInt("laboo_id")));
                analyseList.add(l);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return analyseList;
    }

    public void showAnalyse() {
        ObservableList<analyse> list = getAnalyseList();
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        resultat_col.setCellValueFactory(new PropertyValueFactory<>("resultat"));
        pdf_col.setCellValueFactory(new PropertyValueFactory<>("image"));
        prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));

        labo_col.setCellValueFactory(cellData -> {
            labo la = cellData.getValue().getL();
            return new SimpleStringProperty(la.getNom());
        });
        analyseTable.setItems(list);

    }

    @FXML
    private void update(MouseEvent event) {

        boolean no = true, blo = true, mail = true, med = true, img = true;

        dateErreur.setText("");
        resultatErreur.setText("");
        pdfErreur.setText("");
        prixErreur.setText("");
        laboErreur.setText("");

        if (resultat.getText().equals("") || !controleChaine(resultat.getText())) {
            new animatefx.animation.Shake(resultat).play();
            resultatErreur.setText("Resultat obligatoire");
            no = false;
        }

        if (date_string.getText().equals("") || !controleChaine(date_string.getText())) {
            new animatefx.animation.Shake(date_picker).play();
            dateErreur.setText("Date obligatoire");
            med = false;
        }
        if (prix.getText().equals("") || Integer.parseInt(prix.getText()) < 0) {
            new animatefx.animation.Shake(prix).play();
            prixErreur.setText("Prix obligatoire");
            mail = false;
        }
        if (!pdf.getText().matches("^C.*\\.pdf$")) {
            new animatefx.animation.Shake(pdf).play();
            pdfErreur.setText("PDF obligatoire");
            img = false;
        }
        if (laboratoire.getSelectionModel().getSelectedItem() == null) {
            new animatefx.animation.Shake(laboratoire).play();
            laboErreur.setText("Laboratoire obligatoire");
            blo = false;
        }

        if (no && blo && mail && med && img) {

            an.setId(Integer.parseInt(identifiant.getText()));
            an.setDate(date_string.getText());
            an.setResultat(resultat.getText());
            an.setImage(pdf.getText());
            an.setPrix(Integer.parseInt(prix.getText()));
            an.setL(laboratoire.getValue());
            try {
                sa.Modifier(an);
                showAnalyse();
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

    public boolean controleChaine(String str) {
        int length = str.length();
        return length >= 5 && length <= 10;
    }

    @FXML
    private void MouseActionAnalyse(MouseEvent event) {
        analyse a = analyseTable.getSelectionModel().getSelectedItem();
        identifiant.setText("" + a.getId());
        date_string.setText(a.getDate());

        String dateString = a.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        date_picker.setValue(localDate);

        resultat.setText(a.getResultat());
        pdf.setText(a.getImage());
        prix.setText(a.getPrix() + "");

        laboratoire.setValue(a.getL());
    }

    @FXML
    private void open_pdf(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier PDF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(pdf.getScene().getWindow());

        if (selectedFile != null) {
            pdf.setText(selectedFile.getAbsolutePath());
        }
    }

//to doo 
    private void tri_recherche(MouseEvent event) {
        try {
            Parent addStudentParent = FXMLLoader.load(getClass().getResource("/GUI/tri_recherche_analyse.fxml"));
            Scene addStudentScene = new Scene(addStudentParent);
            Stage newStage = new Stage();
            newStage.setScene(addStudentScene);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LaboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void delete(MouseEvent event) {
        an.setId(Integer.parseInt(identifiant.getText()));
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Message de Confirmation ");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir le supprimer?");

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get() == ButtonType.OK) {
                sa.supprimer(an);
            } else {

                return;
            }
            showAnalyse();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void voir_pdf(MouseEvent event) {
        String pdfFilePath = pdf.getText();
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

    @FXML
    private void selectTri(ActionEvent event) {
        String s = choix_tri.getSelectionModel().getSelectedItem().toString();
        choixT_label.setText(s);
    }

    @FXML
    private void trier_analyse(MouseEvent event) {
        SortedList<analyse> sortedList = new SortedList<>(analyseTable.getItems());

        if (!croiCheckBox.isSelected() && !decroiCheckBox.isSelected()) {
            analyseTable.setItems(sortedList);
            return;
        }

        Comparator<analyse> comparator;
        if (croiCheckBox.isSelected()) {
            comparator = comparatorForCroi();
        } else {
            comparator = comparatorForDecroi();
        }

        sortedList.setComparator(comparator);

        analyseTable.setItems(sortedList);
    }

    private Comparator<analyse> comparatorForCroi() {
        String s = choixT_label.getText();
        switch (s) {
            case "date":
                return Comparator.comparing(analyse::getDate);

            case "resultat":
                return Comparator.comparing(analyse::getResultat);

            case "prix":
                return Comparator.comparing(analyse::getPrix);
            default:
                throw new IllegalArgumentException("Unknown value for choix_tri: " + s);
        }
    }

    private Comparator<analyse> comparatorForDecroi() {
        String s = choixT_label.getText();
        switch (s) {
            case "date":
                return Comparator.comparing(analyse::getDate).reversed();

            case "resultat":
                return Comparator.comparing(analyse::getResultat).reversed();

            case "prix":
                return Comparator.comparing(analyse::getPrix).reversed();

            default:
                throw new IllegalArgumentException("Unknown value for choix_tri: " + s);
        }
    }

    @FXML
    private void generateQR(ActionEvent event) {

        boolean no = true, blo = true, mail = true, med = true, img = true;

        dateErreur.setText("");
        resultatErreur.setText("");
        pdfErreur.setText("");
        prixErreur.setText("");
        laboErreur.setText("");

        if (resultat.getText().equals("") || !controleChaine(resultat.getText())) {
            new animatefx.animation.Shake(resultat).play();
            resultatErreur.setText("Resultat obligatoire");
            no = false;
        }

        if (date_string.getText().equals("") || !controleChaine(date_string.getText())) {
            new animatefx.animation.Shake(date_picker).play();
            dateErreur.setText("Date obligatoire");
            med = false;
        }
        if (prix.getText().equals("") || Integer.parseInt(prix.getText()) < 0) {
            new animatefx.animation.Shake(prix).play();
            prixErreur.setText("Prix obligatoire");
            mail = false;
        }
        if (!pdf.getText().matches("^C.*\\.pdf$")) {
            new animatefx.animation.Shake(pdf).play();
            pdfErreur.setText("PDF obligatoire");
            img = false;
        }
        if (laboratoire.getSelectionModel().getSelectedItem() == null) {
            new animatefx.animation.Shake(laboratoire).play();
            laboErreur.setText("Laboratoire obligatoire");
            blo = false;
        }

        if (no && blo && mail && med && img) {

            an.setId(Integer.parseInt(identifiant.getText()));
            an.setDate(date_string.getText());
            an.setResultat(resultat.getText());
            an.setImage(pdf.getText());
            an.setPrix(Integer.parseInt(prix.getText()));
            an.setL(laboratoire.getValue());
            try {
                String text = " Resultat d'analyse : "+an.getResultat()+"\n Date d'analyse : "+an.getDate()+"\n Prix d'analyse : "+an.getPrix()+"";
                        int width = 300;
                int height = 300;

                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                ByteMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

                WritableImage qrCodeImage = new WritableImage(width, height);
                PixelWriter pixelWriter = qrCodeImage.getPixelWriter();

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (byteMatrix.get(x, y) == 0) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                        } else {
                            pixelWriter.setColor(x, y, Color.BLACK);
                        }
                    }
                }

                ImageView imageView = new ImageView(qrCodeImage);
                Stage stage = new Stage();
                Button downloadButton = new Button("Download");
                downloadButton.setOnAction(downloadEvent -> {
                    // prompt the user to choose a location to save the file
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG files (*.png)", "*.png"));
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null) {
                        // save the image as a PNG file
                        try {
                            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(qrCodeImage, null);
                            ImageIO.write(bufferedImage, "png", file);
                        } catch (IOException ex) {
                            Logger.getLogger(QRcodeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                VBox hbox = new VBox(imageView, downloadButton);
                hbox.setSpacing(10);

                Scene scene = new Scene(new StackPane(hbox), width, height);
                
                stage.setScene(scene);
                stage.show();
            } catch (WriterException ex) {
                Logger.getLogger(QRcodeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("ERREUR");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier vos informations!");
            alert.showAndWait();

        }
    }
      public void rechercherAvance() {
    ObservableList<analyse> list = getAnalyseList();
    date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
    resultat_col.setCellValueFactory(new PropertyValueFactory<>("resultat"));
    pdf_col.setCellValueFactory(new PropertyValueFactory<>("image"));
    prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));
    labo_col.setCellValueFactory(cellData -> {
        labo la = cellData.getValue().getL();
        return new SimpleStringProperty(la.getNom());
    });
    analyseTable.setItems(list);
    FilteredList<analyse> search = new FilteredList<>(list, b -> true);
    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        search.setPredicate(analyse -> {
            if (newValue.isEmpty() || newValue == null) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (analyse.getResultat().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (String.valueOf(analyse.getPrix()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (analyse.getDate().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }
        });
    });
    analyseTable.setItems(search);
}
   

}
