package pl.skosiak.LogParser.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import pl.skosiak.LogParser.model.dto.LogDto;
import pl.skosiak.LogParser.model.request.CustomValidator;
import pl.skosiak.LogParser.model.request.ErrorMessage;
import pl.skosiak.LogParser.model.request.FileUploadRequest;
import pl.skosiak.LogParser.model.request.LogListRequest;
import pl.skosiak.LogParser.service.LogParserService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class LogController {

    private LogParserService logParserService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity uploadFile(@ModelAttribute @Valid FileUploadRequest req) throws IOException {
        logParserService.parseFile(req.getFile());
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/logs", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    private Page<LogDto> getLogs(@RequestParam(value = "dateFrom", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                 @RequestParam(value = "dateTo", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
                                 @RequestParam(value = "pageNumber") Integer pageNumber,
                                 @RequestParam(value = "pageSize") Integer pageSize,
                                 @RequestParam(value = "sortField", required = false) String sortField,
                                 @RequestParam(value = "sortDirection", required = false) String sortDirection){
        LogListRequest request = LogListRequest.builder()
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .sortField(sortField)
                .sortDirection(sortDirection)
                .build();
        CustomValidator.validate(request);
       return logParserService.findLogs(request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ErrorMessage handleBindException(BindException e) {
        return  ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(e.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", ")))
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorMessage handleValidationException(ConstraintViolationException e) {
        return  ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(e.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", ")))
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorMessage handleMissingParams(MissingServletRequestParameterException e) {
        return  ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(String.format("Parameter %s is missing", e.getParameterName()))
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorMessage handleException(Exception e) {
        log.error(""+e);
        return  ErrorMessage.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message("Something bad happened :(")
                .build();
    }
}
