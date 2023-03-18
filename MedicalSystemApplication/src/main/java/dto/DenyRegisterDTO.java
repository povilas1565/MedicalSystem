package dto;

import lombok.Data;

@Data
public class DenyRegisterDTO {
    private String email;
    private String rejectionReason;
}
