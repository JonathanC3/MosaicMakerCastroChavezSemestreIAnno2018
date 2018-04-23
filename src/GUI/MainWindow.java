/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import GUI.Cargar;
import GUI.Bar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author jonathan
 */
public class MainWindow extends Application{
    //Add
    

    BorderPane layout;
    Application window;
    
    
    //add
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mosaic Maker");
        
        //Add
            //Menú
        Menu menu1=new Menu("Projects");
            //Menu Items
        MenuItem mIt1=new MenuItem("New project...");
            //Agrego un evento al botón menú
        mIt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    ChooseSize chSize=new ChooseSize();
                    chSize.start(primaryStage);
                } catch (Exception ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        menu1.getItems().add(mIt1);
            //Separa menú
        menu1.getItems().add(new SeparatorMenuItem());
        MenuItem mIt2=new MenuItem("Open project...");
        
        mIt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Bar b=new Bar();
                b.start(primaryStage);
            }
        });
        menu1.getItems().add(mIt2);
            //Menu Bar
        MenuBar mBar=new MenuBar();
        mBar.getMenus().add(menu1);
        
        layout=new BorderPane();
        layout.setTop(mBar);
        Scene scene=new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
    //add
        primaryStage.show();
    }
}
