/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aexbanner.shared;

/**
 *
 * @author anne
 */
public interface IFonds {
    
    /**
     *
     * @return 
     */
    public String getNaam();
    
    /**
     *
     * @return
     */
    public Double getKoers();
    
    /**
     *
     * @param koers
     */
    public void setKoers(double koers);
    
}
