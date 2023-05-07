package edu.pucp.sisdvac.dao;

import edu.pucp.sisdvac.domain.user.Role;
import edu.pucp.sisdvac.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByDocumentNumber(String documentNumber);
    List<User> findByFirstNameEqualsIgnoreCase(String key);
    List<User> findByLastNameEqualsIgnoreCase(String key);
    List<User> findByRole(Role role);
}
