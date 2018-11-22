package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import util.Taal;

public class Kaart extends VBox{

    private String omschrijving;
    private Label lblOmschrijving;
    
    public Kaart(String omschrijving) 
    {
        this.omschrijving = omschrijving;
        buildGui();
    }

    public Kaart(int achterkant) {
        lblOmschrijving = new Label("");
        omschrijving = Integer.toString(achterkant);
        
        this.setSpacing(5);
        this.setPadding(new Insets(3));
        this.setPrefWidth(100);
        this.setPrefHeight(145);
        this.setMinHeight(145);
        lblOmschrijving.setPrefWidth(100);
        lblOmschrijving.setAlignment(Pos.CENTER);
        
        this.setAlignment(Pos.CENTER);
        
        stelAchtergrondIn(achterkant);
                
        this.getChildren().add(lblOmschrijving);
    }
    
    private void buildGui()
    {
        lblOmschrijving = new Label(omschrijving);
        
        this.setSpacing(5);
        this.setPadding(new Insets(3));
        this.setPrefWidth(100);
        this.setPrefHeight(145);
        this.setMinHeight(145);
        lblOmschrijving.setPrefWidth(100);
        lblOmschrijving.setAlignment(Pos.CENTER);
        
        this.setAlignment(Pos.CENTER);
        
        stelAchtergrondIn();
                
        this.getChildren().add(lblOmschrijving);
    }
    
    public String getOmschrijving()
    {
        return omschrijving;
    }
    
    public void setOmschrijving(String naam)
    {
        lblOmschrijving.setText(naam);
    }

    private void stelAchtergrondIn() {
        this.getStyleClass().add("kaart");
        for(int i=0; i<omschrijving.length(); i++)
            if(omschrijving.charAt(i) == '/')
                this.getStyleClass().add("kaart-plusmin");
        if(omschrijving.length()==2)
        {
            if(omschrijving.charAt(0)=='+')
                this.getStyleClass().add("kaart-plus");
            if(omschrijving.charAt(0)=='-')
                this.getStyleClass().add("kaart-min");
            if(omschrijving.equals("10"))
                this.getStyleClass().add("kaart-gewoon");
            else
                if(omschrijving.equals("1T"))
                    this.getStyleClass().add("kaart-spec");
        }
        else
        {
            if(omschrijving.equals(""))
                this.getStyleClass().add("kaart-leeg");
            else
            {
                if(!omschrijving.equals("D") && omschrijving.length()==1)
                {
                    this.setSpacing(1);
                    this.setPadding(new Insets(0));
                    this.getStyleClass().add("kaart-gewoon");
                }
                else
                {
                    this.setSpacing(1);
                    this.setPadding(new Insets(0));
                    this.getStyleClass().add("kaart-spec");
                }
            }
        }
    }

    private void stelAchtergrondIn(int achterkant) {
        this.getStyleClass().add("kaart");
        this.getStyleClass().add("kaart-achterkant"+omschrijving);
    }
    
    public String geefUitlegKaart(String omschrijving)
    {
        String uitleg = "";
        switch(omschrijving)
        {
            case "+1": case "+2":case "+3": case "+4": case "+5": case "+6": uitleg = Taal.getWoordUitBundle("uitlegplus");break;
            case "-1": case "-2":case "-3": case "-4": case "-5": case "-6": uitleg = Taal.getWoordUitBundle("uitlegmin");break;
            case "+1/-1": case "+2/-2":case "+3/-3": case "+4/-4": case "+5/-5": case "+6/-6": uitleg = Taal.getWoordUitBundle("uitlegplusmin");break;
            case "1T": uitleg = Taal.getWoordUitBundle("uitleg1t");break;
            case "D": uitleg = Taal.getWoordUitBundle("uitlegD");break;
            case "2&4": uitleg = Taal.getWoordUitBundle("uitleg2&4"); break;
            case "3&6": uitleg = Taal.getWoordUitBundle("uitleg3&6"); break;
            case "1+/-2": uitleg = Taal.getWoordUitBundle("uitlegsp");break;
        }
        return uitleg;
    }    
}
