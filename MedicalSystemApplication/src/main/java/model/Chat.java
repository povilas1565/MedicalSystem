package model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateAndTime", nullable = true)
    private Date dateAndTime;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "message", nullable = true)
    private String message;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    public Chat() {
        super();
        this.deleted = false;
    }

    public Chat(Long id, Date dateAndTime, String name, String message, String description, Doctor doctor, Patient patient, Nurse nurse) {
        super();
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.name = name;
        this.message = message;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.nurse = nurse;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


}
