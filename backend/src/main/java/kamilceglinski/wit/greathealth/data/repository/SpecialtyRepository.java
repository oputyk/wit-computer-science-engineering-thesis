package kamilceglinski.wit.greathealth.data.repository;

import java.util.List;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, String> {
    @Query("select distinct s.name from SpecialtyEntity s")
    List<String> getDistinctSpecialties();
}
