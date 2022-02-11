package ru.Itransition.task3.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity{
}
