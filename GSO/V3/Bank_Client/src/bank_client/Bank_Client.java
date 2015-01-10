//<editor-fold defaultstate="collapsed" desc="Jibberish">
package bank_client;

import interfaces.IAccount;
import interfaces.IBank;
import interfaces.IUser;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Bank_Client. //CHECK
 *
 * @author J.B.A.J. Berkvens
 */
public class Bank_Client {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private Stage STAGE;
    private IUser user;
    private IAccount currentAccount;
    private Label accountLabel;
    private Label saldoLabel;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="start(stage)">
    /**
     * This operation starts the GUI for the main Bank_Client.
     *
     * @param stage is the stage where the GUI is designed on.
     * @param user  is the current User.
     */
    public void start(Stage stage, IUser user) {
        this.user = user;
        this.STAGE = stage;
        try {
            currentAccount = this.user.getAccounts().get(0);
            //<editor-fold defaultstate="collapsed" desc="Border Pane">
            accountLabel = new Label("" + currentAccount.getIBAN());
            saldoLabel = new Label("€" + currentAccount.getSaldo());
            BorderPane borderPane = new BorderPane();
            GridPane gridPane = new GridPane();
            gridPane.setHgap(15);
            gridPane.setVgap(15);
            Button registerButton = new Button("Register");
            ComboBox<String> bankComboBox = new ComboBox<>();
            ComboBox<String> accountsComboBox = new ComboBox<>();

            //<editor-fold defaultstate="collapsed" desc="Account Combo Box">
            for (IAccount account : this.user.getAccounts()) {
                accountsComboBox.getItems().add(account.getIBAN());
            }
            accountsComboBox.getItems().add("Add New");
            accountsComboBox.setOnAction((ActionEvent event) -> {
                if (accountsComboBox.getValue().equals("Add New")) {
                    bankComboBox.setVisible(true);
                    registerButton.setVisible(true);
                } else if (!accountsComboBox.getValue().isEmpty()) {
                    try {
                        for (IAccount account : user.getAccounts()) {
                            if (account.getIBAN().equals(accountsComboBox.getValue())) {
                                currentAccount = account;
                                saldoLabel.setText("€" + currentAccount.getSaldo());
                                accountLabel.setText(currentAccount.getIBAN());
                                STAGE.setTitle("MP-Banking (" + currentAccount.getBank() + ")");
                            }
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Bank_Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            accountsComboBox.setValue(accountsComboBox.getItems().get(0));
            gridPane.add(accountsComboBox, 1, 1);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Bank Combo Box">
            bankComboBox.getItems().addAll("ABN AMRO", "ASN", "ING", "RaboBank", "SNS");
            bankComboBox.setVisible(false);
            bankComboBox.setValue("Select Bank");
            gridPane.add(bankComboBox, 2, 1);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Register Button">
            registerButton.setOnAction((ActionEvent event) -> {
                try {
                    String bank = bankComboBox.getValue().replace(" ", "_");
                    currentAccount = register(bank);
                    String iban = currentAccount.getIBAN();
                    bank = currentAccount.getBank();
                    double saldo = currentAccount.getSaldo();
                    accountsComboBox.getItems().remove(accountsComboBox.getItems().size() - 1);
                    accountsComboBox.getItems().add(iban);
                    accountsComboBox.getItems().add("Add New");
                    saldoLabel.setText("€" + saldo);
                    accountLabel.setText(iban);
                    accountsComboBox.setValue(iban);
                    STAGE.setTitle("MP-Banking (" + bank + ")");
                    bankComboBox.getItems().clear();
                    bankComboBox.getItems().addAll("ABN AMRO", "ASN", "ING", "RaboBank", "SNS");
                    bankComboBox.setValue("Select Bank");
                    bankComboBox.setVisible(false);
                    registerButton.setVisible(false);
                } catch (RemoteException ex) {
                    Logger.getLogger(Bank_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            registerButton.setVisible(false);
            gridPane.add(registerButton, 3, 1);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Details (Saldo, Account)">
            Label accountDLabel = new Label("Account:");
            gridPane.add(accountDLabel, 1, 2);
            gridPane.add(accountLabel, 2, 2);
            Label saldoDLabel = new Label("Saldo:");
            gridPane.add(saldoDLabel, 1, 3);
            gridPane.add(saldoLabel, 2, 3);
            borderPane.setCenter(gridPane);
            //</editor-fold>

            Label transactionsLabel = new Label("Transactions");
            gridPane.add(transactionsLabel, 4, 3);
            ListView<String> transactionsListView = new ListView<>();
            gridPane.add(transactionsListView, 4, 4, 5, 5);
            Scene scene = new Scene(borderPane);
            Button transferButton = new Button("Transfer");
            transferButton.setOnAction((ActionEvent event) -> {
                Client_Transfer client_Transfer = new Client_Transfer();
                client_Transfer.start(new Stage(), currentAccount, this);
            });
            gridPane.add(transferButton, 1, 9);
            //</editor-fold>
            stage.setTitle("MP-Banking (" + currentAccount.getBank() + ")");
            stage.setScene(scene);
            stage.show();
            STAGE.setHeight(STAGE.getHeight() + 15);
            STAGE.setWidth(STAGE.getWidth() + 15);
        } catch (RemoteException ex) {
            Logger.getLogger(Bank_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="register()">
    /**
     * This operation lets the User register to the application.
     *
     * @param bank is the bank where he wants his first account.
     *
     * @return the account the bank generated.
     */
    private IAccount register(String bank) {
        IAccount returner = null;
        try (FileInputStream in = new FileInputStream(bank + ".props")) {
            Properties props = new Properties();
            props.load(in);
            String server = props.getProperty("server");
            IBank iBank = (IBank) Naming.lookup("rmi://" + server + "/" + bank);
            returner = iBank.registerNewAccount(user);
            String iban = returner.getIBAN();
            double saldo = returner.getSaldo();
        } catch (IOException | NotBoundException ex) {
            Logger.getLogger(Client_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update()">
    /**
     * This operation updates a specific account. When a different element changes something in an account (like Saldo or Credit)
     * it can call this operation and the specified account will be updated.
     *
     * @param account is the account that will be updated.
     */
    public void updateAccount(IAccount account) {
        try {
            for (IAccount userAccount : user.getAccounts()) {
                if (userAccount.equals(account)) {
                    userAccount.matchTo(account);
                    saldoLabel.setText("€" + account.getSaldo());
                    accountLabel.setText(account.getIBAN());
                    STAGE.setTitle("MP-Banking (" + account.getBank() + ")");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Bank_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    //</editor-fold>
}
