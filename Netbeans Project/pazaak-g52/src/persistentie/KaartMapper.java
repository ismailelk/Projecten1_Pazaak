package persistentie;

import domein.Kaart;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KaartMapper {
    
    public List<Kaart> geefStartstapel() {
        List<Kaart> kaarten = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query1 = conn.prepareStatement("select * from Kaarttype "
                    + "where startstapel = true\n" +
                    "order by prijs, kaarttype, waarde");
            try (ResultSet rs = query1.executeQuery()) {
                while (rs.next()) {
                    String kaarttype = rs.getString("kaarttype");
                    int waarde = rs.getInt("waarde");
                    String omschrijving = rs.getString("omschrijving");
                    int prijs = rs.getInt("prijs");

                    kaarten.add(new Kaart(kaarttype, waarde, omschrijving, prijs));
                }
                
            }            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaarten;
    }
    
    public List<Kaart> geefStapel(String naam) {
        List<Kaart> stapel = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query1 = conn.prepareStatement(
                    "select kaarttype, waarde, Kaart.omschrijving, prijs from Kaart " +
                    "join Kaarttype on Kaarttype.omschrijving = Kaart.omschrijving\n" +
                    "where spelerNaam = ?\n" +
                    "order by prijs, kaarttype, waarde");
            query1.setString(1, naam);
            try (ResultSet rs = query1.executeQuery()) {
                while (rs.next()) {
                    String kaarttype = rs.getString("kaarttype");
                    int waarde = rs.getInt("waarde");
                    String omschrijving = rs.getString("omschrijving");
                    int prijs = rs.getInt("prijs");

                    stapel.add(new Kaart(kaarttype, waarde, omschrijving, prijs));
                }
                
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return stapel;
    }

    public void voegKaartenToe(String naam, List<Kaart> stapel) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            for(Kaart kaart: stapel){
                PreparedStatement query = conn.prepareStatement(
                    "INSERT INTO Kaart (spelerNaam, omschrijving) VALUES (?, ?)");
                query.setString(1, naam);
                query.setString(2, kaart.getOmschrijving());
            
                query.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Kaart geefKaartAdhvOmschrijving(String omschrijving) {
        Kaart kaart = null;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement(
                "SELECT kaarttype, waarde, prijs FROM Kaarttype WHERE omschrijving = '"+ omschrijving+"'");
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    String type = rs.getString("kaarttype");
                    int waarde = rs.getInt("waarde");
                    int prijs = rs.getInt("prijs");
                    kaart = new Kaart(type, waarde, omschrijving, prijs);
                }
            }
 
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return kaart;
        
    }
    
    public List<Kaart> geefAlleKaartenVoorSetStapel(){
        List<Kaart> kaarten = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query1 = conn.prepareStatement("select * from Kaarttype "
                    + "where prijs = 0");
            try (ResultSet rs = query1.executeQuery()) {
                while (rs.next()) {
                    String kaarttype = rs.getString("kaarttype");
                    int waarde = rs.getInt("waarde");
                    String omschrijving = rs.getString("omschrijving");
                    int prijs = rs.getInt("prijs");

                    kaarten.add(new Kaart(kaarttype, waarde, omschrijving, prijs));
                }
                
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return kaarten;
    }

    public List<Kaart> geefAlleKaarten() {
        List<Kaart> kaarten = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query1 = conn.prepareStatement("select * from Kaarttype\n"
                    + "order by prijs, kaarttype, waarde");
            try (ResultSet rs = query1.executeQuery()) {
                while (rs.next()) {
                    String kaarttype = rs.getString("kaarttype");
                    int waarde = rs.getInt("waarde");
                    String omschrijving = rs.getString("omschrijving");
                    int prijs = rs.getInt("prijs");

                    kaarten.add(new Kaart(kaarttype, waarde, omschrijving, prijs));
                }
                
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return kaarten;
    }
    
    public void voegKaartToe(String naam, Kaart kaart) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            
                PreparedStatement query = conn.prepareStatement(
                    "INSERT INTO Kaart (spelerNaam, omschrijving) VALUES (?, ?)");
                query.setString(1, naam);
                query.setString(2, kaart.getOmschrijving());
            
                query.executeUpdate();
            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // ------------------  Use Case 10: Speel tegen een AI -----------------------
    public List<Kaart> geefWedstrijdStapelAI1()
    {
        List<Kaart> kaarten = new ArrayList<>();
        kaarten.add(new Kaart("+", 1, "+1", 5));
        kaarten.add(new Kaart("+", 2, "+2", 5));
        kaarten.add(new Kaart("-", 1, "-1", 5));
        kaarten.add(new Kaart("-", 2, "-2", 5));
        return kaarten;
    }

}
