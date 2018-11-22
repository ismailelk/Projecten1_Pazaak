/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import util.Taal;

/**
 *
 * @author Renaat
 */
public class Hoofdbalk extends HBox implements Scherm
{
    private final Scherm parent;
    private final OptieMenu om;
    private Label lblSpeler1, lblSpeler2, lblScoreSpeler1, lblScoreSpeler2, lblTitel;
    private ImageView imgBeurtSpeler1, imgBeurtSpeler2, imgGewSetsSpeler1, imgGewSetsSpeler2;
    
    public Hoofdbalk(Scherm parent)
    {
        this.parent = parent;
        
        lblSpeler1 = new Label();
        lblSpeler2 = new Label();
        lblScoreSpeler1 = new Label();
        lblScoreSpeler2 = new Label();
        imgBeurtSpeler1 = new ImageView(new Image(getClass().getResourceAsStream("/images/beurtSpeler1.png")));
        imgBeurtSpeler2 = new ImageView(new Image(getClass().getResourceAsStream("/images/beurtSpeler2.png")));
        imgGewSetsSpeler1 = new ImageView();
        imgGewSetsSpeler2 = new ImageView();
        om = new OptieMenu(this);
        lblTitel = new Label();
        
        
        buildGui();
    }

    public void setTitel(String titel) {
        this.lblTitel.setText(titel);
    }

    Hoofdbalk(Scherm parent, Map<String, String[]> wedstrijdSituatie) {
        this(parent);
        this.getChildren().remove(om);
        
        stelHoofdingIn(wedstrijdSituatie);        
    }
    
    private void buildGui()
    {      
        imgBeurtSpeler1.setFitHeight(30);
        imgBeurtSpeler1.setFitWidth(45);
                
        imgGewSetsSpeler1.setFitHeight(25);
        imgGewSetsSpeler1.setFitWidth(53);
            
        imgGewSetsSpeler2.setFitHeight(25);
        imgGewSetsSpeler2.setFitWidth(53);

        imgBeurtSpeler2.setFitHeight(30);
        imgBeurtSpeler2.setFitWidth(45);
        
        lblSpeler1.setPrefWidth(150);
        lblSpeler1.setAlignment(Pos.CENTER);
        lblSpeler2.setPrefWidth(150);
        lblSpeler1.setAlignment(Pos.CENTER);        
        lblTitel.setPrefWidth(400);
        lblTitel.setAlignment(Pos.CENTER);
        
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(7));
        
