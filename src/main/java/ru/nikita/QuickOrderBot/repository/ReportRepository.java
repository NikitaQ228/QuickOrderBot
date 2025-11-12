package ru.nikita.QuickOrderBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikita.QuickOrderBot.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
