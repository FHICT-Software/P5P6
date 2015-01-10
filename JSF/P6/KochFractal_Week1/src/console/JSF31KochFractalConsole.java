/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import calculate.KochFractal;

/**
 *
 * @author phinux
 */
public class JSF31KochFractalConsole {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KochFractal kochFractal = new KochFractal();
        //KochObserver kochObserver = new KochObserver();
        //kochFractal.addObserver(kochObserver);
        kochFractal.setLevel(1);
        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }

}
