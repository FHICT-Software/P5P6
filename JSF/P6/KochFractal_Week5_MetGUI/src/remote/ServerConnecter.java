//<editor-fold defaultstate="collapsed" desc="Jibberish">
package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 * In this class you can find all properties and operations for ServerConnecter.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/27
 */
public class ServerConnecter {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    //<editor-fold defaultstate="collapsed" desc="Static Finnal">
    private static final int PORT = 6752;
    private static final String SERVERNAME = "85.113.237.162";
    //</editor-fold>
    private Socket clientSocket = null;
    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
    
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="connect()">
    public boolean connect() {
        boolean returner = false;
        try {
            String message;
            clientSocket = new Socket(SERVERNAME, PORT);
            message = readMessageFromServer();
            if (message.equals("Welcome to the MP-Fractal Server.")) {
                returner = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnecter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="readMessage()">
    /**
     *
     * @return
     */
    public String readMessageFromServer() {
        String returner = null;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            returner = (String) objectInputStream.readObject();
            System.out.println("Server:\t" + returner);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerConnecter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="readObject()">
    /**
     *
     * @return
     */
    public Object readObjectFromServer() {
        Object returner = null;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            returner = objectInputStream.readObject();
            System.out.println("Server:\t" + returner);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerConnecter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="sendMessage(message)">
    /**
     *
     * @param object
     */
    public void sendObjectToServer(Object object) {
        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("You:\t" + object.toString());
            objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(ServerConnecter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    //</editor-fold>
}
