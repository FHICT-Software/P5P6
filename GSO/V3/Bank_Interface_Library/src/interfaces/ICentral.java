//<editor-fold defaultstate="collapsed" desc="Jibberish">
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
//</editor-fold>

/**
 * In this class you can find all types and operations for ICentral.
 *
 * @author J.B.A.J. Berkvens
 */
public interface ICentral extends Remote {

    /**
     * This operation lets a user login.
     *
     * @param username      is the username used to login.
     * @param passwordGiven is the password for the user.
     *
     * @return true if the login is successful.
     *
     * @throws java.rmi.RemoteException because of the RMI protocol.
     */
    public IUser login(String username, String passwordGiven) throws RemoteException;

    /**
     * This operation lets a user register.
     *
     * @param username   is the username used to register.
     * @param password   is the password for the new user.
     * @param firstName  is the users first name.
     * @param middleName is the users middle name.
     * @param lastName   is the users last name.
     * @param address    is the users address.
     *
     * @return true if the registration is successful.
     *
     * @throws java.rmi.RemoteException because of the RMI protocol.
     */
    public IUser register(String username, String password, String firstName, String middleName, String lastName, String address) throws RemoteException;
}
