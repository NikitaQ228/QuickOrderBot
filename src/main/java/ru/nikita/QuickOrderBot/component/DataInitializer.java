package ru.nikita.QuickOrderBot.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nikita.QuickOrderBot.entity.User;
import ru.nikita.QuickOrderBot.enums.Role;
import ru.nikita.QuickOrderBot.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setFirstName("Nikita");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setRole(Role.ADMIN);
            admin.setPasswordHash(passwordEncoder.encode("admin"));
            userRepository.save(admin);
        }
    }
}
