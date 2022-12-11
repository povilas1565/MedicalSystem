package model;

import javax.persistence.*;
import dto.UserDTO;


@Entity(name = "users")
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User
{
	public enum UserRole{ Patient, Doctor, Nurse, CentreAdmin}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="deleted",nullable = true)
	private Boolean deleted;
	
	@Column(name = "isFirstLog", nullable = false)
	private Boolean isFirstLog;

	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "firstname", nullable = false)
	private String firstname;
	
	@Column(name = "lastname", nullable = false)
	private String lastname;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "date_of_birth", nullable = false)
	private String date_of_birth;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "role", nullable = false)
	private UserRole role;
	
	@Column(name="verified")
	private Boolean verified;
	
	public User()
	{
		super();
		this.deleted = false;
		this.verified = true;
	}

	public User(String password, String email, String firstname, String lastname, String city, String state, String date_of_birth, String phone, UserRole role) {
		super();
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.state = state;
		this.date_of_birth = date_of_birth;
		this.phone = phone;
		this.role = role;
		this.deleted = false;
		this.verified = true;
	}
	
	public User(RegistrationRequest request, UserRole role)
	{
		super();
		this.username = request.getUsername();
		this.password = request.getPassword();
		this.email = request.getEmail();
		this.phone = request.getPhone();
		this.role = role;
		this.deleted = false;
		this.verified = true;
	}
	
	

	public User(User user) {
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.city = user.getCity();
		this.state = user.getState();
		this.date_of_birth = user.getDate_of_birth();
		this.phone = user.getPhone();
		this.role = user.getRole();
		this.deleted = false;
		this.verified = true;
	}

	
	
	public User(UserDTO user) {
		// TODO Auto-generated constructor stub
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.city = user.getCity();
		this.state = user.getState();
		this.date_of_birth = user.getDate_of_birth();
		this.phone = user.getPhone();
		this.role = user.getRole();
		this.deleted = false;
		this.verified = true;
	}

	
	
	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getIsFirstLog() {
		return isFirstLog;
	}

	public void setIsFirstLog(Boolean isFirstLog) {
		this.isFirstLog = isFirstLog;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) { this.date_of_birth = date_of_birth;}

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

	public Long getId() {
		return id;
	}
}
