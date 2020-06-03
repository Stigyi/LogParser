package pl.skosiak.LogParser.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.skosiak.LogParser.model.ApplicationLogPattern;
import pl.skosiak.LogParser.model.ParserPattern;
import pl.skosiak.LogParser.model.dto.LogDto;
import pl.skosiak.LogParser.model.exception.LineParseException;
import pl.skosiak.LogParser.model.mapper.LogMapper;
import pl.skosiak.LogParser.model.request.LogListRequest;
import pl.skosiak.LogParser.persistence.entity.Log;
import pl.skosiak.LogParser.persistence.entity.NonParsableLog;
import pl.skosiak.LogParser.persistence.repository.LogRepository;
import pl.skosiak.LogParser.persistence.repository.NonParsableLogRepository;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class LogParserService {

    private ApplicationLogPattern pattern;
    private LogRepository logRepository;
    private NonParsableLogRepository nonParsableLogRepository;

    @Transactional
    public void parseFile(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String currentLine = reader.readLine();
        while (currentLine != null) {
            StringBuilder logEntry = new StringBuilder(currentLine);
            String nextLine = reader.readLine();
            while(!doesLineContainsTimePattern(nextLine)) {
                logEntry.append(nextLine);
                nextLine = reader.readLine();
            }
            currentLine = nextLine;
            processLogEntry(logEntry.toString());
        }
        reader.close();
    }

    private void processLogEntry(String entry){
        try {
            LogDto logDto = parseLogEntry(entry, pattern);
            logRepository.save(LogMapper.mapDtoToEntity(logDto));
        }catch (LineParseException e){
            log.error(""+e);
           NonParsableLog nonParsableLogEntity =  NonParsableLog.builder()
                    .appException(e.getMessage())
                    .rawText(entry)
                    .build();
            nonParsableLogRepository.save(nonParsableLogEntity);
        }
    }

    private LogDto parseLogEntry(String entry, ParserPattern pattern) throws LineParseException {
        try {
            String time = getMatchingString(pattern.TIME_PATTERN, entry);
            String logLevel = getMatchingString(pattern.LOG_LEVEL_PATTERN, entry);
            String callingClass = getMatchingString(pattern.CLASS_PATTERN, entry);
            String text = getMatchingString(pattern.LOG_TEXT_PATTERN, entry);

            if(Stream.of(time, logLevel, callingClass, text).anyMatch(Objects::isNull)){
                throw new LineParseException("Non parsable log");
            }
            return LogDto.builder()
                    .logTime(LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                    .logLevel(logLevel)
                    .callingClass(callingClass)
                    .text(text)
                    .build();
        }catch (Exception e){
            throw new LineParseException(""+e);
        }
    }

    private String getMatchingString(Pattern pattern, String text){
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()){
          return  matcher.group();
        }
        return null;
    }

    private boolean doesLineContainsTimePattern(String line){
        if(Objects.isNull(line)){
            return true;
        }
        return !Objects.isNull(getMatchingString(pattern.TIME_PATTERN, line));
    }

    public Page<LogDto> findLogs(LogListRequest request) {
        PageRequest pageRequest = requestToPage(request);
        Page<Log>  result;
        if(Objects.isNull(request.getDateFrom()) || Objects.isNull(request.getDateTo())){
            result = logRepository.findAll(pageRequest);
        }else {
            result = logRepository.findAllByLogTimeBetween(request.getDateFrom(),
                    request.getDateTo(), pageRequest);
        }

        return new PageImpl<>(result.get().map(LogMapper::mapEntityToDto).collect(Collectors.toList()),
                result.getPageable(),
                result.getTotalElements());
    }


    private PageRequest requestToPage(LogListRequest request){
        if(Strings.isBlank(request.getSortField())){
            return  PageRequest.of(request.getPageNumber(), request.getPageSize());
        }

       Sort.Direction direction = Optional.ofNullable(request.getSortDirection())
               .map(dir ->{
                   if(dir.equals("ASC")){
                       return Sort.Direction.ASC;
                   }
                   return Sort.Direction.DESC;
               }).orElse(Sort.Direction.DESC);

        return PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by(direction,request.getSortField()));
    }
}
