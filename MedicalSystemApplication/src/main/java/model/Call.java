package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Call {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

       @Column(name = "startTime", nullable = false)
       private Date startTime;

       @Column(name = "endTime", nullable = false)
       private Date endTime;

        @Column(name = "duration", nullable = false)
        private long duration;

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY)
        private List<User> users = new ArrayList<>();

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

        public Call(long duration, List<User> users) {
                this.duration = duration;
                this.users = users;

        }

        public Call(Date startTime, Date endTime, long duration, Audio audio, Video video) {

                this.startTime = startTime;
                this.endTime = endTime;
                this.duration = duration;
                this.users = new ArrayList<>();
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}


