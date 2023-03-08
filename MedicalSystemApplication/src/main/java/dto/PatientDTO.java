package dto;

import model.Patient;
import model.User;

public class PatientDTO {

    private UserDTO user;
    private Long medicalRecordId;

    public PatientDTO() {
        super();
    }

    public PatientDTO(UserDTO user, Long medicalRecordId) {
        super();
        this.user = user;
        this.medicalRecordId = medicalRecordId;;
    }

    public PatientDTO(Patient p)
    {
        UserDTO dto = new UserDTO();
        dto.setUsername(p.getUsername());
        dto.setFirstname(p.getFirstname());
        dto.setLastname(p.getLastname());
        dto.setEmail(p.getEmail());
        dto.setCity(p.getCity());
        dto.setPhone(p.getPhone());
        dto.setState(p.getState());
        dto.setDate_of_birth(p.getDate_of_birth());
        dto.setRole(User.UserRole.Patient);
        this.user = dto;

        if(p.getMedicalRecord() != null)
        {
            this.medicalRecordId = p.getMedicalRecord().getId();
        }
        else
        {
            this.medicalRecordId = Long.valueOf("N/A");
        }
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(Long medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

}

