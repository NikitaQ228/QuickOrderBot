package ru.nikita.QuickOrderBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nikita.QuickOrderBot.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий заказ пользователя.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * Уникальный идентификатор заказа.
     */
    private Long id;

    /**
     * Идентификатор пользователя, создавшего заказ.
     */
    private Long userId;

    /**
     * Список позиций в заказе.
     */
    private List<OrderItem> items;

    /**
     * Адрес доставки заказа.
     */
    private String address;

    /**
     * Статус заказа.
     */
    private OrderStatus status;

    /**
     * Время создания заказа.
     */
    private LocalDateTime createdAt;

    /**
     * Время последнего обновления заказа.
     */
    private LocalDateTime updatedAt;

    /**
     * Конструктор для создания нового заказа с одним пунктом.
     * Инициализирует список позиций и добавляет одну позицию заказа.
     *
     * @param userId  идентификатор пользователя
     * @param foodId  идентификатор блюда
     * @param quantity количество блюда
     * @param address адрес доставки
     */
    public Order(Long userId, Long foodId, int quantity, String address) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.items.add(new OrderItem(foodId, quantity));
        this.address = address;
    }

    /**
     * Возвращает строковое представление заказа, включая все позиции.
     *
     * @return строковое описание заказа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id)
                .append(", User ID: ").append(userId)
                .append(", Address: ").append(address)
                .append(", Status: ").append(status)
                .append(", Created: ").append(createdAt)
                .append(", Updated: ").append(updatedAt)
                .append("\nItems:\n");
        for (OrderItem item : items) {
            sb.append("  - Food ID: ").append(item.getFoodId())
                    .append(", Quantity: ").append(item.getQuantity())
                    .append("\n");
        }
        return sb.toString();
    }
}
