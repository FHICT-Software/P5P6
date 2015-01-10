package gui;

import calculate.KochFractal;
import calculate.KochObserver;

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class Main {
    
    //<editor-fold defaultstate="collapsed" desc="Declarations">

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int LEVEL = 2;
        KochFractal kochFractal = new KochFractal();
        KochObserver kochObserver = new KochObserver();
        kochFractal.addObserver(kochObserver);
        kochFractal.setLevel(LEVEL);
        kochObserver.setLevel(LEVEL);
        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }

}
