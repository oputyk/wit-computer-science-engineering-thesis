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
public class AvailabilityResponseDTO {
    private String uuid;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTill;
}
