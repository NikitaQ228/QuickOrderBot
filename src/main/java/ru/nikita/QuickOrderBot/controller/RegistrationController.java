package ru.nikita.QuickOrderBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nikita.QuickOrderBot.dto.UserRegistrationDTO;
import ru.nikita.QuickOrderBot.services.impl.UserServiceImpl;

@Controller
public class RegistrationController {

    private final UserServiceImpl userService;

    @Autowired
    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserRegistrationDTO userReg, Model model) {
        try {
            userService.addUser(userReg);
            return "redirect:/login";
        } catch (Exception ex) {
            model.addAttribute("message", "User exists");
            return "registration";
        }
    }
}
