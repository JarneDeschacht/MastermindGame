package persistentie;

import domein.Speler;
import domein.SpelerRepository;
import domein.Vertaler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpelerMapper
{

    private static final String INSERT_SPELER = "INSERT INTO ID222177_g37.Speler (gebruikersnaam,wachtwoord)"
            + "VALUES (?, ?)";

    public void voegToe(Speler speler)
    {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_SPELER))
        {
            query.setString(1, speler.getGebruikersnaam());
            query.setString(2, speler.getWachtwoord());
            query.executeUpdate();
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public Speler geefSpeler(String gebruikersnaam)
    {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.Speler WHERE gebruikersnaam = ?"))
        {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    String gebrnaam = rs.getString("gebruikersnaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    int[] wins = new int[3];
                    wins[0] = rs.getInt("aantalWinsGemakkelijk");
                    wins[1] = rs.getInt("aantalWinsNormaal");
                    wins[2] = rs.getInt("aantalWinsMoeilijk");
                    speler = new Speler(gebrnaam, wachtwoord);
                    speler.setAantalWinsPerMoeilijkheidsgraad(wins);
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return speler;
    }

    public int geefID(String gebruikersnaam)
    {
        int spelerID = 0;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT spelerID FROM ID222177_g37.Speler WHERE gebruikersnaam = ?"))
        {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    spelerID = rs.getInt("spelerID");
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return spelerID;
    }

    public void voegWinToe(String moeilijkheidsgraad, SpelerRepository spelerrepo, Speler speler)
    {
        String UPDATE_WINS_GEMAKKELIJK = "UPDATE ID222177_g37.Speler SET aantalWinsGemakkelijk = ? WHERE spelerID = ?";
        String UPDATE_WINS_NORMAAL = "UPDATE ID222177_g37.Speler SET aantalWinsNormaal = ? WHERE spelerID = ?";
        String UPDATE_WINS_MOEILIJK = "UPDATE ID222177_g37.Speler SET aantalWinsMoeilijk = ? WHERE spelerID = ?";
        String gekozenUpdate;
        int index;

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
        {
            gekozenUpdate = UPDATE_WINS_GEMAKKELIJK;
            index = 0;
        } else if (moeilijkheidsgraad.equals(Vertaler.vertaalString("normaal")))
        {
            gekozenUpdate = UPDATE_WINS_NORMAAL;
            index = 1;
        } else
        {
            gekozenUpdate = UPDATE_WINS_MOEILIJK;
            index = 2;
        }
        //updaten aantal wins
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(gekozenUpdate))
        {
            query.setInt(1, speler.getAantalWinsPerMoeilijkheidsgraad()[index]);
            query.setInt(2, spelerrepo.geefID(speler.getGebruikersnaam()));
            query.executeUpdate();
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public Speler[] geefBeschikbareSpelers(String gebruikersnaam, String moeilijkheidsgraad)
    {
        String GEEF_SPELERS;
        List<String> namen = new ArrayList<>();
        List<String> moeilijkheidsgraden = new ArrayList<>();

        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
        {
            GEEF_SPELERS = "SELECT gebruikersnaam, aantalWinsGemakkelijk from ID222177_g37.Speler where gebruikersnaam != ?";
        } else if (moeilijkheidsgraad.equals(Vertaler.vertaalString(("normaal"))))
        {
            GEEF_SPELERS = "SELECT gebruikersnaam, aantalWinsNormaal from ID222177_g37.Speler where aantalWinsGemakkelijk >= 20 and gebruikersnaam != ?";
        } else
        {
            GEEF_SPELERS = "SELECT gebruikersnaam, aantalWinsMoeilijk from ID222177_g37.Speler where aantalWinsNormaal >= 20 and aantalWinsGemakkelijk >= 20 and gebruikersnaam != ?";
        }

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(GEEF_SPELERS))
        {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery())
            {
                do
                {
                    if (rs.next())
                    {
                        namen.add(rs.getString("gebruikersnaam"));
                        if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
                        {
                            moeilijkheidsgraden.add(rs.getString("aantalWinsGemakkelijk"));
                        } else if (moeilijkheidsgraad.equals(Vertaler.vertaalString(("normaal"))))
                        {
                            moeilijkheidsgraden.add(rs.getString("aantalWinsNormaal"));
                        } else
                        {
                            moeilijkheidsgraden.add(rs.getString("aantalWinsMoeilijk"));
                        }
                    }
                    else
                    {
                        break;
                    }
                } while (!rs.isLast());
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        Speler[] spelers = new Speler[namen.size()];

        for (int i = 0; i < namen.size(); i++)
        {
            if (moeilijkheidsgraad.equals(Vertaler.vertaalString("gemakkelijk")))
            {
                spelers[i] = new Speler(namen.get(i), Integer.parseInt(moeilijkheidsgraden.get(i)), 0, 0);
            } else if (moeilijkheidsgraad.equals(Vertaler.vertaalString(("normaal"))))
            {
                spelers[i] = new Speler(namen.get(i), 0, Integer.parseInt(moeilijkheidsgraden.get(i)), 0);
            } else
            {
                spelers[i] = new Speler(namen.get(i), 0, 0, Integer.parseInt(moeilijkheidsgraden.get(i)));
            }
        }
        return spelers;
    }
    
    public Speler geefGebruikersnaamSpeler(String gebruikersnaam)
    {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT gebruikersnaam FROM ID222177_g37.Speler WHERE gebruikersnaam = ?"))
        {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    String gebrnaam = rs.getString("gebruikersnaam");
                    speler = new Speler(gebrnaam);
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return speler;
    }

    public String geefGebruikersnaam(int id)
    {
        String gebruikersnaam = "";
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT gebruikersnaam FROM ID222177_g37.Speler WHERE spelerID = ?"))
        {
            query.setInt(1, id);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    gebruikersnaam = rs.getString("gebruikersnaam");
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return gebruikersnaam;
    }
}
