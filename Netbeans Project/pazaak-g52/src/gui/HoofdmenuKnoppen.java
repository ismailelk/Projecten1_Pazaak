package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import util.Taal;

public class HoofdmenuKnoppen extends VBox{
    private final HoofdmenuScherm parent;
    private final List<Button> menuKnoppen;
    private Taal t;
    private DomeinController dc;
    
    public HoofdmenuKnoppen(HoofdmenuScherm parent, Taal t, DomeinController dc){
        this.parent = parent;
        this.t = t;
        this.dc = dc;
        menuKnoppen = new ArrayList<>();
        
        Button btnMaakSpeler = new Button(Taal.getWoordUitBundle("optie1"));
        btnMaakSpeler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.maakSpeler();
            }
        });
        btnMaakSpeler.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    parent.maakSpeler();
                    }
                }
            
        });
        menuKnoppen.add(btnMaakSpeler);
        
        Button btnSpeelSpel = new Button(Taal.getWoordUitBundle("optie2"));
        btnSpeelSpel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.maakWedstrijd();
            }
        });
        btnSpeelSpel.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    parent.maakWedstrijd();
                    }
                }
            
        });
        menuKnoppen.add(btnSpeelSpel);
        
        Button btnLaadWedstrijd = new Button(Taal.getWoordUitBundle("optie3"));
        btnLaadWedstrijd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.laadWedstrijd();
            }
        });
        btnLaadWedstrijd.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    parent.laadWedstrijd();
                    }
                }
            
        });
        menuKnoppen.add(btnLaadWedstrijd);
           
        this.setSpacing(15);
        
        for(Button b: menuKnoppen){
            b.setPrefHeight(70);
            b.setPrefWidth(450);
            this.getChildren().add(b);
        }
    }
}
