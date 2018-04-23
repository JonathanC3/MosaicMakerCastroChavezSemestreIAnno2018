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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author faubricioch
 */
public class LoadProject extends Application{
    
    HBox hbox;
    VBox vbox;
    GridPane gridP, gridP1;
    Button btnSearch, btnLoad;
    ImageView myImage, myImage2;
    BackgroundImage back;
    Background b;
    
    @Override
    public void start(Stage stage) throws Exception {
        //instancio el hbox y el vbox y les doy el espacio entre cada child
        hbox=new HBox(10);
        vbox=new VBox(10);
        
        //instancio los gridpane
        gridP=new GridPane();
        gridP1=new GridPane();
        
        //instancio los botones
        btnSearch=new Button("Search Project");
        btnLoad=new Button("Load Image");
        
        //instancio los image view
        myImage=new ImageView();
        myImage2=new ImageView();
        
        //declaro las acciones de cada boton
        btnSearch.setOnAction(btnLoadEventListener);
        btnLoad.setOnAction(btnLoad2EventListener);
        
        //instancio el scene y lo agrego al stage
        Scene scene=new Scene(hbox, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Load Project");
        
        //agrego los botones al vbox
        vbox.getChildren().addAll(btnSearch, btnLoad);
        vbox.setVisible(true);
        
        //agrego todo al hbox
        hbox.getChildren().addAll(vbox, gridP, gridP1);
        hbox.setVisible(true);
        
        
        //Definición del primer gridpane
//        gridP.setPadding(new Insets(10, 10, 10, 10));
//        gridP1.setPadding(new Insets(10, 10, 10, 10));
//        gridP.setPrefSize(50, 200);
//        gridP.setAlignment(Pos.TOP_LEFT);
//        gridP.setBackground(b);
//        gridP.setVisible(true);
        GridPane.setConstraints(myImage, 0, 0);
        gridP.getChildren().add(myImage);
        
        GridPane.setConstraints(myImage2, 1, 0);
        gridP1.getChildren().add(myImage2);
        gridP1.setStyle("-fx-grid-lines-visible: true");
    }
    
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
                
                myImage.setImage(image);
                back=new BackgroundImage(myImage.getImage(), BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                b=new Background(back);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            return myImage;
    }
    
    EventHandler<ActionEvent> btnLoad2EventListener = new EventHandler<ActionEvent>(){

        @Override
        public void handle(ActionEvent t) {
    
            getImageView2();
        }
    };
    
    private ImageView getImageView2(){
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
                
                myImage2.setImage(image);
                back=new BackgroundImage(myImage.getImage(), BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                b=new Background(back);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            return myImage2;
    }
}
