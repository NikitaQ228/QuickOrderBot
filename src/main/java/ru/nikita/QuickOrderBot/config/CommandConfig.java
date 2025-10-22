package ru.nikita.QuickOrderBot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nikita.QuickOrderBot.component.CommandProcessor;

import java.util.Scanner;

/**
 * Конфигурационный класс, создающий bean CommandLineRunner.
 * CommandLineRunner запускает цикл чтения команд из консоли и передает их в CommandProcessor.
 */
//@Configuration
//public class CommandConfig {
//
//    /**
//     * Компонент для обработки введённых команд.
//     */
//    @Autowired
//    private CommandProcessor commandProcessor;
//
//    /**
//     * Bean, реализующий CommandLineRunner, запускает чтение команд после старта приложения.
//     *
//     * @return CommandLineRunner, который читает команды из консоли в бесконечном цикле,
//     * пока не будет введена команда "exit".
//     */
//    @Bean
//    public CommandLineRunner commandScanner() {
//        return args -> {
//            try (Scanner scanner = new Scanner(System.in)) {
//                System.out.println("Введите команду. 'exit' для выхода.");
//                while (true) {
//                    System.out.print("> ");
//                    String input = scanner.nextLine();
//                    if ("exit".equalsIgnoreCase(input.trim())) {
//                        System.out.println("Выход из программы...");
//                        break;
//                    }
//                    commandProcessor.processCommand(input);
//                }
//            }
//        };
//    }
//}
