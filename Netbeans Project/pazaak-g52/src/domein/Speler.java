package domein;

import java.util.*;
import persistentie.KaartMapper;
import persistentie.SpelerMapper;
import util.Taal;

public class Speler {

    /**
     * De klasse speler heeft volgende attributen List van Kaart stapel, int geboortejaar, int krediet, String naam, KaartMapper kaartMapper, SpelerMapper spelerMapper,
     * List van Kaart  wedstrijdStapel, int aantalGewonnenSets, List van Kaart  spelbord, int setScore, boolean spelbordBevroren
     */
    private List<Kaart> stapel;
    private int geboortejaar;
    private int krediet;
    private String naam;
    private KaartMapper kaartMapper;
    private List<Kaart> wedstrijdStapel;
    private int aantalGewonnenSets;
    private List<Kaart> spelbord;
    private int setScore;
    private boolean spelbordBevroren;
    // ------------- UC10 --------------
    private boolean isAI;
    private int teBehalenScore;

    /**
     * In de constructor van speler wordt de naam, geboortejaar, en krediet ingesteld. naam en geboortejaar worden gecontroleerd met private controleer methodes.
     * Bij het aanmaken van een speler wordt ook onmiddelijk zijn stapel gemaajk. Dit via de kaart- en spelermapper. Het aantalGewonnenSets wordt geinitialiseerd op 0.
     * spelbordBevroren wordt op false ingesteld.
     * @param naam
     * @param geboortejaar
     * @param krediet 
     * @param startKaarten 
     */
    public Speler(String naam, int geboortejaar, int krediet, List<Kaart> startKaarten)
    {
        controleerNaam(naam);
        this.naam = naam;
        controleerGeboortejaar(geboortejaar);
        this.geboortejaar = geboortejaar;
        this.wedstrijdStapel = new ArrayList<>();   
        this.stapel = startKaarten;        
        this.krediet = krediet;
        
        // aantalGewonnenSets op nul zetten
        this.aantalGewonnenSets = 0;
        
        spelbord = new ArrayList<>();
        spelbordBevroren = false;
        
        // nieuwe stapel kaarten toevoegen
        kaartMapper = new KaartMapper();
        
        // ------------- UC10 --------------
        if(this.naam.equals("AI1")){
            this.wedstrijdStapel = kaartMapper.geefWedstrijdStapelAI1();
            this.isAI = true;
        }
        else{
            this.isAI = false;
        }
    }
    
    /**
     * Dit is de extra constructor van speler, waar de eerste constructor wordt aangeroepen maar krediet ingesteld wordt op 0.
     * @param naam
     * @param geboortejaar 
     * @param startKaarten 
     */
    public Speler(String naam, int geboortejaar, List<Kaart> startKaarten) {
            this(naam,geboortejaar, 0, startKaarten);
    }

    /**
     * Deze methode gaat via de kaartMapper kaarten de stapel van de speler toevoegen aan de DB.
     */
    public void voegKaartenToe() {
        kaartMapper.voegKaartenToe(naam, stapel);
    }

    /**
     * Deze methode geeft het krediet van de speler terug.
     * @return 
     */
    public int getKrediet() {
            return krediet;
    }
    
    /**
     * Deze methode stelt het krediet van de speler in.
     * @param krediet 
     */
    public void setKrediet(int krediet) {
            this.krediet = krediet;
    }

    /**
     * Deze methode geeft de naam van de speler terug.
     * @return 
     */
    public String getNaam() {
            return this.naam;
    }

    /**
     * Deze methode geeft het geboortejaar terug.
     * @return 
     */
    public int getGeboortejaar() {
            return this.geboortejaar;
    }
    
    /**
     * Deze methode haalt de omschrijving van de kaarten uit stapel op.
     * @return 
     */
    public String[] geefOmschrijvingKaarten() {
        String[] omschrijvingKaarten = new String[stapel.size()];
        for(int i=0; i<omschrijvingKaarten.length; i++){
            omschrijvingKaarten[i] = stapel.get(i).getOmschrijving();
        }
        return omschrijvingKaarten;
    }
    
