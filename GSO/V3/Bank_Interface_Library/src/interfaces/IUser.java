//<editor-fold defaultstate="collapsed" desc="Jibberish">
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
//</editor-fold>

/**
 * In this class you can find all types and operations for IUser.
 *
 * @author J.B.A.J. Berkvens
 */
public interface IUser extends Remote {

    public String getUsername() throws RemoteException;

    public List<IAccount> getAccounts() throws RemoteException;

    public void addAccount(IAccount returner) throws RemoteException;
}
