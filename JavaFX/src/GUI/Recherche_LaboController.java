/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.labo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class Recherche_LaboController implements Initializable {

    @FXML
    private ComboBox<String> choix;
    @FXML
    private TableView<labo> laboTable;
   
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
    @FXML
    private TextField rechercher;
    @FXML
    private Button rechercherB;
    @FXML
    private Label choix_label;
    @FXML
    private Button pdfB;
    @FXML
    private Button excelB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choix_label.setText("");
        ObservableList<String> list_choix = FXCollections.observableArrayList("Nom", "Bloc", "Telephone", "Medecin", "E-mail");
        choix.setItems(list_choix);
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
    private void select(ActionEvent event) {
        String s = choix.getSelectionModel().getSelectedItem().toString();
        choix_label.setText(s);
    }

    @FXML
    private void rechercher_labo(MouseEvent event) {
        ObservableList<labo> laboList = choix();
      
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        bloc_col.setCellValueFactory(new PropertyValueFactory<>("bloc"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tel_col.setCellValueFactory(new PropertyValueFactory<>("tel"));
        med_col.setCellValueFactory(new PropertyValueFactory<>("med"));

        laboTable.setItems(laboList);
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
                l = new labo(rs.getInt("id"), rs.getString("nom"), rs.getString("bloc"), rs.getString("mail"), rs.getInt("tel"), rs.getString("img"), rs.getString("med"));
                laboList.add(l);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return laboList;
    }

    public ObservableList<labo> choix() {
        String r = rechercher.getText();
        String s = choix_label.getText();
        ObservableList<labo> laboList = FXCollections.observableArrayList();
        if (r.equals("") || s.equals("")) {

            laboList = getLaboList();
        } else {

            Connection conn = getConnection();
            String query = "SELECT * FROM labo where " + s + " =?";
            PreparedStatement st;
            ResultSet rs;
            try {
                st = conn.prepareStatement(query);
                st.setString(1, r);
                rs = st.executeQuery();
                labo l;
                while (rs.next()) {
                    l = new labo(rs.getInt("id"), rs.getString("nom"), rs.getString("bloc"), rs.getString("mail"), rs.getInt("tel"), rs.getString("img"), rs.getString("med"));
                    laboList.add(l);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return laboList;

    }

    @FXML
    private void telecharger_pdf(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName("laboTable.pdf");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                PdfPTable pdfTable = new PdfPTable(laboTable.getColumns().size());
                pdfTable.setWidthPercentage(100);
                pdfTable.setSpacingBefore(10f);
                pdfTable.setSpacingAfter(10f);

                for (TableColumn column : laboTable.getColumns()) {
                    PdfPCell cell = new PdfPCell(new Phrase(column.getText()));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    pdfTable.addCell(cell);
                }

                for (int i = 0; i < laboTable.getItems().size(); i++) {
                    for (int j = 0; j < laboTable.getColumns().size(); j++) {
                        Object value = laboTable.getColumns().get(j).getCellData(i);
                        pdfTable.addCell(value.toString());
                    }
                }

                document.add(pdfTable);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void telecharger_excel(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les produits");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Excel (.xlsx)", ".xlsx"));
        Stage stage = (Stage) excelB.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                if (!file.exists()) {
                    file.createNewFile();
                }
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Laboratoires");
                XSSFRow headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Nom");
                headerRow.createCell(1).setCellValue("Bloc");
                headerRow.createCell(2).setCellValue("Medecin");
                headerRow.createCell(3).setCellValue("Mail");
                headerRow.createCell(4).setCellValue("Tel");

                ObservableList<labo> produit = getLaboList();

                for (int i = 0; i < produit.size(); i++) {
                    labo a = produit.get(i);
                    XSSFRow row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(a.getNom());
                    row.createCell(1).setCellValue(a.getBloc());
                    row.createCell(2).setCellValue(a.getMed());
                    row.createCell(3).setCellValue(a.getMail());
                    row.createCell(4).setCellValue(a.getTel());
                }

                workbook.write(outputStream);
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
