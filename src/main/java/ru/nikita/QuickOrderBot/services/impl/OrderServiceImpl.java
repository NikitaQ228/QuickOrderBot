package ru.nikita.QuickOrderBot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikita.QuickOrderBot.model.Order;
import ru.nikita.QuickOrderBot.model.OrderItem;
import ru.nikita.QuickOrderBot.enums.OrderStatus;
import ru.nikita.QuickOrderBot.repository.impl.OrderRepository;
import ru.nikita.QuickOrderBot.services.OrderService;

import java.util.List;

/**
 * Сервис для управления логикой заказов.
 * Реализует интерфейс OrderService.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Репозиторий для управления заказами.
     */
    private final OrderRepository orderRepository;

    /**
     * Конструктор с внедрением OrderRepository.
     *
     * @param orderRepository репозиторий заказов
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Создает новый заказ, сохраняя его в репозитории.
     *
     * @param order новый заказ
     */
    @Override
    public void createOrder(Order order) {
        orderRepository.create(order);
    }

    /**
     * Находит заказ по идентификатору.
     * Если заказ не найден, выбрасывает RuntimeException.
     *
     * @param orderId идентификатор заказа
     * @return найденный заказ
     * @throws RuntimeException если заказ не найден
     */
    @Override
    public Order findByOrderId(Long orderId) {
        Order order = orderRepository.read(orderId);
        if (order == null) {
            throw new RuntimeException("Заказ " + orderId + " не найден");
        }
        return order;
    }

    /**
     * Обновляет позицию заказа (OrderItem) по id заказа.
     * Если заказ в статусе DELIVERED или CANCELED, запрещает изменение.
     * Если количество позиции равно нулю, позиция удаляется из заказа.
     *
     * @param orderId   идентификатор заказа
     * @param orderItem позиция заказа с обновленными данными
     * @throws IllegalStateException если заказ нельзя менять из-за статуса
     */
    @Override
    public void updateOrderItem(Long orderId, OrderItem orderItem) {
        Order order = findByOrderId(orderId);

        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("Невозможно изменить заказ со статусом " + order.getStatus());
        }

        List<OrderItem> items = order.getItems();
        boolean found = false;

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            if (item.getFoodId().equals(orderItem.getFoodId())) {
                found = true;
                if (orderItem.getQuantity() == 0) {
                    items.remove(i);
                } else {
                    item.setQuantity(orderItem.getQuantity());
                }
                break;
            }
        }

        if (!found && orderItem.getQuantity() > 0) {
            items.add(orderItem);
        }

        order.setItems(items);
        orderRepository.update(order);
    }

    /**
     * Отменяет заказ по id, устанавливая статус CANCELED.
     *
     * @param orderId идентификатор заказа для отмены
     */
    @Override
    public void cancelOrder(Long orderId) {
        Order order = findByOrderId(orderId);
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.update(order);
    }

    /**
     * Возвращает текущий статус заказа.
     *
     * @param orderId идентификатор заказа
     * @return статус заказа
     */
    @Override
    public OrderStatus getOrderStatus(Long orderId) {
        Order order = findByOrderId(orderId);
        return order.getStatus();
    }

    /**
     * Обновляет статус заказа.
     *
     * @param orderId     идентификатор заказа
     * @param orderStatus новый статус заказа
     */
    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = findByOrderId(orderId);
        order.setStatus(orderStatus);
        orderRepository.update(order);
    }
}
