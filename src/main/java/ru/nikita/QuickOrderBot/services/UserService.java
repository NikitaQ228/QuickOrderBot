package ru.nikita.QuickOrderBot.services;

import ru.nikita.QuickOrderBot.dto.UserDTO;
import ru.nikita.QuickOrderBot.dto.UserRegistrationDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> searchByName(String firstName, String lastName);
    Optional<UserDTO> searchByEmail(String email);
    void addUser(UserRegistrationDTO userReg);
}
