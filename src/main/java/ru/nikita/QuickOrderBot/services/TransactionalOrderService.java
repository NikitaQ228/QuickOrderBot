package ru.nikita.QuickOrderBot.services;

import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.entity.OrderItem;

import java.util.List;

public interface TransactionalOrderService {
    Order createOrderWithItems(Order order, List<OrderItem> items);
}
