package gui;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import util.Taal;

public class OptieMenu extends HBox{
    private final Image imgMenu;
    private final ImageView iv1;
    private final Scherm parent;
    
    public OptieMenu(Scherm parent){
        this.parent = parent;
        
        imgMenu = new Image("/images/cog.png", 30, 30, false, false);
        iv1 = new ImageView();
        iv1.setImage(imgMenu);
        
        buildOptionMenu();
        
        this.setPadding(new Insets(10));
      
        this.getChildren().add(iv1);
        this.setAlignment(Pos.CENTER_RIGHT);
    }

    private void buildOptionMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem taalKeuze = new MenuItem(Taal.getWoordUitBundle("veranderdetaal"));
        taalKeuze.setDisable(true);
        taalKeuze.getStyleClass().add("menu-title");
        List<MenuItem> taalKeuzes = new ArrayList<>();
        List<String> talen = Taal.getTalen();
        for(String taal: talen){
            taalKeuzes.add(new MenuItem(taal));
        }
        contextMenu.getItems().add(taalKeuze);
        
        for(MenuItem m: taalKeuzes){
            m.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    parent.setTaal(taalKeuzes.indexOf(m)+1);
                    parent.refresh();
                }
            });
            contextMenu.getItems().add(m);
        }
        
        iv1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown()) {
                    contextMenu.show(iv1, event.getScreenX(), event.getScreenY());
                }
            }
        });
    }

    
    
}
