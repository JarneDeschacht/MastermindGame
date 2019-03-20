/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import domein.Vertaler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginEnRegistreerScherm extends GridPane {

    private Label lblWelkom, lblNaam, lblWachtwoord, lblError, lblBevestig;
    private TextField txfNaam;
    private PasswordField pwfWachtwoord, pwfBevestig;
    private Button btnLogin, btnRegistreer, btnTerug;
    private HoofdMenuScherm hoofdmenu;

    public LoginEnRegistreerScherm(DomeinController dc) {
        buildGuiLogin(dc);
    }
    
    public void clearScherm()
    {
        this.getChildren().clear();
    }

    private void buildGuiLogin(DomeinController dc) {
        clearScherm();
        lblWelkom = new Label(Vertaler.vertaalString("aanmelden"));
        lblWelkom.setMaxWidth(Double.MAX_VALUE);
        lblWelkom.setFont(Font.font(35));
        lblWelkom.setAlignment(Pos.CENTER);
        
        
        lblNaam = new Label(Vertaler.vertaalString("gebruikersnaam"));
        lblNaam.setFont(Font.font("Arial", FontWeight.BOLD, USE_PREF_SIZE));
        
        lblWachtwoord = new Label(Vertaler.vertaalString("wachtwoord"));
        lblWachtwoord.setFont(Font.font("Arial", FontWeight.BOLD, USE_PREF_SIZE));
        
        lblError = new Label(Vertaler.vertaalString("inloggenMislukt"));
        lblError.setTextFill(Color.web("#ff0000"));

        txfNaam = new TextField();
        txfNaam.setMaxWidth(USE_PREF_SIZE);
        pwfWachtwoord = new PasswordField();        
        pwfWachtwoord.setMaxWidth(USE_PREF_SIZE);

        btnLogin = new Button(Vertaler.vertaalString("aanmelden"));
        btnRegistreer = new Button(Vertaler.vertaalString("registreren"));

        this.setPadding(new Insets(15));
        this.setVgap(10);
        this.setHgap(10);

        this.add(lblWelkom, 0, 0, 4, 1);
        this.add(lblNaam, 0, 1, 4, 1);
        this.add(txfNaam, 0, 2, 3, 1);
        this.add(lblWachtwoord, 0, 3, 4, 1);
        this.add(pwfWachtwoord, 0, 4, 3, 1);
        this.add(lblError, 0, 5, 4, 1);
        this.add(btnLogin, 0, 6);
        this.add(btnRegistreer, 1, 6);

        lblError.setVisible(false);
        btnLogin.setDefaultButton(true);
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    dc.meldAan(txfNaam.getText(), pwfWachtwoord.getText());
                    lblError.setVisible(false);
                    lblWelkom.setText(Vertaler.vertaalString("ingelogd"));
                    hoofdmenu = new HoofdMenuScherm(dc);
                    Scene scene = new Scene(hoofdmenu, 1000, 600);
                    Stage stage = (Stage) getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } 
                catch (IllegalArgumentException e) {
                    lblError.setText(e.getMessage());
                    lblError.setVisible(true);
                    resetLogin();
                }
                catch(NullPointerException npe)
                {
                    lblError.setText(Vertaler.vertaalString("inloggenMislukt"));
                    lblError.setVisible(true);
                    resetLogin();
                }
            }
        });
        btnRegistreer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buildGuiRegistreer(dc);
            }
        });
    }

    private void buildGuiRegistreer(DomeinController dc) {
        clearScherm();
        lblWelkom.setText(Vertaler.vertaalString("registreren"));
        
        lblBevestig = new Label(Vertaler.vertaalString("bevestigWachtwoord"));
        lblBevestig.setFont(Font.font("Arial", FontWeight.BOLD, USE_PREF_SIZE));
        pwfBevestig = new PasswordField();
        btnTerug = new Button("terug");

        txfNaam = new TextField();
        pwfWachtwoord = new PasswordField();

        this.setPadding(new Insets(15));
        this.setVgap(10);
        this.setHgap(10);

        this.add(lblWelkom, 0, 0, 4, 1);
        this.add(lblNaam, 0, 1, 4, 1);
        this.add(txfNaam, 0, 2, 3, 1);
        this.add(lblWachtwoord, 0, 3, 4, 1);
        this.add(pwfWachtwoord, 0, 4, 3, 1);
        this.add(lblBevestig, 0, 5, 4, 1);
        this.add(pwfBevestig, 0, 6, 3, 1);
        this.add(lblError, 0, 7, 4, 1);
        this.add(btnRegistreer, 0, 8);
        this.add(btnTerug, 10, 8);
        
        btnTerug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                buildGuiLogin(dc);
            }
        
        });

        lblError.setVisible(false);
        btnRegistreer.setDefaultButton(true);
        btnRegistreer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (dc.controleerWachtwoord(pwfWachtwoord.getText())) {
                        lblError.setVisible(false);
                        if (pwfBevestig.getText().equals(pwfWachtwoord.getText())) {
                            dc.registreer(txfNaam.getText(), pwfWachtwoord.getText());
                            lblWelkom.setText(Vertaler.vertaalString("ingelogd"));
                            hoofdmenu = new HoofdMenuScherm(dc);
                            Scene scene = new Scene(hoofdmenu, 1000, 600);
                            Stage stage = (Stage) getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            lblError.setText(Vertaler.vertaalString("nietDezelfdeWachtwoorden"));
                            lblError.setVisible(true);
                            resetRegistreer();
                        }
                    } else {
                        lblError.setText(Vertaler.vertaalString("instructieWachtwoord"));
                        lblError.setVisible(true);
                       resetRegistreer();
                    }
                } 
                catch (IllegalArgumentException e) {
                    lblError.setText(e.getMessage());
                    lblError.setVisible(true);
                    resetRegistreer();
                }
                
           }
        });
    }
    private void resetLogin() {
        txfNaam.clear();
        pwfWachtwoord.clear();
    }
    private void resetRegistreer() {
        txfNaam.clear();
        pwfWachtwoord.clear();
        pwfBevestig.clear();
    }
}
