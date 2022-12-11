package dto;

import model.Video;

public class VideoDTO {

    private long id;

    public VideoDTO(){
        super();

    }

    public VideoDTO(Video video)
    {
        super();
        this.id = video.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
