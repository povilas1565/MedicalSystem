package dto;

import model.Video;

public class VideoDTO {

    private Long id;

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

    public void setId(Long id) {
        this.id = id;
    }
}
