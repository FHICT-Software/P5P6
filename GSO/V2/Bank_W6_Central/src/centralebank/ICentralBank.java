//<editor-fold defaultstate="collapsed" desc="Jibberish">
package centralebank;

import java.rmi.Remote;
import java.rmi.RemoteException;
//</editor-fold>

/**
 * In this class you can find all properties and operations for ICentralBank. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/07/04
 */
public interface ICentralBank extends Remote {

    public String getTransactions(String nameBank) throws RemoteException;

    public void saveTransaction(String transactie) throws RemoteException;
    
    public void removeTransaction (String transactie) throws RemoteException;
    
}
