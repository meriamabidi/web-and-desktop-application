/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class QRcodeController implements Initializable {

    @FXML
    private Button generate;
    @FXML
    private ImageView imageView;
    @FXML
    private Button download;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   @FXML
private void generate(ActionEvent event) {
    try {
        
        String text = "https://example.com"; 
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

        // set the QR code image in the ImageView
        imageView.setImage(qrCodeImage);

        // enable the download button
        download.setDisable(false);
        download.setOnAction(downloadEvent -> {
            // prompt the user to choose a location to save the file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG files (*.png)", "*.png"));
            File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());

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

    } catch (WriterException ex) {
        Logger.getLogger(QRcodeController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
 
}
    
    
    
    


