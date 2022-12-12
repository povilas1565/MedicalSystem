package dto;


import model.Image;

public class ImageDTO {

    private long id;

    public ImageDTO(){
        super();
    }

    public ImageDTO(Image image)
    {
        super();
        this.id = image.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
