//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week4_metgui;

import callculate.Edge;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.stage.WindowEvent;
import watchers.WatchDirRunnable;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochFractal_Week4_MetGUI. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/26
 */
public class KochFractal_Week4_MetGUI extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    // Zoom and drag
    private double zoomTranslateX = 0.0;
    private double zoomTranslateY = 0.0;
    private double zoom = 1.0;
    private double startPressedX = 0.0;
    private double startPressedY = 0.0;
    private double lastDragX = 0.0;
    private double lastDragY = 0.0;

    // Current level of Koch fractal
    private final File file = new File("/home/jeroen/Edge");
    private int level = 1;

    // Labels for level, nr edges, calculation time, and drawing time
    private Label labelLevel;
    private Label labelNrEdges;
    private Label labelNrEdgesText;
    private Label labelCalc;
    private Label labelCalcText;
    private Label labelDraw;
    private Label labelDrawText;

    // Koch panel and its size
    private Canvas kochPanel;
    private final int kpWidth = 500;
    private final int kpHeight = 500;
    private final List<Edge> edges = new ArrayList<>();
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Start">
    @Override
    public void start(Stage primaryStage) {

        // Define grid pane
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // For debug purposes
        // Make de grid lines visible
        // grid.setGridLinesVisible(true);
        // Drawing panel for Koch fractal
        kochPanel = new Canvas(kpWidth, kpHeight);
        grid.add(kochPanel, 0, 3, 25, 1);

        // Labels to present number of edges for Koch fractal
        labelNrEdges = new Label("Nr edges:");
        labelNrEdgesText = new Label();
        grid.add(labelNrEdges, 0, 0, 4, 1);
        grid.add(labelNrEdgesText, 3, 0, 22, 1);

        // Labels to present time of calculation for Koch fractal
        labelCalc = new Label("Calculating:");
        labelCalcText = new Label();
        grid.add(labelCalc, 0, 1, 4, 1);
        grid.add(labelCalcText, 3, 1, 22, 1);

        // Labels to present time of drawing for Koch fractal
        labelDraw = new Label("Drawing:");
        labelDrawText = new Label();
        grid.add(labelDraw, 0, 2, 4, 1);
        grid.add(labelDrawText, 3, 2, 22, 1);

        // Label to present current level of Koch fractal
        labelLevel = new Label("Level: " + level);
        grid.add(labelLevel, 0, 6);

        // Button to fit Koch fractal in Koch panel
        Button buttonFitFractal = new Button();
        buttonFitFractal.setText("Fit Fractal");
        buttonFitFractal.setOnAction((ActionEvent event) -> {
            fitFractalButtonActionPerformed(event);
        });
        grid.add(buttonFitFractal, 14, 6);

        // Add mouse clicked event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            kochPanelMouseClicked(event);
        });

        // Add mouse pressed event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            kochPanelMousePressed(event);
        });

        // Add mouse dragged event to Koch panel
        kochPanel.setOnMouseDragged((MouseEvent event) -> {
            kochPanelMouseDragged(event);
        });

        // Create Koch manager and set initial level
        resetZoom();

        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, kpWidth + 50, kpHeight + 170);
        root.getChildren().add(grid);

        // Define title and assign the scene for main window
        primaryStage.setTitle("Koch Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });

        createDirWatch(Paths.get("/home/jeroen/"));
        fitFractalButtonActionPerformed(null);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Draw methodes">
    public void clearKochPanel() {
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, kpWidth, kpHeight);
        gc.setFill(Color.BLACK);
        gc.fillRect(0.0, 0.0, kpWidth, kpHeight);
    }

    public void drawEdges() {
        clearKochPanel();
        for (Edge edge : edges) {
            drawEdge(edge);
        }
    }

    public void drawEdge(Edge e) {
        // Graphics
        GraphicsContext gc = kochPanel.getGraphicsContext2D();

        // Adjust edge for zoom and drag
        Edge e1 = edgeAfterZoomAndDrag(e);

        // Set line color
        gc.setStroke(e1.getColor());

        // Set line width depending on level
        if (level <= 3) {
            gc.setLineWidth(2.0);
        } else if (level <= 5) {
            gc.setLineWidth(1.5);
        } else {
            gc.setLineWidth(1.0);
        }

        // Draw line
        gc.strokeLine(e1.X1, e1.Y1, e1.X2, e1.Y2);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="readFile()">
    public void readFile() throws ClassNotFoundException, FileNotFoundException, IOException {
        clearKochPanel();
        edges.clear();
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(KochFractal_Week4_MetGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                FileChannel fileChannel = randomAccessFile.getChannel();
                MappedByteBuffer out = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, randomAccessFile.length());
                FileLock fileLock = null;

                int startLock = 0;
                int lockLength = 4 + 4;
                fileLock = fileChannel.lock(startLock, lockLength, true);
                level = (int) randomAccessFile.readInt();
                int nrOfEdges = (int) randomAccessFile.readInt();
                fileLock.release();

                lockLength = (3 * 8) + (4 * 8);
                for (int i = 0; i < nrOfEdges; i++) {
                    startLock += lockLength;
                    fileLock = fileChannel.lock(startLock, lockLength, true);
                    double bri = randomAccessFile.readDouble();
                    double hue = randomAccessFile.readDouble();
                    double sat = randomAccessFile.readDouble();
                    double X1 = randomAccessFile.readDouble();
                    double Y1 = randomAccessFile.readDouble();
                    double X2 = randomAccessFile.readDouble();
                    double Y2 = randomAccessFile.readDouble();
                    fileLock.release();

                    Platform.runLater(() -> {
                        Edge whiteEdge = new Edge(X1, Y1, X2, Y2, Color.WHITE);
                        drawEdge(whiteEdge);
                    });
                    Edge edge = new Edge(X1, Y1, X2, Y2, Color.hsb(hue, sat, bri));
                    edges.add(edge);
                }
            } catch (IOException ex) {
                Logger.getLogger(KochFractal_Week4_MetGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(KochFractal_Week4_MetGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.runLater(() -> {
                drawEdges();
            });
        }).start();
        System.out.println("Done");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    public void setTextNrEdges(String text) {
        labelNrEdgesText.setText(text);
    }

    public void setTextCalc(String text) {
        labelCalcText.setText(text);
    }

    public void setTextDraw(String text) {
        labelDrawText.setText(text);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Events">
    //<editor-fold defaultstate="collapsed" desc="createDirWatch(dir)">
    private void createDirWatch(Path dir) {
        // create WatchDirRunnable object to watch the given directory (and possibly recursive)
        WatchDirRunnable watcher;
        try {
            watcher = new WatchDirRunnable(dir, false, this);
            new Thread(watcher).start();
        } catch (IOException ex) {
            Logger.getLogger(KochFractal_Week4_MetGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (new File("/home/jeroen/Edge").exists()) {
            Platform.runLater(() -> {
                try {
                    readFile();
                } catch (ClassNotFoundException | IOException ex) {
                    Logger.getLogger(WatchDirRunnable.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        // create Thread and start watching
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Fit Button">
    private void fitFractalButtonActionPerformed(ActionEvent event) {
        resetZoom();
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panel Clicked">
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

    //<editor-fold defaultstate="collapsed" desc="Panel Dragged">
    private void kochPanelMouseDragged(MouseEvent event) {
        zoomTranslateX = zoomTranslateX + event.getX() - lastDragX;
        zoomTranslateY = zoomTranslateY + event.getY() - lastDragY;
        lastDragX = event.getX();
        lastDragY = event.getY();
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mouse Pressed">
    private void kochPanelMousePressed(MouseEvent event) {
        startPressedX = event.getX();
        startPressedY = event.getY();
        lastDragX = event.getX();
        lastDragY = event.getY();
    }
    //</editor-fold>
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="Zoom">
    private void resetZoom() {
        int kpSize = Math.min(kpWidth, kpHeight);
        zoom = kpSize;
        zoomTranslateX = (kpWidth - kpSize) / 2.0;
        zoomTranslateY = (kpHeight - kpSize) / 2.0;
    }

    private Edge edgeAfterZoomAndDrag(Edge e) {
        return new Edge(
                e.X1 * zoom + zoomTranslateX,
                e.Y1 * zoom + zoomTranslateY,
                e.X2 * zoom + zoomTranslateX,
                e.Y2 * zoom + zoomTranslateY,
                e.getColor());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main(args)">
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
