package com.hackathon.estoque.repository;

import com.hackathon.estoque.model.health.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByVaccineId(Long vaccineId);

    Optional<Inventory> findByHealthFacilityIdAndVaccineIdAndBatch(Long healthFacilityId, Long vaccineId, String batch);
    
    List<Inventory> findByActiveTrue();
    
    List<Inventory> findByHealthFacilityIdAndActiveTrue(Long healthFacilityId);
    
    @Query("SELECT i FROM Inventory i WHERE i.expirationDate <= :date AND i.active = true")
    List<Inventory> findExpiredInventories(@Param("date") java.util.Date date);
    
    @Query("SELECT i FROM Inventory i WHERE i.stock <= :threshold AND i.active = true")
    List<Inventory> findLowStockInventories(@Param("threshold") int threshold);
    
    @Query("SELECT i FROM Inventory i WHERE i.healthFacility.id = :healthFacilityId AND i.vaccine.id = :vaccineId AND i.active = true")
    List<Inventory> findActiveInventoryByFacilityAndVaccine(@Param("healthFacilityId") Long healthFacilityId, 
                                                          @Param("vaccineId") Long vaccineId);
}
