package pl.skosiak.LogParser.model.mapper;

import pl.skosiak.LogParser.model.dto.LogDto;
import pl.skosiak.LogParser.persistence.entity.Log;

import java.util.Optional;

public class LogMapper {

    public static Log mapDtoToEntity(LogDto dto){
        return  Log.builder()
                   .logTime(Optional.ofNullable(dto).map(LogDto::getLogTime).orElse(null))
                   .logLevel(Optional.ofNullable(dto).map(LogDto::getLogLevel).orElse(null))
                   .callingClass(Optional.ofNullable(dto).map(LogDto::getCallingClass).orElse(null))
                   .text(Optional.ofNullable(dto).map(LogDto::getText).orElse(null))
                   .build();
    }

    public static LogDto mapEntityToDto(Log entity){
        return  LogDto.builder()
                      .logTime(Optional.ofNullable(entity).map(Log::getLogTime).orElse(null))
                      .logLevel(Optional.ofNullable(entity).map(Log::getLogLevel).orElse(null))
                      .callingClass(Optional.ofNullable(entity).map(Log::getCallingClass).orElse(null))
                      .text(Optional.ofNullable(entity).map(Log::getText).orElse(null))
                      .build();
    }
}
