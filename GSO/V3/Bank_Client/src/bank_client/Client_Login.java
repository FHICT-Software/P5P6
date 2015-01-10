//<editor-fold defaultstate="collapsed" desc="Jibberish">
package bank_client;

import interfaces.IAccount;
import interfaces.IBank;
import interfaces.ICentral;
import interfaces.IUser;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Client_Login. //CHECK
 *
 * @author J.B.A.J. Berkvens
 */
public class Client_Login extends Application {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private Stage STAGE;
    boolean registering = false;
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="start(stage)">
    /**
     * This operation starts the GUI where a user can Login.
     *
     * @param stage is the stage where the GUI is designed on.
     */
    @Override
    public void start(Stage stage) {
        //<editor-fold defaultstate="collapsed" desc="GridPane">
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        Label errorLabel = new Label();
        errorLabel.setVisible(false);
        //<editor-fold defaultstate="collapsed" desc="Register">
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameTextField = new TextField();
        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameTextField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameTextField = new TextField();
        Label addressLabel = new Label("Address:");
        TextField addressTextField = new TextField();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Main">
        Label usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 5);
        TextField usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 5);
        Label passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 1, 6);
        PasswordField passwordTextField = new PasswordField();
        gridPane.add(passwordTextField, 2, 6);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Register">
        Label passwordConfirmLabel = new Label("Confirm Password");
        passwordConfirmLabel.setVisible(false);
        gridPane.add(passwordConfirmLabel, 1, 7);
        PasswordField passwordConfirmTextField = new PasswordField();
        passwordConfirmTextField.setVisible(false);
        gridPane.add(passwordConfirmTextField, 2, 7);
        Label bankLabel = new Label("Bank:");
        ComboBox<String> bankComboBox = new ComboBox<>();
        bankComboBox.getItems().addAll("ABN AMRO", "ASN", "ING", "RaboBank", "SNS");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Button Login">
        Button loginButton = new Button("Login");
        loginButton.setOnAction((ActionEvent event) -> {
            IUser user = login(usernameTextField.getText(), passwordTextField.getText());
            if (user != null) {
                Bank_Client bank_Client = new Bank_Client();
                bank_Client.start(new Stage(), user);
                STAGE.close();
            } else {
                errorLabel.setText("Login Incorrect!");
                errorLabel.setVisible(true);
            }
        });
        loginButton.setDefaultButton(true);
        gridPane.add(loginButton, 2, 9);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Button Register">
        Button registerButton = new Button("Register");
        registerButton.setOnAction((ActionEvent event) -> {
            //<editor-fold defaultstate="collapsed" desc="Show Fields">
            if (!registering) {
                registering = true;
                gridPane.add(firstNameLabel, 1, 1);
                gridPane.add(firstNameTextField, 2, 1);
                gridPane.add(middleNameLabel, 1, 2);
                gridPane.add(middleNameTextField, 2, 2);
                gridPane.add(lastNameLabel, 1, 3);
                gridPane.add(lastNameTextField, 2, 3);
                gridPane.add(addressLabel, 1, 4);
                gridPane.add(addressTextField, 2, 4);
                passwordConfirmLabel.setVisible(true);
                passwordConfirmTextField.setVisible(true);
                gridPane.add(bankLabel, 1, 8);
                gridPane.add(bankComboBox, 2, 8);
                loginButton.setDefaultButton(false);
                registerButton.setDefaultButton(true);
                firstNameTextField.requestFocus();
                STAGE.setHeight(STAGE.getHeight() + 120);
                STAGE.setWidth(STAGE.getWidth() + 40);
            } //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Register">
            else {
                String bank = bankComboBox.getValue();
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                String firstName = firstNameTextField.getText();
                String middleName = middleNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String address = addressTextField.getText();
                IUser user = register(bank, username, password, firstName, middleName, lastName, address);
                if (user != null) {
                    Bank_Client bank_Client = new Bank_Client();
                    bank_Client.start(new Stage(), user);
                    STAGE.close();
                } else {
                    errorLabel.setText("Login Incorrect!");
                    errorLabel.setVisible(true);
                }
            }
            //</editor-fold>
        });
        gridPane.add(registerButton, 1, 9);
        //</editor-fold>

        BorderPane borderPane = new BorderPane(gridPane);
        borderPane.setPadding(new Insets(10));
        borderPane.setBottom(errorLabel);
        Scene scene = new Scene(borderPane);
        //</editor-fold>

        stage.setTitle("Login - MP-Banking");
        stage.setScene(scene);
        stage.show();
        this.STAGE = stage;
        STAGE.setHeight(STAGE.getHeight() + 15);
        STAGE.setWidth(STAGE.getWidth() + 15);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="login()">
    /**
     * This operation lets the user login.
     *
     * @param username is the username used to login.
     * @param password is the password for the user.
     */
    private IUser login(String username, String password) {
        IUser returner = null;
        try (FileInputStream in = new FileInputStream("Server.props")) {
            Properties props = new Properties();
            props.load(in);
            String server = props.getProperty("server");
            ICentral iCentral = (ICentral) Naming.lookup("rmi://" + server + "/Central");
            returner = iCentral.login(username, password);
        } catch (IOException | NotBoundException ex) {
            Logger.getLogger(Client_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="register()">
    /**
     * This operation lets a new user register.
     *
     * @param bank       is the bank where the user will have his first account.
     * @param username   is the username used to register.
     * @param password   is the password for the new user.
     * @param firstName  is the users first name.
     * @param middleName is the users middle name.
     * @param lastName   is the users last name.
     * @param address    is the users address.
     *
     * @return the new User component.
     */
    private IUser register(String bank, String username, String password, String firstName, String middleName, String lastName, String address) {
        bank = bank.replace(" ", "_");
        IUser returner = null;
        try (FileInputStream in = new FileInputStream("Server.props")) {
            Properties props = new Properties();
            props.load(in);
            String server = props.getProperty("server");
            ICentral iCentral = (ICentral) Naming.lookup("rmi://" + server + "/Central");
            returner = iCentral.register(username, password, firstName, middleName, lastName, address);
            register(bank, returner);
        } catch (IOException | NotBoundException ex) {
            Logger.getLogger(Client_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="register()">
    /**
     * This operation registers the first account for a new user.
     *
     * @param bank is the bank where the account is registered.
     * @param user is the user for who the account will be created.
     *
     * @return the generated account.
     */
    private IAccount register(String bank, IUser user) {
        IAccount returner = null;
        try (FileInputStream in = new FileInputStream(bank + ".props")) {
            Properties props = new Properties();
            props.load(in);
            String server = props.getProperty("server");
            IBank iBank = (IBank) Naming.lookup("rmi://" + server + "/" + bank);
            returner = iBank.registerNewAccount(user);
        } catch (IOException | NotBoundException ex) {
            Logger.getLogger(Client_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main(args)">
    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores
     * main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    //</editor-fold>
    //</editor-fold>
}
