package  pixeon.tech.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import  pixeon.tech.challenge.entity.Exam;

/**
 * @author Anderson Virginio Martins
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findById(Long id);    

}
