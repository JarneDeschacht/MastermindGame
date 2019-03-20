package domein;

import java.util.ArrayList;
import java.util.List;
import persistentie.SpelerMapper;

public class SpelerRepository
{
    private final SpelerMapper spelerMapper;
    private List<Speler> spelers;

    
    /** Constructor
     * 
     */
    public SpelerRepository()
    {
        spelerMapper = new SpelerMapper();
        spelers = new ArrayList<>();       
    }
    
    /** Gaat na of de gebruikersnaam al bestaat in de databank
     * 
     * @param gebruikersnaam
     * @return boolean
     */
    private boolean bestaatSpeler(String gebruikersnaam)
    {
        return spelerMapper.geefSpeler(gebruikersnaam)!=null;
    }
    
    /** Geeft de speler weer wanneer deze succesvol aangemeld is
     * 
     * @param gebruikersnaam
     * @param wachtwoord
     * @return Speler
     */
    public Speler geefSpeler(String gebruikersnaam,String wachtwoord)
    {
        Speler speler = spelerMapper.geefSpeler(gebruikersnaam);
        if (speler.getWachtwoord().equals(wachtwoord))
            return speler;
        else return null;
    }
    
    /** Voegt nieuwe speler toe aan de databank wanneer deze nog niet bestaat
     * 
     * @param speler 
     */
    public void voegToe(Speler speler)
    {
       if (bestaatSpeler(speler.getGebruikersnaam()))
            throw new IllegalArgumentException(Vertaler.vertaalString("IAEGebruikersnaamBestaatAl"));
       
       spelerMapper.voegToe(speler); 
    }
    
    /** Geeft het spelerID weer van de aangemelde speler
     * 
     * @param gebruikersnaam
     * @return spelerID
     */
    public int geefID(String gebruikersnaam){
        return spelerMapper.geefID(gebruikersnaam);
    }
    
    /** Verhoogt het aantal wins van de speler met 1 bij de juiste moeilijkheidsgraad
     * 
     * @param moeilijkheidsgraad
     * @param speler 
     */
    public void voegWinToe(String moeilijkheidsgraad, Speler speler){
        spelerMapper.voegWinToe(moeilijkheidsgraad, new SpelerRepository(), speler);
    }
    
    /** Geeft een lijst van spelers die men kan uitdagen per moeilijkheidsgraad
     * 
     * @param gebruikersnaam
     * @param moeilijkheidsgraad
     * @return beschikbare spelers
     */
    public Speler[] geefBeschikbareSpelers(String gebruikersnaam, String moeilijkheidsgraad)
    {
        return spelerMapper.geefBeschikbareSpelers(gebruikersnaam, moeilijkheidsgraad);
    }
    
    /** Geeft de aangemelde speler weer 
     * 
     * @param gebruikersnaam
     * @return aangemelde speler
     */
    public Speler geefSpeler(String gebruikersnaam)
    {
       return spelerMapper.geefGebruikersnaamSpeler(gebruikersnaam);
    }  
    
    /** Geeft de gebruikersnaam van de speler weer
     * 
     * @param id
     * @return gebruikersnaam
     */
    public String geefGebruikersnaamSpeler(int id)
    {
        return spelerMapper.geefGebruikersnaam(id);
    }
}
