/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aexbanner.shared;

import aexbanner.shared.IFonds;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author anne
 */
public interface IEffectenbeurs extends Remote{
    
    /**
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public IFonds[] getKoersen() throws RemoteException;
    
}
