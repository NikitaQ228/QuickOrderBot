package ru.nikita.QuickOrderBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikita.QuickOrderBot.dto.OrderDTO;
import ru.nikita.QuickOrderBot.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/search/{email}")
    public List<OrderDTO> searchByEmailUser(@PathVariable String email) {
        return orderRepository.findOrdersByUserEmail(email)
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }
}
