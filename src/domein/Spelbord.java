package domein;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author robbe
 */
public class Spelbord
{

    private final Vak[] oplossing;
    private final Vak[][] evaluatiepinnen;
    private final Vak[][] speelpinnen;
    private final String[] kleurmogelijkheden;

    /** Geeft de evaluatiepinnen weer van de ingediende poging
     * 
     * @return evaluatiepinnen
     */
    public Vak[][] getEvaluatiepinnen()
    {
        return evaluatiepinnen;
    }
    
    /** Geeft de speelpinnen weer
     * 
     * @return speelpinnen
     */
    public Vak[][] getSpeelpinnen()
    {
        return speelpinnen;
    }
    
    /** Geeft de oplossing van het spel weer
     * 
     * @return oplossing
     */
    public Vak[] getOplossing()
    {
        return oplossing;
    }
    
    /** Geeft de kleurmogelijkheden van de speelpinnen weer
     * 
     * @return kleurmogelijkheden
     */
    public String[] getKleurmogelijkheden()
    {
        return kleurmogelijkheden;
    }

    /** Maakt een spelbord aan voor het spel
     *
     * @param moeilijkheidsgraad
     * @param vertaler
     */
    public Spelbord(String moeilijkheidsgraad, EnumVertaler vertaler)
    {
        kleurmogelijkheden = vertaler.geefKleurmogelijkheden();
        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")) || moeilijkheidsgraad.equals("moeilijkheidsgraad_2"))
        {
            //Bij moeilijk is er plaats voor 5 pinnen
            this.oplossing = new Vak[5];
            this.evaluatiepinnen = new Vak[12][5];
            this.speelpinnen = new Vak[12][5];
        } else
        {
            //Bij makkelijk/normaal is er plaats voor 4 pinnen
            this.oplossing = new Vak[4];
            this.evaluatiepinnen = new Vak[12][4];
            this.speelpinnen = new Vak[12][4];
        }
        //vakken initialiseren
        for (Vak[] speelpinnen1 : this.speelpinnen)
        {
            for (int j = 0; j < speelpinnen1.length; j++)
            {
                speelpinnen1[j] = new Vak();
            }
        }
        for (Vak[] evaluatiepinnen1 : evaluatiepinnen)
        {
            for (int j = 0; j < evaluatiepinnen1.length; j++)
            {
                evaluatiepinnen1[j] = new Vak();
            }
        }
        for (int i = 0; i < oplossing.length; i++)
        {
            this.oplossing[i] = new Vak();
        }

        genereerOplossing(moeilijkheidsgraad);
    }
    
