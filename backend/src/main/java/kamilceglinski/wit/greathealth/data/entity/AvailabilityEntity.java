package kamilceglinski.wit.greathealth.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "availability")
public class AvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @ManyToOne
    @JoinColumn(name = "doctor_uuid", nullable = false)
    private DoctorEntity doctor;
    @Column(name = "date_time_from", nullable = false)
    private LocalDateTime dateTimeFrom;
    @Column(name = "date_time_till", nullable = false)
    private LocalDateTime dateTimeTill;
}
