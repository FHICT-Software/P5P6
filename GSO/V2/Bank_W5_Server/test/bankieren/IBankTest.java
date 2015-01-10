/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bankieren;

import bankieren.Money;
import bankieren.Bank;
import bankieren.IBank;
import fontys.util.NumberDoesntExistException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import static junit.framework.Assert.*;
import org.junit.*;


/**
 *
 * @author Koen / Nils
 */
public class IBankTest
{
    private IBank b1, b2;
    private int r1, r2, r3;

    public IBankTest() {
    }
    
    @Before
    public void setUp() throws RemoteException, NotBoundException, MalformedURLException {
        b1 = new Bank("Rabo");
        b2 = new Bank("ABN");
        r1 = b1.openRekening("Koen", "Oirschot");
        r2 = b1.openRekening("Nils", "Horst");
        r3 = b1.openRekening("Frankie", "Zeeland");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testOpenRekening() throws RemoteException {
               
        // Naam
        int openRekening = b1.openRekening("", "Oirschot");
        Assert.assertEquals("Naam is leeg",-1, openRekening);
        // Woonplaats
        openRekening = b1.openRekening("Frankie", "");
        Assert.assertEquals("Woonplaats is leeg",-1, openRekening);
    }

    @Test(expected = RuntimeException.class)
    public void testMaakOverBestemmingGelijk() throws NumberDoesntExistException, RemoteException {
        // Omvermaken Bedrag - Eigen Rekening
        b1.maakOver(r1, r1, new Money(1000, Money.EURO));
    }

    @Test(expected = RuntimeException.class)
    public void testMaakOverBedragPositief() throws NumberDoesntExistException, RemoteException {
        // Overmaken Bedrag
        b1.maakOver(r1, r2, new Money(-1, Money.EURO));
    }
    
    @Test(expected = RuntimeException.class)
    public void testMaakOverBedragNul() throws NumberDoesntExistException, RemoteException {
        // Overmaken Bedrag nul
        Assert.assertFalse(b1.maakOver(r1, r2, new Money(0, Money.EURO)));
        
    }
    
    //maak test nul

    @Test
    public void testMaakOver() throws NumberDoesntExistException, RemoteException {
        Money m1 = b1.getRekening(r1).getSaldo();
        Money m2 = b1.getRekening(r2).getSaldo();
        Money mPositief = new Money(1000, Money.EURO);
        Money mNegatief = new Money(-1000, Money.EURO);
        Boolean bool = b1.maakOver(r1, r2, mPositief);
        assertEquals("Geld is niet van rekening 1 afgeschreven", b1.getRekening(r1).getSaldo(), Money.sum(m1, mNegatief));
        assertEquals("Geld is niet naar rekening 2 overgemaakt", b1.getRekening(r2).getSaldo(), Money.sum(m2, mPositief));
        assertTrue("Geld is overgemaakt naar rekening", bool);
    }

    @Test
    public void testGetNaamBank() throws RemoteException {
        // Bank Naam
        assertEquals("De naam van de bank moet Rabo zijn!", b1.getName(), "Rabo");
    }

    @Test
    public void testGetRekening() throws RemoteException {
        // Rekeningen
        assertNotNull("Rekeningnummer moet geen null zijn", b1.getRekening(r1));
        assertNull("Rekeningnummer moet null zijn", b1.getRekening(0));
        assertEquals("Rekening is niet geinitializeerd", b1.getRekening(r1).getNr(), r1);
    }
}
