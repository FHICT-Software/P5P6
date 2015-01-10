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
 * In this class you can find all properties and operations for ClientConnector. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/27
 */
public class ClientConnector extends Thread {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    ObjectInputStream objectInputStream = null;
    ObjectOutputStream objectOutputStream = null;
    Socket clientSocket = null;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor(clientSocket, id)">
    /**
     * This is the constructor for ClientConnecter.
     *
     * @param clientSocket
     */
    public ClientConnector(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="readMessage()">
    public String readMessage() {
        String returner = null;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            returner = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="readObject()">
    public Object readObject() {
        Object returner = null;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            returner = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(WelcomeThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="sendObject(object)">
    public void sendObject(Object object) {
        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(ClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    //</editor-fold>
}
