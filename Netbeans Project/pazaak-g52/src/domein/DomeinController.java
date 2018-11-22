package domein;

import java.util.*;

/**
 *
 * @author Ismail
 */
public class DomeinController {
    /**
     * De domeincontroller heeft volgende attributen spelerInGebruik(Speler), spelersVoorWedstrijd List van Spelers, 
     * spelerRepo (SpelerRepository), kaartRepo (KaartRepository), wedstrijdRepo (WedstrijdRepository), wedstrijd (Wedstrijd), selectie List van Kaart en startKaarten een List van Kaart
     */
    private Speler spelerInGebruik;
    private List<Speler> spelersVoorWedstrijd;
    private SpelerRepository spelerRepo;
    private KaartRepository kaartRepo;
    private WedstrijdRepository wedstrijdRepo;
    private Wedstrijd wedstrijd;
    private List<Kaart> selectie;
    private List<Kaart> startKaarten;
       
    /**
     * In de constructor van de DomeinController wordt een object van spelersVoorWedstrijd ArrayList, 
     * spelerRepo (SpelerRepository), kaartRepo (KaartRepository), wedstrijdRepo (WedstrijdRepository), selectie ArrayList aangemaakt
     */
    public DomeinController(){
        kaartRepo = new KaartRepository();
        wedstrijdRepo = new WedstrijdRepository();
        spelersVoorWedstrijd = new ArrayList<>();
        startKaarten = kaartRepo.getStartKaarten();
        spelerRepo = new SpelerRepository(startKaarten);
        selectie = new ArrayList<>();
    }
    
// ------------------  Use Case 1: maak nieuwe speler -----------------------
    /**
     * In deze methode wordt het Speler object (door het aanroepen van zijn constructor) je maakt en doorgegeven aan de spelerRepo en aan de DB.
     * Hier wordt ook de methode speler.voegKaartenToe aangeroepen
     * @param naam
     * @param geboortejaar 
     */
    public void maakSpelerAan(String naam, int geboortejaar) {
            spelerInGebruik = new Speler(naam, geboortejaar, startKaarten);

            // speler en kaarten toevoegen aan DB en Repo
            spelerRepo.voegSpelerToe(spelerInGebruik);
            spelerInGebruik.voegKaartenToe();
    }
    
    /**
     * In de methode wordt de info van de desbetreffende speler opgehaald en geformateerd. 
     * Dit wordt meegegeven door een String[].
     * De speler info bestaat uit Kaarten(omschrijving), Krediet.
     * @return 
     */
    public String[] geefInfoSpeler() {
        String[] omschrijvingKaarten = spelerInGebruik.geefOmschrijvingKaarten();
        String[] output = new String[omschrijvingKaarten.length + 2];

        //array opvullen
        output[0] = spelerInGebruik.getNaam();
        output[1] = Integer.toString(spelerInGebruik.getKrediet()); //toString om int te casten naar String (niet automatisch)
        for(int i=0; i<omschrijvingKaarten.length; i++)
            output[i+2] = omschrijvingKaarten[i];
        return output;
    }

// ------------------  Use Case 3: maak nieuwe wedstrijd -----------------------    
    /**
     * In deze methode worden de namen van alleBeschikbareSpelers opgehaald uit de spelerRepo, dit met een FOR-lus
     * En dan wordt de naam daar uit gefilterd en teruggegeven.
     * @return 
     */
    public List<String> geefNamenBeschikbareSpelers(){
        List<String> namenBeschikbareSpelers = new ArrayList<>();
        List<Speler> spelerLijst = spelerRepo.getSpelers(); //De Repository geeft via de getSpelers alle spelers terug die in de lijst worden gestoken
        for(int teller = 0; teller < spelerLijst.size(); teller++) // gewone for om de array op te vullen
            namenBeschikbareSpelers.add(spelerLijst.get(teller).getNaam());
        return namenBeschikbareSpelers;
    }

