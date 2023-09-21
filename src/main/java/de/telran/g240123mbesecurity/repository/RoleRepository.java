package de.telran.g240123mbesecurity.repository;

import de.telran.g240123mbesecurity.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
