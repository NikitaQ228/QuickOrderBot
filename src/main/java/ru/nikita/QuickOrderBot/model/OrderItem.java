package ru.nikita.QuickOrderBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий позицию в заказе.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    /**
     * Идентификатор блюда.
     */
    private Long foodId;

    /**
     * Количество данного блюда в заказе.
     */
    private int quantity;
}
