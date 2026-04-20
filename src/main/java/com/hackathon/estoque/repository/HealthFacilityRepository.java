package com.hackathon.estoque.repository;

import com.hackathon.estoque.model.health.HealthFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthFacilityRepository extends JpaRepository<HealthFacility, Long> {
    
    boolean existsByCnes(String cnes);
    
    Optional<HealthFacility> findByCnes(String cnes);
    
    List<HealthFacility> findByNameContainingIgnoreCase(String name);
    
    List<HealthFacility> findByEmailContainingIgnoreCase(String email);
    
    @Query("SELECT hf FROM HealthFacility hf WHERE hf.address.city = :city")
    List<HealthFacility> findByCity(@Param("city") String city);
    
    @Query("SELECT hf FROM HealthFacility hf WHERE hf.address.state = :state")
    List<HealthFacility> findByState(@Param("state") String state);
    
    @Query("SELECT hf FROM HealthFacility hf WHERE hf.address.cep = :cep")
    List<HealthFacility> findByCep(@Param("cep") String cep);
}
