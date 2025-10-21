package ru.nikita.QuickOrderBot.services.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikita.QuickOrderBot.config.AppConfig;
import ru.nikita.QuickOrderBot.services.AppService;

/**
 * Реализация сервиса приложения.
 * Отвечает за вывод названия и версии приложения.
 */
@Service
public class AppServiceImpl implements AppService {

    /**
     * Конфигурация приложения, содержащая имя и версию.
     */
    private final AppConfig appConfig;

    @Autowired
    public AppServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * Инициализация после создания бина.
     * Вызывает метод печати названия и версии приложения.
     */
    @PostConstruct
    public void init() {
        printAppNameAndVersion();
    }

    /**
     * Печатает в консоль название и версию приложения, взятые из конфигурации.
     */
    @Override
    public void printAppNameAndVersion() {
        System.out.println("Название приложения: " + appConfig.getAppName());
        System.out.println("Версия приложения: " + appConfig.getAppVersion());
    }
}
