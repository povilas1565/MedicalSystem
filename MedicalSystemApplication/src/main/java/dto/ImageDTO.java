package dto;
import model.Image;

public class ImageDTO {

    private Long id;
    private String name;

    public ImageDTO() {
        super();
    }

    public ImageDTO(Image image) {
        super();
        this.id = image.getId();
        this.name = image.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
