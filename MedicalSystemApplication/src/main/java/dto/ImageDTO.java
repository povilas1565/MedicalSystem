package dto;
import model.Image;

public class ImageDTO {

    private Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

}
