package showtime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import showtime.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.chrono.JapaneseChronology;
import java.util.stream.Collectors;

/**
 * Examines JSON before it is sent to {@code EventController}. Validates {@code Event}
 * type to help Jackson auto deserialize polymorphic {@code Event}.
 */
@ControllerAdvice(assignableTypes = {EventController.class})
public class EventJsonPostPutAdvice extends RequestBodyAdviceAdapter {

    Logger logger = LoggerFactory.getLogger(EventJsonPostPutAdvice.class);

    @Autowired
    private HttpServletRequest req;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        boolean isSupported = targetType.getTypeName().equals(Event.class.getTypeName());
        logger.trace("supports=" + isSupported);
        return isSupported;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
                                           MethodParameter parameter,
                                           Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType)
            throws IOException {
        logger.debug("Before Body Read for URI: " + req.getRequestURI());

        BufferedReader br = new BufferedReader(new InputStreamReader(inputMessage.getBody()));
        StringBuilder sb = new StringBuilder();
        boolean containsType = false;
        while(br.ready()) {
            String str = br.readLine();
            str = str.trim();
            // brute convert, TODO: add checks
            if(!containsType && str.contains("\"type\"")) {
                containsType = true;
                String urlType = req.getRequestURI().replaceFirst("/api/event/", "");
                String newType = "\"type\": \"" + urlType + "\"";
                str = str.charAt(str.length()-1) == ',' ? newType + "," : newType;
            }
            sb.append(str);
        }
        if(!containsType) {
            String urlType = req.getRequestURI().replaceFirst("/api/event/", "");
            sb.deleteCharAt(sb.length()-1);
            sb.append("," + "\"type\": \"" + urlType + "\"}");
        }

        System.out.println(sb.toString());

        return new MappingJacksonInputMessage(
                new ByteArrayInputStream(sb.toString().getBytes()),
                inputMessage.getHeaders()
        );
    }
}
