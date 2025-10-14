package ru.nikita.QuickOrderBot.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.nikita.QuickOrderBot.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурационный класс для создания bean контейнера заказов.
 * Контейнер представляет собой список заказов, доступный как синглтон в Spring контексте.
 */
@Configuration
public class OrderConfig {

    /**
     * Создаёт singleton bean - контейнер для хранения заказов.
     *
     * @return пустой ArrayList заказов
     */
    @Bean
    public List<Order> orderContainer() {
        return new ArrayList<>();
    }
}
