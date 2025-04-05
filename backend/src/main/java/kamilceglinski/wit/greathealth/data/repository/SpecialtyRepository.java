package kamilceglinski.wit.greathealth.data.repository;

import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, String> {
    void deleteByUuid(String uuid);
}
