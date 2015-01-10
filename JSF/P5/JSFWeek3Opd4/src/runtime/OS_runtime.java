/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Runtime.getRuntime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeroen
 */
public class OS_runtime {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Opdracht 3
        printStats();

        //Opdracht 4
        String s;
        for (int i = 0; i < 100000; i++) {
            s = "Hello" + i;
        }
        printStats();

        //Opdracht 5
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("gnome-calculator");
            Process process = processBuilder.start();
            Thread.sleep(2000);
            process.destroy();
            Thread.sleep(500);
            process = Runtime.getRuntime().exec("gnome-calculator");
            Thread.sleep(2000);
            Runtime.getRuntime().exit(0);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(OS_runtime.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Opdracht 6
        try {
            Process process = Runtime.getRuntime().exec("ls");
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(OS_runtime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printStats() {
        Runtime runtimeObject;
        runtimeObject = getRuntime();
        List<String[]> output = new ArrayList<>();

        // Opdracht A
        int availableProcessors = runtimeObject.availableProcessors();
        String[] opdrachtA = new String[3];
        opdrachtA[0] = "Opdracht A: Aantal beschikbare Processoren:";
        opdrachtA[1] = availableProcessors + " processoren";
        opdrachtA[2] = "";
        output.add(opdrachtA);

        // Opdracht B
        int totalMemory = (int) runtimeObject.totalMemory();
        String[] opdrachtB = new String[3];
        opdrachtB[0] = "Opdracht B: Total Memory:";
        opdrachtB[1] = readableNumber(totalMemory, "b");
        opdrachtB[2] = "(" + totalMemory + " b)";
        output.add(opdrachtB);

        // Opdracht C
        int maxMemory = (int) runtimeObject.maxMemory();
        String[] opdrachtC = new String[3];
        opdrachtC[0] = "Opdracht C: Max Memory:";
        opdrachtC[1] = readableNumber(maxMemory, "b");
        opdrachtC[2] = "(" + maxMemory + " b)";
        output.add(opdrachtC);

        // Opdracht D
        int freeMemory = (int) runtimeObject.freeMemory();
        String[] opdrachtD = new String[3];
        opdrachtD[0] = "Opdracht D: Free Memory:";
        opdrachtD[1] = readableNumber(freeMemory, "b");
        opdrachtD[2] = "(" + freeMemory + " b)";
        output.add(opdrachtD);

        // Opdracht E
        String[] opdrachtE = new String[3];
        opdrachtE[0] = "Opdracht E: Max-Free Memory:";
        opdrachtE[1] = readableNumber(maxMemory - freeMemory, "b ");
        opdrachtE[2] = "(" + (maxMemory - freeMemory) + " b)";
        output.add(opdrachtE);

        // Write Output
        List<String> formattedOutput = formatTable(output);
        for (String s : formattedOutput) {
            System.out.println(s);
        }
    }

    private static List<String> formatTable(List<String[]> table) {
        List<String> returner = new ArrayList<>();
        int[] largestColumnLength = new int[table.get(0).length];
        for (int row = 0; row < table.size(); row++) {
            for (int column = 0; column < table.get(row).length; column++) {
                if (table.get(row)[column].length() > largestColumnLength[column]) {
                    largestColumnLength[column] = table.get(row)[column].length();
                }
            }
        }
        for (String[] row : table) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int column = 0; column < table.get(0).length; column++) {
                while (row[column].length() < largestColumnLength[column]) {
                    row[column] += " ";
                }
                rowBuilder.append(row[column]);
            }
            returner.add(rowBuilder.toString());
        }
        return returner;
    }

    private static String readableNumber(int number) {
        String suffix = "";
        double converted = number;
        while (converted > 1000) {
            converted /= 1000;
            suffix = Formatting.nextShortMetricPrefix(suffix);
        }
        double tmpRounding = converted * 100;
        tmpRounding = Math.round(tmpRounding);
        converted = tmpRounding / 100;
        return converted + suffix;
    }

    private static String readableNumber(int number, String unit) {
        return readableNumber(number) + unit;
    }

}
