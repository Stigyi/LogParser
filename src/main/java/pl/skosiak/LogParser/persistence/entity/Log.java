package pl.skosiak.LogParser.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "log", schema = "logparser")
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq")
    private Long id;
    private LocalDateTime logTime;
    private String logLevel;
    private String callingClass;
    private String text;
}
