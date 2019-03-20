package gui;

import domein.DomeinController;
import domein.Vertaler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import ui.ApplicatieStartSpel;

public class HoofdMenuScherm extends GridPane
{
    //Test
    private Label lblWelkom, lblStartSpelError;
    private Button btnStartSpel, btnLaadSpel, btnDaagUit, btnSpeelUitdaging, btnKlassement, btnExit;
    private Scene scene;
    private final DomeinController dc;

    public HoofdMenuScherm(DomeinController dc)
    {
        this.dc = dc;
        buildGuiHoofdMenu(dc);
    }

    private void buildGuiHoofdMenu(DomeinController dc)
    {        
        this.setPadding(new Insets(15));
        this.setVgap(10);
        this.setHgap(10);

        lblWelkom = new Label(Vertaler.vertaalString("welkom") + " " + dc.geefGebruikersnaam());
        lblWelkom.setMaxWidth(Double.MAX_VALUE);
        lblWelkom.setFont(Font.font(50));
        lblWelkom.setTextFill(Color.web("#0099ff"));

        lblStartSpelError = new Label("Kies een waarde");
        btnStartSpel = new Button(Vertaler.vertaalString("startSpel"));
        btnLaadSpel = new Button(Vertaler.vertaalString("laadSpel"));
        btnDaagUit = new Button(Vertaler.vertaalString("daagUit"));
        btnSpeelUitdaging = new Button(Vertaler.vertaalString("uitgedaagdDoor"));
        btnKlassement = new Button(Vertaler.vertaalString("klassement"));
        btnExit = new Button("exit");

        //voorlopig werkt enkel btnExit en btnKlassement
        //de rest voorlopig uitzetten
        //btnLaadSpel.setDisable(true);
        //btnDaagUit.setDisable(true);
        //btnSpeelUitdaging.setDisable(true);
        ButtonOpmaak(btnStartSpel);
        ButtonOpmaak(btnLaadSpel);
        ButtonOpmaak(btnDaagUit);
        ButtonOpmaak(btnSpeelUitdaging);
        ButtonOpmaak(btnKlassement);
        ButtonOpmaak(btnExit);

        this.add(lblWelkom, 0, 0, 4, 1);
        this.add(btnStartSpel, 0, 4);
        this.add(btnLaadSpel, 0, 7);
        this.add(btnDaagUit, 0, 10);
        this.add(btnSpeelUitdaging, 0, 13);
        this.add(btnKlassement, 0, 16);
        this.add(btnExit, 0, 19);
    }

    public void clearScherm()
    {
        this.getChildren().clear();
        buildGuiHoofdMenu(dc);
    }

