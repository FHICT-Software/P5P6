/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboomstorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import stamboomdomain.Administratie;

public class SerializationMediator implements IStorageMediator {

    private Properties props;

    /**
     * creation of a non configured serialization mediator
     */
    public SerializationMediator() {
        props = null;
    }

    public SerializationMediator(Properties props) {
        configure(props);
    }

    @Override
    public Administratie load() throws IOException {
        if (!isCorrectlyConfigured()) {
            throw new RuntimeException("Serialization mediator isn't initialized correctly.");
        }        
        // todo opgave 2
        FileInputStream stream = new FileInputStream(props.getProperty("file"));
        ObjectInputStream in = new ObjectInputStream(stream);
        Administratie admin = null;
        try {
            admin = (Administratie) in.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SerializationMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        return admin;
    }

    @Override
    public void save(Administratie admin) throws IOException {
        if (!isCorrectlyConfigured()) {
            throw new RuntimeException("Serialization mediator isn't initialized correctly.");
        }

        // todo opgave 2
        FileOutputStream stream = new FileOutputStream(props.getProperty("file"));
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(admin);
        out.close();
        stream.close();
    }

    @Override
    public boolean configure(Properties props) {
        this.props = props;
        return isCorrectlyConfigured();
    }

    @Override
    public Properties config() {
        return props;
    }

    /**
     *
     * @return true if config() contains at least a key "file" and the
     * corresponding value is a File-object, otherwise false
     */
    @Override
    public boolean isCorrectlyConfigured() {
        if (props == null) {
            return false;
        }
        if (props.containsKey("file")) {
            return props.get("file") instanceof File;
        } else {
            return false;
        }
    }
}
