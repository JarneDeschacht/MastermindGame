package main;
import domein.DomeinController;
import domein.Vertaler;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import ui.LoginApplicatie;
import java.util.InputMismatchException;
public class StartUp
{
    public static void main(String[] args)
    {
        ResourceBundle taal = kiesTaal();
        DomeinController dc = new DomeinController();
        new LoginApplicatie(dc);
    }
    
    private static ResourceBundle kiesTaal()
    {
        Scanner sc = new Scanner(System.in);
        int taalkeuze=0;
        do
        {   
            
            try
            {
                System.out.printf("Kies je taal/Choose your language/Choisissez votre langue%n1) Nederlands%n2) English%n3) Français%nUw keuze/your choice/votre choix: ");
                taalkeuze = sc.nextInt();
                if(taalkeuze <1 || taalkeuze > 3)
                 throw new IllegalArgumentException(String.format("%s %s %s", "\u001B[31m","Je moet een getal tussen 1 en 3 ingeven/You need to enter a number between 1 and 3/Vous devez entrer un nombre entre 1 et 3","\u001B[0m"));
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
            catch(InputMismatchException e)
            {
              sc.nextLine();
              System.out.println(String.format("%s %s %s","\u001B[31m","Je moet een getal invoeren!/You need to enter a number!/Vous devez entrer un numéro!","\u001B[0m"));
            }
        } while (taalkeuze <1 || taalkeuze >3);
        
        ResourceBundle taal;
        
        switch(taalkeuze)
        {
            case 1: default: 
                taal = ResourceBundle.getBundle("taal.Taal",new Locale("nl","BE"));
                break;
            case 2:
                taal = ResourceBundle.getBundle("taal.Taal",new Locale("en","US"));
                break;
            case 3: 
                taal = ResourceBundle.getBundle("taal.Taal",new Locale("fr","BE"));
                break;
        }
        new Vertaler(taal);
        return taal;
    }
}