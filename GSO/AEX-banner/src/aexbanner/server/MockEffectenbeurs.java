/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aexbanner.server;

import aexbanner.shared.IEffectenbeurs;
import aexbanner.shared.IFonds;
import aexbanner.shared.MockFonds;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author anne
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs{

    IFonds[] fondsen;
    Random r = new Random();
    Timer myRandomTimer;
    private static final int TIMERTICK = 1000;

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

    @Override
    public IFonds[] getKoersen() throws RemoteException{
        return fondsen;
    }
    
}
