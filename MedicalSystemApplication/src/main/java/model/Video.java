package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;


@Entity
    public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "video_bytes")
    private byte[] videoBytes;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Long callId;


    public Video() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getVideoBytes() { return videoBytes; }

    public void setVideoBytes(byte[] videoBytes) { this.videoBytes = videoBytes; }

}



