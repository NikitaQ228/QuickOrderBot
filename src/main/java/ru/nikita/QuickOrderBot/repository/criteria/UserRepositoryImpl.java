package ru.nikita.QuickOrderBot.repository.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.nikita.QuickOrderBot.entity.User;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        criteriaQuery.select(userRoot).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(userRoot.get("firstName"), firstName),
                        criteriaBuilder.equal(userRoot.get("lastName"), lastName)
                )
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
