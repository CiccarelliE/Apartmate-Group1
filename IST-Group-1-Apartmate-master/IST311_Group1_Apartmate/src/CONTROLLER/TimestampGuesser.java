package CONTROLLER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Cole Daubenspeck Nov 9, 2018
 */
public class TimestampGuesser {

    public static final String UNIFORM_DATE = "yyyy-MM-dd";
    public static final String UNIFORM_TIME = "HH:mm:ss";
    public static final String UNIFORM_TIMESTAMP = UNIFORM_DATE + " " + UNIFORM_TIME;
    

    public static final String[] DATE_FORMATS = {
        //good date formats
        "yyyy-MM-dd",
        "yyyy-MM-d",
        "yyyy-M-d",
        "yyyy-M-dd",
        "yyyyMMdd",
        //mediocre date formats
        "MM/dd/yyyy",
        "MM/dd/yy",
        "MM/d/yyyy",
        "MM/d/yy",
        "M/dd/yyyy",
        "M/dd/yy",
        "M/d/yyyy",
        "M/d/yy",
        //bad date formats
        //these will not be used since Month/Day/Year is declared first.
        //If you want to use these formats remove the previous section
        "dd/MM/yyyy",
        "dd/MM/yy",
        "dd/M/yyyy",
        "dd/M/yy",
        "d/MM/yyyy",
        "d/MM/yy",
        "d/M/yyyy",
        "d/M/yy"
    };

    public static final String[] TIME_FORMATS = {
        //24 hour formats
        "HH:mm",
        "HH:mm:ss",
        "HH:mm:ss.SSS",
        "HH:mm:ss.SSSSSS",
        //12 hour formats
        "hh:mma",
        "hh:mm a",
        "h:mma",
        "h:mm a",
        "hh:mm:ssa",
        "hh:mm:ss a",
        "h:mm",
        //lazy time formats
        "hha",
        "ha",
        "hmma",
        "hmm a",
        "HHmm",
        "Hmm",
        "hmm"
    };

    public static final String[] SEPERATORS = {
        //standard way humans seperate date from time
        " ",
        //actual ISO standard way of seperating date from time
        "'T'"
    };

    public static LocalDateTime guessTimestamp(String input) throws Exception {
        //replace lowercase am and pm with uppercase because Java doesn't support lowercase am and pm in timestamps
        input = input.replace("am", "AM").replace("pm", "PM");

        //for every possible date format
        for (String date_format : DATE_FORMATS) {
            //and for every possible time format
            for (String time_format : TIME_FORMATS) {
                //and for every possible way to seperate the two
                for (String seperator : SEPERATORS) {
                    //build a timestamp format
                    String format = date_format + seperator + time_format;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                    //and try to parse the input with that timestamp
                    try {
                        LocalDateTime timestamp = LocalDateTime.parse(input, formatter);
                        return timestamp;
                    } catch (DateTimeParseException ex) {
                    } //moves on to next format if it doesn't work
                }
            }
        }
        throw new Exception(input + " is not parsable into a commonly known timestamp"); //if no timestamps match, then its not a valid timestamp, obviously
    }

    public static LocalDate guessDate(String input) throws Exception {
        for (String date_format : DATE_FORMATS) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(date_format);
            try {
                LocalDate parsedDate = LocalDate.parse(input, formatter);
                return parsedDate;
            } catch (DateTimeParseException ex) {
            } //moves on to next format if it doesn't work
        }
        throw new Exception(input + " is not parsable into a commonly known date");
    }

    public static String formatTimestamp(LocalDateTime ldt) {
        return ldt.format(DateTimeFormatter.ofPattern(UNIFORM_TIMESTAMP));
    }
    
    public static String formatDate(LocalDate ld) {
        return ld.format(DateTimeFormatter.ofPattern(UNIFORM_DATE));
    }
    
    public static String formatTime(LocalDateTime ld) {
        return ld.format(DateTimeFormatter.ofPattern(UNIFORM_TIME));
    }

    public static void printExample(String format) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)));
    }
}
