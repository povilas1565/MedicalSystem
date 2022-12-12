package dto;

import model.User;
import model.User.UserRole;

public class SessionUserDTO 
{
	private Boolean isFirstLog;
	private Boolean verified;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private String city;
	private String state;
	private String date_of_birth;
	private String phone;
	private UserRole role;
	
	public SessionUserDTO()
	{
		super();
	}

	public SessionUserDTO(String username, String email, String firstname, String lastname, String city, String state, String date_of_birth, String phone, UserRole role) {
		super();
		this.username = username;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.state = state;
		this.date_of_birth = date_of_birth;
		this.phone = phone;
		this.role = role;
	}	

	public SessionUserDTO(User user)
	{
		super();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.city = user.getCity();
		this.state = user.getState();
		this.date_of_birth = user.getDate_of_birth();
		this.phone = user.getPhone();
		this.role = user.getRole();
		this.isFirstLog = user.getIsFirstLog();
		this.verified = user.getVerified();
	}

	
	public Boolean getIsFirstLog() {
		return isFirstLog;
	}
	
	public void setIsFirstLog(Boolean isFirstLog) {
		this.isFirstLog = isFirstLog;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}




}