    // methode om alle handlers en opmaak in te doen
    // veel minder duplicatie van code aangezien we 6 dezelfde knoppen hebben
    public void ButtonOpmaak(Button naamButton)
    {
        naamButton.setMinWidth(350);
        naamButton.setMaxWidth(350);
        naamButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        naamButton.setTextAlignment(TextAlignment.CENTER);
        naamButton.setCursor(Cursor.HAND);
        naamButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                String naam = naamButton.getText();
                enableButtons();
                //kijken welke knop is aangeklikt
                if (naam.equals(Vertaler.vertaalString("klassement")))
                {
                    naamButton.setDisable(true);
                    KlassementScherm klassement = new KlassementScherm(dc);
                    scene = new Scene(klassement, 1000, 600);
                    Stage stage = (Stage) getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else if (naam.equals(Vertaler.vertaalString("startSpel")))
                {
                    clearScherm();
                    buildGuiStartSpel();
                    naamButton.setDisable(true);
                } else if (naam.equals(Vertaler.vertaalString("laadSpel")))
                {
                    clearScherm();
                    buildGuiLaadSpel();
                    naamButton.setDisable(true);
                } else if (naam.equals(Vertaler.vertaalString("daagUit")))
                {
                    clearScherm();
                    buildGuiDaagUit();
                    naamButton.setDisable(true);
                } else if (naam.equals(Vertaler.vertaalString("uitgedaagdDoor")))
                {
                    clearScherm();
                    buildGuiUitgedaagdDoor();
                    naamButton.setDisable(true);
                } else if (naam.equals("exit"))
                {
                    System.exit(0);
                }

            }

        });
    }

    private void enableButtons()
    {
        btnDaagUit.setDisable(false);
        btnExit.setDisable(false);
        btnKlassement.setDisable(false);
        btnLaadSpel.setDisable(false);
        btnSpeelUitdaging.setDisable(false);
        btnStartSpel.setDisable(false);
    }

    private void buildGuiStartSpel()
    {
        ComboBox cboMoeilijkheidsgraad = new ComboBox();
        cboMoeilijkheidsgraad.setPromptText("--- " + Vertaler.vertaalString("keuzeMoeilijkheidsgraad") + " ---");
        String[] mgraden = dc.geefMoeilijkheidsgraden();
        int[] wins = dc.geefAantalKeerGewonnen();
        int loop = 1;

        if (wins[0] >= 20)
        {
            loop++;
            if (wins[1] >= 20)
            {
                loop++;
            }
        }

        String[] moeilijkheidsgraden = new String[loop];

        for (int i = 0; i < loop; i++)
        {
            moeilijkheidsgraden[i] = String.format("%s (%d wins)", mgraden[i], wins[i]);
        }
        ObservableList items = FXCollections.observableArrayList(moeilijkheidsgraden);
        cboMoeilijkheidsgraad.setItems(items);

        Label lblMoeilijkheidsgraad = new Label(Vertaler.vertaalString("keuzeMoeilijkheidsgraad"));
        Button btnSubmit = new Button(Vertaler.vertaalString("submit"));

        btnSubmit.setOnAction((ActionEvent event)
                ->
        {
            //moeilijkheidsgraad uit de cbo halen
            try
            {
                String waarde = cboMoeilijkheidsgraad.getValue().toString().split(" ")[0];
                lblStartSpelError.setVisible(false);
                dc.maakNieuwSpel(waarde);

                SpelbordSchermController spelbord = new SpelbordSchermController(dc, this);
                scene = new Scene(spelbord, 1000, 600);
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e)
            {
                lblStartSpelError.setVisible(true);
            }

        });

        lblStartSpelError.setTextFill(Color.web("#FF0000"));
        this.add(lblMoeilijkheidsgraad, 5, 4);
        this.add(cboMoeilijkheidsgraad, 5, 7);
        this.add(btnSubmit, 5, 10);
        this.add(lblStartSpelError, 6, 7);
        lblStartSpelError.setVisible(false);
    }

    private void buildGuiLaadSpel()
    {
        String[][] spelletjes = dc.geefSpelletjes();
        if (spelletjes.length == 0)
        {
            Label lblSpelen = new Label(Vertaler.vertaalString("geenOpgeslagenSpelen"));
            this.add(lblSpelen, 5, 4);
        } else
        {
            ComboBox cboSpelen = new ComboBox();
            Label lblSpelen = new Label(Vertaler.vertaalString("opgeslagenSpelen"));
            Button btnSubmit = new Button(Vertaler.vertaalString("submit"));

            String[] spelen = new String[spelletjes.length];
            int i = 0;
            for (String[] spel : spelletjes)
            {
                spelen[i] = spel[0] + ": " + Vertaler.vertaalString(spel[1]);
                i++;
            }

            ObservableList items = FXCollections.observableArrayList(spelen);
            cboSpelen.setItems(items);

            HoofdMenuScherm scherm = this;
            btnSubmit.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    int indexSpel = cboSpelen.getSelectionModel().getSelectedIndex();
                    String waarde = spelletjes[indexSpel][0];
                    dc.kiesSpel(waarde);

                    SpelbordSchermController spelbord = new SpelbordSchermController(dc, scherm);
                    scene = new Scene(spelbord, 1000, 600);
                    Stage stage = (Stage) getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }

            });

            this.add(lblSpelen, 5, 4);
            this.add(cboSpelen, 5, 7);
            this.add(btnSubmit, 5, 10);
        }

    }

    private String[][] spelers;

    private void buildGuiDaagUit()
    {
        Label lblSpelers = new Label();
        ComboBox cboMoeilijkheidsgraad = new ComboBox();
        cboMoeilijkheidsgraad.setPromptText("--- " + Vertaler.vertaalString("keuzeMoeilijkheidsgraad") + " ---");
        Label lblErrorSpeler = new Label();
        ComboBox cboSpelers = new ComboBox();
        cboSpelers.setPromptText("--- " + Vertaler.vertaalString("kiesSpeler") + " ---");
        String[] mgraden = dc.geefMoeilijkheidsgraden();
        cboSpelers.setVisible(false);
        Label lblGeenSpelers = new Label(Vertaler.vertaalString("geenTegenstanders"));
        lblGeenSpelers.setVisible(false);

        int[] wins = dc.geefAantalKeerGewonnen();
        int loop = 1;

        if (wins[0] >= 20)
        {
            loop++;
            if (wins[1] >= 20)
            {
                loop++;
            }
        }

        String[] moeilijkheidsgraden = new String[loop];

        for (int i = 0; i < loop; i++)
        {
            moeilijkheidsgraden[i] = String.format("%s (%d %s)", mgraden[i], wins[i], Vertaler.vertaalString("gewonnenSpelen"));
        }
        ObservableList items = FXCollections.observableArrayList(moeilijkheidsgraden);
        cboMoeilijkheidsgraad.setItems(items);

        Label lblMoeilijkheidsgraad = new Label(Vertaler.vertaalString("daagUitMoeilijkheidsgraad"));
        Button btnSubmit = new Button(Vertaler.vertaalString("submit"));
        btnSubmit.setVisible(false);

        cboMoeilijkheidsgraad.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                int index = cboMoeilijkheidsgraad.getSelectionModel().getSelectedIndex();
                spelers = dc.geefBeschikbareSpelers(mgraden[index]);
                if(spelers.length == 0)
                {
                    lblGeenSpelers.setVisible(true);
                    cboSpelers.setVisible(false);
                    btnSubmit.setVisible(false);
                }
                else
                {
                    String[] arraySpelers = new String[spelers.length];
                    int i = 0;
                    for (String[] s : spelers)
                    {
                        arraySpelers[i] = String.format("%s: %s: %s", s[0], Vertaler.vertaalString("gewonnenSpelen"), s[1]);
                        i++;
                    }
                    cboSpelers.setItems(FXCollections.observableArrayList(arraySpelers));
                    lblGeenSpelers.setVisible(false);
                    cboSpelers.setVisible(true);
                    btnSubmit.setVisible(false);
                }
                
            }

        });

        cboSpelers.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                btnSubmit.setVisible(true);
            }
        });

        HoofdMenuScherm scherm = this;
        btnSubmit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                dc.kiesSpeler(spelers[cboSpelers.getSelectionModel().getSelectedIndex()][0], mgraden[cboMoeilijkheidsgraad.getSelectionModel().getSelectedIndex()]);

                SpelbordSchermController spelbord = new SpelbordSchermController(dc, scherm);
                scene = new Scene(spelbord, 1000, 600);
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        });

        this.add(lblMoeilijkheidsgraad, 5, 4);
        this.add(cboMoeilijkheidsgraad, 5, 7);
        this.add(cboSpelers, 5, 10);
        this.add(lblGeenSpelers, 5, 10);
        this.add(btnSubmit, 5, 13);
    }

    private void buildGuiUitgedaagdDoor()
    {
        String[][] uitdagingen = dc.geefUitdagingenSpeler();
        String[] stringUitdagingen = new String[uitdagingen.length];
        String onafgewerkteOpgeslagenUitdaging = dc.onafgewerkteOpgeslagenUitdaging();

        if (uitdagingen.length == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(Vertaler.vertaalString("geenUitdagingenGevonden"));
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (!onafgewerkteOpgeslagenUitdaging.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(Vertaler.vertaalString("opgeslagenUitdaging") + " " + onafgewerkteOpgeslagenUitdaging);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else
        {

            int i = 0;
            for (String[] uitdaging : uitdagingen)
            {
                stringUitdagingen[i] = String.format("%s, %s(%s wins)", uitdaging[0], uitdaging[1], uitdaging[2]);
                i++;
            }

            Button btnSubmit = new Button(Vertaler.vertaalString("submit"));
            btnSubmit.setVisible(false);
            Label lblUitdaging = new Label(Vertaler.vertaalString("beschikbareUitdagingen"));
            ComboBox cboUitdaging = new ComboBox();
            ObservableList items = FXCollections.observableArrayList(stringUitdagingen);
            cboUitdaging.setItems(items);

            cboUitdaging.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    btnSubmit.setVisible(true);
                }

            });

            HoofdMenuScherm scherm = this;
            btnSubmit.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    dc.kiesUitdaging(uitdagingen[cboUitdaging.getSelectionModel().getSelectedIndex()][0]);
                    SpelbordSchermController spelbord = new SpelbordSchermController(dc, scherm);
                    scene = new Scene(spelbord, 1000, 600);
                    Stage stage = (Stage) getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }

            });

            this.add(lblUitdaging, 5, 4);
            this.add(cboUitdaging, 5, 7);
            this.add(btnSubmit, 5, 13);
        }

    }
}
