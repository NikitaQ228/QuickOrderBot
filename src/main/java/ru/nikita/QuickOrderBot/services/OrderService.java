package ru.nikita.QuickOrderBot.services;

import ru.nikita.QuickOrderBot.model.Order;
import ru.nikita.QuickOrderBot.model.OrderItem;
import ru.nikita.QuickOrderBot.enums.OrderStatus;

/**
 * Интерфейс сервиса для управления заказами.
 */
public interface OrderService {

    /**
     * Создает новый заказ.
     *
     * @param order объект заказа для создания
     */
    void createOrder(Order order);

    /**
     * Находит заказ по идентификатору.
     *
     * @param orderId идентификатор заказа
     * @return заказ с данным id либо null, если заказ не найден
     */
    Order findByOrderId(Long orderId);

    /**
     * Обновляет позицию в заказе по id заказа.
     *
     * @param orderId идентификатор заказа
     * @param orderItem объект позиции заказа с обновленными данными
     */
    void updateOrderItem(Long orderId, OrderItem orderItem);

    /**
     * Отменяет заказ по его идентификатору.
     *
     * @param orderId идентификатор заказа для отмены
     */
    void cancelOrder(Long orderId);

    /**
     * Получает текущий статус заказа.
     *
     * @param orderId идентификатор заказа
     * @return текущий статус заказа
     */
    OrderStatus getOrderStatus(Long orderId);

    /**
     * Обновляет статус заказа.
     *
     * @param orderId идентификатор заказа
     * @param orderStatus новый статус заказа
     */
    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