    /**
     * Hier worden alle spelers overlopen en wordt hun naam opgehaald en in een List van Strings gestopt zodat we alle namen kunnen zien
     * @return 
     */
    public List<String> geefNamenSpelersVoorWedstrijd(){       
        
        List<String> namenSpelersVoorWedstrijd = new ArrayList<>();
        for(Speler speler: spelersVoorWedstrijd)
        {
            namenSpelersVoorWedstrijd.add(speler.getNaam());
        }
        return namenSpelersVoorWedstrijd;
    }

    /**
     * In deze methode wordt de naam meegegeven, en dan wordt de speler opgehaald uit de spelerRepo en toegevoegd aan spelersVoorWedstrijd
     * @param naam 
     */
    public void kiesSpelerVoorWedstrijd(String naam){
        spelersVoorWedstrijd.add(spelerRepo.geefSpelerAdhvNaam(naam));
    }

    /**
     * In deze methode wordt spelerInGebruik ingesteld, deze wordt opgehaald in de spelerRepo aan de hand van zijn naam
     * @param naam 
     */
    public void kiesSpelerVoorWedstrijdZonderWedstrijdstapel(String naam){
        for(Speler spelerUitspelers: spelersVoorWedstrijd){
            if(naam.equals(spelerUitspelers.getNaam())){
                this.spelerInGebruik = spelerUitspelers;
                break;
            }
        }
    }
    
    /**
     * Hier wordt een wedstrijd object gemaakt met spelersVoorWedstrijd
     */
    public void maakWedstrijd(){
        this.wedstrijd = new Wedstrijd(spelersVoorWedstrijd, spelerRepo);
    }

// ------------------  Use Case 4: maak wedstrijdstapel -----------------------     
    /**
     * Hier worden alle kaarten gefiltert die nog niet in je stapel zitten. 
     * Dus eerst worden alle mogelijke kaarten opgehaald en die worden dan vergeleken met de kaarten die je al in je stapel hebt.
     * Alle kaarten waaruit je kan kiezen worden dan weergegeven.
     * @return 
     */
    public String[] geefSelecteerbareKaartenUitStapel()
    {
        String[] stapel = spelerInGebruik.geefOmschrijvingKaarten();
        String[] selecteerbareKaartenUitStartstapel = new String[stapel.length-selectie.size()];
        int teller = 0;
        boolean zitInStapel;

        for(int i=0; i<stapel.length; i++){
            zitInStapel = false;
            for(Kaart kaart: selectie){
                if(stapel[i].equals(kaart.getOmschrijving())){
                    zitInStapel = true; //Als hij nooit in de if geraakt mag de kaart pas toegevoegd worden aan selecteerbareKaartenUitStartstapel
                    break;
                }
            }
            if(!zitInStapel){
                selecteerbareKaartenUitStartstapel[teller] = stapel[i];
                teller++;
            }
        }
        return selecteerbareKaartenUitStartstapel;
    }
    /**
     * Hier wordt een kaart uit de spelerInGebruik zijn stapel geselecteerd om toe te voegen aan de selectie.
     * @param omschrijving 
     */
    public void kiesKaartVoorSelectie(String omschrijving)
    {
        selectie.add(spelerInGebruik.geefKaartAdhvOmschrijving(omschrijving));
    }

    /**
     * In deze methode wordt de selectie leeggemaakt via een methode van List
     */
    public void clearSelectie(){
        selectie.clear();
    }
    /**
    * Hier wordt de methode maakWedstrijdStapel in speler aangeroepen, en de selectie terug leegemaakt voor de volgende keer
    * en wordt de speler in gebruik in de lijst spelersVoorWedstrijd vervangen door de speler met wedstrijdstapel aangeroepen 
    */
    public void maakWedstrijdstapel()
    {
        spelerInGebruik.maakWedstrijdstapel(selectie);
        selectie.clear();
       
        //nietInOntwerp, geen methode buiten getNaam opgeroepen
        for(int i=0; i<spelersVoorWedstrijd.size(); i++){
            if(spelersVoorWedstrijd.get(i).getNaam().equals(spelerInGebruik.getNaam())){
                spelersVoorWedstrijd.set(i, spelerInGebruik);
            }
        }
    }
    
