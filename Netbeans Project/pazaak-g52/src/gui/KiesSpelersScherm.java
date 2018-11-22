/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.Taal;

/**
 *
 * @author Renaat
 */
public class KiesSpelersScherm extends BorderPane implements Scherm
{
    private final DomeinController dc;
    private int taalnr;
    private final Taal t;
    private final Scherm parent;
    private Hoofdbalk hoofdbalk;
    private Zijbalk zijbalkLinks, zijbalkRechts;
    private final List<Button> knoppen;
    
    public KiesSpelersScherm(DomeinController dc, Taal t, int taalnr, Scherm parent){
        this.dc = dc;
        this.parent = parent;
        this.t = t;
        this.taalnr = taalnr;
        
        t.kiesTaal(taalnr);
        
        knoppen = new ArrayList<>();
        hoofdbalk = new Hoofdbalk(this);
        hoofdbalk.verwijderOM();
        zijbalkLinks = new Zijbalk();
        zijbalkRechts = new Zijbalk();
        
        buildGui();
    }
    
    private void buildGui()
    {
        //Declaratie
        GridPane centerbalk = new GridPane();
        ScrollPane sp = new ScrollPane(centerbalk);
        HBox knoppenbalk = new HBox();
        Button btnKiesWedstrijdstapel = new Button(Taal.getWoordUitBundle("kiesspelers"));
        Button btnTerug = new Button(Taal.getWoordUitBundle("Cancel"));
        Button btnReset = new Button(Taal.getWoordUitBundle("reset"));
        
        //Buttons
            btnKiesWedstrijdstapel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kiesWedstrijdstapel();                
                }
            });
            btnKiesWedstrijdstapel.setOnKeyPressed(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    kiesWedstrijdstapel();
                }
            });   
        knoppen.add(btnKiesWedstrijdstapel);
        
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refresh();
            }
        });
        btnReset.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))
                    refresh(); 
            }
        });            
        knoppen.add(btnReset);
         
        btnTerug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gaTerug();
            }
        });
        btnTerug.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))
                    gaTerug(); 
            }
        });            
        knoppen.add(btnTerug);
        
        //Opmaak
        centerbalk.setAlignment(Pos.CENTER);
        centerbalk.setHgap(20);
        centerbalk.setVgap(20);
        knoppenbalk.setSpacing(50);
        knoppenbalk.setPadding(new Insets(20));
        knoppenbalk.setAlignment(Pos.CENTER);
        
        //Opvullen
        List<String> namenSpelers = dc.geefNamenBeschikbareSpelers();
        List<SpelerAvatar> spelers = new ArrayList<>();
        for(String s: namenSpelers)
            spelers.add(new SpelerAvatar(s));
        for(int i = 0; i < namenSpelers.size(); i++)
            centerbalk.add(spelers.get(i), i%4, i/4);

        //hoofdbalk
        ((Label)hoofdbalk.getChildren().get(4)).setText(Taal.getWoordUitBundle("kiesspeler1"));
        hoofdbalk.setImgBeurtSpeler1(true);
        hoofdbalk.setImgBeurtSpeler2(false);
        hoofdbalk.visibilityImgGewSetsSpeler1(false);
        hoofdbalk.visibilityImgGewSetsSpeler2(false);
        
        //knoppen
        for(Button b: knoppen)
        {
            b.setPrefHeight(50);
            b.setPrefWidth(250);
            knoppenbalk.getChildren().add(b);
        }
        knoppen.get(0).setPrefWidth(300);
              
        //Zijbalk
        zijbalkLinks.setTitle(Taal.getWoordUitBundle("speler1"));
        zijbalkRechts.setTitle(Taal.getWoordUitBundle("speler2"));
        
        //EventHandlers
        dc.clearSpelersVoorWedstrijd();

        int aantalGekozenSpelers = spelers.size();
        for(SpelerAvatar speler: spelers){
            speler.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(aantalGekozenSpelers == spelers.size())
                    {
                        zijbalkLinks.setTextAvatar(speler.getNaam());
                        zijbalkLinks.voegSpelerAvatarToe(speler);
                        hoofdbalk.setLblSpeler1(speler.getNaam());
                        hoofdbalk.setImgBeurtSpeler1(false);
                        hoofdbalk.setImgBeurtSpeler2(true);
                        ((Label)hoofdbalk.getChildren().get(4)).setText(Taal.getWoordUitBundle("kiesspeler2"));
                    }
                    else
                        if(aantalGekozenSpelers-1 == spelers.size())
                        {
                            zijbalkRechts.setTextAvatar(speler.getNaam());
                            zijbalkRechts.voegSpelerAvatarToe(speler);
                            hoofdbalk.setLblSpeler2(speler.getNaam());
                            hoofdbalk.setImgBeurtSpeler2(false);
                            ((Label)hoofdbalk.getChildren().get(4)).setText("");
                        }
                        else
                        {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle(Taal.getWoordUitBundle("geenspelersmeerselecteerbaar"));
                            alert.setContentText(Taal.getWoordUitBundle("erkunnengeenspelersmeergeselecteerdworden"));
                            alert.show();
                        }
                dc.kiesSpelerVoorWedstrijd(speler.getNaam());
                spelers.remove(speler);
                removeMouseEvent(speler);
                }
            });
        }
        
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.F5)){
                    refresh();
                }
            }  
        });
        
        sp.setPadding(new Insets(10));
        centerbalk.setPadding(new Insets(10, 40, 10, 40));
        
        this.setCenter(sp);
        this.setTop(hoofdbalk);
        this.setRight(zijbalkRechts);
        this.setLeft(zijbalkLinks);
        this.setBottom(knoppenbalk);
        
        //css
        this.getStyleClass().add("bgimage-notext");
    }

    @Override
    public void refresh()
    {
        Scene s = this.getScene();
        s.setRoot(new KiesSpelersScherm(dc, t, taalnr, (HoofdmenuScherm) parent));
    }

    @Override
    public void setTaal(int taalnr)
    {
        try
        {
            this.taalnr = taalnr;
        }
        catch(NullPointerException ex)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("NullPointer Exception");
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void gaTerug()
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(Taal.getWoordUitBundle("bentuzekerdatuwilteruggaan"));
        alert.setTitle(Taal.getWoordUitBundle("gaterug"));
        alert.setContentText(Taal.getWoordUitBundle("drukOKomterugtegaan"));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if(antwoord.get() == ButtonType.OK)
        {
            Scene s = this.getScene();
            s.setRoot((HoofdmenuScherm) parent);
        }
    }    
    
    public void kiesWedstrijdstapel()
    {
        try
        {
            if(dc.geefNamenSpelersVoorWedstrijd().size() < 2)
                throw new IllegalArgumentException(Taal.getWoordUitBundle("erzijnnietgenoegspelersomeenwedstrijdtestarten"));
            else
            {
                Scene s = this.getScene();
                s.setRoot(new KiesWedstrijdstapelScherm(dc, t, taalnr, this));
            }
        }
        catch(IllegalArgumentException e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(Taal.getWoordUitBundle("teweinigspelers"));
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(e.getMessage());
            alert.show();
        }
        catch(IndexOutOfBoundsException e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(Taal.getWoordUitBundle("teweinigspelers"));
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(Taal.getWoordUitBundle("erzijnnietgenoegspelersomeenwedstrijdtestarten"));
            alert.show();
        }
    }
    public void removeMouseEvent(SpelerAvatar speler){
        speler.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //remove event
                }
            });
    }
}
