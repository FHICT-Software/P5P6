//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week4_zondergui;

import callculate.Edge;
import callculate.KochFractal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochFractal_Week4_ZonderGUI. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/26
 */
public class KochFractal_Week4_ZonderGUI implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final Scanner input;
    private final KochFractal koch;
    private File file;
    private List<Edge> edges;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor/Main">
    public KochFractal_Week4_ZonderGUI() {
        input = new Scanner(System.in);
        this.koch = new KochFractal();
        koch.addObserver(this);
    }

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KochFractal_Week4_ZonderGUI console = new KochFractal_Week4_ZonderGUI();
        String file;
        if (args.length < 1) {
            file = "/home/jeroen/Edge";
        } else {
            file = args[0];
        }
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
        console.start(file);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="start(fileDir)">
    private void start(String fileDir) {
        file = new File(fileDir);
        int choice = chooseLevel();
        edges = new ArrayList<>();
        koch.setLevel(choice);
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        try {
            writeEdges();
        } catch (IOException ex) {
            Logger.getLogger(KochFractal_Week4_ZonderGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="writeEdges()">
    public void writeEdges() throws FileNotFoundException, IOException {
        RandomAccessFile memoryMappedFile = new RandomAccessFile(file, "rw");
        int size = 4 + 4 + (koch.getNrOfEdges() * 3 * 8) + (koch.getNrOfEdges() * 4 * 8);
        FileChannel fileChannel = memoryMappedFile.getChannel();
        MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size);
        FileLock fileLock = null;

        int firstLock = 0;
        int lastLock = 4 + 4;
        fileLock = fileChannel.lock(firstLock, lastLock, false);
        out.position(0);
        out.putInt(koch.getLevel());
        out.putInt(koch.getNrOfEdges());
        fileLock.release();

        firstLock = lastLock;
        lastLock = (3 * 8) + (4 * 8);
        for (Edge edge : edges) {
            firstLock += lastLock;
            fileLock = fileChannel.lock(firstLock, lastLock, false);
            out.putDouble(edge.bri);
            out.putDouble(edge.hue);
            out.putDouble(edge.sat);
            out.putDouble(edge.X1);
            out.putDouble(edge.Y1);
            out.putDouble(edge.X2);
            out.putDouble(edge.Y2);
            fileLock.release();
        }
        System.out.println("Writing to memory mapped file completed");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="chooseLevel()">
    int chooseLevel() {
        System.out.println();
        int maxNr = 10;
        int nr;
        nr = readInt("Select Level (1 - " + maxNr + ")");
        while (nr < 1 || nr > maxNr) {
            nr = readInt("Select Level (1 - " + maxNr + ")");
        }
        input.nextLine();
        return nr;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="readInt(string)">
    int readInt(String string) {
        int returner = -1;
        boolean inputCorrect = false;
        while (!inputCorrect) {
            try {
                System.out.print(string + " ");
                returner = input.nextInt();
                inputCorrect = true;
            } catch (InputMismatchException exc) {
                System.out.println("Not a correct input.");
                input.nextLine();
            }

        }
        return returner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update(o, arg)">
    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        edges.add(e);
    }
    //</editor-fold>
    //</editor-fold>
}
