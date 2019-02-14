package com.example.database;

import com.example.common.ErrorCodes;

import java.sql.*;
import java.util.*;

public class DatabaseConnectionImplementation implements DatabaseConnection {
	private Connection conn;
	
	public Connection getConnection() {return conn;}
	
	@Override
	public void createConnection() {
		Locale locale = new Locale("en", "US");
		ResourceBundle labels = ResourceBundle.getBundle("resources", locale);
		try {
			Class.forName(labels.getString("driver"));
			conn = DriverManager.getConnection(labels.getString("db_name"), 
					labels.getString("db_user"), labels.getString("db_password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(ErrorCodes.DATABASE_CONNECTION);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(ErrorCodes.DATABASE_CONNECTION);
		}
	}
	
	@Override
	public void closeConnection() {
		try {
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(ErrorCodes.DATABASE_CONNECTION);
		}
	}	
}
