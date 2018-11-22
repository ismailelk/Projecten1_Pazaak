package main;

import domein.DomeinController;
import gui.KiesTaalScherm;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartUpFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        KiesTaalScherm kt = new KiesTaalScherm(new DomeinController());
        Scene scene = new Scene(kt, 1250, 750);
        scene.getStylesheets().add("css/style.css");
        primaryStage.setTitle("Run - Pazaak G52");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                kt.sluitScherm();
                event.consume();
            }
            
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
