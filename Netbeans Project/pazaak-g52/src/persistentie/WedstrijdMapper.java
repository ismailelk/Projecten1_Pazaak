package persistentie;

import domein.Kaart;
import domein.Speler;
import domein.Wedstrijd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WedstrijdMapper {

    public void bewaarWedstrijd(Wedstrijd wedstrijd) {
        //Wedstrijd opslaan
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement(
                    "INSERT INTO Wedstrijd (naam, aantalGespeeldeSets) VALUES (?, ?)");
            query.setString(1, wedstrijd.getNaam());
            query.setInt(2, wedstrijd.getAantalGespeeldeSets());
            
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        //Spelers opslaan
        List<Speler> spelers = wedstrijd.getSpelersVoorWedstrijd();
        
        for(Speler s: spelers){
            try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
                PreparedStatement query = conn.prepareStatement(
                        "INSERT INTO WedstrijdSpeler (naamWedstrijd, naamSpeler, aantalGewonnenSets) VALUES (?, ?, ?)");
                query.setString(1, wedstrijd.getNaam());
                query.setString(2, s.getNaam());
                query.setInt(3, s.getAantalGewonnenSets());
            
                query.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        
            //Kaarten opslaan
            List<Kaart> kaarten = s.getWedstrijdStapel();
            try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
                for(Kaart k: kaarten){
                    PreparedStatement query = conn.prepareStatement(
                        "INSERT INTO WedstrijdKaartSpeler (naamWedstrijd, naamSpeler, omschrijving) VALUES (?, ?, ?)");
                    query.setString(1, wedstrijd.getNaam());
                    query.setString(2, s.getNaam());
                    query.setString(3, k.getOmschrijving());
            
                    query.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public List<String> geefAlleWedstrijdNamen() {
        List<String> namenWedstrijden = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query1 = conn.prepareStatement("select naam from Wedstrijd");
            try (ResultSet rs = query1.executeQuery()) {
                while (rs.next()) {
                    String naam = rs.getString("naam");

                    namenWedstrijden.add(naam);
                }
                
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return namenWedstrijden;
    }    

    public String[] geefNamenSpelersTeLadenWedstrijd(String naamGekozenWedstrijd)
    {
        List<String> namenSpelers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            
            PreparedStatement query = conn.prepareStatement("select naamSpeler from WedstrijdSpeler where naamWedstrijd = ?");
            query.setString(1, naamGekozenWedstrijd);
            
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    String naam = rs.getString("naamSpeler");

                    namenSpelers.add(naam);
                }
                
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return namenSpelers.toArray(new String[namenSpelers.size()]);
    }

    public String[] geefAantalGewonnenSetsEnWedstrijdStapelSpeler(String naamGekozenWedstrijd, String spelerNaam)
    {
        List<String> aantalGewonnenSetsEnWedstrijdStapelSpeler = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            
            PreparedStatement query1 = conn.prepareStatement("select aantalGewonnenSets from WedstrijdSpeler where (naamWedstrijd = ? and naamSpeler = ?)");
            query1.setString(1, naamGekozenWedstrijd);
            query1.setString(2, spelerNaam);
            
            try (ResultSet rs = query1.executeQuery()) {
                while (rs.next()) {
                    String aantalGewonnenSets = Integer.toString(rs.getInt("aantalGewonnenSets"));
                    aantalGewonnenSetsEnWedstrijdStapelSpeler.add(aantalGewonnenSets);
                } 
            }
            
            PreparedStatement query2 = conn.prepareStatement("select omschrijving from WedstrijdKaartSpeler where (naamWedstrijd = ? and naamSpeler = ?)");
            query2.setString(1, naamGekozenWedstrijd);
            query2.setString(2, spelerNaam);
            
            try (ResultSet rs = query2.executeQuery()) {
                while (rs.next()) {
                    String omschrijving = rs.getString("omschrijving");
                    aantalGewonnenSetsEnWedstrijdStapelSpeler.add(omschrijving);
                } 
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return aantalGewonnenSetsEnWedstrijdStapelSpeler.toArray(new String[aantalGewonnenSetsEnWedstrijdStapelSpeler.size()]);
    }

    public int geefAantalGespeeldeSets(String naamGekozenWedstrijd)
    {
        int aantalGespeeldeSets = 0;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            
            PreparedStatement query = conn.prepareStatement("select aantalGespeeldeSets from Wedstrijd where naam = ?");
            query.setString(1, naamGekozenWedstrijd);
            
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    aantalGespeeldeSets = rs.getInt("aantalGespeeldeSets");
                }           
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return aantalGespeeldeSets;
    }
}
