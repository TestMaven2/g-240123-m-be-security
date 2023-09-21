package de.telran.g240123mbesecurity.repository;

import de.telran.g240123mbesecurity.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}