package kamilceglinski.wit.greathealth.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor_specialty")
public class DoctorSpecialtyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "doctor_uuid", nullable = false)
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "specialty_uuid", nullable = false)
    private SpecialtyEntity specialty;
}
