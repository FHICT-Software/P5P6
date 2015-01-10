//<editor-fold defaultstate="collapsed" desc="Jibberish">
package runnable;

//</editor-fold>
import calculate.KochFractal;
import interfaces.SuperClass;
import java.util.Observable;
import java.util.Observer;

/**
 * In this class you can find all properties and operations for RightEdge. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/23
 */
public class RightEdge implements Runnable, Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final KochFractal KOCHFRACTAL;
    private final SuperClass superClass;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(kochFractal, superClass, lvl)">
    /**
     * This is the constructor for RightEdge.
     *
     * @param kochFractal
     * @param superClass
     * @param lvl
     */
    public RightEdge(KochFractal kochFractal, SuperClass superClass, int lvl) {
        this.KOCHFRACTAL = kochFractal;
        this.KOCHFRACTAL.setLevel(lvl);
        this.KOCHFRACTAL.addObserver(this);
        this.superClass = superClass;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="run()">
    @Override
    public void run() {
        KOCHFRACTAL.generateRightEdge();
        superClass.preformAction("plus", null);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        Object[] args = new Object[1];
        args[0] = arg;
        superClass.preformAction("updateEdges", args);
    }
    //</editor-fold>
    //</editor-fold>
}
