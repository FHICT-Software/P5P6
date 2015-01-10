/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnable;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.concurrent.Task;

/**
 *
 * @author jeroen
 */
public class RightEdgeTask extends Task<List<Edge>> implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final KochFractal kochFractal;
    private final KochManager kochManager;
    private final List<Edge> edges;
    private int currentEdge = 0;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor(kochManager, kochFractal, level)">
    public RightEdgeTask(KochManager kochManager, KochFractal kochFractal, int level) {
        this.kochManager = kochManager;
        this.kochFractal = kochFractal;
        this.kochFractal.setLevel(level);
        this.kochFractal.addObserver(this);
        edges = new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="call()">
    @Override
    protected List<Edge> call() throws Exception {
        kochFractal.generateRightEdge();
        kochManager.plus(this);
        return null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="succeeded()">
    @Override
    public void succeeded() {
        super.succeeded();
        updateMessage(currentEdge + " (succeeded)");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancelled()">
    @Override
    public void cancelled() {
        super.cancelled();
        updateMessage(currentEdge + " (cancelled)");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        if (isCancelled()) {
            kochFractal.cancel();
            cancelled();
        }
        currentEdge++;
        kochManager.updateEdges((Edge) arg);
        updateProgress(currentEdge, kochFractal.getNrOfEdges() / 3);
        updateMessage(currentEdge + " (running)");
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            if (isCancelled()) {
                kochFractal.cancel();
                cancelled();
            }
        }
    }
    //</editor-fold>
}
