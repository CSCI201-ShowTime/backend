package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultJackson {

    @Autowired
    ObjectMapper objectMapper;
}
