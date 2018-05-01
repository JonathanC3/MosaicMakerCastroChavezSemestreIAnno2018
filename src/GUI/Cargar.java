/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import data.MosaicFile;
import domain.Mosaic;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.LongStringConverter;
import javax.imageio.ImageIO;

/**
 * 
 */
public class Cargar extends Application {
    
    HBox hbox;
    VBox vbox;
    Button btnSet, btnLoad, btnSave;
    TextField tfdNombre, tfdPixel;
    Label lblNombre, lblPixel;
    Image im;
    ImageView myImage; 
    ImageView myImage2;
    ImageView imv=new ImageView();
    ScrollPane sp;
    GraphicsContext gContext, gContext2;
    Canvas can1, can2;
    private int size;
    private int pix;
    MosaicFile mFile;
    Mosaic mosaic;
    Long pixels;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        //instancio canvas
        mosaic=new Mosaic();
        can1=new Canvas();
        can2=new Canvas();
        
        sp=new ScrollPane();
        //instancio el hbox y el vbox y les doy el espacio entre cada child
        hbox=new HBox(10);
        vbox=new VBox(3);
        
        //instancio los botones
        btnLoad=new Button(" Load Image");
        btnLoad.setDisable(true);
        btnSet=new Button(" Set data");
        btnSave=new Button(" Save Project");
        btnSave.setDisable(true);
        lblNombre=new Label(" Project Name");
        lblPixel=new Label(" PixelSize");
        tfdNombre=new TextField();
        tfdPixel=new TextField();
        
        //instancio los image view
        myImage=new ImageView();
        myImage2=new ImageView();
        
        //declaro las acciones de cada boton
        btnLoad.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //cargo la imagen
                im=getImageView();
                //dibujo canvas 1
                can1.setVisible(true);
                can1.setHeight(im.getHeight());
                can1.setWidth(im.getWidth());
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
        btnSet.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                btnLoad.setDisable(false);
                btnSave.setDisable(false);
                String name=tfdNombre.getText();
                String pix=tfdPixel.getText();
                if(name.equals("")){
                    //validamos que no esté vacío
                    System.out.println("Enter a valid name");
                    tfdNombre.setText("");
                    tfdPixel.setText("");
                }else{
                    try{
                        //validamos que sea un numero
                        pixels=Long.parseLong(pix);
                        
                        mosaic.setName(name);
                        mosaic.setPixels(pixels);
                        File file=new File("./"+name+".dat");
                        mFile=new MosaicFile(file);
                        mFile.addEndRecord(mosaic);
                    }catch(Exception e){
                        tfdNombre.setText("");
                        tfdPixel.setText("");
                        System.out.println(e);
                    }
                }
                initCom(gContext2, pixels);
                btnSet.setDisable(true);
            }
        });
        
        btnSave.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                //declaro un nuevo Graphics Context
                GraphicsContext gpc=can2.getGraphicsContext2D();
                //Declaro un nuevo ImageView
                ImageView img=new ImageView();
                
                //Recorto la imagen
                WritableImage wri=new WritableImage((int)can2.getWidth(), (int)can2.getHeight());
                img.setImage(can2.snapshot(null, wri));
                
                //le doy la ruta de guardado a la imagen
                FileChooser fch=new FileChooser();
                File f= fch.showSaveDialog(null);
                //renderizo la imagen
                BufferedImage bf= SwingFXUtils.fromFXImage(img.getImage(), null);
                
                try {
                    //Guardo la imagen
                    ImageIO.write(bf, "png", f);
                } catch (IOException ex) {
                    Logger.getLogger(Cargar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //acción del canvas de imagen
        can1.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
                int x=(int)t.getX();
                int y=(int)t.getY();
                
                //al recortar produce el error de no seleccionar correctamente las cordenadas (x,y)
                if(im.getHeight()<can2.getHeight()){
                    if(x%100==0 && y%100==0){
                        WritableImage wim=new WritableImage(100, 100);
                        SnapshotParameters snp=new SnapshotParameters();
                        Rectangle2D rec=new Rectangle2D(x, y, 100, 100);
                        snp.setViewport(rec);
                        imv.setImage(can1.snapshot(snp, wim));
                    }else{
                        int tempx=x%100;
                        int tempy=y%100;

                        x=x-tempx;
                        y=y-tempy;

                        System.out.println("("+x+","+y+")");

                        WritableImage wim=new WritableImage(100, 100);
                        SnapshotParameters snp=new SnapshotParameters();
                        Rectangle2D rec=new Rectangle2D(x+181, y+0.2, 100, 100);
                        snp.setViewport(rec);
                        imv.setImage(can1.snapshot(snp, wim));
                    }
                }else{
                    if(x%100==0 && y%100==0){
                        WritableImage wim=new WritableImage(100, 100);
                        SnapshotParameters snp=new SnapshotParameters();
                        Rectangle2D rec=new Rectangle2D(x, y, 100, 100);
                        snp.setViewport(rec);
                        imv.setImage(can1.snapshot(snp, wim));
                    }else{
                        int tempx=x%100;
                        int tempy=y%100;

                        x=x-tempx;
                        y=y-tempy;

                        System.out.println("Say Hi");

                        WritableImage wim=new WritableImage(100, 100);
                        SnapshotParameters snp=new SnapshotParameters();
                        Rectangle2D rec=new Rectangle2D(x+181, y+0.2, 100, 100);
                        snp.setViewport(rec);
                        imv.setImage(can1.snapshot(snp, wim));
                    }
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
        
        vbox.getChildren().addAll(lblNombre, tfdNombre,lblPixel, tfdPixel,btnLoad, btnSet, btnSave);
        
        hbox.getChildren().addAll(vbox, can1, can2);
        hbox.setVisible(true);
        
        //instancio el scene y lo agrego al stage
        Scene scene=new Scene(hbox, 1000, 650);
        stage.setScene(scene);
        stage.setTitle("Load Project");
    }
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
    public void initCom(GraphicsContext gc, long pix){
        this.can2.setHeight(pix);
        this.can2.setWidth(pix);
        gc=this.can2.getGraphicsContext2D();
        gc.fillRect(0, 0, pix, pix);
        
        for(int i=0; i<pix; i=i+100){
            gc.strokeLine(100+i, 0, 100+i, pix);
        }
        for(int i=0; i<pix; i=i+100){
            gc.strokeLine(0, 100+i, pix, 100+i);
        }
        gc.setLineWidth(1);
        gc.fill();
    }

   private ScrollPane getScrollPane(Canvas c1){
       
       sp.setPrefSize(300, 250);
       sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
       sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
       sp.setContent(c1);
       return sp;
   }

}