    /**
     * Deze methode controleert of het geboortejaar voldoet aan de domeinregels.
     * De leeftijd wordt berekend en bekeken of deze tussen de grenzen ligt.
     * @param geboortejaar 
     */
    private void controleerGeboortejaar(int geboortejaar) {
        int leeftijd = Calendar.getInstance().get(Calendar.YEAR) - geboortejaar;
        if(leeftijd<6 || leeftijd>99)
            throw new IllegalArgumentException(Taal.getWoordUitBundle("Deleeftijdmoettussen6en99liggen"));
    }
    
    /**
     * Deze methode controleer of de naam voldoet aan de domeinregels, dit door karakter per karakter te gaan controleren. 
     * Dit kon ook met reguliere expressies, maar wij doen dit nu door het controlleren van de ASCII code
     * @param naam 
     */
    private void controleerNaam(String naam) {
        if(naam.length()<3)
            throw new IllegalArgumentException(Taal.getWoordUitBundle("Denaammoetminstens3karakterslangzijn"));
        if(naam.charAt(0) >= '0' && naam.charAt(0) <= '9')
            throw new IllegalArgumentException(Taal.getWoordUitBundle("Deeerstelettervandenaammaggeencijferzijn"));
        for(int i = 0; i < naam.length(); i++){
            char c = naam.charAt(i);
            if(c == 32)
                throw new IllegalArgumentException(Taal.getWoordUitBundle("Ermogengeenspatiesindenaamzitten"));
            if((c<=47) || (c>=58 && c<=64) || (c>=91 && c<=96) || (c>=123)) // via intervallen ASCII
                throw new IllegalArgumentException(Taal.getWoordUitBundle("Ermogengeenspecialetekensinnaamzitten"));
        }  
    }
    
    /**
     * Deze methode geeft de wedstrijdstapel terug
     * @return 
     */
    public List<Kaart> getWedstrijdStapel(){
        return wedstrijdStapel;
    }
    
    /**
     * Deze methode maakt de wedstrijdstapel van de huidige speler leeg.
     */
    public void clearWedstrijdStapel()
    {
        this.wedstrijdStapel.clear();
    }

    /**
     * Deze methode set het aantal gewonnen sets van een speler in. 
     * Dit wordt gebruikt bij het laden van een wedstrijd.
     * @param aantalGewonnenSets 
     */
    public void setAantalGewonnenSets(int aantalGewonnenSets)
    {
        this.aantalGewonnenSets = aantalGewonnenSets;
    }
    
   // ------------------  Use Case 4: maak wedstrijdstapel -----------------------
    /**
     * Deze methode vraagt aan de DB via de kaartMapper om een Kaart zijn type en waarde terug te geven via zijn omschrijving.
     * @param omschrijving
     * @return 
     */
    public Kaart geefKaartAdhvOmschrijving(String omschrijving)
    {
        return kaartMapper.geefKaartAdhvOmschrijving(omschrijving);
    }

    /**
     * Deze methode maakt een random wedstrijd stapel aan.
     * @param selectie 
     */
    public void maakWedstrijdstapel(List<Kaart> selectie)
    {
        /* We hebben 4 verschillende getallen binnen het interval [0,5] nodig.
        GEBRUIKTE OPLOSSING:
        Een lijst met de getallen van 0 tem 5 in laten shuffelen en de eerste 4 eruit nemen
        */
        ArrayList<Integer> shuffelLijst = new ArrayList<Integer>();
        for (int i=0; i<6; i++) {
            shuffelLijst.add(new Integer(i));
        }
        Collections.shuffle(shuffelLijst);
        int index;
        for (int i=0; i<4; i++) {
            index = shuffelLijst.get(i);
            this.wedstrijdStapel.add(selectie.get(index));
        }
    }
    
