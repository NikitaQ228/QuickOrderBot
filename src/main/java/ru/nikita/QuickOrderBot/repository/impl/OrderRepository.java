package ru.nikita.QuickOrderBot.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikita.QuickOrderBot.model.Order;
import ru.nikita.QuickOrderBot.model.OrderStatus;
import ru.nikita.QuickOrderBot.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для управления заказами.
 * Реализует операции CRUD над сущностями Order.
 */
@Component
public class OrderRepository implements CrudRepository<Order, Long> {
    private final List<Order> orderContainer;

    @Autowired
    public OrderRepository(List<Order> orderContainer) {
        this.orderContainer = orderContainer;
    }

    /**
     * Создаёт новый заказ.
     * Если идентификатор заказа не задан, генерирует новый уникальный ID.
     * Устанавливает статус заказа CREATED и фиксирует время создания и обновления.
     * Добавляет заказ в контейнер.
     *
     * @param order создаваемый заказ
     */
    @Override
    public void create(Order order) {
        if (order.getId() == null) {
            long newId = orderContainer.stream()
                    .mapToLong(Order::getId)
                    .max().orElse(0L) + 1;
            order.setId(newId);
        }
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderContainer.add(order);
    }

    /**
     * Читает заказ по идентификатору.
     *
     * @param id идентификатор заказа
     * @return найденный заказ либо null, если заказ с таким id отсутствует
     */
    @Override
    public Order read(Long id) {
        return orderContainer.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Обновляет существующий заказ.
     * Если заказ с указанным идентификатором не найден, выбрасывает RuntimeException.
     * Обновляет время последнего изменения.
     *
     * @param order заказ с обновлёнными данными
     * @throws RuntimeException если заказ для обновления не найден
     */
    @Override
    public void update(Order order) {
        for (int i = 0; i < orderContainer.size(); ++i) {
            if (orderContainer.get(i).getId().equals(order.getId())) {
                order.setUpdatedAt(LocalDateTime.now());
                orderContainer.set(i, order);
                return;
            }
        }
        throw new RuntimeException("Заказ " + order.getId() + " не найден.");
    }

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id идентификатор заказа для удаления
     */
    @Override
    public void delete(Long id) {
        orderContainer.removeIf(order -> order.getId().equals(id));
    }
}
