package dto;

import model.Audio;
import model.Hall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AudioDTO {

    private Date startTime;
    private Date endTime;
    private long duration;
    private long id;
    private byte[] audioBytes;

    public AudioDTO(){
        super();
    }

    public AudioDTO(Audio audio)
    {
        super();
        this.duration = audio.getDuration();
        this.id = audio.getId();
    }

    public AudioDTO(Date startTime, Date endTime, long duration, byte[] audioBytes) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.audioBytes = audioBytes;
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

    public byte[] getAudioBytes() { return audioBytes; }

    public void setAudioBytes(byte[] audioBytes) {
        this.audioBytes = audioBytes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
