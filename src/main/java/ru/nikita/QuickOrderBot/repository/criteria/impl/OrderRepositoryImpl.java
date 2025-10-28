package ru.nikita.QuickOrderBot.repository.criteria.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.repository.criteria.OrderRepositoryCustom;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findOrdersByUserEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Object, Object> user = orderRoot.join("user");

        criteriaQuery.select(orderRoot).where(criteriaBuilder.equal(user.get("email"), email));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
