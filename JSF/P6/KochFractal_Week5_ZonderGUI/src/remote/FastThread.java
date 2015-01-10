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
import java.util.logging.Level;
import java.util.logging.Logger;
import kochfractal_week5_zondergui.Socket_Server;
//</editor-fold>

/**
 * In this class you can find all properties and operations for WelcomeThread. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/26
 */
public class FastThread extends ClientConnector implements IThread, Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private List<Edge> edges = null;
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
    public FastThread(Socket clientSocket, int level) {
        super(clientSocket);
        this.level = level;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="run()">
    @Override
    public void run() {
        String message = null;
        message = readMessage();
        if (message.toLowerCase().contains("cash")) {
            edges = Socket_Server.getList(level);
            if (edges == null) {
                message = "No. Sorry.";
            } else {
                message = "Yes, I'll send it to you.";
            }
            sendObject(message);
        }
        if (edges == null) {
            edges = new ArrayList<>();
            KochFractal kochFractal = new KochFractal();
            kochFractal.setLevel(level);
            nrOfEdges = kochFractal.getNrOfEdges();
            sendObject(nrOfEdges);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(FastThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            kochFractal.addObserver(this);
            kochFractal.generateLeftEdge();
            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
        } else {
            sendObject(edges);
            finish();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="finish()">
    private void finish() {
        Socket_Server.setList(level, edges);
        String message = "You're Welcome!";
        sendObject(message);
    }

    //<editor-fold defaultstate="collapsed" desc="update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        sendObject(arg);
        Edge e = (Edge) arg;
        edges.add(e);
        if (edges.size() == nrOfEdges) {
            finish();
        }
    }
    //</editor-fold>
    //</editor-fold>
}
