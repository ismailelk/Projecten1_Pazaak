package cui;

import static cui.Cui.kiesMenuOptie;
import domein.DomeinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import util.Taal;

/**
 *
 * @author Jonathan
 */
public class ApplicatieUC6
{
    /**
     * Aanmaken van private final attribuur van DomeinController
     * declaratie van nodige variabelen int kaarrNr en String gekozenOmschrijving
     */
    private final DomeinController dc;
    int kaartNr;
    String gekozenOmschrijving; 
    
    /**
     * Constructor waar de domeincontroller wordt gedefinieerd.
     * @param dc 
     */
    ApplicatieUC6(DomeinController dc) {
        this.dc = dc;
    }
    
    /**
     * In deze methode wordt een set gespeeld
     * Er wordt een nieuwe set aangemaakt
     * De startspeler wordt berekend en in 'spelerAanBeurt' gestoken 
     * Beide spelers hun spelbord wordt gecleared en hun 'spelbordBevroren' attribuut wordt op false gezet
     * Er wordt een setStapel geinitialiseerd
     * De wedstrijdsituatie wordt weergegeven
     * De spelerAanBeurt krijgt een keuzemenu(stop,bevries,speelHandkaart)
     */
    public int speelSet(){
        dc.startNieuweSet();
        /*
        Er wordt een nieuwe set aangemaakt
        De startspeler wordt berekend en in 'spelerAanBeurt' gestoken
        Beide spelers hun spelbord wordt gecleared en hun 'spelbordBevroren' attribuut wordt op false gezet
        Er wordt een setStapel geinitialiseerd
        */
        while(!dc.isSetTenEinde()){
            dc.geefSpelerNieuweSetKaartOpSpelbord();
            if(dc.isSpelerAanBeurtEenAI()){
                dc.speelBeurt();
                this.toonWedstrijdSituatie();
            }
            else{
                this.toonWedstrijdSituatie();

                int keuze = 0;
                List<String> menuOpties =  new ArrayList<>();
                if(dc.geefWedstrijdStapel().length>0){
                    menuOpties =  Arrays.asList(Taal.getWoordUitBundle("stop"), Taal.getWoordUitBundle("bevries"), Taal.getWoordUitBundle("speelHandkaart"));
                }else{
                    menuOpties =  Arrays.asList(Taal.getWoordUitBundle("stop"), Taal.getWoordUitBundle("bevries"));
                }
                keuze = kiesMenuOptie(Taal.getWoordUitBundle("kies_toepassing"), Taal.getWoordUitBundle("ditzijndemogelijkeactiesdieukanuitvoeren"), menuOpties);
                if(keuze == -1)
                    return -1; 

                // als de keuze stop (1) is gaan we door zonder iets te doen
                if(keuze == 2){
                    dc.bevriesSpelbordSpelerAanBeurt();
                }
                else if(keuze==3){
                    String[] kaarten = dc.geefWedstrijdStapel();
                    int kaartNr = 0;
                        kaartNr = kiesMenuOptie(Taal.getWoordUitBundle("Kies1vandevolgendekaartenomtoetevoegenaanjewedstrijdstapel"),Taal.getWoordUitBundle("ditisuwwedstrijdstapel"), Arrays.asList(kaarten));
                    if(kaartNr != -1){
                        gekozenOmschrijving = kaarten[kaartNr-1];
                        if(!dc.geefOmschrijvingWisselKaarten(gekozenOmschrijving).isEmpty()){
                            List<String> wisselKaarten = dc.geefOmschrijvingWisselKaarten(gekozenOmschrijving);
                            kaartNr = kiesMenuOptie(Taal.getWoordUitBundle("Kies1vandevolgendekaartenomtoetevoegenaanjewedstrijdstapel"), Taal.getWoordUitBundle("kieseenkaart"), wisselKaarten);                         
                            if(kaartNr != -1){
                                String gekozenOmschrijvingWisselKaart = wisselKaarten.get(kaartNr-1);
                                dc.voegKaartToeAanSpelbord(gekozenOmschrijvingWisselKaart, gekozenOmschrijving);
                            }     
                        }
                        else{
                            dc.voegKaartToeAanSpelbord(gekozenOmschrijving);
                        }
                    }

                    // Je moet alleen vragen of hij wil bevriezen, als hij nog niet bevroren is (door de xT kaart)
                    if(!dc.isSpelbordSpelerAanBeurtBevroren()){
                        this.toonWedstrijdSituatie();

                        keuze = 0;
                        menuOpties =  Arrays.asList(Taal.getWoordUitBundle("stop"), Taal.getWoordUitBundle("bevries"));
                        keuze = kiesMenuOptie(Taal.getWoordUitBundle("kies_toepassing"), Taal.getWoordUitBundle("ditzijndemogelijkeactiesdieukanuitvoeren"), menuOpties);
                        if(keuze == -1)
                            return -1;

                        // als de keuze stop (1) is gaan we door zonder iets te doen
                        if(keuze == 2){
                            dc.bevriesSpelbordSpelerAanBeurt();
                        }
                    }
                }
            }

            // dus als het spelbord van de andere speler wel bevroren is, dan wordt er niet gewisseld en blijft de huidige Speler aan de beurt
            if(dc.isSpelbordSpelerNietAanBeurtBevroren()==false){
                dc.wisselSpelerAanBeurtEnSpelerNietAanBeurt();
            }

        }
        return 0;
    }
    /**
     * Deze methode bestaat eruit om alle nodige gegevens en informatie op te vragen, en dit allemaal in attributen te steken en zo weer te geven.
     * Het spelbord en de wedstrijdstapel wordt weergegeven
     */
    public void toonWedstrijdSituatie(){
        Map<String,String[]> wedstrijdSituatieMap = dc.geefWedstrijdSituatie();
        
        String[] NaamSetScoreEnSpelerAanBeurtSpeler1 = wedstrijdSituatieMap.get("NaamSetScoreEnSpelerAanBeurtSpeler0");
        String[] NaamSetScoreEnSpelerAanBeurtSpeler2 = wedstrijdSituatieMap.get("NaamSetScoreEnSpelerAanBeurtSpeler1");
        
        int lengteNaamSpeler1 = NaamSetScoreEnSpelerAanBeurtSpeler1[0].length();
        int lengteNaamSpeler2 = NaamSetScoreEnSpelerAanBeurtSpeler2[0].length();
        
        String[] spelbordSpeler1 = wedstrijdSituatieMap.get("spelbordSpeler0");
        String[] spelbordSpeler2 = wedstrijdSituatieMap.get("spelbordSpeler1");

        String[] wedstrijdStapelSpeler1 = wedstrijdSituatieMap.get("wedstrijdStapelSpeler0");
        String[] wedstrijdStapelSpeler2 = wedstrijdSituatieMap.get("wedstrijdStapelSpeler1");

        System.out.print(String.format("%-21s%"+(lengteNaamSpeler1+5)+"s%"+(lengteNaamSpeler2+5)+"s%n", Taal.getWoordUitBundle("naamSpeler") , NaamSetScoreEnSpelerAanBeurtSpeler1[0], NaamSetScoreEnSpelerAanBeurtSpeler2[0]));
        System.out.print(String.format("%-21s%"+(lengteNaamSpeler1+5)+"s%"+(lengteNaamSpeler2+5)+"s%n", Taal.getWoordUitBundle("aantalgewonnensets") , NaamSetScoreEnSpelerAanBeurtSpeler1[1], NaamSetScoreEnSpelerAanBeurtSpeler2[1]));
        System.out.print(String.format("%-21s%"+(lengteNaamSpeler1+5)+"s%"+(lengteNaamSpeler2+5)+"s%n", Taal.getWoordUitBundle("aanbeurt") , NaamSetScoreEnSpelerAanBeurtSpeler1[2], NaamSetScoreEnSpelerAanBeurtSpeler2[2]));
        System.out.print(String.format("%-21s%"+(lengteNaamSpeler1+5)+"s%"+(lengteNaamSpeler2+5)+"s%n", Taal.getWoordUitBundle("spelbordbevroren") , NaamSetScoreEnSpelerAanBeurtSpeler1[3], NaamSetScoreEnSpelerAanBeurtSpeler2[3]));
        System.out.print(String.format("%-21s%"+(lengteNaamSpeler1+5)+"s%"+(lengteNaamSpeler2+5)+"s%n", Taal.getWoordUitBundle("setscore") , NaamSetScoreEnSpelerAanBeurtSpeler1[4], NaamSetScoreEnSpelerAanBeurtSpeler2[4]));
        
        System.out.print(Taal.getWoordUitBundle("spelbord")+"\n");
        int meesteKaartenSpelbord = Math.max(spelbordSpeler1.length, spelbordSpeler2.length);
        for (int i = 0; i < meesteKaartenSpelbord; i++) {
            if(i<spelbordSpeler1.length && i<spelbordSpeler2.length){
                System.out.print(String.format("%"+(lengteNaamSpeler1+5+21)+"s%"+(lengteNaamSpeler2+5)+"s%n", spelbordSpeler1[i], spelbordSpeler2[i]));
            }
            else if(i<spelbordSpeler1.length){
                System.out.print(String.format("%"+(lengteNaamSpeler1+5+21)+"s%n", spelbordSpeler1[i]));
            }
            else if(i<spelbordSpeler2.length){
                System.out.print(String.format("%"+(lengteNaamSpeler1+5+21 + lengteNaamSpeler2+5)+"s%n", spelbordSpeler2[i]));
            }
        }
        
        System.out.print(Taal.getWoordUitBundle("wedstrijdstapel")+"\n");
        int meesteKaartenWedstrijdstapel = Math.max(wedstrijdStapelSpeler1.length, wedstrijdStapelSpeler2.length);
        for (int i = 0; i < meesteKaartenWedstrijdstapel; i++) {
            if(i<wedstrijdStapelSpeler1.length && i<wedstrijdStapelSpeler2.length){
                System.out.print(String.format("%"+(lengteNaamSpeler1+5+21)+"s%"+(lengteNaamSpeler2+5)+"s%n", wedstrijdStapelSpeler1[i], wedstrijdStapelSpeler2[i]));
            }
            else if(i<wedstrijdStapelSpeler1.length){
                System.out.print(String.format("%"+(lengteNaamSpeler1+5+21)+"s%n", wedstrijdStapelSpeler1[i]));
            }
            else if(i<wedstrijdStapelSpeler2.length){
                System.out.print(String.format("%"+(lengteNaamSpeler1+5+21 + lengteNaamSpeler2+5)+"s%n", wedstrijdStapelSpeler2[i]));
            }
        }
        System.out.print("\n");
    }
}
