package View;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.EmployeDAOImpl;
import Model.Poste;
import Model.Rol;
import Model.Employe;
import Model.EmployeModel;
import Model.HolidayType;

public class MainView extends JFrame {
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;

    // Employee form components
    public JTextField saisie = new JTextField(20);
    public JTextField saisie1 = new JTextField(20);
    public JTextField saisie2 = new JTextField(20);
    public JTextField saisie3 = new JTextField(20);
    public JTextField saisie4 = new JTextField(20);
    public JComboBox<Rol> choix = new JComboBox<>(Rol.values());
    public JComboBox<Poste> choix2 = new JComboBox<>(Poste.values());
    public JSpinner idSpinner;

    // Holiday form components
    public JComboBox<String> dateD = new JComboBox<>();
    public JComboBox<String> dateF = new JComboBox<>();
    public JComboBox<String> choixEmploye = new JComboBox<>(); 
    
    public JTextField saisie1Holiday = new JTextField(20);
    public JComboBox<HolidayType> choixHoliday = new JComboBox<>(HolidayType.values());

    // Buttons for both Employee and Holiday
    public JButton ajouterE = new JButton("Ajouter");
    public JButton supprimerE = new JButton("Supprimer");
    public JButton afficherE = new JButton("Afficher");
    public JButton modifierE = new JButton("Modifier");

    public JButton ajouterH = new JButton("Ajouter");
    public JButton supprimerH = new JButton("Supprimer");
    public JButton afficherH = new JButton("Afficher");
    public JButton modifierH = new JButton("Modifier");
    
    public JButton importer = new JButton("Importer");
    public JButton exporter = new JButton("Exporter");
    
    // Table components
    public JTable table1;
    public DefaultTableModel model1;
    public JScrollPane scrollPane1;
    
    public JTable table2;
    public DefaultTableModel model2;
    public JScrollPane scrollPane2;

    public MainView() {
        // Configuration of the main window
        setTitle("Gestion des Employés et Congés");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create JTabbedPane for switching between sections
        tabbedPane1 = new JTabbedPane();

        // Create panels for Employee and Holiday management
        JPanel employePanel = createEmployeePanel();
        JPanel holidayPanel = createHolidayPanel();
        // Add panels to JTabbedPane
        tabbedPane1.addTab("Employés", employePanel);
        tabbedPane1.addTab("Congés", holidayPanel);

        // Add the JTabbedPane to the main frame
        add(tabbedPane1, BorderLayout.CENTER);
        getNom();
        setVisible(true);
    }

    // Employee panel creation method
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Employee form panel
        JPanel pan2 = new JPanel();
        pan2.setLayout(new GridLayout(8, 2));

       
        pan2.add(new JLabel("Nom"));
        pan2.add(saisie);
        pan2.add(new JLabel("Prenom"));
        pan2.add(saisie1);
        pan2.add(new JLabel("Email"));
        pan2.add(saisie2);
        pan2.add(new JLabel("Téléphone"));
        pan2.add(saisie3);
        pan2.add(new JLabel("Salaire"));
        pan2.add(saisie4);
        pan2.add(new JLabel("Role"));
        pan2.add(choix);
        pan2.add(new JLabel("Poste"));
        pan2.add(choix2);

        // Button panel
        JPanel pan4 = new JPanel();
        pan4.setLayout(new FlowLayout());
        pan4.add(ajouterE);
        pan4.add(supprimerE);
        pan4.add(afficherE);
        pan4.add(modifierE);
        pan4.add(importer);
        pan4.add(exporter);

        // Table panel
        String[] columnNames = { "Id", "Nom", "Prenom", "Téléphone", "Email", "Salaire", "Role", "Poste" };
        model1 = new DefaultTableModel(columnNames, 0);
        table1 = new JTable(model1);
        scrollPane1 = new JScrollPane(table1);

        // Combine everything into the employee panel
        panel.add(pan2, BorderLayout.NORTH);
        panel.add(pan4, BorderLayout.SOUTH);
        panel.add(scrollPane1, BorderLayout.CENTER);

