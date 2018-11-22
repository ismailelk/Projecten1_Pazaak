package cui;

import domein.DomeinController;
import util.Taal;

/**
 *
 * @author Jonathan
 */
public class ApplicatieUC5
{
    /**
     * Aanmaken van private final attributen van DomeinController, ApplicatieUC6 uc6, ApplicatieUC8 uc8
     */
    private final DomeinController dc;
    private final ApplicatieUC6 uc6;
    private final ApplicatieUC8 uc8;  
    
    /**
     * Constructor waar de domeincontroller wordt gedefinieerd.
     * En uc6,uc8 worden aangemaakt.
     * @param dc 
     */
    ApplicatieUC5(DomeinController dc) {
        this.dc = dc;
        uc6 = new ApplicatieUC6(dc);
        uc8 = new ApplicatieUC8(dc);
    }
    
    /**
     * In deze methode wordt een wedstrijd gepeeld (De set wordt gespeeld in Applicatie UC6)
     * Deze methode blijft herhaald worden tot de wedstrijd ten einde is (While-lus met dc.isWedstrijdTenEinde)
     * uc.speelSet wordt aangeroepen en de naam van de winnaar wordt teruggegeven en zijn krediet wordt met 5 verhoogd.
     * Indien er een gelijkspel is wordt dit ook weergegeven.
     */
    public int speelWedstrijd(){
        while(!dc.isWedstrijdTenEinde()){
            // ------------------  Use Case 6: Speel set -----------------------
            if(uc6.speelSet()==-1)
                return -1;
            String naamWinnaar = dc.geefUitSlagSet();
            if(naamWinnaar.equals("=")){
                System.out.print(Taal.getWoordUitBundle("gelijkspel"));
            }
            else{
                System.out.print(naamWinnaar + Taal.getWoordUitBundle("setGewonnen"));
                dc.verhoogAantalGewonnenSetsMetEen(naamWinnaar);
            }
            // ---------------------- Use Case 8: Bewaar Wedstrijd -----------------------------
            if(!dc.isWedstrijdTenEinde())
                if(uc8.bewaarWedstrijd()==-1)
                    return -1;
        }
        dc.verhoogKredietWinnaarMetVijf();
        System.out.printf(Taal.getWoordUitBundle("Dewinnaarvandewedstrijdis"),dc.geefNaamWinnaar(), dc.geefKredietWinnaar());
        return 0;
    }
    
}
