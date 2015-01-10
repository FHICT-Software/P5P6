/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfweek3opdr9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class MyRunnable implements Runnable {
    
    public String command;
    public String arg;
    
    public MyRunnable(String c, String a)
    {
        this.command=c;
        this.arg = a;
    }
    
    public void run()
    {
        Boolean output = false;
        if (!arg.isEmpty())
        {
            output = true;
            try {
                ProcessBuilder pb = new ProcessBuilder(command,arg);
                Process proc = pb.start();
            InputStream is = proc.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            } catch (IOException ex) {
                Logger.getLogger(MyRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try {
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.start();
            } catch (IOException ex) {
                Logger.getLogger(MyRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
