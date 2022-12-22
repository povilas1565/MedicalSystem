
package model;

import javax.persistence.*;

import helpers.UserBuilder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class CentreAdmin extends User {

	
    @ManyToOne(fetch = FetchType.EAGER)
    private Centre centre;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AppointmentRequest> appointmentRequests;

    @OneToMany(fetch = FetchType.LAZY)
    private  List<VacationRequest> vacationRequests;

    public CentreAdmin(){
        super();
        setRole(UserRole.CentreAdmin);
    	this.setIsFirstLog(true);
    }

    public CentreAdmin(String username, String password, String email, String firstname, String lastname, String city,  String state, String date_of_birth, String phone, Centre centre) {
        super(username, password, email, firstname, lastname, city, state, date_of_birth, phone, UserRole.CentreAdmin);
        this.centre = centre;
        this.appointmentRequests = new ArrayList<>();
        this.vacationRequests = new ArrayList<>();
        this.setIsFirstLog(true);
    }

    public CentreAdmin(User user)
    {
    	super(user);
    	this.appointmentRequests = new ArrayList<>();
        this.vacationRequests = new ArrayList<>();
        this.setIsFirstLog(true);
    }

    public Centre getCentre() {
        return centre;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    public List<AppointmentRequest> getAppointmentRequests() {
        return appointmentRequests;
    }

    public void setAppointmentRequests(List<AppointmentRequest> appointmentRequests) {
        this.appointmentRequests = appointmentRequests;
    }

    public List<VacationRequest> getVacationRequests() {
        return vacationRequests;
    }

    public void setVacationRequests(List<VacationRequest> vacationRequests) {
        this.vacationRequests = vacationRequests;
    }
    
    public static class Builder extends UserBuilder
    {
    	public Centre centre;
		
		public Builder(String email)
		{
			super(email);
		}


		public Builder withUsername(String username)
		{
			super.withUsername(username);

			return this;
		}

		public Builder withPassword(String password)
		{
			super.withPassword(password);
			
			return this;
		}

		public Builder withFirstname(String firstname)
		{
			super.withFirstname(firstname);
			
			return this;
		}
	
		public Builder withLastname(String lastname)
		{
			super.withLastname(lastname);
			
			return this;
		}
		
		public Builder withCity(String city)
		{
			super.withCity(city);
			
			return this;
		}

		
		public Builder withState(String state)
		{
			super.withState(state);
			
			return this;
		}

		public Builder withDate_of_birth(String date_of_birth) {
			super.withDate_of_birth(date_of_birth);
			return this;
		}
		
		public Builder withPhone(String phone)
		{
			super.withPhone(phone);
			
			return this;
		}
		
		public Builder withCentre(Centre centre)
		{
			this.centre = centre;
			
			return this;
		}
		
		public CentreAdmin build()
		{
			super.withRole(UserRole.CentreAdmin);
			User user = super.build();
			CentreAdmin a = new CentreAdmin(user);
			a.setCentre(this.centre);
			return a;
		}
    }
}

