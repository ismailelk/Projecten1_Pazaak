package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.Taal;

public class MaakSpelerScherm extends BorderPane implements Scherm{
    private final DomeinController dc;
    private int taalnr;
    private final Taal t;
    private final HoofdmenuScherm parent;
    private final List<TextField> txtInvoer;
    
    public MaakSpelerScherm(DomeinController dc, Taal t, int taalnr, HoofdmenuScherm parent) {
        this.dc = dc;
        this.t = t;
        this.parent = parent;
        this.taalnr = taalnr;
        t.kiesTaal(taalnr);
        txtInvoer = new ArrayList<>();
        
        buildGui();
    }

    private void buildGui() {
        GridPane gp = new GridPane();
        HBox hbKnoppen = new HBox();
        txtInvoer.add(new TextField());
        txtInvoer.add(new TextField());
        
        this.getStyleClass().add("bgimage");
        gp.setAlignment(Pos.CENTER);
        gp.getStyleClass().add("bgkader");
        gp.setMaxWidth(600);
        gp.setMaxHeight(200);
        gp.setPadding(new Insets(40));
        
        gp.add(new Label(Taal.getWoordUitBundle("naam")), 0, 0);
        gp.add(new Label(Taal.getWoordUitBundle("geboortejaar")), 0, 1);
        gp.add(txtInvoer.get(0), 1, 0);
        gp.add(txtInvoer.get(1), 1, 1);
        
        gp.setHgap(20);
        gp.setVgap(20);
        gp.getColumnConstraints().add(new ColumnConstraints(200));
        gp.getColumnConstraints().add(new ColumnConstraints(370));
        
        Button btnMaakSpeler = new Button(Taal.getWoordUitBundle("Maakspeler"));
        Button btnTerug = new Button(Taal.getWoordUitBundle("Cancel"));
        hbKnoppen.getChildren().addAll(btnMaakSpeler, btnTerug);
        hbKnoppen.setSpacing(400);
        hbKnoppen.setAlignment(Pos.CENTER_RIGHT);
        hbKnoppen.setPadding(new Insets(20));
        
        btnMaakSpeler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                maakSpeler();
            }
        });
        
        btnTerug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gaTerug();
            }
        });
        
        for(TextField txt: txtInvoer){
        txt.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    maakSpeler();
                    event.consume();
                }
                if(event.getCode().equals(KeyCode.TAB)){
                    int indexVolgende = (txtInvoer.indexOf(txt)+1) % txtInvoer.size();
                    txtInvoer.get(indexVolgende).selectAll();
                    txtInvoer.get(indexVolgende).requestFocus();
                    event.consume();
                }
                if (event.getCode().equals(KeyCode.F5)){
                    refresh();
                }
            }
            
        });
        }
                
        this.setCenter(gp);
        this.setBottom(hbKnoppen);
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
    
    private void maakSpeler(){
        try{
            probeerEenSpelerTeMaken();
            gaTerug();
        }catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(Taal.getWoordUitBundle("Speleraanmaken"));
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(String.format("%s", Taal.getWoordUitBundle("Geefeennummerin")));

            alert.showAndWait();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(Taal.getWoordUitBundle("Speleraanmaken"));
            alert.setHeaderText(Taal.getWoordUitBundle("Mislukt"));
            alert.setContentText(String.format("%s", e.getMessage()));

            alert.showAndWait();
        }
    }
    
    private void probeerEenSpelerTeMaken(){
        String naam = txtInvoer.get(0).getText().trim();
        int jaar = Integer.parseInt(txtInvoer.get(1).getText());
        
        dc.maakSpelerAan(naam, jaar);
        String outputKaarten = "";
        String[] outputSpeler = dc.geefInfoSpeler();
        for(int i=2; i<outputSpeler.length; i++){
            outputKaarten += String.format("%10s%n", outputSpeler[i]);;
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(Taal.getWoordUitBundle("Speleraangemaakt"));
        alert.setHeaderText(Taal.getWoordUitBundle("Gelukt"));
        alert.setContentText(String.format("%s %s %s %n%s", Taal.getWoordUitBundle("Speler") , naam, Taal.getWoordUitBundle("Isaangemaaktmetdevolgendekaarten"), outputKaarten));
        
        alert.showAndWait();
    }

}
