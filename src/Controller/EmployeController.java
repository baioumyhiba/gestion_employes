package Controller;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.Employe;
import Model.EmployeModel;
import Model.HolidayModel;
import Model.Poste;
import Model.Rol;
import View.MainView;

public class EmployeController {
 
	private MainView view;
	private EmployeModel employeModel;
	private HolidayModel holidayModel;
	
	public EmployeController(MainView view, EmployeModel employeModel) {
		this.view = view;
		this.employeModel =employeModel;
		
		this.view.ajouterE.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				String nom = view.saisie.getText();
				String prenom=view.saisie1.getText();
				String email=view.saisie2.getText();
				String telephone=view.saisie3.getText();
				double salaire=Double.parseDouble(view.saisie4.getText());
				Rol role=(Rol) view.choix.getSelectedItem();
				Poste poste=(Poste) view.choix2.getSelectedItem();
				employeModel.add(nom, prenom, email, telephone, salaire, role, poste);
				Object[] row = {nom, prenom,email, telephone, salaire, role, poste};
                System.out.println(nom+prenom+email+telephone+salaire+role+poste);
                view.model1.addRow(row);
			}
		});
		this.view.modifierE.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = view.table1.getSelectedRow();
		if(selectedRow != -1) {
			int id = (int) view.table1.getValueAt(selectedRow, 0);
            String nom = view.saisie.getText();
            String prenom = view.saisie1.getText();
            String email = view.saisie2.getText();
            String telephone = view.saisie3.getText();
            double salaire = 0;
            try {
            	salaire = Double.parseDouble(view.saisie4.getText());
            }catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Entrée de salaire invalide. Valeur par défaut définie.");
            }
            Rol role = (Rol) view.choix.getSelectedItem();
            Poste poste = (Poste) view.choix2.getSelectedItem();
            employeModel.update(nom, prenom, email, telephone, salaire, role, poste);
            view.model1.setValueAt(id,selectedRow,0);
            view.model1.setValueAt(nom, selectedRow, 1);
            view.model1.setValueAt(prenom, selectedRow, 2);
            view.model1.setValueAt(email, selectedRow, 3);
            view.model1.setValueAt(telephone, selectedRow, 4);
            view.model1.setValueAt(salaire, selectedRow, 5);
            view.model1.setValueAt(role, selectedRow, 6);
            view.model1.setValueAt(poste, selectedRow, 7);
	}else {
        JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à modifier.");
	}
		}
				
	});

		this.view.supprimerE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.table1.getSelectedRow();
				if (selectedRow != -1) {
                    int id = (int) view.model1.getValueAt(selectedRow, 0);
                    employeModel.delete(id);
                    view.model1.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à supprimer.");
                }
			}
		});
		
		this.view.afficherE.addActionListener(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent e) {
				 ArrayList<Object[]> employes = employeModel.display();
				view.model1.setRowCount(0);
				for(Object[] emp :employes) {
					view.model1.addRow(emp);
				}
				}
			});
		
	this.view.importer.addActionListener(new ActionListener() {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            handleImport(); 
        }});

    // ActionListener pour le bouton "Exporter"
    this.view.exporter.addActionListener(new ActionListener() {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            handleExport(); // Appelle la méthode handleExport pour gérer l'exportation
        }
    });
}
	
	private void handleImport () {
	JFileChooser fileChooser = new JFileChooser ();
	fileChooser. setFileFilter (new FileNameExtensionFilter("Fichiers CSV", "txt"));
	
	if(fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
	try {
	String filePath = fileChooser.getSelectedFile().getAbsolutePath();
	employeModel.importData(filePath);
	view.afficherMessageSuccess("Importation réussie");
	
	}catch (Exception ex) {
		view.afficherMessageError("Erreur lors de l'importation: " + ex.getMessage());			
	}
	}
}

private void handleExport() {
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers CSV","csv"));

	if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
	try {
	String filePath = fileChooser.getSelectedFile().getAbsolutePath();
	List<String> holidays = new ArrayList<>();
	if(!filePath.toLowerCase().endsWith(".txt")) {
	filePath += ".txt";
	}
	List<Object[]> employe = employeModel.display();
	holidayModel.exportData(filePath, holidays);
	view.afficherMessageSuccess("Exportation réussie");
	
	}catch (IOException ex) {
	view.afficherMessageError("Erreur lors de l'exportation : " +ex.getMessage());
}
	}
}

}
