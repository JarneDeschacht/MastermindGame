package domein;

public class DomeinController
{

    private Speler deSpeler;
    private final SpelerRepository spelerRepository;
    private final SpelRepository spelRepository;
    private final UitdagingRepository uitdagingRepository;
    private Spel hetSpel;
    private final EnumVertaler vertaler;

    public DomeinController()
    {
        spelerRepository = new SpelerRepository();
        spelRepository = new SpelRepository();
        uitdagingRepository = new UitdagingRepository();
        vertaler = new EnumVertaler();
    }

    /** Stelt de speler in
     * @param speler */
    public void setDeSpeler(Speler speler)
    {
        this.deSpeler = speler;
    }

    /** laat de speler aanmelden, geeft een foutmelding indien gebruikersnaam/wachtwoord incorrect
     * @param gebruikersnaam
     * @param wachtwoord
     * @exception IllegalArgumentException*/
    public void meldAan(String gebruikersnaam, String wachtwoord)
    {
        Speler gevondenSpeler = spelerRepository.geefSpeler(gebruikersnaam, wachtwoord);
        if (gevondenSpeler != null)
        {
            setDeSpeler(gevondenSpeler);
        } else
        {
            throw new IllegalArgumentException(Vertaler.vertaalString("inloggenMislukt"));
        }
    }

    /** Geeft de gebruikersnaam van de ingelogde speler weer
     * @return gebruikersnaam*/
    public String geefGebruikersnaam()
    {
        return deSpeler.getGebruikersnaam();
    }
    
    /** Registreert een nieuwe speler, volgens de domeinregels
     * @param gebruikersnaam
     * @param wachtwoord*/
    public void registreer(String gebruikersnaam, String wachtwoord)
    {
        Speler nieuweSpeler = new Speler(gebruikersnaam, wachtwoord);
        setDeSpeler(nieuweSpeler);
        spelerRepository.voegToe(nieuweSpeler);
    }

    /** Slaat een spel op in de databank onder de ingegeven naam
     * @param naam*/
    public void slaOp(String naam)
    {
        hetSpel.setNaam(naam);
        spelRepository.voegToe(hetSpel, vertaler, hetSpel.getAangemeldeSpeler());
    }

    /** Controleert of het ingegeven wachtwoord bij de registratie voldoet aan de domeinregels
     * @param wachtwoord 
     * @return boolean */
    public boolean controleerWachtwoord(String wachtwoord)
    {
        Speler nieuweSpeler = new Speler("test", wachtwoord);
        return nieuweSpeler.controleerWachtwoord(wachtwoord);
    }

    /** Maakt een nieuw spel aan
     * @param moeilijkheidsgraad*/
    public void maakNieuwSpel(String moeilijkheidsgraad)
    {
        hetSpel = new Spel(moeilijkheidsgraad, vertaler, deSpeler);
    }

    /** Geeft een lijst met de beschikbare moeilijkheidsgraden voor de aangemelde speler weer
     * @return moeilijkheidsgraden */
    public String[] geefMoeilijkheidsgraden()
    {
        return vertaler.geefMoeilijkheidsgraden();
    }

    /** Geeft het aantal gewonnen spelletjes per moeilijkheidsgraad weer
     * @return aantalWinsPerMoeilijkheidsgraad*/
    public int[] geefAantalKeerGewonnen()
    {
        return deSpeler.getAantalWinsPerMoeilijkheidsgraad();
    }

    /** Geeft het spelbord weer 
     * @return spelbord*/
    public String[][] geefSpelbord()
    {
        return hetSpel.getSpelbord().geefOverzichtMetKleuren(hetSpel.getMoeilijkheidsgraad());
    }
    
    /** Geeft alle kleurmogelijkheden van het spel weer 
     * @return kleurmogelijkheden*/
    public String[] geefKleurmogelijkheden()
    {
        return vertaler.geefKleurmogelijkheden();
    }

    /** Laat de speler een poging indienen om de code te kraken 
     * @param speelpinnen*/
    public void dienPogingIn(String[] speelpinnen)
    {
        hetSpel.dienPogingIn(speelpinnen);
    }

    /** Controleert of het einde van het spel is bereikt, door winst of door het overschrijden van het aantal pogingen 
     * @return einde of niet*/
    public boolean isEindeSpel()
    {
        return hetSpel.isEindeSpel();
    }

