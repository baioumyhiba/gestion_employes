package DAO;

import java.io.IOException;
import java.util.List;

import Model.Employe;

public interface DataImportExport<T> {
//importer les data depuis un fichier
	void importData(String fileName) throws IOException;
	
	//export er les donnees vers un fichier
	
	void exportData(String fileName, List<T> data) throws IOException;

}
