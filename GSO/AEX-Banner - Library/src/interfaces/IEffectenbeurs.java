//<editor-fold defaultstate="collapsed" desc="Jibberish">
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public interface IEffectenbeurs extends Remote {

    /**
     *
     * @return @throws java.rmi.RemoteException
     */
    public IFonds[] getKoersen() throws RemoteException;

}
