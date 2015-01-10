//<editor-fold defaultstate="collapsed" desc="Jibberish">
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerserver;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import javafx.application.Application;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author Nico Kuijpers
 */
public class RMIServer extends Application{

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private static boolean createRegistry = true;
    private static int portNumber = 1099;
    private static String bindingName = "Effectenbeurs";
    private Registry     registry = null;
    private MockEffectenbeurs effectenbeurs = null;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    public RMIServer() {
        
        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);
        
        // Create Effectenbeurs
        try {
            effectenbeurs = new MockEffectenbeurs();
            System.out.println("Server: Effectenbeurs created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create Effectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            effectenbeurs = null;
        }
        
        // Bind Effectenbeurs
        if (createRegistry) {
            // Create registry at port numberStudentAdministration
            registry = createRegistry();
        
            // Bind Effectenbeurs using registry
            if (registry != null && effectenbeurs != null) {
                bindEffectenbeursUsingRegistry();
                System.out.println("Server: Effectenbeurs bound to " + bindingName);
            }
            else {
                System.out.println("Server: Effectenbeurs not bound");
            }
        }
        else {
            // Bind Effectenbeurs using Naming
            if (effectenbeurs != null) {
                bindEffectenbeursUsingNaming();
                System.out.println("Server: Effectenbeurs bound to " + bindingName);
            }
            else {
                System.out.println("Server: Effectenbeurs not bound");
            }
        }
    }
    //</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="createRegistry()">
    private Registry createRegistry() {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }
        return registry;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="bindEffectenbeursUsingRegistry()">
    private void bindEffectenbeursUsingRegistry() {
        try {
            registry.rebind(bindingName,effectenbeurs);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind Effectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="bindEffectenbeursUsingNaming()">
    private void bindEffectenbeursUsingNaming() {
        try {
            LocateRegistry.createRegistry(portNumber);
            Naming.rebind(bindingName,effectenbeurs);
        } catch (MalformedURLException ex) {
            System.out.println("Server: Cannot bind Effectenbeurs");
            System.out.println("Server: MalformedURLException: " + ex.getMessage());
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind Effectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="printIPAddresses()">
    private static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (int i = 0; i < allMyIps.length; i++) {
                    System.out.println("    " + allMyIps[i]);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        try {
            System.out.println("Server: Full list of network interfaces:");
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    System.out.println("        " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException ex) {
            System.out.println("Server: Cannot retrieve network interface list");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="static main">
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="start(stage)">
    @Override
    public void start(Stage stage) throws Exception {
        // Welcome message
        if (createRegistry) {
            System.out.println("SERVER USING CREATE REGISTRY");
        }
        else {
            System.out.println("SERVER USING NAMING");
        }
        
        // Print IP addresses and network interfaces
        printIPAddresses();
        
        // Create server
        RMIServer server = new RMIServer();
    }
    //</editor-fold>
}
