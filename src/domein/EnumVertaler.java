package domein;

import java.util.ResourceBundle;

/**
 *
 * @author robbe
 */
public class EnumVertaler
{
    public EnumVertaler(){
    }
    
    /** Geeft de mogelijke moeilijkheidsgraden weer
     * @return moeilijkheidsgraden */
    public String[] geefMoeilijkheidsgraden(){
        String[] tempString = new String[3];
        for (int i = 0; i < tempString.length; i++)
        {
            tempString[i] = Vertaler.vertaalString(KeuzeMogelijkheden.MOEILIJKHEIDSGRADEN.getMogelijkheden()[i]);
        }
        return tempString;
    }
    
    /** Geeft de mogelijke kleurmogelijkheden weer
     * 
     * @return kleurmogelijkheden 
     */
    public String[] geefKleurmogelijkheden(){
        String[] tempString = new String[8];
        for (int i = 0; i < tempString.length; i++)
        {
            tempString[i] = Vertaler.vertaalString(KeuzeMogelijkheden.KLEURMOGELIJKHEDEN.getMogelijkheden()[i]);
        }
        return tempString;
    }
}
