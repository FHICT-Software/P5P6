package callable;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * In this class you can find all properties and operations for RightEdge. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/23
 */
public class RightEdge implements Callable<ArrayList<Edge>>, Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final KochFractal kochFractal;
    private final KochManager kochManager;
    private ArrayList<Edge> edges;
    
    //</editor-fold>
    
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(kochFractal, lvl)">
    /**
     * This is the constructor for RightEdge.
     *
     * @param kochManager
     * @param kochFractal
     * @param lvl
     */
    public RightEdge(KochManager kochManager, KochFractal kochFractal, int lvl) {
        this.kochManager = kochManager;
        this.kochFractal = kochFractal;
        this.kochFractal.setLevel(lvl);
        this.kochFractal.addObserver(this);
        
        this.edges = new ArrayList<>();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="@Override">
    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }
    
    @Override
    public ArrayList<Edge> call() throws Exception {
        
        kochFractal.generateRightEdge();
        kochManager.getCyclicBarrier().await();
        return edges;
    }
    
    //</editor-fold>
    
    //</editor-fold>

    
}
