package fx;

import calculate.Edge;
import calculate.KochManager;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Nico Kuijpers
 */
public class JSF31KochFractalFX extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    // Zoom and drag
    private double zoomTranslateX = 0.0;
    private double zoomTranslateY = 0.0;
    private double zoom = 1.0;
    private double startPressedX = 0.0;
    private double startPressedY = 0.0;
    private double lastDragX = 0.0;
    private double lastDragY = 0.0;

    // Koch manager
    private KochManager kochManager;

    // Current level of Koch fractal
    private int currentLevel = 1;

    // Labels for level, nr edges, calculation time, and drawing time
    private Label labelLevel;
    private Label labelNrEdges;

    // Koch panel and its size
    private Canvas kochPanel;
    private final int kpWidth = 750;
    private final int kpHeight = 500;

    //Progress
    public ProgressBar bottomProgressBar;
    public Label bottomLabel;
    public Button bottomCancelButton;
    public ProgressBar leftProgressBar;
    public Label leftLabel;
    public Button leftCancelButton;
    public ProgressBar rightProgressBar;
    public Label rightLabel;
    public Button rightCancelButton;
    public Button allCancelButton;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="start(primaryStage)">
    @Override
    public void start(Stage primaryStage) {
        //<editor-fold defaultstate="collapsed" desc="GridPane">
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        //</editor-fold>
        
        //<editor-fold desc="Progress">
        //<editor-fold defaultstate="collapsed" desc="Bottom">
        bottomProgressBar = new ProgressBar();
        bottomProgressBar.setProgress(0);
        grid.add(bottomProgressBar, 0, 8);
        bottomLabel = new Label();
        grid.add(bottomLabel, 0, 9);
        bottomCancelButton = new Button();
        bottomCancelButton.setDisable(true);
        bottomCancelButton.setText("Cancel");
        bottomCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                kochManager.bottomEdgeTask.cancel();
            }
        });
        grid.add(bottomCancelButton, 0, 10);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Left">
        leftProgressBar = new ProgressBar();
        leftProgressBar.setProgress(0);
        grid.add(leftProgressBar, 1, 8);
        leftLabel = new Label();
        grid.add(leftLabel, 1, 9);
        leftCancelButton = new Button();
        leftCancelButton.setDisable(true);
        leftCancelButton.setText("Cancel");
        leftCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                kochManager.leftEdgeTask.cancel();
            }
        });
        grid.add(leftCancelButton, 1, 10);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Right">
        rightProgressBar = new ProgressBar();
        rightProgressBar.setProgress(0);
        grid.add(rightProgressBar, 2, 8);
        rightLabel = new Label();
        grid.add(rightLabel, 2, 9);
        rightCancelButton = new Button();
        rightCancelButton.setDisable(true);
        rightCancelButton.setText("Cancel");
        rightCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                kochManager.rightEdgeTask.cancel();
            }
        });
        grid.add(rightCancelButton, 2, 10);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Total">
        labelNrEdges = new Label();
        grid.add(labelNrEdges, 3, 9);
        allCancelButton = new Button();
        allCancelButton.setDisable(true);
        allCancelButton.setText("Cancel");
        allCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                kochManager.cancel();
            }
        });
        grid.add(allCancelButton, 3, 10);
        //</editor-fold>
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Panel">
        kochPanel = new Canvas(kpWidth, kpHeight);
        grid.add(kochPanel, 0, 0, 5, 5);
        labelLevel = new Label("Level: " + currentLevel);
        grid.add(labelLevel, 0, 6);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Increase/Decrease/Fit">
        Button buttonIncreaseLevel = new Button();
        buttonIncreaseLevel.setText("Increase Level");
        buttonIncreaseLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                increaseLevelButtonActionPerformed(event);
            }
        });
        grid.add(buttonIncreaseLevel, 1, 6);

        Button buttonDecreaseLevel = new Button();
        buttonDecreaseLevel.setText("Decrease Level");
        buttonDecreaseLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                decreaseLevelButtonActionPerformed(event);
            }
        });
        grid.add(buttonDecreaseLevel, 2, 6);
        
        Button buttonFitFractal = new Button();
        buttonFitFractal.setText("Fit Fractal");
        buttonFitFractal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fitFractalButtonActionPerformed(event);
            }
        });
        grid.add(buttonFitFractal, 3, 6);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Mouse">
        kochPanel.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        kochPanelMouseClicked(event);
                    }
                });
        kochPanel.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        kochPanelMousePressed(event);
                    }
                });
        kochPanel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                kochPanelMouseDragged(event);
            }
        });
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Setup & Window">
        resetZoom();
        kochManager = new KochManager(this);
        kochManager.changeLevel(currentLevel);

        Group root = new Group();
        Scene scene = new Scene(root, kpWidth + 50, kpHeight + 200);
        root.getChildren().add(grid);

        primaryStage.setTitle("Koch Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Draw methodes">
    //<editor-fold defaultstate="collapsed" desc="clearKochPanel()">
    public void clearKochPanel() {
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        gc.setFill(Color.TRANSPARENT);
        gc.clearRect(0.0, 0.0, kpWidth, kpHeight);
        gc.setFill(Color.BLACK);
        gc.fillRect(0.0, 0.0, kpWidth, kpHeight);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="requestDrawEdges()">
    public void requestDrawEdges() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                kochManager.drawEdges();
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="callDrawEdge(e)">
    public synchronized void callDrawEdge(final Edge e) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drawEdge(e);
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawEdge(e)">
    private synchronized void drawEdge(Edge e) {
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        Edge e1 = edgeAfterZoomAndDrag(e);
        gc.setStroke(e1.color);
        if (currentLevel <= 3) {
            gc.setLineWidth(2.0);
        } else if (currentLevel <= 5) {
            gc.setLineWidth(1.5);
        } else {
            gc.setLineWidth(1.0);
        }
        gc.strokeLine(e1.X1, e1.Y1, e1.X2, e1.Y2);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setTextNrEdges(text)">
    public void setTextNrEdges(String text) {
        labelNrEdges.setText(text + " Total");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Events">
    //<editor-fold defaultstate="collapsed" desc="increaseLevelButtonActionPerformed(event)">
    private void increaseLevelButtonActionPerformed(ActionEvent event) {
        if (currentLevel < 12) {
            // resetZoom();
            currentLevel++;
            kochManager.changeLevel(currentLevel);
            labelLevel.setText("Level: " + currentLevel);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="decreaseLevelButtonActionPerformed(event)">
    private void decreaseLevelButtonActionPerformed(ActionEvent event) {
        if (currentLevel > 1) {
            // resetZoom();
            currentLevel--;
            kochManager.changeLevel(currentLevel);
            labelLevel.setText("Level: " + currentLevel);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fitFractalButtonActionPerformed(event)">
    private void fitFractalButtonActionPerformed(ActionEvent event) {
        resetZoom();
        kochManager.drawEdges();
    }
    //</editor-fold>

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
            kochManager.drawEdges();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMouseDragged(event)">
    private void kochPanelMouseDragged(MouseEvent event) {
        zoomTranslateX = zoomTranslateX + event.getX() - lastDragX;
        zoomTranslateY = zoomTranslateY + event.getY() - lastDragY;
        lastDragX = event.getX();
        lastDragY = event.getY();
        kochManager.drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="kochPanelMousePressed(event)">
    private void kochPanelMousePressed(MouseEvent event) {
        startPressedX = event.getX();
        startPressedY = event.getY();
        lastDragX = event.getX();
        lastDragY = event.getY();
    }
    //</editor-fold>
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="Zoom">
    //<editor-fold defaultstate="collapsed" desc="resetZoom()">
    private void resetZoom() {
        int kpSize = Math.min(kpWidth, kpHeight);
        zoom = kpSize;
        zoomTranslateX = (kpWidth - kpSize) / 2.0;
        zoomTranslateY = (kpHeight - kpSize) / 2.0;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="edgeAfterZoomAndDrag(e)">
    private Edge edgeAfterZoomAndDrag(Edge e) {
        return new Edge(
                e.X1 * zoom + zoomTranslateX,
                e.Y1 * zoom + zoomTranslateY,
                e.X2 * zoom + zoomTranslateX,
                e.Y2 * zoom + zoomTranslateY,
                e.color);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main(args)">
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
    //</editor-fold>
}
