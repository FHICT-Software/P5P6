package stamboomdomain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;


public class Administratie implements java.io.Serializable {

    //************************datavelden*************************************
    private int nextGezinsNr;
    private int nextPersNr;
    private ArrayList<Persoon> personen;
    private transient ObservableList<Persoon> observablePersonen;

    private ArrayList<Gezin> gezinnen;
    private transient ObservableList<Gezin> observableGezinnen;

    //***********************constructoren***********************************
    /**
     * Er wordt een administratie gecreeerd met 0 personen en dus 0 gezinnen.
     * Personen en gezinnen die in de toekomst zullen worden gecreeerd, worden
     * elk opvolgend genummerd vanaf 1
     */
    public Administratie() {
        nextGezinsNr = 1;
        nextPersNr = 1;
        personen = new ArrayList<>();
        gezinnen = new ArrayList<>();
        observablePersonen = observableList(personen);
        observableGezinnen = observableList(gezinnen);
    }

    //**********************methoden****************************************
    /**
     * er wordt een persoon met een gegeven geslacht, met als voornamen vnamen,
     * achternaam anaam, tussenvoegsel tvoegsel, geboortedatum gebdat,
     * geboorteplaats gebplaats en een gegeven ouderlijk gezin gecreeerd; de
     * persoon krijgt een uniek nummer toegewezen de persoon is voortaan ook bij
     * het ouderlijk gezin bekend. Voor de voornamen, achternaam en gebplaats
     * geldt dat de eerste letter naar een hoofdletter en de resterende letters
     * naar een kleine letter zijn geconverteerd; het tussenvoegsel is zo nodig
     * in zijn geheel geconverteerd naar kleine letters; overbodige spaties zijn
     * verwijderd
     *
     * @param geslacht
     * @param vnamen vnamen.length>0; alle strings zijn niet leeg
     * @param anaam niet leeg
     * @param tvoegsel
     * @param gebdat
     * @param gebplaats niet leeg
     * @param ouderlijkGezin mag de waarde null (=onbekend) hebben
     *
     * @return als de persoon al bekend was (op basis van combinatie van
     * getNaam(), geboorteplaats en geboortedatum), wordt er null geretourneerd,
     * anders de nieuwe persoon
     */
    public Persoon addPersoon(Geslacht geslacht, String[] vnamen, String anaam, String tvoegsel, Calendar gebdat, String gebplaats, Gezin ouderlijkGezin) {
        Persoon returner;
        if (vnamen.length == 0) {
            throw new IllegalArgumentException("ten minst 1 voornaam");
        }
        for (String voornaam : vnamen) {
            if (voornaam.trim().isEmpty()) {
                throw new IllegalArgumentException("lege voornaam is niet toegestaan");
            }
        }

        if (anaam.trim().isEmpty()) {
            throw new IllegalArgumentException("lege achternaam is niet toegestaan");
        }

        if (gebplaats.trim().isEmpty()) {
            throw new IllegalArgumentException("lege geboorteplaats is niet toegestaan");
        }
        if (getPersoon(vnamen, anaam, tvoegsel, gebdat, gebplaats) != null) {
            return null;
        } else {
            returner = new Persoon(nextPersNr, vnamen, anaam, tvoegsel, gebdat, gebplaats, geslacht, ouderlijkGezin);
            observablePersonen.add(returner);
            if (ouderlijkGezin != null) {
                returner.setOuders(ouderlijkGezin);
            }
            nextPersNr++;
            System.out.println(personen.toString());
            System.out.println(observablePersonen.size());
            return returner;
        }
    }

