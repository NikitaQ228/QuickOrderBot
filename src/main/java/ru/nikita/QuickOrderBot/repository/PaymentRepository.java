package ru.nikita.QuickOrderBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikita.QuickOrderBot.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
