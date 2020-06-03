package pl.skosiak.LogParser.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skosiak.LogParser.persistence.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
}
