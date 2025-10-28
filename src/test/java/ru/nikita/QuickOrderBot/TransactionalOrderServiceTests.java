package ru.nikita.QuickOrderBot;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.entity.OrderItem;
import ru.nikita.QuickOrderBot.enums.OrderStatus;
import ru.nikita.QuickOrderBot.repository.OrderItemRepository;
import ru.nikita.QuickOrderBot.repository.OrderRepository;
import ru.nikita.QuickOrderBot.services.impl.TransactionalOrderServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class TransactionalOrderServiceTests {
    @Autowired
    private TransactionalOrderServiceImpl transactionalOrderService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testCreateOrderWithItems_success() {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();
        List<OrderItem> items = Arrays.asList(item1, item2);

        Order savedOrder = transactionalOrderService.createOrderWithItems(order, items);

        assertNotNull(savedOrder.getId());
        List<OrderItem> itemsInDB = orderItemRepository.findByOrderId(savedOrder.getId());
        assertEquals(2, itemsInDB.size());
    }

    @Test
    void testCreateOrderWithItems_rollbackOnException() {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        OrderItem item1 = new OrderItem();
        List<OrderItem> items = Arrays.asList(item1, null);

        assertThrows(NullPointerException.class, () -> {
            transactionalOrderService.createOrderWithItems(order, items);
        });

        List<Order> orders = orderRepository.findAll();
        assertTrue(orders.isEmpty());

        List<OrderItem> itemsInDB = orderItemRepository.findAll();
        assertTrue(itemsInDB.isEmpty());
    }
}
