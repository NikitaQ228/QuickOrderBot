package ru.nikita.QuickOrderBot.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.entity.OrderItem;
import ru.nikita.QuickOrderBot.repository.OrderItemRepository;
import ru.nikita.QuickOrderBot.repository.OrderRepository;
import ru.nikita.QuickOrderBot.services.TransactionalOrderService;

import java.util.List;

@Service
public class TransactionalOrderServiceImpl implements TransactionalOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public TransactionalOrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public Order createOrderWithItems(Order order, List<OrderItem> items) {
        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : items) {
            item.setOrder(savedOrder);
        }

        orderItemRepository.saveAll(items);
        return savedOrder;
    }
}