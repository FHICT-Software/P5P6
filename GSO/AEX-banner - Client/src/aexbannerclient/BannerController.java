//<editor-fold defaultstate="collapsed" desc="Jibberish">
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerclient;

import interfaces.IEffectenbeurs;
import interfaces.IFonds;
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
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class BannerController {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final AEXBannerGUI aexBannerGui;
    private Timer timer;
    private static boolean locateRegistry = true;
    private static String bindingName = "Effectenbeurs";
    private Registry registry = null;
    private IEffectenbeurs effectenbeurs = null;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor(opAddress, portNumber)">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Registry / Naming">
    //<editor-fold defaultstate="collapsed" desc="localeRegistry(ipAdress, portNumber)">
    private Registry locateRegistry(String ipAddress, int portNumber) {
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
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="printContentRegistry()">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="bindEffectenbeursUsingRegistry()">
    private IEffectenbeurs bindEffectenbeursUsingRegistry() {
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="bindEffectenbeursUsingNaming(ipAddress, portNumber)">
    private IEffectenbeurs bindEffectenbeursUsingNaming(String ipAddress, int portNumber) {
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
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="static main">
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
    //</editor-fold>
}
