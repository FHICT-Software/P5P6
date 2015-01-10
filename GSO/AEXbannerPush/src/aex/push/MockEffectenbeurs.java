/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aex.push;

import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Koen
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectbeurs, RemotePublisher
{

    Timer time;
    private ArrayList<Fonds> koersen;
    private BasicPublisher publisher;
    double random;
    String[] koers = new String[1];

    public MockEffectenbeurs() throws RemoteException
    {
        time = new Timer();
        koersen = new ArrayList<Fonds>();
        time.schedule(new Time(), 0, 5000);
        koers[0] = "koersen";
        getKoersen();
        publisher = new BasicPublisher(koers);

    }

    @Override
    public ArrayList<Fonds> getKoersen() throws RemoteException
    {
        koersen.add(new Fonds("Rabobank", random()));
        koersen.add(new Fonds("Philips", random()));
        koersen.add(new Fonds("Sony", random()));
        koersen.add(new Fonds("Facebook", random()));
        return koersen;
    }
    

    public double random()
    {
        random = (int) (Math.random() * 1000) / 100.0;        
        return random;
        
    }

    @Override
    public void addListener(RemotePropertyListener rl, String string) throws RemoteException
    {
        publisher.addListener(rl, string);
    }

    @Override
    public void removeListener(RemotePropertyListener rl, String string) throws RemoteException
    {
        publisher.removeListener(rl, string);
    }

    private class Time extends TimerTask
    {
        @Override
        public void run()
        {
            String sb = "";
            String old = "";
            double oldD;
            double newD;
            double percentage;
            String percentageS = "";
            
            for(IFonds fonds : koersen)
            {
                oldD = fonds.getKoers();
                old = old + fonds.getNaam()+ " oude koers: " + fonds.getKoers() + " ";
                fonds.setKoers(random()); 
                newD = fonds.getKoers();
                percentage = Math.round((newD/oldD)*100);
                
                if(percentage < 100)
                {
                    percentageS = " -" + (100 - percentage);                    
                }
                else
                {
                    percentageS = " +" + String.valueOf(percentage);
                }
                
                sb = sb + fonds.getNaam()+ " koers: " + fonds.getKoers() + " " + percentageS +"%  ";               
                publisher.inform(this, "koersen", old, sb);
              
            }
        }
    }

}
