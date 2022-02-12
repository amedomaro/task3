package ru.Itransition.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Itransition.task3.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

    User findByUsername(String name);
}
