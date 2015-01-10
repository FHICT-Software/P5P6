package jsfweek3opd8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import timeutil.TimeStamp;

/**
 *
 * @author user
 */
public class JSFWeek3Opd8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length > 1) {
            System.err.println("Voer een enkel proces in.");
            System.exit(0);
        }

        TimeStamp timestamp = new TimeStamp();

        timestamp.setBegin();

        ProcessBuilder pb = new ProcessBuilder(args[0]);
        Process proc = pb.start();

        InputStream is = proc.getInputStream();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);

        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();

        timestamp.setEnd();

        System.out.println("Met de processbuilder: \n" + timestamp.toString() + ".");

        Thread.sleep(5000);

        TimeStamp timeStamp = new TimeStamp();

        timeStamp.setBegin();

        Process proc2 = Runtime.getRuntime().exec(args[0]);

        InputStream is2 = proc2.getInputStream();

        InputStreamReader isr2 = new InputStreamReader(is2);

        BufferedReader br2 = new BufferedReader(isr2);

        String line2;

        while ((line2 = br2.readLine()) != null) {
            System.out.println(line2);
        }
        br2.close();


        timeStamp.setEnd();

        System.out.println("Met de exec via runtime: \n" + timeStamp.toString()+".");

        /*
         * 
         * Originele output time ls commando: 
         * time java -jar ProcessBuilderProjectOutput.jar ls
         ProcessBuilderProjectOutput.jar
         README.TXT

         real	0m0.167s
         user	0m0.084s
         sys	0m0.000s

         */

        // TODO code application logic here
    }
}
