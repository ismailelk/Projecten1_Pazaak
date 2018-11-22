package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import util.Taal;

public class Kaart {
    /**
     * De klasse kaart heeft volgende attributen String type, int waarde, String omschrijving, int prijs en List van Kaart Objecten wisselKaarten.
     */
    private String type;
    private int waarde;
    private String omschrijving;
    private int prijs;
    private List<Kaart> wisselKaarten;
    

    /**
     * De constructor van een kaartObject, ieder kaartobject moet 3 parameters hebben.
     * De parameter prijs is niet verplicht, zie 2de constructor
     * @param type
     * @param waarde
     * @param omschrijving
     * @param prijs
     * Deze parameters worden gecontroleerd met private controleer-methodes
     * Indien een kaart een wisselkaart is wordt er nog een stukje extra code uitgevoerd
     */
    public Kaart(String type, int waarde, String omschrijving, int prijs) {
            this.type = type;
            controleerWaarde(waarde);
            this.waarde = waarde;
            controleerOmschrijving(omschrijving);
            this.omschrijving = omschrijving;
            controleerPrijs(prijs);
            this.prijs = prijs;
            // Indien een kaart wisselKaarten heeft, maak je deze aan
            wisselKaarten = new ArrayList<>();
            if(this.type.equals("+/-")){
                this.wisselKaarten.add(new Kaart("+", this.waarde, "+"+Integer.toString(this.waarde)));
                this.wisselKaarten.add(new Kaart("-", this.waarde, "-"+Integer.toString(this.waarde)));
            }
            if(this.type.equals("x+/-y")){
                this.wisselKaarten.add(new Kaart("+", 1, "+"+1));
                this.wisselKaarten.add(new Kaart("-", 1, "-"+1));
                this.wisselKaarten.add(new Kaart("+", 2, "+"+2));
                this.wisselKaarten.add(new Kaart("-", 2, "-"+2));
            }
            
    }
    /**
     * Constructor van een kaartObject, maar waar de prijs ingesteld wordt op 0
     * Dit zijn kaarten die de speler sowieso tot zijn beschikking heeft.
     * @param type
     * @param waarde
     * @param omschrijving 
     */
    public Kaart(String type, int waarde, String omschrijving){
        this(type, waarde, omschrijving, 0);
    }
    /**
     * De methode heeft de omschrijving van een kaart terug
     * @return 
     */
    public String getOmschrijving() {
            return this.omschrijving;
    }
    /**
     * Deze methode heeft de waarde van een kaart terug
     * @return 
     */
    public int getWaarde() {
            return this.waarde;
    }
    /**
     * Deze methode heeft het type van een kaart terug
     * @return 
     */
    public String getType() {
            return type;
    }
    /**
     * Deze private methode controleert de waarde van een kaart indien de waarde niet tussen 1 en 10 ligt.
     * Indien dit niet voldoet wordt er Exception geworpen en een gepaste melding gegeven. 
     * @param waarde 
     */
    private void controleerWaarde(int waarde) {
        if(waarde<0 || waarde>10)
            throw new IllegalArgumentException(Taal.getWoordUitBundle("Dewaardemoettussen1en10zijn"));
    }
    /**
     * Deze private methode controleert de omschrijving van een kaart. Deze mag niet leeg zijn
     * @param omschrijving 
     */
    private void controleerOmschrijving(String omschrijving) {
        if(omschrijving==null || omschrijving.equals(""))
            throw new IllegalArgumentException(Taal.getWoordUitBundle("Deomschrijvingmoetingevuldzijn"));
    }
    /**
     * Deze methode heeft de prijs van een kaart terug
     * @return 
     */
    public int getPrijs() {
        return prijs;
    }
    
    /**
     * Deze private methode controleert de prijs van een kaart. Deze moet tussne de 0 en 100 liggen.
     * @param prijs 
     */
    private void controleerPrijs(int prijs) {
        if(prijs<0 || prijs > 100)
            throw new IllegalArgumentException(Taal.getWoordUitBundle("prijsmoettussen"));
    }
    
    /**
     * Deze methode geeft de wisselkaarten terug
     * @return 
     */
    public List<Kaart> getWisselkaarten()
    {
        return wisselKaarten;
    }
    
    /**
     * Deze methode heeft de omschrijving van wisselkaarten terug. Maar dit is geen echte getter. 
     * De omschrijving van wisselkaarten wordt toegeboegd aan een List van String die dan wordt teruggegeven
     * @return 
     */
    public List<String> geefOmschrijvingWisselKaarten()
    {
        List<String> omschrijvingenWisselkaarten = new ArrayList<>();
        for(Kaart wisselKaart: wisselKaarten){
            omschrijvingenWisselkaarten.add(wisselKaart.getOmschrijving());
        }
        return omschrijvingenWisselkaarten;
    }

    /**
     * Deze methode kent adhv omschrijving een unieke hashcode toe aan een kaart. 
     * Dit gebruiken we dan in de volgende methode om te controleren op gelijkheid.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.omschrijving);
        return hash;
    }

    /**
     * Deze methode vergelijkt aan de hand van de hashcoden of 2 objecten gelijk zijn
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kaart other = (Kaart) obj;
        if (!Objects.equals(this.omschrijving, other.omschrijving)) {
            return false;
        }
        return true;
    }
    


}