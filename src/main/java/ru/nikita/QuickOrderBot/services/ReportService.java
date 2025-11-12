package ru.nikita.QuickOrderBot.services;

import java.util.concurrent.CompletableFuture;

public interface ReportService {
    Long createReport();

    String getContentReport(Long reportId);

    CompletableFuture<Void> generateReport(Long reportId);
}