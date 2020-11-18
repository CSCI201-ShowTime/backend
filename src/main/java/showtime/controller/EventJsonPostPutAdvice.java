package showtime.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import showtime.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Examines JSON before it reaches Jackson or {@link EventController}. Validates
 * {@link Event#type} to help Jackson auto deserialize polymorphic {@code Event}s.
 */
@ControllerAdvice(assignableTypes = {EventController.class})
public class EventJsonPostPutAdvice extends RequestBodyAdviceAdapter {

    Logger logger = LoggerFactory.getLogger(EventJsonPostPutAdvice.class);

    private static final Pattern pattern = Pattern.compile("\"type\"\\s*:\\s*.*[,}]");
    private static final Pattern prefix = Pattern.compile("\"type\"\\s*:\\s*");

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
        logger.trace("Before Body Read for URI: " + req.getRequestURI());
        return new MappingJacksonInputMessage(
                new ByteArrayInputStream(validateType(inputMessage.getBody()).getBytes()),
                inputMessage.getHeaders()
        );
    }

    /**
     * Validates {@link Event#type}. In particular, {@code type} must match the URI address
     * the request is sent to. In case {@code type} is absent, automatically appends a type
     * according to the URI address.
     */
    private String validateType(InputStream input) throws IOException {

        String urlType = req.getRequestURI().replaceFirst("/api/event/", "");

        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String json = br.lines().collect(Collectors.joining("\n"));
        Matcher matcher = pattern.matcher(json);
        if(matcher.find()) {
            logger.debug("Type is found in JSON");
            // matches literally "type": xyz ,
            String jsonType = matcher.group();
            // removes "type":
            String stripJsonType = prefix.matcher(jsonType).replaceFirst("");
            // removes , or } and spaces
            stripJsonType = stripJsonType.substring(0, stripJsonType.length()-1).trim();
            // removes " "
            stripJsonType = stripJsonType.substring(1, stripJsonType.length()-1);

            if(!stripJsonType.equals(urlType)) {
                logger.debug("Type mismatch in JSON, expected=" + urlType + ", found=" + stripJsonType);
                // wtf? deprecated?
                throw new JsonMappingException(
                        "JSON type mismatch with requested URI, expected=" + urlType + ", found=" + stripJsonType);
            }
        }
        else {
            logger.debug("Type is NOT found in JSON");
            json = json.substring(0, json.length()-1).concat(",\"type\":" + urlType + "}");
        }

        return json;
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> handleJsonValidationException(JsonMappingException jme) {
        return new ResponseEntity<>(jme.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
