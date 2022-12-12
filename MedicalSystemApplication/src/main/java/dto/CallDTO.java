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
            users.add(user.getUsername());
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

