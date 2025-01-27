package kamilceglinski.wit.greathealth.data.repository;

import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
}
