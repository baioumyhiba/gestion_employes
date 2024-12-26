package DAO;

import java.sql.*;
import java.util.*;
import javax.swing.*;

import Model.Employe;
import Model.Poste;
import Model.Rol;


public class EmployeDAOImpl implements GenericDAOI<Employe>{

	private Connexion conn;
	
	public EmployeDAOImpl() {
		//initialiser la connexion
		
		conn = new Connexion();
	}
	
	//implementation des fonctions
	
	@Override
    public Employe findById(int id) {
        String sql = "SELECT * FROM Employe WHERE employe_id = ?";
        
        try (PreparedStatement stmt = conn.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employe(
                    rs.getInt("employe_id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getDouble("salaire"),
                    Rol.valueOf(rs.getString("rol")),
					Poste.valueOf(rs.getString("poste"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;// Retourne null si l'employé n'est pas trouvé
	}
	
	 public int findByName(String name) {
	        String sql = "SELECT employe_id FROM Employe WHERE nom = ?";
	        
	        try (PreparedStatement stmt = conn.getConnexion().prepareStatement(sql)) {
	            stmt.setString(1, name);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("employe_id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return -1;// Retourne null si l'employé n'est pas trouvé
		}
	 
	 
	@Override
	public void add(Employe employe) {
		String sql = "INSERT INTO Employe (nom, prenom, email, telephone, salaire, rol, poste, holidayBalance) VALUES (?,?,?,?,?,?,?,?)";
		try(PreparedStatement stmt = conn.getConnexion().prepareStatement(sql)){
			stmt.setString(1, employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setString(4, employe.getTelephone());
			stmt.setDouble(5, employe.getSalaire());
			stmt.setString(6, employe.getRole());
			stmt.setString(7, employe.getPoste());
			stmt.setInt(8, 25);
			stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Employe ajouté avec succées!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		}catch(SQLException e) {
			System.out.println(e.getMessage()); 
			JOptionPane.showMessageDialog(null, "Employe added successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Echec d'ajout!"+e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		}
	
	@Override
	public void update(Employe employe) {
		String sql = "UPDATE Employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, rol = ?, poste = ? WHERE employe_id = ?";
		 try (PreparedStatement stmt = conn.getConnexion().prepareStatement(sql)) {
	            stmt.setString(1, employe.getNom());
	            stmt.setString(2, employe.getPrenom());
	            stmt.setString(3, employe.getEmail());
	            stmt.setString(4, employe.getTelephone());
	            stmt.setDouble(5, employe.getSalaire());
	            stmt.setString(6, employe.getRole());
	            stmt.setString(7, employe.getPoste());
	            stmt.setInt(8, employe.getId());
	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0) {
	                JOptionPane.showMessageDialog(null, "Employe modifié avec succées!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(null, "Aucun employé trouvé à mise à jour.", "Attention", JOptionPane.WARNING_MESSAGE);
	            }
	}catch (SQLException e) {
        System.out.println(e.getMessage());
        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour de l'employe : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
	}
	
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM Employe WHERE employe_id = ?";
		try(PreparedStatement stmt = conn.getConnexion().prepareStatement(sql)){
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Employé supprimé avec succès!","Confirmation",  JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "Aucun employé trouvé avec le nom et prénom donnés.", "Information", JOptionPane.WARNING_MESSAGE);
			}
		}catch(SQLException e) {
			 System.out.println(e.getMessage());
	            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'employé : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<Employe>display(){
		List<Employe>employe = new ArrayList<>();
		String sql = "SELECT * FROM Employe";
		try(PreparedStatement stmt = conn.getConnexion().prepareStatement(sql);
				ResultSet rslt = stmt.executeQuery()){
					while(rslt.next()) {
						employe.add(new Employe(
								rslt.getInt("employe_id"),
								rslt.getString("nom"),
								rslt.getString("prenom"),
								rslt.getString("email"),
								rslt.getString("telephone"),
								rslt.getDouble("salaire"),
								
								Rol.valueOf(rslt.getString("rol")),
								Poste.valueOf(rslt.getString("poste"))
								));
					} 
				}catch(SQLException e) {
					System.out.println(e.getMessage());
		            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des employés : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				return employe;
	}
	}

