package ru.Itransition.task3.service;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegistration {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatPassword;

}
