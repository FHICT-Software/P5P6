//<editor-fold defaultstate="collapsed" desc="Jibberish">
package gui;

import calculate.KochFractal;
import calculate.KochObserver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mp_gui_lib.MessageBox;
//</editor-fold>

/**
 * In this class you can find the main GUI.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/17
 */
public class Fractal_02 extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    //</editor-fold>
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Start(primaryStage)">
    @Override
    public void start(Stage primaryStage) {
        Button buttonPrintFractal = new Button();
        buttonPrintFractal.setText("Print first lvl Fractal");
        buttonPrintFractal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                KochFractal kochFractal = new KochFractal();
                KochObserver kochObserver = new KochObserver();
                kochFractal.addObserver(kochObserver);
                kochFractal.setLevel(1);
                kochFractal.generateBottomEdge();
                kochFractal.generateLeftEdge();
                kochFractal.generateRightEdge();
                MessageBox.show("You van find the coördinates in the output.");
            }
        });

        StackPane root = new StackPane();
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);
        vBox.getChildren().add(buttonPrintFractal);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Print Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main(args)">
    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores
     * main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    //</editor-fold>
    //</editor-fold>
}
