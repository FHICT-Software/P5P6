/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.client;

import aexbanner.shared.IEffectenbeurs;
import aexbanner.shared.IFonds;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author anne
 */
public class BannerController {

    private final AEXBannerGUI aexBannerGui;
    private Timer timer;

    // Set flag locateRegistry when binding using registry
    // Reset flag locateRegistry when binding using Naming
    private static boolean locateRegistry = true;

    // Set binding name for Effectenbeurs
    private static String bindingName = "Effectenbeurs";

    // References to registry and Effectenbeurs
    private Registry registry = null;
    private IEffectenbeurs effectenbeurs = null;

    // Constructor
    /**
     *
     * @param ipAddress
     * @param portNumber
     */
    public BannerController(String ipAddress, int portNumber) {

        aexBannerGui = new AEXBannerGUI();

        timer = new Timer();

        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Bind EffectenbeursFondsen
        if (locateRegistry) {
            // Locate registry at IP address and port number
            registry = locateRegistry(ipAddress, portNumber);

            // Print result locating registry
            if (registry != null) {
                System.out.println("Client: Registry located");
            } else {
                System.out.println("Client: Cannot locate registry");
                System.out.println("Client: Registry is null pointer");
            }

            // Print contents of registry
            if (registry != null) {
                printContentsRegistry();
            }

            // Bind Efecctenbeurs using registry
            if (registry != null) {
                effectenbeurs = bindEffectenbeursUsingRegistry();
            }
        } else {
            // Bind fonds using Naming
            effectenbeurs = bindEffectenbeursUsingNaming(ipAddress, portNumber);
        }

        // Print result binding Effectenbeurs
        if (effectenbeurs != null) {
            System.out.println("Client: Effectenbeurs bound");
        } else {
            System.out.println("Client: Effectenbeurs is null pointer");
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            IFonds[] fondsen = effectenbeurs.getKoersen();
                            aexBannerGui.setKoersen(fondsen);
                        } catch (RemoteException ex) {
                            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }, 0, 20);

        aexBannerGui.start(new Stage());
    }

    // Locate registry
    private Registry locateRegistry(String ipAddress, int portNumber) {

        // Locate registry at IP address and port number
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }
        return registry;
    }

    // Print contents of registry
    private void printContentsRegistry() {
        try {
            String[] listOfNames = registry.list();
            System.out.println("Client: list of names bound in registry:");
            if (listOfNames.length != 0) {
                for (String s : registry.list()) {
                    System.out.println(s);
                }
            } else {
                System.out.println("Client: list of names bound in registry is empty");
            }
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot show list of names bound in registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
    }

    // Bind Effectenbeurs using registry
    private IEffectenbeurs bindEffectenbeursUsingRegistry() {

        // Bind Effectenbeurs
        IEffectenbeurs effectenbeurs1 = null;
        try {
            Object object = registry.lookup(bindingName);
            effectenbeurs1 = (IEffectenbeurs) registry.lookup(bindingName);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot bind Effectenbeurs");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            effectenbeurs1 = null;
        } catch (NotBoundException ex) {
            System.out.println("Client: Cannot bind Effectenbeurs");
            System.out.println("Client: NotBoundException: " + ex.getMessage());
            effectenbeurs1 = null;
        }
        return effectenbeurs1;
    }

    // Bind Effectenbeurs using Naming
    private IEffectenbeurs bindEffectenbeursUsingNaming(String ipAddress, int portNumber) {

        // Bind Effectenbeurs
        IEffectenbeurs Effectenbeurs = null;
        try {
            Effectenbeurs = (IEffectenbeurs) Naming.lookup("rmi://" + ipAddress + ":" + portNumber + "/" + bindingName);
        } catch (MalformedURLException ex) {
            System.out.println("Client: Cannot bind Effectenbeurs");
            System.out.println("Client: MalformedURLException: " + ex.getMessage());
            Effectenbeurs = null;
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot bind Effectenbeurs");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            Effectenbeurs = null;
        } catch (NotBoundException ex) {
            System.out.println("Client: Cannot bind Effectenbeurs");
            System.out.println("Client: NotBoundException: " + ex.getMessage());
            Effectenbeurs = null;
        }
        return Effectenbeurs;
    }

    // Main method
    public static void main(String[] args) {

        // Welcome message
        if (locateRegistry) {
            System.out.println("CLIENT USING LOCATE REGISTRY");
        } else {
            System.out.println("CLIENT USING NAMING");
        }

        // Get ip address of server
        Scanner input = new Scanner(System.in);
        System.out.print("Client: Enter IP address of server: ");
        String ipAddress = input.nextLine();

        // Get port number
        System.out.print("Client: Enter port number: ");
        int portNumber = input.nextInt();

        // Create client
        BannerController client = new BannerController(ipAddress, portNumber);
    }

}
