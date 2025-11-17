package ru.nikita.QuickOrderBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.QuickOrderBot.services.impl.ReportServiceImpl;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportServiceImpl reportService;

    @Autowired
    public ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Long> createAndGenerateReport() {
        Long reportId = reportService.createReport();
        reportService.generateReport(reportId);
        return ResponseEntity.ok(reportId);
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<?> getReportContent(@PathVariable("id") Long reportId) {
        String result = reportService.getContentReport(reportId);
        if ("Отчет не найден".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if ("Отчет еще не сформирован".equals(result) || result.startsWith("Формирование отчета завершилось с ошибкой")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8")
                    .body(result);
        }
    }

}
