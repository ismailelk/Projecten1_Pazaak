package cui;

import static cui.Cui.kiesMenuOptie;
import domein.DomeinController;
import java.util.Arrays;
import java.util.List;
import util.Taal;

/**
 *
 * @author Jonathan
 */
public class ApplicatieUC4
{
    /**
     * Aanmaken van private final attribuur van DomeinController
     * declaratie van nodige variabelen String invoer, int kaarrNr, String gekozenOmschrijving
     */
    private final DomeinController dc;
    String invoer = "";
    int kaartNr;
    String gekozenOmschrijving;
    
    /**
     * Constructor waar de domeincontroller wordt gedefinieerd.
     * @param dc 
     */
    ApplicatieUC4(DomeinController dc) {
        this.dc = dc;
    }
    
    /**
     * In deze methode wordt een wedstrijdstapel aangemaakt
     * Er moeten 6 kaarten in een wedstrijdstapel zitten. De speler ziet de selecteerbare kaarten uit stapel. Daar uit kan hij er 6 kiezen
     * deze kaarten worden toegevoegd aan de selectie, van zodra de selectie uit 6 kaarten bestaat wordt wedstrijdStapel aangemaakt.
     */
    public int maakWedstrijdStapel(){
        // 6 kaarten toevoegen aan selectie
            for(int i=0; i<6; i++)
            {
                dc.clearSpelerInGebruik();
                String[] kaarten = dc.geefSelecteerbareKaartenUitStapel();
                
                //KIESKAARTMENU
                int kaartNr = 0;
                List<String> menuOpties = Arrays.asList(kaarten);
                kaartNr = kiesMenuOptie(Taal.getWoordUitBundle("Kies1vandevolgendekaartenomtoetevoegenaanjewedstrijdstapel"), Taal.getWoordUitBundle("kieseenkaart"), menuOpties);
                if(kaartNr == -1)
                    return -1;
                
                gekozenOmschrijving = kaarten[kaartNr-1];
                dc.kiesKaartVoorSelectie(gekozenOmschrijving);
            }
                
            dc.maakWedstrijdstapel();
            return 0;
    }
}
