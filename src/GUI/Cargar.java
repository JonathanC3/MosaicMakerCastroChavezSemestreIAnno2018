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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * 
 */
public class Cargar extends Application {
    
    ImageView myImageView;
    ScrollPane sp;
    Image images;
//    VBox vb;
//    Pane pane;
//    Label fileName;
//    String imageName;
//    Canvas cv;
    
    @Override
    public void start(Stage primaryStage) {
        
        Button btnLoad = new Button("Load");
        btnLoad.setOnAction(btnLoadEventListener);
        

        myImageView = new ImageView();
        
        sp = new ScrollPane();
        sp.setPrefSize(300, 250);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setContent(myImageView);
        
        
        
        GridPane gp = new GridPane();

        gp.setPadding(new Insets(0, 100, 0, 1));

        gp.setAlignment(Pos.TOP_LEFT);
//        tablero.setStyle("-fx-background-color: #F7EAAA; -fx-grid-lines-visible: true");

        GridPane.setConstraints(btnLoad, 0, 0);
        gp.getChildren().add(btnLoad);

        GridPane.setConstraints(sp, 1, 1);
        gp.getChildren().add(sp);


        Scene scene = new Scene(gp, 900, 650);
        
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
    
            getImageView();
        }
    };
    private ImageView getImageView(){
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
                sp.setContent(null);
                sp.setContent(myImageView);
                
                
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            return myImageView;
    }
   
}
