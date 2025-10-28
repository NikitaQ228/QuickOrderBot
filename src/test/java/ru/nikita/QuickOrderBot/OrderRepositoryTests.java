package ru.nikita.QuickOrderBot;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.entity.User;
import ru.nikita.QuickOrderBot.repository.OrderRepository;
import ru.nikita.QuickOrderBot.repository.UserRepository;
import ru.nikita.QuickOrderBot.repository.criteria.impl.OrderRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class OrderRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderRepositoryImpl orderRepositoryImpl;

    @Test
    void testFindOrdersByUserEmail_queryMethod() {
        User user = new User();
        user.setEmail("test@example.com");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        orderRepository.save(order);

        List<Order> orders = orderRepository.findOrdersByUserEmail("test@example.com");
        assertEquals(1, orders.size());
        assertEquals(order.getId(), orders.get(0).getId());
    }

    @Test
    void testFindOrdersByUserEmail_criteriaImpl() {
        User user = new User();
        user.setEmail("impl@example.com");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        orderRepository.save(order);

        List<Order> orders = orderRepositoryImpl.findOrdersByUserEmail("impl@example.com");
        assertEquals(1, orders.size());
        assertEquals(order.getId(), orders.get(0).getId());
    }
}
