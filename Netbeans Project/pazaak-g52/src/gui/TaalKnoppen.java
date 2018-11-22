package gui;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaalKnoppen extends VBox{
    private KiesTaalScherm parent;
    private List<ImageView> taalKnoppen;
    
    TaalKnoppen(KiesTaalScherm parent) {
        this.parent = parent;
        
        List<String> talen = new ArrayList<>();
        taalKnoppen = new ArrayList<>();
        
        talen.add("NL");
        talen.add("FR");
        talen.add("EN");
        
        taalKnoppen = new ArrayList<>();
        
        //voeg hieronder nieuwe talen toe
        for(String taal: talen){
            taalKnoppen.add(new ImageView(new Image("/images/vlag" + taal + ".jpg", 75, 50, false, false)));
        }
                
        Label lbl = new Label("taal / langue / language");
        HBox taal = new HBox();
        
        for(ImageView b: taalKnoppen){
            b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    kiesTaal(b);
                    parent.goToMenu();
                }
            });
            b.setOnKeyPressed(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)){
                        kiesTaal((ImageView)event.getSource());
                        parent.goToMenu();
                        event.consume();
                    }
                }
            
            });
            taal.getChildren().add(b);
            taal.setSpacing(40);
        }
        
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(lbl, taal);
        this.getStyleClass().add("bgkader");
        taal.setAlignment(Pos.CENTER);
    }
    
    private void kiesTaal(ImageView b){
        parent.setTaal(taalKnoppen.indexOf(b) + 1);
    }
}
