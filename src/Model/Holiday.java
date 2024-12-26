package Model;

import java.sql.Date;

public class Holiday {

	//Attributs
	
	private int holiday_id;
	private String nom;
	private String dateDebut;
	private String dateFin;
	private HolidayType type;
	
	//Methodes
	
	public Holiday(int holiday_id,String nom, String dateDebut, String dateFin, HolidayType type) {
		this.holiday_id = holiday_id;
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
	}
	
	//Getters & Setters
	
	public int getHolidayId() {return holiday_id;}
	public void setHolidayId(int holiday_id) {this.holiday_id = holiday_id;}
	
	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}
	
	public String getDateDebut() {return dateDebut;}
	public void setDateDebut(String dateDebut) {this.dateDebut = dateDebut;}
	
	public String getDateFin() {return dateFin;}
	public void setDateFin(String dateFin) {this.dateFin = dateFin;}
	
	public String getType() {return type.name();}
	public void setType(HolidayType type) {this.type = type;}

	
	
}
