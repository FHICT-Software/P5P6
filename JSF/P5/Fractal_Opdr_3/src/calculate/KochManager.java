//<editor-fold defaultstate="collapsed" desc="Jibberish">
package calculate;

//</editor-fold>
import interfaces.SuperClass;
import java.util.Observable;
import java.util.Observer;

/**
 * In this class you can find all properties and operations for KochManager.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/18
 */
public class KochManager implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final SuperClass SUPERCLASS;
    private final KochFractal KOCHFRACTAL;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(superApplication)">
    /**
     * This is the constructor for KochManager.
     *
     * @param superApplication is the application that creates this class.
     */
    public KochManager(SuperClass superApplication) {
        this.SUPERCLASS = superApplication;
        this.KOCHFRACTAL = new KochFractal();
        this.KOCHFRACTAL.addObserver(this);
    }
  //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setLevel(lvl)">
    public void setLevel(int lvl) {
        KOCHFRACTAL.setLevel(lvl);
        drawEdges();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawEdges()">
    public void drawEdges() {
        SUPERCLASS.preformAction("clearKochPanel", null);
        KOCHFRACTAL.generateBottomEdge();
        KOCHFRACTAL.generateLeftEdge();
        KOCHFRACTAL.generateRightEdge();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        Object[] args = new Object[1];
        args[0] = arg;
        SUPERCLASS.preformAction("drawEdge",args);
    }
    //</editor-fold>
    //</editor-fold>
}
