package ru.nikita.QuickOrderBot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.QuickOrderBot.entity.Food;
import ru.nikita.QuickOrderBot.entity.Report;
import ru.nikita.QuickOrderBot.enums.ReportStatus;
import ru.nikita.QuickOrderBot.repository.FoodRepository;
import ru.nikita.QuickOrderBot.repository.ReportRepository;
import ru.nikita.QuickOrderBot.repository.UserRepository;
import ru.nikita.QuickOrderBot.services.ReportService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, FoodRepository foodRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report = reportRepository.save(report);
        return report.getId();
    }

    @Override
    public String getContentReport(Long reportId) {
        Optional<Report> reportOpt = reportRepository.findById(reportId);
        if (reportOpt.isEmpty()) {
            return "Отчет не найден";
        }
        Report report = reportOpt.get();
        if (report.getStatus() == ReportStatus.CREATED) {
            return "Отчет еще не сформирован";
        }
        if (report.getStatus() == ReportStatus.ERROR) {
            return "Формирование отчета завершилось с ошибкой:\n" + report.getContent();
        }
        return report.getContent();
    }

    @Override
    @Async
    public CompletableFuture<Void> generateReport(Long reportId) {
        return CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            AtomicLong userCount = new AtomicLong();
            AtomicReference<List<Food>> foodList = new AtomicReference<>();
            AtomicReference<Exception> exception = new AtomicReference<>();
            long[] timing = new long[2];

            Thread userCountThread = new Thread(() -> {
                long tStart = System.currentTimeMillis();
                try {
                    userCount.set(getUsersCounts());
                } catch (Exception e) {
                    exception.set(e);
                }
                timing[0] = System.currentTimeMillis() - tStart;
            });

            Thread foodListThread = new Thread(() -> {
                long tStart = System.currentTimeMillis();
                try {
                    foodList.set(getFoodList());
                } catch (Exception e) {
                    exception.set(e);
                }
                timing[1] = System.currentTimeMillis() - tStart;
            });

            userCountThread.start();
            foodListThread.start();

            try {
                userCountThread.join();
                foodListThread.join();
            } catch (InterruptedException e) {
                exception.set(e);
                Thread.currentThread().interrupt();
            }

            long totalTime = (System.currentTimeMillis() - startTime);
            ReportStatus reportStatus;
            String htmlReport;

            if (exception.get() != null) {
                reportStatus = ReportStatus.ERROR;
                htmlReport = "<html><body><h2>Ошибка при формировании отчёта</h2>"
                        + "<pre>" + exception.get().getMessage() + "</pre></body></html>";
            } else {
                reportStatus = ReportStatus.COMPLETED;
                StringBuilder html = new StringBuilder();
                html.append("<html><body>");
                html.append("<h2>Статистика приложения</h2>");
                html.append("<table border='1' cellpadding='5' style='border-collapse:collapse;'>");
                html.append("<tr><th>Показатель</th><th>Значение</th><th>Время (мс)</th></tr>");
                html.append("<tr><td>Количество пользователей</td><td>")
                        .append(userCount.get())
                        .append("</td><td>")
                        .append(timing[0])
                        .append("</td></tr>");
                html.append("<tr><td>Список объектов (еда)</td><td><ul>");
                for (Food food : foodList.get()) {
                    html.append("<li>").append(food.toString()).append("</li>");
                }
                html.append("</ul></td><td>")
                        .append(timing[1])
                        .append("</td></tr>");
                html.append("<tr><td colspan='2'><b>Общее время формирования отчёта</b></td><td><b>")
                        .append(totalTime)
                        .append("</b></td></tr>");
                html.append("</table>");
                html.append("</body></html>");
                htmlReport = html.toString();
            }

            updateReport(reportId, reportStatus, htmlReport);
        });
    }

    private long getUsersCounts() {
        return userRepository.count();
    }

    private List<Food> getFoodList() {
        return foodRepository.findAll();
    }

    @Transactional
    private void updateReport(Long reportId, ReportStatus status, String content) {
        reportRepository.findById(reportId).ifPresent(report -> {
            report.setStatus(status);
            report.setContent(content);
            reportRepository.save(report);
        });
    }
}
