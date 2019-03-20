package gui;

import domein.DomeinController;
import domein.Vertaler;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TaalKeuzeScherm extends GridPane {

    private Label lblWelkom, lblTaal;
    //private Button btnBE, btnUK, btnFR;
    private ImageView imgBE,imgUK,imgFR;

    public TaalKeuzeScherm(DomeinController dc) {
        buildGui(dc);
    }
    
    private void buildGui(DomeinController dc) {
        this.getChildren().clear();
        imgBE = new ImageView(new Image(getClass().getResourceAsStream("/images/vlagBelgie.jpg")));
        imgBE.setFitWidth(100);
        imgBE.setFitHeight(100);
        imgBE.getStyleClass().add("vlag");
        
        imgUK = new ImageView(new Image(getClass().getResourceAsStream("/images/vlagUK.jpg")));
        imgUK.setFitWidth(100);
        imgUK.setFitHeight(100);
        
        imgFR = new ImageView(new Image(getClass().getResourceAsStream("/images/vlagFrankrijk.jpg")));
        imgFR.setFitWidth(100);
        imgFR.setFitHeight(100);
        
        
        lblTaal = new Label("Kies je taal/Choose your language/Choisissez votre langue");
        lblTaal.setPadding(new Insets(10, 0, 10, 30));
        
        lblWelkom = new Label("Mastermind");
        lblWelkom.setMaxWidth(Double.MAX_VALUE);
        lblWelkom.setAlignment(Pos.CENTER);
        lblWelkom.setTextFill(Color.web("#0099ff"));
        lblWelkom.setFont(Font.font("Arial", FontWeight.BOLD, 35));

        this.add(lblWelkom, 0, 0,3,1);
        this.add(lblTaal, 0, 1, 3, 1);
        this.add(imgBE, 0, 2);
        this.add(imgUK, 1, 2);
        this.add(imgFR, 2, 2);

        imgBE.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                ResourceBundle taal = ResourceBundle.getBundle("taal.Taal", new Locale("nl", "BE"));
                new Vertaler(taal);
                LoginEnRegistreerScherm loginScherm = new LoginEnRegistreerScherm(dc);
                Scene scene = new Scene(loginScherm, 400, 350);
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });
        
        imgUK.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                ResourceBundle taal = ResourceBundle.getBundle("taal.Taal",new Locale("en","US"));
                new Vertaler(taal);
                LoginEnRegistreerScherm loginScherm = new LoginEnRegistreerScherm(dc);
                Scene scene = new Scene(loginScherm, 400, 350);
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });
        
        imgFR.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                ResourceBundle taal = ResourceBundle.getBundle("taal.Taal",new Locale("fr","BE"));
                new Vertaler(taal);
                LoginEnRegistreerScherm loginScherm = new LoginEnRegistreerScherm(dc);
                Scene scene = new Scene(loginScherm,400, 350);
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });
    }
}
