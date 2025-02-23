package kamilceglinski.wit.greathealth.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {
    private String doctorUuid;
    private String serviceUuid;
    private LocalDateTime dateTimeFrom;
}
