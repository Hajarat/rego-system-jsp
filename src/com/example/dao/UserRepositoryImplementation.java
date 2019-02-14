package com.example.dao;

import com.example.bo.*;
import com.example.common.ErrorCodes;
import com.example.database.*;

import java.security.*;
import java.sql.*;
import java.util.ArrayList;

public class UserRepositoryImplementation implements UserRepository {
	
	@Override
	public ArrayList<User> fetchAllUsers(DatabaseConnection dbc) throws SQLException {
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
		+ "SELECT * FROM users WHERE deleted=0 ORDER BY user_id");
		ResultSet rs = stmt.executeQuery();
		ArrayList<User> users = new ArrayList<User>();
		while(rs.next()) {
			users.add(new User(rs.getInt("user_id"), rs.getString("user_name"),
					rs.getInt("user_age"), rs.getInt("user_level"),
					rs.getString("email")));
		}
		stmt.close();
		return users;
	}
	
	@Override
	public String fetchPasswordById(int user_id, DatabaseConnection dbc) throws SQLException {
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
		+ "SELECT password FROM shadow WHERE user_id=?");
		stmt.setInt(1, user_id);
		ResultSet rs = stmt.executeQuery();
		String password = null;
		while(rs.next()) {
			password = rs.getString("password");
		}
		stmt.close();
		return password;
	}

	@Override
	public byte[] fetchSaltById(int user_id, DatabaseConnection dbc) throws SQLException {
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
		+ "SELECT salt FROM shadow WHERE user_id=?");
		stmt.setInt(1, user_id);
		ResultSet rs = stmt.executeQuery();
		byte[] salt = null;
		while(rs.next()) {
			salt = rs.getBytes("salt");
		}
		stmt.close();
		return salt;
	}
	
	@Override
	public User fetchUserByEmail(String email, DatabaseConnection dbc) throws SQLException {
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
				+ "SELECT * FROM users WHERE email=?");
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		if(rs.next() == false) {
			stmt.close();
			return null;
		} else {
			User user = new User(rs.getInt("user_id"), rs.getString("user_name"),
					rs.getInt("user_age"), rs.getInt("user_level"),
					rs.getString("email"));
			stmt.close();
			return user;
		}
	}
	
	@Override
	public User fetchUserById(int user_id, DatabaseConnection dbc) throws SQLException {
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
		+ "SELECT * FROM users WHERE user_id=?");
		stmt.setInt(1, user_id);
		ResultSet rs = stmt.executeQuery();
		if(rs.next() == false) {
			stmt.close();
			throw new RuntimeException(ErrorCodes.DATABASE_QUERY);
		} else {
			User user = new User(rs.getInt("user_id"), rs.getString("user_name"),
					rs.getInt("user_age"), rs.getInt("user_level"),
					rs.getString("email"));
			stmt.close();
			return user;
		}
		
	}

	@Override
	public void removeUser(int user_id, DatabaseConnection dbc) throws SQLException {
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
		+ "UPDATE users SET deleted=1 WHERE user_id=?");
		stmt.setInt(1, user_id);
		stmt.execute();
		stmt.close();
	}
	
	@Override
	public void rewriteUser(User user, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException {
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
		+ "UPDATE users SET user_name=?,user_age=?,user_level=? WHERE user_id=?");
		stmt.setString(1, user.getName());
		stmt.setInt(2, user.getAge());
		stmt.setInt(3, user.getLevel());
		stmt.setInt(4, user.getId());
		stmt.execute();
		stmt.close();
		
		if(user.getPassword().length() != 0) {
			PreparedStatement stmt2 = dbc.getConnection().prepareStatement(""
			+ "UPDATE shadow SET password=?,salt=? WHERE user_id=?");
			stmt2.setString(1, user.getPassword());
			stmt2.setBytes(2, user.getSalt());
			stmt2.setInt(3, user.getId());
			stmt2.execute();
			stmt2.close();
		}
	}
	
	@Override
	public void saveUser(User user, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException {	
		PreparedStatement stmt = dbc.getConnection().prepareStatement(""
		+ "INSERT INTO users(user_name, user_age, user_level, email) VALUES (?,?,?,?)");
		stmt.setString(1, user.getName());
		stmt.setInt(2, user.getAge());
		stmt.setInt(3, user.getLevel());
		stmt.setString(4, user.getEmail());
		stmt.execute();
		stmt.close();
		
		//Save private details to shadow table
		PreparedStatement stmt2 = dbc.getConnection().prepareStatement(""
		+ "INSERT INTO shadow(user_id, password, salt) VALUES (?,?,?)");
		int user_id = fetchUserByEmail(user.getEmail(), dbc).getId();
		stmt2.setInt(1, user_id);
		stmt2.setString(2, user.getPassword());
		stmt2.setBytes(3, user.getSalt());
		stmt2.execute();
		stmt2.close();
	}

}
