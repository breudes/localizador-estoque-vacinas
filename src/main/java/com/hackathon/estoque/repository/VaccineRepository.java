package com.hackathon.estoque.repository;

import com.hackathon.estoque.model.health.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    boolean existsByVaccineName(String vaccineName);

    Optional<Vaccine> findByVaccineName(String vaccineName);

    List<Vaccine> findByPreventedDiseaseContaining(String preventedDisease);

    List<Vaccine> findByLifeStageContaining(String lifeStage);
    
    @Query("SELECT v FROM Vaccine v WHERE " +
           "(:initialAge IS NULL OR v.initialAgeInYears <= :initialAge) AND " +
           "(:finalAge IS NULL OR v.finalAgeInYears >= :finalAge)")
    List<Vaccine> findByAgeRange(@Param("initialAge") Integer initialAge, 
                                @Param("finalAge") Integer finalAge);

    @Query("SELECT v FROM Vaccine v WHERE " +
            "(:initialAge IS NULL OR v.initialAgeInMonths <= :initialAge) AND " +
            "(:finalAge IS NULL OR v.finalAgeInMonths >= :finalAge)")
    List<Vaccine> findByAgeRangeInMonths(@Param("initialAge") Integer initialAge,@Param("finalAge") Integer finalAge);
}
