package domein;

import java.util.ArrayList;
import java.util.List;
import persistentie.KaartMapper;

public class KaartRepository {
    /**
     * De klasse KaartRepository heeft volgende attributen private List van Kaart Objecten kaarten en private final KaartMapper mapper 
     */
    private List<Kaart> kaarten;
    private List<Kaart> startKaarten;
    private final KaartMapper mapper;
    
    /**
     * Deze methode geeft de startKaarten terug.
     * @return 
     */
    public List<Kaart> getStartKaarten() {
        return startKaarten;
    }
    
    /**
     * In de constructor wordt een mapper object aangemaakt en aan het attribuut kaarten worden alle kaarten toegevoegd met de methode geefAlleKaarten() uit KaartMapper (persistentie).
     * Daar worden alle kaarten opgehaald uit de DB en in het lokale geheugen geladen
     */
    public KaartRepository(){
            mapper = new KaartMapper();
            kaarten = mapper.geefAlleKaarten();
            startKaarten = mapper.geefStartstapel();
    }
    
    /**
     * In deze methode worden alle kaarten voor een setStapel opgehaald uit de DB via de KaartMapper
     * En toegevoegd aan List setStapel en wordt dan ook teruggegeven.
     * @return 
     */
    public List<Kaart> geefKaartenVoorSetStapel(){
        List<Kaart> setStapel = new ArrayList<>();
        
        List<Kaart> kaartenList = mapper.geefAlleKaartenVoorSetStapel();
        
        for(int i=0; i<kaartenList.size(); i++){
            for(int j=0; j<4; j++){
                setStapel.add(kaartenList.get(i));
            }
        }
        
        return setStapel;
    }
        
    /**
     * In deze methode worden alle kaarten opgehaald die de speler nog niet in zijn bezig heeft.
     * En worden ook de kaarten die speler niet kan kopen eruit gefilterd.
     * @return 
     */
    public List<Kaart> geefBeschikbareKaarten(Speler speler) {
        List<Kaart> stapel = speler.getStapel();
        
        List<Kaart> alleKaarten = new ArrayList<>(this.kaarten);
        boolean kaartGevonden;
        //filter uit alle kaarten: kaarten die speler al bezit (dus stapel)
        for(int i = 0; i< alleKaarten.size();i++){
            kaartGevonden = false;
            for(Kaart k: stapel)
                if(k.equals(alleKaarten.get(i))){
                    alleKaarten.remove(i);
                    kaartGevonden = true;
                    break;
                }                    
            if(kaartGevonden) i--;
        }
        //filter uit alle kaarten: kaarten in DB die speler niet mag kopen (vb +9, +10)
        for(int i = 0; i< alleKaarten.size();i++){
            if(alleKaarten.get(i).getPrijs() == 0){
                alleKaarten.remove(i);
                i--;
            }
        }
        return alleKaarten;
    }
    
    /**
     * In deze methode wordt een Kaartobject gezocht aan de hand van zijn omschrijving en daarna wordt het KaartObject teruggegeven.
     * @param omschrijving
     * @return 
     */
    public Kaart geefKaartAdhvOmschrijving(String omschrijving)
    {
        kaarten = mapper.geefAlleKaarten();
        for(Kaart kaart: kaarten){
            if(omschrijving.equals(kaart.getOmschrijving())){
                return kaart;
            }
        }
        // Voor het oproepen van deze methode moet gecheckt worden of de parameters correct zijn
        return null;
    }

    
            
}
