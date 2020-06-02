package pl.skosiak.LogParser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.skosiak.LogParser.model.request.ErrorMessage;
import pl.skosiak.LogParser.model.request.FileUploadRequest;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LogController {


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity uploadFile(@ModelAttribute @Valid FileUploadRequest req) {
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ErrorMessage handleException(BindException e) {
        return  ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(e.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", ")))
                .build();
    }


}