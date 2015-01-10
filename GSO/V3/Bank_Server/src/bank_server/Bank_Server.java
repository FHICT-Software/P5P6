//<editor-fold defaultstate="collapsed" desc="Jibberish">
package bank_server;

import components.Account;
import interfaces.IAccount;
import interfaces.IBank;
import interfaces.IUser;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Bank_Server. //CHECK
 *
 * @author J.B.A.J. Berkvens
 */
public class Bank_Server extends UnicastRemoteObject implements IBank {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final String bankName;
    private final String bankIdentity;
    private int nextAccountNr = 1000;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for the Bank_Server. This runs as a separate server for a specific bank.
     *
     * @param bankName is the name of the bank.
     *
     * @throws java.rmi.RemoteException because of the RMI protocol.
     */
    public Bank_Server(String bankName) throws RemoteException {
        this.bankName = bankName;
        switch (bankName) {
            case "ING": {
                bankIdentity = "INGB";
                break;
            }
            case "RaboBank": {
                bankIdentity = "RABO";
                break;
            }
            case "SNS": {
                bankIdentity = "SNSB";
                break;
            }
            case "ABN_AMRO": {
                bankIdentity = "ABNA";
                break;
            }
            case "ASN": {
                bankIdentity = "ASNB";
                break;
            }
            default: {
                bankIdentity = "";
                break;
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getNextAccountNr()">
    private String getNextAccountNr() {
        String returner = null;
        String passwordDatabase = null;
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");

            preparedStatement = conn.prepareStatement("SELECT IBAN FROM " + bankName + "_Account");
            resultSet = preparedStatement.executeQuery();
            int highestNr = 0;
            while (resultSet.next()) {
                int tmp = Integer.parseInt(resultSet.getString("IBAN").substring(9));
                if (tmp > highestNr) {
                    highestNr = tmp;
                }
            }
            returner = "" + ++highestNr;
            while (returner.length() < 10) {
                returner = "0" + returner;
            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Bank_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="registerNewAccount()">
    @Override
    public IAccount registerNewAccount(IUser user) {
        IAccount returner = null;
        Connection conn;
        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");
            preparedStatement = conn.prepareStatement("INSERT INTO " + bankName + "_Account (IBAN, Username) VALUES (?, ?)");
            Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode(bankIdentity).accountNumber(getNextAccountNr()).build();
            preparedStatement.setString(1, iban.toString());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.execute();
            returner = new Account(bankName, iban.toString(), 0, 100);
            user.addAccount(returner);
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException | RemoteException ex) {
            Logger.getLogger(Bank_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>
    //</editor-fold>
}
