package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
public class Hall
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@OneToOne()
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "centre_id")
	private Centre centre;
	
	@Column(name= "number",nullable = false)
	private int number; 	
	
	@Column(name= "name",nullable = false)
	private String name;
	
	@Column(name="deleted",nullable = false)
	private Boolean deleted;

	public Hall() {
		super();
		// TODO Auto-generated constructor stub
		deleted = false;
	}
	
	
	public Hall(Centre centre, int number, String name) {
		super();
		this.centre = centre;
		this.number = number;
		this.name = name;
		deleted = false;
	}

	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Centre getCentre() {
		return centre;
	}
	public void setCentre(Centre centre) {
		this.centre = centre;
	}


	@Override
	public String toString() {
		return "Hall [hallID=" + id + ", centre=" + centre + ", number=" + number + ", scheduleAppointments="
				+ "]";
	}
	
}
