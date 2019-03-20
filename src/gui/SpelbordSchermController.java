/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import domein.Vertaler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javax.swing.text.View;

/**
 * FXML Controller class
 *
 * @author Jarne
 */
public class SpelbordSchermController extends GridPane
{

    @FXML
    private Label lblTitel;

    @FXML
    private Label lblOplossing;
    @FXML
    private Label lblPoging;
    @FXML
    private Button btnSlaOp;

    private Label lblSpeel;
    private Label lblEvaluatie;
    private ImageView[][] imgSpeelpinnen;
    private ImageView[][] imgEvaluatiePinnen;
    private ImageView[] imgOplossing;
    private ImageView[] imgPoging;
    private ImageView[] btnVorige;
    private ImageView[] btnVolgende;
    private String[] kleurmogelijkheden;
    private Image[] afbeeldingen;
    private int[] poging;
    private Button btnSubmit, btnMenu;
    List<String> kleuren; //Nodig voor spelbord te updaten

    private final DomeinController dc;
    private HoofdMenuScherm vorigScherm;

    public SpelbordSchermController(DomeinController dc, HoofdMenuScherm vorigScherm)
    {
        this.dc = dc;
        this.vorigScherm = vorigScherm;
        
        //scherm ophalen en tonen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SpelbordScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try
        {
            loader.load();
        } catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }

