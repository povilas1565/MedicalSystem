package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import model.Appointment.AppointmentType;


@Data
@Entity
public class AppointmentRequest {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name= "startingDateAndTime",nullable = false)
	private Date date;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Hall hall;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centre_id")
	private Centre centre;
		
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Doctor> doctors;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "priceslist_id")
	private Priceslist priceslist;
	
	@Column(name = "appointmentType", nullable = true)
	private AppointmentType appointmentType;
	
	@Column(name = "timestamp")
	private Date timestamp;
	
	public AppointmentRequest()
	{
		super();
		this.doctors = new ArrayList<>();
	}
		
	public AppointmentRequest(Long id, Date date,Hall hall, Patient patient, Centre centre,
			Priceslist priceslist, AppointmentType appointmentType) {
		super();
		this.id = id;
		this.date = date;
		this.hall = hall;
		this.patient = patient;
		this.centre = centre;
		this.doctors = new ArrayList<>();
		this.priceslist = priceslist;
		this.appointmentType = appointmentType;
	}

	public AppointmentRequest(Date date, Hall hall, Patient patient, Centre centre, Priceslist priceslist, AppointmentType appointmentType) {
		this.date = date;
		this.hall = hall;
		this.patient = patient;
		this.centre = centre;
		this.doctors = new ArrayList<>();
		this.priceslist = priceslist;
		this.appointmentType = appointmentType;
		this.timestamp = new Date();
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Centre getCentre() {
		return centre;
	}

	public void setCentre(Centre centre) {
		this.centre = centre;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Priceslist getPriceslist() {
		return priceslist;
	}

	public void setPriceslist(Priceslist priceslist) {
		this.priceslist = priceslist;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
	
	
	
}
