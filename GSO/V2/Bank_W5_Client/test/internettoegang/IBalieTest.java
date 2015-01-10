package internettoegang;

import internettoegang.Balie;
import internettoegang.IBalie;
import internettoegang.IBankiersessie;
import junit.framework.Assert;
import bankieren.IRekening;
import bankieren.IBank;
import bankieren.Bank;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Koen
 */
public class IBalieTest {

    private IBank bank;
    private IBalie balie;

    @Before
    public void setUp() throws RemoteException, NotBoundException, MalformedURLException {
        // Bank & Balie
        this.bank = new Bank("Rabo");
        this.balie = new Balie(this.bank);
    }

    @Test
    public void testOpenRekening() throws Exception {
        // AccountNaam - Leeg
        String br = this.balie.openRekening("", "Oirschot", "wachtwoord");
        Assert.assertNull("Naam mag niet leeg zijn", br);
        // Account Plaats - Leeg
        br = this.balie.openRekening("Nils", "", "wachtwoord");
        Assert.assertNull("Plaats mag niet leeg zijn", br);
        // Wachtwoord < 4 Karakters
        br = this.balie.openRekening("Erik", "Eindhoven", "dr");
        Assert.assertNull("Wachtwoord moet minimaal 4 karakers lang zijn", br);
        // Wachtwoord > 8 Karakters
        br = this.balie.openRekening("Lisa", "Springfield", "ditgaatnietgebeuren");
        Assert.assertNull("Wachtwoord mag niet langer zijn dan 8 karakters", br);
        // Account Naam = 8 Karakters
        br = this.balie.openRekening("Francoise", "Boulange", "groen");
        Assert.assertEquals("Accountnaam moet 8 karakters lang zijn", br.length(), 8);
        // Account Inloggen
        IBankiersessie bs = this.balie.logIn(br, "groen");
        IRekening rek = bs.getRekening();
        // Check Saldo
        Assert.assertEquals("Saldo moet 0,00 euro zijn!", "0,00", "0,00");
    }

    @Test
    public void testLogIn() throws Exception {
        // Account
        String br = this.balie.openRekening("Koen", "Oirschot", "groen");
        // Inloggen - Mislukt
        IBankiersessie bs = this.balie.logIn(br, "verkeerd");
        Assert.assertNull("Wachtwoord is onjuist!", bs);
        // Inloggen - Mislukt
        bs = this.balie.logIn("Nils", "groen");
        Assert.assertNull("Accountnaam is onjuist!", bs);
        // Inloggen - Succes
        bs = this.balie.logIn(br, "groen");
        Assert.assertNotNull("Bankiersessie", bs);
    }
}
