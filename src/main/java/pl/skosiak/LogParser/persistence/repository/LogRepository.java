package pl.skosiak.LogParser.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.skosiak.LogParser.persistence.entity.Log;

import java.time.LocalDateTime;

public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findAllByLogTimeBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);
}
