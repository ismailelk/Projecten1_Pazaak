package gui;

import static cui.Cui.kiesMenuOptie;
import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Taal;

public class SpeelSetScherm extends BorderPane implements Scherm {
    private final DomeinController dc;
    private int taalnr;
    private final Taal t;
    private Hoofdbalk hoofdbalk;
    private final List<Button> knoppen;
    private  Zijbalk zijbalkRechts, zijbalkLinks;
    private String[] namenSpelers;
    private Map<String,String[]> wedstrijdSituatie;
    private final int ACHTERGROND_SPELER1, ACHTERGROND_SPELER2;
    private final SpeelWedstrijdScherm parent;
    private final GridPane spelbordSpeler1, spelbordSpeler2;
    private final HBox spelbord;
    private Button btnGaTerug;
    private HBox hbKnoppen;

    public SpeelSetScherm(DomeinController dc, Taal t, int taalnr, SpeelWedstrijdScherm parent, Zijbalk zijbalkLinks, Zijbalk zijbalkRechts, Map<String,String[]> wedstrijdSituatie, int achtergrond_speler1, int achtergrond_speler2) {
        this.dc = dc;
        this.t = t;
        this.taalnr = taalnr;
        this.parent = parent;
        this.zijbalkLinks = zijbalkLinks;
        this.zijbalkRechts = zijbalkRechts;
        this.wedstrijdSituatie = wedstrijdSituatie;
        hoofdbalk = new Hoofdbalk(this, wedstrijdSituatie);
        
        knoppen = new ArrayList<>();
        spelbordSpeler1 = new GridPane();
        spelbordSpeler2 = new GridPane();
        spelbord = new HBox();
        
        //achtergrond kaarten
        this.ACHTERGROND_SPELER1 = achtergrond_speler1;
        this.ACHTERGROND_SPELER2 = achtergrond_speler2;
        
        buildGui();
        
        speelBeurt();
    }
    
