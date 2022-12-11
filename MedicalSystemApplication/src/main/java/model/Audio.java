package model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startTime", nullable = true)
    private Date startTime;

    @Column(name = "endTime", nullable = true)
    private Date endTime;

    @Column(name = "duration", nullable = true)
    private long duration;

    @Column(name = "audio", nullable = true)
    private byte[] audioBytes;

    public Audio() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Audio(Date startTime, Date endTime, long duration, byte[] audioBytes) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.audioBytes = audioBytes;
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


    public byte[] getAudioBytes() {
        return audioBytes;
    }

    public void setAudioBytes(byte[] audioBytes) {
        this.audioBytes = audioBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


