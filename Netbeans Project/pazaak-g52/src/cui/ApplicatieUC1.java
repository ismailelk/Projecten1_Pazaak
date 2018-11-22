package cui;

import static cui.Cui.geefInvoer;
import domein.DomeinController;
import util.Taal;

/**
 *
 * @author Simon
 */
public class ApplicatieUC1 {

    /**
     * aanmaken van een private final attribuuit DomeinController dc
     */
    private final DomeinController dc;

    /**
     * contructor Applicatie1, de domeincontroller wordt gedefinieerd.
     * @param dc 
     */
    public ApplicatieUC1(DomeinController dc) {
        this.dc = dc;
    }

    /**
     * De methode zorgt voor het aanmaken van een nieuwe speler
     * Er staat rond deze methode een try catch,met een do-While (een boolean als flag)
     * invoer geboortejaar en naam
     *indien alles goed gaat wordt een nieuwe speler gemaakt door de domeincontroller (dc.maakSpelerAan(naam,geboortejaar)
     *
     * De fouten die bij het maken van Speler (in controleer methoden) zijn
     * gethrowed (in klasse Speler) opvangen en tonen   
     * 
     * aanroep geefInfoSpeler + formattering uitvoer
     *     
     * Dan wordt de startstapel van de speler weergegeven, FOR lus voor de uitvoer van de kaarten van de speler
     */
    public void maakNieuweSpeler() throws Exception {

        boolean error = true;
        String invoer = "";
        do {
            try {
                error = true;
                System.out.println("\n" + Taal.getWoordUitBundle("aanmaakSpeler"));

                invoer = geefInvoer(Taal.getWoordUitBundle("naam"));
                if (invoer.equals("x")) {
                    return;
                }
                String naam = invoer;

                invoer = geefInvoer(Taal.getWoordUitBundle("geboortejaar"), 1);
                if (invoer.equals("x")) {
                    return;
                }
                int geboortejaar = Integer.parseInt(invoer);

                dc.maakSpelerAan(naam, geboortejaar);
                error = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (error);

        System.out.println(Taal.getWoordUitBundle("nieuweSpelerAangemaakt"));
        System.out.println();

        String[] outputSpeler = dc.geefInfoSpeler();
        
        System.out.printf("%n%-20s%-20s%n%10s%10s%n", Taal.getWoordUitBundle("naamSpeler"), outputSpeler[0], Taal.getWoordUitBundle("huidigKrediet"), outputSpeler[1]);
        System.out.printf(Taal.getWoordUitBundle("stapelKaarten") + ":%n%10s%n", Taal.getWoordUitBundle("omschrijving"));

        for (int i = 2; i < outputSpeler.length; i++) {
            System.out.printf("%10s%n", outputSpeler[i]);
        }

    }

}
