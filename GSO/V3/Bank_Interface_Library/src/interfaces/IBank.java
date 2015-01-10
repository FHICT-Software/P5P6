//<editor-fold defaultstate="collapsed" desc="Jibberish">
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
//</editor-fold>

/**
 * In this class you can find all types and operations for IBank.
 *
 * @author J.B.A.J. Berkvens
 */
public interface IBank extends Remote {

    /**
     * This operation lets a user register a new account.
     *
     * @param user is the user that requests a new account.
     *
     * @return the new generated account.
     *
     * @throws RemoteException because of the RMI protocol.
     */
    public IAccount registerNewAccount(IUser user) throws RemoteException;
}