    /** Geeft weer of het spel gewonnen is of niet 
     * @return gewonnen of niet*/
    public int geefStatus()
    {
        return hetSpel.geefStatus();
    }

    /** Geeft de oplossing weer 
     * @return gekraakte code*/
    public String geefGekraakteCode()
    {
        return hetSpel.geefGekraakteCode();
    }

    /** Geeft het aantal pogingen weer die ondernomen werden om de code te kraken 
     * @return aantal pogingen*/
    public int geefAantalPogingen()
    {
        return hetSpel.getAantalPogingen();
    }

    /** Geeft het aantal sterren van de speler weer in de moeilijkheidsgraad van het gespeelde spel 
     * @return aantal sterren*/
    public int geefAantalSterren()
    {
        return hetSpel.getAangemeldeSpeler().bepaalAantalSterren(hetSpel.getMoeilijkheidsgraad());
    }

    /** Geeft het aantal te winnen spelletjes weer die nodig zijn om de volgende ster te behalen 
     * @return aantal te winnen spellen*/
    public int geefAantalTeWinnenSpellen()
    {
        return hetSpel.getAangemeldeSpeler().bepaalAantalTeWinnenSpellen(hetSpel.getMoeilijkheidsgraad());
    }

    /** Verhoogt het aantal wins van de speler met 1 in de databank bij de juiste moeilijkheidsgraad */
    public void voegWinToe()
    {
        hetSpel.voegWinToe();
        spelerRepository.voegWinToe(hetSpel.getMoeilijkheidsgraad(), hetSpel.getAangemeldeSpeler());
        
        if(hetSpel.getUitdagingID() != 0)
        {
            boolean gewonnen = false;
            if(hetSpel.geefStatus() == 1)
                gewonnen = true;
            updateUitdaging(hetSpel.getUitdagingID(), hetSpel.getAantalPogingen(), gewonnen);
        }
    }

    /** Geeft de opgeslagen spelletjes van de speler weer 
     * @return opgeslagen spelletjes*/
    public String[][] geefSpelletjes()
    {
        return spelRepository.geefSpelletjes(deSpeler.getGebruikersnaam());
    }

    /** Laat de speler een opgeslagen spel kiezen om te hervatten 
     * @param naam*/
    public void kiesSpel(String naam)
    {
        Spel spel = spelRepository.kiesSpel(naam, deSpeler, vertaler);
        hetSpel = spel;
    }

    /** Geeft de moeilijkheidsgraad van het spel weer 
     * @return moeilijkheidsgraad*/
    public String geefMoeilijkheidsgraad()
    {
        return hetSpel.getMoeilijkheidsgraad();
    }

    /** Geeft een lijst met spelers beschikbaar om uit te dagen in de gekozen moeilijkheidsgraad 
     * @param moeilijkheidsgraad
     * @return beschikbare spelers*/
    public String[][] geefBeschikbareSpelers(String moeilijkheidsgraad)
    {
        Speler[] spelers = spelerRepository.geefBeschikbareSpelers(deSpeler.getGebruikersnaam(), moeilijkheidsgraad);
        String[][] output = new String[spelers.length][2];
        int i = 0;

        for (Speler s : spelers)
        {
            output[i][0] = s.getGebruikersnaam();
            int[] wins = s.getAantalWinsPerMoeilijkheidsgraad();
            if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
            {
                output[i][1] = String.format("%d", wins[0]);
            } else if (moeilijkheidsgraad.equals(Vertaler.vertaalString(("normaal"))))
            {
                output[i][1] = String.format("%d", wins[1]);
            } else
            {
                output[i][1] = String.format("%d", wins[2]);
            }
            i++;
        }

        return output;
    }

    /** Laat de speler een tegenstander kiezen voor de uitdaging 
     * @param gebruikersnaam
     * @param moeilijkheidsgraad*/
    public void kiesSpeler(String gebruikersnaam, String moeilijkheidsgraad)
    {
        String[] code;
        code = deSpeler.maakUitdaging(moeilijkheidsgraad, vertaler);
        hetSpel = new Spel(moeilijkheidsgraad, vertaler, deSpeler, code);
        voegUitdagingToe(deSpeler, gebruikersnaam, hetSpel);
        hetSpel.setUitdagingID(geefUitdagingID(deSpeler.getGebruikersnaam(), gebruikersnaam));
    }

