package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Call {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

       @Column(name = "startTime", nullable = true)
       private Date startTime;

       @Column(name = "endTime", nullable = true)
       private Date endTime;

        @Column(name = "duration", nullable = true)
        private long duration;

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY)
        private List<User> users = new ArrayList<>();

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY)
        private List<Patient> patients = new ArrayList<>();

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY)
        private List<Doctor> doctors = new ArrayList<>();

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY)
        private List<Nurse> nurses = new ArrayList<>();

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY)
        private List<Hall> halls = new ArrayList<>();

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "audio_id")
        private Audio audio;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "video_id")
        private Video video;



        public Call() {
                super();
                // TODO Auto-generated constructor stub
        }

        public Call(long duration, List<User> users, List<Patient> patients, List<Doctor> doctors, List<Nurse> nurses, List<Hall> halls) {
                this.duration = duration;
                this.users = users;
                this.patients = patients;
                this.doctors = doctors;
                this.nurses = nurses;
                this.halls = halls;

        }

        public Call(Date startTime, Date endTime, long duration, Audio audio, Video video) {

                this.startTime = startTime;
                this.endTime = endTime;
                this.duration = duration;
                this.users = new ArrayList<>();
                this.patients = new ArrayList<>();
                this.doctors = new ArrayList<>();
                this.nurses = new ArrayList<>();
                this.halls = new ArrayList<>();
                this.audio = audio;
                this.video = video;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) { this.duration = duration;}

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) { this.audio = audio; }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) { this.video = video; }


    public List<User> getUsers() { return users; }

    public void setUsers(List<User> users) { this.users = users; }

    public List<Patient> getPatients() { return patients; }

    public void setPatients(List<Patient> patients) { this.patients = patients; }

    public List<Doctor> getDoctors() { return doctors; }

    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }

    public List<Nurse> getNurses() { return nurses; }

    public void setNurses(List<Nurse> nurses) { this.nurses = nurses; }

    public List<Hall> getHalls() { return halls; }

    public void setHalls(List<Hall> halls) { this.halls = halls; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}


