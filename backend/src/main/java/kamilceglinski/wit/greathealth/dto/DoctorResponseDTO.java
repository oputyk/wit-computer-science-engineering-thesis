package kamilceglinski.wit.greathealth.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDTO {
    private String uuid;
    private String email;
    private String name;
    private String surname;
    private String pesel;
    private List<SpecialtyResponseDTO> specialties;
}
