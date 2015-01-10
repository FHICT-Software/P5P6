/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package internettoegang;

import internettoegang.Bankiersessie;
import internettoegang.IBankiersessie;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import bankieren.Bank;
import bankieren.IBank;
import bankieren.Money;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Koen
 */
public class IBankiersessieTest {

    private IBank b1;
    private int r1, r2;
    private IBankiersessie bs;

    public IBankiersessieTest() throws RemoteException, NotBoundException, MalformedURLException {
        // Bank
        b1 = new Bank("Rabo");
        // Rekeningen
        r1 = b1.openRekening("Koen", "Oirschot");
        r2 = b1.openRekening("Nils", "Horst");
        // BankSessie
        bs = new Bankiersessie(r1, b1);
    }

    @Test
    public void isGeldigTest() throws RemoteException, NumberDoesntExistException, InvalidSessionException, InterruptedException {
        // BankSessie
        bs = new Bankiersessie(r1, b1);
        bs.getRekening();
        // BankSessie Geldig
        boolean bool = bs.isGeldig();
        assertTrue("Geldigheidsduur is nog niet verlopen", bool);
    }
    
    //sleep test

    @Test
    public void maakOverTest() throws RemoteException, NumberDoesntExistException, InvalidSessionException {
        // Bedragen
        Money s1 = b1.getRekening(r1).getSaldo();
        Money s2 = b1.getRekening(r2).getSaldo();
        Money mPositief = new Money(1000, Money.EURO);
        Money mNegatief = new Money(-1000, Money.EURO);
        // BankSessie + Overmaken
        IBankiersessie bs = new Bankiersessie(r1, b1);
        boolean bool = bs.maakOver(r2, mPositief);
        // Assert Check
        assertEquals("Geld is niet van rekening 1 afgeschreven", b1.getRekening(r1).getSaldo(), Money.sum(s1, mNegatief));
        assertEquals("Geld is niet naar rekening 2 overgemaakt", b1.getRekening(r2).getSaldo(), Money.sum(s2, mPositief));
        assertTrue("Geld is overgemaakt naar rekening", bool);
    }

    @Test(expected = RuntimeException.class)
    public void maakOverBestemming() throws RemoteException, NumberDoesntExistException, InvalidSessionException {
        // Rekening Nummer != BankSessie
        bs.maakOver(r1, new Money(1, Money.EURO));
        // Fail
        //fail("Rekening mag niet hetzelfde zijn!");
    }

    @Test(expected = RuntimeException.class)
    public void testMaakOverBedragPositief() throws NumberDoesntExistException, InvalidSessionException, RemoteException {
        // Overmaken Bedrag > 0
        bs.maakOver(r2, new Money(-1, Money.EURO));
        // Fail
       
    }

    @Test(expected = NumberDoesntExistException.class)
    public void testMaakOver() throws NumberDoesntExistException, InvalidSessionException, RemoteException {
        // Overmaken - Rekening niet bekend
        bs.maakOver(10000, new Money(1, Money.EURO));
        // Fail
        
    }
    
    //logout, get rekening
}