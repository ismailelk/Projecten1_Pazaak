/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.*;

/**
 *
 * @author Renaat
 */
public class Wedstrijd {

    /**
     * De klasse Wedstrijd heeft volgende private attributen List van Kaart  spelersVoorWedstrijd, int aantalGespeeldeSets, Set huidigeSet, String naam
     */
    List<Speler> spelersVoorWedstrijd = new ArrayList<>();
    private int aantalGespeeldeSets;
    private Set huidigeSet;
    private String naam;
    private SpelerRepository spelerRepository;

    /**
     * De constructor voor wedstrijd maar een wedstrijd aan met spelersVoorWedstrijd deze lijst bestaat uit 2 spelerObjecten en het aantal gewonnenSets wordt op 0 gezet.
     * @param spelersVoorWedstrijd 
     * @param spelerRepository 
     */
    public Wedstrijd(List<Speler> spelersVoorWedstrijd, SpelerRepository spelerRepository){
        this.spelersVoorWedstrijd = spelersVoorWedstrijd;
        this.spelerRepository = spelerRepository;
        
        for(int teller = 0; teller < this.spelersVoorWedstrijd.size(); teller++){
            this.spelersVoorWedstrijd.get(teller).ZetAantalGewonnenSetsOpNul();
        }
        this.huidigeSet = null;
        this.aantalGespeeldeSets = 0;
    }   

    // Constructor voor het laden van een wedstrijd
    public Wedstrijd(List<Speler> spelersVoorWedstrijd, int aantalGespeeldeSets, String naam, SpelerRepository spelerRepository)
    {
        this.spelersVoorWedstrijd = spelersVoorWedstrijd;
        this.aantalGespeeldeSets = aantalGespeeldeSets;
        this.spelerRepository = spelerRepository;
        this.naam = naam;
    }
    
    
    
    /**
     * Deze methode geeft het aanalGespeelde sets tijdens de wedstrijd terug
     * @return 
     */
    public int getAantalGespeeldeSets() {
        return aantalGespeeldeSets;
    }
    
    /**
     * Deze methode geeft de naam van de wedstrijd terug
     * @return 
     */
    public String getNaam() {
        return naam;
    }
    
    /**
     * Deze methode geeft de List van de spelersVoorWedstrijden terug
     * @return 
     */
    public List<Speler> getSpelersVoorWedstrijd() {
        return spelersVoorWedstrijd;
    }
    
    /**
     * De methode kijkt of er een winnaar is, zo niet wordt geretourneerd dat de wedstrijd nog niet ten einde is.
     * @return 
     */
    public boolean isWedstrijdTenEinde()
    {
        if(geefWinnaar()!=null)
            return true;
        return false;
    }
    
    /**
     * Deze methode vraagt via wedstrijd om het krediet van de winnaar die bepaalt wordt met bepaalWinnaar() met 5 te verhogen.
     */
    public void verhoogKredietWinnaarMetVijf(){
        Speler winnaar = geefWinnaar();
        spelerRepository.verhoogKredietSpelerMetVijf(winnaar);
        winnaar.setKrediet(winnaar.getKrediet() + 5);
    }
        