    /**
     * Deze methode roept van de spelerInGebruik een methide aan die zijn Wedstrijdstapel leegmaakt.
     */
    public void clearSpelerInGebruik(){
        this.spelerInGebruik.clearWedstrijdStapel();
    }

// ------------------  Use Case 5: speel wedstrijd -----------------------    
    /**
     * Methode in wedstrijd wordt aangeroepen, met als bedoeling het krediet van de winnaar te verhogen met vijf.
     */
    public void verhoogKredietWinnaarMetVijf(){
        wedstrijd.verhoogKredietWinnaarMetVijf();
    }
    /**
     * Methode in wedstrijd wordt aangeroepen om te zien of er een winnaar is
     * Zo ja, de naam van de winnaar terug te geven.
     * @return 
     */
    public String geefNaamWinnaar(){
        Speler speler = wedstrijd.geefWinnaar();
        return speler.getNaam();
    }
    /**
     * Methode in wedstrijd wordt aangeroepen om te zien of er een winnaar is
     * Zo ja, het krediet van de winnaar wordt geretourneerd
     * @return 
     */
    public int geefKredietWinnaar(){
        Speler speler = wedstrijd.geefWinnaar();
        return speler.getKrediet();
    }
    
    

// ------------------  Use Case 6: Speel set -----------------------   
    /**
     * Methode in wedstrijd wordt aangeroepen om te controleren of de wedstrijd ten einde is
     * @return 
     */    
    public boolean isWedstrijdTenEinde(){
        return wedstrijd.isWedstrijdTenEinde();
    }
    /**
     * Methode in wedstrijd wordt aangeroepen om een nieuwe set te starten
     */
    public void startNieuweSet(){
        wedstrijd.startNieuweSet(kaartRepo.geefKaartenVoorSetStapel());
    }
    
    /**
     * Methode in wedstrijd wordt aangeroepen om te controleren of de set ten einde is
     * @return 
     */
    public boolean isSetTenEinde(){
        return wedstrijd.isSetTenEinde();
    }
    
    /**
     * Methode in wedstrijd wordt aangeroepen om nieuwe set kaart op het spelbord van de speler te leggen
     */
    public void geefSpelerNieuweSetKaartOpSpelbord(){
        wedstrijd.geefSpelerNieuweSetKaartOpSpelbord();
    }
    
    /**
     *  Methode in wedstrijd wordt aangeroepen om de wedstrijdsituatie te retourneren dit in een Map van String en String[]
     * @return 
     */
    public Map<String,String[]> geefWedstrijdSituatie(){
        return wedstrijd.geefWedstrijdSituatie();
    }
    
    /**
     *  Methode in wedstrijd wordt aangeroepen om  spelbord van de spelerAanBeurt te bevriezen
     */
    public void bevriesSpelbordSpelerAanBeurt(){
        wedstrijd.bevriesSpelbordSpelerAanBeurt();
    }
    
    /**
     *  Methode in wedstrijd wordt aangeroepen om te weten of spelerbord van de spelerNietAanBeurtBevroren is
     * @return 
     */
    public boolean isSpelbordSpelerNietAanBeurtBevroren(){
        return wedstrijd.isSpelbordSpelerNietAanBeurtBevroren();
    }
    /**
     *  Methode in wedstrijd wordt aangeroepen om te weten of spelbord van de spelerAanBeurtBevroren is
     * @return 
     */
    public boolean isSpelbordSpelerAanBeurtBevroren(){
        return wedstrijd.isSpelbordSpelerAanBeurtBevroren();
    }
    /**
     *  Methode in wedstrijd wordt aangeroepen om spelerAanBeurt en spelerNietAanBeurt om te wisselen
     */
    public void wisselSpelerAanBeurtEnSpelerNietAanBeurt()
    {
        wedstrijd.wisselSpelerAanBeurtEnSpelerNietAanBeurt();
    }
    /**
     *  Methode in wedstrijd wordt aangeroepen om de wedstrijdstapel terug te geven
     * @return 
     */
    public String[] geefWedstrijdStapel()
    {
        return wedstrijd.geefWedstrijdStapel();
    }
    
