package jsfweek2opd3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Maak een derde Java programma dat command-line arguments accepteert, en
 * gebruik deze arguments om de properties file mee te vullen. Bijvoorbeeld:
 * ~/jdk1.7.0_11/bin/java -jar MyProgram.jar prop1 val1 prop2 val2 Dit moet
 * leiden tot een properties file die twee properties bevat: prop1 met waarde
 * val1, en prop2 met waarde val2. Controleer de properties file met het
 * programma dat je gemaakt hebt in stap 2. Let op: je programma moet niet
 * alleen goed werken met 2 properties op de command line, maar ook met een
 * willekeurig aantal properties.
 *
 * @author phinux
 */
public class JSFW2OPD3 {

    private static Properties defaultProps = new Properties();

    public static void main(String[] args) {
        // TODO code application logic here

        //Controleer of er een even aantal argumenten zijn ingevoerd
        if ((args.length % 2) == 0) {
            //vul propertiesclass
            //loadProperties();

            //loop door args
            for(int i= 0; i < args.length; i+=2)
            {
                addProperties(args[i], args[i+1]);
                
            }
            
            //save to propertiesfile
            saveProperties();
        } else {
            //zoniet msg "Invoer is niet compleet"
            System.out.println("Argumenten zijn niet compleet");
        }
    }

    private static void addProperties(String propertieString, String value) {
        defaultProps.setProperty(propertieString, value);
        System.out.println("Propertie " + propertieString + " met waarde " + value + " is toegevoegd.");

    }

    private static void saveProperties() {
        FileOutputStream out;
        try {        
            out = new FileOutputStream("/home/phinux/Workspaces/Java/defaultProperties.txt");
            defaultProps.store(out, "--NO Comment ---");
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSFW2OPD3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSFW2OPD3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static Properties loadProperties() {
        FileInputStream in = null;
        try {
            // create and load default properties
            in = new FileInputStream("/home/phinux/Workspaces/Java/defaultProperties.txt");
            defaultProps.load(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSFW2OPD3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSFW2OPD3.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(JSFW2OPD3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return defaultProps;
    }

}
