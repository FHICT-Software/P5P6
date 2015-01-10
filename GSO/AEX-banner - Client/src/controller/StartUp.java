//<editor-fold defaultstate="collapsed" desc="Jibberish">
package controller;

import aexbannerclient.BannerController;
import javafx.application.Application;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class StartUp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BannerController.main(null);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
