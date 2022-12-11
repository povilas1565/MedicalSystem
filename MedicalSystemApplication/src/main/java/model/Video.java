package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
    public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] videoBytes;


    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Long callId;

    @JsonIgnore
    private Long doctorId;

    @JsonIgnore
    private Long patientId;

    @JsonIgnore
    private Long nurseId;

    @JsonIgnore
    private Long hallId;
}

