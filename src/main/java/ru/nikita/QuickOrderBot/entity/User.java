package ru.nikita.QuickOrderBot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nikita.QuickOrderBot.enums.Role;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String passwordHash;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
