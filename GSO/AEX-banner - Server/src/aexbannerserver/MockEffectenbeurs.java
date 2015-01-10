//<editor-fold defaultstate="collapsed" desc="Jibberish">
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aexbannerserver;

import fonds.MockFonds;
import interfaces.IEffectenbeurs;
import interfaces.IFonds;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs{

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    IFonds[] fondsen;
    Random r = new Random();
    Timer myRandomTimer;
    private static final int TIMERTICK = 1000;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     *
     * @throws java.rmi.RemoteException
     */
    public MockEffectenbeurs() throws RemoteException{

        fondsen = new IFonds[5];

        fondsen[0] = new MockFonds("AEX", 10);
        fondsen[1] = new MockFonds("Bel20", 15);
        fondsen[2] = new MockFonds("DAX", 25);
        fondsen[3] = new MockFonds("CAC-50", 30);
        fondsen[4] = new MockFonds("Nasdaq", 50);


        myRandomTimer = new Timer();
        myRandomTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Timer code
                for (IFonds fonds : fondsen) {
                    MockFonds curFonds = (MockFonds) fonds;
                    Double koers = (r.nextDouble()*150) - (r.nextDouble()*25) ;
                    curFonds.setKoers(koers);
                }
            }
        }, TIMERTICK, TIMERTICK);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getKoersen()">
    @Override
    public IFonds[] getKoersen() throws RemoteException{
        return fondsen;
    }
    //</editor-fold>
}
