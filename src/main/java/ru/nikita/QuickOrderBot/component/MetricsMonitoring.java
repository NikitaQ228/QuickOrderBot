package ru.nikita.QuickOrderBot.component;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Компонент для мониторинга и вывода метрик приложения.
 * Использует Micrometer MeterRegistry для доступа к зарегистрированным метрикам.
 */
@Component
public class MetricsMonitoring {

    /**
     * Регистр счетчиков и других метрик Micrometer.
     */
    private final MeterRegistry meterRegistry;

    /**
     * Конструктор с внедрением MeterRegistry.
     *
     * @param meterRegistry объект MeterRegistry для сбора метрик
     */
    @Autowired
    public MetricsMonitoring(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * Печатает в консоль все зарегистрированные в MeterRegistry метрики.
     * Для каждой метрики выводится ее имя и значения показателей.
     */
    public void printMetrics() {
        meterRegistry.getMeters().forEach(meter -> {
            System.out.println("Meter: " + meter.getId().getName());
            meter.measure().forEach(measurement -> {
                System.out.println("\t" + measurement.getStatistic() + " = " + measurement.getValue());
            });
        });
    }
}
