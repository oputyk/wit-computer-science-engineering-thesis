package kamilceglinski.wit.greathealth.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "appointment")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @ManyToOne
    @JoinColumn(name = "patient_uuid", nullable = false)
    private PatientEntity patient;
    @ManyToOne
    @JoinColumn(name = "doctor_uuid", nullable = false)
    private DoctorEntity doctor;
    @ManyToOne
    @JoinColumn(name = "service_uuid", nullable = false)
    private ServiceEntity service;
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime dateTimeFrom;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
