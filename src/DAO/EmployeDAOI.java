package DAO;

import java.util.List;

import Model.Employe;

public interface EmployeDAOI {

	public void addEmploye(Employe employe);
	public void updateEmploye(Employe employe);
	public void deleteEmploye(int id);
	public List<Employe> displayEmployes();
}
