package ru.nikita.QuickOrderBot.repository.criteria;

import ru.nikita.QuickOrderBot.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findOrdersByUserEmail(String email);
}
