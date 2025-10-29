package ru.nikita.QuickOrderBot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.entity.User;
import ru.nikita.QuickOrderBot.enums.Role;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private String phone;
    private List<Long> orderIds;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        if (user.getOrders() != null) {
            this.orderIds = user.getOrders().stream()
                    .map(Order::getId)
                    .collect(Collectors.toList());
        }
    }
}