    /**
     * Methode voegKaartToeAanSpelbord met 2 parameters wordt aangeroepen om het kaart adhv zijn omschrijving toe te voegen aan het spelbord.
     * hier wordt omschrijving dubbel meegegeven, daardoor kan deze methode voor normale kaarten maar ook voor wisselkaarten gebruikt worden.
     * @param omschrijving 
     */
    public void voegKaartToeAanSpelbord(String omschrijving)
    {
        voegKaartToeAanSpelbord(omschrijving, omschrijving);
    }
    
    /**
     * In deze methode wordt aan de hand van zijn omschrijving een kaart object opgehaald uit de databank via de kaartRepo.
     * Dan wordt via een methode in wedstrijd voegKaartToeAanSpelbord aangeroepen. De originele kaart wordt ook opgehaald uit de databank adhv zijn omschrijving.
     * En kan zo worden verwijdert van de wedstrijdstapel.
     * @param omschrijvingGekozenKaart
     * @param omschrijvingOrigineleKaart 
     */
    public void voegKaartToeAanSpelbord(String omschrijvingGekozenKaart, String omschrijvingOrigineleKaart)
    {
        Kaart kaart = kaartRepo.geefKaartAdhvOmschrijving(omschrijvingGekozenKaart);
        wedstrijd.voegKaartToeAanSpelbord(kaart);
        Kaart origineleKaart = kaartRepo.geefKaartAdhvOmschrijving(omschrijvingOrigineleKaart);
        wedstrijd.verwijderGekozenHandkaartUitWedstrijdStapel(origineleKaart);
    }
    
    /**
     *  Methode in wedstrijd wordt aangeroepen om de uitslag van de set terug te geven.
     * @return 
     */
    public String geefUitSlagSet()
    {
        return wedstrijd.geefUitSlagSet();
    }
    
    /**
     * Methode in wedstrijd wordt aangeroepen om aantalGewonnenSets van de winnaar te verhogen met 1.
     * @param naamWinnaar 
     */
    public void verhoogAantalGewonnenSetsMetEen(String naamWinnaar)
    {
        wedstrijd.verhoogAantalGewonnenSetsMetEen(naamWinnaar);
    }

    /**
     * spelersVoorWedstrijd wordt gecleared
     */
    public void clearSpelersVoorWedstrijd()
    {
        this.spelersVoorWedstrijd.clear();
    }

// ------------------  Use Case 7: Koop kaart ----------------------- 
    
    /**
     * In deze methode wordt aan de spelerInGebruik zijn krediet gevraagd.
     * @return 
     */
    public int geefKredietSpeler(){
        return spelerInGebruik.getKrediet();
    }
    
    /**
     * In deze methode worden alle beschikbare kaarten om te kopen opgehaald en in een array van Strings gestopt samen met hun prijs.
     * En dan via een aparte sorteermethode worden deze allemaal geretourneerd, gesorteerd op prijs.
     * @return 
     */
    public String[][] geefBeschikbareKaarten(){
        List<Kaart> kaartenLijst = kaartRepo.geefBeschikbareKaarten(spelerInGebruik);
        String[][] kaarten = new String[kaartenLijst.size()][2];
        int i = 0;
        for(Kaart k: kaartenLijst){
            kaarten[i][0] = k.getOmschrijving();
            kaarten[i][1] = Integer.toString(k.getPrijs());
            i++;
        }
        //return this.sorteerKaarten(kaarten);
        return kaarten;
    }
    
    /**
     * In deze methode roep je de koopKaart van spelerRepo aan en geef je omschrijving van de gekozenKaart maae die de speler wil kopen.
     * @param omschrijving 
     */
    public void koopKaart(String omschrijving){
        spelerRepo.koopKaart(spelerInGebruik, kaartRepo.geefKaartAdhvOmschrijving(omschrijving));
    }
    
