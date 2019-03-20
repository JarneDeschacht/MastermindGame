package domein;


public class Vak
{
    private String kleur;
    
    /** Maakt een nieuw vak aan
     * 
     */
    public Vak(){
        setKleur(Vertaler.vertaalString("leeg"));
    }

    /** Stelt het kleur van het vak in
     * 
     * @param kleur 
     */
    public final void setKleur(String kleur)
    {
        this.kleur = kleur;
    }
    
    /** Geeft het kleur van het vak weer
     * 
     * @return kleur
     */
    public String getKleur()
    {
        return kleur;
    }
}
