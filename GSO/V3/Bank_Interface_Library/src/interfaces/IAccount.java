//<editor-fold defaultstate="collapsed" desc="Jibberish">
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
//</editor-fold>

/**
 * In this class you can find all types and operations for IAccount.
 *
 * @author J.B.A.J. Berkvens
 */
public interface IAccount extends Remote {

    public String getBank() throws RemoteException;

    public String getIBAN() throws RemoteException;

    public double getSaldo() throws RemoteException;

    public double getCredit() throws RemoteException;

    /**
     * This operation transfers money from the this account to the given account.
     *
     * @param bank   is the destination bank.
     * @param amount is the amount to be transferred.
     * @param IBAN   is the destination account.
     *
     * @return true if the transaction is successful.
     *
     * @throws RemoteException because of the RMI protocol.
     */
    public boolean transfer(String bank, double amount, String IBAN) throws RemoteException;

    /**
     * This operation matches this account to the account given (used for updating all fields).
     *
     * @param account is the account that will be matched.
     *
     * @throws RemoteException because of the RMI protocol.
     */
    public void matchTo(IAccount account) throws RemoteException;
}
