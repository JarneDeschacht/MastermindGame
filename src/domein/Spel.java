package domein;

public class Spel
{

    private final String[] moeilijkheidsgraden;
    private final String moeilijkheidsgraad;
    private final Spelbord hetSpelbord;
    private String naam;
    private int aantalPogingen = 0;
    private Speler aangemeldeSpeler;
    private int uitdagingID = 0;

    /**
     * Maakt een nieuw spel aan
     *
     * @param moeilijkheidsgraad
     * @param vertaler
     * @param aangemeldeSpeler
     */
    public Spel(String moeilijkheidsgraad, EnumVertaler vertaler, Speler aangemeldeSpeler)
    {
        this.moeilijkheidsgraden = vertaler.geefMoeilijkheidsgraden();
        controleerMoeilijkheidsgraad(moeilijkheidsgraad);
        this.moeilijkheidsgraad = moeilijkheidsgraad;
        this.hetSpelbord = new Spelbord(moeilijkheidsgraad, vertaler);
        this.aangemeldeSpeler = aangemeldeSpeler;
    }

    /**
     * Maakt een nieuw spel aan
     *
     * @param moeilijkheidsgraad
     * @param vertaler
     * @param aangemeldeSpeler
     * @param oplossing
     */
    public Spel(String moeilijkheidsgraad, EnumVertaler vertaler, Speler aangemeldeSpeler, String[] oplossing)
    {
        String[] leeg = new String[0];
        this.moeilijkheidsgraden = vertaler.geefMoeilijkheidsgraden();
        controleerMoeilijkheidsgraad(moeilijkheidsgraad);
        this.moeilijkheidsgraad = moeilijkheidsgraad;
        this.hetSpelbord = new Spelbord(moeilijkheidsgraad, vertaler, leeg, oplossing);
        this.aangemeldeSpeler = aangemeldeSpeler;
    }

    /**
     * Maakt een nieuw spel aan
     *
     * @param moeilijkheidsgraad
     * @param vertaler
     * @param aangemeldeSpeler
     * @param aantalPogingen
     * @param speelpinnen
     * @param oplossing
     * @param uitdagingID
     */
    public Spel(String moeilijkheidsgraad, EnumVertaler vertaler, Speler aangemeldeSpeler, int aantalPogingen, String[] speelpinnen, String[] oplossing, int uitdagingID)
    {
        this.moeilijkheidsgraden = vertaler.geefMoeilijkheidsgraden();
        controleerMoeilijkheidsgraad(moeilijkheidsgraad);
        this.moeilijkheidsgraad = moeilijkheidsgraad;
        this.aangemeldeSpeler = aangemeldeSpeler;
        this.aantalPogingen = aantalPogingen;
        this.uitdagingID = uitdagingID;
        this.hetSpelbord = new Spelbord(moeilijkheidsgraad, vertaler, speelpinnen, oplossing);
        for (int i = 0; i < aantalPogingen; i++)
        {
            hetSpelbord.vulEvaluatiePinnen(i, moeilijkheidsgraad);
        }
    }

    /**
     * Geeft de moeilijkheidsgraden weer
     *
     * @return moeilijkheidsgraden
     */
    public String[] getMoeilijkheidsgraden()
    {
        return moeilijkheidsgraden;
    }

    /**
     * Geeft de aangemelde speler weer
     *
     * @return aangemelde speler
     */
    public Speler getAangemeldeSpeler()
    {
        return aangemeldeSpeler;
    }

    /**
     * Geeft het spelbord weer
     *
     * @return spelbord
     */
    public Spelbord getSpelbord()
    {
        return hetSpelbord;
    }

    /**
     * Geeft de moeilijkheidsgraad van het spel weer
     *
     * @return moeilijkheidsgraad
     */
    public String getMoeilijkheidsgraad()
    {
        return moeilijkheidsgraad;
    }

    /**
     * Stelt de naam van het spel in
     *
     * @param naam
     */
    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    /**
     * Geeft de naam van het spel weer
     *
     * @return naam
     */
    public String getNaam()
    {
        return naam;
    }

    /**
     * Geeft het aantal pogingen weer
     *
     * @return aantal pogingen
     */
    public int getAantalPogingen()
    {
        return aantalPogingen;
    }

    /**
     * Stelt het aantal pogingen in
     *
     * @param aantalPogingen
     */
    public void setAantalPogingen(int aantalPogingen)
    {
        this.aantalPogingen = aantalPogingen;
    }

    /**
     * Geeft het uitdagingID weer
     *
     * @return uitdagingID
     */
    public int getUitdagingID()
    {
        return uitdagingID;
    }