    /**
     * Deze methode geeft de winnende speler terug, er wordt gekeken of een speler 3 sets gewonnen heeft, 
     * van zodra een speler 3 sets gewonnen heeft is hij de winnaar van de wedstrijd
     * @return speler
     */
    public Speler geefWinnaar() {
        if(spelersVoorWedstrijd.get(0).getAantalGewonnenSets() == 3)
            return spelersVoorWedstrijd.get(0);
        if(spelersVoorWedstrijd.get(1).getAantalGewonnenSets() == 3)
            return spelersVoorWedstrijd.get(1);
        else
            return null;
    }
    
    
    // ------------------  Use Case 6: Speel set -----------------------   
    /**
     * In deze methode wordt bekeken wie mag beginnen dit volgens de opgelegde domeinregels
     * en wordt dan een set aangemaakt
     * @param setStapel 
     */
    public void startNieuweSet(List<Kaart> setStapel){
        int geboortejaarOudsteSpeler = Integer.MAX_VALUE;
        Speler oudsteSpeler = null;
        
        for(Speler speler: spelersVoorWedstrijd){
            speler.maakSpelbordLeeg();
            speler.setSpelbordBevroren(false);
            
            if(speler.getGeboortejaar()<geboortejaarOudsteSpeler){
                oudsteSpeler = speler;
                geboortejaarOudsteSpeler = speler.getGeboortejaar();
            }
            else if(speler.getGeboortejaar()==geboortejaarOudsteSpeler){
                String naamOudsteSpeler = oudsteSpeler.getNaam().toLowerCase();
                String naamSpeler = speler.getNaam().toLowerCase();
                
                // Als de naam van de huidige speler alfabetisch voor de naam van de huidige oudste speler komt, wordt de huidige speler de oudsteSpeler
                if(naamOudsteSpeler.compareTo(naamSpeler)>0){
                    oudsteSpeler = speler;
                }
            }
        }
        // Nu we de oudste speler weten, moeten we nog de jongste speler uit de lijst krijgen
        Speler jongsteSpeler = null;
        for(Speler speler: spelersVoorWedstrijd){
            if(speler!=oudsteSpeler){
                jongsteSpeler = speler;
            }
        }
        
        // als het aantalGespeeldeSets even is, dan mag de oudste Speler beginnen
        if(aantalGespeeldeSets%2 == 0){
            huidigeSet = new Set(oudsteSpeler,jongsteSpeler,setStapel);
        }
        else{
            huidigeSet = new Set(jongsteSpeler,oudsteSpeler,setStapel);
        }
        this.aantalGespeeldeSets++;
    }
    
    /**
     * De methode isSetTenEinde vraagt aan set of de huidige set ten einde is
     * @return 
     */
    public boolean isSetTenEinde(){
        return huidigeSet.isSetTenEinde();
    }
    
    /**
     * Deze methode vraagt aan Set om de speler een nieuwe kaart op zijn spelbotd te geven
     */
    public void geefSpelerNieuweSetKaartOpSpelbord(){
        huidigeSet.geefSpelerNieuweSetKaartOpSpelbord();
    }
    
    /**
     * In deze methode wordt wedstrijdsituatie volledig opgebouw en opgeslaan in een HashMap
     * @return 
     */
    public Map<String,String[]> geefWedstrijdSituatie(){
        Map<String,String[]> wedstrijdSituatieMap = new HashMap();
        
        for(int i=0; i<spelersVoorWedstrijd.size(); i++){
            Speler speler = spelersVoorWedstrijd.get(i);
            
            // Toevoegen naam, setScore en true of false voor spelerAanBeurt per Speler
            String[] naamSetScoreEnSpelerAanBeurt = new String[5];
            naamSetScoreEnSpelerAanBeurt[0] = speler.getNaam();
            naamSetScoreEnSpelerAanBeurt[1] = Integer.toString(speler.getAantalGewonnenSets());
            if(speler.equals(huidigeSet.getSpelerAanBeurt())){
                naamSetScoreEnSpelerAanBeurt[2] = "true";
            }
            else{
                naamSetScoreEnSpelerAanBeurt[2] = "false";
            }
            if(speler.isSpelbordBevroren()){
                naamSetScoreEnSpelerAanBeurt[3] = "true";
            }
            else{
                naamSetScoreEnSpelerAanBeurt[3] = "false";
            }
            naamSetScoreEnSpelerAanBeurt[4] = Integer.toString(speler.getSetScore());
            
            String naamSetScore = "NaamSetScoreEnSpelerAanBeurtSpeler"+Integer.toString(i);
            wedstrijdSituatieMap.put(naamSetScore,naamSetScoreEnSpelerAanBeurt);
            
            // Toevoegen spelbord per Speler
            String[] spelbord = new String[speler.getSpelbord().size()];
            int spelbordTeller = 0;
            for(Kaart kaart: speler.getSpelbord()){
                spelbord[spelbordTeller] = kaart.getOmschrijving();
                spelbordTeller++;
            }
            String naamSpelbord = "spelbordSpeler"+Integer.toString(i);
            wedstrijdSituatieMap.put(naamSpelbord,spelbord);
            
            // Toevoegen wedstrijdStapel per Speler
            String[] wedstrijdStapel = new String[speler.getWedstrijdStapel().size()];
            int wedstrijdStapelTeller = 0;
            for(Kaart kaart: speler.getWedstrijdStapel()){
                wedstrijdStapel[wedstrijdStapelTeller] = kaart.getOmschrijving();
                wedstrijdStapelTeller++;
            }
            String naamWedstrijdStapel = "wedstrijdStapelSpeler"+Integer.toString(i);
            wedstrijdSituatieMap.put(naamWedstrijdStapel,wedstrijdStapel);

        }
        
        return wedstrijdSituatieMap;
    }
    /**
     * Deze methode vraagt aan Set om het spelbord van de speler aan beurt te bevriezen
     */
    public void bevriesSpelbordSpelerAanBeurt(){
        huidigeSet.bevriesSpelbordSpelerAanBeurt();
    }
    
