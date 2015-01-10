/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Nico Kuijpers
 */
public class RMIServer {

    // Set flag createRegistry when binding using registry
    // Reset flag createRegistry when binding using Naming
    private static boolean createRegistry = true;
    
    // Set port number
    private static int portNumber = 1099;
    
    // Set binding name for Effectenbeurs
    private static String bindingName = "Effectenbeurs";
    
    // References to registry and Effectenbeurs
    private Registry     registry = null;
    private MockEffectenbeurs effectenbeurs = null;
    
    // Constructor
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
  
    // Create registry
    private Registry createRegistry() {
        
        // Create registry at port number
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
    
    // Bind Effectenbeurs using registry
    private void bindEffectenbeursUsingRegistry() {
        try {
            registry.rebind(bindingName,effectenbeurs);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind Effectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
        
    // Bind Effectenbeurs using Naming
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
    
    // Print IP addresses and network interfaces
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
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
}
