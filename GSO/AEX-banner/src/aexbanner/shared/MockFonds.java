/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aexbanner.shared;

import java.io.Serializable;

/**
 *
 * @author anne
 */
public class MockFonds implements IFonds, Serializable {
    
    private final String naam;
    private double koers;
    
    public MockFonds(String naam, double koers) {
        this.naam = naam;
        this.koers = koers;
    }

    @Override
    public String getNaam() {
        return this.naam;
    }

    @Override
    public Double getKoers() {
        return this.koers;
    }

    @Override
    public void setKoers(double koers) {
        this.koers = koers;
    }
    
}
