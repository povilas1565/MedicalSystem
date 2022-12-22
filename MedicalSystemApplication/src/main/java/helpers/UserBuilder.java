package helpers;

import model.User;
import model.User.UserRole;

public class UserBuilder {
	private String username;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String city;
	private String state;
	private String date_of_birth;
	private String phone;
	private UserRole role;

	protected UserBuilder(String email)
	{
		this.email = email;
	}

	protected UserBuilder withUsername(String username) {
		this.username = username;
		return this;
	}

	protected UserBuilder withPassword(String password)
	{
		this.password = password;
		
		return this;
	}
	
	protected UserBuilder withFirstname(String firstname)
	{
		this.firstname = firstname;
		
		return this;
	}
	
	protected UserBuilder withLastname(String lastname)
	{
		this.lastname = lastname;
		
		return this;
	}
	
	protected UserBuilder withCity(String city)
	{
		this.city = city;
		
		return this;
	}

	
	protected UserBuilder withState(String state)
	{
		this.state = state;
		
		return this;
	}

	protected UserBuilder withDate_of_birth(String date_of_birth)
	{
		this.date_of_birth = date_of_birth;

		return this;
	}
	
	protected UserBuilder withPhone(String phone)
	{
		this.phone = phone;
		
		return this;
	}
	
	protected UserBuilder withRole(UserRole role)
	{
		this.role = role;
		
		return this;
	}

	
	protected User build()
	{
		User user = new User();
		user.setEmail(this.email);
		user.setUsername(this.username);
		user.setPassword(this.password);
		user.setFirstname(this.firstname);
		user.setLastname(this.lastname);
		user.setCity(this.city);
		user.setState(this.state);
		user.setDate_of_birth((this.date_of_birth));
		user.setPhone(this.phone);
		user.setRole(this.role);
		return user;
	}
	
	
}