    /** Start de uitdaging op, een nieuw spel wordt gemaakt dat bij de uitgedaagde speler onder "wie daagt mij uit?" te vinden is 
     * @return spelbord uitdaging*/
    public String[][] startUitdaging()
    {
        String[][] uitkomst = new String[2][3];
        uitkomst[0] = geefMoeilijkheidsgraden();
        for (int i = 0; i < geefAantalKeerGewonnen().length; i++)
        {
            uitkomst[1][i] = Integer.toString(geefAantalKeerGewonnen()[i]);
        }
        return uitkomst;
    }
    
    /** Geeft het ID van de uitdaging weer uit de databank 
     * @param uitdager
     * @param uitgedaagde
     * @return uitdagingID*/
    public int geefUitdagingID(String uitdager, String uitgedaagde)
    {
        return uitdagingRepository.geefUitdagingID(uitdager, uitgedaagde);
    }
 
    /** Voegt de uitdaging toe aan de databank 
     * @param uitdager
     * @param uitgedaagde 
     * @param spel*/
    public void voegUitdagingToe(Speler uitdager, String uitgedaagde, Spel spel){
        uitdagingRepository.voegToe(uitdager, uitgedaagde, spel);
    }
    
    /** Update de uitdaging in de databank met de aantal pogingen van de uitdager en of de code al dan niet gekraakt is 
     * @param uitdagingID
     * @param aantalPogingen
     * @param codeGekraakt*/
    public void updateUitdaging(int uitdagingID, int aantalPogingen, boolean codeGekraakt)
    {
        uitdagingRepository.updateUitdaging(uitdagingID, aantalPogingen, deSpeler.getGebruikersnaam(), codeGekraakt);
    }
    
    /** Geeft alle openstaande uitdagingen weer 
     * @return uitdagingen*/ 
    public String[][] geefUitdagingenSpeler()
    {
        String[][] uitdagingen = uitdagingRepository.geefUitdagingen(spelerRepository.geefID(deSpeler.getGebruikersnaam()));
        String[][] uitdagingenInfo = new String[uitdagingen.length][3];
        int i = 0;
        int[] wins = deSpeler.getAantalWinsPerMoeilijkheidsgraad();
        
        for(String[] uitdaging : uitdagingen)
        {
            uitdagingenInfo[i][0] = spelerRepository.geefGebruikersnaamSpeler(Integer.parseInt(uitdaging[0]));
            
            if(Integer.parseInt(uitdaging[1]) == 0)
            {
                uitdagingenInfo[i][1] = Vertaler.vertaalString("gemakkelijk");
                uitdagingenInfo[i][2] = Integer.toString(wins[0]);
            }   
            else if(Integer.parseInt(uitdaging[1]) == 1)
            {
                uitdagingenInfo[i][1] = Vertaler.vertaalString("normaal");
                uitdagingenInfo[i][2] = Integer.toString(wins[1]);
            }
            else 
            {
                uitdagingenInfo[i][1] = Vertaler.vertaalString("moeilijk");
                uitdagingenInfo[i][2] = Integer.toString(wins[2]);
            }
            
            i++;
            
        }
       return uitdagingenInfo;
    }
    
    /** Laat de speler een uitdaging kiezen om aan te gaan 
     * @param naamUitdager*/
    public void kiesUitdaging(String naamUitdager)
    {
        String naamUitgedaagde = deSpeler.getGebruikersnaam();
        deSpeler.setDeUitdaging(uitdagingRepository.geefUitdaging(naamUitdager, naamUitgedaagde));
        String code[];
        code = deSpeler.geefUitdagingCode();
        String moeilijkheidsgraad = uitdagingRepository.geefMoeilijkheidsgraadUitdaging(naamUitdager, naamUitgedaagde);
        hetSpel = new Spel(moeilijkheidsgraad, vertaler, deSpeler, code);
        int uitdagingID = uitdagingRepository.geefUitdagingID(naamUitdager, naamUitgedaagde);
        hetSpel.setUitdagingID(uitdagingID);
    }
    
    /** Geeft de uitdagingen weer die nog niet zijn afgewerkt 
     * @return onafgewerkteUitdaging*/
    public String onafgewerkteOpgeslagenUitdaging()
    {
        String uitgedaagdeNaam = deSpeler.getGebruikersnaam();
        return uitdagingRepository.opgeslagenOnafgewerkteUitdaging(uitgedaagdeNaam);
    }
    
    public String[][][] toonKlassement()
    {
        return uitdagingRepository.toonKlassement();
    }
}
