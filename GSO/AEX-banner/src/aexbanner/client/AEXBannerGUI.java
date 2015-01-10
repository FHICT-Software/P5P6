/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.client;

import aexbanner.shared.IFonds;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author anne
 */
public class AEXBannerGUI extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    //main timeline
    private Timeline timeline;
    private AnimationTimer animationTimer;
    private static final int TIMERTICK = 200;

    //variable for storing actual frame
    private Integer i = 0;

    //variable for text
    String koersen = "Test";
    Text text;

    //variable for domain
    private BannerController bannerController;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="start">
    @Override
    public void start(Stage primaryStage) {
        //create bannercontroller & IEffectenbeurs
        //bannerController = new BannerController(this);      

        //create a text
        text = new Text(koersen);
        text.setStroke(Color.BLACK);
        text.setLayoutX(600);
        text.setLayoutY(150);
        text.setFont(new Font(60));

        final Pane root = new Pane();
        root.getChildren().add(text);

        Scene scene = new Scene(root, 1000, 250);

        primaryStage.setTitle("AEX-Banner");
        primaryStage.setScene(scene);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.show();

        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        //You can add a specific action when each frame is started.
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                text.setText(koersen);
                i++;
            }

        };
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.exit(0);
            }
        });

        //create a keyValue with factory
        KeyValue keyValue = new KeyValue(text.xProperty(), -10000);

        //create a keyFrame, the keyValue is reached at time 20ms
        Duration duration = Duration.millis(60000);
        //one can add a specific action when the keyframFe is reached

        KeyFrame keyFrame = new KeyFrame(duration, keyValue);

        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);

        timeline.play();

        animationTimer.start();
    }

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
    public void setKoersen(IFonds[] koersen) {
        StringBuilder sB = new StringBuilder();
        for (IFonds f : koersen) {
            sB.append(f.getNaam()).append(": ").append(f.getKoers()).append(" | ");
        }
        this.koersen = sB.toString();
    }

}
