package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Employe;
import Model.Holiday;
import Model.HolidayType;

public class HolidayDAOImpl implements GenericDAOI<Holiday>{
	
private Connexion conn;

public HolidayDAOImpl() {
	conn = new Connexion();
}

@Override
public void add(Holiday holiday) {
	String sql = "INSERT INTO Holiday(nom, dateDebut, dateFin, type) VALUES(?,?,?,?)";
    
	try(PreparedStatement stmt = conn.getConnexion().prepareStatement(sql)){
		stmt.setString(1, holiday.getNom());
		stmt.setDate(2,Date.valueOf(holiday.getDateDebut()));
		stmt.setDate(3, Date.valueOf(holiday.getDateFin()));
		stmt.setString(4, holiday.getType());
		stmt.executeUpdate();
		
	}catch(SQLException e) {
		e.printStackTrace();	
	}
}

@Override
public void update(Holiday holiday) {
	String query = "UPDATE Holiday SET dateDebut=?, dateFin=?, type=?, nom=? WHERE holiday_id=?";
    try (PreparedStatement stmt = conn.getConnexion().prepareStatement(query)) {
        stmt.setDate(1, Date.valueOf(holiday.getDateDebut()));
        stmt.setDate(2, Date.valueOf(holiday.getDateFin()));
        stmt.setString(3, holiday.getType());
        stmt.setString(4, holiday.getNom());
        stmt.setInt(5,holiday.getHolidayId()); 
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
	
	
}

@Override
public void delete(int id) {
	 String query = "DELETE FROM Holiday WHERE holiday_id=?";
        try (PreparedStatement stmt = conn.getConnexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	
}

@Override
public ArrayList<Holiday> display(){
	ArrayList<Holiday> holiday = new ArrayList<>();
    String query = "SELECT* FROM Holiday";

    try (PreparedStatement stmt = conn.getConnexion().prepareStatement(query);
    		ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            holiday.add( new Holiday(
            rs.getInt("holiday_id"),
            rs.getString("nom"),
            rs.getString("dateDebut"),
            rs.getString("dateFin"),
            	HolidayType.valueOf(rs.getString("type"))
            	));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return holiday;
}


@Override
public Holiday findById(int id) {
	return null;
}
 public List<Employe> getEmployesName() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT nom FROM Employe";

        try (Statement stmt = conn.getConnexion().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employe employe = new Employe(0, query, query, query, query, 0, null, null);
                employe.setNom(rs.getString("nom"));
                employes.add(employe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }
 
 public int getSolde(String nom) {
     String query = "SELECT holidayBalance FROM Employe WHERE nom = ?";
     try (PreparedStatement stmt = conn.getConnexion().prepareStatement(query)) {
         stmt.setString(1, nom);
         try (ResultSet rs = stmt.executeQuery()) {
             if (rs.next()) {
                 return rs.getInt("holidayBalance");
             }
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return -1; // Retourne -1 si une erreur survient ou si l'employé n'existe pas
 }
 
 public void updateSolde(String nom, int nouveauSolde) {
     String query = "UPDATE Employe SET holidayBalance = ? WHERE nom = ?";
     try (PreparedStatement stmt = conn.getConnexion().prepareStatement(query)) {
         stmt.setInt(1, nouveauSolde);
         stmt.setString(2, nom);
         stmt.executeUpdate();
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
}
