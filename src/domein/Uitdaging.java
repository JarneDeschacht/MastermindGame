package domein;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author joppe
 */
public class Uitdaging
{

    //Attributen
    private Vak[] oplossing;
    private String[] code;

    //Constructor
    
    /** Constructor voor uitdaging, maakt een nieuwe uitdaging aan
     * 
     * @param moeilijkheidsgraad
     * @param vertaler 
     */
    public Uitdaging(String moeilijkheidsgraad, EnumVertaler vertaler)
    {
        String[] kleurmogelijkheden = vertaler.geefKleurmogelijkheden();
        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")))
        {
            //Bij moeilijk is er plaats voor 5 pinnen
            this.oplossing = new Vak[5];
            for (int i = 0; i < 5; i++)
            {
                this.oplossing[i] = new Vak();
            }
        } else
        {
            //Bij makkelijk/normaal is er plaats voor 4 pinnen
            this.oplossing = new Vak[4];
            for (int i = 0; i < 4; i++)
            {
                this.oplossing[i] = new Vak();
            }
        }

        //Oplossing genereren zoals in spelbord (methode genereer oplossing)
        int oplossingLengte;
        SecureRandom rnd = new SecureRandom();

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")))
        {
            oplossingLengte = 5;
        } else
        {
            oplossingLengte = 4;
        }

        if (oplossingLengte == 4 && moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            for (int i = 0; i < oplossingLengte; i++)
            {
                oplossing[i].setKleur(kleurmogelijkheden[rnd.nextInt(8)]);
            }
        } else if (oplossingLengte == 4 && moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
        {
            //lijst maken en oplvullen met de mogelijke indexen van kleurmogelijkheden
            List<Integer> random = new ArrayList();
            for (int i = 0; i < kleurmogelijkheden.length; i++)
            {
                random.add(i);
            }

            //de lijst random door elkaar smijten
            Collections.shuffle(random);
            //de eerste 4 indexen er uit halen --> zijn random bepaald en uniek
            for (int i = 0; i < oplossingLengte; i++)
            {
                oplossing[i].setKleur(kleurmogelijkheden[random.get(i)]);
            }

        } else
        {
            int aantalLeeg = 0;
            int randomKleur;
            for (int i = 0; i < oplossingLengte; i++)
            {
                if (aantalLeeg < 2)
                {
                    randomKleur = rnd.nextInt(9);
                    if (randomKleur == 8)
                    {
                        oplossing[i].setKleur(Vertaler.vertaalString("leeg"));
                        aantalLeeg++;
                    } else
                    {
                        oplossing[i].setKleur(kleurmogelijkheden[rnd.nextInt(8)]);

                    }
                } else
                {
                    oplossing[i].setKleur(kleurmogelijkheden[rnd.nextInt(8)]);
                }
            }
        }

        this.code = new String[oplossing.length];

        for (int k = 0; k < oplossing.length; k++)
        {
            code[k] = oplossing[k].getKleur();
        }
    }
    
    /** Constructor op basis van de oplossing
     * 
     * @param code 
     */
    public Uitdaging(String[] code)
    {
        this.code = code;
    }
    
    /** Geeft de oplossing weer van de uitdaging
     * 
     * @return code
     */
    public String[] getCode()
    {
        return code;
    }

//    public Spel startUitdaging(String moeilijkheidsgraad, EnumVertaler vertaler)
//    {
//        Spel hetSpel = new Spel(moeilijkheidsgraad, vertaler, aangemeldeSpeler, code);
//        return hetSpel;
//    }
    //
}
