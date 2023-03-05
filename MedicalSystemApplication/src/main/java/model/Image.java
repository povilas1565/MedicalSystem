package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dto.ImageDTO;
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

        @Lob
        @Column(name = "bytea")
        private byte[] imageBytes;

        @JsonIgnore
        private Long userId;


        public Image() {
            super();
            deleted = false;
        }

        public Image(Long id) {
            super();
            this.id = id;
            deleted = false;
        }

        public byte[] getImageBytes() { return imageBytes; }

        public void setImageBytes(byte[] imageBytes) { this.imageBytes = imageBytes; }

        public Long getUserId() {
        return userId;
        }

        public void setUserId(Long userId) {
        this.userId = userId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

