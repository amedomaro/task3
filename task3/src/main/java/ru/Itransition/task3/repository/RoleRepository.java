package ru.Itransition.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Itransition.task3.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