        //spelbord maken
        String moeilijkheidsgraad = dc.geefMoeilijkheidsgraad();
        lblSpeel = new Label(Vertaler.vertaalString("speelpinnen"));
        lblEvaluatie = new Label(Vertaler.vertaalString("evaluatiepinnen"));
        lblSpeel.setMaxWidth(Double.MAX_VALUE);
        lblEvaluatie.setMaxWidth(Double.MAX_VALUE);
        lblSpeel.setAlignment(Pos.CENTER);
        lblEvaluatie.setAlignment(Pos.CENTER);
        btnSubmit = new Button("Submit");
        btnMenu = new Button("Menu");
        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")) || moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            buildGemakkelijk();
        }
        else if(moeilijkheidsgraad.equals("moeilijkheidsgraad_0") || moeilijkheidsgraad.equals("moeilijkheidsgraad_1"))
        {
            buildGemakkelijk();
            updateSpelbordMakkelijk();
        }
        else if(moeilijkheidsgraad.equals("moeilijkheidsgraad_2"))
        {
            buildMoeilijk();
            updateSpelbordMoeilijk();
        }
        else
        {
            buildMoeilijk();
        }

        //Opslaan button
        btnSlaOp.setOnAction(new EventHandler<ActionEvent>()
        {
            boolean opnieuw = false;
            @Override
            public void handle(ActionEvent event)
            {
                //Bron: http://code.makery.ch/blog/javafx-dialogs-official/
                
                TextInputDialog dialog = new TextInputDialog();
                dialog.setContentText("");
                dialog.setOnCloseRequest(new EventHandler<DialogEvent>(){ //Op kruisje klikken in dialogbox --> stoppen met opslaan
                    @Override
                    public void handle(DialogEvent event)
                    {
                        opnieuw = false;
                    }
                
                });
                do
                {
                    
                    dialog.setTitle(Vertaler.vertaalString("spelOpslaan"));
                    dialog.setHeaderText(Vertaler.vertaalString("naamNieuwSpel"));
                    try
                    {
                        String naam = dialog.showAndWait().get();
                        dc.slaOp(naam);
                        Stage stage = (Stage) getScene().getWindow();
                        stage.setScene(vorigScherm.getScene());
                        vorigScherm.clearScherm();
                        stage.show();
                        opnieuw = false;
                    } catch (NoSuchElementException ex) //Als dialog wordt gesloten (cancel)
                    {
                        opnieuw = false;
                    }
                    catch(RuntimeException ex)
                    {
                        dialog.setContentText(Vertaler.vertaalString("spelBestaatAl"));
                        opnieuw = true;
                    }
                }while(opnieuw);
            
            }
        });

    }

    private void buildGemakkelijk()
    {
        this.add(lblSpeel, 1, 1, 4, 1);
        this.add(lblEvaluatie, 7, 1, 4, 1);

        //Kleurmogelijkheden
        this.kleurmogelijkheden = dc.geefKleurmogelijkheden();
        kleuren = new ArrayList<String>();
        for(String kleur : kleurmogelijkheden)
        {
            kleuren.add(kleur);
        }
        kleuren.add(Vertaler.vertaalString("leeg"));
        kleuren.add(Vertaler.vertaalString("wit"));
        kleuren.add(Vertaler.vertaalString("zwart"));
        this.afbeeldingen = new Image[kleuren.size()];

        this.afbeeldingen[0] = new Image(getClass().getResourceAsStream("/images/pinrood.png"));
        this.afbeeldingen[1] = new Image(getClass().getResourceAsStream("/images/pinoranje.png"));
        this.afbeeldingen[2] = new Image(getClass().getResourceAsStream("/images/pingeel.png"));
        this.afbeeldingen[3] = new Image(getClass().getResourceAsStream("/images/pingroen.png"));
        this.afbeeldingen[4] = new Image(getClass().getResourceAsStream("/images/pinblauw.png"));
        this.afbeeldingen[5] = new Image(getClass().getResourceAsStream("/images/pinbruin.png"));
        this.afbeeldingen[6] = new Image(getClass().getResourceAsStream("/images/pinpaars.png"));
        this.afbeeldingen[7] = new Image(getClass().getResourceAsStream("/images/pingrijs.png"));
        this.afbeeldingen[8] = new Image(getClass().getResourceAsStream("/images/pinleeg.png"));
        this.afbeeldingen[9] = new Image(getClass().getResourceAsStream("/images/pinwit.png"));
        this.afbeeldingen[10] = new Image(getClass().getResourceAsStream("/images/pinzwart.png"));

        //Spelbord aanmaken
        imgSpeelpinnen = new ImageView[12][4];
        for (int i = 0; i < imgSpeelpinnen.length; i++)
        {
            for (int j = 0; j < imgSpeelpinnen[i].length; j++)
            {
                imgSpeelpinnen[i][j] = new ImageView(new Image(getClass().getResourceAsStream("/images/pinLeeg.png")));
                this.add(imgSpeelpinnen[i][j], j + 1, i + 2);
            }
        }
        imgEvaluatiePinnen = new ImageView[12][4];
        for (int i = 0; i < imgEvaluatiePinnen.length; i++)
        {
            for (int j = 0; j < imgEvaluatiePinnen[i].length; j++)
            {
                imgEvaluatiePinnen[i][j] = new ImageView(new Image(getClass().getResourceAsStream("/images/pinLeeg.png")));
                this.add(imgEvaluatiePinnen[i][j], j + 7, i + 2);
            }
        }

        imgOplossing = new ImageView[4];

        imgPoging = new ImageView[4];
        for (int i = 0; i < imgPoging.length; i++)
        {
            imgPoging[i] = new ImageView(afbeeldingen[0]);
            this.add(imgPoging[i], 1 + i, 18);
        }
        //Pogingen
        poging = new int[4];
        for (int i = 0; i < poging.length; i++)
        {
            poging[i] = 0;
        }

        //Buttons vorige
        btnVorige = new ImageView[4];
        for (int i = 0; i < btnVorige.length; i++)
        {
            btnVorige[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/pijlOmhoog.png")));
        }

        btnVorige[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[0] = (poging[0] - 1) % kleurmogelijkheden.length;
                if (poging[0] < 0)
                {
                    poging[0] = kleurmogelijkheden.length - 1;
                }
                imgPoging[0].setImage(afbeeldingen[poging[0]]);
            }

        });
        btnVorige[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[1] = (poging[1] - 1) % kleurmogelijkheden.length;
                if (poging[1] < 0)
                {
                    poging[1] = kleurmogelijkheden.length - 1;
                }
                imgPoging[1].setImage(afbeeldingen[poging[1]]);
            }

        });
        btnVorige[2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[2] = (poging[2] - 1) % kleurmogelijkheden.length;
                if (poging[2] < 0)
                {
                    poging[2] = kleurmogelijkheden.length - 1;
                }
                imgPoging[2].setImage(afbeeldingen[poging[2]]);
            }

        });
        btnVorige[3].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[3] = (poging[3] - 1) % kleurmogelijkheden.length;
                if (poging[3] < 0)
                {
                    poging[3] = kleurmogelijkheden.length - 1;
                }
                imgPoging[3].setImage(afbeeldingen[poging[3]]);
            }

        });

        for (int i = 0; i < btnVorige.length; i++)
        {
            this.add(btnVorige[i], 1 + i, 17);
        }

        //Buttons volgende
        btnVolgende = new ImageView[4];
        for (int i = 0; i < btnVolgende.length; i++)
        {
            btnVolgende[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/pijlOmlaag.png")));
        }
        btnVolgende[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[0] = (poging[0] + 1) % kleurmogelijkheden.length;
                imgPoging[0].setImage(afbeeldingen[poging[0]]);
            }
        });
        btnVolgende[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[1] = (poging[1] + 1) % kleurmogelijkheden.length;
                imgPoging[1].setImage(afbeeldingen[poging[1]]);
            }
        });
        btnVolgende[2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[2] = (poging[2] + 1) % kleurmogelijkheden.length;
                imgPoging[2].setImage(afbeeldingen[poging[2]]);
            }
        });
        btnVolgende[3].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[3] = (poging[3] + 1) % kleurmogelijkheden.length;
                imgPoging[3].setImage(afbeeldingen[poging[3]]);
            }
        });

        for (int i = 0; i < btnVolgende.length; i++)
        {
            this.add(btnVolgende[i], 1 + i, 19);
        }

        //Button Submit toevoegen
        this.add(btnSubmit, 5, 18);

        btnSubmit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                String[] ingevoerdePoging = new String[4];
                for (int i = 0; i < poging.length; i++)
                {
                    ingevoerdePoging[i] = kleurmogelijkheden[poging[i]];
                }
                dc.dienPogingIn(ingevoerdePoging);
                updateSpelbordMakkelijk();
                boolean eindeSpel = dc.isEindeSpel();

                if (eindeSpel && dc.geefStatus() != 3)
                {
                    if (dc.geefStatus() == 1)
                    {
                        dc.voegWinToe();
                    }

                }
            }
        });

        //Button Menu
        btnMenu.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                HoofdMenuScherm hoofdmenu = new HoofdMenuScherm(dc);
                Scene scene = new Scene(hoofdmenu, 1000, 600);
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void updateSpelbordMakkelijk()
    {
        String[][] spelbord = dc.geefSpelbord();
        for (int i = 0; i < imgSpeelpinnen.length; i++)
        {
            for (int j = 0; j < imgSpeelpinnen[i].length; j++)
            {
                //imgSpeelpinnen[i][j].setImage(new Image(getClass().getResourceAsStream(String.format("/images/pin%s.png", spelbord[i][j]))));
                
                int indexKleur = kleuren.indexOf(spelbord[i][j]);
                imgSpeelpinnen[i][j].setImage(afbeeldingen[indexKleur]);
            }
        }
        for (int i = 0; i < imgEvaluatiePinnen.length; i++)
        {
            for (int j = 0; j < imgEvaluatiePinnen[i].length; j++)
            {
                //imgEvaluatiePinnen[i][j].setImage(new Image(getClass().getResourceAsStream(String.format("/images/pin%s.png", spelbord[i][j + 4]))));
                int indexKleur = kleuren.indexOf(spelbord[i][j+4]);
                imgEvaluatiePinnen[i][j].setImage(afbeeldingen[indexKleur]);
            }
        }
        if (dc.isEindeSpel())
        {
            for (int i = 0; i < imgOplossing.length; i++)
            {
                //imgOplossing[i] = new ImageView(new Image(getClass().getResourceAsStream(String.format("/images/pin%s.png", spelbord[12][i]))));
                int indexKleur = kleuren.indexOf(spelbord[12][i]);
                imgOplossing[i] = new ImageView(afbeeldingen[indexKleur]);
                
                this.add(imgOplossing[i], i + 1, 15);
            }
            this.getChildren().remove(btnSubmit);
            this.getChildren().remove(btnSlaOp);
            btnMenu.setPrefWidth(Double.MAX_VALUE);
            this.add(btnMenu, 9, 18, 2, 1);
            eindeSpel();
        }
    }

    private void buildMoeilijk()
    {
        this.add(lblSpeel, 1, 1, 5, 1);
        this.add(lblEvaluatie, 7, 1, 5, 1);

        //Kleurmogelijkheden
        this.kleurmogelijkheden = new String[dc.geefKleurmogelijkheden().length + 1];
        System.arraycopy(dc.geefKleurmogelijkheden(), 0, this.kleurmogelijkheden, 0, this.kleurmogelijkheden.length - 1);
        this.kleurmogelijkheden[this.kleurmogelijkheden.length - 1] = Vertaler.vertaalString("leeg");
        kleuren = new ArrayList<String>();
        for(String kleur : kleurmogelijkheden)
        {
            kleuren.add(kleur);
        }
        //kleuren.add(Vertaler.vertaalString("leeg"));
        kleuren.add(Vertaler.vertaalString("wit"));
        kleuren.add(Vertaler.vertaalString("zwart"));
        this.afbeeldingen = new Image[kleuren.size()];

        this.afbeeldingen[0] = new Image(getClass().getResourceAsStream("/images/pinrood.png"));
        this.afbeeldingen[1] = new Image(getClass().getResourceAsStream("/images/pinoranje.png"));
        this.afbeeldingen[2] = new Image(getClass().getResourceAsStream("/images/pingeel.png"));
        this.afbeeldingen[3] = new Image(getClass().getResourceAsStream("/images/pingroen.png"));
        this.afbeeldingen[4] = new Image(getClass().getResourceAsStream("/images/pinblauw.png"));
        this.afbeeldingen[5] = new Image(getClass().getResourceAsStream("/images/pinbruin.png"));
        this.afbeeldingen[6] = new Image(getClass().getResourceAsStream("/images/pinpaars.png"));
        this.afbeeldingen[7] = new Image(getClass().getResourceAsStream("/images/pingrijs.png"));
        this.afbeeldingen[8] = new Image(getClass().getResourceAsStream("/images/pinleeg.png"));
        this.afbeeldingen[9] = new Image(getClass().getResourceAsStream("/images/pinwit.png"));
        this.afbeeldingen[10] = new Image(getClass().getResourceAsStream("/images/pinzwart.png"));

        //Spelbord aanmaken
        imgSpeelpinnen = new ImageView[12][5];
        for (int i = 0; i < imgSpeelpinnen.length; i++)
        {
            for (int j = 0; j < imgSpeelpinnen[i].length; j++)
            {
                imgSpeelpinnen[i][j] = new ImageView(new Image(getClass().getResourceAsStream("/images/pinLeeg.png")));
                this.add(imgSpeelpinnen[i][j], j + 1, i + 2);
            }
        }
        imgEvaluatiePinnen = new ImageView[12][5];
        for (int i = 0; i < imgEvaluatiePinnen.length; i++)
        {
            for (int j = 0; j < imgEvaluatiePinnen[i].length; j++)
            {
                imgEvaluatiePinnen[i][j] = new ImageView(new Image(getClass().getResourceAsStream("/images/pinLeeg.png")));
                this.add(imgEvaluatiePinnen[i][j], j + 7, i + 2);
            }
        }

        imgOplossing = new ImageView[5];

        imgPoging = new ImageView[5];
        for (int i = 0; i < imgPoging.length; i++)
        {
            imgPoging[i] = new ImageView(afbeeldingen[0]);
            this.add(imgPoging[i], 1 + i, 18);
        }

        //Pogingen
        poging = new int[5];
        for (int i = 0; i < poging.length; i++)
        {
            poging[i] = 0;
        }

        //Buttons vorige
        btnVorige = new ImageView[5];
        for (int i = 0; i < btnVorige.length; i++)
        {
            btnVorige[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/pijlOmhoog.png")));
        }

        btnVorige[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[0] = (poging[0] - 1) % kleurmogelijkheden.length;
                if (poging[0] < 0)
                {
                    poging[0] = kleurmogelijkheden.length - 1;
                }
                imgPoging[0].setImage(afbeeldingen[poging[0]]);
            }

        });
        btnVorige[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[1] = (poging[1] - 1) % kleurmogelijkheden.length;
                if (poging[1] < 0)
                {
                    poging[1] = kleurmogelijkheden.length - 1;
                }
                imgPoging[1].setImage(afbeeldingen[poging[1]]);
            }

        });
        btnVorige[2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[2] = (poging[2] - 1) % kleurmogelijkheden.length;
                if (poging[2] < 0)
                {
                    poging[2] = kleurmogelijkheden.length - 1;
                }
                imgPoging[2].setImage(afbeeldingen[poging[2]]);
            }

        });
        btnVorige[3].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[3] = (poging[3] - 1) % kleurmogelijkheden.length;
                if (poging[3] < 0)
                {
                    poging[3] = kleurmogelijkheden.length - 1;
                }
                imgPoging[3].setImage(afbeeldingen[poging[3]]);
            }

        });
        btnVorige[4].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[4] = (poging[4] - 1) % kleurmogelijkheden.length;
                if (poging[4] < 0)
                {
                    poging[4] = kleurmogelijkheden.length - 1;
                }
                imgPoging[4].setImage(afbeeldingen[poging[4]]);
            }

        });
        for (int i = 0; i < btnVorige.length; i++)
        {
            this.add(btnVorige[i], 1 + i, 17);
        }

        //Buttons volgende
        btnVolgende = new ImageView[5];
        for (int i = 0; i < btnVolgende.length; i++)
        {
            btnVolgende[i] = new ImageView(new Image(getClass().getResourceAsStream("/images/pijlOmlaag.png")));
        }
        btnVolgende[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[0] = (poging[0] + 1) % kleurmogelijkheden.length;
                imgPoging[0].setImage(afbeeldingen[poging[0]]);
            }
        });
        btnVolgende[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[1] = (poging[1] + 1) % kleurmogelijkheden.length;
                imgPoging[1].setImage(afbeeldingen[poging[1]]);
            }
        });
        btnVolgende[2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[2] = (poging[2] + 1) % kleurmogelijkheden.length;
                imgPoging[2].setImage(afbeeldingen[poging[2]]);
            }
        });
        btnVolgende[3].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[3] = (poging[3] + 1) % kleurmogelijkheden.length;
                imgPoging[3].setImage(afbeeldingen[poging[3]]);
            }
        });
        btnVolgende[4].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                poging[4] = (poging[4] + 1) % kleurmogelijkheden.length;
                imgPoging[4].setImage(afbeeldingen[poging[4]]);
            }
        });

        for (int i = 0; i < btnVolgende.length; i++)
        {
            this.add(btnVolgende[i], 1 + i, 19);
        }
        
        //Button Menu
        btnMenu.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                HoofdMenuScherm hoofdmenu = new HoofdMenuScherm(dc);
                Scene scene = new Scene(hoofdmenu, 1000, 600);
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });

        //Button Submit toevoegen
        btnSubmit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                String[] ingevoerdePoging = new String[5];
                for (int i = 0; i < poging.length; i++)
                {
                    ingevoerdePoging[i] = kleurmogelijkheden[poging[i]];
                }
                dc.dienPogingIn(ingevoerdePoging);
                updateSpelbordMoeilijk();
                boolean eindeSpel = dc.isEindeSpel();

                if (eindeSpel && dc.geefStatus() != 3)
                {
                    btnSubmit.setDisable(true);
                    if (dc.geefStatus() == 1)
                    {
                        dc.voegWinToe();
                    }
                }
            }
        });
        this.add(btnSubmit, 6, 18);

    }

    private void updateSpelbordMoeilijk()
    {
        String[][] spelbord = dc.geefSpelbord();

        for (int i = 0; i < imgSpeelpinnen.length; i++)
        {
            for (int j = 0; j < imgSpeelpinnen[i].length; j++)
            {
                //imgSpeelpinnen[i][j].setImage(new Image(getClass().getResourceAsStream(String.format("/images/pin%s.png", spelbord[i][j]))));
                
                int indexKleur = kleuren.indexOf(spelbord[i][j]);
                imgSpeelpinnen[i][j].setImage(afbeeldingen[indexKleur]);
            }
        }
        for (int i = 0; i < imgEvaluatiePinnen.length; i++)
        {
            for (int j = 0; j < imgEvaluatiePinnen[i].length; j++)
            {
                //imgEvaluatiePinnen[i][j].setImage(new Image(getClass().getResourceAsStream(String.format("/images/pin%s.png", spelbord[i][j + 5]))));
                int indexKleur = kleuren.indexOf(spelbord[i][j+5]);
                imgEvaluatiePinnen[i][j].setImage(afbeeldingen[indexKleur]);
                
            }
        }
        if (dc.isEindeSpel())
        {
            for (int i = 0; i < imgOplossing.length; i++)
            {
                //imgOplossing[i] = new ImageView(new Image(getClass().getResourceAsStream(String.format("/images/pin%s.png", spelbord[12][i]))));
                int indexKleur = kleuren.indexOf(spelbord[12][i]);
                imgOplossing[i] = new ImageView(afbeeldingen[indexKleur]);
                
                this.add(imgOplossing[i], i + 1, 15);
                
            }
            
            this.getChildren().remove(btnSubmit);
            this.getChildren().remove(btnSlaOp);
            btnMenu.setPrefWidth(Double.MAX_VALUE);
            this.add(btnMenu, 9, 18, 2, 1);
            eindeSpel();
        }
    }
    
    private void eindeSpel()
    {
        String output = "";
        String titel = "";
        if (dc.isEindeSpel() == true && dc.geefStatus() == 1)
        {
            titel = Vertaler.vertaalString("gewonnen");
            output += String.format("%s%n%s%d%n", Vertaler.vertaalString("codeGekraakt"), Vertaler.vertaalString("aantalPogingen"), dc.geefAantalPogingen());
            output += String.format("%s%d%n", Vertaler.vertaalString("aantalSterren"), dc.geefAantalSterren());
            output += String.format("%s%d%n", Vertaler.vertaalString("aantalWinsNodig"), dc.geefAantalTeWinnenSpellen());
        }
        else
        {
            titel = Vertaler.vertaalString("verloren");
            output += String.format("%s%n", Vertaler.vertaalString("codeNietGekraakt"));
        }
        
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setHeaderText(titel + "!");
        alert.setContentText(output);

        alert.showAndWait();
    }
}
