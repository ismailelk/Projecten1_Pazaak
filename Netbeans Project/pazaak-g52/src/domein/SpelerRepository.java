package domein;

import exceptions.KoopKaartException;
import java.util.*;
import persistentie.SpelerMapper;
import util.Taal;

public class SpelerRepository {
        /**
        * De klasse spelerRepository heeft volgende private attrubyten List van Kaart  spelers , final SpelerMapper mapper
        */
	private List<Speler> spelers;
        private final SpelerMapper mapper;
        
        /**
        * In de constructor wordt een mapper van SpelerMapper aangemaakt en wordt de volledige lijst van spelers opgevraagd uit de DB via de mapper.
         * @param startKaarten
        */
        public SpelerRepository(List<Kaart> startKaarten){
            mapper = new SpelerMapper(startKaarten);
            spelers = mapper.geefLijstSpelers();
        }
        
        /**
         * Deze methode voegt een neiuwe speler toe aan de lijst met spelers en voeft deze ook toe aan de DB via de mapper. 
         * Maar voor hij dit doet wordt eerst via methode bestaatSpeler gecontroleerd op de speler bestaat. Als de speler al bestaat wordt een Exception geworpen en de methode niet voltooid.
         * @param speler 
         */
	public void voegSpelerToe(Speler speler) {
            if(bestaatSpeler(speler)){
                throw new IllegalArgumentException(Taal.getWoordUitBundle("Despelerbestaalal"));
            }
            mapper.voegToe(speler);
            spelers.add(speler);
	}

        /**
         * Deze methode overloopt alle spelers en controleert of er een speler met dezelde naam is.
         * @param speler
         * @return 
         */
	public boolean bestaatSpeler(Speler speler) {
            for(Speler spelerUitspelers: spelers){
                if(speler.getNaam().toLowerCase().equals(spelerUitspelers.getNaam().toLowerCase())){
                    return true;
                }
            }
        return false;
	}
        
        /**
         * Deze merhode geeft de lijst van Spelers terug
         * @return 
         */
        public List<Speler> getSpelers(){
            return spelers;
        }
        
        /**
         * Deze methode geeft een SpelerObject terug uit spelers via zijn naam
         * @param naam
         * @return 
         */
        public Speler geefSpelerAdhvNaam(String naam){
            for(Speler spelerUitspelers: spelers){
                if(naam.equals(spelerUitspelers.getNaam())){
                    return mapper.geefSpeler(naam);
                }
            }
        // Voor het oproepen van deze methode moet gecheckt worden of de parameters correct zijn
        return null;
        }
        
    public void koopKaart(Speler speler, Kaart kaart){
        if(speler.heeftVoldoendeKrediet(kaart)){
            speler.voegKaartToeAanStapel(kaart);
            mapper.wijzigKredietSpeler(speler, speler.getKrediet() - kaart.getPrijs());
            speler.setKrediet(speler.getKrediet() - kaart.getPrijs());
        }
        else
            throw new KoopKaartException(Taal.getWoordUitBundle("uheeftteweinigkrediet"));
    }
        
    public void verhoogKredietSpelerMetVijf(Speler winnaar) {
        mapper.wijzigKredietSpeler(winnaar, winnaar.getKrediet() + 5);
    }


}
