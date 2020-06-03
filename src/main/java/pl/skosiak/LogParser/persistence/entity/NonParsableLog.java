package pl.skosiak.LogParser.persistence.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "non_parsable_log", schema = "logparser")
public class NonParsableLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "non_parsable_log_seq")
    private Long id;
    private String appException;
    private String rawText;
}
