/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboomcontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import stamboomdomain.Administratie;
import stamboomstorage.*;

public class StamboomController {

    private Administratie admin;
    private IStorageMediator storageMediator;

    /**
     * creatie van stamboomcontroller met lege administratie en onbekend
     * opslagmedium
     */
    public StamboomController() {
        admin = new Administratie();
        storageMediator = null;
    }

    public Administratie getAdministratie() {
        return admin;
    }

    /**
     * administratie wordt leeggemaakt (geen personen en geen gezinnen)
     */
    public void clearAdministratie() {
        admin = new Administratie();
    }

    /**
     * administratie wordt in geserialiseerd bestand opgeslagen
     *
     * @param bestand
     * @throws IOException
     */
    public void serialize(File bestand) throws IOException {
        if (!(storageMediator instanceof SerializationMediator)) {
            Properties props = new Properties();
            props.setProperty("file", bestand.toString());
            
            storageMediator = new SerializationMediator(props);
        }
        
        storageMediator.save(admin);
        System.out.println("Serialized data is saved in " + bestand.toString());
    }

    /**
     * administratie wordt vanuit geserialiseerd bestand gevuld
     *
     * @param bestand
     * @throws IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void deserialize(File bestand) throws IOException, ClassNotFoundException {
        if (!(storageMediator instanceof SerializationMediator)) {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream(bestand)) {
                props.load(in);
            }
            storageMediator = new SerializationMediator(props);
        }
        
        admin = storageMediator.load();
        System.out.println("Serialized data is loaded from " + bestand.toString());
    }

    // opgave 4
    private void initDatabaseMedium() throws IOException {
        if (!(storageMediator instanceof DatabaseMediator)) {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream("database.properties")) {
                props.load(in);
            }
            storageMediator = new DatabaseMediator(props);
        }
    }

    /**
     * administratie wordt vanuit standaarddatabase opgehaald
     *
     * @throws IOException
     */
    public void loadFromDatabase() throws IOException {
        initDatabaseMedium();
        storageMediator.load();
    }

    /**
     * administratie wordt in standaarddatabase bewaard
     *
     * @throws IOException
     */
    public void saveToDatabase() throws IOException {
        //todo opgave 4
        initDatabaseMedium();
        storageMediator.save(admin);
    }

}
