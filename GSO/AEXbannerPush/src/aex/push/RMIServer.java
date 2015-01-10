/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aex.push;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 *
 * @author Koen
 */
public class RMIServer
{
     // Set flag createRegistry when binding using registry
    // Reset flag createRegistry when binding using Naming
    private static boolean createRegistry = true;
    
    // Set port number
    private static int portNumber = 1099;
    
    // Set binding name for Mockeffectenbeurs
    private static String bindingName = "MockEffectenbeurs";
    
    // References to registry and Mockeffectenbeurs
    private Registry     registry = null;
    private MockEffectenbeurs mock = null;
    
    public RMIServer()
    {
         // Print port number for registry
        System.out.println("Server: Port number " + portNumber);
        
        // Create student administration
        try {
            mock = new MockEffectenbeurs();
            System.out.println("Server: MockEffectenbeurs created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create MockEffectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            mock = null;
        }
        
        // Bind student administration
        if (createRegistry) {
            // Create registry at port number
            registry = createRegistry();
        
            // Bind student administration using registry
            if (registry != null && mock != null) {
                bindMockUsingRegistry();
                System.out.println("Server: MockEffectenbeurs bound to " + bindingName);
            }
            else {
                System.out.println("Server: MockEffectenbeurs not bound");
            }
        }
        else {
            // Bind student adiministration using Naming
            if (mock != null) {
                bindMockUsingNaming();
                System.out.println("Server: MockEffectenbeurs bound to " + bindingName);
            }
            else {
                System.out.println("Server: MockEffectenbeurs not bound");
            }
        }
    }    
    
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
    
    // Bind student administration using registry
    private void bindMockUsingRegistry() {
        try {
            registry.rebind(bindingName,mock);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind MockEffectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }
        
    // Bind student administration using Naming
    private void bindMockUsingNaming() {
        try {
            LocateRegistry.createRegistry(portNumber);
            Naming.rebind(bindingName,mock);
        } catch (MalformedURLException ex) {
            System.out.println("Server: Cannot bind MockEffectenbeurs");
            System.out.println("Server: MalformedURLException: " + ex.getMessage());
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind MockEffectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }

   
    
     public static void main(String[] args)
    {
       // Welcome message
        if (createRegistry) {
            System.out.println("SERVER USING CREATE REGISTRY");
        }
        else {
            System.out.println("SERVER USING NAMING");
        }
        
        // Create server
        RMIServer server = new RMIServer();
    }
    
}
