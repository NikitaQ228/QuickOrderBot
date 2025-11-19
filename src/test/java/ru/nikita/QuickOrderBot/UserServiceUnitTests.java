package ru.nikita.QuickOrderBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nikita.QuickOrderBot.dto.UserDTO;
import ru.nikita.QuickOrderBot.dto.UserRegistrationDTO;
import ru.nikita.QuickOrderBot.entity.User;
import ru.nikita.QuickOrderBot.enums.Role;
import ru.nikita.QuickOrderBot.repository.UserRepository;
import ru.nikita.QuickOrderBot.services.impl.UserServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRegistrationDTO registrationDTO;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setRole(Role.USER);
        user.setPasswordHash("hashed_pass");

        registrationDTO = new UserRegistrationDTO();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("john.doe@example.com");
        registrationDTO.setPassword("plain_pass");
    }

    // === searchByName ===

    @Test
    void searchByName_returnsUserDTOList_whenFound() {
        when(userRepository.findByFirstNameAndLastName("John", "Doe"))
                .thenReturn(Collections.singletonList(user));

        List<UserDTO> result = userService.searchByName("John", "Doe");

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(userRepository).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void searchByName_returnsEmptyList_whenNotFound() {
        when(userRepository.findByFirstNameAndLastName("Jane", "Smith"))
                .thenReturn(Collections.emptyList());

        List<UserDTO> result = userService.searchByName("Jane", "Smith");

        assertTrue(result.isEmpty());
        verify(userRepository).findByFirstNameAndLastName("Jane", "Smith");
    }

    // === addUser ===

    @Test
    void addUser_success_whenEmailIsUnique() {
        when(userRepository.findByEmail(registrationDTO.getEmail()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("plain_pass"))
                .thenReturn("hashed_pass");

        userService.addUser(registrationDTO);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals(Role.USER, savedUser.getRole());
        assertEquals("john.doe@example.com", savedUser.getEmail());
        assertEquals("hashed_pass", savedUser.getPasswordHash());
    }

    @Test
    void addUser_throwsException_whenEmailExists() {
        when(userRepository.findByEmail(registrationDTO.getEmail()))
                .thenReturn(Optional.of(user));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.addUser(registrationDTO)
        );
        assertEquals("User with this email already exists", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    // === searchByEmail ===

    @Test
    void searchByEmail_returnsUserDTO_whenFound() {
        when(userRepository.findByEmail("john.doe@example.com"))
                .thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.searchByEmail("john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        verify(userRepository).findByEmail("john.doe@example.com");
    }

    @Test
    void searchByEmail_returnsEmpty_whenNotFound() {
        when(userRepository.findByEmail("not.found@example.com"))
                .thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.searchByEmail("not.found@example.com");

        assertFalse(result.isPresent());
        verify(userRepository).findByEmail("not.found@example.com");
    }
}
