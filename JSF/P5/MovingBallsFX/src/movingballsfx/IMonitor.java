/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package movingballsfx;

/**
 *
 * @author anne
 */
public interface IMonitor {
    
    
    public void enterReader() throws InterruptedException;
    public void exitReader();
    public void enterWriter() throws InterruptedException;
    public void exitWriter();
    
}
