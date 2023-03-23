package model;
import lombok.Data;

import javax.persistence.*;

@Data
    @Entity
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name="deleted", nullable = true)
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

        public Image(Long id, String name) {
            super();
            this.id = id;
            this.name = name;
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

