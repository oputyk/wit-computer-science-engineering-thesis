package kamilceglinski.wit.greathealth.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "specialty")
public class SpecialtyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(targetEntity = DoctorSpecialtyEntity.class, mappedBy = "doctor")
    private List<DoctorSpecialtyEntity> doctors;
}
