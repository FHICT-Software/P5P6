package jsfweek3opdr9;

import java.io.IOException;
import java.util.ArrayList;
import timeutil.TimeStamp;

/**
 *
 * @author user
 */
public class JSFWeek3Opd9 {

    public static ArrayList<MyRunnable> commandos = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        TimeStamp ts = new TimeStamp();
        
        ts.setBegin();
        
        int i = 0;

        while (i < args.length) {
            MyRunnable r = new MyRunnable(args[i], args[i + 1]);
            commandos.add(r);
            i+=2;
        }
        
        for (MyRunnable r : commandos)
        {
            Thread thrd = new Thread(r);
            thrd.start();
        }
        
        ts.setEnd();
        
        Thread.sleep(10000);

        System.out.println("Tijd gebruikt voor uitvoeren: " + ts.toString());
        // TODO code application logic here
    }
}
