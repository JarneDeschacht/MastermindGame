package ui;

import domein.DomeinController;
import domein.Vertaler;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginApplicatie
{

    private final DomeinController controller;

    public LoginApplicatie(DomeinController dc)
    {
        Scanner sc = new Scanner(System.in);
        this.controller = dc;

        System.out.println(Vertaler.vertaalString("welkomTekst"));
        //inloggen
        int keuze = 0;
        String gebruikersnaam, wachtwoord, bevestigWachtwoord;

        do
        {

            try
            {
                System.out.printf("login%n1) %s%n2) %s%n%s: ", Vertaler.vertaalString("aanmelden"), Vertaler.vertaalString("registreren"), Vertaler.vertaalString("keuze"));
                keuze = sc.nextInt();
                if (keuze < 1 || keuze > 2)
                {
                    throw new IllegalArgumentException(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("getalTussen1en2"), "\u001B[0m"));
                }
            } catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e)
            {
                System.out.println(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("inputGetal"), "\u001B[0m"));
                sc.nextLine();
            }
        } while (keuze < 1 || keuze > 2);

        boolean nogHerhalen = true;
        do
        {
            try
            {

                switch (keuze)
                {
                    case 1:

                        System.out.printf("%s: ", Vertaler.vertaalString("gebruikersnaam"));
                        gebruikersnaam = sc.next();
                        System.out.printf("%s: ", Vertaler.vertaalString("wachtwoord"));
                        wachtwoord = sc.next();
                        controller.meldAan(gebruikersnaam, wachtwoord);
                        break;
                    case 2:
                    default:
                        System.out.printf("%s: ", Vertaler.vertaalString("gebruikersnaam"));
                        gebruikersnaam = sc.next();
                        System.out.printf("%s: ", Vertaler.vertaalString("wachtwoord"));
                        wachtwoord = sc.next();
                        while (!controller.controleerWachtwoord(wachtwoord))
                        {
                            System.out.printf("%s%n%s%n%s: ", Vertaler.vertaalString("foutWachtwoord"), Vertaler.vertaalString("instructieWachtwoord"), Vertaler.vertaalString("nieuwWachtwoord"));
                            wachtwoord = sc.next();
                        }
                        System.out.printf("%s: ", Vertaler.vertaalString("bevestigWachtwoord"));
                        bevestigWachtwoord = sc.next();
                        while (!bevestigWachtwoord.equals(wachtwoord))
                        {

                            System.out.printf("%s %s %s", "\u001B[31m", Vertaler.vertaalString("nietDezelfdeWachtwoorden"), "\u001B[0m");
                            bevestigWachtwoord = sc.next();
                        }

                        controller.registreer(gebruikersnaam, wachtwoord);
                        break;
                }
                nogHerhalen = false;
            } catch (NullPointerException e)
            {
                sc.nextLine();
                System.out.println(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("inloggenMislukt"), "\u001B[0m"));
            } catch (IllegalArgumentException e)
            {
                System.out.println(String.format("%s %s %s", "\u001B[31m", e.getMessage(), "\u001B[0m"));
            }
        } while (nogHerhalen);

        System.out.printf("%n%s %s%n%n", Vertaler.vertaalString("welkom"), controller.geefGebruikersnaam());
        
        menuKeuzes(sc, controller);
    }
    public static void menuKeuzes(Scanner sc,DomeinController controller)
    {
        //spelmogelijkheden selecteren
        int spelkeuze;
         boolean nogHerhalen = true;

        do
        {
            System.out.printf("%n%s:%n1) %s%n2) %s%n3) %s%n4) %s%n5) %s%n0) %s%n%s: ", Vertaler.vertaalString("keuzeTekst"), Vertaler.vertaalString("startSpel"), Vertaler.vertaalString("laadSpel"), Vertaler.vertaalString("daagUit"), Vertaler.vertaalString("uitgedaagdDoor"), Vertaler.vertaalString("klassement"),Vertaler.vertaalString("stoppen"), Vertaler.vertaalString("keuze"));

            try
            {
                spelkeuze = sc.nextInt();
                if (spelkeuze < 0 || spelkeuze > 5)
                {
                    throw new IllegalArgumentException(Vertaler.vertaalString("getalTussen1en5"));
                }

                switch (spelkeuze)
                {
                    default:
                        break;
                    case (1):
                        new ApplicatieStartSpel(controller);
                        break;
                    case (2):
                        new ApplicatieLaadSpel(controller);
                        break;
                    case (3):
                        new ApplicatieDaagUit(controller);
                        break;
                    case (4):
                        new ApplicatieDoorWieUitgedaagd(controller);
                        break;
                    case (5):
                        new ApplicatieKlassement(controller);
                        break;
                    case (0): System.exit(0); break;
                }
                nogHerhalen = false;
            } 
            catch (InputMismatchException e)
            {
                sc.nextLine();
                System.out.println(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("inputGetal"), "\u001B[0m"));
            } 
            catch (IllegalArgumentException e)
            {
                System.out.println(String.format("%s %s %s", "\u001B[31m", e.getMessage(), "\u001B[0m"));
            }
        } while (nogHerhalen);
    }

}
