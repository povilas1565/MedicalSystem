package dto;

import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallDTO {

    private Date startTime;
    private Date endTime;
    private long duration;
    private List<String> users = new ArrayList<>();
    private List<String> halls = new ArrayList<>();
    private List<String> patients = new ArrayList<>();
    private List<String> doctors = new ArrayList<>();
    private List<String> nurses = new ArrayList<>();
    private Audio audio;
    private Video video;
    private long id;

    public CallDTO(){
        super();

    }

    public CallDTO(Call call)
    {
        super();
        this.duration = call.getDuration();
        this.users = new ArrayList<>();
        for(User user : call.getUsers()) {
            users.add(user.getFirstname());
        }

        this.patients = new ArrayList<>();
        for(Patient patient : call.getPatients()) {
            patients.add(patient.getFirstname());
        }

        this.doctors = new ArrayList<>();
        for(Doctor doctor : call.getDoctors()) {
            doctors.add(doctor.getEmail());
        }

        this.nurses = new ArrayList<>();
        for(Nurse nurse : call.getNurses()) {
            nurses.add(nurse.getEmail());
        }

        this.halls = new ArrayList<>();
        for(Hall hall : call.getHalls())
        {
            halls.add((hall.getName()));
        }

        this.audio = call.getAudio();
        this.video = call.getVideo();
        this.id = call.getId();
    }

    public CallDTO(Date startTime, Date endTime, long duration, List<String> users, List<String> patients, List<String> doctors, List<String> nurses, List<String> halls) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;

        this.users = new ArrayList<>();
        for(String user : users) {
            users.add(user);
        }

        this.patients = new ArrayList<>();
        for(String patient : patients) {
            patients.add(patient);
        }

        this.doctors = new ArrayList<>();
        for(String doctor : doctors) {
            doctors.add(doctor);
        }

        this.nurses = new ArrayList<>();
        for(String nurse : nurses) {
            nurses.add(nurse);
        }

        this.halls = new ArrayList<>();
        for(String hall : halls)
        {
            halls.add(hall);
        }
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getPatients() {
        return patients;
    }

    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    public List<String> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<String> doctors) {
        this.doctors = doctors;
    }

    public List<String> getNurses() {
        return nurses;
    }

    public void setNurses(List<String> nurses) {
        this.nurses = nurses;
    }


    public List<String> getHalls() {
        return halls;
    }

    public void setHalls(List<String> halls) {
        this.halls = halls;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Video getVideo() {
        return video;
    }

    public void  setVideo(Video video) {
        this.video = video;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

