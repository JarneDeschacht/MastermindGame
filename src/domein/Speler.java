package domein;

public class Speler
{

    private String gebruikersnaam;
    private String wachtwoord;
    private int[] aantalWinsPerMoeilijkheidsgraad = new int[3];
    private Uitdaging deUitdaging;

    /** Maakt een nieuwe speler aan
     * 
     * @param gebruikersnaam
     * @param wachtwoord 
     */
    public Speler(String gebruikersnaam, String wachtwoord)
    {
        setGebruikersnaam(gebruikersnaam);
        setWachtwoord(wachtwoord);
    }
    
    /** Maakt een nieuwe speler aan
     * 
     * @param gebruikersnaam 
     */
    public Speler(String gebruikersnaam)
    {
        setGebruikersnaam(gebruikersnaam);
    }

    /** Maakt een nieuwe speler aan
     * 
     * @param gebruikersnaam
     * @param winsMakkelijk
     * @param winsNormaal
     * @param winsMoeilijk 
     */
    public Speler(String gebruikersnaam, int winsMakkelijk, int winsNormaal, int winsMoeilijk)
    {
        setGebruikersnaam(gebruikersnaam);
        aantalWinsPerMoeilijkheidsgraad[0] = winsMakkelijk;
        aantalWinsPerMoeilijkheidsgraad[1] = winsNormaal;
        aantalWinsPerMoeilijkheidsgraad[2] = winsMoeilijk;
    }

    /** Geeft de gebruikersnaam weer
     * 
     * @return gebruikersnaam
     */
    public String getGebruikersnaam()
    {
        return gebruikersnaam;
    }

    /** Geeft het aantal wins per moeilijkheidsgraad weer van de speler
     * 
     * @return aantal wins
     */
    public int[] getAantalWinsPerMoeilijkheidsgraad()
    {
        return aantalWinsPerMoeilijkheidsgraad;
    }

    /** Stelt de gebruikersnaam in
     * 
     * @param gebruikersnaam 
     * @exception IllegalArgumentException
     */
    public final void setGebruikersnaam(String gebruikersnaam)
    {
        if (gebruikersnaam == null || gebruikersnaam.length() == 0)
        {
            throw new IllegalArgumentException(Vertaler.vertaalString("IAELegeGebruikersnaam"));
        }
        this.gebruikersnaam = gebruikersnaam;
    }

    /** Stelt de aantal wins per moeilijkheidsgraad in
     * 
     * @param aantalWinsPerMoeilijkheidsgraad 
     */
    public final void setAantalWinsPerMoeilijkheidsgraad(int[] aantalWinsPerMoeilijkheidsgraad)
    {
        this.aantalWinsPerMoeilijkheidsgraad = aantalWinsPerMoeilijkheidsgraad;
    }

    /** Geeft het wachtwoord van de speler weer
     * 
     * @return wachtwoord
     */
    public String getWachtwoord()
    {
        return wachtwoord;
    }

    /** Stelt het wachtwoord van de speler in
     * 
     * @param wachtwoord 
     * @exception IllegalArgumentException
     */
    public final void setWachtwoord(String wachtwoord)
    {
        if (wachtwoord == null || wachtwoord.length() == 0)
        {
            throw new IllegalArgumentException(Vertaler.vertaalString("IAELeegWachtwoord"));
        }
        this.wachtwoord = wachtwoord;
    }

    /** Stelt de uitdaging in
     * 
     * @param deUitdaging 
     */
    public final void setDeUitdaging(Uitdaging deUitdaging)
    {
        this.deUitdaging = deUitdaging;
    }    

    /** Controleert of het wachtwoord voldoet aan de domeinregels
     * 
     * @param wachtwoord
     * @return 
     */
    public boolean controleerWachtwoord(String wachtwoord)
    {
        boolean correct = true;
        if (wachtwoord.length() < 8)
        {
            correct = false;
        } else
        {
            for (int i = 0; i < wachtwoord.length(); i++)
            {
                if (i == 0 || i == wachtwoord.length() - 1)
                {
                    if (!Character.isDigit(wachtwoord.charAt(i)))
                    {
                        correct = false;
                    }
                } else
                {
                    if (!Character.isAlphabetic(wachtwoord.charAt(i)))
                    {
                        correct = false;
                    }
                }
            }
        }
        return correct;
    }

