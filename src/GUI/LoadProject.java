/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import data.MosaicFile;
import domain.Mosaic;
import java.awt.geom.AffineTransform;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    Cargar c= new Cargar();
    HBox hbox;
    VBox vbox, box;
    Button btnSearch, btnLoad, saveProject;
    ImageView myImage; 
    ImageView myImage2;
    ImageView imv=new ImageView();
    Label lbl=new Label("Write the project name to search");
    TextField txtName=new TextField();
    MosaicFile mosaicFile;
    Mosaic mosaic;
    ScrollPane sp1, sp2;
    GraphicsContext gContext;
    Canvas can1, can2;
    String name, pathIm, pathMosaic;
    
    @Override
    public void start(Stage stage) throws Exception {
        //instancio canvas
        can1=new Canvas();
        can2=new Canvas();
//        initCom(gContext);
        
        //ScrollPanes
        sp1=c.getScrollPane(can1);
        sp2=c.getScrollPane(can2);
        
        //instancio objeto
        mosaic=new Mosaic();
        
        //instancio el hbox y el vbox y les doy el espacio entre cada child
        hbox=new HBox(10);
        vbox=new VBox(10);
        box=new VBox(10);
        //instancio los botones
        btnLoad=new Button("Load Image");
        btnSearch=new Button("Search Project");
        saveProject=new Button("Save Project");
        
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
                can1.setHeight(im.getHeight()-(im.getHeight()%mosaic.getSqPixels()));
                can1.setWidth(im.getWidth()-(im.getWidth()%mosaic.getSqPixels()));
                gContext=can1.getGraphicsContext2D();
                gContext.fillRect(0, 0, im.getWidth(), im.getHeight());
                gContext.drawImage(im, 1, 1);
                for(int i=0; i<im.getWidth(); i=i+(int)mosaic.getSqPixels()){
                    gContext.strokeLine(mosaic.getSqPixels()+i, 0, mosaic.getSqPixels()+i, im.getHeight());
                }
                for(int i=0; i<im.getHeight(); i=i+(int)mosaic.getSqPixels()){
                    gContext.strokeLine(0, mosaic.getSqPixels()+i, im.getWidth(), mosaic.getSqPixels()+i);
                }
                gContext.setLineWidth(1);
                gContext.fill();
                
            }
        });
        btnSearch.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //Busco el file
                try{
                    name=txtName.getText();
                    File fil=new File("./"+name+".dat");
                    mosaicFile=new MosaicFile(fil);
                    mosaic=mosaicFile.getMosaic(0);
                }catch(Exception e){
                    System.out.println(e);
                }
                
                
                //cargo la ultima imagen usada
                String p=mosaic.getPathImage();
                Image im1=new Image(new File(p).toURI().toString());
                can1.setHeight(im1.getHeight()-(im1.getHeight()%mosaic.getSqPixels()));
                can1.setWidth(im1.getWidth()-(im1.getWidth()%mosaic.getSqPixels()));
                gContext=can1.getGraphicsContext2D();
                gContext.drawImage(im1, 0, 0);
                for(int i=0; i<im1.getWidth(); i=i+(int)mosaic.getSqPixels()){
                    gContext.strokeLine(mosaic.getSqPixels()+i, 0, mosaic.getSqPixels()+i, im1.getHeight());
                }
                for(int i=0; i<im1.getHeight(); i=i+(int)mosaic.getSqPixels()){
                    gContext.strokeLine(0, mosaic.getSqPixels()+i, im1.getWidth(), mosaic.getSqPixels()+i);
                }

                //cargo el mosaico ya guardado
                String p1=mosaic.getPathMosaic();
                Image im=new Image(new File(p1).toURI().toString());
                can2.setVisible(true);
                can2.setHeight(mosaic.getPixels()-(mosaic.getPixels()%mosaic.getSqPixels()));
                can2.setWidth(mosaic.getPixels()-(mosaic.getPixels()%mosaic.getSqPixels()));
                gContext=can2.getGraphicsContext2D();
                gContext.fillRect(0, 0, im.getWidth(), im.getHeight());
                gContext.drawImage(im, 0, 0);
                for(int i=0; i<im.getWidth(); i=i+(int)mosaic.getSqPixels()){
                    gContext.strokeLine(mosaic.getSqPixels()+i, 0, mosaic.getSqPixels()+i, im.getHeight());
                }
                for(int i=0; i<im.getHeight(); i=i+(int)mosaic.getSqPixels()){
                    gContext.strokeLine(0, mosaic.getSqPixels()+i, im.getWidth(), mosaic.getSqPixels()+i);
                }
                gContext.setLineWidth(1);
                gContext.fill();
                
            }
        });
        
        saveProject.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                
                ImageView img=new ImageView();
                
                //Recorto la imagen
                WritableImage wri=new WritableImage((int)can2.getWidth(), (int)can2.getHeight());
                img.setImage(can2.snapshot(null, wri));
                
                //le doy la ruta de guardado a la imagen
                FileChooser fch=new FileChooser();
                File f= fch.showSaveDialog(null);
                pathMosaic=f.getPath();
                //renderizo la imagen
                BufferedImage bf= SwingFXUtils.fromFXImage(img.getImage(), null);
                
                try {
                    //Guardo la imagen
                    ImageIO.write(bf, "png", f);
                } catch (IOException ex) {
                    Logger.getLogger(Cargar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //guardo los datos en el fichero RAF
                try{
                    mosaic.setName(name);
                    mosaic.setPixels(mosaic.getPixels());
                    mosaic.setSqPixels(mosaic.getSqPixels());
                    mosaic.setPathImage(pathIm);
                    mosaic.setPathMosaic(pathMosaic);
                    File file=new File("./"+name+".dat");
                    mosaicFile=new MosaicFile(file);
                    mosaicFile.addEndRecord(mosaic);
                }catch(Exception e){
                    System.out.println(e+" Hi");
                }
                
            }
        });
        
        //acción del canvas de imagen
        can1.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
                int x=(int)t.getX();
                int y=(int)t.getY();
                int tempx=(x%(int)mosaic.getSqPixels());
                int tempy=(y%(int)mosaic.getSqPixels());
                
                //redefino x, y
                x=x-tempx;
                y=y-tempy;
                
                System.out.println(x+", "+y);
                
                WritableImage wim=new WritableImage((int)mosaic.getSqPixels(),(int)mosaic.getSqPixels());
                SnapshotParameters snp=new SnapshotParameters();
                Rectangle2D rec=new Rectangle2D(x, y+0.2, mosaic.getSqPixels(), mosaic.getSqPixels());
                snp.setViewport(rec);
                imv.setImage(can1.snapshot(snp, wim));
                
            }
        });
        
        can2.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
                int x=(int)t.getX();
                int y=(int)t.getY();
                int tempx=0;
                int tempy=0;
                
                if(x%mosaic.getSqPixels()==0 && y%mosaic.getSqPixels()==0){
                    gContext= can2.getGraphicsContext2D();
                    gContext.drawImage(imv.getImage(), x, y);
                }else{
                    tempx=x%(int)mosaic.getSqPixels();
                    tempy=y%(int)mosaic.getSqPixels();
                    
                    x=x-tempx;
                    y=y-tempy;
                    
                    gContext= can2.getGraphicsContext2D();
                    
                    gContext.save();
                    gContext.translate(x, y);
                    gContext.rotate(90);
                    System.out.println("x: "+x+" y: "+y);
                    System.out.println(-(imv.getImage().getWidth()/2));
                    gContext.drawImage(imv.getImage(), -(imv.getImage().getWidth()/2), -(imv.getImage().getHeight()/2));
                    gContext.restore();
                }
                
                
            }
        });
        
        vbox.getChildren().addAll(btnLoad, btnSearch, saveProject, lbl, txtName);
        box.getChildren().addAll(sp1, sp2);
        hbox.getChildren().addAll(vbox, box);
        hbox.setVisible(true);
        
        //instancio el scene y lo agrego al stage
        Scene scene=new Scene(hbox, 1000, 650, Color.LIGHTGREEN);
        stage.setScene(scene);
        stage.setTitle("Load Project");
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
    }
    
    public Image getImageView(){
        FileChooser fileChooser = new FileChooser();
            
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
             
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
            pathIm=file.getPath();
                      
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                
                myImage.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            return myImage.getImage();
    }

}
