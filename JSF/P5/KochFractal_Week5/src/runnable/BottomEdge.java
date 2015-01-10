package runnable;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import java.util.Observable;
import java.util.Observer;

/**
 * In this class you can find all properties and operations for BottomEdge. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/23
 */
public class BottomEdge implements Runnable, Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final KochFractal kochFractal;
    private final KochManager kochManager;
    //</editor-fold>
    

    
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(kochFractal, superClass, lvl)">
    /**
     * This is the constructor for BottomEdge.
     *
     * @param kochManager
     * @param kochFractal
     * @param lvl
     */
    public BottomEdge(KochManager kochManager, KochFractal kochFractal, int lvl) {
        this.kochManager = kochManager;
        this.kochFractal = kochFractal;
        this.kochFractal.setLevel(lvl);
        this.kochFractal.addObserver(this);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="run()">
    @Override
    public void run() {
        kochFractal.generateBottomEdge();
        kochManager.plus();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        kochManager.updateEdges((Edge) arg);
        
    }
    //</editor-fold>
    //</editor-fold>
}
