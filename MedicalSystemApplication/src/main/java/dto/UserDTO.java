package dto;

import model.User;
import model.User.UserRole;

public class UserDTO 
{
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private String city;
	private String state;
	private String date_of_birth;
	private String phone;
	private UserRole role;
	
	public UserDTO()
	{
		super();
	}
		
	public UserDTO(String username, String password, String email, String firstname, String lastname, String city,
			 String state, String date_of_birth, String phone,UserRole role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.state = state;
		this.date_of_birth = date_of_birth;
		this.phone = phone;
		this.role = role;
	}

	public UserDTO(User dto)
	{
		super();
		username = dto.getUsername();
		email = dto.getEmail();
		password = dto.getPassword();
		firstname = dto.getFirstname();
		lastname = dto.getLastname();
		city = dto.getCity();
		state = dto.getState();
		date_of_birth = dto.getDate_of_birth();
		phone = dto.getPhone();
		role = dto.getRole();
	}
	

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getDate_of_birth() { return date_of_birth; }
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
