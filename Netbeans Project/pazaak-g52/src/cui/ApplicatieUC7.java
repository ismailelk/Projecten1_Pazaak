package cui;

import static cui.Cui.kiesMenuOptie;
import domein.DomeinController;
import exceptions.KoopKaartException;
import java.util.ArrayList;
import java.util.List;
import util.Taal;

/**
 * 
 * @author Ismail
 */


public class ApplicatieUC7 {
    /**
     * Aanmaken van private final attributen van DomeinController dc
     * Aanmaken van variable String[][] teKopenKaarten
     */
    private final DomeinController dc;
    private String[][] teKopenKaarten;
    
    /**
     * Constructor waar de domeincontroller wordt gedefinieerd.
     * @param dc 
     */
    ApplicatieUC7(DomeinController dc) {
        this.dc = dc;
    }
    /**
     * Deze methode wordt aangeroepen wanneer de speler bij het aanmaken van zijn wedstrijdstapel ervoor kiest om een kaart aan te kopen.
     * In deze methode wordt eerst de volgende methode dc.startAankoopKaart aangeroepen, de uitkost hiervan wordt bij gehouden in teKopenKaarten
     * Indien de speler alle kaarten al in zijn bezit heeft, dan wordt een gepaste melding gegeven.
     * Indien de speler kiest om de kaart te kopen wordt gevraagd dit nogmaals te bevestigen.
     */
    public int koopKaart(){
        int keuze;
        teKopenKaarten = dc.geefBeschikbareKaarten();
        do{            
            if(teKopenKaarten.length == 0){
                System.out.println(Taal.getWoordUitBundle("jehebtallekaartenalinbezit"));
                return 0;
            }
            
            //KIESKAARTMENU - Ja/Nee keuze
            keuze = kiesMenuOptie(Taal.getWoordUitBundle("uwkeuze"), Taal.getWoordUitBundle("wilueenkaartkopen"));
            
            switch(keuze){
                case -1: return -1;
                case 2: return 0;
                case 1: 
                    String menuHeader = String.format(Taal.getWoordUitBundle("ditzijndetekopenkaartenuheeft")+" %s "+Taal.getWoordUitBundle("krediet")+".%n%17s%10s", dc.geefKredietSpeler(), Taal.getWoordUitBundle("omschrijving"), Taal.getWoordUitBundle("prijs"));
                    List<String> menuOpties = new ArrayList<>();

                    //reset als er een kaart gekocht werd
                    teKopenKaarten = dc.geefBeschikbareKaarten();

                    for(int i=0; i<teKopenKaarten.length; i++){
                        menuOpties.add(String.format("%12s%10s", teKopenKaarten[i][0], teKopenKaarten[i][1]));
                    }

                    keuze = 0;
                    boolean error = true;
                    do{
                        //KOOPKAARTMENU
                        keuze = kiesMenuOptie( Taal.getWoordUitBundle("kieseennummeromeenkaarttekopen"), menuHeader, menuOpties);
                        if(keuze == -1)
                            return 0;
                        try{
                            dc.koopKaart(teKopenKaarten[keuze - 1][0]);
                            System.out.printf(Taal.getWoordUitBundle("uheefnogkredietover"), dc.geefInfoSpeler()[1]);
                            error = false;
                        }catch(KoopKaartException e){
                            System.out.println(e.getMessage());
                        }
                    }while(error);
            }    
        }while((keuze!=2) || (keuze!=-1));
        return 0;
    }
}
