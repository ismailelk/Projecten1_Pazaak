package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SpelerAvatar extends VBox{

    private String naamSpeler;
    private ImageView imgSpeler;
    private Label lblNaam;
    
    public SpelerAvatar(String naamSpeler) 
    {
        this.naamSpeler = naamSpeler;
        imgSpeler = new ImageView(new Image(getClass().getResourceAsStream("/images/Avatar.png")));
        buildGui();
    }
    
    private void buildGui()
    {
        lblNaam = new Label(naamSpeler);
        
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        imgSpeler.setFitHeight(60);
        imgSpeler.setFitWidth(60);
        this.setAlignment(Pos.CENTER);
        
        this.getChildren().addAll(imgSpeler, lblNaam);
        this.setPrefWidth(150);
        this.setMaxWidth(150);
        
        //css
        lblNaam.getStyleClass().add("label-avatar");
        this.getStyleClass().add("bgkader");
    }
    
    public String getNaam()
    {
        return naamSpeler;
    }
    
    public void setNaam(String naam)
    {
        lblNaam.setText(naam);
    }
    
    
}
