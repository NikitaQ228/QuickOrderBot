package ru.nikita.QuickOrderBot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nikita.QuickOrderBot.entity.OrderItem;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private Long foodId;

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.quantity = orderItem.getQuantity();
        this.foodId = orderItem.getFood().getId();
    }
}
