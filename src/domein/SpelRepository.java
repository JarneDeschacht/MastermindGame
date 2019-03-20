package domein;

import java.util.List;
import persistentie.SpelMapper;

/**
 *
 * @author robbe
 */
public class SpelRepository
{

    private final SpelMapper spelMapper;

    public SpelRepository()
    {
        spelMapper = new SpelMapper();
    }

//    public boolean bestaatSpel(String naam, EnumVertaler vertaler)
//    {
//        //return spelMapper.geefSpel(naam, vertaler)!=null;
//    }
    
    /** Voegt het spel toe aan de databank
     * 
     * @param spel
     * @param vertaler
     * @param speler 
     */
    public void voegToe(Spel spel, EnumVertaler vertaler, Speler speler)
    { 
        spelMapper.voegToe(spel, speler);
    }
    
    /** Geeft de opgeslagen spelletjes van de speler weer
     * 
     * @param gebruikersnaam
     * @return opgeslagen spelletjes
     */
    public String[][] geefSpelletjes(String gebruikersnaam)
    {
        return spelMapper.geefSpelletjes(gebruikersnaam);
    }
    
    /** Laat de speler een opgeslagen spel kiezen om te hervatten
     * 
     * @param naam
     * @param speler
     * @param vertaler
     * @return spel
     */
    public Spel kiesSpel(String naam, Speler speler, EnumVertaler vertaler){
        Spel spel = spelMapper.kiesSpel(naam, speler, vertaler);
        spelMapper.verwijderSpel(naam);
        return spel;
    }
}
