package  pixeon.tech.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pixeon.tech.challenge.entity.HealthCareInstitution;

/**
 * @author Anderson Virginio Martins
 */
@Repository
public interface HealthCareInstitutionRepository extends JpaRepository<HealthCareInstitution, Long> {

    Optional<HealthCareInstitution> findById(Long id);

    @Query("FROM HealthCareInstitution ")
    Iterable<HealthCareInstitution> findAllHealthCareInstitution();

}
