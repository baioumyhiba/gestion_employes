package Main;

import Controller.EmployeController;
import Controller.HolidayController;  // Si vous avez un HolidayController
import DAO.EmployeDAOImpl;
import DAO.HolidayDAOImpl;  // Si vous avez une classe HolidayDAOImpl
import Model.Employe;
import Model.EmployeModel;
import Model.Holiday;
import Model.HolidayModel;  // Si vous avez un modèle pour les congés
import Model.HolidayType;
import Model.Poste;
import Model.Rol;
import View.MainView;

public class Main {
    public static void main(String[] args) {
     
        EmployeDAOImpl employeDAO = new EmployeDAOImpl();
        EmployeModel employeModel = new EmployeModel(employeDAO);

        HolidayDAOImpl holidayDAO = new HolidayDAOImpl();  // Si vous avez un DAO pour les congés
        HolidayModel holidayModel = new HolidayModel(holidayDAO);  // Si vous avez un modèle pour les congés

        // Créer l'instance de MainView qui gère à la fois les employés et les congés
        MainView mainView = new MainView();

        // Initialiser les contrôleurs
        EmployeController employeController = new EmployeController(mainView, employeModel);
        HolidayController holidayController = new HolidayController(mainView, holidayModel);  // Si vous avez un HolidayController

        // Rendre la MainView visible
        mainView.setVisible(true);
        
   
    }
}
