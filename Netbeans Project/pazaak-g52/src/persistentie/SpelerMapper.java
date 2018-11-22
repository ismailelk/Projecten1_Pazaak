package persistentie;

import domein.Kaart;
import domein.Speler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpelerMapper {
    private List<Kaart> startKaarten;
    private KaartMapper kaartMapper;

    public SpelerMapper(List<Kaart> startKaarten) {
        this.startKaarten = startKaarten;
        kaartMapper = new KaartMapper();
    }
    public void voegToe(Speler speler) {
        
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement(
                    "INSERT INTO Speler (naam, geboortejaar, krediet) VALUES (?, ?, ?)");
            query.setString(1, speler.getNaam());
            query.setInt(2, speler.getGeboortejaar());
            query.setInt(3, speler.getKrediet());
            
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    public Speler geefSpeler(String spelerNaam) {
        Speler speler = null;
        List<Kaart> stapel = kaartMapper.geefStapel(spelerNaam);
        
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("SELECT * from Speler WHERE naam = ?");
            query.setString(1, spelerNaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String naam = rs.getString("naam");
                    int geboortejaar = rs.getInt("geboortejaar");
                    int krediet = rs.getInt("krediet");
                    
                    speler = new Speler(naam, geboortejaar, krediet, stapel);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return speler;
    }
 
    public List<Speler> geefLijstSpelers() {
        List<Speler> spelerLijst = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query1 = conn.prepareStatement("select * from Speler");
            try (ResultSet rs = query1.executeQuery()) {
                while (rs.next()) {
                    String naam = rs.getString("naam");
                    int geboortejaar = rs.getInt("geboortejaar");
                    int krediet = rs.getInt("krediet");

                    spelerLijst.add(new Speler(naam, geboortejaar, krediet, startKaarten));
                }
                
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return spelerLijst;
    }

    public void wijzigKredietSpeler(Speler speler, int krediet) {
        String naam = speler.getNaam();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement(
                    "UPDATE Speler SET krediet = ? WHERE naam = ?");
            query.setInt(1, krediet);
            query.setString(2, naam);
            
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
