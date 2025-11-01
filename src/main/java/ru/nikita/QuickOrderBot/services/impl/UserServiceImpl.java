package ru.nikita.QuickOrderBot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nikita.QuickOrderBot.dto.UserDTO;
import ru.nikita.QuickOrderBot.dto.UserRegistrationDTO;
import ru.nikita.QuickOrderBot.entity.User;
import ru.nikita.QuickOrderBot.enums.Role;
import ru.nikita.QuickOrderBot.repository.UserRepository;
import ru.nikita.QuickOrderBot.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> searchByName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(UserRegistrationDTO userReg) {
        if (userRepository.findByEmail(userReg.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User user = new User();
        user.setFirstName(userReg.getFirstName());
        user.setLastName(userReg.getLastName());
        user.setRole(Role.USER);
        user.setEmail(userReg.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userReg.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Optional<UserDTO> searchByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::new);
    }
}
