package domein;

import exceptions.LaadWedstrijdException;
import exceptions.WedstrijdBestaatAlException;
import java.util.ArrayList;
import java.util.List;
import persistentie.WedstrijdMapper;
import util.Taal;

public class WedstrijdRepository {
    
    private WedstrijdMapper mapper;
    private List<Wedstrijd> wedstrijden;
    
    public WedstrijdRepository(){
        mapper = new WedstrijdMapper();
        wedstrijden = new ArrayList<>();
        // wedstrijden pas uit DB gehaald als dit nodig is, anders vertraging op programma!
    }
    
    /**
     * In deze methode wordt de naam doorgegegven voor de wedstrijd en indien uniek opgeslaan aan de WedstrijdMapper die deze dan in de databank bewaard
     * @param naam
     * @param wedstrijd 
     */
    public void bewaarWedstrijd(String naam, Wedstrijd wedstrijd) {
        if(isUniekeWedstrijd(naam)){
            wedstrijd.setNaam(naam);
            mapper.bewaarWedstrijd(wedstrijd);
        }
        else
            throw new WedstrijdBestaatAlException(Taal.getWoordUitBundle("dewedstrijdmetnaam") + naam + Taal.getWoordUitBundle("bestaatal"));
    }
    
    /**
     * Deze methode haal alle wedstrijdnamen op uit de DB via de WedstrijdMapper, en controleert op de gekozen naam uniek is.
     * En geeft dit terug dmv een boolean
     * @param naam
     * @return isUniek
     */
    private boolean isUniekeWedstrijd(String naam) {
        List<String> wedstrijdNamen = mapper.geefAlleWedstrijdNamen();
        boolean isUniek = true;
        for(String w: wedstrijdNamen)
            if(w.toLowerCase().equals(naam.toLowerCase()))
                isUniek = false;
        return isUniek;
    }

    /**
     * Deze methode haalt alle namen van de bestaande wedstrijden via de mapper
     * @return 
     */
    public List<String> geefNamenBestaandeWedstrijden()
    {
        List<String> wedstrijdLijst = mapper.geefAlleWedstrijdNamen();
        if(wedstrijdLijst.isEmpty())
            throw new LaadWedstrijdException(Taal.getWoordUitBundle("nogGeenWedstrijden"));
        return wedstrijdLijst;
    }

    /**
     * Deze methode haalt vanuit de mapper de namen van de  spelers op waarvan je een wedstrijd geselecteerd hebt.
     * @param naamGekozenWedstrijd
     * @return 
     */
    public String[] geefNamenSpelersTeLadenWedstrijd(String naamGekozenWedstrijd)
    {
        return mapper.geefNamenSpelersTeLadenWedstrijd(naamGekozenWedstrijd);
    }

    /**
     * Deze methode haalt vanuit de mapper het aantalGewonnenSets van de geselecteerde spelers op en geeft deze terug.
     * @param naamGekozenWedstrijd
     * @param spelerNaam
     * @return 
     */
    public String[] geefAantalGewonnenSetsEnWedstrijdStapelSpeler(String naamGekozenWedstrijd, String spelerNaam)
    {
        return mapper.geefAantalGewonnenSetsEnWedstrijdStapelSpeler(naamGekozenWedstrijd, spelerNaam);
    }

    /**
     * Deze methode haalt vanuit de mapper het aantalGespeeldeSets op, en geeft deze terug.
     * @param naamGekozenWedstrijd
     * @return 
     */
    public int geefAantalGespeeldeSets(String naamGekozenWedstrijd)
    {
        return mapper.geefAantalGespeeldeSets(naamGekozenWedstrijd);
    }
    
}
