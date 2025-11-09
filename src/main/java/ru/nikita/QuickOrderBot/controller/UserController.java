package ru.nikita.QuickOrderBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public List<UserDTO> searchByName(@RequestParam String firstName, @RequestParam String lastName) {
        return userService.searchByName(firstName, lastName);
    }

    @GetMapping("/search/email")
    public Optional<UserDTO> searchByEmail(@RequestParam String email) {
        return userService.searchByEmail(email);
    }

}