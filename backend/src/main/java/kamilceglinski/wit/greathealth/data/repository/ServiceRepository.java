package kamilceglinski.wit.greathealth.data.repository;

import java.util.List;
import java.util.Optional;
import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    Optional<ServiceEntity> findBySpecialtyInAndUuid(List<SpecialtyEntity> specialties, String uuid);
}
