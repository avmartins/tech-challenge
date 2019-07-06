package  pixeon.tech.challenge.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import  pixeon.tech.challenge.entity.RegistroGeral;
import  pixeon.tech.challenge.entity.User;

@Repository
public interface RegistroGeralRepository extends JpaRepository<RegistroGeral, Long> {

    RegistroGeral getFirstByUserOrderByDataDesc(User user);

    Iterable<RegistroGeral> findByUserOrderByData(User user);

    @Query("FROM RegistroGeral f WHERE f.data = :data")
    Iterable<RegistroGeral> findAllByData(LocalDate data);

    @Query("FROM RegistroGeral f WHERE f.userRole = 'User' AND f.user.isActive = true")
    Iterable<RegistroGeral> findAllByDataAndUserRoleIsUser();
    
    @Query("FROM RegistroGeral f WHERE f.userRole = 'Administrador' AND f.user.isActive = true")
    Iterable<RegistroGeral> findAllByDataAndUserRoleIsAdministrador();
    
    @Query("FROM RegistroGeral f WHERE f.user.isActive = true")
    List<RegistroGeral> findAll();

    @Query("FROM RegistroGeral f WHERE f.data = :data AND f.user = :user AND f.user.isActive = true")
    RegistroGeral findByDataAndUser(LocalDate data, User user);

}
