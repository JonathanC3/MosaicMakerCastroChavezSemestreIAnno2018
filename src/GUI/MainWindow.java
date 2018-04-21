/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
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
        mIt1.setOnAction(e->menuClicked(1));
        menu1.getItems().add(mIt1);
            //Separa menú
        menu1.getItems().add(new SeparatorMenuItem());
        MenuItem mIt2=new MenuItem("Open project...");
        mIt2.setOnAction(a->menuClicked(2));
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
    
    public void menuClicked(int num){
        if(num==1){
            System.out.println("New project...");
        }
        if(num==2){
            System.out.println("Open project...");
        }
    }
}
