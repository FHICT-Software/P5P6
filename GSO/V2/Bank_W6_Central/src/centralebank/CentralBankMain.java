//<editor-fold defaultstate="collapsed" desc="Jibberish">
package centralebank;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
//</editor-fold>

/**
 * In this class you can find all properties and operations for CentralBankMain. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/07/04
 */
public class CentralBankMain extends Thread{
    
    //<editor-fold defaultstate="collapsed" desc="static main">
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
        // TODO code application logic here
        CentralBank cb = new CentralBank();
        Registry registry = LocateRegistry.createRegistry(1100);
        registry.bind("localhost", cb);
        System.out.println("Centrale server opgestart");
    }
    //</editor-fold>
}
