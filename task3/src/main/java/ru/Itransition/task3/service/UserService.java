package ru.Itransition.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Itransition.task3.model.Status;
import ru.Itransition.task3.model.User;
import ru.Itransition.task3.repository.RoleRepository;
import ru.Itransition.task3.repository.UserRepository;
import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegistration userRegistration){
        User user = new User();
        user.setUsername(userRegistration.getUsername());
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }
}
