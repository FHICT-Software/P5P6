/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catanguitest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author anne
 */
public class CatanGUITest extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    //Canvas
    private Canvas catan;
    private final int cvWidth = 500;
    private final int cvpHeight = 500;

    @Override
    public void start(Stage primaryStage) {
catan = new Canvas(300, 300);
        final GraphicsContext gc = catan.getGraphicsContext2D();
        gc.clearRect(0, 0, catan.getWidth(), catan.getHeight());

        gc.setFill(Color.BLACK);
        gc.setFont(Font.getDefault());
        gc.fillText("hello   world!", 15, 50);

        gc.setLineWidth(5);
        gc.setStroke(Color.PURPLE);

        gc.strokeOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.strokeRect(30, 100, 40, 40);
        
        gc.drawImage(new Image("file:/Images/Tegels/Woestijn.png"), 10, 10);

        StackPane root = new StackPane();
        root.getChildren().add(catan);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
}
    
    

/**
 * The main() method is ignored in correctly deployed JavaFX application. main()
 * serves only as fallback in case the application can not be launched through
 * deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores
 * main().
 *
 * @param args the command line arguments
 */
public static void main(String[] args) {
        launch(args);
    }
    
}
