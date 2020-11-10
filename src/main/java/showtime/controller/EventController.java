package showtime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import showtime.model.Event;
import showtime.repository.EventRepository;
import showtime.service.EventSpecification;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventRepository eventRepo;

    /**
     * Responds to "/api/event.GET" requests. Retrieves all events matching criteria in the database.
     *
     * @param params criteria to match
     * @return {@code 200 OK} if any (including 0) events are found
     */
    @GetMapping("/event/rawevent")
    public ResponseEntity<List<Event>> getRawEventByAllSpecs(@RequestParam MultiValueMap<String, String> params) {

        logger.debug(params.toString());
        List<Event> eventO = eventRepo.findAll(new EventSpecification(params));
        return new ResponseEntity<>(eventO, HttpStatus.OK);
    }
}
