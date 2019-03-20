package persistentie;

import domein.Spel;
import domein.Speler;
import domein.SpelerRepository;
import domein.Uitdaging;
import domein.Vertaler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robbe
 */
public class UitdagingMapper
{

    private static final String INSERT_UITDAGING = "INSERT INTO ID222177_g37.Uitdaging (uitdagerID, uitgedaagdeID, moeilijkheidsgraad)"
            + "VALUES (?, ?, ?)";
    private static final String INSERT_OPLOSSING = "INSERT INTO ID222177_g37.Oplossingspin (pinID, uitdagingID, KLEUR)"
            + "VALUES (?, ?, ?)";

    public void voegToe(Speler uitdager, String uitgedaagde, Spel spel)
    {
        SpelerRepository spelerRepository = new SpelerRepository();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_UITDAGING))
        {
            query.setInt(1, spelerRepository.geefID(uitdager.getGebruikersnaam()));
            query.setInt(2, spelerRepository.geefID(uitgedaagde));
            //Werken met indexen i.p.v. namen op te slaan in de databank
            //0 = gemakkelijk
            //1 = normaal
            //2 = moeilijk
            String[] moeilijkheidsgraden = spel.getMoeilijkheidsgraden();
            String gekozenGraad = spel.getMoeilijkheidsgraad();
            if (gekozenGraad.equals(moeilijkheidsgraden[0]) || gekozenGraad.equals("moeilijkheidsgraad_0"))
            {
                query.setInt(3, 0);
            } else if (gekozenGraad.equals(moeilijkheidsgraden[1]) || gekozenGraad.equals("moeilijkheidsgraad_1"))
            {
                query.setInt(3, 1);
            } else
            {
                query.setInt(3, 2);
            }
            query.executeUpdate();
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        int uitdagingID = geefID(uitdager.getGebruikersnaam(), uitgedaagde);
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_OPLOSSING))
        {
            for (int i = 0; i < spel.getSpelbord().getOplossing().length; i++)
            {
                query.setInt(1, i);
                query.setInt(2, uitdagingID);
                //query.setString(4, spel.getSpelbord().getOplossing()[i].getKleur());
                String kleur = spel.getSpelbord().getOplossing()[i].getKleur();
                String opslaan = "";
                if (kleur.equals(Vertaler.vertaalString("kleur_0")))
                {
                    opslaan = "kleur_0";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_1")))
                {
                    opslaan = "kleur_1";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_2")))
                {
                    opslaan = "kleur_2";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_3")))
                {
                    opslaan = "kleur_3";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_4")))
                {
                    opslaan = "kleur_4";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_5")))
                {
                    opslaan = "kleur_5";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_6")))
                {
                    opslaan = "kleur_6";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_7")))
                {
                    opslaan = "kleur_7";
                } else if (kleur.equals(Vertaler.vertaalString("kleur_8")))
                {
                    opslaan = "kleur_8";
                } else
                {
                    opslaan = "kleur_onbekend";
                }
                query.setString(3, opslaan);
                query.executeUpdate();

            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public int geefID(String uitdager, String uitgedaagde)
    {
        SpelerRepository spelerRepository = new SpelerRepository();
        int uitdagingID = 0;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT uitdagingID FROM ID222177_g37.Uitdaging WHERE uitdagerID = ? AND uitgedaagdeID = ? order by uitdagingID desc limit 1"))
        {
            query.setInt(1, spelerRepository.geefID(uitdager));
            query.setInt(2, spelerRepository.geefID(uitgedaagde));
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    uitdagingID = rs.getInt("uitdagingID");
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return uitdagingID;
    }

    public int[] geefSpelerIDs(int uitdagingID)
    {
        int[] spelerids = new int[2];
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT uitdagerID, uitgedaagdeID FROM ID222177_g37.Uitdaging WHERE uitdagingID = ?"))
        {
            query.setInt(1, uitdagingID);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    spelerids[0] = rs.getInt("uitdagerID");
                    spelerids[1] = rs.getInt("uitgedaagdeID");
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return spelerids;
    }

//    private static final String UPDATE_UITDAGER = "UPDATE ID222177_g37.Uitdaging SET uitdagerPogingen = ?, uitdagerCodeGekraakt = ? where uitdagingID = ?";
    //private static final String UPDATE_UITGEDAAGDE = "UPDATE ID222177_g37.Uitdaging SET uitgedaagdePogingen = ?, uitgedaagdeCodeGekraakt = ? where uitdagingID = ?";
//    public void updateUitdaging(String uitdager, String uitgedaagde, int aantalPogingen, boolean gewonnen)
//    {
//        int[] spelerids = geefSpelerIDs(geefID(uitdager, uitgedaagde));
//        SpelerRepository spelerRepository = new SpelerRepository();
//        if (spelerRepository.geefID(uitdager) == spelerids[0])
//        {
//            try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
//                    PreparedStatement query = conn.prepareStatement(UPDATE_UITDAGER))
//            {
//                query.setInt(1, aantalPogingen);
//                query.setBoolean(2, gewonnen);
//                query.setInt(3, geefID(uitdager, uitgedaagde));
//                query.executeUpdate();
//            } catch (SQLException ex)
//            {
//                throw new RuntimeException(ex);
//            }
//        } else {
//            try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
//                    PreparedStatement query = conn.prepareStatement(UPDATE_UITGEDAAGDE))
//            {
//                query.setInt(1, aantalPogingen);
//                query.setBoolean(2, gewonnen);
//                query.setInt(3, geefID(uitdager, uitgedaagde));
//                query.executeUpdate();
//            } catch (SQLException ex)
//            {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
    private static final String UPDATE_UITGEDAAGDE = "UPDATE ID222177_g37.Uitdaging SET uitgedaagdePogingen = ?,  uitgedaagdeCodeGekraakt = ? WHERE uitdagingID = ?";
    private static final String UPDATE_UITDAGER = "UPDATE ID222177_g37.Uitdaging SET uitdagerPogingen = ?,  uitdagerCodeGekraakt = ? WHERE uitdagingID = ?";

    public void updateUitdaging(int uitdagingID, int aantalPogingen, String gebruikersnaam, boolean codeGekraakt)
    {
        SpelerMapper spelerMapper = new SpelerMapper();
        int spelerID = spelerMapper.geefID(gebruikersnaam);

        int[] spelerIDs = geefSpelerIDs(uitdagingID);

        if (spelerID == spelerIDs[0])
        {
            try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                    PreparedStatement query = conn.prepareStatement(UPDATE_UITDAGER))
            {
                query.setInt(1, aantalPogingen);
                query.setBoolean(2, codeGekraakt);
                query.setInt(3, uitdagingID);
                query.executeUpdate();
            } catch (SQLException ex)
            {
                throw new RuntimeException(ex);
            }
        } else
        {
            try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                    PreparedStatement query = conn.prepareStatement(UPDATE_UITGEDAAGDE))
            {
                query.setInt(1, aantalPogingen);
                query.setBoolean(2, codeGekraakt);
                query.setInt(3, uitdagingID);
                query.executeUpdate();
            } catch (SQLException ex)
            {
                throw new RuntimeException(ex);
            }
        }

    }

    public String[][] geefUitdagingen(int aangemeldeSpelerID)
    {
        String[][] uitdagingen;
        List<String> namenUitdagers = new ArrayList<>();
        List<String> moeilijkheidsgraden = new ArrayList<>();
        String SQL = "SELECT uitdagerID,moeilijkheidsgraad FROM ID222177_g37.Uitdaging WHERE uitgedaagdeID = ? AND uitgedaagdePogingen IS NULL";

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SQL))
        {
            query.setInt(1, aangemeldeSpelerID);
            try (ResultSet rs = query.executeQuery())
            {
                do
                {
                    if (rs.next())
                    {
                        namenUitdagers.add(rs.getString("uitdagerID"));
                        moeilijkheidsgraden.add(rs.getString("moeilijkheidsgraad"));
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

        uitdagingen = new String[namenUitdagers.size()][2];

        for (int i = 0; i < namenUitdagers.size(); i++)
        {
            uitdagingen[i][0] = namenUitdagers.get(i);
            uitdagingen[i][1] = moeilijkheidsgraden.get(i);
        }

        return uitdagingen;
    }

    public Uitdaging geefUitdaging(String naamUitdager, String naamUitgedaagde)
    {
        int uitdagingID = geefID(naamUitdager, naamUitgedaagde);
        String SQL = "SELECT kleur FROM ID222177_g37.Oplossingspin WHERE uitdagingID = ?;";
        List<String> kleuren = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SQL))
        {
            query.setInt(1, uitdagingID);
            ResultSet rs = query.executeQuery();
            do
            {
                if (rs.next())
                {
                    kleuren.add(Vertaler.vertaalString(rs.getString(1)));
                }
            } while (!rs.isLast());

        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        String[] kleurenArray = new String[kleuren.size()];
        int i = 0;
        for (String kleur : kleuren)
        {
            kleurenArray[i] = kleur;
            i++;
        }

        Uitdaging uitdaging = new Uitdaging(kleurenArray);

        return uitdaging;
    }

    public String geefMoeilijkheidsgraadUitdaging(String naamUitdager, String naamUitgedaagde)
    {
        int uitdagingID = geefID(naamUitdager, naamUitgedaagde);
        String moeilijkheidsgraad = "";
        String SQL = "SELECT moeilijkheidsgraad FROM ID222177_g37.Uitdaging WHERE uitdagingID = ?;";
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SQL))
        {
            query.setInt(1, uitdagingID);
            ResultSet rs = query.executeQuery();
            if (rs.next())
            {
                moeilijkheidsgraad = Vertaler.vertaalString(String.format("moeilijkheidsgraad_%s", rs.getString(1)));
            }

        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return moeilijkheidsgraad;
    }
    
    public String onafgewerkteOpgeslagenUitdaging(String uitgedaagdeNaam)
    {
        String naamSpel = "";
        SpelerMapper spelerMapper = new SpelerMapper();
        int uitgedaagdeId = spelerMapper.geefID(uitgedaagdeNaam);
        
        String SQL = "SELECT Spel.naam FROM ID222177_g37.Spel INNER JOIN ID222177_g37.Uitdaging ON ID222177_g37.Spel.uitdagingID = ID222177_g37.Uitdaging.uitdagingID WHERE ID222177_g37.Uitdaging.uitgedaagdeID = ? AND ID222177_g37.Spel.spelerID = ID222177_g37.Uitdaging.uitgedaagdeID;";
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SQL))
        {
            query.setInt(1, uitgedaagdeId);
            ResultSet rs = query.executeQuery();
            if (rs.next())
            {
                naamSpel = rs.getString(1);
            }

        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        
        
        return naamSpel;
    }
    
    public String[][] toonKlassement()
    {
        //String SQL = "SELECT moeilijkheidsgraad,uitdagers.gebruikersnaam as 'uitdager',uitgedaagden.gebruikersnaam as 'uitgedaagde',uitdagerPogingen,uitgedaagdePogingen FROM ID222177_g37.Uitdaging \n" +
        //             "inner join ID222177_g37.Speler as uitdagers on uitdagerID = uitdagers.spelerID \n" +
        //             "inner join ID222177_g37.Speler as uitgedaagden on uitgedaagdeID = uitgedaagden.spelerID\n" +
        //             "where uitdagerCodeGekraakt = 1 and uitgedaagdeCodeGekraakt = 1";
        String SQL = "SELECT uitdager.gebruikersnaam AS 'uitdager', Uitdaging.uitdagerPogingen, Uitdaging.uitdagerCodeGekraakt, uitgedaagde.gebruikersnaam AS 'uitgedaagde', Uitdaging.uitgedaagdePogingen, Uitdaging.uitgedaagdeCodeGekraakt, Uitdaging.moeilijkheidsgraad  FROM ID222177_g37.Uitdaging \n" +
                    "INNER JOIN ID222177_g37.Speler AS uitdager ON uitdager.spelerID = Uitdaging.uitdagerID\n" +
                    "INNER JOIN ID222177_g37.Speler AS uitgedaagde ON uitgedaagde.spelerID = Uitdaging.uitgedaagdeID\n" +
                    "WHERE (Uitdaging.uitdagerPogingen IS NOT NULL AND Uitdaging.uitgedaagdePogingen IS NOT NULL);";

        String[][] klassement;
        List<String> namenUitdagers = new ArrayList<>();
        List<String> namenUitgedaagden = new ArrayList<>();
        List<String> pogingenUitdagers = new ArrayList<>();
        List<String> pogingenUitgedaagden = new ArrayList<>();
        List<String> moeilijkheidsgraden = new ArrayList<>();
        List<String> uitdagersCodeGekraakt = new ArrayList<>();
        List<String> uitgedaagdenCodeGekraakt = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SQL))
        {
            try (ResultSet rs = query.executeQuery())
            {
                do
                {
                    if (rs.next())
                    {
                        namenUitdagers.add(rs.getString("uitdager"));
                        namenUitgedaagden.add(rs.getString("uitgedaagde"));
                        moeilijkheidsgraden.add(rs.getString("moeilijkheidsgraad"));
                        pogingenUitdagers.add(rs.getString("uitdagerPogingen"));
                        pogingenUitgedaagden.add(rs.getString("uitgedaagdePogingen"));
                        uitdagersCodeGekraakt.add(rs.getString("uitdagerCodeGekraakt"));
                        uitgedaagdenCodeGekraakt.add(rs.getString("uitgedaagdeCodeGekraakt"));
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
        
        klassement = new String[namenUitdagers.size()][7];

        for (int i = 0; i < namenUitdagers.size(); i++)
        {
           klassement[i][0] = moeilijkheidsgraden.get(i);
           klassement[i][1] = namenUitdagers.get(i);
           klassement[i][2] = namenUitgedaagden.get(i);
           klassement[i][3] = pogingenUitdagers.get(i);
           klassement[i][4] = pogingenUitgedaagden.get(i);
           klassement[i][5] = uitdagersCodeGekraakt.get(i);
           klassement[i][6] = uitgedaagdenCodeGekraakt.get(i);
        }
        
        return klassement;
    }
    
}
