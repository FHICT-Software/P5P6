package gui;

import calculate.KochFractal;
import calculate.KochObserver;

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class Console {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KochFractal kochFractal = new KochFractal();
        KochObserver kochObserver = new KochObserver();
        kochFractal.addObserver(kochObserver);
        kochFractal.setLevel(1);
        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }

}
