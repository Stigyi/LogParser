package pl.skosiak.LogParser.model;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ApplicationLogPattern extends ParserPattern{

    static {
        TIME_FORMAT = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}";
        LOG_LEVEL_FORMAT = "ALL|DEBUG|INFO|WARN|ERROR|FATAL|OFF|TRACE";
        CLASS_FORMAT = "([a-z0-9]*\\.){1,}([A-Z]\\w*)+(\\.[A-Z]\\w*)*";
        LOG_TEXT_FORMAT =  ": .*$";

        TIME_PATTERN = Pattern.compile(TIME_FORMAT);
        LOG_LEVEL_PATTERN = Pattern.compile(LOG_LEVEL_FORMAT);
        CLASS_PATTERN = Pattern.compile(CLASS_FORMAT);
        LOG_TEXT_PATTERN = Pattern.compile(LOG_TEXT_FORMAT);
    }
}
