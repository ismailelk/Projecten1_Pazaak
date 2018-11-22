/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cui;

import static cui.Cui.kiesMenuOptie;
import domein.DomeinController;
import exceptions.LaadWedstrijdException;
import java.util.List;
import util.Taal;

/**
 *
 * @author Jonathan
 */
public class ApplicatieUC9
{
    private final ApplicatieUC5 uc5;
    
    /**
     * aanmaken van een private final attribuuit DomeinController dc
     */
    private DomeinController dc;

    /**
     * contructor Applicatie1, de domeincontroller wordt gedefinieerd.
     * @param dc 
     */
    public ApplicatieUC9(DomeinController dc) {
        this.dc = dc;
        this.uc5 = new ApplicatieUC5(dc);
    }
    
    public void laadWedstrijd(){
        List<String> namenVanBestaandeWedstrijden;
        try{
            namenVanBestaandeWedstrijden = dc.geefNamenBestaandeWedstrijden();
        }catch(LaadWedstrijdException e){
            System.out.println(e.getMessage());
            return;
        }
        String gekozenNaam = "";
        int kaartNr = 0;
        kaartNr = kiesMenuOptie(Taal.getWoordUitBundle("kiesWedstrijd"), Taal.getWoordUitBundle("beschikbareWedstrijden"), namenVanBestaandeWedstrijden);
        if(kaartNr == -1)
            return;
        gekozenNaam = namenVanBestaandeWedstrijden.get(kaartNr-1);
        dc.laadWedstrijd(gekozenNaam);
        
        uc5.speelWedstrijd();
    }
}
