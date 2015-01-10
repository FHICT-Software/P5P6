//<editor-fold defaultstate="collapsed" desc="Jibberish">
package bank_client;

import interfaces.IAccount;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Client_Transfer. //CHECK
 *
 * @author J.B.A.J. Berkvens
 */
public class Client_Transfer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private Stage STAGE;
    private String bank;
    private IAccount account;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This operation starts the GUI where a user can Transfer money to a different account.
     *
     * @param stage   is the stage where the GUI is designed on.
     * @param account is the current selected account (where the money comes from).
     * @param parent  is the parent form (when a transaction has been made, the Saldo can be pushed here.
     */
    public void start(Stage stage, IAccount account, Bank_Client parent) {
        try {
            this.bank = account.getBank();
            this.account = account;
            BorderPane borderPane = new BorderPane();
            GridPane gridPane = new GridPane();
            gridPane.setHgap(15);
            gridPane.setVgap(15);
            Label toAccountLabel = new Label("IBAN:");
            gridPane.add(toAccountLabel, 1, 1);
            TextField toAccountTextField = new TextField();
            gridPane.add(toAccountTextField, 2, 1);
            Label amountLabel = new Label("Amount:");
            gridPane.add(amountLabel, 1, 2);
            TextField amountTextField = new TextField();
            amountTextField.setOnKeyTyped((KeyEvent event) -> {
                if (!event.getCharacter().matches("[0-9,.]")) {
                    event.consume();
                }
            });
            gridPane.add(amountTextField, 2, 2);
            Button confirmButton = new Button("Confirm");
            confirmButton.setOnAction((ActionEvent ActionEvent) -> {
                try {
                    String IBAN = toAccountTextField.getText();
                    String code = IBAN.substring(4, 8);
                    String otherBank = "";
                    switch (code) {
                        case "INGB": {
                            otherBank = "ING";
                            break;
                        }
                        case "RABO": {
                            otherBank = "RaboBank";
                            break;
                        }
                        case "SNSB": {
                            otherBank = "SNS";
                            break;
                        }
                        case "ABNA": {
                            otherBank = "ABN_AMRO";
                            break;
                        }
                        case "ASNB": {
                            otherBank = "ASN";
                            break;
                        }
                        default: {
                            otherBank = "";
                            break;
                        }
                    }
                    this.account.transfer(otherBank, Double.parseDouble(amountTextField.getText()), IBAN);
                    parent.updateAccount(account);
                    parent.updateAccount(this.account);
                    STAGE.close();
                } catch (RemoteException ex) {
                    Logger.getLogger(Client_Transfer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            confirmButton.setDefaultButton(true);
            gridPane.add(confirmButton, 2, 3);
            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction((ActionEvent) -> {
                STAGE.close();
            });
            gridPane.add(cancelButton, 1, 3);
            borderPane.setCenter(gridPane);
            Scene scene = new Scene(borderPane);
            stage.setTitle("MP-Banking (" + bank + ")");
            stage.setScene(scene);
            stage.show();
            this.STAGE = stage;
            STAGE.setHeight(STAGE.getHeight() + 15);
            STAGE.setWidth(STAGE.getWidth() + 15);
        } catch (RemoteException ex) {
            Logger.getLogger(Client_Transfer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    //</editor-fold>
}
