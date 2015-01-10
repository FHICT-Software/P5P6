//<editor-fold defaultstate="collapsed" desc="Jibberish">
package components;

import bank_server.Central_Server;
import interfaces.IAccount;
import interfaces.IUser;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 * In this class you can find all properties and operations for User. //CHECK
 *
 * @author J.B.A.J. Berkvens
 */
public class User extends UnicastRemoteObject implements IUser {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final String username;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String address;
    private final List<IAccount> accounts;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public List<IAccount> getAccounts() {
        return accounts;
    }

    @Override
    public void addAccount(IAccount account) {
        accounts.add(account);
    }
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for User.
     *
     * @param username   is the Username the user uses to login.
     * @param firstName  is the users first name.
     * @param middleName is the users middle name.
     * @param lastName   is the users last name.
     * @param address    is the users address.
     *
     * @throws java.rmi.RemoteException because of the RMI protocol.
     */
    public User(String username, String firstName, String middleName, String lastName, String address) throws RemoteException {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.accounts = getUserAccounts();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get(username)">
    public static User get(String username) throws RemoteException {
        User returner = null;
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");

            preparedStatement = conn.prepareStatement("SELECT * FROM User WHERE Username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String middleName = resultSet.getString("MiddleName");
                String lastName = resultSet.getString("LastName");
                String address = resultSet.getString("Address");
                returner = new User(username, firstName, middleName, lastName, address);
            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Central_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getUserAccounts()">
    private List<IAccount> getUserAccounts() throws RemoteException {
        List<IAccount> returner = new ArrayList<>();
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<String> banks = new ArrayList();
        banks.add("RaboBank");
        banks.add("ING");
        banks.add("SNS");
        banks.add("ABN_AMRO");
        banks.add("ASN");
        for (String bankName : banks) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");
                preparedStatement = conn.prepareStatement("SELECT * FROM " + bankName + "_Account WHERE Username = ?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String IBAN = resultSet.getString("IBAN");
                    double saldo = resultSet.getDouble("Saldo");
                    double credit = resultSet.getDouble("Credit");
                    returner.add(new Account(bankName, IBAN, saldo, credit));
                }
            } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Central_Server.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return returner;
    }
    //</editor-fold>
    //</editor-fold>
}
