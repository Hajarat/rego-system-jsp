package com.example.bo;



public class User{
	private int id;
	private String name;
	private int age;
	private int level;
	private String email;
	private String password;
	private byte[] salt;
	private boolean deleted;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	/**
	 * Custom constructor for user display only (no password information
	 * @param id id
	 * @param name usernmae
	 * @param age age
	 * @param level level
	 * @param email email
	 */
	public User(int id, String name, int age, int level, String email) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.level = level;
		this.email = email;
		this.setDeleted(false);
	}

	/**
	 * Custom constructor for retrieving database rows by specifying id from request object
	 * @param id id
	 * @param name username 
	 * @param age age
	 * @param level level
	 * @param email email
	 * @param password password
	 */
	public User(int id, String name, int age, int level, String email, String password) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.level = level;
		this.email = email;
		this.password = password;
		this.setDeleted(false);
	}
	
	/**
	 * Custom constructor for object insertion into database
	 * @param name username 
	 * @param age age
	 * @param level level
	 * @param email email
	 * @param password password
	 */
	public User(String name, int age, int level, String email, String password) {
		this.name = name;
		this.age = age;
		this.level = level;
		this.email = email;
		this.password = password;
		this.setDeleted(false);
	}
	
	/**
	 * Custom constructor used to retrieve only non-deleted objects from database
	 * @param id id
	 * @param name username 
	 * @param age age
	 * @param level level
	 * @param email email
	 * @param password password
	 * @param deleted flag for whether object is marked as deleted or not on the database
	 */
	public User(int id, String name, int age, int level, String email, String password, boolean deleted) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.level = level;
		this.email = email;
		this.password = password;
		this.deleted = deleted;
	}
		
	/**
	 * Basic constructor, helpful for creating a user object quickly if usage of getters/setters is desired to manipulate object
	 */
	public User() {}

}
