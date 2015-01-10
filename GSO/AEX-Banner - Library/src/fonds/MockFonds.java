//<editor-fold defaultstate="collapsed" desc="Jibberish">
package fonds;

import interfaces.IFonds;
import java.io.Serializable;
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class MockFonds implements IFonds, Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private final String naam;
    private double koers;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //<editor-fold defaultstate="collapsed" desc="Naam">
    @Override
    public String getNaam() {
        return this.naam;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Koers">
    @Override
    public Double getKoers() {
        return this.koers;
    }
    
    @Override
    public void setKoers(double koers) {
        this.koers = koers;
    }
    //</editor-fold>
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor(naam, koers)">
    public MockFonds(String naam, double koers) {
        this.naam = naam;
        this.koers = koers;
    }
    //</editor-fold>
}
