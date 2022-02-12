package ru.Itransition.task3.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.Itransition.task3.model.Role;
import ru.Itransition.task3.model.Status;
import ru.Itransition.task3.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory(){
    }

    public static JwtUser create(User user){
        return new JwtUser(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getPassword(), user.getEmail(), user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(), mapToGrantedAuthority(new ArrayList<>(user.getRoles())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> userRoles){
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
