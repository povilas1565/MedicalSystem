package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] audioBytes;


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


