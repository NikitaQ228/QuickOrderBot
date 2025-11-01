package ru.nikita.QuickOrderBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nikita.QuickOrderBot.dto.UserDTO;
import ru.nikita.QuickOrderBot.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/search")
    public List<UserDTO> searchUser(@RequestParam String firstName, @RequestParam String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

}