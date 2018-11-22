package cui; 

import static cui.Cui.kiesMenuOptie;
import java.util.List;
import util.Taal;
import domein.DomeinController;
import java.util.Arrays;


/**
 *
 * @author Simon
 */
public class ApplicatieUC3 {
    /**
     * Aanmaken van private final attributen van DomeinController, ApplicatieUC4, ApplicatieUC5, ApplicatieUC7
     */
    private final DomeinController dc;
    private final ApplicatieUC4 uc4;
    private final ApplicatieUC5 uc5;
    private final ApplicatieUC7 uc7;
    
    /**
     * Constructor waar de domeincontroller wordt gedefinieerd.
     * En uc4,uc5, uc7 worden aangemaakt.
     * @param dc 
     */
    public ApplicatieUC3(DomeinController dc){
        this.dc = dc; 
        uc4 = new ApplicatieUC4(dc);
        uc5 = new ApplicatieUC5(dc);
        uc7 = new ApplicatieUC7(dc);
    }
    /**
     * In deze methode wordt een nieuwe wedstrijd aangemaakt
     * deze bevat een List van String met beschikbareSpelers
     * Eerst wordt spelerVoorWedstrijd leeggemaakt (dit voor een nieuwe wedstrijd zodat de spelers van de vorige wedstrijd weg zijn)
     * De beschikbareSpelers worden weergegeven en de gebruiker kan 2 spelers kiezen (indien er niet genoeg spelers in het spel zouden zitten, wordt een gepaste melding weergegeven)
     * De spelers moeten daarna hun wedstrijdstapel maken(UC4), en krijgen daarbij ook de mogelijkheid om kaarten bij te kopen(UC7).
     * Als de 2 spelers een wedstrijdstapel hebben, wordt de wedstrijd aangemaakt en start de wedstrijd(UC5)
     */
    public void maakNieuweWedstrijd() {
        String gekozenSpeler = "";
        List<String> beschikbareSpelers;
        
        // dc.spelersVoorWedstrijd leegmaken voor een nieuwe (of na een gespeelde) wedstrijd
        dc.clearSpelersVoorWedstrijd();
        
        beschikbareSpelers = dc.geefNamenBeschikbareSpelers();
        if(beschikbareSpelers.size()<2){
            System.out.println(Taal.getWoordUitBundle("erzijnnietgenoegspelersomeenwedstrijdtestarten"));
            return;
        }
        for(int i = 0; i<2; i++){            
            int kaartNr = 0;
            kaartNr = kiesMenuOptie(Taal.getWoordUitBundle("kiesSpelerVoorWedstrijd"), Taal.getWoordUitBundle("beschikbareSpelers"), beschikbareSpelers);
            if(kaartNr == -1)
                return;
            gekozenSpeler = beschikbareSpelers.get(kaartNr-1);
            dc.kiesSpelerVoorWedstrijd(gekozenSpeler);
            beschikbareSpelers.remove(kaartNr-1);             
        }
        
        List<String> namenSpelersZW = dc.geefNamenSpelersVoorWedstrijd();
        
        // ALS EEN VAN DE SPELERS EEN BOT IS, HEEFT DEZE AL EEN WEDSTRIJDSTAPEL
        boolean isAI = false;
        for(String naam: namenSpelersZW){
            if(naam.equals("AI1"))
                isAI = true;
        }
        if(isAI)
            namenSpelersZW.remove("AI1");
        
        
        while(namenSpelersZW.size()>0){
            int kaartNr = 0;
            kaartNr = kiesMenuOptie(Taal.getWoordUitBundle("Kieseenspeleromzijnwedstrijdstapelaantemaken"), Taal.getWoordUitBundle("Devolgendespeler(s)hebbennoggeenwedstrijdstapel"), namenSpelersZW);
            if(kaartNr == -1)
                return;
            gekozenSpeler = namenSpelersZW.get(kaartNr-1);
            dc.kiesSpelerVoorWedstrijdZonderWedstrijdstapel(gekozenSpeler);

            // ------------------ Use Case 7: Koop Kaart ----------------------------------   
            if(uc7.koopKaart()==-1)
                return;

            // ------------------  Use Case 4: maak wedstrijdstapel -----------------------
            if(uc4.maakWedstrijdStapel()==-1)
                return;
            
            namenSpelersZW.remove(gekozenSpeler);                           
        }
        
        System.out.printf(Taal.getWoordUitBundle("Allespelershebbennueenwedstrijdstapel"));
        dc.maakWedstrijd();
    
        // ------------------ Use Case 5: Speel wedstrijd ----------------------------------        
        if(uc5.speelWedstrijd()==-1)
            return;
    
    }
}