    // ------------------  Use Case 5: speel wedstrijd -----------------------
    /**
     * Deze methode geeft het aantal gewonnen sets van de huidige speler terug.
     * @return 
     */
    public int getAantalGewonnenSets(){
        return aantalGewonnenSets;
    }
    
    /**
     * Deze methode verhoogt het aantal gewonnen sets van de huidige speler met één
     */
    public void verhoogAantalGewonnenSetsMetEen(){
        this.aantalGewonnenSets += 1;
    }
        
    /**
     * Deze methode zet het aantalGewonnenSets van de huidige speler op nul
     */
    public void ZetAantalGewonnenSetsOpNul(){
        this.aantalGewonnenSets = 0;
    }

    // ------------------  Use Case 6: Speel set -----------------------
    /**
     * Deze methode maakt het spelbord van de huidige speler leeg
     */
    public void maakSpelbordLeeg(){
        this.spelbord.clear();
    }

    /**
     * Deze methode stelt de boolean spelbordBevroren in van de huidige speler
     * @param spelbordBevroren 
     */
    public void setSpelbordBevroren(boolean spelbordBevroren)
    {
        this.spelbordBevroren = spelbordBevroren;
    }
    
    /**
     * Deze methode geeft het spelbord van de huidige speler terug
     * @return 
     */
    public List<Kaart> getSpelbord()
    {
        return spelbord;
    }

    /**
     * Deze methode geeft de waarde van het attribuut spelbordBevroren terug
     * @return 
     */
    public boolean isSpelbordBevroren()
    {
        return spelbordBevroren;
    }

    /**
     * Deze methode geeft de setScore van de huidge speler terug
     * @return 
     */
    public int getSetScore()
    {
        return setScore;
    }
    
    /**
     * Deze methode voegt het meegegeven kaart object toe aan het spelbord van de huidige speler. Daarbij wordt onderscheid gemaakt tussen de verschillende soorten kaarten
     * @param kaart 
     */
    public void voegKaartToeAanSpelbord(Kaart kaart){       
        // Setscore aanpassen
        if(kaart.getType().equals("+") || (kaart.getType().equals("-")) || (kaart.getType().equals("xT"))){
            spelbord.add(kaart);
            setScore = berekenSetScore();
        }
        else if(kaart.getType().equals("D")){
            Kaart laatsteKaart = spelbord.get(spelbord.size()-1);
            spelbord.add(laatsteKaart);
            setScore = berekenSetScore();
        }
        else if(kaart.getType().equals("x&y")){
            List<Kaart> spelbordTemp = new ArrayList<>(spelbord);
            maakSpelbordLeeg();
            for(Kaart spelbordKaart: spelbordTemp){
                if((spelbordKaart.getWaarde()==kaart.getWaarde()) || (spelbordKaart.getWaarde()==kaart.getWaarde()*2)){
                    if(spelbordKaart.getType().equals("+")){
                        spelbord.add(new Kaart("-", spelbordKaart.getWaarde(), "-"+spelbordKaart.getWaarde()));
                    }
                    else if(spelbordKaart.getType().equals("-")){
                        spelbord.add(new Kaart("+", spelbordKaart.getWaarde(), "+"+spelbordKaart.getWaarde()));
                    }
                }
                else{
                    spelbord.add(spelbordKaart);
                }
            }
            spelbord.add(kaart);
            setScore = berekenSetScore();
        }
    }

    /**
     * Deze methode berekent telkens de setScore afhankelijk van de kaarten die op het spelbord liggen.
     * @return 
     */
    private int berekenSetScore(){
        int huidigeSetScore = 0;
        for(Kaart kaart: spelbord){
            if(kaart.getType().equals("+") || kaart.getType().equals("xT")){
                huidigeSetScore += kaart.getWaarde();
            }
            if(kaart.getType().equals("-")){
                huidigeSetScore -= kaart.getWaarde();
            }
            // Bij type x&y doe je niets
        }
        return huidigeSetScore;
    }
    
