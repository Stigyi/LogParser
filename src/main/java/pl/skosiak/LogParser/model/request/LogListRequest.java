package pl.skosiak.LogParser.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Builder
public class LogListRequest {

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    @NotNull(message = "pageNumber cannot be null")
    private Integer pageNumber;
    @NotNull(message = "pageSize cannot be null")
    private Integer pageSize;
    @Pattern(regexp = "logTime|logLevel|callingClass|text", message = "invalid value in sortField")
    private String sortField;
    @Pattern(regexp = "ASC|DESC")
    private String sortDirection;
}
