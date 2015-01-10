
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import jsf31kochfractal.fx.JSF31KochFractalFX;
import timeutil.TimeStamp;


/**
 * In this class you can find all properties and operations for KochObserver.
 *
 * @organization: Moridrin
 * @author Anne Toonen
 * @date 2014/03/19
 */
public class KochManager implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for KochObserver.
     *
     * @param application
     */
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.koch.addObserver(this);
        this.koch = new KochFractal();

        edges = new ArrayList<>();
    }
    //</editor-fold>

    public void changeLevel(int level) {
        koch.setLevel(level);
        edges.clear();

        TimeStamp ts = new TimeStamp();
        ts.setBegin();

        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();

        ts.setEnd();
        application.setTextCalc(ts.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));

        drawEdges();
    }

    public void drawEdges() {
        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        application.clearKochPanel();

        for (Edge e : edges) {
            application.drawEdge(e);
        }
        ts.setEnd();
        application.setTextDraw(ts.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        //application.drawEdge((Edge) arg);
        edges.add((Edge) arg);
    }

    //</editor-fold>
}
