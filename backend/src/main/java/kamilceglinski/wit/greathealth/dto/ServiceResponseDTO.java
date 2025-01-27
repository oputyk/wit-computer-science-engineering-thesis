package kamilceglinski.wit.greathealth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseDTO {
    private String uuid;
    private String name;
    private Integer timeInMinutes;
}
