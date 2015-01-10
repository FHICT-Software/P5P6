package jsfweek2opd1;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phinux
 */
public class JSFW2OPD1 {

    /**
     * Maak een Java programma dat deze environment variabele inleest en dat
     * zowel de naam als de waarde van die variabele toont als console output.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {
            String ingelezenVariable = System.getenv("TestEnviron");
            System.out.println(ingelezenVariable);

            Properties defaultProps = loadProperties();
            addProperties(defaultProps, ingelezenVariable);
        } catch (NullPointerException exc) {
            System.out.println("Er is geen variable mee gegeven");
        }

    }

    private static void addProperties(Properties defaultProps, String value) {
        defaultProps.setProperty("TestEnviron", value);
        saveProperties(defaultProps);
    }

    private static void saveProperties(Properties defaultProps) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("/home/phinux/Workspaces/Java/defaultProperties.txt");
            defaultProps.store(out, "--NO Comment ---");
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSFW2OPD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSFW2OPD1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Properties loadProperties() {
        FileInputStream in = null;
        Properties defaultProps = new Properties();
        try {
            // create and load default properties
            in = new FileInputStream("/home/phinux/Workspaces/Java/defaultProperties.txt");
            defaultProps.load(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSFW2OPD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSFW2OPD1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(JSFW2OPD1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return defaultProps;
    }

}
