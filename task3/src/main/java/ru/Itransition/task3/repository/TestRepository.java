package ru.Itransition.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Itransition.task3.model.User;

import java.util.Optional;

public interface TestRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

