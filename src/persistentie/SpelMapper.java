package persistentie;

import domein.EnumVertaler;
import domein.Spel;
import domein.Speler;
import domein.SpelerRepository;
import domein.Vertaler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robbe
 */
public class SpelMapper
{

    private static final String INSERT_SPEL = "INSERT INTO ID222177_g37.Spel (spelerID, naam, moeilijkheidsgraad, aantalPogingen, uitdagingID)"
            + "VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_VAKKEN = "INSERT INTO ID222177_g37.Vak (spelID, vakID, soort, kleur)"
            + "VALUES (?, ?, ?, ?)";
    private static final String VERWIJDER_SPEL = "DELETE ID222177_g37.Spel, ID222177_g37.Vak FROM ID222177_g37.Spel INNER JOIN ID222177_g37.Vak ON ID222177_g37.Spel.spelID = ID222177_g37.Vak.spelID WHERE ID222177_g37.Spel.spelID = ?";

    public void voegToe(Spel spel, Speler speler)
    {
        SpelerRepository spelerRepository = new SpelerRepository();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_SPEL))
        {
            query.setInt(1, spelerRepository.geefID(speler.getGebruikersnaam()));
            query.setString(2, spel.getNaam());
            //query.setString(3, spel.getMoeilijkheidsgraad());
            //Werken met indexen i.p.v. namen op te slaan in de databank
            //0 = gemakkelijk
            //1 = normaal
            //2 = moeilijk
            String[] moeilijkheidsgraden = spel.getMoeilijkheidsgraden();
            String gekozenGraad = spel.getMoeilijkheidsgraad();
            if(gekozenGraad.equals(moeilijkheidsgraden[0]) || gekozenGraad.equals("moeilijkheidsgraad_0"))
                query.setInt(3, 0);
            else if(gekozenGraad.equals(moeilijkheidsgraden[1])|| gekozenGraad.equals("moeilijkheidsgraad_1"))
                query.setInt(3, 1);
            else
                query.setInt(3, 2);
            query.setInt(4, spel.getAantalPogingen());
            int uitdagingID = spel.getUitdagingID();
            if(uitdagingID == 0)
                query.setNull(5, java.sql.Types.INTEGER);
            else
                query.setInt(5, uitdagingID);
            query.executeUpdate();
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_VAKKEN))
        {            
            for (int i = 0; i < spel.getAantalPogingen(); i++)
            {
                for (int j = 0; j < spel.getSpelbord().getSpeelpinnen()[i].length; j++)
                {
                    query.setInt(1, geefID(spel.getNaam()));
                    query.setInt(2, (i * spel.getSpelbord().getSpeelpinnen()[i].length) + j);
                    query.setString(3, "speelpin");
                    //query.setString(4, spel.getSpelbord().getSpeelpinnen()[i][j].getKleur());
                    String kleur = spel.getSpelbord().getSpeelpinnen()[i][j].getKleur();
                    String opslaan = "";
                    if(kleur.equals(Vertaler.vertaalString("kleur_0")))
                        opslaan = "kleur_0";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_1")))
                        opslaan = "kleur_1";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_2")))
                        opslaan = "kleur_2";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_3")))
                        opslaan = "kleur_3";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_4")))
                        opslaan = "kleur_4";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_5")))
                        opslaan = "kleur_5";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_6")))
                        opslaan = "kleur_6";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_7")))
                        opslaan = "kleur_7";
                    else if(kleur.equals(Vertaler.vertaalString("kleur_8")))
                        opslaan = "kleur_8";
                    else
                        opslaan = "kleur_onbekend";
                    query.setString(4, opslaan);
                        
                    query.executeUpdate();
                }
            }
            for (int i = 0; i < spel.getSpelbord().getOplossing().length; i++)
            {
                query.setInt(1, geefID(spel.getNaam()));
                query.setInt(2, i);
                query.setString(3, "oplossingpin");
                //query.setString(4, spel.getSpelbord().getOplossing()[i].getKleur());
                String kleur =  spel.getSpelbord().getOplossing()[i].getKleur();
                String opslaan = "";
                if(kleur.equals(Vertaler.vertaalString("kleur_0")))
                    opslaan = "kleur_0";
                else if(kleur.equals(Vertaler.vertaalString("kleur_1")))
                    opslaan = "kleur_1";
                else if(kleur.equals(Vertaler.vertaalString("kleur_2")))
                    opslaan = "kleur_2";
                else if(kleur.equals(Vertaler.vertaalString("kleur_3")))
                    opslaan = "kleur_3";
                else if(kleur.equals(Vertaler.vertaalString("kleur_4")))
                    opslaan = "kleur_4";
                else if(kleur.equals(Vertaler.vertaalString("kleur_5")))
                    opslaan = "kleur_5";
                else if(kleur.equals(Vertaler.vertaalString("kleur_6")))
                    opslaan = "kleur_6";
                else if(kleur.equals(Vertaler.vertaalString("kleur_7")))
                    opslaan = "kleur_7";
                else if(kleur.equals(Vertaler.vertaalString("kleur_8")))
                    opslaan = "kleur_8";
                else
                    opslaan = "kleur_onbekend";
                query.setString(4, opslaan);
                query.executeUpdate();

            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

//    public Spel geefSpel(String naam, EnumVertaler vertaler)
//    {
//        Spel spel = null;
//
//        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
//                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g37.Spel WHERE naam = ?"))
//        {
//            query.setString(1, naam);
//            try (ResultSet rs = query.executeQuery())
//            {
//                if (rs.next())
//                {
//                    String spelnaam = rs.getString("naam");
//                    String moeilijkheidsgraad = rs.getString("moeilijkheidsgraad");
//                    int aantalPogingen = rs.getInt("aantalPogingen");
//                    spel = new Spel(moeilijkheidsgraad, vertaler,);
//                }
//            }
//        } catch (SQLException ex)
//        {
//            throw new RuntimeException(ex);
//        }
//
//        return spel;
//    }
    public int geefID(String naam)
    {
        int spelID = 0;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT spelID FROM ID222177_g37.Spel WHERE naam = ?"))
        {
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    spelID = rs.getInt("spelID");
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return spelID;
    }

    public String[][] geefSpelletjes(String gebruikersnaam)
    {
        List<String> namen = new ArrayList<>();
        List<String> moeilijkheidsgraden = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT Spel.naam, Spel.moeilijkheidsgraad FROM ID222177_g37.Spel INNER JOIN ID222177_g37.Speler ON ID222177_g37.Spel.spelerID = ID222177_g37.Speler.spelerID WHERE Speler.gebruikersnaam = ?;"))
        {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery())
            {
                do
                {
                    if (rs.next())
                    {
                        namen.add(rs.getString("naam"));
                        String moeilijkheidsgraad = String.format("%s%s", "moeilijkheidsgraad_",rs.getString("moeilijkheidsgraad"));
                        moeilijkheidsgraden.add(moeilijkheidsgraad);
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

        String[][] output = new String[namen.size()][2];
        int i = 0;
        for (String naam : namen)
        {
            output[i][0] = naam;
            i++;
        }
        i = 0;
        for (String graad : moeilijkheidsgraden)
        {
            output[i][1] = graad;
            i++;
        }
        return output;
    }

    public Spel kiesSpel(String naam, Speler speler, EnumVertaler vertaler)
    {
        int aantalPogingen = 0;
        String moeilijkheidsgraad = "";
        int uitdagingID = 0;
        SpelerRepository spelerRepository = new SpelerRepository();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("select S.moeilijkheidsgraad, S.aantalpogingen, S.uitdagingID from ID222177_g37.Spel S where S.naam = ? and S.spelerID = ?"))
        {
            query.setString(1, naam);
            query.setInt(2, spelerRepository.geefID(speler.getGebruikersnaam()));
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    moeilijkheidsgraad = String.format("%s%s", "moeilijkheidsgraad_",rs.getString("moeilijkheidsgraad"));
                    aantalPogingen = rs.getInt("aantalpogingen");
                    uitdagingID = rs.getInt("uitdagingID");
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        String[] speelpinnen = new String[moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk"))|| moeilijkheidsgraad.equals("moeilijkheidsgraad_2") ? aantalPogingen * 5 : aantalPogingen * 4];
        String[] oplossing = new String[moeilijkheidsgraad.equals(Vertaler.vertaalString("moeilijk"))|| moeilijkheidsgraad.equals("moeilijkheidsgraad_2") ? 5 : 4];
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("select V.soort, V.kleur from ID222177_g37.Vak V join ID222177_g37.Spel S on V.spelID = S.spelID where vakID = ? and soort = ? and S.naam = ?"))
        {
            for (int i = 0; i < speelpinnen.length; i++)
            {
                query.setInt(1, i);
                query.setString(2, "speelpin");
                query.setString(3, naam);
                try (ResultSet rs = query.executeQuery())
                {
                    if (rs.next())
                    {
                        speelpinnen[i] = Vertaler.vertaalString(rs.getString("kleur"));
                    }
                }
            }
            for (int i = 0; i < oplossing.length; i++)
            {
                query.setInt(1, i);
                query.setString(2, "oplossingpin");
                query.setString(3, naam);
                try (ResultSet rs = query.executeQuery())
                {
                    if (rs.next())
                    {
                        oplossing[i] = Vertaler.vertaalString(rs.getString("kleur"));
                    }
                }
            }
            Spel spel = new Spel(moeilijkheidsgraad, vertaler, speler, aantalPogingen, speelpinnen, oplossing, uitdagingID);
            return spel;

        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }
    public void verwijderSpel(String naam)
    {
        int spelID = geefID(naam);
        
        try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(VERWIJDER_SPEL))
        {
            query.setInt(1, spelID);
            query.executeUpdate();
            
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        
        
    }
}
