package model;


import javax.persistence.*;
import java.util.Date;

@Entity
    public class Video {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;


    @Column(name = "startTime", nullable = true)
    private Date startTime;

    @Column(name = "endTime", nullable = true)
    private Date endTime;

    @Column(name = "duration", nullable = true)
    private long duration;

    @Column(name = "video", nullable = true)
    private byte[] videoBytes;



    public Video() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Video(Date startTime, Date endTime, long duration, byte[] videoBytes) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.videoBytes = videoBytes;
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

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public byte[] getVideoBytes() {
        return videoBytes;
    }

    public void setVideoBytes(byte[] videoBytes) {
        this.videoBytes = videoBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

