package ui;

import domein.DomeinController;
import domein.Vertaler;
import java.util.Scanner;

/**
 *
 * @author robbe
 */
public class ApplicatieKlassement
{
    private final DomeinController dc;
    
    public ApplicatieKlassement(DomeinController dc)
    {
        this.dc = dc;
        Scanner sc = new Scanner(System.in);
        String[][][] klassement = dc.toonKlassement();
        
        //klassement tonen
        System.out.println("--- " + Vertaler.vertaalString("gemakkelijk") +  " ---");
        for(String[] record : klassement[0])
        {
            System.out.print(record[0] + ":\t");
            System.out.println(record[1]);
        }
        
        System.out.println("--- " + Vertaler.vertaalString("normaal") +  " ---");
        for(String[] record : klassement[1])
        {
            System.out.print(record[0] + ":\t");
            System.out.println(record[1]);
        }
        
        System.out.println("--- " + Vertaler.vertaalString("moeilijk") +  " ---");
        for(String[] record : klassement[2])
        {
            System.out.print(record[0] + ":\t");
            System.out.println(record[1]);
        }
        
        
        LoginApplicatie.menuKeuzes(sc, dc);
    }
    
}
