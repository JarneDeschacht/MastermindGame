package ui;

import domein.DomeinController;
import domein.KeuzeMogelijkheden;
import domein.Vertaler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static ui.ApplicatieStartSpel.speelSpel;
import static ui.ApplicatieStartSpel.toonBord;

/**
 *
 * @author robbe
 */
public class ApplicatieDaagUit {

    private final DomeinController controller;

    public ApplicatieDaagUit(DomeinController dc) {
        this.controller = dc;
        String[] oplossing;

        Scanner sc = new Scanner(System.in);
        String moeilijkheidsgraad = "";
        String[] moeilijkheidsgraden = controller.startUitdaging()[0];
        boolean nogHerhalen = true;
        int[] aantalWinsPerMoeilijkheidsgraad = new int[controller.startUitdaging()[1].length];

        for (int i = 0; i < controller.startUitdaging()[1].length; i++) {
            aantalWinsPerMoeilijkheidsgraad[i] = Integer.parseInt(controller.startUitdaging()[1][i]);
        }

        do {
            try {
                if (aantalWinsPerMoeilijkheidsgraad[0] >= 20 && aantalWinsPerMoeilijkheidsgraad[1] < 20) {
                    System.out.printf("%s %s(%d wins) %s %s(%d wins)%n%s: ", Vertaler.vertaalString("daagUitMoeilijkheidsgraad"), moeilijkheidsgraden[0], aantalWinsPerMoeilijkheidsgraad[0], Vertaler.vertaalString("en"), moeilijkheidsgraden[1], aantalWinsPerMoeilijkheidsgraad[1], Vertaler.vertaalString("keuzeMoeilijkheidsgraad"));
                } else if (aantalWinsPerMoeilijkheidsgraad[1] >= 20)
                    System.out.printf("%s %s(%d wins), %s(%d wins) %s %s(%d wins)%n%s: ", Vertaler.vertaalString("daagUitMoeilijkheidsgraad"), moeilijkheidsgraden[0], aantalWinsPerMoeilijkheidsgraad[0], moeilijkheidsgraden[1], aantalWinsPerMoeilijkheidsgraad[1], Vertaler.vertaalString("en"), moeilijkheidsgraden[2], aantalWinsPerMoeilijkheidsgraad[2], Vertaler.vertaalString("keuzeMoeilijkheidsgraad"));
                else 
                {
                    System.out.printf("%s %s(%d wins)%n%s: ", Vertaler.vertaalString("daagUitMoeilijkheidsgraad"), moeilijkheidsgraden[0], aantalWinsPerMoeilijkheidsgraad[0], Vertaler.vertaalString("keuzeMoeilijkheidsgraad"));
                }
                moeilijkheidsgraad = sc.next();
                if (!(moeilijkheidsgraad.equals(moeilijkheidsgraden[0])) && !(moeilijkheidsgraad.equals(moeilijkheidsgraden[1])) && !(moeilijkheidsgraad.equals(moeilijkheidsgraden[2]))) {
                    throw new IllegalArgumentException(String.format("%s %s %s", "\u001B[31m", Vertaler.vertaalString("MoeilijkheidsgraadBestaatNiet"), "\u001B[0m"));
                }
                nogHerhalen = false;
            } catch (IllegalArgumentException e) {
                System.out.println(String.format("%s %s %s", "\u001B[31m", e.getMessage(), "\u001B[0m"));
            }
        } while (nogHerhalen);

        String[][] res = controller.geefBeschikbareSpelers(moeilijkheidsgraad);
        List<String> spelers = new ArrayList<>();
        for (String[] r : res) {
            System.out.printf("%-20s%s%s%n", r[0], Vertaler.vertaalString("winsInMoeilijkheidsgraad"), r[1]);
            spelers.add(r[0]);
        }
        String uitgedaagdeSpeler;
        System.out.printf("%s", Vertaler.vertaalString("kiesSpeler"));
        uitgedaagdeSpeler = sc.next();
        while (!spelers.contains(uitgedaagdeSpeler)) {
            System.out.printf("%s%n", Vertaler.vertaalString("fouteSpelerUitgedaagd"));
            System.out.printf("%s", Vertaler.vertaalString("kiesSpeler"));
            uitgedaagdeSpeler = sc.next();
        }

        controller.kiesSpeler(uitgedaagdeSpeler, moeilijkheidsgraad);

        ApplicatieStartSpel.toonBord(controller);
        ApplicatieStartSpel.speelSpel(moeilijkheidsgraad, controller);

//        if (controller.geefStatus() == 1)
//        {
//            controller.updateUitdaging(uitgedaagdeSpeler, true);
//        } else if (controller.geefStatus() == 2)
//        {
//            controller.updateUitdaging(uitgedaagdeSpeler, false);
//        }
        LoginApplicatie.menuKeuzes(sc, dc);
    }
}
