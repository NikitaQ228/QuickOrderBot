package ru.nikita.QuickOrderBot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации приложения.
 * Содержит свойства, загружаемые из файла настроек (application.properties).
 */
@Getter
@Configuration
public class AppConfig {

    /**
     * Название приложения.
     * Загружается из свойства app.name.
     */
    @Value("${app.name}")
    private String appName;

    /**
     * Версия приложения.
     * Загружается из свойства app.version.
     */
    @Value("${app.version}")
    private String appVersion;
}