    /**
     * Deze methode vraagt aan Set of het spelbord van spelerNietAanBeurt bevroren is
     * @return 
     */
    public boolean isSpelbordSpelerNietAanBeurtBevroren(){
        return huidigeSet.isSpelbordSpelerNietAanBeurtBevroren();
    }
    
     /**
     * Deze methode vraagt aan Set of het spelbord van spelerAanBeurt bevroren is
     * @return 
     */
    public boolean isSpelbordSpelerAanBeurtBevroren(){
        return huidigeSet.isSpelbordSpelerAanBeurtBevroren();
    }
    
    /**
     * Deze methode vraagt aan Set om de spelerAanBeurt en SpelerNietAanBeurt om te wisselen.
     */
    public void wisselSpelerAanBeurtEnSpelerNietAanBeurt()
    {
        huidigeSet.wisselSpelerAanBeurtEnSpelerNietAanBeurt();
    }

    /**
     * Deze methode bouwt de wedstrijdStapel op in de vorm van een String[] en geeft deze terug.
     * @return 
     */
    public String[] geefWedstrijdStapel()
    {
        String[] wedstrijdStapel = new String[huidigeSet.getSpelerAanBeurt().getWedstrijdStapel().size()];
            int wedstrijdStapelTeller = 0;
            for(Kaart kaart: huidigeSet.getSpelerAanBeurt().getWedstrijdStapel()){
                wedstrijdStapel[wedstrijdStapelTeller] = kaart.getOmschrijving();
                wedstrijdStapelTeller++;
            }
        return wedstrijdStapel;

    }
    /**
     * Deze methode vraagt via Set om de meegegeven kaart toe te voegen aan het spelbord
     * @param kaart 
     */
    public void voegKaartToeAanSpelbord(Kaart kaart)
    {
        huidigeSet.voegKaartToeAanSpelbord(kaart);
    }
    
    /**
     * Deze methode vraagt aan Set om de gekozenHandkaart van de speler via zijn omschrijving te verwijderen uit zijn wedstrijdstapel
     * @param kaart 
     */
    public void verwijderGekozenHandkaartUitWedstrijdStapel(Kaart kaart)
    {
        huidigeSet.verwijderGekozenHandkaartUitWedstrijdStapel(kaart);
    }
    
    /**
     * Deze methode vraagt via Set de uitslag van de huidige set op
     * @return 
     */
    public String geefUitSlagSet()
    {
        return huidigeSet.geefUitSlagSet();
    }

    /**
     * Deze methode vraagt aan Speler om voor de winnaar zijn aantalGewonnenSets met 1 te verhogen
     * @param naamWinnaar 
     */
    public void verhoogAantalGewonnenSetsMetEen(String naamWinnaar)
    {
        for(Speler speler: spelersVoorWedstrijd){
            if(speler.getNaam().equals(naamWinnaar)){
                speler.verhoogAantalGewonnenSetsMetEen();
            }
        }
    }

    /**
     * Deze methode stelt de naam van de speler in.
     * @param naam 
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    // ------------------  Use Case 10: Speel tegen een AI -----------------------
    public boolean isSpelerAanBeurtEenAI(){
        return huidigeSet.isSpelerAanBeurtEenAI();
    }
    
    public void speelBeurt()
    {
        huidigeSet.speelBeurt();        
    }


        
}
