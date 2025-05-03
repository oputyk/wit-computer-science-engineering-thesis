package kamilceglinski.wit.greathealth.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableAppointmentTimeResponseDTO {
    private DoctorResponseDTO doctor;
    private ServiceResponseDTO service;
    private LocalDateTime dateTimeFrom;
}

