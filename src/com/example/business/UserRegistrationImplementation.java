package com.example.business;

import com.example.bo.*;
import com.example.common.*;
import com.example.dao.*;
import com.example.database.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRegistrationImplementation implements UserRegistration {
	
	@Override
	public boolean checkPasswordMatch(int user_id, String password, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException {
		UserRepository passCheck = new UserRepositoryImplementation();
		byte[] salt = passCheck.fetchSaltById(user_id, dbc);
		String storedPassword = passCheck.fetchPasswordById(user_id, dbc);
		if(generatePassword(password,salt).equals(storedPassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void deleteUser(int user_id, DatabaseConnection dbc) throws SQLException {
		UserRepository deleter = new UserRepositoryImplementation();
		try {
			deleter.removeUser(user_id, dbc);
		} catch (Exception e) {
			throw new RuntimeException(ErrorCodes.DATABASE_QUERY);
		}
		
	}

	@Override
	public ArrayList<User> displayAllUsers(DatabaseConnection dbc) throws SQLException {
		UserRepository userFetcher = new UserRepositoryImplementation();
		ArrayList<User> users = userFetcher.fetchAllUsers(dbc);
		return users;
	}
	
	@Override
	public boolean isEmailExists(String email, DatabaseConnection dbc) throws SQLException {
		UserRepository userFetcher = new UserRepositoryImplementation();
		return (userFetcher.fetchUserByEmail(email, dbc)!=null);
	}
	
	@Override
	public void registerUser(User user, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException {
		UserRepository saver = new UserRepositoryImplementation();
		User checkTakenEmail = saver.fetchUserByEmail(user.getEmail(), dbc);
		if (checkTakenEmail != null) {
			throw new RuntimeException(ErrorCodes.EMAIL_ALREADY_EXISTS);
		}
		byte[] salt = getSalt();
		user.setSalt(salt);
		user.setPassword(generatePassword(user.getPassword(), salt));
		saver.saveUser(user, dbc);
	}

	@Override
	public boolean shouldUpdate(User user, DatabaseConnection dbc) throws SQLException {
		UserRepository checker = new UserRepositoryImplementation();
		User toCheck = checker.fetchUserById(user.getId(), dbc);
		if(user.getName().equals(toCheck.getName()) && user.getAge()==toCheck.getAge() && user.getLevel()==toCheck.getLevel()) {
			return false;
		}
		return true;
	}
	
	@Override
	public void updateUser(User user, DatabaseConnection dbc) throws SQLException, NoSuchAlgorithmException {
		UserRepository updater = new UserRepositoryImplementation();
		user.setSalt(getSalt());
		user.setPassword(generatePassword(user.getPassword(), user.getSalt()));
		updater.rewriteUser(user, dbc);
	}
	
	/**
	 * Password encryption method: given a password to encrypt and it's salt value, will encrypt the combination
	 * of password string and salt and return the SHA-256 Hash
	 * @param toHash the password to encrypt
	 * @param salt the salt value to add (concatenate) to the password
	 * @return the SHA-256 hash of the password to encrypt
	 * @throws NoSuchAlgorithmException
	 */
	private static String generatePassword(String toHash, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(salt);
		byte[] bytes = md.digest(toHash.getBytes());
		StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();		
	}

	/**
	 * Produces a random salt value for SHA hashing
	 * @return randomly generated salt value
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
