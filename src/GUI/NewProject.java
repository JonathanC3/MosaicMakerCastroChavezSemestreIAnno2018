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
import javafx.scene.input.KeyEvent;
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
public class NewProject extends Application {
    
    HBox hbox;
    VBox vbox, vb;
    Button btnSet, btnLoad, btnSave, btnRotate;
    TextField tfdNombre, tfdPixel, tfdSquare;
    Label lblNombre, lblPixel, lblSquare;
    Image im;
    ImageView myImage; 
    ImageView myImage2;
    ImageView imv=new ImageView();
    ScrollPane sp1, sp2;
    GraphicsContext gContext, gContext2;
    Canvas can1, can2;
    private int size, rot;
    //private int pix;
    MosaicFile mFile;
    Mosaic mosaic;
    Long pixels;
    String name;
    
    @Override
    public void start(Stage stage) throws Exception {
        //instancio canvas
        mosaic=new Mosaic();
        can1=new Canvas();
        can2=new Canvas();
        
        sp1=getScrollPane(can1);
        sp2=getScrollPane(can2);
        //instancio el hbox y el vbox y les doy el espacio entre cada child
        hbox=new HBox(10);
        vbox=new VBox(3);
        vb=new VBox(3);
        
        rot=0;
        
        //instancio los botones
        btnRotate=new Button("Rotate images");
        btnRotate.setDisable(true);
        btnLoad=new Button(" Load Image");
        btnLoad.setDisable(true);
        btnSet=new Button(" Set data");
        btnSave=new Button(" Save Project");
        btnSave.setDisable(true);
        lblNombre=new Label(" Project Name");
        lblPixel=new Label(" Pixel Mosaic Size");
        lblSquare=new Label("Square Size");
        tfdNombre=new TextField();
        tfdPixel=new TextField();
        tfdSquare=new TextField();
        
        //instancio los image view
        myImage=new ImageView();
//        myImage2=new ImageView();
        
        //declaro las acciones de cada boton
        btnLoad.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //cargo la imagen
                im=getImageView();
                //dibujo canvas 1
                size=Integer.parseInt(tfdSquare.getText());
                tfdSquare.setEditable(false);
                can1.setVisible(true);
                can1.setHeight(im.getHeight()-(im.getHeight()%size));
                can1.setWidth(im.getWidth()-(im.getWidth()%size));
                gContext=can1.getGraphicsContext2D();
                gContext.fillRect(0, 0, im.getWidth(), im.getHeight());
                gContext.drawImage(im, 1, 1);
                for(int i=0; i<im.getHeight(); i=i+size){
                    gContext.strokeLine(size+i, 0, size+i, im.getHeight());
                }
                for(int i=0; i<im.getWidth(); i=i+size){
                    gContext.strokeLine(0, size+i, im.getWidth(), size+i);
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
                btnRotate.setDisable(false);
                name=tfdNombre.getText();
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
                        initCom(gContext2, pixels);
                        btnSet.setDisable(true);
                    }catch(Exception e){
                        tfdNombre.setText("");
                        tfdPixel.setText("");
                        System.out.println(e);
                    }
                }
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
                    Logger.getLogger(NewProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnRotate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if(rot==0){
                    rot=1;
                }else{
                    rot=0;
                }
            }
        });
        
        //acción del canvas de imagen
        can1.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
                int x=(int)t.getX();
                int y=(int)t.getY();
                int tempx=(x%size);
                int tempy=(y%size);
                
                //redefino x, y
                x=x-tempx;
                y=y-tempy;
                
                System.out.println(x+", "+y);
                
                WritableImage wim=new WritableImage(size, size);
                SnapshotParameters snp=new SnapshotParameters();
                Rectangle2D rec=new Rectangle2D(x, y, size, size);
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
                
                if(x%size==0 && y%size==0){
                    gContext= can2.getGraphicsContext2D();
                    gContext.drawImage(imv.getImage(), x, y);
                }else{
                    tempx=x%size;
                    tempy=y%size;
                    
                    x=x-tempx;
                    y=y-tempy;
                    
                    gContext= can2.getGraphicsContext2D();
                    gContext.drawImage(imv.getImage(), x, y);
                }
                
            }
        });
        
        //probando como rotar
        can2.setOnMousePressed(new EventHandler<MouseEvent>() {
            
            
            @Override
            public void handle(MouseEvent t) {
                int xx=(int)t.getX();
                int yy=(int)t.getY();
                
                xx=xx-(xx%100);
                yy=yy-(yy%100); 
                
                System.out.println("Hi");
            }
        });
        
        vbox.setSpacing(10);
        vbox.getChildren().addAll(lblNombre, tfdNombre,lblPixel, tfdPixel, lblSquare, tfdSquare, btnLoad, btnSet, btnRotate);
        vb.getChildren().addAll(sp1, sp2,btnSave);
        hbox.getChildren().addAll(vbox, vb);
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
       ScrollPane sp=new ScrollPane();
       sp.setPrefSize(300, 250);
       sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
       sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
       sp.setContent(c1);
       return sp;
   }

}
