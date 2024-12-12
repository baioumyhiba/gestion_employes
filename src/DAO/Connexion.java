package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

	//attributs
	
	private static final String URL = "jdbc:mysql://localhost:3306/BDEmploye";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	static Connection conn = null;
	
	public static Connection getConnexion() {
		if(conn == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}catch(ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
				
				throw new RuntimeException("Error lors de la connexion");
			}
		}
		return conn;
	}
}
