//<editor-fold defaultstate="collapsed" desc="Jibberish">
package remote;

import callculate.Edge;
import callculate.KochFractal;
import interfaces.IThread;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import kochfractal_week5_zondergui.Socket_Server;
//</editor-fold>

/**
 * In this class you can find all properties and operations for WelcomeThread. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/26
 */
public class SlowThread extends ClientConnector implements IThread, Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private List<Edge> edges;
    private final int level;
    private int nrOfEdges;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for OpenThread.
     *
     * @param clientSocket is the open socket to the client.
     * @param level
     */
    public SlowThread(Socket clientSocket, int level) {
        super(clientSocket);
        this.level = level;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="run()">
    @Override
    public void run() {
        String message = null;
        edges = Socket_Server.getList(level);
        if (edges == null) {
            edges = new ArrayList<>();
            KochFractal kochFractal = new KochFractal();
            kochFractal.setLevel(level);
            nrOfEdges = kochFractal.getNrOfEdges();
            kochFractal.addObserver(this);
            kochFractal.generateLeftEdge();
            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        edges.add(e);
        if (edges.size() == nrOfEdges) {
            sendObject(edges);
            Socket_Server.setList(level, edges);
        }
    }
    //</editor-fold>
    //</editor-fold>
}
