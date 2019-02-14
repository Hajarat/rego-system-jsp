package com.example.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

import com.example.bo.*;
import com.example.database.*;

public interface UserRepository {
	
	/**
	 * Fetch all undeleted users from the database provided by the given connection
	 * @param dbc the database connection object
	 * @return all undeleted users
	 * @throws SQLException
	 */
	public ArrayList<User> fetchAllUsers(DatabaseConnection dbc) throws SQLException;
	
	public String fetchPasswordById(int user_id, DatabaseConnection dbc) throws SQLException;
	
	public byte[] fetchSaltById(int user_id, DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Fetch a user given an email from the database provided by the given connection
	 * @param email the email to identify the desired user with
	 * @param dbc the database connection object
	 * @return User with the provided email
	 * @throws SQLException
	 */
	public User fetchUserByEmail(String email, DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Fetch a user given an id from the database provided by the given connection
	 * @param user_id the id of the desired user
	 * @param dbc the database connection object
	 * @return User with the provided id
	 * @throws SQLException
	 */
	public User fetchUserById(int user_id, DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Delete the user of the given id from the database provided by the given connection
	 * @param user_id the id of the user to delete
	 * @param dbc the database connection object
	 * @throws SQLException
	 */
	public void removeUser(int user_id, DatabaseConnection dbc) throws SQLException;
	
	/**
	 * Change the details of the pre-existing user from the database provided by the given connection
	 * @param user the pre-existing user (with particular changes)
	 * @param dbc the database connection object
	 * @throws SQLException
	 */
	public void rewriteUser(User user, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException;
	
	
	/**
	 * Save user provided into the database provided by the given connection
	 * @param user user to be saved
	 * @param dbc the database connection object
	 * @throws SQLException
	 */
	public void saveUser(User user, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException;
}
