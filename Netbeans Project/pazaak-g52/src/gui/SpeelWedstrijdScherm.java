/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.WedstrijdBestaatAlException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Taal;

/**
 *
 * @author Renaat
 */
public class SpeelWedstrijdScherm extends BorderPane implements Scherm {

    private final DomeinController dc;
    private int taalnr;
    private final Taal t;
    private Hoofdbalk hoofdbalk;
    private final List<Button> knoppen;
    private Zijbalk zijbalkRechts, zijbalkLinks;
    private final VBox vbMenuKnoppen;
    private String[] namenSpelers;
    private Map<String, String[]> wedstrijdSituatie;
    private int ACHTERGROND_SPELER1, ACHTERGROND_SPELER2;
    private TextField txfNaam;

    public SpeelWedstrijdScherm(DomeinController dc, Taal t, int taalnr) {
        this.dc = dc;
        this.t = t;
        this.taalnr = taalnr;

        t.kiesTaal(taalnr);

        dc.startNieuweSet();
        wedstrijdSituatie = dc.geefWedstrijdSituatie();

        knoppen = new ArrayList<>();
        hoofdbalk = new Hoofdbalk(this, wedstrijdSituatie);
        namenSpelers = new String[2];
        vbMenuKnoppen = new VBox();
        zijbalkRechts = new Zijbalk();
        zijbalkLinks = new Zijbalk();

        //achtergrond kaarten
        SecureRandom sr = new SecureRandom();
        ACHTERGROND_SPELER1 = sr.nextInt(6) + 1;
        do {
            ACHTERGROND_SPELER2 = sr.nextInt(6) + 1;
        } while (ACHTERGROND_SPELER1 == ACHTERGROND_SPELER2);

        buildGui();
        if (dc.isWedstrijdTenEinde()) {
            toonWinnaar();
        }
    }

