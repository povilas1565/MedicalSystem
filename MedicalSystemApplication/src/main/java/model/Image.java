package model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Image {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name ="deleted")
    private Boolean deleted;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "bytea")
    private byte[] imageBytes;

        public Image() {
            super();
            deleted = false;
        }


        public byte[] getImageBytes() { return imageBytes; }

        public void setImageBytes(byte[] imageBytes) { this.imageBytes = imageBytes; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