    /**
     * Stelt het uitdagingID in
     *
     * @param uitdagingID
     */
    public void setUitdagingID(int uitdagingID)
    {
        this.uitdagingID = uitdagingID;
    }

    /**
     * Geeft de oplossing weer
     *
     * @return gekraakte code
     */
    public String geefGekraakteCode()
    {
        String res = "";
        Vak[] opl = hetSpelbord.getOplossing();
        for (int i = 0; i < opl.length; i++)
        {
            res += String.format("%-10s", opl[i].getKleur());
        }
        return res;
    }

    /**
     * Controleert of de ingevoerde moeilijkheidsgraad klopt
     *
     * @param moeilijkheidsgraad
     * @exception IllegalArgumentException
     */
    private void controleerMoeilijkheidsgraad(String moeilijkheidsgraad)
    {
        if (!(moeilijkheidsgraad.equals(moeilijkheidsgraden[0]) || moeilijkheidsgraad.equals(moeilijkheidsgraden[1]) || moeilijkheidsgraad.equals(moeilijkheidsgraden[2])))
        {
            if (!(moeilijkheidsgraad.equals("moeilijkheidsgraad_0") || moeilijkheidsgraad.equals("moeilijkheidsgraad_1") || moeilijkheidsgraad.equals("moeilijkheidsgraad_2")))
            {
                throw new IllegalArgumentException(String.format("%s: %s, %s en %s", Vertaler.vertaalString("IAEmoeilijkheidsgraad"), moeilijkheidsgraden[0], moeilijkheidsgraden[1], moeilijkheidsgraden[2]));
            }

        }
    }

    /**
     * Geeft de kleurmogelijkheden van de speelpinnen weer
     *
     * @return kleurmogelijkheden
     */
    public String[] geefKleurmogelijkheden()
    {
        return hetSpelbord.getKleurmogelijkheden();
    }

    /**
     * Laat de speler een poging indienen
     *
     * @param speelpinnen
     */
    public void dienPogingIn(String[] speelpinnen)
    {
        hetSpelbord.vulSpeelPinnen(speelpinnen, aantalPogingen);
        hetSpelbord.vulEvaluatiePinnen(aantalPogingen, moeilijkheidsgraad);
        aantalPogingen++;
    }

    /**
     * Geeft aan of het spel gewonnen, bezig of verloren is
     *
     * @return status
     */
    public int geefStatus()
    {
        //De evaluatie pinnen opvragen
        Vak[][] evaluatiepinnen = hetSpelbord.getEvaluatiepinnen();
        final int WINNAAR = 1;
        final int VERLIEZER = 2;
        final int BEZIG = 3;

        int eindeSpel = WINNAAR;
        boolean allesZwart = true;
        //Controlleren per evaluatiepin alsof er al dan niet een zwarte pin opstaat
        for (Vak eval : evaluatiepinnen[aantalPogingen - 1])
        {
            if (!eval.getKleur().equals(Vertaler.vertaalString("zwart")))
            {
                allesZwart = false;
            }
        }

        if (aantalPogingen < 12)
        {
            if (allesZwart == false)
            {
                eindeSpel = BEZIG;
            } else
            {
                eindeSpel = WINNAAR;

            }
        } //Stoppen op na de 12 poging
        else
        {
            if (allesZwart == false)
            {
                eindeSpel = VERLIEZER;
            } else
            {
                eindeSpel = WINNAAR;
            }
        }
        return eindeSpel;
    }

    /**
     * Geeft aan of het einde van het spel bereikt is
     *
     * @return einde of niet
     */
    public boolean isEindeSpel()
    {
        //De evaluatie pinnen opvragen
        Vak[][] evaluatiepinnen = hetSpelbord.getEvaluatiepinnen();
        boolean allesZwart = true;

        if (aantalPogingen == 0)//Zorgen dat hij niet crashed bij het laden van een spel
        {
            allesZwart = false;
        } else
        {
            //Controleren per evaluatiepin alsof er al dan niet een zwarte pin opstaat
            for (Vak eval : evaluatiepinnen[aantalPogingen - 1])
            {
                if (!eval.getKleur().equals(Vertaler.vertaalString("zwart")))
                {
                    allesZwart = false;
                }
            }
        }

        if (allesZwart == false && aantalPogingen < 12)
        {
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * Verhoogt het aantal wins van de speler in de databank bij de juiste
     * moeilijkheidsgraad
     *
     */
    public void voegWinToe()
    {
        int[] wins = aangemeldeSpeler.getAantalWinsPerMoeilijkheidsgraad();

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
        {
            wins[0] += 1;
        } else if (moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            wins[1] += 1;
        } else
        {
            wins[2] += 1;
        }

        aangemeldeSpeler.setAantalWinsPerMoeilijkheidsgraad(wins);
    }
    //
}
