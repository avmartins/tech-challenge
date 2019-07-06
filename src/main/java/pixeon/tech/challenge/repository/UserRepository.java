package  pixeon.tech.challenge.repository;

import  pixeon.tech.challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Anderson Virginio Martins
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findById(Long id);

    User findByCpf(String cpf);

    @Query("FROM User u WHERE u.isActive = true")
    Iterable<User> findByActive();

    @Query("SELECT COUNT(u.id) FROM User u WHERE u.isActive = true AND u.userRole = 'Investidor'")
    Integer countAllInvestors();

}
