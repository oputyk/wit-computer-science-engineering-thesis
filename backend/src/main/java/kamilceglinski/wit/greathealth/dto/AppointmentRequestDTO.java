package kamilceglinski.wit.greathealth.dto;

import java.time.LocalDateTime;
import kamilceglinski.wit.greathealth.data.entity.StatusEnum;
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
    private StatusEnum status;
}
