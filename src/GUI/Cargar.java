/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * 
 */
public class Cargar extends Application {
    
    ImageView myImageView;
    
    @Override
    public void start(Stage primaryStage) {
        
        Button btnLoad = new Button("Load");
        btnLoad.setOnAction(btnLoadEventListener);
        
        myImageView = new ImageView();        
        
        VBox rootBox = new VBox();
        rootBox.getChildren().addAll(btnLoad, myImageView);
        
        Scene scene = new Scene(rootBox, 300, 300);
        
        primaryStage.setTitle("cargar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
    
    EventHandler<ActionEvent> btnLoadEventListener = new EventHandler<ActionEvent>(){

        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();
            
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
             
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                      
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                String s=file.toString();
                System.out.println(s);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                
                myImageView.setImage(image);
                
                
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };
}
