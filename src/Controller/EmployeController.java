package Controller;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Model.Employe;
import Model.EmployeModel;
import Model.Poste;
import Model.Rol;
import View.MainView;

public class EmployeController {
 
	private MainView view;
	private EmployeModel model;
	
	public EmployeController(MainView view, EmployeModel model) {
		this.view = view;
		this.model = model;
		
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
				model.add(nom, prenom, email, telephone, salaire, role, poste);
				Object[] row = {nom, prenom,email, telephone, salaire, role, poste};
                System.out.println(nom+prenom+email+telephone+salaire+role+poste);
                view.model.addRow(row);
			}
		});
		this.view.modifierE.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = view.table.getSelectedRow();
		if(selectedRow != -1) {
			int id = (int) view.table.getValueAt(selectedRow, 0);
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
            model.update(nom, prenom, email, telephone, salaire, role, poste);
            view.model.setValueAt(id,selectedRow,0);
            view.model.setValueAt(nom, selectedRow, 1);
            view.model.setValueAt(prenom, selectedRow, 2);
            view.model.setValueAt(email, selectedRow, 3);
            view.model.setValueAt(telephone, selectedRow, 4);
            view.model.setValueAt(salaire, selectedRow, 5);
            view.model.setValueAt(role, selectedRow, 6);
            view.model.setValueAt(poste, selectedRow, 7);
	}else {
        JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à modifier.");
	}
		}
				
	});

		this.view.supprimerE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.table.getSelectedRow();
				if (selectedRow != -1) {
                    int id = (int) view.model.getValueAt(selectedRow, 0);
                    model.delete(id);
                    view.model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à supprimer.");
                }
			}
		});
		
		this.view.afficherE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 ArrayList<Object[]> employes = model.display();
				view.model.setRowCount(0);
				for(Object[] emp :employes) {
					view.model.addRow(emp);
				}
				}
			});
}}