        this.getChildren().addAll(imgBeurtSpeler1, lblScoreSpeler1, imgGewSetsSpeler1, lblSpeler1, lblTitel, 
                lblSpeler2, imgGewSetsSpeler2, lblScoreSpeler2, imgBeurtSpeler2, om);
        
    }

    @Override
    public void refresh()
    {
        parent.refresh();
    }

    @Override
    public void setTaal(int taalnr)
    {
        parent.setTaal(taalnr);
    }
    
    public void setImgBeurtSpeler1(boolean beurt)
    {
        imgBeurtSpeler1.setVisible(beurt);
    }
    
    public void setLblScoreSpeler1(String score)
    {
        lblScoreSpeler1.setText(score);
    }
    
    public void visibilityImgGewSetsSpeler1(boolean vlag)
    {
        imgGewSetsSpeler1.setVisible(vlag);
    }
    
    public void visibilityImgGewSetsSpeler2(boolean vlag)
    {
        imgGewSetsSpeler2.setVisible(vlag);
    }
    
    public void setImgGewSetsSpeler1(int aantalGewSets)
    {
        if(aantalGewSets == 0)
            imgGewSetsSpeler1.setImage(new Image(getClass().getResourceAsStream("/images/0Set.png")));
        else
            if(aantalGewSets == 1)
                imgGewSetsSpeler1.setImage(new Image(getClass().getResourceAsStream("/images/1Set.png")));
            else
                imgGewSetsSpeler1.setImage(new Image(getClass().getResourceAsStream("/images/2Set.png")));           
    }
    
    public void setLblSpeler1(String naam)
    {
        lblSpeler1.setText(naam);
    }
    
    public void setLblSpeler2(String naam)
    {
        lblSpeler2.setText(naam);
    }
    
    public void setImgGewSetsSpeler2(int aantalGewSets)
    {
        if(aantalGewSets == 0)
            imgGewSetsSpeler2.setImage(new Image(getClass().getResourceAsStream("/images/0Set.png")));
        else
            if(aantalGewSets == 1)
                imgGewSetsSpeler2.setImage(new Image(getClass().getResourceAsStream("/images/1Set.png")));
            else
                imgGewSetsSpeler2.setImage(new Image(getClass().getResourceAsStream("/images/2Set.png"))); 
    }
    
    public void setLblScoreSpeler2(String score)
    {
        lblScoreSpeler2.setText(score);
    }
    
    public void setImgBeurtSpeler2(boolean beurt)
    {
        imgBeurtSpeler2.setVisible(beurt);
    }

    private void stelHoofdingIn(Map<String, String[]> wedstrijdSituatie) {
        String[] NaamSetScoreEnSpelerAanBeurtSpeler1 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler0");
        String[] NaamSetScoreEnSpelerAanBeurtSpeler2 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler1");
        
        lblSpeler1.setText(NaamSetScoreEnSpelerAanBeurtSpeler1[0]);
        lblSpeler2.setText(NaamSetScoreEnSpelerAanBeurtSpeler2[0]);
        lblScoreSpeler1.setText(NaamSetScoreEnSpelerAanBeurtSpeler1[4]);
        lblScoreSpeler2.setText(NaamSetScoreEnSpelerAanBeurtSpeler2[4]);
        
        this.setImgBeurtSpeler1(Boolean.parseBoolean(NaamSetScoreEnSpelerAanBeurtSpeler1[2]));
        this.setImgBeurtSpeler2(Boolean.parseBoolean(NaamSetScoreEnSpelerAanBeurtSpeler2[2]));
        this.setImgGewSetsSpeler1(Integer.parseInt(NaamSetScoreEnSpelerAanBeurtSpeler1[1]));
        this.setImgGewSetsSpeler2(Integer.parseInt(NaamSetScoreEnSpelerAanBeurtSpeler2[1]));
    }

    public void pasScoreAan(Map<String, String[]> wedstrijdSituatie) {
        String[] NaamSetScoreEnSpelerAanBeurtSpeler1 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler0");
        String[] NaamSetScoreEnSpelerAanBeurtSpeler2 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler1");
        lblScoreSpeler1.setText(NaamSetScoreEnSpelerAanBeurtSpeler1[4]);
        lblScoreSpeler2.setText(NaamSetScoreEnSpelerAanBeurtSpeler2[4]);
    }

    public void veranderSetsWinnaar(Map<String, String[]> wedstrijdSituatie) {
        String[] NaamSetScoreEnSpelerAanBeurtSpeler1 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler0");
        String[] NaamSetScoreEnSpelerAanBeurtSpeler2 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler1");
        this.setImgGewSetsSpeler1(Integer.parseInt(NaamSetScoreEnSpelerAanBeurtSpeler1[1]));
        this.setImgGewSetsSpeler2(Integer.parseInt(NaamSetScoreEnSpelerAanBeurtSpeler2[1]));
    }

    public void stelBeurtImgIn(Map<String, String[]> wedstrijdSituatie) {
        String[] NaamSetScoreEnSpelerAanBeurtSpeler1 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler0");
        String[] NaamSetScoreEnSpelerAanBeurtSpeler2 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler1");
        this.setImgBeurtSpeler1(Boolean.parseBoolean(NaamSetScoreEnSpelerAanBeurtSpeler1[2]));
        this.setImgBeurtSpeler2(Boolean.parseBoolean(NaamSetScoreEnSpelerAanBeurtSpeler2[2]));
    }

    public void bevriesSpeler(Map<String, String[]> wedstrijdSituatie) {
        String[] NaamSetScoreEnSpelerAanBeurtSpeler1 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler0");
        String[] NaamSetScoreEnSpelerAanBeurtSpeler2 = wedstrijdSituatie.get("NaamSetScoreEnSpelerAanBeurtSpeler1");
        if(Boolean.parseBoolean(NaamSetScoreEnSpelerAanBeurtSpeler1[3])){
            lblSpeler1.getStyleClass().add("bgkader");
            lblSpeler1.getStyleClass().add("zwarteTekst");
        }
        if(Boolean.parseBoolean(NaamSetScoreEnSpelerAanBeurtSpeler2[3])){
            lblSpeler2.getStyleClass().add("bgkader");
            lblSpeler2.getStyleClass().add("zwarteTekst");
        }
    }
    
    public void verwijderOM(){
        this.getChildren().remove(om);
    }
}
