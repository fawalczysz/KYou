package fr.isima.kyou.beans.dao;

public class User {

	private Integer id;
	private String firstname;
	private String lastname;
	private String email;

	public User(String firstname, String lastname, String email) {
		setFirstname(firstname);
		setLastname(lastname);
		setEmail(email);
	}

	public User() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
