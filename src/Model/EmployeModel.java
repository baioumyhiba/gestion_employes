package Model;

import java.util.List;

import DAO.EmployeDAOImpl;

public class EmployeModel {

	private EmployeDAOImpl dao;
	
	public EmployeModel(EmployeDAOImpl dao) {
		this.dao = dao;
	}
	
	public boolean addEmploye(int id, String nom, String prenom, String email, String telephone, double salaire, Rol role, Poste poste) {
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
		
		Employe employe = new Employe(id, nom, prenom, email, telephone, salaire, role, poste);
		dao.addEmploye(employe);
		return true;
	}
	
	public boolean updateEmploye(int id, String nom, String prenom, String email, String telephone, double salaire, Rol role, Poste poste) {
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
		
		Employe employe = new Employe(id, nom, prenom, email, telephone, salaire, role, poste);
			dao.updateEmploye(employe);
			return true;
	}
	
	public boolean deleteEmploye(int id) {
		dao.deleteEmploye(id);
		return true;
	}
	
	public Object[][] displayEmployes(){
		List<Employe> employe = dao.displayEmployes();
		Object[][] tab =new Object[employe.size()][8];
		for (int i = 0; i < employe.size(); i++) {
            Employe empl = employe.get(i);
            tab[i][0] = empl.getId();
            tab[i][1] = empl.getNom();
            tab[i][2] = empl.getPrenom();
            tab[i][3] = empl.getEmail();
            tab[i][4] = empl.getTelephone();
            tab[i][5] = empl.getSalaire();
            tab[i][6] = empl.getRole();
            tab[i][7] = empl.getPoste();
        }
		return tab;
	}
}
