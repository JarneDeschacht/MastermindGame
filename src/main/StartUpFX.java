
package main;

import domein.DomeinController;
import gui.TaalKeuzeScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpFX extends Application {
    DomeinController dc = new DomeinController();
    @Override
    public void start(Stage primaryStage) {
        TaalKeuzeScherm root = new TaalKeuzeScherm(dc);
        
        Scene scene = new Scene(root, 450, 200);
        scene.getStylesheets().add("/css/style.css");
        
        primaryStage.setTitle("Mastermind");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
