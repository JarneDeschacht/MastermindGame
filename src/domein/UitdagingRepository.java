package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import persistentie.UitdagingMapper;

/**
 *
 * @author robbe
 */
public class UitdagingRepository
{

    private final UitdagingMapper uitdagingMapper;

    /** Constructor
     * 
     */
    public UitdagingRepository()
    {
        uitdagingMapper = new UitdagingMapper();
    }

    /** Voegt de uitdaging toe aan de databank
     * 
     * @param uitdager
     * @param uitgedaagde
     * @param spel 
     */
    public void voegToe(Speler uitdager, String uitgedaagde, Spel spel)
    { 
        uitdagingMapper.voegToe(uitdager, uitgedaagde, spel);
    }
    
//    public void updateUitdaging(String uitdager, String uitgedaagde, int aantalpogingen, boolean gewonnen)
//    {
//        uitdagingMapper.updateUitdaging(uitdager, uitgedaagde, aantalpogingen, gewonnen);
//    }
    
    /** Stelt het aantal pogingen in van de speler die het spel heeft afgewerkt
     * 
     * @param uitdagingID
     * @param aantalPogingen
     * @param gebruikersnaam
     * @param codeGekraakt 
     */
    public void updateUitdaging(int uitdagingID, int aantalPogingen, String gebruikersnaam, boolean codeGekraakt)
    {
        uitdagingMapper.updateUitdaging(uitdagingID, aantalPogingen, gebruikersnaam, codeGekraakt);
    }
    
    /** Geeft het uitdagingID weer
     * 
     * @param uitdager
     * @param uitgedaagde
     * @return uitdagingID
     */
    public int geefUitdagingID(String uitdager, String uitgedaagde)
    {
        return uitdagingMapper.geefID(uitdager, uitgedaagde);
    }
    
    /** Geeft een lijst weer van alle openstaande uitdagingen
     * 
     * @param aangemeldeSpelerID
     * @return uitdagingen
     */
    public String[][] geefUitdagingen(int aangemeldeSpelerID)
    {
        return uitdagingMapper.geefUitdagingen(aangemeldeSpelerID);
    }
    
    /** Geeft de gekozen uitdaging weer
     * 
     * @param naamUitdager
     * @param naamUitgedaagde
     * @return uitdaging
     */
    public Uitdaging geefUitdaging(String naamUitdager, String naamUitgedaagde)
    {
        return uitdagingMapper.geefUitdaging(naamUitdager, naamUitgedaagde);
    }
    
    /** Geeft de moeilijkheidsgraad van de uitdaging weer
     * 
     * @param naamUitdager
     * @param naamUitgedaagde
     * @return moeilijkheidsgraad
     */
    public String geefMoeilijkheidsgraadUitdaging(String naamUitdager, String naamUitgedaagde)
    {
        return uitdagingMapper.geefMoeilijkheidsgraadUitdaging(naamUitdager, naamUitgedaagde);
    }
    
    /** Geeft een opgeslagen uitdaging weer die nog niet is afgewerkt
     * 
     * @param uitgedaagdeNaam
     * @return naam uitdaging
     */
    public String opgeslagenOnafgewerkteUitdaging(String uitgedaagdeNaam)
    {
        return uitdagingMapper.onafgewerkteOpgeslagenUitdaging(uitgedaagdeNaam);
    }
    