    /**
     * er wordt, zo mogelijk (zie return) een (kinderloos) ongehuwd gezin met
     * ouder1 en ouder2 als ouders gecreeerd; de huwelijks- en scheidingsdatum
     * zijn onbekend (null); het gezin krijgt een uniek nummer toegewezen; dit
     * gezin wordt ook bij de afzonderlijke ouders geregistreerd;
     *
     * @param ouder1
     * @param ouder2 mag null zijn
     *
     * @return null als ouder1 = ouder2 of als de volgende voorwaarden worden
     * overtreden: 1) een van de ouders is op dit moment getrouwd. 2) het koppel
     * uit een ongehuwd gezin kan niet tegelijkertijd als koppel bij een ander
     * ongehuwd gezin betrokken zijn anders het gewenste gezin
     */
    public Gezin addOngehuwdGezin(Persoon ouder1, Persoon ouder2) {
        if (ouder1 == ouder2) {
            return null;
        }
        Calendar nu = Calendar.getInstance();
        if (ouder1.isGetrouwdOp(nu)) {
            return null;
        } else if (ouder2 != null && ouder2.isGetrouwdOp(nu)) {
            return null;
        } else if (ongehuwdGezinBestaat(ouder1, ouder2)) {
            return null;
        }

        Gezin gezin = new Gezin(nextGezinsNr, ouder1, ouder2);
        nextGezinsNr++;
        observableGezinnen.add(gezin);

        ouder1.wordtOuderIn(gezin);
        if (ouder2 != null) {
            ouder2.wordtOuderIn(gezin);
        }

        return gezin;
    }

    /**
     * Als het ouderlijk gezin van persoon nog onbekend is dan wordt persoon een
     * kind van ouderlijkGezin en tevens wordt persoon als kind in dat gezin
     * geregistreerd; <br>
     * Als de ouders bij aanroep al bekend zijn, verandert er niets
     *
     * @param persoon
     * @param ouderlijkGezin
     */
    public void setOuders(Persoon persoon, Gezin ouderlijkGezin) {
        persoon.setOuders(ouderlijkGezin);
    }

    /**
     * als de ouders van dit gezin gehuwd zijn en nog niet gescheiden en datum
     * na de huwelijksdatum ligt, wordt dit de scheidingsdatum. Anders gebeurt
     * er niets.
     *
     * @param gezin
     * @param datum
     * @return true als scheiding geaccepteerd, anders false
     */
    public boolean setScheiding(Gezin gezin, Calendar datum) {
        return gezin.setScheiding(datum);
    }

    /**
     * registreert het huwelijk, mits gezin nog geen huwelijk is en beide ouders
     * op deze datum mogen trouwen (pas op: ook de toekomst kan hierbij een rol
     * spelen omdat toekomstige gezinnen eerder zijn geregisteerd)
     *
     * @param gezin
     * @param datum de huwelijksdatum
     * @return false als huwelijk niet mocht worden voltrokken, anders true
     */
    public boolean setHuwelijk(Gezin gezin, Calendar datum) {
        return gezin.setHuwelijk(datum);
    }

    /**
     *
     * @param ouder1
     * @param ouder2
     * @return true als dit koppel (ouder1,ouder2) al een ongehuwd gezin vormt
     */
    boolean ongehuwdGezinBestaat(Persoon ouder1, Persoon ouder2) {
        return ouder1.heeftOngehuwdGezinMet(ouder2) != null;
    }

    /**
     * als er al een ongehuwd gezin voor dit koppel bestaat, wordt het huwelijk
     * voltrokken, anders wordt er zo mogelijk (zie return) een (kinderloos)
     * gehuwd gezin met ouder1 en ouder2 als ouders gecreeerd; de
     * scheidingsdatum is onbekend (null); het gezin krijgt een uniek nummer
     * toegewezen; dit gezin wordt ook bij de afzonderlijke ouders
     * geregistreerd;
     *
     * @param ouder1
     * @param ouder2
     * @param huwdatum
     * @return null als ouder1 = ouder2 of als een van de ouders getrouwd is
     * anders het gehuwde gezin
     */
    public Gezin addHuwelijk(Persoon ouder1, Persoon ouder2, Calendar huwdatum) {
        Gezin returner = null;
        if (ouder1 == ouder2) {
            return null;
        }
        if (ouder1.kanTrouwenOp(huwdatum) && ouder2.kanTrouwenOp(huwdatum)) {
            for (Gezin g : gezinnen) {
                if (g.isOngehuwd()) {
                    if (g.getOuder1().equals(ouder1) && g.getOuder2().equals(ouder2)) {
                        returner = g;
                    } else if (g.getOuder1().equals(ouder2) && g.getOuder2().equals(ouder1)) {
                        returner = g;
                    }
                }
            }
            if (returner == null) {
                returner = new Gezin(nextGezinsNr, ouder1, ouder2);
                nextGezinsNr++;
            }
            gezinnen.add(returner);
            if (!returner.setHuwelijk(huwdatum)) {
                throw new IllegalArgumentException("Datum voor scheidingsdatum");
            }
        }
        return returner;
    }

