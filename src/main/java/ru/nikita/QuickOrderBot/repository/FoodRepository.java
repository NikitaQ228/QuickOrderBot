package ru.nikita.QuickOrderBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikita.QuickOrderBot.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
