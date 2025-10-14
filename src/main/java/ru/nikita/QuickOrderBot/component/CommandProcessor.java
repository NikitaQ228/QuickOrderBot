package ru.nikita.QuickOrderBot.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikita.QuickOrderBot.model.Order;
import ru.nikita.QuickOrderBot.model.OrderItem;
import ru.nikita.QuickOrderBot.model.OrderStatus;
import ru.nikita.QuickOrderBot.services.OrderService;

/**
 * Компонент для обработки вводимых пользователем текстовых команд.
 * Делегирует команды соответствующим сервисам.
 */
@Component
public class CommandProcessor {

    /**
     * Сервис для работы с заказами.
     */
    private final OrderService orderService;

    /**
     * Мониторинг метрик приложения.
     */
    private final MetricsMonitoring metricsMonitoring;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param orderService      сервис для заказов
     * @param metricsMonitoring сервис мониторинга метрик
     */
    @Autowired
    public CommandProcessor(OrderService orderService, MetricsMonitoring metricsMonitoring) {
        this.orderService = orderService;
        this.metricsMonitoring = metricsMonitoring;
    }

    /**
     * Обрабатывает команду, переданную в виде строки.
     * Распознает команды и аргументы, вызывает соответствующие методы,
     * выводит результаты или сообщения об ошибках.
     *
     * @param input ввод пользователя с командой и аргументами
     */
    public void processCommand(String input) {
        String[] cmd = input.split(" ");

        if (cmd.length == 0 || cmd[0].isEmpty()) {
            System.out.println("Введите команду или help для списка доступных команд");
            return;
        }

        try {
            switch (cmd[0]) {
                case "help" -> {
                    System.out.println("Доступные команды:");
                    System.out.println("metricsApp");
                    System.out.println("createOrder userId foodId quantity address");
                    System.out.println("findByOrderId orderId");
                    System.out.println("updateOrderItem orderId foodId quantity");
                    System.out.println("cancelOrder orderId");
                    System.out.println("getOrderStatus orderId");
                    System.out.println("updateOrderStatus orderId newStatus");
                }
                case "metricsApp" -> {
                    metricsMonitoring.printMetrics();
                }
                case "createOrder" -> {
                    if (cmd.length < 5) throw new IllegalArgumentException("Недостаточно аргументов для createOrder");
                    Order order = new Order(
                            Long.parseLong(cmd[1]),
                            Long.parseLong(cmd[2]),
                            Integer.parseInt(cmd[3]),
                            cmd[4]
                    );
                    orderService.createOrder(order);
                    System.out.println("Заказ успешно добавлен...");
                }
                case "findByOrderId" -> {
                    if (cmd.length < 2) throw new IllegalArgumentException("Недостаточно аргументов для findByOrderId");
                    Order order = orderService.findByOrderId(Long.parseLong(cmd[1]));
                    if (order != null) {
                        System.out.println(order);
                    } else {
                        System.out.println("Заказ с ID " + cmd[1] + " не найден");
                    }
                }
                case "updateOrderItem" -> {
                    if (cmd.length < 4)
                        throw new IllegalArgumentException("Недостаточно аргументов для updateOrderItem");
                    OrderItem orderItem = new OrderItem(
                            Long.parseLong(cmd[2]),
                            Integer.parseInt(cmd[3])
                    );
                    orderService.updateOrderItem(
                            Long.parseLong(cmd[1]),
                            orderItem
                    );
                    System.out.println("Заказ успешно изменен...");
                }
                case "cancelOrder" -> {
                    if (cmd.length < 2) throw new IllegalArgumentException("Недостаточно аргументов для cancelOrder");
                    orderService.cancelOrder(Long.parseLong(cmd[1]));
                    System.out.println("Заказ успешно отменен...");
                }
                case "getOrderStatus" -> {
                    if (cmd.length < 2)
                        throw new IllegalArgumentException("Недостаточно аргументов для getOrderStatus");
                    OrderStatus orderStatus = orderService.getOrderStatus(Long.parseLong(cmd[1]));
                    System.out.println("Статус заказа: " + orderStatus);
                }
                case "updateOrderStatus" -> {
                    if (cmd.length < 3)
                        throw new IllegalArgumentException("Недостаточно аргументов для updateOrderStatus");
                    OrderStatus newStatus;
                    try {
                        newStatus = OrderStatus.valueOf(cmd[2].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Неверный статус заказа: " + cmd[2]);
                        System.out.print("Допустимые статусы: ");
                        for (OrderStatus status : OrderStatus.values()) {
                            System.out.print(status + " ");
                        }
                        System.out.println();
                        break;
                    }
                    orderService.updateOrderStatus(Long.parseLong(cmd[1]), newStatus);
                    System.out.println("Статус заказа успешно обновлён на " + newStatus);
                }
                default -> System.out.println("Введена неизвестная команда, введите help для списка команд");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат числа в аргументах - " + e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
