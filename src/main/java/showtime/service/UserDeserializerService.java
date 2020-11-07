package showtime.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import showtime.model.User;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class UserDeserializerService extends JsonDeserializer<User> {

    private static Pattern pattern = Pattern.compile("/^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$/");

    @Override
    public User deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);

        User user = new User();
        // email, required, not null
        String email = node.get("email").asText();
        user.setEmail(email);
        // username, not null, default to email if field not included or empty
        JsonNode username = node.get("username");
        String usernameStr = username == null || username.asText().equals("") ? email : username.asText();
        user.setUsername(usernameStr);
        // password, required, not null
        user.setPassword(node.get("password").asText());
        // fname, not null
        user.setFname(node.get("firstname").asText());
        // lname, not null
        user.setLname(node.get("lastname").asText());
        // latitude, not null, default to 91
        JsonNode lat = node.get("latitude");
        double latDouble = lat == null ? 91 : lat.asDouble();
        user.setLatitude(latDouble);
        // longitude, not null, default to 181
        JsonNode lon = node.get("longitude");
        double lonDouble = lon == null ? 181 : lon.asDouble();
        user.setLatitude(lonDouble);
        return user;
    }
}
