package domein;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author robbe
 */
public class Vertaler
{
    private static ResourceBundle taal = ResourceBundle.getBundle("taal.Taal",new Locale("nl","BE"));
    
    /** Constructor
     * 
     * @param taal 
     */
    public Vertaler(ResourceBundle taal){
        this.taal = taal;
    }
    
    /** Vertaalt de opgegeven string in de juiste taal
     * 
     * @param vertaling
     * @return vertaling
     */
    public static String vertaalString(String vertaling){
         return taal.getString(vertaling);
    }
}
