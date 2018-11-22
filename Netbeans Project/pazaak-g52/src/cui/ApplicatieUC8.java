package cui;

import static cui.Cui.geefInvoer;
import static cui.Cui.kiesMenuOptie;
import domein.DomeinController;
import exceptions.MenukeuzeBuitenInterval;
import exceptions.WedstrijdBestaatAlException;
import util.Taal;

/**
 *
 * @author Jonathan
 */
public class ApplicatieUC8
{
    /**
     * Aanmaken van private final attributen van DomeinController dc
     * Aanmaken van variable String invoer
     */
    private final DomeinController dc;
    String invoer = "";
    
    /**
     * Constructor waar de domeincontroller wordt gedefinieerd.
     * @param dc 
     */
    ApplicatieUC8(DomeinController dc) {
        this.dc = dc;
    }
    
    /**
     * In deze methode wordt wedstrijd opgeslaan
     * Hier krijgt de speler de mogelijk om de wedstrijd op te slaan, indien hij hiervoer kiest moet hij de wedstrijd een unieke naam geven
     * Dit wordt allemaal gecontrolleerd.
     * indien de naam uniek is, wordt de wedstrijd opgeslaan in de database.     * 
     */
    public int bewaarWedstrijd(){
        int keuze = 0, verderSpelen = 0;
        boolean error;
        boolean terugKeren;
        keuze = kiesMenuOptie(Taal.getWoordUitBundle("uwkeuze"), Taal.getWoordUitBundle("wiludewedstrijdbewaren"));
        switch(keuze){
            case -1: case 2: return 0;
            case 1: 
                do{
                    error = true;
                    terugKeren = false;
                    try{
                        invoer = geefInvoer(Taal.getWoordUitBundle("geefeenuniekenaamvoorwedstrijd"));
                        if(invoer.equals("x")){
                            terugKeren = true;
                            break;
                        } 
                        dc.bewaarWedstrijd(invoer);
                        error = false;
                    }catch(WedstrijdBestaatAlException e){
                            System.out.print(e.getMessage());
                    }
                }while(error);
                //TODO: taal onderstaande lijn
                verderSpelen = kiesMenuOptie(Taal.getWoordUitBundle("uwkeuze"), Taal.getWoordUitBundle("wiltuverderspelen"));
                if(verderSpelen == 2 || verderSpelen == -1)
                    return -1;
                break;
        }
        return 0;
    }
}
