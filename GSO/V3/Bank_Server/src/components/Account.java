//<editor-fold defaultstate="collapsed" desc="Jibberish">
package components;

import bank_server.Central_Server;
import interfaces.IAccount;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Account. //CHECK
 *
 * @author J.B.A.J. Berkvens
 */
public class Account extends UnicastRemoteObject implements IAccount {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final String bank;
    private final String IBAN;
    private double saldo = 0;
    private double credit = 100;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    @Override
    public String getBank() throws RemoteException {
        return bank;
    }

    @Override
    public String getIBAN() {
        return IBAN;
    }

    @Override
    public double getSaldo() {
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");
            preparedStatement = conn.prepareStatement("SELECT Saldo FROM " + bank + "_Account WHERE IBAN = ?");
            preparedStatement.setString(1, IBAN);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                saldo = resultSet.getDouble("Saldo");
            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Central_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saldo;
    }

    private void addToSaldo(double amount) {
        this.saldo += amount;
        Connection conn;
        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");
            preparedStatement = conn.prepareStatement("UPDATE " + bank + "_Account SET Saldo = ? WHERE IBAN = ?");
            preparedStatement.setDouble(1, saldo);
            preparedStatement.setString(2, IBAN);
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeFromSaldo(double amount) {
        this.saldo -= amount;
        Connection conn;
        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");
            preparedStatement = conn.prepareStatement("UPDATE " + bank + "_Account SET Saldo = ? WHERE IBAN = ?");
            preparedStatement.setDouble(1, saldo);
            preparedStatement.setString(2, IBAN);
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double getCredit() {
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");
            preparedStatement = conn.prepareStatement("SELECT Credit FROM " + bank + "_Account WHERE IBAN = ?");
            preparedStatement.setString(1, IBAN);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                credit = resultSet.getDouble("Credit");
            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Central_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return credit;
    }
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for Account.
     *
     * @param bank   is the bank where this account is from.
     * @param IBAN   is the unique definition of the account.
     * @param saldo  is the amount of money in this account.
     * @param credit is the maximum amount the saldo can be negative.
     *
     * @throws java.rmi.RemoteException because of the RMI protocol.
     */
    public Account(String bank, String IBAN, double saldo, double credit) throws RemoteException {
        this.bank = bank;
        this.IBAN = IBAN;
        this.saldo = saldo;
        this.credit = credit;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="transfer(toAccount)">
    @Override
    public boolean transfer(String bankName, double ammount, String IBAN) {
        try {
            Account account = Account.get(bankName, IBAN);
            if (getSaldo() + getCredit() > ammount) {
                this.removeFromSaldo(ammount);
                account.addToSaldo(ammount);
                return true;
            } else {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get(bank, name, accountNr)">
    /**
     * This operation returns the account that meets the parameter requirements.
     *
     * @param bankName is the name of the bank where the account is from.
     * @param IBAN     is the unique definition of the account.
     *
     * @return the Account that meets the parameter requirements.
     *
     * @throws RemoteException because of the RMI protocol.
     */
    public static Account get(String bankName, String IBAN) throws RemoteException {
        Account returner = null;
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://95.211.77.109:3306/jeroen001_Bank", "jeroen001_GSO", "qxhI5j119d");

            preparedStatement = conn.prepareStatement("SELECT * FROM " + bankName + "_Account WHERE IBAN = ?");
            preparedStatement.setString(1, IBAN);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                double saldo = resultSet.getDouble("Saldo");
                double credit = resultSet.getDouble("Credit");
                returner = new Account(bankName, IBAN, saldo, credit);
            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Central_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Class">
    @Override
    public void matchTo(IAccount account) {
        try {
            this.credit = account.getCredit();
            this.saldo = account.getSaldo();
        } catch (RemoteException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Account) {
            return this.equals((Account) object);
        } else {
            return false;
        }
    }

    public boolean equals(Account account) {
        if (this.IBAN.equals(account.IBAN)) {
            return true;
        } else {
            return false;
        }
    }
    //</editor-fold>
}
