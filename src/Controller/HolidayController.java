package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DAO.EmployeDAOImpl;
import DAO.HolidayDAOImpl;
import Model.Employe;
import Model.Holiday;
import Model.HolidayModel;
import Model.HolidayType;
import View.MainView;

public class HolidayController {
	private MainView view;
	private HolidayModel model;
	
	public HolidayController(MainView view, HolidayModel model) {
		this.view = view;
		this.model = model;
		
		this.view=view;
		this.model=model;
		 initComboBoxData();
	        initDateComboBoxData(); 
	       display();
		this.view.ajouterH.addActionListener(e->add()); 
		this.view.supprimerH.addActionListener(e->delete()); 
		this.view.modifierH.addActionListener(e->update());
		}
	
	private void initDateComboBoxData() {
        LocalDate dateDebut = LocalDate.of(2024,12,23);
        LocalDate dateFin = LocalDate.of(2025,2,21); 
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        List<String> dates = new ArrayList<>();
        LocalDate currentDate = dateDebut;
        while (!currentDate.isAfter(dateFin)) {
            dates.add(currentDate.format(formatter)); 
            currentDate = currentDate.plusDays(1); 
        }
        
        MainView.remplirComboBox(view.dateD, dates);
       MainView.remplirComboBox(view.dateF, dates);
    }
	
	private void initComboBoxData() {
        MainView.remplirCombo(view.getNomComboBox(), model.getEmployesName());
    }
	
	public void add() {
		try {
		String nom= view.getNom();
		String dateD = view.getDateDebut();
		String dateF= view.getDateFin();
	//!!//
		HolidayType type =(HolidayType) view.getTypes().getSelectedItem();
        int solde = view.getSolde();
		if(nom.isEmpty() || dateD.isEmpty() || dateF.isEmpty() || type == null) {
			view.afficherMessageError("Tous les champs doivent être remplis.");
			return;
		}
		
		  if (solde > model.getSolde(nom)) {
              view.afficherMessageError("Solde insuffisant, votre solde est : " + model.getSolde(nom));
              return;
          }
		  boolean succes= model.add(nom,dateD,dateF,type);
			if(succes) {
				view.afficherMessageSuccess("Conge ajouter");
				view.clearFields();
				 List<Holiday> updatedHolidays = model.display();
				 model.updateSolde(nom,model.getSolde(nom)-solde);
		            Object[][] data = new Object[updatedHolidays.size()][5]; // 5 colonnes pour id, date_debut, date_fin, type, nom

		            for (int i = 0; i < updatedHolidays.size(); i++) {
		                Holiday holiday = updatedHolidays.get(i);
		                data[i][0] = holiday.getHolidayId();
		                data[i][1] = holiday.getNom();
		                data[i][2] = holiday.getDateDebut();
		                data[i][3] = holiday.getDateFin();
		                data[i][4] = holiday.getType();
		            }
		            view.updateTable(data);

			}else {
				view.afficherMessageError("Erreur lors de l'ajout");
			}
			}catch(Exception ex) {
	            view.afficherMessageError("Erreur : " + ex.getMessage());
			}
}
	
	
	public void display() {
		try {
			view.model.setRowCount(0);
			List<Holiday>holiday= model.display();
			for(Holiday h:holiday) {
				Object[]row={
					h.getHolidayId(),
					h.getNom(),
					h.getDateDebut(),
					h.getDateFin(),
					h.getType()
				};
				view.model.addRow(row);
				view.clearFields();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void delete() {
		 view.table.addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseClicked(MouseEvent e) {
	             int row = view.table.getSelectedRow();
	             if (row != -1) {
	                 int Id = (int) view.model.getValueAt(row, 0); 
	                 String nom = (String) view.model.getValueAt(row, 1);
	                 String dateDebut = (String) view.model.getValueAt(row, 2);
	                 String dateFin = (String) view.model.getValueAt(row, 3);
	                 LocalDate debut = LocalDate.parse(dateDebut);
	                 LocalDate fin = LocalDate.parse(dateFin);
	                 int solde= (int) ChronoUnit.DAYS.between(debut, fin);
	                 int reponse = JOptionPane.showConfirmDialog(null, 
	                         "Êtes-vous sûr de vouloir supprimer ce congé ?", 
	                         "Confirmation", 
	                         JOptionPane.YES_NO_OPTION);
	                 if(reponse  == JOptionPane.YES_OPTION) {
	                     boolean success = model.delete(Id);
	                     model.updateSolde(nom,model.getSolde(nom)+solde);
	                     if(success) {
	                    	    view.model.removeRow(row);
	                            view.afficherMessageSuccess("congé supprimer avec succès.");
	                     }else {
	                    	 view.afficherMessageError("Erreur lors de la suppression");
	                     }   
	                 }}
	 }
	 });
	 }
	
	private void update() {
		 view.table.addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseClicked(MouseEvent e) {
	        	 String nom= view.getNom();
	     		String dateDebut = view.getDateDebut();
	     		String dateFin= view.getDateFin();
	     		HolidayType type = (HolidayType) view.getTypes().getSelectedItem();
	     		int solde = view.getSolde();
	     		if(nom.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty() || type == null) {
	     			view.afficherMessageError("Tous les champs doivent être remplis.");
	     			return;
	     		}
	        	   int row = view.table.getSelectedRow(); 
	               if (row != -1) { 
	                   int id = (int) view.model.getValueAt(row, 0); 
	                   String nomT = (String) view.model.getValueAt(row, 1);
		                 String dateDebutT = (String) view.model.getValueAt(row, 2);
		                 String dateFinT = (String) view.model.getValueAt(row, 3);
		                 LocalDate debut = LocalDate.parse(dateDebutT);
		                 LocalDate fin = LocalDate.parse(dateFinT);
		                 int soldeT= (int) ChronoUnit.DAYS.between(debut, fin);
	                   int reponse = JOptionPane.showConfirmDialog(null, 
	                           "Êtes-vous sûr de vouloir modifier ce congé ?", 
	                           "Confirmation", 
	                           JOptionPane.YES_NO_OPTION);
	                   if(reponse  == JOptionPane.YES_OPTION) {
	               		boolean succes= model.update(id,nom,dateDebut,dateFin,type);
	               		model.updateSolde(nomT,model.getSolde(nomT)+soldeT);
	               		model.updateSolde(nomT,model.getSolde(nomT)-solde);
	   	        		view.clearFields();
	   	        	 List<Holiday> updatedHolidays = model.display(); // Récupérer les congés mis à jour
                    
                    // Convertir la liste en tableau d'objets pour le tableau
                    Object[][] data = new Object[updatedHolidays.size()][5]; // 5 colonnes pour id, date_debut, date_fin, type, nom

                    for (int i = 0; i < updatedHolidays.size(); i++) {
                        Holiday holiday = updatedHolidays.get(i);
                        data[i][0] = holiday.getHolidayId();
                        data[i][1] = holiday.getNom();
                        data[i][2] = holiday.getDateDebut();
                        data[i][3] = holiday.getDateFin();
                        data[i][4] = holiday.getType();
                    }
                    // Mettre à jour le tableau avec les données actualisées
                    view.updateTable(data);

	                	  if(succes) {
	                          view.afficherMessageSuccess("congé modifier avec succès.");
	                	  }
	                   }
	               } }
		 });
	}
}


