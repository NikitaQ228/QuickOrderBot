package ru.nikita.QuickOrderBot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nikita.QuickOrderBot.entity.Payment;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private double amount;
    private LocalDateTime paymentDate;

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.orderId = payment.getOrder().getId();
        this.amount = payment.getAmount();
        this.paymentDate = payment.getPaymentDate();
    }
}