    private void buildGui() {
        //spelbord speler 1
        for(int i=0; i<9; i++){
            Kaart k = new Kaart("");
            spelbordSpeler1.add(k, i%3, i/3);
        }
        
        //spelbord speler 2
        for(int i=0; i<9; i++){
            Kaart k = new Kaart("");
            spelbordSpeler2.add(k, i%3, i/3);
        }
        
        //spelbord
        spelbord.getChildren().addAll(spelbordSpeler1, spelbordSpeler2);
        
        //knoppen
        Button btnEindeBeurt = new Button(Taal.getWoordUitBundle("eindebeurt"));
        btnEindeBeurt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eindeBeurt();
            }
        });
        btnEindeBeurt.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){   
                    eindeBeurt();
                }
            }
        });
        knoppen.add(btnEindeBeurt);
        
        Button btnBevries = new Button(Taal.getWoordUitBundle("bevries"));
        btnBevries.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bevriesSpelbord();
                eindeBeurt();
            }
        });
        btnBevries.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){   
                    bevriesSpelbord();
                    eindeBeurt();
                }
            }
        });
        knoppen.add(btnBevries);
        
        btnGaTerug = new Button(Taal.getWoordUitBundle("gaterug"));
        btnGaTerug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gaTerug();
            }
        });
        btnGaTerug.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){   
                    gaTerug();
                }
            }
        });
        
        hbKnoppen = new HBox();
        for(Button b: knoppen){
            b.setPrefWidth(250);
            hbKnoppen.getChildren().add(b);            
        }
        
        //declaratie BorderPane
        this.setTop(hoofdbalk);
        this.setLeft(zijbalkLinks);
        this.setRight(zijbalkRechts);
        this.setCenter(spelbord);
        this.setBottom(hbKnoppen);
        
        //opmaak
        this.getStyleClass().add("bgimage-notext");
        spelbord.getStyleClass().add("bgkader");
        spelbord.setPadding(new Insets(40));
        spelbord.setAlignment(Pos.CENTER);
        spelbord.setSpacing(40);
        spelbordSpeler1.setHgap(10);
        spelbordSpeler2.setHgap(10);
        spelbordSpeler1.setVgap(10);
        spelbordSpeler2.setVgap(10);
        spelbord.setMaxHeight(400);
        hbKnoppen.setSpacing(50);
        hbKnoppen.setAlignment(Pos.CENTER);
        hbKnoppen.setPadding(new Insets(20));
        
    }
    
    private void speelBeurt(){
        if(!dc.isSetTenEinde()){
            dc.geefSpelerNieuweSetKaartOpSpelbord();
            wedstrijdSituatie = dc.geefWedstrijdSituatie();
            hoofdbalk.stelBeurtImgIn(wedstrijdSituatie);
            enableHandkaarten();
            pasScoreAan();
            toonKaarten();
            if(spelbordIsVol()){
                disableHandkaarten();
                toonWinnaar();
                knoppen.get(1).setDisable(true);
                hbKnoppen.getChildren().set(0, btnGaTerug);
            }
            
            // BOT LOGICA
            if(dc.isSpelerAanBeurtEenAI()){
                dc.speelBeurt();
                if(dc.isSpelbordSpelerNietAanBeurtBevroren()==false){
                    dc.wisselSpelerAanBeurtEnSpelerNietAanBeurt();
                }
                disableHandkaarten();
                speelBeurt();
            }
        }
        else{
            wedstrijdSituatie = dc.geefWedstrijdSituatie();
            pasScoreAan();
            toonKaarten();
            disableHandkaarten();
            toonWinnaar();
            knoppen.get(1).setDisable(true);
            hbKnoppen.getChildren().set(0, btnGaTerug);
        }
    }
    
    @Override
    public void refresh() {
        Scene s = this.getScene();
        s.setRoot(new SpeelSetScherm(dc, t, taalnr, parent, zijbalkLinks, zijbalkRechts, wedstrijdSituatie, this.ACHTERGROND_SPELER1, this.ACHTERGROND_SPELER2));
    }

    @Override
    public void setTaal(int taalnr) {
        this.taalnr = taalnr;
    }

    private void pasScoreAan() {
        hoofdbalk.pasScoreAan(wedstrijdSituatie);
    }

    private void toonKaarten() {
        wedstrijdSituatie = dc.geefWedstrijdSituatie();
        String[] kaartenSpeler1 = wedstrijdSituatie.get("spelbordSpeler0");
        String[] kaartenSpeler2 = wedstrijdSituatie.get("spelbordSpeler1");
                
        for(int i=0; i<kaartenSpeler1.length; i++){
            spelbordSpeler1.add(new Kaart(kaartenSpeler1[i]), i%3, i/3);
        }
        for(int i=0; i<kaartenSpeler2.length; i++){
            spelbordSpeler2.add(new Kaart(kaartenSpeler2[i]), i%3, i/3);
        }
    }

    private void toonWinnaar() {
        String naamWinnaar = dc.geefUitSlagSet();
        wedstrijdSituatie = dc.geefWedstrijdSituatie();
        if(naamWinnaar.equals("=")){
            hoofdbalk.setTitel(Taal.getWoordUitBundle("gelijkspel"));
        }
        else{
            dc.verhoogAantalGewonnenSetsMetEen(naamWinnaar);
            wedstrijdSituatie = dc.geefWedstrijdSituatie();
            hoofdbalk.setTitel(naamWinnaar + Taal.getWoordUitBundle("heeftgewonnen"));
            hoofdbalk.veranderSetsWinnaar(wedstrijdSituatie);
        }
    }
    
    private void eindeBeurt(){
        if(dc.isSpelbordSpelerNietAanBeurtBevroren()==false){
            dc.wisselSpelerAanBeurtEnSpelerNietAanBeurt();
        }
        disableHandkaarten();
        speelBeurt();
    }
    
    private void enableHandkaarten(){
        if(wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler0")[2].equals("true")){
            zetLinkerbalkOpActief();
        }
        else
            if(wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler1")[2].equals("true"))
                zetRechterbalkOpActief();
    }
    
    private void disableHandkaarten(){
        wedstrijdSituatie = dc.geefWedstrijdSituatie();
        String[] wedstrijdStapelSpeler1 = wedstrijdSituatie.get("wedstrijdStapelSpeler0");
        String[] wedstrijdStapelSpeler2 = wedstrijdSituatie.get("wedstrijdStapelSpeler1");
        
        //clear zijbalk
        zijbalkLinks.getChildren().clear();
        zijbalkRechts.getChildren().clear();
        
        Kaart k;
        for(String omschrijving: wedstrijdStapelSpeler1){
            k = new Kaart(ACHTERGROND_SPELER1);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            zijbalkLinks.getChildren().add(k);
        }
        for(String omschrijving: wedstrijdStapelSpeler2){
            k = new Kaart(ACHTERGROND_SPELER2);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            zijbalkRechts.getChildren().add(k);
        }
    }

    private void zetLinkerbalkOpActief() {
        zijbalkLinks.getChildren().clear();
        String[] wedstrijdStapelSpeler1 = wedstrijdSituatie.get("wedstrijdStapelSpeler0");
        Kaart k;
        for(String omschrijving: wedstrijdStapelSpeler1){
            k = new Kaart(omschrijving);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            k.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event){
                            if(kiesHandkaart(omschrijving)==0)
                                return;                           
                            removeMouseEventKaartenLinks();   
                        }
                    });
            zijbalkLinks.getChildren().add(k);
        }
    }
    
    private void zetRechterbalkOpActief() {
        zijbalkRechts.getChildren().clear();
        String[] wedstrijdStapelSpeler2 = wedstrijdSituatie.get("wedstrijdStapelSpeler1");
        Kaart k;
        for(String omschrijving: wedstrijdStapelSpeler2){
            k = new Kaart(omschrijving);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            k.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event){
                            if(kiesHandkaart(omschrijving)==0)
                                return;
                            removeMouseEventKaartenRechts();   
                        }
                    });
            zijbalkRechts.getChildren().add(k);
        }
    }
    
    private void removeMouseEventKaartenLinks(){
        zijbalkLinks.getChildren().clear();
        String[] wedstrijdStapelSpeler1 = wedstrijdSituatie.get("wedstrijdStapelSpeler0");
        Kaart k;
        for(String omschrijving: wedstrijdStapelSpeler1){
            k = new Kaart(omschrijving);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            k.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event){
                            
                        }
                    });
            zijbalkLinks.getChildren().add(k);
        }
    }
    
    private void removeMouseEventKaartenRechts(){
        zijbalkRechts.getChildren().clear();
        String[] wedstrijdStapelSpeler2 = wedstrijdSituatie.get("wedstrijdStapelSpeler1");
        Kaart k;
        for(String omschrijving: wedstrijdStapelSpeler2){
            k = new Kaart(omschrijving);
            k.setMaxHeight(145);
            k.setMaxWidth(100);
            k.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event){                            
                        }
                    });
            zijbalkRechts.getChildren().add(k);
        }
    }
    
    private void gaTerug(){
        Scene s = this.getScene();
        s.setRoot(new SpeelWedstrijdScherm(dc, t, taalnr));
    }
    
    private void bevriesSpelbord(){
        dc.bevriesSpelbordSpelerAanBeurt();
        wedstrijdSituatie = dc.geefWedstrijdSituatie();
        hoofdbalk.bevriesSpeler(wedstrijdSituatie);
        disableHandkaarten();
    }
    
    private int kiesHandkaart(String omschrijving){        
        try{
            if(!dc.geefOmschrijvingWisselKaarten(omschrijving).isEmpty()){
                List<String> wisselKaarten = dc.geefOmschrijvingWisselKaarten(omschrijving);
                String gekozenOmschrijvingWisselKaart = kiesWisselKaart(wisselKaarten);
                if(gekozenOmschrijvingWisselKaart.equals("0")) 
                    return 0;
                dc.voegKaartToeAanSpelbord(gekozenOmschrijvingWisselKaart, omschrijving);
            }
            else
                dc.voegKaartToeAanSpelbord(omschrijving);
            wedstrijdSituatie = dc.geefWedstrijdSituatie();
            toonKaarten();
            pasScoreAan();
            }
            catch(IndexOutOfBoundsException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Taal.getWoordUitBundle("wedstrijdstapelvol"));
                alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
                alert.setContentText(Taal.getWoordUitBundle("dewedstrijdstapelzitalvol"));
                alert.show();
            }
            return 1;                 
    }

    private String kiesWisselKaart(List<String> wisselKaarten) {
        String omschrijving = "";
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(Taal.getWoordUitBundle("wisselkaart"));
        alert.setHeaderText(Taal.getWoordUitBundle("kies_toepassing"));
        alert.setContentText(Taal.getWoordUitBundle("welkewaardewiltuaandewisselkaarttoekennen"));
        
        alert.getButtonTypes().clear();
        
        for(String s: wisselKaarten){
            alert.getButtonTypes().add(new ButtonType(s));
        }
        
        ButtonType buttonTypeCancel = new ButtonType(Taal.getWoordUitBundle("cancel"), ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().add(buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        for(int i=0; i<wisselKaarten.size(); i++){
            if(result.get().getText().equals(wisselKaarten.get(i)))
                omschrijving = wisselKaarten.get(i);
        }
        if(result.get().equals(buttonTypeCancel))
            omschrijving = "0";
        return omschrijving;
    }

    private boolean spelbordIsVol() {
        boolean isVol = false;
        if(wedstrijdSituatie.get("spelbordSpeler0").length == 9)
            isVol = true;
        else
            if(wedstrijdSituatie.get("spelbordSpeler1").length == 9)
                isVol = true;
        return isVol;
    }
}
