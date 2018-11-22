/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import util.Taal;

/**
 *
 * @author Renaat
 */
public class Zijbalk extends VBox
{
    private SpelerAvatar speler;
    private Label lblText;
    
    public Zijbalk()
    {
        lblText = new Label();
        speler = new SpelerAvatar("");  
        
        buildGui();
    }
    
    private void buildGui()
    {   
        lblText.setTextFill(Paint.valueOf("2ecaf7"));
        this.getChildren().addAll(lblText);
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(5);
        this.setPadding(new Insets(7));
        this.setPrefWidth(200);
    }
    
    public void setTitle(String tekst)
    {
        lblText.setText(tekst);
    }
    
    public void setTextAvatar(String text)
    {
        speler.setNaam(text);
    }
    
    public void voegSpelerAvatarToe(SpelerAvatar speler)
    {
        this.getChildren().addAll(speler);
    }    
}
