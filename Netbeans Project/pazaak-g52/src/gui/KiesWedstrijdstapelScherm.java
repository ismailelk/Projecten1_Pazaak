/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import util.Taal;

/**
 *
 * @author Renaat
 */
public class KiesWedstrijdstapelScherm extends BorderPane implements Scherm
{
    private final DomeinController dc;
    private int taalnr, kaartnr, indexSpelerAanBeurt = 0;
    private final Taal t;
    private final Scherm parent;
    private Hoofdbalk hoofdbalk;
    private final List<Button> knoppen;
    private List<String> namenSpelersVoorWedstrijd;
    private List<Kaart> kaartenSpeler;
    private HBox hbKaarten;
    private GridPane center;
    private Button btnSpeelWedstrijd;
    private VBox vbCenter;
    
    public KiesWedstrijdstapelScherm(DomeinController dc, Taal t, int taalnr, Scherm parent){
        this.dc = dc;
        this.parent = parent;
        this.t = t;
        this.taalnr = taalnr;
        kaartnr = 0;
        
        t.kiesTaal(taalnr);
        
        knoppen = new ArrayList<>();
        this.hoofdbalk = new Hoofdbalk(this);
        hoofdbalk.verwijderOM();
        namenSpelersVoorWedstrijd = dc.geefNamenSpelersVoorWedstrijd();
        kaartenSpeler = new ArrayList<>();
        hbKaarten = new HBox();
        center = new GridPane();
        
        buildGui();
    }
    
