/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboomgui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stamboomcontroller.StamboomController;
import stamboomdomain.Geslacht;
import stamboomdomain.Gezin;
import stamboomdomain.Persoon;
import stamboomutil.StringUtilities;

/**
 *
 * @author frankpeeters
 */
public class StamboomFXController extends StamboomController implements Initializable {

    //<editor-fold defaultstate="collepsed" desc="FXML IDs">
    //MENUs en TABs
    @FXML MenuBar menuBar;
    @FXML MenuItem miNew;
    @FXML MenuItem miOpen;
    @FXML MenuItem miSave;
    @FXML CheckMenuItem cmDatabase;
    @FXML MenuItem miClose;
    @FXML Tab tabPersoon;
    @FXML Tab tabGezin;
    @FXML Tab tabPersoonInvoer;
    @FXML Tab tabGezinInvoer;

    //PERSOON
    @FXML ComboBox cbPersonen;
    @FXML TextField tfPersoonNr;
    @FXML TextField tfVoornamen;
    @FXML TextField tfTussenvoegsel;
    @FXML TextField tfAchternaam;
    @FXML TextField tfGeslacht;
    @FXML TextField tfGebDatum;
    @FXML TextField tfGebPlaats;
    @FXML ComboBox cbOuderlijkGezin;
    @FXML ListView lvAlsOuderBetrokkenBij;
    @FXML TextArea taStamboom;
    @FXML Button btStamboom;

    //GEZIN
    @FXML Label labelOuder1;
    @FXML Label labelOuder2;
    @FXML TextField txtHuwDatum;
    @FXML TextField txtScheidingDatum;
    @FXML ComboBox cbGezinnen;
    @FXML Button btSetHuwDatum;
    @FXML Button btSetScheidingsDatum;

    //INVOER PERSOON
    @FXML TextField txtPersoonNr;
    @FXML TextField txtVoornamen;
    @FXML TextField txtTussenvoegsel;
    @FXML TextField txtAchternaam;
    @FXML TextField txtGeslacht;
    @FXML TextField txtGebDatum;
    @FXML TextField txtGebPlaats;
    @FXML ComboBox cbOuderlijkGezinNewPersoon;
    @FXML Button btNewPersoon;
    @FXML Button btCancelPersoon;

    //INVOER GEZIN
    @FXML ComboBox cbOuder1Invoer;
    @FXML ComboBox cbOuder2Invoer;
    @FXML TextField tfHuwelijkInvoer;
    @FXML TextField tfScheidingInvoer;
    @FXML Button btOKGezinInvoer;
    @FXML Button btCancelGezinInvoer;

