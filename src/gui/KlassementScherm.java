package gui;

import domein.DomeinController;
import domein.Vertaler;
import java.util.Arrays;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class KlassementScherm extends GridPane
{
    private ComboBox cboMoeilijkheidsgraden;
    private Label lblTitel;
    private Button btnTerug;
    private TableView<String[]> table;
    private String[][][] klassement;
    private TableColumn positie;
    private TableColumn gebruikersnaam;
    private TableColumn punten;

    public KlassementScherm(DomeinController dc)
    {
        buildKlassement(dc);
        klassement = dc.toonKlassement();
    }

    private void buildKlassement(DomeinController dc)
    {
       this.setPadding(new Insets(15));
       this.setVgap(10);
       this.setHgap(10);
        
       cboMoeilijkheidsgraden = new ComboBox();
       String[] titels = dc.geefMoeilijkheidsgraden();
       ObservableList items = FXCollections.observableArrayList(titels);
       cboMoeilijkheidsgraden.setItems(items);
       cboMoeilijkheidsgraden.setPromptText("--- " + Vertaler.vertaalString("keuzeMoeilijkheidsgraad") + " ---");
       
       
       lblTitel = new Label(Vertaler.vertaalString("klassement"));
       lblTitel.setMaxWidth(Double.MAX_VALUE);
       lblTitel.setFont(Font.font(50));
       lblTitel.setTextFill(Color.web("#0099ff"));
       
       table = new TableView();
       table.setEditable(false);
       
       //kolommen
       //positie = new TableColumn(Vertaler.vertaalString("positie"));
       gebruikersnaam = new TableColumn(Vertaler.vertaalString("gebruikersnaam"));
       gebruikersnaam.setPrefWidth(250);
       punten = new TableColumn(Vertaler.vertaalString("punten")); 
       
       table.getColumns().addAll(gebruikersnaam, punten);
       
       
       btnTerug = new Button(Vertaler.vertaalString("terugNaarHoofdmenu"));
       btnTerug.setOnAction((event) ->
       {
           HoofdMenuScherm hoofdmenu = new HoofdMenuScherm(dc);
           Scene scene = new Scene(hoofdmenu, 1000, 600);
           Stage stage = (Stage) getScene().getWindow();
           stage.setScene(scene);
           stage.show();
       });
               
       this.add(lblTitel, 0, 1);
       this.add(cboMoeilijkheidsgraden,0,3);
       this.add(table, 0, 5);
       this.add(btnTerug, 0, 7);
       
       
       cboMoeilijkheidsgraden.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event)
           {
               int moeilijkheidsgraad;
               if(cboMoeilijkheidsgraden.getValue().equals(Vertaler.vertaalString("gemakkelijk")))
               {
                   moeilijkheidsgraad = 0;
               }
               else if(cboMoeilijkheidsgraden.getValue().equals(Vertaler.vertaalString("normaal")))
               {
                   moeilijkheidsgraad = 1;
               }
               else
               {
                   moeilijkheidsgraad = 2;
               }
               ObservableList<String[]> data = FXCollections.observableArrayList();
               data.addAll(Arrays.asList(klassement[moeilijkheidsgraad]));
               gebruikersnaam.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                            return new SimpleStringProperty((p.getValue()[0]));
                        }
               });
               punten.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                            return new SimpleStringProperty((p.getValue()[1]));
                        }
               });
               //Bron: https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
               table.setItems(data);
           }
       
       });
       
    }
    
}
