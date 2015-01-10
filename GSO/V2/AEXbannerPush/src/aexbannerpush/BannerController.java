/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aexbannerpush;

import aexbannerpush.IEffectbeurs;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;



/**
 *
 * @author Koen
 */
public class BannerController extends UnicastRemoteObject implements RemotePropertyListener
{  
    @FXML Label tfAEX;
    @FXML Label tfAEXoud;
    
    public AEXBanner aexbanner; 
    Color groen = Color.GREEN;
    Color rood = Color.RED;
    
    
    
    // Set flag locateRegistry when binding using registry
    // Reset flag locateRegistry when binding using Naming
    private static boolean locateRegistry = true;
    
    // Set binding name for Mockeffectenbeurs
    private static String bindingName = "koersen";
    
    // References to registry and Mockeffectenbeurs
    private Registry      registry = null;
     
    public BannerController()throws RemoteException
    {
        try
        {
            RemotePublisher publisher = (RemotePublisher)Naming.lookup("rmi://localhost:1099/MockEffectenbeurs");
            publisher.addListener(this, bindingName);
        } catch (NotBoundException ex)
        {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex)
        {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent pce) throws RemoteException
    {
        
       Platform.runLater(new Runnable()
       {
           @Override
           public void run()
           {   
               Reflection r = new Reflection();
               r.setFraction(0.10f);
               tfAEX.setText(pce.getNewValue().toString());
               tfAEX.setEffect(r);
               tfAEXoud.setText(pce.getOldValue().toString());
           }
       });
    }
    
    
}
