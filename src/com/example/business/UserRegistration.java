package com.example.business;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.bo.*;
import com.example.database.*;

public interface UserRegistration {
	
	/**
	 * Check if the password provided for the given user id matches the one stored in the password folder
	 * @param user_id the identification to use to retrieve stored password hash and salt
	 * @param password the password to hash in order to compare with database entry
	 * @param dbc the database connection object
	 * @return whether the authentication password matches the one stored in the database
	 * @throws SQLException
	 */
	public boolean checkPasswordMatch(int user_id, String password, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException;
	
	/**
	 * Given a user's id, send that information to the dao for deletion from the given database connection
	 * @param user_id id of the user to be deleted
	 * @param dbc the database connection object
	 * @throws SQLException
	 */
	public void deleteUser(int user_id, DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Retrieve all undeleted users by forwarding a request to the dao with the given connection
	 * @param dbc the database connection object
	 * @return an arraylist of all undeleted users from the given database connection
	 * @throws SQLException
	 */
	public ArrayList<User> displayAllUsers(DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Determines wether provided email exists in the database or not
	 * @param email the email to check
	 * @param dbc the database connection object
	 * @throws SQLException
	 * @return true if email exists in the given database, false otherwise
	 */
	public boolean isEmailExists(String email, DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Check if the user can be registered by accessing the dao, then send user details to dao for registration when applicable
	 * @param user user to insert into database
	 * @param dbc the database connection object
	 * @throws SQLException
	 */
	public void registerUser(User user, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException;
	
	/**
	 * Check whether the user had some values changed compared to his/her database entry or not
	 * @param user the user to check
	 * @param dbc the database connection object
	 * @throws SQLException
	 * @return true if the update should be performed, false otherwise
	 */
	public boolean shouldUpdate(User user, DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Check if provided user details can be updated by asking dao, then update given user when applicable
	 * @param user user to change
	 * @param dbc the database connection object
	 * @throws SQLException
	 */
	public void updateUser(User user, DatabaseConnection dbc) throws SQLException , NoSuchAlgorithmException;
}
