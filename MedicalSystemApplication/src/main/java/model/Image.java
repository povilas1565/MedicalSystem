package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
    @Entity
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Lob
        @Column(name = "image_bytes")
        private byte[] imageBytes;

        @JsonIgnore
        private Long userId;

        public Image() {
            super();
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

