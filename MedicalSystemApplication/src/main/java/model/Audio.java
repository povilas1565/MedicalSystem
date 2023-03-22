package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Audio {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "bytea")
    private byte[] audioBytes;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Long callId;


    public Audio() {
        super();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAudioBytes() { return audioBytes; }

        public void setAudioBytes(byte[] audioBytes) { this.audioBytes = audioBytes; }

}