    private void buildGui()
    {
        //Opvullen
        vulKaartenOp(center);
        
        //Declaratie
        ScrollPane sp = new ScrollPane(center);
        HBox knoppenbalk = new HBox();
        btnSpeelWedstrijd = new Button(Taal.getWoordUitBundle("speelwedstrijd"));
        Button btnTerug = new Button(Taal.getWoordUitBundle("gaterug"));
        Button btnReset = new Button(Taal.getWoordUitBundle("Reset"));
        Button btnMaakWedstrijdstapel = new Button(Taal.getWoordUitBundle("maakwedstrijdstapel"));
        Button btnKoopKaart = new Button(Taal.getWoordUitBundle("koopkaart"));
        vbCenter = new VBox();
               
        //Center
        vbCenter.getChildren().addAll(hbKaarten, sp);
        hbKaarten.getStyleClass().add("bgkader");
        vbCenter.setSpacing(20);
        vbCenter.setPadding(new Insets(20, 150, 0, 150));
        vbCenter.setAlignment(Pos.CENTER);
        
        hbKaarten.setSpacing(40);
        hbKaarten.setAlignment(Pos.CENTER);
        hbKaarten.setPrefHeight(240);
        hbKaarten.setMinHeight(240);
        
        //Buttons
        btnSpeelWedstrijd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                speelWedstrijd();
            }
        });
        btnSpeelWedstrijd.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))   
                    speelWedstrijd();
            }
        });
        
        btnMaakWedstrijdstapel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                maakWedstrijdstapel();
            }
        });
        btnMaakWedstrijdstapel.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))   
                    maakWedstrijdstapel();
            }
        });
        knoppen.add(btnMaakWedstrijdstapel);
        
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
        
        btnKoopKaart.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override  
            public void handle(ActionEvent event)
            {
                koopKaart();
            }
        });
        btnTerug.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))
                    koopKaart(); 
            }
        });            
        knoppen.add(btnKoopKaart);
        
        //Opmaak
        center.setHgap(40);
        center.setVgap(20);
        center.setPadding(new Insets(0, 0, 0, 40));
        sp.setPadding(new Insets(15));
        
        this.getStyleClass().add("bgimage-notext");
        
        //hoofdbalk
        ((Label)hoofdbalk.getChildren().get(4)).setText(Taal.getWoordUitBundle("maakwedstrijdstapel"));
        hoofdbalk.setImgBeurtSpeler1(true);
        hoofdbalk.setImgBeurtSpeler2(false);
        hoofdbalk.setLblSpeler1(namenSpelersVoorWedstrijd.get(0));
        hoofdbalk.setLblSpeler2(namenSpelersVoorWedstrijd.get(1));
        
        //knoppen
        for(Button b: knoppen)
        {
            b.setPrefHeight(50);
            b.setPrefWidth(250);
            knoppenbalk.getChildren().add(b);
        }
        knoppenbalk.setSpacing(30);
        knoppenbalk.setAlignment(Pos.CENTER);
        knoppenbalk.setPadding(new Insets(20));
        ((Button)knoppenbalk.getChildren().get(0)).setPrefWidth(300);
              
        //EventHandlers
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.F5))
                    refresh();
            }
        });
                
        this.setCenter(vbCenter);
        this.setTop(hoofdbalk);
        this.setBottom(knoppenbalk);
    }

    @Override
    public void refresh()
    {
        //score aanpassen
        setKredietSpeler(indexSpelerAanBeurt);
        //kaarten resetten
        vulKaartenOp(center);
        //selectie clearen
    }

    @Override
    public void setTaal(int taalnr)
    {
        this.taalnr = taalnr;
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
            dc.clearSelectie();
            Scene s = this.getScene();
            s.setRoot((Parent) parent);
            parent.refresh();
        }
    }    
    
    public void speelWedstrijd()
    {
        dc.maakWedstrijd();
        Scene s = this.getScene();
        s.setRoot(new SpeelWedstrijdScherm(dc, t, taalnr));
    }

    private void vulKaartenOp(GridPane center) {
        dc.clearSelectie();
        kaartnr = 0;
        hbKaarten.getChildren().clear();
        for(int i=0; i<6; i++)
            hbKaarten.getChildren().add(new Kaart(""));
        center.getChildren().clear();
        
        // ALS EEN VAN DE SPELERS EEN BOT IS, HEEFT DEZE AL EEN WEDSTRIJDSTAPEL
        if(namenSpelersVoorWedstrijd.get(indexSpelerAanBeurt).equals("AI1"))
            indexSpelerAanBeurt++;
        
        String speler = namenSpelersVoorWedstrijd.get(indexSpelerAanBeurt);
        dc.kiesSpelerVoorWedstrijdZonderWedstrijdstapel(speler);
        setKredietSpeler(indexSpelerAanBeurt);
        List<String> selecteerbareKaarten = Arrays.asList(dc.geefSelecteerbareKaartenUitStapel());        
        List<Kaart> kaarten = new ArrayList<>();
        for(String s: selecteerbareKaarten)
            kaarten.add(new Kaart(s));
        
        for(int i = 0; i < kaarten.size(); i++)
            center.add(kaarten.get(i), i%6, i/6);
        
        for(Kaart kaart: kaarten)
        {
            kaart.setOnMouseClicked(new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    try
                    {
                        dc.kiesKaartVoorSelectie(kaart.getOmschrijving());
                        voegKaartToe(kaart);
                        removeMouseEvent(kaart);
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle(Taal.getWoordUitBundle("wedstrijdstapelvol"));
                        alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
                        alert.setContentText(Taal.getWoordUitBundle("dewedstrijdstapelzitalvol"));
                        alert.show();
                    }
                }
            }); 
        }
    }
    
    private void voegKaartToe(Kaart kaart) 
    {
        hbKaarten.getChildren().set(kaartnr, kaart);
        kaartnr++;
        center.getChildren().remove(kaart);
    }
    
    public void removeMouseEvent(Kaart kaart)
    {
        kaart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //remove event
            }
        });
    }
    
    private void maakWedstrijdstapel()
    {
        try
        {
            dc.maakWedstrijdstapel();
            indexSpelerAanBeurt++;
            
            // ALS ER EEN BOT MEESPEELT MOETEN WE SOWIESO NAAR CASE 2
            for(String spelerNaam: namenSpelersVoorWedstrijd){
                if(spelerNaam.equals("AI1"))
                    indexSpelerAanBeurt=2;
            }

            switch(indexSpelerAanBeurt)
            {
                case 1:                
                    hoofdbalk.setImgBeurtSpeler1(false);
                    hoofdbalk.setImgBeurtSpeler2(true);
                    vulKaartenOp(center);
                    break;
                case 2:
                    hoofdbalk.setImgBeurtSpeler1(false);
                    hoofdbalk.setImgBeurtSpeler2(false);
                    maakVanCenterTussenscherm();
                    break;
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(Taal.getWoordUitBundle("uheeftteweinigkaartengekozen"));
            alert.show();
        }
    }

    private void maakVanCenterTussenscherm() 
    {
        knoppen.get(0).setDisable(true);
        knoppen.get(1).setDisable(true);
        vbCenter.getChildren().set(0, new Label(Taal.getWoordUitBundle("allewedstrijdstapelszijngemaakt")));
        vbCenter.getChildren().set(1, btnSpeelWedstrijd);
        vbCenter.getStyleClass().add("bgkader");
        
        vbCenter.setMaxSize(800, 200);
        vbCenter.setPadding(new Insets(40));
    }

    private void setKredietSpeler(int indexSpelerAanBeurt)
    {
        if(indexSpelerAanBeurt == 0)
            hoofdbalk.setLblScoreSpeler1("$ " + Integer.toString(dc.geefKredietSpeler()));
        else
            hoofdbalk.setLblScoreSpeler2("$ " + Integer.toString(dc.geefKredietSpeler()));
    }
    
    private void koopKaart()
    {
        String[][] teKopenKaarten = dc.geefBeschikbareKaarten();
        if(teKopenKaarten.length == 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(Taal.getWoordUitBundle("wedstrijdstapelvol"));
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(Taal.getWoordUitBundle("jehebtallekaartenalinbezit"));
            alert.show();
        }
        else
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(Taal.getWoordUitBundle("bentuzekerdatuverderwilgaan"));
            alert.setTitle(Taal.getWoordUitBundle("koopkaart"));
            alert.setContentText(Taal.getWoordUitBundle("drukokomverdertegaan"));
            Optional<ButtonType> antwoord = alert.showAndWait();
            try
            {
                    Scene s = this.getScene();
                    s.setRoot(new KoopKaartScherm(dc, t, taalnr, this));
            }
            catch(IllegalArgumentException e)
            {
                Alert alert3 = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
        
    }
}
