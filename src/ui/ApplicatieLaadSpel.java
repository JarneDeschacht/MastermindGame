package ui;

import domein.DomeinController;
import domein.Vertaler;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author robbe
 */
public class ApplicatieLaadSpel
{

    private final DomeinController controller;
    private final Scanner input;

    public ApplicatieLaadSpel(DomeinController dc)
    {
        this.controller = dc;
        this.input = new Scanner(System.in);
        System.out.printf("%s:%n", Vertaler.vertaalString("opgeslagenSpelen"));
        String[][] spelletjes = controller.geefSpelletjes();
        
        if (spelletjes.length == 0)
        {
            System.out.println(Vertaler.vertaalString("geenOpgeslagenSpelen"));
            System.exit(0);
        }
        String output = "";
        for (String[] spel : spelletjes)
        {
            output += String.format("%s: %s%n", spel[0], Vertaler.vertaalString(spel[1]));
        }
        System.out.printf("%s", output);
        boolean nogHerhalen = true;
        do
        {
            System.out.printf(Vertaler.vertaalString("naamSpel"));
            String spelnaam = input.next();
            for(String[] spel : spelletjes)
            {
                if(spelnaam.equals(spel[0]))
                {
                    controller.kiesSpel(spelnaam);
                    nogHerhalen = false;
                }     
            }
            if(nogHerhalen)
                System.out.println(String.format("%s %s %s", "\u001B[31m",Vertaler.vertaalString("SpelBestaatNiet"), "\u001B[0m"));
        }while(nogHerhalen);

        String moeilijkheidsgraad = controller.geefMoeilijkheidsgraad();
        //Spelbord weergeven
        toonBord();
        ApplicatieStartSpel.speelSpel(moeilijkheidsgraad, controller);

        LoginApplicatie.menuKeuzes(input,dc);
    }

    public  void toonBord()
    {
        //spelbord weergeven
        String[][] overzicht = controller.geefSpelbord();
        String output = String.format("%30s%30s%n", Vertaler.vertaalString("speelpinnen"), Vertaler.vertaalString("evaluatiepinnen"));
        for (int i = 0; i < overzicht.length - 1; i++)
        {
            for (int j = 0; j < overzicht[i].length; j++)
            {
                output += String.format("%10s", overzicht[i][j]);
            }
            output += String.format("%n");
        }
        // gemaskeerde oplossing toevoegen ipv normale oplossing
        for (int i = 0; i < overzicht[1].length / 2; i++)
        {
//            String maskeren = "";
//            for (int j = 0; j < overzicht[12][i].length(); j++)
//            {
//                maskeren += "*";
//            }
            String maskeren = overzicht[12][i];
            output += String.format("%10s", maskeren);
        }
        // rest van de 'oplossingrij' leeg voorstellen
        for (int i = overzicht[1].length / 2; i < overzicht[i].length; i++)
        {
            output += String.format("%10s", " ");
        }
        output += String.format("%n");
        System.out.printf("%s", output);
    }
}
