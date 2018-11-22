/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.KoopKaartException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Taal;

/**
 *
 * @author Renaat
 */
public class KoopKaartScherm extends BorderPane implements Scherm
{
    private final DomeinController dc;
    private int taalnr;
    private final Taal t;
    private final Scherm parent;
    private Hoofdbalk hoofdbalk;
    private final List<Button> knoppen;
    //private List<String> namenSpelersZW;
    private String[][] teKopenKaarten;
    private Kaart gekozenKaart;
    private HBox center;
    private GridPane gpUitleg, gpKaarten;
    private int indexGekozenKaart;
    
    public KoopKaartScherm(DomeinController dc, Taal t, int taalnr, Scherm parent){
        this.dc = dc;
        this.parent = parent;
        this.t = t;
        this.taalnr = taalnr;
        
        t.kiesTaal(taalnr);
        
        
        knoppen = new ArrayList<>();
        this.hoofdbalk = new Hoofdbalk(this);
        hoofdbalk.verwijderOM();
        //namenSpelersZW = dc.geefNamenSpelersVoorWedstrijdZonderWedstrijdstapel();
        gpUitleg = new GridPane();
        gpKaarten = new GridPane();
        center = new HBox();
        indexGekozenKaart = 0;
        
        buildGui();
    }
    
    private void buildGui()
    {
        ScrollPane sp = new ScrollPane(gpKaarten);
        
        //Opvullen
        vulKaartenOp(gpKaarten);
        center.getChildren().addAll(sp, gpUitleg);
        
        //Declaratie
        HBox knoppenbalk = new HBox();
        Button btnTerug = new Button(Taal.getWoordUitBundle("gaterug"));
        Button btnKoopKaart = new Button(Taal.getWoordUitBundle("koopkaart"));
        
        
        //Buttons
        btnKoopKaart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                koopKaart(gekozenKaart);    
            }
        });
        btnKoopKaart.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))   
                    koopKaart(gekozenKaart);
            }
        });
        knoppen.add(btnKoopKaart);
        
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
        gpUitleg.setAlignment(Pos.CENTER);
        gpUitleg.setHgap(20);
        gpUitleg.setVgap(10);
        gpUitleg.setPadding(new Insets(15));
        gpUitleg.setPrefWidth(500);
        gpUitleg.setMaxHeight(400);
        gpUitleg.getStyleClass().add("bgkader");
        gpUitleg.getColumnConstraints().add(new ColumnConstraints(180));
        gpUitleg.setAlignment(Pos.CENTER);
        
        gpKaarten.setHgap(20);
        gpKaarten.setVgap(20);
        
        sp.setMinWidth(550);        
        sp.setMaxHeight(400);
        
        center.setSpacing(40);
        center.setPadding(new Insets(30));
        center.setAlignment(Pos.CENTER);
        
        this.getStyleClass().add("bgimage-notext");
        
        //hoofdbalk
        String [] infoSpeler = dc.geefInfoSpeler();
        hoofdbalk.setLblSpeler1(infoSpeler[0]);
        hoofdbalk.setLblScoreSpeler1("$ " + infoSpeler[1]);
        ((Label)hoofdbalk.getChildren().get(4)).setText(Taal.getWoordUitBundle("ditzijndetekopenkaartenuheeft"));
        hoofdbalk.setImgBeurtSpeler1(true);
        hoofdbalk.setImgBeurtSpeler2(false);
        
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
              
        //EventHandlers
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.F5))
                    refresh();
            }
        });
        
        this.setCenter(center);
        this.setTop(hoofdbalk);
        this.setBottom(knoppenbalk);
    }

    @Override
    public void refresh()
    {
        dc.clearSelectie();
        Scene s = this.getScene();
        s.setRoot(new KoopKaartScherm(dc, t, taalnr, (KiesWedstrijdstapelScherm) parent));
    }

    @Override
    public void setTaal(int taalnr)
    {
        this.taalnr = taalnr;
    }

    private void gaTerug()
    {
            dc.clearSelectie();
            Scene s = this.getScene();
            s.setRoot((Parent) parent);
            parent.refresh();
    }    
    
    private void koopKaart(Kaart gekozenKaart)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(Taal.getWoordUitBundle("bentuzekerdatudezekaartwilkopen"));
        alert.setTitle(Taal.getWoordUitBundle("koopkaart"));
        alert.setContentText(Taal.getWoordUitBundle("drukokomdekaarttekopen"));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if(antwoord.get() == ButtonType.OK)
        {
            try
            {
                String[] infoSpeler = dc.geefInfoSpeler();
                if(Integer.parseInt(infoSpeler[1]) >= Integer.parseInt(teKopenKaarten[indexGekozenKaart][1]))
                {
                    dc.koopKaart(gekozenKaart.getOmschrijving());
                    refresh();
                }
                else
                    throw new IllegalArgumentException(Taal.getWoordUitBundle("uheeftonvoldoendekredietomdegewenstekaarttekopen"));
            }
            catch(KoopKaartException ex)
            {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle(Taal.getWoordUitBundle("onvoldoendekrediet"));
                alert2.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
                alert2.setContentText(ex.getMessage());
                alert2.showAndWait();
            }
            catch(IllegalArgumentException e)
            {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle(Taal.getWoordUitBundle("onvoldoendekrediet"));
                alert3.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
                alert3.setContentText(e.getMessage());
                alert3.showAndWait();
            }            
        }
    }

    private void vulKaartenOp(GridPane gpKaarten) 
    {        
        teKopenKaarten = dc.geefBeschikbareKaarten();
        List<Kaart> kaarten = new ArrayList<>();
        for(int i=0; i<teKopenKaarten.length; i++)
        {
            kaarten.add(new Kaart(teKopenKaarten[i][0]));
            gpKaarten.add(kaarten.get(i), i%4, i/4);;
        }     
        
        for(Kaart kaart: kaarten)
        {
            kaart.setOnMouseClicked(new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    try
                    {
                        indexGekozenKaart = kaarten.indexOf(kaart);
                        gekozenKaart = kaart;
                        geefUitleg(kaart);
                    }
                    catch(IndexOutOfBoundsException e)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(Taal.getWoordUitBundle("wedstrijdstapelvol"));
                        alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
                        alert.setContentText(Taal.getWoordUitBundle("dewedstrijdstapelzitalvol"));
                        alert.show();
                    }
                }
            }); 
        }
    }
    
    private void geefUitleg(Kaart kaart)
    {
        gpUitleg.getChildren().clear();
        gpUitleg.add(new Label(Taal.getWoordUitBundle("omschrijving")), 0, 0);
        gpUitleg.add(new Label(Taal.getWoordUitBundle("prijs")), 0, 1);
        gpUitleg.add(new Label(Taal.getWoordUitBundle("definitie")), 0, 2);//Nog in taalproperty verwerken
        gpUitleg.add(new Label(teKopenKaarten[indexGekozenKaart][0]), 1, 0);
        gpUitleg.add(new Label(teKopenKaarten[indexGekozenKaart][1]), 1, 1);
        Label lblUitlegKaart = new Label(kaart.geefUitlegKaart(teKopenKaarten[indexGekozenKaart][0]));
        lblUitlegKaart.setWrapText(true);
        gpUitleg.add(lblUitlegKaart, 1, 2);
    }   
}
