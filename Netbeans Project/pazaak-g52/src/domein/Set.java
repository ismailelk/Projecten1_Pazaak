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
public class Set {
    /**
     * De klasse set heeft volgende private attrubuten Speler spelerAanBeurt, Speler spelerNietAanBeurt en List van Kaart setStapel
     */
    private Speler spelerAanBeurt;
    private Speler spelerNietAanBeurt;
    private List<Kaart> setStapel;
    
    /**
     * De constructor van een set, de parameters worden allemaal toegewezen.
     * De setStapel wordt door elkaar geschud, en de nodige attributen van beide spelers worden geinitialiseerd:
     * setSpelbordBevroren(true), setScore wordt gereset en spelbord wordt leeggemaakt
     * @param beginnendeSpeler
     * @param nietBeginnendeSpeler
     * @param setStapel 
     */
    public Set(Speler beginnendeSpeler, Speler nietBeginnendeSpeler, List<Kaart> setStapel){
        this.spelerAanBeurt = beginnendeSpeler;
        this.spelerNietAanBeurt = nietBeginnendeSpeler;
        
        this.setStapel = setStapel;
        Collections.shuffle(setStapel);
        
        this.spelerAanBeurt.setSpelbordBevroren(false);
        this.spelerAanBeurt.resetSetScore();
        this.spelerAanBeurt.maakSpelbordLeeg();
        
        this.spelerNietAanBeurt.setSpelbordBevroren(false);
        this.spelerNietAanBeurt.resetSetScore();
        this.spelerNietAanBeurt.maakSpelbordLeeg();
    }
    
    /**
     * Deze methode bekijkt of de set ten einde is en geeft dan een boolean terug
     * @return 
     */
    public boolean isSetTenEinde(){
        boolean setTenEinde = false;
        // kan allemaal in 1 if, maar dit is overzichtelijker vind ik
        if(spelerAanBeurt.getSpelbord().size()==9 || spelerNietAanBeurt.getSpelbord().size()==9){
            setTenEinde = true;
        }
        else if(spelerAanBeurt.isSpelbordBevroren() && spelerNietAanBeurt.isSpelbordBevroren()){
            setTenEinde = true;
        }
        else if(spelerAanBeurt.getSetScore()>20 || spelerNietAanBeurt.getSetScore()>20){
            setTenEinde = true;
        }
        return setTenEinde;
    }
    
    /**
     * Het systeem voegt een kaart van de setStapel toe aan het spelbord van de spelerAanBeurt en verwijdert deze uit de setStapel.
     */
    public void geefSpelerNieuweSetKaartOpSpelbord(){
        Kaart toeTeVoegenKaart = setStapel.get(0);
        setStapel.remove(toeTeVoegenKaart);
        
        spelerAanBeurt.voegKaartToeAanSpelbord(toeTeVoegenKaart);
    }
    
    /**
     * Deze methode geeft de spelerAanBeurt terug
     * @return 
     */
    public Speler getSpelerAanBeurt()
    {
        return spelerAanBeurt;
    }
    
    /**
     * Deze methode geeft door aan spelerAanBeurt dat zijn spelbordBevroren(attribuut) bevroren moet worden
     */
    public void bevriesSpelbordSpelerAanBeurt(){
        spelerAanBeurt.setSpelbordBevroren(true);
    }
    
    /**
     * Deze methode vraagt aan spelerNietAanBeurt of zijn spelbord wel of niet bevroren is
     * @return 
     */
    public boolean isSpelbordSpelerNietAanBeurtBevroren(){
        return spelerNietAanBeurt.isSpelbordBevroren();
    }
    
    /**
     * Deze methode vraagt aan spelerAanBeurt of zijn spelbord wel of niet bevroren is
     * @return 
     */
    public boolean isSpelbordSpelerAanBeurtBevroren(){
        return spelerAanBeurt.isSpelbordBevroren();
    }

    /**
     * Deze methode wisselt de spelerAanbeurt met de spelerNietAanBeurt
     */
    public void wisselSpelerAanBeurtEnSpelerNietAanBeurt()
    {
        Speler temp = spelerAanBeurt;
        spelerAanBeurt = spelerNietAanBeurt;
        spelerNietAanBeurt = temp;
    }
    
    /**
     * Deze methode voegt via spelerAanBeurt de meegegeven kaart toe aan zijn spelbord en als het een xT kaart is wordt zijn spelbord bevroren.
     * @param kaart 
     */
    public void voegKaartToeAanSpelbord(Kaart kaart)
    {
        this.spelerAanBeurt.voegKaartToeAanSpelbord(kaart);
        if(kaart.getType().equals("xT")){
            spelerAanBeurt.setSpelbordBevroren(true);
        }
    }

    /**
     * Deze methode geeft door aan SpelerAanBeurt dat hij de gekozen handkaart uit zijn wedstrijdStapel moet verwijderen
     * @param kaart 
     */
    public void verwijderGekozenHandkaartUitWedstrijdStapel(Kaart kaart)
    {
        spelerAanBeurt.verwijderGekozenHandkaartUitWedstrijdStapel(kaart);
    }

    /**
     * In deze methode wordt de uitslag van de set bepaald, de score en het spelbord worden bekijken om zo de naam van de winnaar te kunnen teruggeven.
     * @return 
     */
    public String geefUitSlagSet()
    {
        String naamWinnaar = "";
        if(spelerAanBeurt.getSetScore()>20){
            naamWinnaar = spelerNietAanBeurt.getNaam();
        }
        else if(spelerNietAanBeurt.getSetScore()>20){
            naamWinnaar = spelerAanBeurt.getNaam();
        }
        else if(spelerAanBeurt.getSpelbord().size()==9){
            naamWinnaar = spelerAanBeurt.getNaam();
        }
        else if(spelerNietAanBeurt.getSpelbord().size()==9){
            naamWinnaar = spelerNietAanBeurt.getNaam();
        }
        else if(spelerAanBeurt.getSetScore()==spelerNietAanBeurt.getSetScore()){
            if((spelerAanBeurt.geefLaatsteKaartVanSpelbord().getType().equals("xT")) && (!spelerNietAanBeurt.geefLaatsteKaartVanSpelbord().getType().equals("xT"))){
                naamWinnaar = spelerAanBeurt.getNaam();
            }
            else if((spelerNietAanBeurt.geefLaatsteKaartVanSpelbord().getType().equals("xT")) && (!spelerAanBeurt.geefLaatsteKaartVanSpelbord().getType().equals("xT"))){
                naamWinnaar = spelerNietAanBeurt.getNaam();
            }
            else{
                naamWinnaar = "=";
            }
        }
        else if(spelerAanBeurt.getSetScore()>spelerNietAanBeurt.getSetScore()){
            naamWinnaar = spelerAanBeurt.getNaam();
        }
        else if(spelerAanBeurt.getSetScore()<spelerNietAanBeurt.getSetScore()){
            naamWinnaar = spelerNietAanBeurt.getNaam();
        }
        return naamWinnaar;
    }
    
    // ------------------  Use Case 10: Speel tegen een AI -----------------------
    public boolean isSpelerAanBeurtEenAI(){
        return spelerAanBeurt.isIsAI();
    }
    
    public void speelBeurt()
    {
        this.spelerAanBeurt.speelBeurt(spelerNietAanBeurt);
    }

}