        return panel;
    }
    
    public JTextField getFirstName() { return saisie; }
    public JTextField getLastName() { return saisie1; }
    public JTextField getPhone() { return saisie3; }
    public JTextField getEmail() { return saisie2; }
    public JTextField getSalary() { return saisie4; }
    public JComboBox<Rol> getRoles() { return choix; }
    public JComboBox<Poste> getPostes() { return choix2; }
    public JTable getTable() { return table1; }
    public DefaultTableModel getModel() { return model1; }
    public JButton getAddButton() { return ajouterE; }
    public JButton getUpdateButton() { return modifierE; }
    public JButton getDeleteButton() { return supprimerE; }
    public JButton getDisplayButton() { return afficherE; }

    // Holiday panel creation method
    private JPanel createHolidayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Holiday form panel
        JPanel pan2 = new JPanel();
        pan2.setLayout(new GridLayout(4, 2));

        pan2.add(new JLabel("Nom de l'employé :"));
        pan2.add(choixEmploye);
        pan2.add(new JLabel("Type de congé"));
        pan2.add(choixHoliday);
        pan2.add(new JLabel("Date de début :"));
        pan2.add(dateD);
        pan2.add(new JLabel("Date de fin :"));
        pan2.add(dateF);

        // Button panel
        JPanel pan4 = new JPanel();
        pan4.setLayout(new FlowLayout());
        pan4.add(ajouterH);
        pan4.add(supprimerH);
        pan4.add(afficherH);
        pan4.add(modifierH);

        // Table panel
        String[] columnNames = { "Id", "Employe", "Date Début", "Date Fin", "Type" };
        model2 = new DefaultTableModel(columnNames, 0);
        table2 = new JTable(model2);
        scrollPane2 = new JScrollPane(table2);

        // Combine everything into the holiday panel
        panel.add(pan2, BorderLayout.NORTH);
        panel.add(pan4, BorderLayout.SOUTH);
        panel.add(scrollPane2, BorderLayout.CENTER);

        return panel;
       
    }
    
    public String getNom() {
        return (String) choixEmploye.getSelectedItem();
    }
 public String getDateDebut() {
	 return (String) dateD.getSelectedItem();
 }
 
 public String getDateFin() {
	 return (String) dateF.getSelectedItem();
 }
 public String getSelectedHolidayType() {
	 return choixHoliday.getSelectedItem().toString();
 }
 
 public int getSolde() {
     if (getDateDebut() != null && getDateFin() != null) {
         // Conversion des chaînes de caractères en LocalDate
         LocalDate debut = LocalDate.parse(getDateDebut());
         LocalDate fin = LocalDate.parse(getDateFin());
         return (int) java.time.temporal.ChronoUnit.DAYS.between(debut, fin);
     } else {
         throw new IllegalArgumentException("Les dates de début et de fin ne doivent pas être nulles.");
     }
 }
 
 public JComboBox<HolidayType> getTypes() { return choixHoliday; }
 public JComboBox<String> getNomComboBox() {
     return choixEmploye;
 }

 public JComboBox<String> DateDebut() {
     return DateDebut();
 }

 public JComboBox<String> DateFin() {
     return DateFin();
 }
 
 public void clearFields() {
     choixHoliday.setSelectedIndex(-1); // Réinitialise la sélection de la ComboBox
     choixEmploye.setSelectedIndex(-1);
     dateD.setSelectedIndex(-1);
     dateF.setSelectedIndex(-1);
 }
 
 public static void remplirComboBox(JComboBox<String> comboBox, List<String> items) {
     if (comboBox == null) {
         throw new IllegalArgumentException("Le JComboBox fourni est null.");
     }
     if (items == null || items.isEmpty()) {
         System.out.println("Aucune donnée à ajouter dans le JComboBox.");
         return;
     }

     comboBox.removeAllItems();
     for (String item : items) {
         comboBox.addItem(item);
     }
 }
 
 public static void remplirCombo(JComboBox<String> comboBox, List<Employe> items) {
     comboBox.removeAllItems();
     for (Employe employee : items) {
         comboBox.addItem(employee.getNom());
     }
 }
 
 public void afficherMessageError(String message) {
		JOptionPane.showMessageDialog(this, message,"Error",JOptionPane.ERROR_MESSAGE);
	}
	public void afficherMessageSuccess(String message) {
		JOptionPane.showMessageDialog(this, message,"Succes",JOptionPane.INFORMATION_MESSAGE);
	}
	 public void updateTable(Object[][] data) {
	        model2.setRowCount(0); 
	        for (Object[] row : data) {
	        	model2.addRow(row);
	        }
	 }
    public static void main(String[] args) {
        new MainView();
    }
}
