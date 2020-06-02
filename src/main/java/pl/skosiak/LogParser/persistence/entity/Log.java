package pl.skosiak.LogParser.persistence.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "log", schema = "logparser")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq")
    @SequenceGenerator(name="log_error_generator", sequenceName = "log_seq", allocationSize=1)
    private Long id;
    private LocalDateTime logTime;
    private String logLevel;
    private String callingClass;
    private String text;
}
