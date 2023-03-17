package dto;

import model.Chat;
import model.Doctor;
import model.Nurse;
import model.Patient;

import java.util.Date;

public class ChatDTO {

    private String description;
    private String name;
    private String message;
    private Date dateAndTime;
    private String doctorEmail;
    private String patientEmail;
    private String nurseEmail;
    private long id;

    public ChatDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ChatDTO(String description, String name, String message, Date dateAndTime, String doctorEmail, String patientEmail, String nurseEmail) {
        super();
        this.description = description;
        this.name = name;
        this.message = message;
        this.dateAndTime = dateAndTime;
        this.doctorEmail = doctorEmail;
        this.patientEmail = patientEmail;
        this.nurseEmail = nurseEmail;
    }

    public ChatDTO(Chat chat) {
        this.description = chat.getDescription();
        this.name = chat.getName();
        this.message = chat.getMessage();
        this.dateAndTime = chat.getDateAndTime();
        this.doctorEmail = chat.getDoctor().getEmail();
        this.patientEmail = chat.getPatient().getEmail();
        this.nurseEmail = chat.getNurse().getEmail();
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

    public String getDoctor() {
        return doctorEmail;
    }

    public void setDoctor(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getPatient() {
        return patientEmail;
    }

    public void setPatient(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getNurse() {
        return nurseEmail;
    }

    public void setNurse(String nurseEmail) {
        this.nurseEmail = nurseEmail;
    }
    }

