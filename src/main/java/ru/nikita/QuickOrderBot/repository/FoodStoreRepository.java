package ru.nikita.QuickOrderBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikita.QuickOrderBot.entity.FoodStore;

@Repository
public interface FoodStoreRepository extends JpaRepository<FoodStore, Long> {
}
