package ru.Itransition.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Itransition.task3.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
