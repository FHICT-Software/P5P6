//<editor-fold defaultstate="collapsed" desc="Jibberish">
package calculate;

import formatting.Console;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
//</editor-fold>

/**
 * In this class you can find all properties and operations for KochObserver.
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/17
 */
public class KochObserver implements Observer {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    ArrayList<String[]> table;
    //</editor-fold>
    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor()">
    /**
     * This is the constructor for KochObserver.
     */
    public KochObserver() {
        table = new ArrayList<>();
    }
  //</editor-fold>

    //</editor-fold>
    @Override
    public void update(Observable o, Object arg) {
        Edge edge = (Edge) arg;
        String[] row01 = new String[4];
        row01[0] = "Edge " + (table.size() / 3) + "|";
        row01[1] = "Point_1:";
        row01[2] = "(" + edge.X1;
        row01[3] = "; " + edge.Y1 + ")";
        table.add(row01);
        String[] row02 = new String[4];
        row02[0] = "      ";
        while (row02[0].length() < row01[0].length()-1){
            row02[0] += " ";
        }
        row02[0] += "|";
        row02[1] = "Point_2:";
        row02[2] = "(" + edge.X1;
        row02[3] = "; " + edge.Y1 + ")";
        table.add(row02);
        table.add(null);
        if ((table.size() % 9) == 0) {
            Console.printTable(table);
        }
    }
}
