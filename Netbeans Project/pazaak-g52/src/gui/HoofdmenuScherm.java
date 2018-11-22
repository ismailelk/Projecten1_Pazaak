package gui;

import domein.DomeinController;
import exceptions.LaadWedstrijdException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import util.Taal;

public class HoofdmenuScherm extends BorderPane implements Scherm{
    private final DomeinController dc;
    private int taalnr;
    private final Taal t;
    private final KiesTaalScherm parent;
    private HoofdmenuKnoppen mk;
    
    public HoofdmenuScherm(DomeinController dc, int taal, KiesTaalScherm parent) {
        this.dc = dc;
        this.t = new Taal();
        taalnr = taal;
        t.kiesTaal(taalnr);
        this.parent = parent;
        mk = new HoofdmenuKnoppen(this, t, dc);
        buildGui();
        try{
            dc.geefNamenBestaandeWedstrijden();
        }
        catch(LaadWedstrijdException e){
            mk.getChildren().get(2).setDisable(true);
        }
    }
    
    private void buildGui(){
        mk.setAlignment(Pos.CENTER);
        this.setCenter(mk);
        this.setTop(new OptieMenu(this));
        this.getStyleClass().add("bgimage");
        
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.F5))
                    refresh();
            }
        });
    }
    
    @Override
    public void refresh(){
        Scene s = this.getScene();
        s.setRoot(new HoofdmenuScherm(dc, taalnr , (KiesTaalScherm) parent));
    }
    
    @Override
    public void setTaal(int taalnr){
        this.taalnr = taalnr;
    }
    
    public void maakSpeler(){
        Scene s = this.getScene();
        s.setRoot(new MaakSpelerScherm(dc, t, taalnr , this));
    }
    
    public void maakWedstrijd(){
        Scene s = this.getScene();
        s.setRoot(new KiesSpelersScherm(dc, t, taalnr, this));
    }
    
    public void laadWedstrijd(){
        Scene s = this.getScene();
        s.setRoot(new LaadWedstrijdScherm(dc, t, taalnr, this));
    }
    
 }
