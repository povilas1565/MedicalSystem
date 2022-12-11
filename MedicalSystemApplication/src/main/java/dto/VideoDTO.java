package dto;

import model.Video;

import java.util.Date;

public class VideoDTO {

    private Date startTime;
    private Date endTime;
    private long duration;
    private long id;
    private byte[] videoBytes;

    public VideoDTO(){
        super();

    }

    public VideoDTO(Video video)
    {
        super();
        this.duration = video.getDuration();
        this.id = video.getId();
    }

    public VideoDTO(Date startTime, Date endTime, long duration, byte[] videoBytes) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.videoBytes = videoBytes;
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

    public void setEndTime(Date enTime) {
        this.endTime = endTime;
    }

    public byte[] getVideoBytes() { return videoBytes; }

    public void setVideoBytes(byte[] videoBytes) {
        this.videoBytes = videoBytes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