    /**
     * Deze methode is een extra methode
     * Die ervoor zorgt dat de kaarten gesorteerd worden volgens prijs
     * Dit gebeurt door de kaarten te kopieren naar een temp, en dan te sorteren en daarna terug te kopieren naar teKopenKaarten
     */
//    private String[][] sorteerKaarten(String[][] teSorterenKaarten)
//    {
//        String[][] kaartenTemp = teSorterenKaarten.clone();
//        teSorterenKaarten = new String[kaartenTemp.length][2];
//        teSorterenKaarten[0][0] = kaartenTemp[0][0];
//        int index = 0;
//        for(int i=0; i<teSorterenKaarten.length; i++){
//            int laagstePrijs = Integer.MAX_VALUE;
//            for(int j=0; j<teSorterenKaarten.length; j++){
//                if(Integer.parseInt(kaartenTemp[j][1])<laagstePrijs){
//                    laagstePrijs = Integer.parseInt(kaartenTemp[j][1]);
//                    index = j;
//                }
//            }
//            teSorterenKaarten[i][0] = kaartenTemp[index][0];
//            teSorterenKaarten[i][1] = kaartenTemp[index][1];
//            
//            kaartenTemp[index][1] = Integer.toString(Integer.MAX_VALUE);
//        }
//        
//        return teSorterenKaarten;
//    }
    
    
// ------------------  Use Case 8: Bewaar Wedstrijd -----------------------
    /**
     *  Methode in wedstrijd wordt aangeroepen om een naam aan deze wedstrijd te geven. 
     * Daarna wordt de wedstrijd via de methode bewaarWedstrijd in wedstrijdRepo opgeslagen.
     * @param naam 
     */
    public void bewaarWedstrijd(String naam){
        wedstrijdRepo.bewaarWedstrijd(naam, wedstrijd);
    }
    
// ------------------  Use Case 9: Laad Wedstrijd --------------------------

    /**
     * Vraagt aan de wedstrijdRepo alle namen van bestaande wedstrijd en geeft deze terug.
     * @return
     */
    public List<String> geefNamenBestaandeWedstrijden()
    {
        return wedstrijdRepo.geefNamenBestaandeWedstrijden();
    }
  
    /**
     * Deze methode haalt de wedstrijd informatie op uit de DB via de WedstrijdRepository. 
     * En wordt er een nieuwe wedstrijd object aangemaakt met de opgehaalde gegevens.
     * @param naamGekozenWedstrijd 
     */
    public void laadWedstrijd(String naamGekozenWedstrijd)
    {
        String[] namenSpelers = wedstrijdRepo.geefNamenSpelersTeLadenWedstrijd(naamGekozenWedstrijd);
        List<Speler> spelersVoorTeLadenWedstrijd = new ArrayList<>();
        for(String spelerNaam: namenSpelers){
            Speler speler = spelerRepo.geefSpelerAdhvNaam(spelerNaam);
            speler.clearWedstrijdStapel(); // JUST IN CASE dat hij nog niet helemaal leeg is
            String[] aantalGewonnenSetsEnWedstrijdStapelSpeler = wedstrijdRepo.geefAantalGewonnenSetsEnWedstrijdStapelSpeler(naamGekozenWedstrijd,spelerNaam);
            speler.setAantalGewonnenSets(Integer.parseInt(aantalGewonnenSetsEnWedstrijdStapelSpeler[0]));
            Kaart kaart = null;
            for (int i = 1; i < aantalGewonnenSetsEnWedstrijdStapelSpeler.length; i++) {
                kaart = kaartRepo.geefKaartAdhvOmschrijving(aantalGewonnenSetsEnWedstrijdStapelSpeler[i]);
                speler.voegKaartToeAanWedstrijdStapel(kaart);
            }
            spelersVoorTeLadenWedstrijd.add(speler);
        }
        int aantalGespeeldeSets = wedstrijdRepo.geefAantalGespeeldeSets(naamGekozenWedstrijd);
        wedstrijd = new Wedstrijd(spelersVoorTeLadenWedstrijd, aantalGespeeldeSets, naamGekozenWedstrijd, spelerRepo);
        
    }
    
// ------------------  Use Case 10: Speel tegen een AI -----------------------
    public boolean isSpelerAanBeurtEenAI(){
        return wedstrijd.isSpelerAanBeurtEenAI();
    }

    public void speelBeurt()
    {
        wedstrijd.speelBeurt();        
    }

    public List<String> geefOmschrijvingWisselKaarten(String omschrijving)
    {
        Kaart kaart = kaartRepo.geefKaartAdhvOmschrijving(omschrijving);
        return kaart.geefOmschrijvingWisselKaarten();
    }

    

    
    
}

