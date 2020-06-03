package pl.skosiak.LogParser.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skosiak.LogParser.persistence.entity.NonParsableLog;

public interface NonParsableLogRepository extends JpaRepository<NonParsableLog, Long> {
}
