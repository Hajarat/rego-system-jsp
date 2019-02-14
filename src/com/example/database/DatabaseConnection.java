package com.example.database;
import java.sql.*;

public interface DatabaseConnection {
	
	/**
	 * Getter method for database connection object
	 * @return Connection object
	 */
	public Connection getConnection();
	
	/**
	 * Create connection to database by extracting login details from resource file
	 */
	public void createConnection();
	
	/**
	 * Close the connection created for the current DatabaseConnection object
	 */
	public void closeConnection();
}
