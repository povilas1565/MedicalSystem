package dto;

import model.Audio;



public class AudioDTO {

    private long id;

    public AudioDTO(){
        super();
    }

    public AudioDTO(Audio audio)
    {
        super();
        this.id = audio.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
