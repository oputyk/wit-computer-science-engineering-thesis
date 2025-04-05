package kamilceglinski.wit.greathealth.data.repository;

import java.util.List;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String> {
    @Query(value = "select d.* from doctor d join doctor_specialty ds on d.uuid = ds.doctor_uuid and ds.specialty_uuid = ?1",
     nativeQuery = true)
    List<DoctorEntity> findBySpecialty(String specialtyUuid);

    void deleteByUuid(String uuid);
}
