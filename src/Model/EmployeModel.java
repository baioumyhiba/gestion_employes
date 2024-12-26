package Model;

import java.util.ArrayList;
import java.util.List;

import DAO.EmployeDAOImpl;

public class EmployeModel {

	private EmployeDAOImpl dao;
	
	public EmployeModel(EmployeDAOImpl dao) {
		this.dao = dao;
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
