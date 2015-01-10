//<editor-fold defaultstate="collapsed" desc="Jibberish">
package bank_server;

import components.User;
import interfaces.IBank;
import interfaces.ICentral;
import interfaces.IUser;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
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
 * In this class you can find all properties and operations for Central_Server. //CHECK
 *
 * @author J.B.A.J. Berkvens
 */
public class Central_Server extends UnicastRemoteObject implements ICentral {

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for Bank.
     *
     * @throws java.rmi.RemoteException because of the RMI protocol.
     */
    public Central_Server() throws RemoteException {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="static main">
    /**
     * This is the constructor for Central_Server.
     *
     * @param args the command line arguments
     *
     * @throws java.rmi.RemoteException because of the RMI protocol.
     */
    public static void main(String[] args) throws RemoteException {
        try {
            String address = null;
            try {
                address = java.net.InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ex) {
                Logger.getLogger(Central_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            int port = 1099;
            java.rmi.registry.LocateRegistry.createRegistry(port);
            String server = "//" + address + ":" + port + "/Central";
            ICentral central = new Central_Server();
            Naming.rebind(server, central);
            System.out.println("Server bound on: " + server);
            List<String> banks = new ArrayList();
            banks.add("RaboBank");
            banks.add("ING");
            banks.add("SNS");
            banks.add("ABN_AMRO");
            banks.add("ASN");
            for (String bankName : banks) {
                try {
                    server = "//" + address + ":" + port + "/" + bankName;
                    IBank bank = new Bank_Server(bankName);
                    Naming.rebind(server, bank);
                    System.out.println("Server bound on: " + server);
                } catch (RemoteException | MalformedURLException ex) {
                    Logger.getLogger(Bank_Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Central_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="login(username, password)">
    @Override
    public IUser login(String username, String passwordGiven) throws RemoteException {
        String passwordDatabase = null;
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
                passwordDatabase = resultSet.getString("Password");

            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Central_Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        if (passwordGiven.equals(passwordDatabase)) {
            return User.get(username);
        } else {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="register(username, password, firstName, middleName, lastName, address)">
    @Override
    public IUser register(String username, String password, String firstName, String middleName, String lastName, String address) throws RemoteException {
        Connection conn;
        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");
            preparedStatement = conn.prepareStatement("INSERT INTO User (Username, Password, FirstName, MiddleName, LastName, Address) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, middleName);
            preparedStatement.setString(5, lastName);
            preparedStatement.setString(6, address);
            preparedStatement.execute();

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Central_Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return login(username, password);
    }
    //</editor-fold>
    //</editor-fold>
}
