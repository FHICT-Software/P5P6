package jsfweek2opd2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phinux
 */
public class JSFW2OPD2 {

    public static void main(String[] args) {
        Properties defaultProperties = loadProperties();
        Set<String> lijstkeys = defaultProperties.stringPropertyNames();
        for (String s : lijstkeys) {
            System.out.println(s + " = " + defaultProperties.getProperty(s));
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
            Logger.getLogger(JSFW2OPD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSFW2OPD2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(JSFW2OPD2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return defaultProps;
    }
}
