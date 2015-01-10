/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboomstorage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import stamboomdomain.*;

public class DatabaseMediator implements IStorageMediator {

    private Properties props;
    private Connection conn;

    public DatabaseMediator(Properties props) {
        configure(props);
    }

    @Override
    public Administratie load() throws IOException {
        //todo opgave 4
        if (!isCorrectlyConfigured()) {
            throw new RuntimeException("Database mediator isn't initialized correctly.");
        }
        return null;
    }

    @Override
    public void save(Administratie admin) throws IOException {
        //todo opgave 4
        if (!isCorrectlyConfigured()) {
            try {
                initConnection();
                PreparedStatement stat;
                for (Persoon p : admin.getPersonen()) {
                    String query = "INSERT INTO PERSOON VALUES (?,?,?,?,?,?,?,?)";
                    stat = conn.prepareStatement(query);
                    stat.setInt(1, p.getNr());
                    stat.setString(2, p.getVoornamen());
                    stat.setString(3, p.getAchternaam());
                    stat.setString(4, p.getTussenvoegsel());
                    stat.setDate(5, new Date(p.getGebDat().get(Calendar.YEAR) - 1900, p.getGebDat().get(Calendar.MONTH), p.getGebDat().get(Calendar.DAY_OF_MONTH)));
                    stat.setString(6, p.getGebPlaats());
                    stat.setString(7, p.getGeslacht().name());
                    stat.setNull(8, java.sql.Types.INTEGER);
                    stat.execute();
                }
                for (Gezin g : admin.getGezinnen()) {
                    String query = "INSERT INTO GEZIN VALUES (?,?,?,?,?)";
                    stat = conn.prepareStatement(query);
                    stat.setInt(1, g.getNr());
                    stat.setInt(2, g.getOuder1().getNr());
                    stat.setInt(3, g.getOuder2().getNr());
                    try {
                        stat.setDate(4, new Date(g.getHuwelijksdatum().get(Calendar.YEAR) - 1900, g.getHuwelijksdatum().get(Calendar.MONTH), g.getHuwelijksdatum().get(Calendar.DAY_OF_MONTH)));
                    } catch (NullPointerException e) {
                        stat.setDate(4, null);
                    }
                    try {
                        stat.setDate(5, new Date(g.getScheidingsdatum().get(Calendar.YEAR) - 1900, g.getScheidingsdatum().get(Calendar.MONTH), g.getScheidingsdatum().get(Calendar.DAY_OF_MONTH)));
                    } catch (NullPointerException e) {
                        stat.setDate(5, null);
                    }
                    stat.execute();
                }
                for (Persoon p : admin.getPersonen()) {
                    if (p.getOuderlijkGezin() != null) {
                        String query = "UPDATE PERSOON SET OUDERLIJKGEZIN = ? WHERE PERSOONSNUMMER = ?";
                        stat = conn.prepareStatement(query);
                        stat.setInt(1, p.getOuderlijkGezin().getNr());
                        stat.setInt(2, p.getNr());
                        stat.execute();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public final boolean configure(Properties props) {
        this.props = props;

        try {
            initConnection();
            return isCorrectlyConfigured();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            this.props = null;
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Properties config() {
        return props;
    }

    @Override
    public boolean isCorrectlyConfigured() {
        if (props == null) {
            return false;
        }
        if (!props.containsKey("driver")) {
            return false;
        }
        if (!props.containsKey("url")) {
            return false;
        }
        if (!props.containsKey("username")) {
            return false;
        }
        if (!props.containsKey("password")) {
            return false;
        }
        return true;
    }

    private void initConnection() throws SQLException {
        //opgave 4
        conn = DriverManager.getConnection("jdbc:mysql:", props);
    }

    private void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
