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
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
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
    Button btnSearch, btnLoad, saveProject;
    ImageView myImage; 
    ImageView myImage2;
    ImageView imv=new ImageView();
    
    GraphicsContext gContext;
    Canvas can1, can2;
    
    @Override
    public void start(Stage stage) throws Exception {
        //instancio canvas
        can1=new Canvas();
        can2=new Canvas();
        initCom(gContext);
        
        //instancio el hbox y el vbox y les doy el espacio entre cada child
        hbox=new HBox(10);
        vbox=new VBox(10);
        
        //instancio los botones
        btnLoad=new Button("Load Image");
        btnSearch=new Button("Search Project");
        saveProject=new Button("Save Project");
        btnLoad.setPrefWidth(btnSearch.getWidth());
        saveProject.setPrefWidth(btnSearch.getWidth());
        
        //instancio los image view
        myImage=new ImageView();
        myImage2=new ImageView();
        
        //declaro las acciones de cada boton
        btnLoad.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //cargo la imagen
                Image im=getImageView();
                can1.setVisible(true);
                can1.setHeight(300);
                can1.setWidth(300);
                gContext=can1.getGraphicsContext2D();
                gContext.fillRect(0, 0, im.getWidth(), im.getHeight());
                gContext.drawImage(im, 1, 1);
                for(int i=0; i<im.getHeight(); i=i+100){
                    gContext.strokeLine(100+i, 0, 100+i, im.getHeight());
                }
                for(int i=0; i<im.getWidth(); i=i+100){
                    gContext.strokeLine(0, 100+i, im.getWidth(), 100+i);
                }
                gContext.setLineWidth(1);
                gContext.fill();
                
            }
        });
        btnSearch.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //cargo la imagen
                Image im=getImageView();
                can2.setVisible(true);
                can2.setHeight(300);
                can2.setWidth(300);
                gContext=can2.getGraphicsContext2D();
                gContext.fillRect(0, 0, im.getWidth(), im.getHeight());
                gContext.drawImage(im, 0, 0);
                for(int i=0; i<im.getHeight(); i=i+100){
                    gContext.strokeLine(100+i, 0, 100+i, im.getHeight());
                }
                for(int i=0; i<im.getWidth(); i=i+100){
                    gContext.strokeLine(0, 100+i, im.getWidth(), 100+i);
                }
                gContext.setLineWidth(1);
                gContext.fill();
                
            }
        });
        
        //acción del canvas de imagen
        can1.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
                int x=(int)t.getX();
                int y=(int)t.getY();
                
                if(x%100==0 && y%100==0){
                    WritableImage wim=new WritableImage(100, 100);
                    SnapshotParameters snp=new SnapshotParameters();
                    Rectangle2D rec=new Rectangle2D(x+30, y, 100, 100);
                    snp.setViewport(rec);
                    imv.setImage(can1.snapshot(snp, wim));
                }else{
                    int tempx=x%100;
                    int tempy=y%100;
                    
                    x=x-tempx;
                    y=y-tempy;
                    
                    System.out.println(x+" "+y);
                    
                    System.out.println("("+x+","+y+")");
                    
                    WritableImage wim=new WritableImage(100, 100);
                    SnapshotParameters snp=new SnapshotParameters();
                    Rectangle2D rec=new Rectangle2D(x+123.09, y+0.2, 100, 100);
                    snp.setViewport(rec);
                    imv.setImage(can1.snapshot(snp, wim));
                }
                
            }
        });
        
        can2.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
                int x=(int)t.getX();
                int y=(int)t.getY();
                int tempx=0;
                int tempy=0;
                
                if(x%100==0 && y%100==0){
                    gContext= can2.getGraphicsContext2D();
                    gContext.drawImage(imv.getImage(), x, y);
                }else{
                    tempx=x%100;
                    tempy=y%100;
                    
                    x=x-tempx;
                    y=y-tempy;
                    
                    gContext= can2.getGraphicsContext2D();
                    gContext.drawImage(imv.getImage(), x, y);
                }
                
            }
        });
        
        vbox.getChildren().addAll(btnLoad, btnSearch, saveProject);
        
        hbox.getChildren().addAll(vbox, can1, can2);
        hbox.setVisible(true);
        
        //instancio el scene y lo agrego al stage
        Scene scene=new Scene(hbox, 1000, 650);
        stage.setScene(scene);
        stage.setTitle("Load Project");
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
    
    public void initCom(GraphicsContext gc){
        //define el tamaño por defecto que tendrá el segundo canvas
        this.can2.setHeight(300);
        this.can2.setWidth(300);
        //le doy al graphicContext los datos o medidas del canvas
        gc=this.can2.getGraphicsContext2D();
        //define un rectangulo del tamaño del canvas en el canvas
        gc.fillRect(0, 0, this.can2.getWidth(), can2.getHeight());
        //
        gc.strokeLine(0, 100, this.can2.getWidth(), 100);
        gc.setFill(Color.WHITE);
        gc.setLineWidth(1);
        this.can2.setVisible(true);
    }
}
