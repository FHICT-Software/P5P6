package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.*;
import jsf31kochfractal.fx.JSF31KochFractalFX;
import callable.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final JSF31KochFractalFX application;
    private final KochFractal koch;
    private final ArrayList<Edge> edges;
    public TimeStamp ts1;
    public TimeStamp ts2;
    
    private ExecutorService pool;
    Future<ArrayList<Edge>> futButtom;
    Future<ArrayList<Edge>> futLeft;
    Future<ArrayList<Edge>> futRight;
    CyclicBarrier cb;

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
        this.koch = new KochFractal();
        

        edges = new ArrayList<>();

        int treads = 4;
        pool = Executors.newFixedThreadPool(treads);
        this.cb = new CyclicBarrier(treads);
    }
    //</editor-fold>

    public synchronized void changeLevel(int level) {
        koch.setLevel(level);
        edges.clear();
        this.ts1 = new TimeStamp();
        ts1.setBegin();

        futButtom = pool.submit(new BottomEdge(this, new KochFractal(), level));
        futLeft = pool.submit(new LeftEdge(this, new KochFractal(), level));
        futRight = pool.submit(new RightEdge(this, new KochFractal(), level));
        pool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    cb.await();
                    Thread.sleep(200);
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                ts1.setEnd();
        application.requestDrawEdges();
            }
        });
        
        
    }

    public synchronized void drawEdges() {
        application.setTextCalc(ts1.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));

        updateEdges();
        
        ts2 = new TimeStamp();
        ts2.setBegin();
        application.clearKochPanel();

        for (Edge e : edges) {
            application.drawEdge(e);
        }
        ts2.setEnd();
        application.setTextDraw(ts2.toString());
    }

    public synchronized void updateEdges() {
        try {
            edges.addAll(futButtom.get());
            edges.addAll(futLeft.get());
            edges.addAll(futRight.get());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //application.drawEdge((Edge) arg);
        edges.add((Edge) arg);
    }

    //</editor-fold>
    public CyclicBarrier getCyclicBarrier() {
        return cb;
    }
}
