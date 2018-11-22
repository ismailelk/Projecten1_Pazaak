/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Taal;

/**
 *
 * @author Jonathan
 */
public class LaadWedstrijdScherm extends BorderPane implements Scherm
{
    private final DomeinController dc;
    private int taalnr;
    private final Taal t;
    private final HoofdmenuScherm parent;
    private List<String> namenTeLadenWedstijden;
    private VBox vbCenter;
    private String geselecteerdeWedstrijd;
    private Button btnLaadWedstrijd;

    public LaadWedstrijdScherm(DomeinController dc, Taal t, int taalnr, HoofdmenuScherm parent)
    {
        this.dc = dc;
        this.t = t;
        this.parent = parent;
        this.taalnr = taalnr;
        t.kiesTaal(taalnr);
        namenTeLadenWedstijden = dc.geefNamenBestaandeWedstrijden();
        
        buildGui();
    }
    
    private void buildGui() {
        vbCenter = new VBox();
        ScrollPane sp = new ScrollPane(vbCenter);
        HBox hbKnoppen = new HBox();
        
        this.getStyleClass().add("bgimage");
        sp.getStyleClass().add("bgkader");
        // TODO: stijl toevoegen aan vbCenter
        
        sp.setMaxWidth(550);
        sp.setMaxHeight(500);
        sp.setPadding(new Insets(40));
        
        vulNamenWedstrijdenOp();
        
        btnLaadWedstrijd = new Button(Taal.getWoordUitBundle("optie3"));
        btnLaadWedstrijd.setDisable(true);
        Button btnTerug = new Button(Taal.getWoordUitBundle("Cancel"));
        hbKnoppen.getChildren().addAll(btnLaadWedstrijd, btnTerug);
        hbKnoppen.setSpacing(400);
        hbKnoppen.setAlignment(Pos.CENTER_RIGHT);
        hbKnoppen.setPadding(new Insets(20));
        
        btnLaadWedstrijd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                laadWedstrijd();
            }
        });
        
        btnTerug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gaTerug();
            }
        });
        
        this.setCenter(sp);
        this.setBottom(hbKnoppen);
        
        //opmaak
        vbCenter.getStyleClass().add("bgkader");
        vbCenter.setPrefWidth(500);
        vbCenter.setPadding(new Insets(20));
    }
    
    @Override
    public void refresh(){
        Scene s = this.getScene();
        s.setRoot(new MaakSpelerScherm(dc, t, taalnr, parent));
    }
    
    @Override
    public void setTaal(int taalnr){
        this.taalnr = taalnr;
    }
    
     private void gaTerug(){
        Scene s = this.getScene();
        s.setRoot(new HoofdmenuScherm(dc, taalnr, new KiesTaalScherm(dc)));
    }
    
    private void laadWedstrijd(){
        dc.laadWedstrijd(geselecteerdeWedstrijd);
        Scene s = this.getScene();
        s.setRoot(new SpeelWedstrijdScherm(dc, t, taalnr));
    }

    private void vulNamenWedstrijdenOp()
    {
        for(String naamWedstrijd: namenTeLadenWedstijden)
            vbCenter.getChildren().add(new Label(naamWedstrijd));
        
        for(Node n: vbCenter.getChildren())
        {
            (n).setOnMouseClicked(new EventHandler<MouseEvent>() 
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    geselecteerdeWedstrijd = ((Label)n).getText();
                    n.getStyleClass().add("geselecteerd");
                    ((Label)n).setPrefWidth(500);
                    btnLaadWedstrijd.setDisable(false);
                    removeSelected(n);
                }

            });

        }
    }
    
    private void removeSelected(Node gekozen){
        for(Node n: vbCenter.getChildren())
            if(!n.equals(gekozen))
                n.getStyleClass().remove("geselecteerd");
    }
}
