package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DBConstants.JDBC_DRIVER); //STEP 2: Register JDBC driver
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} 
		return DriverManager.getConnection(DBConstants.DB_URL, DBConstants.USER, DBConstants.PASS); //STEP 3: Open a connection
	}
}
