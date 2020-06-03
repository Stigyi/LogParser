package pl.skosiak.LogParser.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class LogListRequest {

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    @NotNull(message = "pageNumber cannot be null")
    private Integer pageNumber;
    @NotNull(message = "pageSize cannot be null")
    private Integer pageSize;
    private String sortField;
    private String sortDirection;
}
