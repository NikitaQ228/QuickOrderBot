package ru.nikita.QuickOrderBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikita.QuickOrderBot.dto.UserDTO;
import ru.nikita.QuickOrderBot.services.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/search/name")
    public List<UserDTO> searchUser(@RequestParam String firstName, @RequestParam String lastName) {
        return userService.searchByName(firstName, lastName);
    }

    @GetMapping("/search/email")
    public Optional<UserDTO> searchUser(@RequestParam String email) {
        return userService.searchByEmail(email);
    }

}