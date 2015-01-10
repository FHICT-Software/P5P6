//<editor-fold defaultstate="collapsed" desc="Jibberish">
package calculate;

import fx.JSF31KochFractalFX;
import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import runnable.BottomEdgeTask;
import runnable.LeftEdgeTask;
import runnable.RightEdgeTask;
import timeutil.TimeStamp;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochObserver.
 *
 * @organization: Moridrin
 * @author Anne Toonen
 * @date 2014/03/19
 */
public class KochManager {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final JSF31KochFractalFX application;
    private final KochFractal koch;
    private final ArrayList<Edge> edges;
    private int counter;
    public TimeStamp ts1;
    public TimeStamp ts2;
    public BottomEdgeTask bottomEdgeTask;
    public LeftEdgeTask leftEdgeTask;
    public RightEdgeTask rightEdgeTask;
    //</editor-fold>  

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(application)">
    /**
     * This is the constructor for KochObserver.
     *
     * @param application
     */
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.koch = new KochFractal();
        edges = new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="changeLevel(level)">
    public synchronized void changeLevel(int level) {
        cancel();
        application.allCancelButton.setDisable(false);
        application.clearKochPanel();
        koch.setLevel(level);
        edges.clear();
        this.ts1 = new TimeStamp();
        ts1.setBegin();

        counter = 0;
        application.bottomCancelButton.setDisable(false);
        bottomEdgeTask = new BottomEdgeTask(this, new KochFractal(), level);
        Thread bottomThread = new Thread(bottomEdgeTask);
        application.bottomProgressBar.progressProperty().bind(bottomEdgeTask.progressProperty());
        application.bottomLabel.textProperty().bind(bottomEdgeTask.messageProperty());
        bottomThread.start();

        application.leftCancelButton.setDisable(false);
        leftEdgeTask = new LeftEdgeTask(this, new KochFractal(), level);
        Thread leftThread = new Thread(leftEdgeTask);
        application.leftProgressBar.progressProperty().bind(leftEdgeTask.progressProperty());
        application.leftLabel.textProperty().bind(leftEdgeTask.messageProperty());
        leftThread.start();

        application.rightCancelButton.setDisable(false);
        rightEdgeTask = new RightEdgeTask(this, new KochFractal(), level);
        Thread rightThread = new Thread(rightEdgeTask);
        application.rightProgressBar.progressProperty().bind(rightEdgeTask.progressProperty());
        application.rightLabel.textProperty().bind(rightEdgeTask.messageProperty());
        rightThread.start();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="plus()">
    public synchronized void plus(Task caller) {
        if (caller instanceof BottomEdgeTask) {
            application.bottomCancelButton.setDisable(true);
        } else if (caller instanceof LeftEdgeTask) {
            application.leftCancelButton.setDisable(true);
        } else if (caller instanceof RightEdgeTask) {
            application.rightCancelButton.setDisable(true);
        }
        counter++;
        if (counter >= 3) {
            application.allCancelButton.setDisable(true);
            ts1.setEnd();
            application.requestDrawEdges();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawEdges()">
    public synchronized void drawEdges() {
        application.setTextNrEdges(String.valueOf(edges.size()));
        ts2 = new TimeStamp();
        ts2.setBegin();
        application.clearKochPanel();

        for (Edge e : edges) {
            application.callDrawEdge(e);
        }
        ts2.setEnd();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="updateEdges(e)">
    public synchronized void updateEdges(Edge e) {
        edges.add(e);
        Edge e1 = e.clone();
        e1.color = Color.WHITE;
        application.callDrawEdge(e1);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancel()">
    public void cancel() {
        try {
            bottomEdgeTask.cancel();
            leftEdgeTask.cancel();
            rightEdgeTask.cancel();
        } catch (NullPointerException exception) {
            //Do nothing.
        }
    }
    //</editor-fold>
    //</editor-fold>
}
