package ru.nikita.QuickOrderBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.nikita.QuickOrderBot.entity.Food;

@RepositoryRestResource
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
