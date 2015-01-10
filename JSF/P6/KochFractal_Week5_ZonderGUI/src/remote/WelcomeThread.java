//<editor-fold defaultstate="collapsed" desc="Jibberish">
package remote;

import interfaces.IThread;
import java.net.Socket;
//</editor-fold>

/**
 * In this class you can find all properties and operations for WelcomeThread. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/26
 */
public class WelcomeThread extends ClientConnector implements IThread {

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for OpenThread.
     *
     * @param clientSocket is the open socket to the client.
     */
    public WelcomeThread(Socket clientSocket) {
        super(clientSocket);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="run()">
    @Override
    public void run() {
        String message = null;

        //Identify as the MP-Fractal Server
        message = "Welcome to the MP-Fractal Server.";
        sendObject(message);
        
        //See if you can help
        message = readMessage();
        if (!message.toLowerCase().contains("can you") || !message.toLowerCase().contains("fractal")) {
            message = "Sorry, I can't help you.";
            sendObject(message);
            return;
        }
        
        //Ask for the Level
        message = "Shure I can, what Level do you want it to be?";
        sendObject(message);
        int level = (int) readObject();
        
        //See with what priority they want their edges, and start the calculation threads
        message = readMessage();
        if (message.toLowerCase().contains("each edge") || message.toLowerCase().contains("every edge")) {
            new FastThread(clientSocket, level).start();
        } else {
            new SlowThread(clientSocket, level).start();
        }
    }
    //</editor-fold>
    //</editor-fold>
}
