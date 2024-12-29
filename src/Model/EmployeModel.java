package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import DAO.EmployeDAOImpl;

public class EmployeModel {

	private EmployeDAOImpl dao;
	
	public EmployeModel(EmployeDAOImpl dao) {
		this.dao = dao;
	}
	
	public List<Employe> importData(String filePath) {
	    List<Employe> employes = new ArrayList<>();
	    int failureCount = 0;

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line = reader.readLine(); // Lire la ligne d'en-tête

	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(",");
	            if (data.length != 7) { // Vérifie qu'il y a bien 7 colonnes
	                System.err.println("Erreur : Nombre de colonnes invalide dans la ligne : " + line);
	                failureCount++;
	                continue;
	            }

	            try {
	                // Lecture des données
	                String nom = data[0].trim();
	                String prenom = data[1].trim();
	                String email = data[2].trim();
	                String telephone = data[3].trim();
	                double salaire = Double.parseDouble(data[4].trim());
	                Rol role = Rol.valueOf(data[5].trim().toUpperCase());
	                Poste poste = Poste.valueOf(data[6].trim().toUpperCase());

	                // Validation des données
	                if (email == null || !(email.contains("@"))) {
	                    System.err.println("Erreur : email invalide dans la ligne : " + line);
	                    failureCount++;
	                    continue;
	                }
	                if (telephone.length() != 10) {
	                    System.err.println("Erreur : numéro de téléphone invalide dans la ligne : " + line);
	                    failureCount++;
	                    continue;
	                }
	                if (salaire < 0) {
	                    System.err.println("Erreur : salaire négatif dans la ligne : " + line);
	                    failureCount++;
	                    continue;
	                }

	                // Créer un objet Employe valide
	                Employe employe = new Employe(0, nom, prenom, email, telephone, salaire, role, poste);
	                employes.add(employe);

	            } catch (Exception e) {
	                System.err.println("Erreur lors du traitement de la ligne : " + line);
	                e.printStackTrace();
	                failureCount++;
	            }
	        }

	        System.out.println("Importation terminée !");
	        System.out.println("Employés valides : " + employes.size());
	        System.out.println("Employés échoués : " + failureCount);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return employes;
	}


	
	public boolean add(String nom, String prenom, String email, String telephone, double salaire, Rol role, Poste poste) {
		if(salaire <0){
			System.err.println("Erreur :Le salaire doit être positif.");
			return false;
		}
		if(email == null || !(email.contains("@"))) {
			System.err.println("Erreur : email doit contenir @");
			return false;
		}
		if (telephone.length() != 10) {
            System.err.println("Le numéro de telephone doit contenir exactement 10 chiffres.");
            return false;
        }
		
		Employe employe = new Employe(0,nom, prenom, email, telephone, salaire, role, poste);
		dao.add(employe);
		return true;
	}
	
	public boolean update(String nom, String prenom, String email, String telephone, double salaire, Rol role, Poste poste) {
		if(salaire <0){
			System.err.println("Erreur :Le salaire doit être positif.");
			return false;
		}
		if(email == null || !(email.contains("@"))) {
			System.err.println("Erreur : email doit contenir @");
			return false;
		}
		if (telephone.length() != 10) {
            System.err.println("Le numéro de telephone doit contenir exactement 10 chiffres.");
            return false;
        }
		
		Employe employe = new Employe(0,nom, prenom, email, telephone, salaire, role, poste);
			dao.update(employe);
			return true;
	}
	
	public boolean delete(int id) {
		dao.delete(id);
		return true;
	}
	
	public ArrayList<Object[]> display(){
		 ArrayList<Employe>emp=(ArrayList<Employe>) dao.display();
		  ArrayList<Object[]> tabEmp = new ArrayList<>();
		  for (Employe empl : emp) {
			  Object[] tab = new Object[8];
           tab[0] = empl.getId();
           tab[1] = empl.getNom();
           tab[2] = empl.getPrenom();
           tab[3] = empl.getEmail();
           tab[4] = empl.getTelephone();
           tab[5] = empl.getSalaire();
           tab[6] = empl.getRole();
           tab[7] = empl.getPoste();
           tabEmp.add(tab);
       }
		return tabEmp;
	}
}