    //</editor-fold>
    //opgave 4
    private StamboomController controller;
    private boolean withDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboboxes();
        withDatabase = false;
        controller = new StamboomController();
    }

    private void initComboboxes() {
        //todo opgave 3
        cbGezinnen.setItems(getAdministratie().getGezinnen());
        cbOuder1Invoer.setItems(getAdministratie().getPersonen());
        cbOuder2Invoer.setItems(getAdministratie().getPersonen());
        cbOuderlijkGezin.setItems(getAdministratie().getGezinnen());
        cbOuderlijkGezinNewPersoon.setItems(getAdministratie().getGezinnen());
        cbPersonen.setItems(getAdministratie().getPersonen());
    }

    public void selectPersoon(Event evt) {
        Persoon persoon = (Persoon) cbPersonen.getSelectionModel().getSelectedItem();
        showPersoon(persoon);
    }

    private void showPersoon(Persoon persoon) {
        if (persoon == null) {
            clearTabPersoon();
        } else {
            tfPersoonNr.setText(persoon.getNr() + "");
            tfVoornamen.setText(persoon.getVoornamen());
            tfTussenvoegsel.setText(persoon.getTussenvoegsel());
            tfAchternaam.setText(persoon.getAchternaam());
            tfGeslacht.setText(persoon.getGeslacht().toString());
            tfGebDatum.setText(StringUtilities.datumString(persoon.getGebDat()));
            tfGebPlaats.setText(persoon.getGebPlaats());
            if (persoon.getOuderlijkGezin() != null) {
                cbOuderlijkGezin.getSelectionModel().select(persoon.getOuderlijkGezin());
            } else {
                cbOuderlijkGezin.getSelectionModel().clearSelection();
            }

            //todo opgave 3
            //lvAlsOuderBetrokkenBij.setItems(persoon.getAlsOuderBetrokkenIn());
        }
    }

    public void setOuders(Event evt) {
        if (tfPersoonNr.getText().isEmpty()) {
            return;
        }
        Gezin ouderlijkGezin = (Gezin) cbOuderlijkGezin.getSelectionModel().getSelectedItem();
        if (ouderlijkGezin == null) {
            return;
        }

        int nr = Integer.parseInt(tfPersoonNr.getText());
        Persoon p = getAdministratie().getPersoon(nr);
        getAdministratie().setOuders(p, ouderlijkGezin);
    }

    public void selectGezin(Event evt) {
        // todo opgave 3
        Gezin gezin = (Gezin) cbGezinnen.getSelectionModel().getSelectedItem();
        showGezin(gezin);
    }

    private void showGezin(Gezin gezin) {
        // todo opgave 3
        if (gezin == null) {
            clearTabGezin();
        } else {
            labelOuder1.setText(gezin.getOuder1().toString());
            labelOuder2.setText(gezin.getOuder2().toString());
            txtHuwDatum.setText(gezin.getHuwelijksdatum().toString());
            txtScheidingDatum.setText(gezin.getScheidingsdatum().toString());
            if (gezin.getHuwelijksdatum() != null) {
                btSetHuwDatum.setVisible(false);
            } else {
                btSetScheidingsDatum.setVisible(true);
            }
        }
    }

    public void setHuwdatum(Event evt) {
        Gezin ouderlijkGezin = (Gezin) cbOuderlijkGezin.getSelectionModel().getSelectedItem();
        Calendar calendar = new GregorianCalendar();
        int year = Integer.parseInt(txtHuwDatum.getText().substring(6, 4));
        int month = Integer.parseInt(txtHuwDatum.getText().substring(3, 2));
        int date = Integer.parseInt(txtHuwDatum.getText().substring(0, 2));
        calendar.set(year, month, date);
        ouderlijkGezin.setHuwelijk(calendar);
    }

    public void setScheidingsdatum(Event evt) {
        Gezin ouderlijkGezin = (Gezin) cbOuderlijkGezin.getSelectionModel().getSelectedItem();
        Calendar calendar = new GregorianCalendar();
        int year = Integer.parseInt(txtScheidingDatum.getText().substring(6, 4));
        int month = Integer.parseInt(txtScheidingDatum.getText().substring(3, 2));
        int date = Integer.parseInt(txtScheidingDatum.getText().substring(0, 2));
        calendar.set(year, month, date);
        ouderlijkGezin.setScheiding(calendar);
    }

    public void cancelPersoonInvoer(Event evt) {
        // todo opgave 3
        clearTabPersoonInvoer();

    }

    public void okPersoonInvoer(Event evt) {

        String[] vnamen = txtVoornamen.getText().split(" ");

        String tvoegsel = txtTussenvoegsel.getText();

        String anaam = txtAchternaam.getText();

        Geslacht geslacht = null;
        if (txtGeslacht.getText().toLowerCase().charAt(0) == 'm') {
            geslacht = Geslacht.MAN;
        } else if (txtGeslacht.getText().toLowerCase().charAt(0) == 'v') {
            geslacht = Geslacht.VROUW;
        } else {
            showDialog("Warning", "Geslacht is niet juist ingevult");
        }

        Calendar gebdat = new GregorianCalendar();
        int year = Integer.parseInt(txtGebDatum.getText().substring(6, 4));
        int month = Integer.parseInt(txtGebDatum.getText().substring(3, 2));
        int date = Integer.parseInt(txtGebDatum.getText().substring(0, 2));
        gebdat.set(year, month, date);

        String gebplaats = txtGebPlaats.getText();

        Gezin ouders;
        if (cbOuderlijkGezinNewPersoon.getSelectionModel().equals("")) {
            ouders = null;
            getAdministratie().addPersoon(geslacht, vnamen, anaam, tvoegsel, gebdat, gebplaats, ouders);
        } else {
            ouders = (Gezin) cbOuderlijkGezinNewPersoon.getSelectionModel().getSelectedItem();
            ouders.breidUitMet(getAdministratie().addPersoon(geslacht, vnamen, anaam, tvoegsel, gebdat, gebplaats, ouders));
        }
    }

    public void okGezinInvoer(Event evt) {
        Persoon ouder1 = (Persoon) cbOuder1Invoer.getSelectionModel().getSelectedItem();
        if (ouder1 == null) {
            showDialog("Warning", "eerste ouder is niet ingevoerd");
            return;
        }
        Persoon ouder2 = (Persoon) cbOuder2Invoer.getSelectionModel().getSelectedItem();
        Calendar huwdatum;
        try {
            huwdatum = StringUtilities.datum(tfHuwelijkInvoer.getText());
        } catch (IllegalArgumentException exc) {
            showDialog("Warning", "huwelijksdatum :" + exc.getMessage());
            return;
        }
        Gezin g;
        if (huwdatum != null) {
            g = getAdministratie().addHuwelijk(ouder1, ouder2, huwdatum);
            if (g == null) {
                showDialog("Warning", "Invoer huwelijk is niet geaccepteerd");
            } else {
                Calendar scheidingsdatum;
                try {
                    scheidingsdatum = StringUtilities.datum(tfScheidingInvoer.getText());
                    getAdministratie().setScheiding(g, scheidingsdatum);
                } catch (IllegalArgumentException exc) {
                    showDialog("Warning", "scheidingsdatum :" + exc.getMessage());
                }
            }
        } else {
            g = getAdministratie().addOngehuwdGezin(ouder1, ouder2);
            if (g == null) {
                showDialog("Warning", "Invoer ongehuwd gezin is niet geaccepteerd");
            }
        }

        clearTabGezinInvoer();
    }

    public void cancelGezinInvoer(Event evt) {
        clearTabGezinInvoer();
    }

    public void showStamboom(Event evt) {
        // todo opgave 3
        Persoon persoon = (Persoon) cbPersonen.getSelectionModel().getSelectedItem();
        taStamboom.setText(persoon.stamboomAlsString());
    }

    public void createEmptyStamboom(Event evt) {
        this.clearAdministratie();
        clearTabs();
        initComboboxes();
    }

    public void openStamboom(Event evt) {
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.length() - 4);
        //Testing voor Jeroen
        path = "/home/jeroen/Workspaces/Java/GIT/C2J/StamboomProject/dist/";
        File file = new File(path, "Backup.ser");
        try {
            controller.deserialize(file);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveStamboom(Event evt) {
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.length() - 4);
        //Testing voor Jeroen
        path = "/home/jeroen/Workspaces/Java/GIT/C2J/StamboomProject/dist/";
        File file = new File(path, "Backup.ser");
        try {
            controller.serialize(file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeApplication(Event evt) {
        saveStamboom(evt);
        getStage().close();
    }

    public void configureStorage(Event evt) {
        withDatabase = cmDatabase.isSelected();
    }

    public void selectTab(Event evt) {
        Object source = evt.getSource();
        if (source == tabPersoon) {
            clearTabPersoon();
        } else if (source == tabGezin) {
            clearTabGezin();
        } else if (source == tabPersoonInvoer) {
            clearTabPersoonInvoer();
        } else if (source == tabGezinInvoer) {
            clearTabGezinInvoer();
        }
    }

    private void clearTabs() {
        clearTabPersoon();
        clearTabPersoonInvoer();
        clearTabGezin();
        clearTabGezinInvoer();
    }

    private void clearTabPersoonInvoer() {
        //todo opgave 3
        txtPersoonNr.clear();
        txtVoornamen.clear();
        txtTussenvoegsel.clear();
        txtAchternaam.clear();
        txtGeslacht.clear();
        txtGebDatum.clear();
        txtGebPlaats.clear();
        cbOuderlijkGezinNewPersoon.getSelectionModel().clearSelection();
    }

    private void clearTabGezinInvoer() {
        //todo opgave 3
        cbOuder1Invoer.getSelectionModel().clearSelection();
        cbOuder2Invoer.getSelectionModel().clearSelection();
        tfHuwelijkInvoer.clear();
        tfScheidingInvoer.clear();
    }

    private void clearTabPersoon() {
        cbPersonen.getSelectionModel().clearSelection();
        tfPersoonNr.clear();
        tfVoornamen.clear();
        tfTussenvoegsel.clear();
        tfAchternaam.clear();
        tfGeslacht.clear();
        tfGebDatum.clear();
        tfGebPlaats.clear();
        cbOuderlijkGezin.getSelectionModel().clearSelection();
        lvAlsOuderBetrokkenBij.setItems(FXCollections.emptyObservableList());
    }

    private void clearTabGezin() {
        //todo opgave 3
        labelOuder1.setText("");
        labelOuder2.setText("");
        txtHuwDatum.clear();
        txtScheidingDatum.clear();
        cbGezinnen.getSelectionModel().clearSelection();
        btSetHuwDatum.setVisible(true);
        btSetScheidingsDatum.setVisible(true);
    }

    private void showDialog(String type, String message) {
        Stage myDialog = new Dialog(getStage(), type, message);
        myDialog.show();
    }

    private Stage getStage() {
        return (Stage) menuBar.getScene().getWindow();
    }

}
