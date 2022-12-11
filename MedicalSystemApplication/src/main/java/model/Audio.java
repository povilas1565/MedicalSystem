package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "audio_bytes")
    private byte[] audioBytes;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Long callId;

    @JsonIgnore
    private Long hallId;


    public Audio() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}


