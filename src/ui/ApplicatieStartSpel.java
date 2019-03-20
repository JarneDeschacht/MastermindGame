package ui;

import domein.DomeinController;
import domein.KeuzeMogelijkheden;
import domein.Spel;
import domein.Vertaler;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ApplicatieStartSpel
{

    private final DomeinController controller;

    public ApplicatieStartSpel(DomeinController dc)
    {
        this.controller = dc;

        Scanner sc = new Scanner(System.in);
        //moeilijkheidsgraad selecteren
        String[] moeilijkheidsgraden = controller.geefMoeilijkheidsgraden();
        int[] aantalWinsPerMoeilijkheidsgraad = controller.geefAantalKeerGewonnen();
        boolean nogHerhalen = true;
        String moeilijkheidsgraad = moeilijkheidsgraden[0];
        do
        {
            try
            {
                if (aantalWinsPerMoeilijkheidsgraad[0] >= 20 && aantalWinsPerMoeilijkheidsgraad[1] < 20)
                {
                    System.out.printf("%s %s(%d wins) %s %s(%d wins)%n%s: ", Vertaler.vertaalString("moeilijkheidsgraden"), moeilijkheidsgraden[0], aantalWinsPerMoeilijkheidsgraad[0], Vertaler.vertaalString("en"), moeilijkheidsgraden[1], aantalWinsPerMoeilijkheidsgraad[1], Vertaler.vertaalString("keuzeMoeilijkheidsgraad"));
                } else if (aantalWinsPerMoeilijkheidsgraad[1] >= 20)
                {
                    System.out.printf("%s %s(%d wins), %s(%d wins) %s %s(%d wins)%n%s: ", Vertaler.vertaalString("moeilijkheidsgraden"), moeilijkheidsgraden[0], aantalWinsPerMoeilijkheidsgraad[0], moeilijkheidsgraden[1], aantalWinsPerMoeilijkheidsgraad[1], Vertaler.vertaalString("en"), moeilijkheidsgraden[2], aantalWinsPerMoeilijkheidsgraad[2], Vertaler.vertaalString("keuzeMoeilijkheidsgraad"));
                } else
                {
                    System.out.printf("%s %s(%d wins)%n%s: ", Vertaler.vertaalString("moeilijkheidsgraden"), moeilijkheidsgraden[0], aantalWinsPerMoeilijkheidsgraad[0], Vertaler.vertaalString("keuzeMoeilijkheidsgraad"));
                }

                moeilijkheidsgraad = sc.next();
                if (!(moeilijkheidsgraad.equals(moeilijkheidsgraden[0])) && !(moeilijkheidsgraad.equals(moeilijkheidsgraden[1])) && !(moeilijkheidsgraad.equals(moeilijkheidsgraden[2])))
                {
                    throw new IllegalArgumentException(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("MoeilijkheidsgraadBestaatNiet"), "\u001B[0m"));
                }
                nogHerhalen = false;
            } catch (IllegalArgumentException e)
            {
                System.out.println(String.format("%s %s %s", "\u001B[31m", e.getMessage(), "\u001B[0m"));
            }
        } while (nogHerhalen);

        controller.maakNieuwSpel(moeilijkheidsgraad);

        //Spelbord weergeven
        toonBord(controller);
        speelSpel(moeilijkheidsgraad, controller);
        LoginApplicatie.menuKeuzes(sc, dc);
    }

    public static void toonBord(DomeinController controller)
    {
        //spelbord weergeven
        String[][] overzicht = controller.geefSpelbord();
        String output = String.format("%30s%30s%n", Vertaler.vertaalString("speelpinnen"), Vertaler.vertaalString("evaluatiepinnen"));
        for (int i = 0; i < overzicht.length - 1; i++)
        {
            for (int j = 0; j < overzicht[i].length; j++)
            {
                output += String.format("%s%10s%s", KeuzeMogelijkheden.ANSI.getMogelijkheden()[overzicht[i][j].equals("rood") ? 1 : overzicht[i][j].equals("oranje") ? 2 : overzicht[i][j].equals("geel") ? 3 : overzicht[i][j].equals("groen") ? 4 : overzicht[i][j].equals("blauw") ? 5 : overzicht[i][j].equals("bruin") ? 6 : overzicht[i][j].equals("paars") ? 7 : overzicht[i][j].equals("grijs") ? 8 : 0], overzicht[i][j], KeuzeMogelijkheden.ANSI.getMogelijkheden()[0]);
            }
            output += String.format("%n");
        }
        // gemaskeerde oplossing toevoegen ipv normale oplossing
        for (int i = 0; i < overzicht[1].length / 2; i++)
        {
//           String maskeren = "";
            //           for (int j = 0; j < overzicht[12][i].length(); j++)
            //           {
            //maskeren += "*";
            //}
            String maskeren = "*****";
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

    public static void speelSpel(String moeilijkheidsgraad, DomeinController controller)
    {
        Scanner input = new Scanner(System.in);
        // --- Spel spelen --- 
        //Kijken hoeveel pinnen er moeten ingevuld worden
        int inTeVullenPinnen;
        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")) || moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            inTeVullenPinnen = 4;
        } else
        {
            inTeVullenPinnen = 5;
        }

        boolean opgeslagenSpel = false;
        do
        {

            //Kleurmogelijkheden weergeven
            System.out.printf("%s: %n", Vertaler.vertaalString("kleurMogelijkhedenWeergeven"));
            String[] kleurmogelijkheden = controller.geefKleurmogelijkheden();
            for (String kleurmogelijkheid : kleurmogelijkheden)
            {
                System.out.printf("%s ", kleurmogelijkheid);
            }
            System.out.println();

            //Pinnen invullen
            String[] pinnen = new String[inTeVullenPinnen];
            for (int i = 0; i < inTeVullenPinnen; i++)
            {
                boolean geldigKleur = false;

                do
                {
                    System.out.printf("%s %d: ", Vertaler.vertaalString("geefKleurIn"), i + 1);
                    String kleur = input.next();

                    if (kleur.equals("stop"))
                    {
                        boolean succes = true;

                        do
                        {
                            try
                            {
                                System.out.printf(Vertaler.vertaalString("naamNieuwSpel"));
                                String naam = input.next();
                                controller.slaOp(naam);
                                i = inTeVullenPinnen;
                                opgeslagenSpel = true;
                                succes = true;
                            } catch (RuntimeException ex)
                            {
                                System.out.println(Vertaler.vertaalString("spelBestaatAl"));
                                succes = false;
                            }
                        }while(!succes);

                        
                    
                    } else
                    {
                        //Kijken alsof het ingevulde kleur een geldig kleur is
                        for (String kleurmogelijkheid : kleurmogelijkheden)
                        {
                            if (kleurmogelijkheid.equals(kleur))
                            {
                                geldigKleur = true;
                            }
                        }
                        if (kleur.endsWith(Vertaler.vertaalString("leeg")))
                        {
                            geldigKleur = true;
                        }

                        //Melding laten weergeven als het een ongeldig kleur is
                        if (!geldigKleur)
                        {
                            System.out.println(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("geldigKleur"), "\u001B[0m"));
                        } else
                        {
                            pinnen[i] = kleur;
                        }
                    }
                } while (!geldigKleur && !opgeslagenSpel);
            }
            if (!opgeslagenSpel)
            {
                controller.dienPogingIn(pinnen);
                ApplicatieStartSpel.toonBord(controller);
            }

        } while (!opgeslagenSpel && controller.isEindeSpel() == false && controller.geefStatus() == 3);

        // knowing methode UC3
        if (!opgeslagenSpel)
        {
            if (controller.isEindeSpel() == true && controller.geefStatus() == 1)
            {
                controller.voegWinToe();
                System.out.printf("%s%n%s%n%s%d%n", Vertaler.vertaalString("gekraakteCodeWinnaar"), controller.geefGekraakteCode(), Vertaler.vertaalString("aantalPogingen"), controller.geefAantalPogingen());
                System.out.printf("%s%d%n", Vertaler.vertaalString("aantalSterren"), controller.geefAantalSterren());
                System.out.printf("%s%d%n", Vertaler.vertaalString("aantalWinsNodig"), controller.geefAantalTeWinnenSpellen());

            } else
            {
                System.out.printf("%s%n%s%n", Vertaler.vertaalString("gekraakteCodeVerliezer"), controller.geefGekraakteCode());
            }
        }
    }

}