    private void buildGui() {
        //kaarten
        String[] wedstrijdStapelSpeler1 = wedstrijdSituatie.get("wedstrijdStapelSpeler0");
        String[] wedstrijdStapelSpeler2 = wedstrijdSituatie.get("wedstrijdStapelSpeler1");

        //clear zijbalk
        zijbalkLinks.getChildren().clear();
        zijbalkRechts.getChildren().clear();

        Kaart k;
        for (String omschrijving : wedstrijdStapelSpeler1) {
            k = new Kaart(ACHTERGROND_SPELER1);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            zijbalkLinks.getChildren().add(k);
        }
        for (String omschrijving : wedstrijdStapelSpeler2) {
            k = new Kaart(ACHTERGROND_SPELER2);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            zijbalkRechts.getChildren().add(k);
        }

        //menuknoppen
        Button btnSpeelSet = new Button(Taal.getWoordUitBundle("speelset"));
        btnSpeelSet.setPrefWidth(250);
        btnSpeelSet.setPrefHeight(50);
        btnSpeelSet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                speelSet();
            }
        });
        btnSpeelSet.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    speelSet();
                }
            }
        });
        vbMenuKnoppen.getChildren().add(btnSpeelSet);

        Button btnSlaWedstrijdOp = new Button(Taal.getWoordUitBundle("opslaan"));
        btnSlaWedstrijdOp.setPrefWidth(250);
        btnSlaWedstrijdOp.setPrefHeight(50);
        btnSlaWedstrijdOp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toonOpslaanScherm();
            }
        });
        btnSlaWedstrijdOp.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    toonOpslaanScherm();
                }
            }
        });
        vbMenuKnoppen.getChildren().add(btnSlaWedstrijdOp);

        Button btnStop = new Button(Taal.getWoordUitBundle("stop"));
        btnStop.setPrefWidth(250);
        btnStop.setPrefHeight(50);
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gaNaarHoofdmenu();
            }
        });
        btnStop.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    gaNaarHoofdmenu();
                }
            }
        });
        vbMenuKnoppen.getChildren().add(btnStop);

        //declaratie BorderPane
        this.setTop(hoofdbalk);
        this.setLeft(zijbalkLinks);
        this.setRight(zijbalkRechts);
        this.setCenter(vbMenuKnoppen);

        //opmaak
        this.getStyleClass().add("bgimage-notext");
        vbMenuKnoppen.setAlignment(Pos.CENTER);
        vbMenuKnoppen.setSpacing(20);
        vbMenuKnoppen.setMaxSize(800, 200);
    }

    @Override
    public void refresh() {
        Scene s = this.getScene();
        s.setRoot(new SpeelWedstrijdScherm(dc, t, taalnr));
    }

    @Override
    public void setTaal(int taalnr) {
        this.taalnr = taalnr;
    }

    private void speelSet() {
        Scene s = this.getScene();
        s.setRoot(new SpeelSetScherm(dc, t, taalnr, this, zijbalkLinks, zijbalkRechts, wedstrijdSituatie, ACHTERGROND_SPELER1, ACHTERGROND_SPELER2));
    }

    private void toonOpslaanScherm() {
        this.vbMenuKnoppen.getChildren().clear();//scherm leeggemaakt
        HBox hbOpslaan = new HBox();
        Label lbNaam = new Label(Taal.getWoordUitBundle("naam"));
        txfNaam = new TextField();
        txfNaam.setPrefWidth(400);
        hbOpslaan.getChildren().addAll(lbNaam, txfNaam);
        Button btnOpslaan = new Button(Taal.getWoordUitBundle("opslaan"));
        Button btnTerug = new Button(Taal.getWoordUitBundle("Cancel"));
        vbMenuKnoppen.getChildren().addAll(hbOpslaan);
        HBox knoppen = new HBox();
        knoppen.getChildren().addAll(btnOpslaan, btnTerug);
        knoppen.setAlignment(Pos.CENTER);
        knoppen.setSpacing(20);
        vbMenuKnoppen.getChildren().add(knoppen);
        this.vbMenuKnoppen.getStyleClass().add("bgkader");
        hbOpslaan.setAlignment(Pos.CENTER);
        hbOpslaan.setSpacing(20);
        vbMenuKnoppen.setSpacing(30);
        vbMenuKnoppen.setMaxWidth(700);

        btnOpslaan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                slaWedstrijdOp();
            }
        });
        btnOpslaan.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    slaWedstrijdOp();
                }
            }
        });
        btnTerug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gaNaarSpeelWedstrijdScherm();
            }
        });
        btnTerug.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    gaNaarSpeelWedstrijdScherm();
                }
            }
        });

    }

    private void slaWedstrijdOp() {
        //TODO ISMAIL wordt aangeroepen bij 2de maal klik op OPSLAAN KNOP
        try {
            String naam = txfNaam.getText().trim();
            dc.bewaarWedstrijd(naam);
            Scene s = this.getScene();
            s.setRoot(new SpeelWedstrijdScherm(dc, t, taalnr));
            dc.wisselSpelerAanBeurtEnSpelerNietAanBeurt();
        } catch (WedstrijdBestaatAlException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Taal.getWoordUitBundle("geefeenuniekenaamvoorwedstrijd"));
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(String.format("%s", e.getMessage()));
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Taal.getWoordUitBundle("geefeenuniekenaamvoorwedstrijd"));
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(String.format("%s", e.getMessage()));
            alert.showAndWait();
        }
    }

    private void gaNaarHoofdmenu() {
        Scene s = this.getScene();
        s.setRoot(new HoofdmenuScherm(dc, taalnr, new KiesTaalScherm(dc)));
    }
    
        private void gaNaarSpeelWedstrijdScherm() {
        Scene s = this.getScene();
        s.setRoot(new SpeelWedstrijdScherm(dc, t, taalnr));
    }

    private void toonWinnaar() {
        dc.verhoogKredietWinnaarMetVijf();
        this.vbMenuKnoppen.getChildren().remove(0);
        this.vbMenuKnoppen.getChildren().remove(0);
        String winnaarLabel = String.format(Taal.getWoordUitBundle("isgewonnenenkrediet"), dc.geefNaamWinnaar(), dc.geefKredietWinnaar());
        this.vbMenuKnoppen.getChildren().add(0, new Label(winnaarLabel));
        ((Button) this.vbMenuKnoppen.getChildren().get(1)).setText(Taal.getWoordUitBundle("gaterug"));
        this.vbMenuKnoppen.getStyleClass().add("bgkader");
    }
}
