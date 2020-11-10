package showtime.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Utility class for converting values obtained from {@code get()}
 * using {@link org.springframework.util.LinkedMultiValueMap},
 * an implementation of {@code MultiValueMap} used in {@code Controller}.
 */
public class MVPUtil {

    /**
     * Converts the values from {@code LinkedList} of {@code String}
     * to {@code ArrayList} of {@code int}. Ignores malformed values
     * during parsing.
     *
     * @param mapValue values from {@code MultiValueMap}
     * @return {@code ArrayList} of {@code int}
     */
    public static ArrayList<Integer> parseIntNoexcept(List<String> mapValue) {
        ArrayList<Integer> list = new ArrayList<>();
        if(mapValue != null) {
            for(String each : mapValue) {
                try {
                    list.add(Integer.parseInt(each));
                }
                catch(NumberFormatException nfe) { }
            }
        }
        return list;
    }

    /**
     * Converts the values from {@code LinkedList} to {@code ArrayList}.
     *
     * @param mapValue values from {@code MultiValueMap}
     * @return {@code ArrayList} of {@code String}
     */
    public static ArrayList<String> toStringNoexcept(List<String> mapValue) {
        ArrayList<String> list = new ArrayList<>();
        if(mapValue != null) {
            for(String each : mapValue) {
                try {
                    list.add(each);
                }
                catch(NumberFormatException nfe) { }
            }
        }
        return list;
    }

    /**
     * Converts the values from {@code LinkedList} of {@code String}
     * to {@code ArrayList} of {@code sql.Timestamp}. Ignores malformed values
     * during parsing.
     *
     * @param mapValue values from {@code MultiValueMap}
     * @return {@code ArrayList} of {@code sql.Timestamp}
     */
    public static ArrayList<Timestamp> parseTimeStampNoexcept(List<String> mapValue) {
        ArrayList<Timestamp> list = new ArrayList<>();
        if(mapValue != null) {
            for(String each : mapValue) {
                // https://stackoverflow.com/questions/18915075/java-convert-string-to-timestamp
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    Date parsedDate = dateFormat.parse(each);
                    Timestamp timestamp = new Timestamp(parsedDate.getTime());
                    list.add(timestamp);
                } catch(Exception e) { }
            }
        }
        return list;
    }
}
