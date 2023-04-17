package dto;

import lombok.Builder;
import java.util.Date;

@Builder
public class ChatDTO {

    private String description;
    private String name;
    private String message;
    private Date dateAndTime;
    private String doctorEmail;
    private String patientEmail;
    private String nurseEmail;
    private long id;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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


