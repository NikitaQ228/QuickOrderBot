package ru.nikita.QuickOrderBot;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nikita.QuickOrderBot.entity.User;
import ru.nikita.QuickOrderBot.repository.UserRepository;
import ru.nikita.QuickOrderBot.repository.criteria.impl.UserRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    void testFindByFirstNameAndLastName_queryMethod() {
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Smith");
        userRepository.save(user);

        List<User> users = userRepository.findByFirstNameAndLastName("Alice", "Smith");
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.getFirst().getId());
    }

    @Test
    void testFindByFirstNameAndLastName_criteriaImpl() {
        User user = new User();
        user.setFirstName("Bob");
        user.setLastName("Johnson");
        userRepository.save(user);

        List<User> users = userRepositoryImpl.findByFirstNameAndLastName("Bob", "Johnson");
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.getFirst().getId());
    }
}
