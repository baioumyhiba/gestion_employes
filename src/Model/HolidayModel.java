package Model;

import java.sql.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.*;
import java.util.concurrent.TimeUnit;

import DAO.EmployeDAOImpl;
import DAO.HolidayDAOImpl;
public class HolidayModel {
	
private HolidayDAOImpl holidaydao;

public HolidayModel(HolidayDAOImpl holidaydao) {
	this.holidaydao = holidaydao;
}


public boolean add(String nom, String dateDebut, String dateFin, HolidayType type) {

	try {

		Date DateD=Date.valueOf(dateDebut);
		Date DateF=Date.valueOf(dateFin);

		 if (DateD.after(DateF)) {
	            System.out.println("La date de début doit être avant la date de fin.");
	            return false;
	        }
	}catch(IllegalArgumentException e) {
		System.out.println("Les dates doivent être au format 'yyyy-MM-dd'.");
        return false;
	}
	Holiday holiday = new Holiday(0,nom, dateDebut, dateFin, type);
	holidaydao.add(holiday);
	return true;
}


public boolean delete(int id) {
	if(id<0) {
		return false;
	}
	holidaydao.delete(id);
	return true;
}

public boolean update(int id,String nom,String dateDebut, String dateFin, HolidayType type) {
	
	try {
       Date DateD = Date.valueOf(dateDebut);
       Date DateF = Date.valueOf(dateFin);

        // Vérification si la date de début est avant la date de fin
        if (DateD.after(DateF)) {
            System.out.println("La date de début doit être avant la date de fin.");
            return false;
        }
    } catch (IllegalArgumentException e) {
        System.out.println("Les dates doivent être au format 'yyyy-MM-dd'.");
        return false;
    }
	Holiday nv= new Holiday(id,nom,dateDebut,dateFin,type);
	holidaydao.update(nv);
	return true;
}

public List<Holiday> display() {
	 return holidaydao.display();
}

public List<Employe> getEmployesName(){return holidaydao.getEmployesName();}

public int getSolde(String nom) {return holidaydao.getSolde(nom);}

public void updateSolde(String nom, int nouveauSolde) {	holidaydao.updateSolde(nom, nouveauSolde);}

}
