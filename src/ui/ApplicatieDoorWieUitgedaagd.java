package ui;

import domein.DomeinController;
import domein.Vertaler;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author robbe
 */
public class ApplicatieDoorWieUitgedaagd {

    private DomeinController dc;

    public ApplicatieDoorWieUitgedaagd(DomeinController dc) {
        this.dc = dc;
        Scanner sc = new Scanner(System.in);
        String[][] uitdagingen = dc.geefUitdagingenSpeler();
        String onafgewerkteOpgeslagenUitdaging = dc.onafgewerkteOpgeslagenUitdaging();

        if (uitdagingen.length == 0) {
            System.out.println(Vertaler.vertaalString("geenUitdagingenGevonden"));
        } else if (!onafgewerkteOpgeslagenUitdaging.isEmpty()) {
            System.out.println(Vertaler.vertaalString("opgeslagenUitdaging") + " " + onafgewerkteOpgeslagenUitdaging);
        } else {
            System.out.println();
            System.out.println(Vertaler.vertaalString("beschikbareUitdagingen"));

            int i = 0;
            for (String[] uitdaging : uitdagingen) {
                System.out.printf("%d: %s, %s(%s wins)%n", i + 1, uitdaging[0], uitdaging[1], uitdaging[2]);
                i++;
            }

            int keuze = 0;
            System.out.println(Vertaler.vertaalString("keuze"));
            try {
                keuze = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("inputGetal"), "\u001B[0m"));
                sc.nextLine();
            }

            while (keuze < 1 || keuze > uitdagingen.length) {
                System.out.println(Vertaler.vertaalString("keuze"));
                try {
                    keuze = sc.nextInt();
                } 
                catch (InputMismatchException e) {
                    System.out.println(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("inputGetal"), "\u001B[0m"));
                    sc.nextLine();
                }
            }

            dc.kiesUitdaging(uitdagingen[keuze - 1][0]);
            String moeilijkheidsgraad = dc.geefMoeilijkheidsgraad();
            ApplicatieStartSpel.toonBord(dc);
            ApplicatieStartSpel.speelSpel(moeilijkheidsgraad, dc);
        }

        LoginApplicatie.menuKeuzes(sc, dc);
    }

}