    /**
     * Deze methode verwijdert de gekozen handkaart uit de wedstrijdkaart dit via zijn omschrijving.
     * @param kaart
     */
    public void verwijderGekozenHandkaartUitWedstrijdStapel(Kaart kaart)
    {
        wedstrijdStapel.remove(kaart);
    }

    /**
     * In deze methode wordt de setScore terug op 0 ingesteld
     */
    public void resetSetScore()
    {
        this.setScore = 0;
    }
    
    /**
     * Deze methode geeft de laatste kaart terug die op het spelbord gelegd geweest is.
     * @return 
     */
    public Kaart geefLaatsteKaartVanSpelbord(){
        return spelbord.get(spelbord.size()-1);
    }
    
// ------------------  Use Case 7: Koop kaart -----------------------
    
    /**
     * Deze methode geeft de stapel van de speler terug
     * @return 
     */
    public List<Kaart> getStapel() {
        return stapel;
    }

    /**
     * deze methode heeft een boolean terug die aangeeft of de speler nog voldoende krediet heeft om de gekozen kaart te kopen.
     * @param kaart
     * @return 
     */
    public boolean heeftVoldoendeKrediet(Kaart kaart){
        return kaart.getPrijs()<= krediet;
    }
    
    /**
     * Deze methode gaat via de kaartMapper de teKopenkaart toevoegen aan de stapel van de huidige speler
     * @param teKopenKaart 
     */
    public void voegKaartToeAanStapel(Kaart teKopenKaart) {
      stapel.add(teKopenKaart);
      kaartMapper.voegKaartToe(naam, teKopenKaart);
    }


    // ------------------  Use Case 9: Laad Wedstrijd -----------------------
    /**
     * Deze methode voegt een kaart toe aan de wedstrijdstapel van een speler
     * @param kaart 
     */
    public void voegKaartToeAanWedstrijdStapel(Kaart kaart)
    {
        this.wedstrijdStapel.add(kaart);
    }
    
    // ------------------  Use Case 10: Speel tegen een AI -----------------------

    public boolean isIsAI()
    {
        return isAI;
    }

    // VOORLOPIG ENKEL VOOR + en - KAARTEN
    public void speelBeurt(Speler spelerNietAanBeurt)
    {
        teBehalenScore = 20;
        
        if(spelerNietAanBeurt.isSpelbordBevroren()==true && spelerNietAanBeurt.getSetScore()<20){ 
            teBehalenScore = spelerNietAanBeurt.getSetScore()+1;
        }
        
        // WINCONDITIES
        if(this.setScore>=teBehalenScore && this.setScore<=20){
            this.setSpelbordBevroren(true);
        }
        else{
            int mogelijkeSetscore = 0;
            Kaart handkaartVoorWin = null;
            for(Kaart handkaart: wedstrijdStapel){
                mogelijkeSetscore = berekenSetScoreBijToevoegenKaart(handkaart);
                if(mogelijkeSetscore>=teBehalenScore && mogelijkeSetscore<=20){
                    handkaartVoorWin = handkaart;
                }
            }
            if(handkaartVoorWin!=null){
                this.voegKaartToeAanSpelbord(handkaartVoorWin);
                this.wedstrijdStapel.remove(handkaartVoorWin);
                this.setSpelbordBevroren(true);
            }
        }
    }
    
    public int berekenSetScoreBijToevoegenKaart(Kaart kaart){
        int mogelijkeSetscore = 0;
        
        if(kaart.getType().equals("+")){
            mogelijkeSetscore = setScore + kaart.getWaarde();
        }
        else if(kaart.getType().equals("-")){
            mogelijkeSetscore = setScore - kaart.getWaarde();
        }
        
        return mogelijkeSetscore;
    }

}
