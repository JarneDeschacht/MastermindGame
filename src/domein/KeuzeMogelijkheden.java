package domein;

/**
 *
 * @author robbe
 */
public enum KeuzeMogelijkheden
{
    MOEILIJKHEIDSGRADEN(new String[]
    {
        "gemakkelijk", "normaal", "moeilijk"
    }),
    KLEURMOGELIJKHEDEN(new String[]
    {
        "rood", "oranje", "geel", "groen", "blauw", "bruin", "paars", "grijs"
    }),
    ANSI(new String[]
    {
        "\u001B[0m", "\u001B[31m", "\u001B[0m", "\u001B[33m", "\u001B[32m", "\u001B[34m", "\u001B[0m", "\u001B[35m", "\u001B[0m"
    });
    private final String[] mogelijkheden;

    private KeuzeMogelijkheden(String[] mogelijkheden)
    {
        this.mogelijkheden = mogelijkheden;
    }

    public String[] getMogelijkheden()
    {
        return mogelijkheden;
    }
}