    /** Geeft een klassement weer van alle spelers in de databank op basis van de verdiende punten bij uitdagingen
     * 
     * @return klassement
     */
    public String[][][] toonKlassement()
    {
        String[][] uitdagingenAfgewerkt = uitdagingMapper.toonKlassement();
        List<String> spelersMakkelijk = new ArrayList();
        List<String> spelersNormaal = new ArrayList();
        List<String> spelersMoeilijk = new ArrayList();
        
        // lijstjes met namen per moeilijkheidsgraad vullen (geen dubbele namen)
        for (int i = 0; i < uitdagingenAfgewerkt.length; i++)
        {
            if(uitdagingenAfgewerkt[i][0].equals("0"))
            {
                if(!spelersMakkelijk.isEmpty())
                {
                    for (int j = 0; j < spelersMakkelijk.size(); j++)
                    {
                        if(!(spelersMakkelijk.get(j).equals(uitdagingenAfgewerkt[i][1]) || spelersMakkelijk.get(j).equals(uitdagingenAfgewerkt[i][2])))
                        {
                            spelersMakkelijk.add(uitdagingenAfgewerkt[i][1]);
                            spelersMakkelijk.add(uitdagingenAfgewerkt[i][2]);
                        }
                    }
                }
                else
                {
                    spelersMakkelijk.add(uitdagingenAfgewerkt[i][1]);
                    spelersMakkelijk.add(uitdagingenAfgewerkt[i][2]);
                }
            }
            else if(uitdagingenAfgewerkt[i][0].equals("1"))
            {
                if(!spelersNormaal.isEmpty())
                {
                    for (int j = 0; j < spelersNormaal.size(); j++)
                    {
                        if(!(spelersNormaal.get(j).equals(uitdagingenAfgewerkt[i][1]) || spelersNormaal.get(j).equals(uitdagingenAfgewerkt[i][2])))
                        {
                            spelersNormaal.add(uitdagingenAfgewerkt[i][1]);
                            spelersNormaal.add(uitdagingenAfgewerkt[i][2]);
                        }
                    }
                }
                else
                {
                    spelersNormaal.add(uitdagingenAfgewerkt[i][1]);
                    spelersNormaal.add(uitdagingenAfgewerkt[i][2]);
                }
            }
            else
            {
                if(!spelersMoeilijk.isEmpty())
                {
                    for (int j = 0; j < spelersMoeilijk.size(); j++)
                    {
                        if(!(spelersMoeilijk.get(j).equals(uitdagingenAfgewerkt[i][1]) || spelersNormaal.get(j).equals(uitdagingenAfgewerkt[i][2])))
                        {
                            spelersMoeilijk.add(uitdagingenAfgewerkt[i][1]);
                            spelersMoeilijk.add(uitdagingenAfgewerkt[i][2]);
                        }
                    }
                }
                else
                {
                    spelersMoeilijk.add(uitdagingenAfgewerkt[i][1]);
                    spelersMoeilijk.add(uitdagingenAfgewerkt[i][2]);
                }
            }
                
        }
        
        //rijen hangen af van het aantal spelers die al een keer in die moeilijkeidsgraad een uitdaging hebben voltooid
        // kolommen zijn 2: naam en punten
        // moeilijkheidsgraad wordt hier niet bijgehouden aangezien het aparte lijstjes zijn per moeilijkheidsgraad
//        String[][] klassementGemakkelijk = new String[spelersMakkelijk.size()][2];
//        String[][] klassementNormaal = new String[spelersNormaal.size()][2];
//        String[][] klassementMoeilijk = new String[spelersMoeilijk.size()][2];
//        
//        // klassementen opbouwen
//        for (int i = 0; i < klassementGemakkelijk.length; i++)
//        {
//            klassementGemakkelijk[i][0] = spelersMakkelijk.get(i); 
//        }
//        for (int i = 0; i < klassementNormaal.length; i++)
//        {
//            klassementNormaal[i][0] = spelersNormaal.get(i);
//        }
//        for (int i = 0; i < klassementMoeilijk.length; i++)
//        {
//            klassementMoeilijk[i][0] = spelersMoeilijk.get(i);
//        }
        //HashMaps maken met de spelers en hun score
        HashMap<String, Integer> hashMapGemakkelijkWins = new HashMap<>();
        HashMap<String, Integer> hashMapGemakkelijkLosses = new HashMap<>();
        HashMap<String, Integer> hashMapNormaalWins = new HashMap<>();
        HashMap<String, Integer> hashMapNormaalLosses = new HashMap<>();
        HashMap<String, Integer> hashMapMoeilijkWins = new HashMap<>();
        HashMap<String, Integer> hashMapMoeilijkLosses = new HashMap<>();
        
        //HashMaps met de spelers vullen
        for(String speler: spelersMakkelijk)
        {
            hashMapGemakkelijkWins.put(speler, 0);
            hashMapGemakkelijkLosses.put(speler, 0);
        }
        
        for(String speler: spelersNormaal)
        {
            hashMapNormaalWins.put(speler, 0);
            hashMapNormaalLosses.put(speler, 0);
        }
        
        for(String speler: spelersMoeilijk)
        {
            hashMapMoeilijkWins.put(speler, 0);
            hashMapMoeilijkLosses.put(speler, 0);
        }
        
        for(String[] uitdaging: uitdagingenAfgewerkt)
        {
            if(uitdaging[0].equals("0"))//Makkelijke uitdaging
            {
                //Uitdager is de winnaar
                if(("1".equals(uitdaging[5]) && "0".equals(uitdaging[6])) || //Uitdager heeft code gekraakt en uitgedaagde niet
                  (("1".equals(uitdaging[5]) && "1".equals(uitdaging[6])) && (Integer.parseInt(uitdaging[3]) <= Integer.parseInt(uitdaging[4]))) || //Uitdager en uitgedaagde hebben code gekraakt, uitdager kraakte de code in evenveel of minder pogingen
                  (uitdaging[5].equals("0")) && uitdaging[6].equals("0")) //Ze hebben beide de code niet gekraakt       
                {
                    hashMapGemakkelijkWins.put(uitdaging[1], hashMapGemakkelijkWins.get(uitdaging[1]) + 1); //Score uitdager incrementeren met 3 punten
                    hashMapGemakkelijkLosses.put(uitdaging[2], hashMapGemakkelijkLosses.get(uitdaging[2]) + 1); //Score uitgedaagde verminderen met 1 punt
                }
                //Uitgedaagde is de winnaar
                else
                {
                    hashMapGemakkelijkLosses.put(uitdaging[1], hashMapGemakkelijkLosses.get(uitdaging[1]) + 1); //Score uitdager verminderen met 1 punt
                    hashMapGemakkelijkWins.put(uitdaging[2], hashMapGemakkelijkWins.get(uitdaging[2]) + 1); //Score uitgedaagde incrementeren met 3 punten
                }
                
                
            }
            else if(uitdaging[0].equals("1"))//Normale uitdaging
            {
                //Uitdager is de winnaar
                if(("1".equals(uitdaging[5]) && "0".equals(uitdaging[6])) || //Uitdager heeft code gekraakt en uitgedaagde niet
                  (("1".equals(uitdaging[5]) && "1".equals(uitdaging[6])) && (Integer.parseInt(uitdaging[3]) <= Integer.parseInt(uitdaging[4]))) || //Uitdager en uitgedaagde hebben code gekraakt, uitdager kraakte de code in evenveel of minder pogingen
                  (uitdaging[5].equals("0")) && uitdaging[6].equals("0")) //Ze hebben beide de code niet gekraakt       
                {
                    hashMapNormaalWins.put(uitdaging[1], hashMapNormaalWins.get(uitdaging[1]) + 1); //Score uitdager incrementeren met 3 punten
                    hashMapNormaalLosses.put(uitdaging[2], hashMapNormaalLosses.get(uitdaging[2]) + 1); //Score uitgedaagde verminderen met 1 punt
                }
                //Uitgedaagde is de winnaar
                else
                {
                    hashMapNormaalLosses.put(uitdaging[1], hashMapNormaalLosses.get(uitdaging[1]) + 1); //Score uitdager verminderen met 1 punt
                    hashMapNormaalWins.put(uitdaging[2], hashMapNormaalWins.get(uitdaging[2]) + 1); //Score uitgedaagde incrementeren met 3 punten
                }
            }
            else //Moeijlijke uitdaging
            {
                //Uitdager is de winnaar
                if(("1".equals(uitdaging[5]) && "0".equals(uitdaging[6])) || //Uitdager heeft code gekraakt en uitgedaagde niet
                  (("1".equals(uitdaging[5]) && "1".equals(uitdaging[6])) && (Integer.parseInt(uitdaging[3]) <= Integer.parseInt(uitdaging[4]))) || //Uitdager en uitgedaagde hebben code gekraakt, uitdager kraakte de code in evenveel of minder pogingen
                  (uitdaging[5].equals("0")) && uitdaging[6].equals("0")) //Ze hebben beide de code niet gekraakt       
                {
                    hashMapMoeilijkWins.put(uitdaging[1], hashMapMoeilijkWins.get(uitdaging[1]) + 1); //Score uitdager incrementeren met 3 punten
                    hashMapMoeilijkLosses.put(uitdaging[2], hashMapMoeilijkLosses.get(uitdaging[2]) + 1); //Score uitgedaagde verminderen met 1 punt
                }
                //Uitgedaagde is de winnaar
                else
                {
                    hashMapMoeilijkLosses.put(uitdaging[1], hashMapMoeilijkLosses.get(uitdaging[1]) + 1); //Score uitdager verminderen met 1 punt
                    hashMapMoeilijkWins.put(uitdaging[2], hashMapMoeilijkWins.get(uitdaging[2]) + 1); //Score uitgedaagde incrementeren met 3 punten
                }
            }
        }//Einde overlopen alle uitdagingen
        
        //Nog te doen:
        //Hashmpas sorteren adhv de score in de hashmaps
        //Hashmaps omzetten naar 2dimensionale array
        
        String[][][] klassement = new String[3][][];
        //Gemakkelijk
        Set<String> hashSpelersMakkelijk = hashMapGemakkelijkWins.keySet();
        klassement[0] = new String[hashSpelersMakkelijk.size()][2];
        
        //Normaal
        Set<String> hashSpelersNormaal = hashMapNormaalWins.keySet();
        klassement[1] = new String[hashSpelersNormaal.size()][2];

        //Moeilijk
        Set<String> hashSpelersMoeilijk = hashMapMoeilijkWins.keySet();
        klassement[2] = new String[hashSpelersMoeilijk.size()][2];
        
        //Hashmaps omzetten naar 3 dimensionale array
        //Gemakkelijk
        for(int i = 0; i < klassement[0].length; i++)
        {
            int hoogsteScore = Integer.MIN_VALUE;
            String naamHoogsteScore = "";
            for(String naam : hashSpelersMakkelijk)
            {
                int score = (hashMapGemakkelijkWins.get(naam) * 3) - hashMapGemakkelijkLosses.get(naam);
                if(score > hoogsteScore)
                {
                    hoogsteScore = score;
                    naamHoogsteScore = naam;
                }
                else if(score == hoogsteScore)
                {
                    double procentHoogste = hashMapGemakkelijkWins.get(naamHoogsteScore) / hashMapGemakkelijkLosses.get(naamHoogsteScore);
                    double procentGeselecteerde = hashMapGemakkelijkWins.get(naam) / hashMapGemakkelijkLosses.get(naam);
                    if(procentGeselecteerde > procentHoogste)
                    {
                        hoogsteScore = score;
                        naamHoogsteScore = naam;
                    }
                }
            }
            klassement[0][i][0] = naamHoogsteScore;
            klassement[0][i][1] = Integer.toString(hoogsteScore);
            hashMapGemakkelijkWins.remove(naamHoogsteScore);
        }
        
        //Normaal -- nog niet klaar
        for(int i = 0; i < klassement[1].length; i++)
        {
            int hoogsteScore = Integer.MIN_VALUE;
            String naamHoogsteScore = "";
            for(String naam : hashSpelersNormaal)
            {
                int score = (hashMapNormaalWins.get(naam) * 3) - hashMapNormaalLosses.get(naam);
                if(score > hoogsteScore)
                {
                    hoogsteScore = score;
                    naamHoogsteScore = naam;
                }
                else if(score == hoogsteScore)
                {
                    double procentHoogste = hashMapNormaalWins.get(naamHoogsteScore) / hashMapNormaalLosses.get(naamHoogsteScore);
                    double procentGeselecteerde = hashMapNormaalWins.get(naam) / hashMapNormaalLosses.get(naam);
                    if(procentGeselecteerde > procentHoogste)
                    {
                        hoogsteScore = score;
                        naamHoogsteScore = naam;
                    }
                }
            }
            klassement[1][i][0] = naamHoogsteScore;
            klassement[1][i][1] = Integer.toString(hoogsteScore);
            hashMapNormaalWins.remove(naamHoogsteScore);
        }
        
        //Moeilijk -- nog niet klaar
        for(int i = 0; i < klassement[2].length; i++)
        {
            int hoogsteScore = Integer.MIN_VALUE;
            String naamHoogsteScore = "";
            for(String naam : hashSpelersMoeilijk)
            {
                int score = (hashMapMoeilijkWins.get(naam) * 3) - hashMapMoeilijkLosses.get(naam);
                if(score > hoogsteScore)
                {
                    hoogsteScore = score;
                    naamHoogsteScore = naam;
                }
                else if(score == hoogsteScore)
                {
                    double procentHoogste = hashMapMoeilijkWins.get(naamHoogsteScore) / hashMapMoeilijkLosses.get(naamHoogsteScore);
                    double procentGeselecteerde = hashMapMoeilijkWins.get(naam) / hashMapMoeilijkLosses.get(naam);
                    if(procentGeselecteerde > procentHoogste)
                    {
                        hoogsteScore = score;
                        naamHoogsteScore = naam;
                    }
                }
            }
            klassement[2][i][0] = naamHoogsteScore;
            klassement[2][i][1] = Integer.toString(hoogsteScore);
            hashMapMoeilijkWins.remove(naamHoogsteScore);
        }
        
        return klassement;
    }
}