    /** Bepaalt het aantal sterren per moeilijkheidsgraad van de speler
     * 
     * @param moeilijkheidsgraad
     * @return aantal sterren
     */
    public int bepaalAantalSterren(String moeilijkheidsgraad)
    {
        int res = 0;

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
        {
            if (aantalWinsPerMoeilijkheidsgraad[0] < 10)
            {
                res = 0;
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 20)
            {
                res = 1;
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 50)
            {
                res = 2;
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 100)
            {
                res = 3;
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 250)
            {
                res = 4;
            } else
            {
                res = 5;
            }
        }

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            if (aantalWinsPerMoeilijkheidsgraad[1] < 10)
            {
                res = 0;
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 20)
            {
                res = 1;
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 50)
            {
                res = 2;
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 100)
            {
                res = 3;
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 250)
            {
                res = 4;
            } else
            {
                res = 5;
            }
        }

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")))
        {
            if (aantalWinsPerMoeilijkheidsgraad[2] < 10)
            {
                res = 0;
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 20)
            {
                res = 1;
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 50)
            {
                res = 2;
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 100)
            {
                res = 3;
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 250)
            {
                res = 4;
            } else
            {
                res = 5;
            }
        }
        return res;
    }

    /** Bepaalt het aantal te winnen spelletjes tot de volgende ster
     * 
     * @param moeilijkheidsgraad
     * @return aantal te winnen spelletjes
     */
    public int bepaalAantalTeWinnenSpellen(String moeilijkheidsgraad)
    {
        int res = 0;

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
        {
            if (aantalWinsPerMoeilijkheidsgraad[0] < 10)
            {
                res = 10 - aantalWinsPerMoeilijkheidsgraad[0];
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 20)
            {
                res = 20 - aantalWinsPerMoeilijkheidsgraad[0];
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 50)
            {
                res = 50 - aantalWinsPerMoeilijkheidsgraad[0];
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 100)
            {
                res = 100 - aantalWinsPerMoeilijkheidsgraad[0];
            } else if (aantalWinsPerMoeilijkheidsgraad[0] < 250)
            {
                res = 250 - aantalWinsPerMoeilijkheidsgraad[0];
            }
        }

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            if (aantalWinsPerMoeilijkheidsgraad[1] < 10)
            {
                res = 10 - aantalWinsPerMoeilijkheidsgraad[1];
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 20)
            {
                res = 20 - aantalWinsPerMoeilijkheidsgraad[1];
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 50)
            {
                res = 50 - aantalWinsPerMoeilijkheidsgraad[1];
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 100)
            {
                res = 100 - aantalWinsPerMoeilijkheidsgraad[1];
            } else if (aantalWinsPerMoeilijkheidsgraad[1] < 250)
            {
                res = 250 - aantalWinsPerMoeilijkheidsgraad[1];
            }
        }

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk")))
        {
            if (aantalWinsPerMoeilijkheidsgraad[2] < 10)
            {
                res = 10 - aantalWinsPerMoeilijkheidsgraad[2];
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 20)
            {
                res = 20 - aantalWinsPerMoeilijkheidsgraad[2];
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 50)
            {
                res = 50 - aantalWinsPerMoeilijkheidsgraad[0];
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 100)
            {
                res = 100 - aantalWinsPerMoeilijkheidsgraad[2];
            } else if (aantalWinsPerMoeilijkheidsgraad[2] < 250)
            {
                res = 250 - aantalWinsPerMoeilijkheidsgraad[2];
            }
        }
        return res;
    }
    
    /** Maakt de uitdaging aan
     * 
     * @param moeilijkheidsgraad
     * @param vertaler
     * @return 
     */
    public String[] maakUitdaging(String moeilijkheidsgraad,EnumVertaler vertaler)
    {
        deUitdaging = new Uitdaging(moeilijkheidsgraad, vertaler);
        return deUitdaging.getCode();
    }
    
    /** Geeft de oplossing van de uitdaging weer
     * 
     * @return oplossing uitdaging
     */
    public String[] geefUitdagingCode()
    {
        return deUitdaging.getCode();
    }
}
