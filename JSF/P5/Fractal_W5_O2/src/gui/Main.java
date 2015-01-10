//<editor-fold defaultstate="collapsed" desc="Jibberish">
package gui;

import calculate.Edge;
import calculate.KochFractal;
import calculate.TimeStamp;
import interfaces.SuperClass;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import runnable.BottomEdge;
import runnable.LeftEdge;
import runnable.RightEdge;
//</editor-fold>

/**
 * In this class you can find the main GUI.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/17
 */
public class Main extends Application implements SuperClass {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    //<editor-fold desc="Zoom">
    private double zoomTranslateX = 0.0;
    private double zoomTranslateY = 0.0;
    private double zoom = 1.0;
    private double startPressedX = 0.0;
    private double startPressedY = 0.0;
    private double lastDragX = 0.0;
    private double lastDragY = 0.0;
    //</editor-fold>
    private int currentLevel = 1;
    private List<Edge> edges;
    private int counter = 0;
    private BottomEdge bottomEdge;
    private LeftEdge leftEdge;
    private RightEdge rightEdge;
    private TimeStamp timestamp;
    //<editor-fold desc="Interface Components">
    private Label labelLevel;
    private Label labelNrEdges;
    private Label labelNrEdgesText;
    private Label labelCalc;
    private Label labelCalcText;
    private Label labelDraw;
    private Label labelDrawText;
    private Canvas kochPanel;
    private final int kpWidth = 500;
    private final int kpHeight = 500;
    private Button buttonIncreaseLevel;
    private Button buttonDecreaseLevel;
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Start(primaryStage)">
    @Override
    public void start(Stage primaryStage) {
        //<editor-fold defaultstate="collapsed" desc="GridPane">
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Panel">
        kochPanel = new Canvas(kpWidth, kpHeight);
        grid.add(kochPanel, 0, 3, 25, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Labels">
        //<editor-fold defaultstate="collapsed" desc="Label Nr of Edges">
        labelNrEdges = new Label("Nr edges:");
        labelNrEdgesText = new Label();
        grid.add(labelNrEdges, 0, 0, 4, 1);
        grid.add(labelNrEdgesText, 3, 0, 22, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Label Time to Calculate">
        labelCalc = new Label("Calculating:");
        labelCalcText = new Label();
        grid.add(labelCalc, 0, 1, 4, 1);
        grid.add(labelCalcText, 3, 1, 22, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Label Time to Draw">
        labelDraw = new Label("Drawing:");
        labelDrawText = new Label();
        grid.add(labelDraw, 0, 2, 4, 1);
        grid.add(labelDrawText, 3, 2, 22, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Label Current koch lvl">
        labelLevel = new Label("Level: " + currentLevel);
        grid.add(labelLevel, 0, 6);
        //</editor-fold>
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Buttons">
        //<editor-fold defaultstate="collapsed" desc="Button to Increase lvl">
        buttonIncreaseLevel = new Button();
        buttonIncreaseLevel.setText("Increase Level");
        buttonIncreaseLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                increaseLevelButtonActionPerformed(event);
            }
        });
        grid.add(buttonIncreaseLevel, 3, 6);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Button to Decrease lvl">
        buttonDecreaseLevel = new Button();
        buttonDecreaseLevel.setText("Decrease Level");
        buttonDecreaseLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                decreaseLevelButtonActionPerformed(event);
            }
        });
        grid.add(buttonDecreaseLevel, 5, 6);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Button to Fit KochFractal">
        Button buttonFitFractal = new Button();
        buttonFitFractal.setText("Fit Fractal");
        buttonFitFractal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fitFractalButtonActionPerformed(event);
            }
        });
        grid.add(buttonFitFractal, 14, 6);
        //</editor-fold>
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Mouse events">
        //<editor-fold defaultstate="collapsed" desc="Mouse Click event">
        kochPanel.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                  new EventHandler<MouseEvent>() {
                                      @Override
                                      public void handle(MouseEvent event) {
                                          kochPanelMouseClicked(event);
                                      }
                                  });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Mouse Pressed event">
        kochPanel.addEventHandler(MouseEvent.MOUSE_PRESSED,
                                  new EventHandler<MouseEvent>() {
                                      @Override
                                      public void handle(MouseEvent event) {
                                          kochPanelMousePressed(event);
                                      }
                                  });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Mouse Dragged event">
        kochPanel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                kochPanelMouseDragged(event);
            }
        });
        //</editor-fold>
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="KochManager">
        edges = new ArrayList<>();
        resetZoom();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Scene">
        Group root = new Group();
        Scene scene = new Scene(root, kpWidth + 50, kpHeight + 170);
        root.getChildren().add(grid);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Finalizing">
        primaryStage.setTitle("Koch Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
        requestDrawEdges();
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="clearKochPanel()">
    public void clearKochPanel() {
        edges.clear();
        counter = 0;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="requestDrawEdges()">
    public void requestDrawEdges() {
        clearKochPanel();
        edges.clear();
        counter = 0;
        bottomEdge = new BottomEdge(new KochFractal(), this, currentLevel);
        Thread bottomThread = new Thread(bottomEdge);
        bottomThread.start();
        leftEdge = new LeftEdge(new KochFractal(), this, currentLevel);
        Thread leftThread = new Thread(leftEdge);
        leftThread.start();
        rightEdge = new RightEdge(new KochFractal(), this, currentLevel);
        Thread rightThread = new Thread(rightEdge);
        rightThread.start();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="requestDraw()">
    private void requestDraw() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drawEdges();
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawEdge()">
    public void drawEdges() {
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, kpWidth, kpHeight);
        gc.setFill(Color.BLACK);
        gc.fillRect(0.0, 0.0, kpWidth, kpHeight);
        for (Edge edge : edges) {
            Edge e = edgeAfterZoomAndDrag(edge);
            gc.setStroke(e.color);

            if (currentLevel <= 3) {
                gc.setLineWidth(2.0);
            } else if (currentLevel <= 5) {
                gc.setLineWidth(1.5);
            } else {
                gc.setLineWidth(1.0);
            }

            gc.strokeLine(e.X1, e.Y1, e.X2, e.Y2);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setText*">
    //<editor-fold defaultstate="collapsed" desc="setTextNrEdge(text)">
    public void setTextNrEdges(String text) {
        labelNrEdgesText.setText(text);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setTextCalc(text)">
    public void setTextCalc(String text) {
        labelCalcText.setText(text);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setTextDraw(text)">
    public void setTextDraw(String text) {
        labelDrawText.setText(text);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="*ButtonActionPreformed">
    //<editor-fold defaultstate="collapsed" desc="increaseLevelButtonActionPerformed(event)">
    private void increaseLevelButtonActionPerformed(ActionEvent event) {
        if (currentLevel < 12) {
            // resetZoom();
            currentLevel++;
            labelLevel.setText("Level: " + currentLevel);
            requestDrawEdges();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="decreaseLevelButtonActionPerformed(event)">
    private void decreaseLevelButtonActionPerformed(ActionEvent event) {
        if (currentLevel > 1) {
            // resetZoom();
            currentLevel--;
            labelLevel.setText("Level: " + currentLevel);
            requestDrawEdges();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fitFractalButtonActionPerformed(event)">
    private void fitFractalButtonActionPerformed(ActionEvent event) {
        resetZoom();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="plus()">
    public synchronized void plus() {
        if (counter == 0) {
            timestamp = new TimeStamp();
            timestamp.setBegin("Begin genereren");
        }
        counter++;
        if (counter >= 3) {
            requestDraw();
            timestamp.setEnd("Eind genereren");
            System.out.println(timestamp.toString());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMouse*">
    //<editor-fold defaultstate="collapsed" desc="kochPanelMouseClicked(event)">
    private void kochPanelMouseClicked(MouseEvent event) {
        if (Math.abs(event.getX() - startPressedX) < 1.0
            && Math.abs(event.getY() - startPressedY) < 1.0) {
            double originalPointClickedX = (event.getX() - zoomTranslateX) / zoom;
            double originalPointClickedY = (event.getY() - zoomTranslateY) / zoom;
            if (event.getButton() == MouseButton.PRIMARY) {
                zoom *= 2.0;
            } else if (event.getButton() == MouseButton.SECONDARY) {
                zoom /= 2.0;
            }
            zoomTranslateX = (int) (event.getX() - originalPointClickedX * zoom);
            zoomTranslateY = (int) (event.getY() - originalPointClickedY * zoom);
            drawEdges();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMouseDragged(event)">
    private void kochPanelMouseDragged(MouseEvent event) {
        zoomTranslateX = zoomTranslateX + event.getX() - lastDragX;
        zoomTranslateY = zoomTranslateY + event.getY() - lastDragY;
        lastDragX = event.getX();
        lastDragY = event.getY();
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMousePressed(event)">
    private void kochPanelMousePressed(MouseEvent event) {
        startPressedX = event.getX();
        startPressedY = event.getY();
        lastDragX = event.getX();
        lastDragY = event.getY();
        drawEdges();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="resetZoom()">
    private void resetZoom() {
        int kpSize = Math.min(kpWidth, kpHeight);
        zoom = kpSize;
        zoomTranslateX = (kpWidth - kpSize) / 2.0;
        zoomTranslateY = (kpHeight - kpSize) / 2.0;
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="edgeAfterZoomAndDrag(edge)">
    private Edge edgeAfterZoomAndDrag(Edge edge) {
        return new Edge(
                edge.X1 * zoom + zoomTranslateX,
                edge.Y1 * zoom + zoomTranslateY,
                edge.X2 * zoom + zoomTranslateX,
                edge.Y2 * zoom + zoomTranslateY,
                edge.color);
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

    //<editor-fold defaultstate="collapsed" desc="preformAction(action, args)">
    @Override
    public synchronized void preformAction(String action, Object[] args) {
        switch (action) {
            case "clearKochPanel":
                clearKochPanel();
                break;
            case "drawEdge":
                drawEdges();
                break;
            case "updateEdges":
                edges.add((Edge) args[0]);
                break;
            case "plus":
                plus();
                break;
        }
    }
    //</editor-fold>
    //</editor-fold>
}