    /** Maakt een spelbord aan voor het spel
     * 
     * @param moeilijkheidsgraad
     * @param vertaler
     * @param speelpinnen
     * @param oplossing 
     */
    public Spelbord(String moeilijkheidsgraad, EnumVertaler vertaler, String[] speelpinnen, String[] oplossing){
        
        this(moeilijkheidsgraad, vertaler);
        
        //gekende waarden invullen
        for (int i = 0; i < (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")) || moeilijkheidsgraad.equals("moeilijkheidsgraad_2")? speelpinnen.length/5 : speelpinnen.length/4); i++)
        {
            for (int j = 0; j < this.speelpinnen[i].length; j++)
            {
                this.speelpinnen[i][j].setKleur(speelpinnen[(this.speelpinnen[i].length * i)+j]);
            }
        }
        for (int i = 0; i < this.oplossing.length; i++)
        {
            this.oplossing[i].setKleur(oplossing[i]);
        }
    }

    /** Geeft een overzicht met de verschillende pogingen en evaluatiepinnen
     * 
     * @param moeilijkheidsgraad
     * @return overzicht
     */
    public String[][] geefOverzichtMetKleuren(String moeilijkheidsgraad)
    {
        //Nieuwe array overzicht waarin de kleuren komen | 13de rij bevat gemaskeerde oplossing
        String[][] overzicht = new String[13][8];
        //Bij moeilijk is er een grotere array nodig
        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")) || moeilijkheidsgraad.equals("moeilijkheidsgraad_2"))
        {
            overzicht = new String[13][10];
        }
        for (int i = 0; i < overzicht.length - 1; i++)
        {
            //Het linkerdeel van de array komen de speelpinnen (geplaatst door de speler)
            for (int j = 0; j < (overzicht[i].length / 2); j++)
            {
                overzicht[i][j] = speelpinnen[i][j].getKleur(); //.equals("rood")? "\u001B[31m rood \u001B[0m" : speelpinnen[i][j].getKleur().equals("oranje")? "\u001B[0m oranje \u001B[0m" : speelpinnen[i][j].getKleur().equals("geel")? "\u001B[33m geel \u001B[0m" : speelpinnen[i][j].getKleur().equals("groen")? "\u001B[32m groen \u001B[0m" : speelpinnen[i][j].getKleur().equals("blauw")? "\u001B[34m blauw \u001B[0m" : speelpinnen[i][j].getKleur().equals("bruin")? "\u001B[0m bruin \u001B[0m" : speelpinnen[i][j].getKleur().equals("paars")? "\u001B[35m paars \u001B[0m" : speelpinnen[i][j].getKleur().equals("grijs")? "\u001B[0m grijs \u001B[0m" : "\u001B[0m leeg \u001B[0m";
            }
            //In het rechterdeel komen de controlepinnen
            for (int j = overzicht[i].length / 2; j < (overzicht[i].length); j++)
            {
                overzicht[i][j] = evaluatiepinnen[i][j - (overzicht[i].length / 2)].getKleur();
            }
        }
        for (int i = 0; i < overzicht[12].length / 2; i++)
        {
            overzicht[12][i] = oplossing[i].getKleur();
        }
        return overzicht;
    }

    /** Genereert een willekeurige oplossing voor het spel
     * 
     * @param moeilijkheidsgraad 
     */
    public final void genereerOplossing(String moeilijkheidsgraad)
    {
        int oplossingLengte;
        SecureRandom rnd = new SecureRandom();

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")))
        {
            oplossingLengte = 5;
        } 
        else
        {
            oplossingLengte = 4;
        }

        if (oplossingLengte == 4 && moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            for (int i = 0; i < oplossingLengte; i++)
            {
                oplossing[i].setKleur(kleurmogelijkheden[rnd.nextInt(8)]);
            }
        }
        else if(oplossingLengte == 4 && moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
        {
            //lijst maken en oplvullen met de mogelijke indexen van kleurmogelijkheden
            List <Integer> random = new ArrayList();
            for(int i=0;i<kleurmogelijkheden.length;i++)
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
            
        }
        else
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

    }
   
    /** Geeft de speelpinnen het gekozen kleur van de speler
     * 
     * @param speelpinnen
     * @param aantalPogingen 
     */
    public void vulSpeelPinnen(String[] speelpinnen, int aantalPogingen)
    {
        //De kleuren van de pinnen instellen
        int i = 0;
        for(Vak pin : this.speelpinnen[aantalPogingen])
        {
            pin.setKleur(speelpinnen[i]);
            i++;
        }
    }
    
    /** Vult de evaluatiepinnen correct in volgens de ingediende poging
     * 
     * @param aantalPogingen
     * @param moeilijkheidsgraad 
     */
    public void vulEvaluatiePinnen(int aantalPogingen, String moeilijkheidsgraad)
    {
        //Als moeilijkheidsgraad gemakkelijk geselecteerd is
        if(moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")) || moeilijkheidsgraad.equals("moeilijkheidsgraad_0"))
        {
            //Alle pinnen overlopen
            for(int i = 0; i < speelpinnen[aantalPogingen].length; i++)
            {
                //Als de het kleur van de speelpin gelijk is aan het kleur van de oplossing
                //In dezelfde positie wordt de evaluatie pin gelijkgesteld aan zwart
                if(speelpinnen[aantalPogingen][i].getKleur().equals(oplossing[i].getKleur()))
                    evaluatiepinnen[aantalPogingen][i].setKleur(Vertaler.vertaalString("zwart"));
                else
                {
                    //Alle oplossingen overlopen om te kijken
                    //alsof een van de oplossingen dezelfde kleur heeft als de speelpin
                    for(Vak o : oplossing)
                    {
                        if(speelpinnen[aantalPogingen][i].getKleur().equals(o.getKleur()))
                            evaluatiepinnen[aantalPogingen][i].setKleur(Vertaler.vertaalString("wit"));
                    }
                }
            }
        }
        //Als moielijkheidsgraad normaal of moeilijk geselecteerd is
        else
        {
            int aantalZwarte = 0;
            int aantalWitte = 0;
            //Alle pinnen overlopen
            for(int i = 0; i < speelpinnen[aantalPogingen].length; i++)
            {
                //Als de het kleur van de speelpin gelijk is aan het kleur van de oplossing
                //In dezelfde positie wordt de evaluatie pin gelijkgesteld aan zwart
                if(speelpinnen[aantalPogingen][i].getKleur().equals(oplossing[i].getKleur()))
                    aantalZwarte++;
                else
                {
                    //Alle oplossingen overlopen om te kijken
                    //alsof een van de oplossingen dezelfde kleur heeft als de speelpin
                    for(Vak o : oplossing)
                    {
                        if(speelpinnen[aantalPogingen][i].getKleur().equals(o.getKleur()))
                        {
                            aantalWitte++;
                            //vanaf dat er je weet dat hij 1 keer voorkomt stoppen met te kijken alsof de kleur voorkomt
                            break;
                        }
                    }
                }
            }
            for(int i = 0; i < speelpinnen[aantalPogingen].length; i++)
            {
                if(aantalZwarte > 0)
                {
                    evaluatiepinnen[aantalPogingen][i].setKleur(Vertaler.vertaalString("zwart"));
                    aantalZwarte--;
                }
                else if(aantalWitte > 0)
                {
                    evaluatiepinnen[aantalPogingen][i].setKleur(Vertaler.vertaalString("wit"));
                    aantalWitte--;
                }
            }
        }
    }
    //
}
