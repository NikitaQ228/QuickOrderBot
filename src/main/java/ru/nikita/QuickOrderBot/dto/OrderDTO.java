package ru.nikita.QuickOrderBot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> orderItems;
    private String comment;
    private String address;
    private OrderStatus status;
    private PaymentDTO payment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
        this.comment = order.getComment();
        this.address = order.getAddress();
        this.status = order.getStatus();
        this.payment = order.getPayment() != null ? new PaymentDTO(order.getPayment()) : null;
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }
}
