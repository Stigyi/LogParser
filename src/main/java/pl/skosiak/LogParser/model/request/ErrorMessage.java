package pl.skosiak.LogParser.model.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMessage {
    private Integer status;
    private String error;
    private String message;
}
