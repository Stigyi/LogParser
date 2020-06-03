package pl.skosiak.LogParser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@XmlRootElement
public class LogDto {
    private LocalDateTime logTime;
    private String logLevel;
    private String callingClass;
    private String text;
}
