package gui;

import domein.DomeinController;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import util.Taal;

public class KiesTaalScherm extends VBox implements Scherm{
    private final DomeinController dc;
    private int taal;
    private final Taal t;
    private final TaalKnoppen tk;

    public KiesTaalScherm(DomeinController dc) {
        t = new Taal();
        t.kiesTaal(1);
        this.dc = dc;
        tk = new TaalKnoppen(this);
        buildGui();
    }
    
    public void buildGui(){
        tk.setPadding(new Insets(40));
        this.getChildren().addAll(tk);
        tk.setMaxWidth(450);
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.F5)){
                    refresh();
                }
            }
            
        });
        
        this.setPadding(new Insets(300, 750, 100, 100));
        this.getStyleClass().add("bgimage");
    }
    
    public void goToMenu(){
        Scene s = this.getScene();
        s.setRoot(new HoofdmenuScherm(dc, taal, this));
    }
 
    @Override
    public void setTaal(int taal) {
        this.taal = taal;
    }
    
    public void sluitScherm(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(Taal.getWoordUitBundle("Zekeromaftesluiten"));
        alert.setTitle(Taal.getWoordUitBundle("Afsluiten"));
        alert.setContentText(Taal.getWoordUitBundle("Drukokomaftesluiten"));
        Optional<ButtonType> antwoord = alert.showAndWait();
        if(antwoord.get() == ButtonType.OK)
            Platform.exit();
    }

    @Override
    public void refresh() {
        Scene s = this.getScene();
        s.setRoot(new KiesTaalScherm(dc));
    }
}
