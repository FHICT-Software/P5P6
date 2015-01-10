//<editor-fold defaultstate="collapsed" desc="Jibberish">
package kochFractal_week2_zondergui;

import callculate.Edge;
import callculate.KochFractal;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 * In this class you can find all properties and operations for TextBuffer.
 * //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/05/26
 */
public class TextBuffer implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final Scanner input;
    private final KochFractal koch;
    private File file;
    private FileWriter fw;
    private BufferedWriter out;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor/Main">
    public TextBuffer() {
        input = new Scanner(System.in);
        this.koch = new KochFractal();
        koch.addObserver(this);
    }

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TextBuffer console = new TextBuffer();
        String file;
        if (args.length < 1) {
            file = "/tmp/Edge";
        } else {
            file = args[0];
        }
        console.start(file);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operations">
    private void start(String fileDir) {
        try {
            file = controle(fileDir);
        } catch (IOException ex) {
            Logger.getLogger(TextBuffer.class.getName()).log(Level.SEVERE, null, ex);
        }
        int choice = kiesLevel();
        koch.setLevel(choice);
        openFileStream(choice);
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(TextBuffer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    File controle(String bestandslocatie) throws IOException {
        File f = new File(bestandslocatie);
        if (!f.exists() && f.isDirectory()) {
            f = new File(bestandslocatie + "/Edges");
            f.createNewFile();
        } else if (!f.exists() && !f.isDirectory()) {
            f.createNewFile();
        }
        return f;
    }

    int kiesLevel() {
        System.out.println();
        int maxNr = 10;
        int nr;
        nr = readInt("maak een keuze uit 1 t/m " + maxNr);
        while (nr < 1 || nr > maxNr) {
            nr = readInt("maak een keuze uit 1 t/m " + maxNr);
        }
        input.nextLine();
        return nr;
    }

    int readInt(String helptekst) {
        boolean invoerOk = false;
        int invoer = -1;
        while (!invoerOk) {
            try {
                System.out.print(helptekst + " ");
                invoer = input.nextInt();
                invoerOk = true;
            } catch (InputMismatchException exc) {
                System.out.println("Let op, invoer moet een getal zijn!");
                input.nextLine();
            }
        }
        return invoer;
    }

    private void openFileStream(int level) {
        try {
            fw = new FileWriter(file);
            out = new BufferedWriter(fw);
            out.write(level + "\n");
        } catch (IOException ex) {
            Logger.getLogger(TextBuffer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge) arg;
        try {
            out.write(e.toString());
            out.write("\n");
        } catch (IOException ex) {
            Logger.getLogger(TextBuffer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
}
