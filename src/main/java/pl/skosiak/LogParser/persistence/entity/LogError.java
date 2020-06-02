package pl.skosiak.LogParser.persistence.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "log_error", schema = "logparser")
public class LogError {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_error_seq")
    @SequenceGenerator(name="log_error_generator", sequenceName = "log_error_seq", allocationSize=1)
    private Long id;
    private String rawText;
}
