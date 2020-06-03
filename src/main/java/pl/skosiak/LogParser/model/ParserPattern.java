package pl.skosiak.LogParser.model;

import java.util.regex.Pattern;

public class ParserPattern {
    public static String TIME_FORMAT;
    public static String LOG_LEVEL_FORMAT;
    public static String CLASS_FORMAT;
    public static String LOG_TEXT_FORMAT;

    public static Pattern TIME_PATTERN = null;
    public static Pattern LOG_LEVEL_PATTERN = null;
    public static Pattern CLASS_PATTERN = null;
    public static Pattern LOG_TEXT_PATTERN = null;

}
