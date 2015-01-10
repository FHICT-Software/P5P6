package controller;

import aexbanner.client.BannerController;
import aexbanner.server.RMIServer;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class StartUp extends Application {

    @Override
    public void start(Stage primaryStage) {
        RMIServer.main(null);
        BannerController.main(null);
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
