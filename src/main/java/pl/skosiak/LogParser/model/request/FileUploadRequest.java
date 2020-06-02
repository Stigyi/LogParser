package pl.skosiak.LogParser.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class FileUploadRequest {
    @NotNull(message = "file cannot be null")
    private MultipartFile file;
}
