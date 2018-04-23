/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Domain.SaveNumber;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 *
 * @author faubricioch
 */
public class ChooseSize extends Application{

    TextField txtField;
    Button btnBuscar;
    Label label;
    SaveNumber number;
    
    @Override
    public void start(Stage stage) throws Exception {
        //características del campo de texto
        txtField =new TextField();
        //txtField.setDisable(true);
        txtField.setStyle("-fx-font: 18 arial; -fx-base: #EBC504;");
        
        //boton de busqueda
        btnBuscar=new Button("Search image");
        btnBuscar.setPrefSize(125, 30);
        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(txtField.getText().equals("")){
                    System.out.println("Digite un número");
                }else{
                    try{
                        number=new SaveNumber(Integer.parseInt(txtField.getText()));
                        Cargar carga=new Cargar();
                        carga.start(stage);
                    }catch(Exception e){
                        System.out.println(e+" Digite un número");
                    }
                }
                
            }
        });
        
        label=new Label("Digite el numero de cuadros deseados para el mosaico");
        
        //GridPane
        GridPane pane=new GridPane();
        pane.setPadding(new Insets(4, 1, 5, 5));
        pane.setVgap(20);
        
        GridPane.setConstraints(btnBuscar, 0, 1);
        pane.getChildren().add(btnBuscar);
        
        GridPane.setConstraints(txtField, 1, 1);
        pane.getChildren().add(txtField);
        
        pane.getColumnConstraints().add(new ColumnConstraints(150));
        
        Scene scene=new Scene(pane, 400, 100);
        stage.setTitle("Choose image size");
        stage.setScene(scene);
        stage.show();
    }
}
