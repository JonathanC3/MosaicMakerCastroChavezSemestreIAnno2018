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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
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
    BorderPane[][] bPane;
    BorderPane bpGen;
    Button btnSearch, btnLoad;
    ImageView myImage; 
    ImageView myImage2;
    ImageView imv=new ImageView();
    
    GraphicsContext gContext;
    Canvas gen=new Canvas(100, 100);
    private int cw=300;
    private int ch=300;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //instancio el hbox y el vbox y les doy el espacio entre cada child
        hbox=new HBox(10);
        vbox=new VBox(10);
        
        //instancia de borderpanes
        bPane=new BorderPane[10][10];
        bpGen=new BorderPane();
        for(int i=0; i<10;i++){
            for(int x=0; x<10; x++){
                bPane[i][x]=bpGen;
            }
        }
        
        //instancio los botones
        btnSearch=new Button("Search Project");
        btnLoad=new Button("Load Image");
        
        //instancio los image view
        myImage=new ImageView();
        myImage2=new ImageView();
        
        //declaro las acciones de cada boton
        btnSearch.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                
                Image im=getImageView();
                int h=100;
                int w=100;
                gen.setWidth(w);
                gen.setHeight(h);
                
                //WritableImage wim=new WritableImage(100, 100);
                for(int i=0; i<10; i++){
                    System.out.println(i);
                    gen=new Canvas();
                    gContext=gen.getGraphicsContext2D();
                    gContext.drawImage(im, i*100, i*100, w, h, i*100, i*100, w, h);
                    
                    bPane[i][0].setCenter(gen);
                    bPane[i][0].setPrefSize(w, h);
                }
            }
        });
        btnLoad.setOnAction(btnLoad2EventListener);
        
        //instancio el scene y lo agrego al stage
        Scene scene=new Scene(hbox, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Load Project");
        
        //agrego los botones al vbox
        vbox.getChildren().addAll(btnSearch, btnLoad);
        vbox.setVisible(true);
        
        bpGen.setVisible(true);
        hbox.getChildren().addAll(vbox, bpGen);
        hbox.setVisible(true);
    }
    
    EventHandler<ActionEvent> btnLoadEventListener = new EventHandler<ActionEvent>(){

        @Override
        public void handle(ActionEvent t) {
            //getImageView();
        }
    };
    
    private Image getImageView(){
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
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            return myImage.getImage();
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
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            return myImage2;
    }
    
    private void initDraw(GraphicsContext gc){
        double canW=gc.getCanvas().getWidth();
        double canH=gc.getCanvas().getHeight();
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        
        gc.fill();
    }
}
