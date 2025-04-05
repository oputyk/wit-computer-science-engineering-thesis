package kamilceglinski.wit.greathealth.data.repository;

import kamilceglinski.wit.greathealth.data.entity.DoctorSpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialtyEntity, String> {
    void deleteByDoctor_uuidAndUuid(String uuid, String specialtyUuid);
}