    /**
     *
     * @return het aantal geregistreerde personen
     */
    public int aantalGeregistreerdePersonen() {
        return nextPersNr - 1;
    }

    /**
     *
     * @return het aantal geregistreerde gezinnen
     */
    public int aantalGeregistreerdeGezinnen() {
        return nextGezinsNr - 1;
    }

    /**
     *
     * @param nr
     * @return de persoon met nummer nr, als die niet bekend is wordt er null
     * geretourneerd
     */
    public Persoon getPersoon(int nr) {
        return personen.get(nr);
    }

    /**
     * @param achternaam
     * @return alle personen met een achternaam gelijk aan de meegegeven
     * achternaam (ongeacht hoofd- en kleine letters)
     */
    public List<Persoon> getPersonenMetAchternaam(String achternaam) {
        List<Persoon> returner = new ArrayList<>();
        for (Persoon p : personen) {
            if (p.getAchternaam().equalsIgnoreCase(achternaam)) {
                returner.add(p);
            }
        }
        return returner;
    }

    /**
     *
     * @return de geregistreerde personen
     */
    public ObservableList<Persoon> getPersonen() {
        return (ObservableList<Persoon>) FXCollections.unmodifiableObservableList(observablePersonen);
    }

    /**
     *
     * @param vnamen
     * @param anaam
     * @param tvoegsel
     * @param gebdat
     * @param gebplaats
     * @return de persoon met dezelfde initialen, tussenvoegsel, achternaam,
     * geboortedatum en -plaats mits bekend (ongeacht hoofd- en kleine letters),
     * anders null
     */
    public Persoon getPersoon(String[] vnamen, String anaam, String tvoegsel, Calendar gebdat, String gebplaats) {
        StringBuilder vnamenBuilder = new StringBuilder();
        for (String vn : vnamen) {
            vnamenBuilder.append(vn.substring(0, 1));
            vnamenBuilder.append(".");
        }
        String vnamenString = vnamenBuilder.toString();
        for (Persoon p : personen) {
            if (p.getInitialen().equalsIgnoreCase(vnamenString) && p.getAchternaam().equalsIgnoreCase(anaam) && p.getTussenvoegsel().equalsIgnoreCase(tvoegsel) && p.getGebDat().getTime().equals(gebdat.getTime()) && p.getGebPlaats().equalsIgnoreCase(gebplaats)) {
                return p;
            }
        }
        return null;
    }

    /**
     *
     * @return de geregistreerde gezinnen
     */
    public ObservableList<Gezin> getGezinnen() {
        return (ObservableList<Gezin>) FXCollections.unmodifiableObservableList(observableGezinnen);
    }

    /**
     *
     * @param gezinsNr
     * @return het gezin met nummer nr. Als dat niet bekend is wordt er null
     * geretourneerd
     */
    public Gezin getGezin(int gezinsNr) {
        // aanname: er worden geen gezinnen verwijderd
        if (gezinsNr < gezinnen.size()) {
            return gezinnen.get(gezinsNr);
        }
        return null;
    }

    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        observablePersonen = FXCollections.observableList(personen);
        observableGezinnen = FXCollections.observableList(gezinnen);
    }

}
