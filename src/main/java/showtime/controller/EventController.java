package showtime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import showtime.model.DurationEvent;
import showtime.model.Event;
import showtime.model.Reminder;
import showtime.repository.BudgetRepository;
import showtime.repository.DiaryRepository;
import showtime.repository.DurationEventRepository;
import showtime.repository.EventRepository;
import showtime.repository.ReminderRepository;
import showtime.service.EventSpecification;

import java.sql.SQLSyntaxErrorException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventRepository eventRepo;

    @GetMapping("/rawevent")
    public ResponseEntity<List<Event>> getRawEventByCriteria(
            @RequestParam MultiValueMap<String, String> params) {

        logger.debug(params.toString());
        List<Event> eventO = eventRepo.findAll(new EventSpecification(params));
        return new ResponseEntity<>(eventO, HttpStatus.OK);
    }

    @PostMapping("/rawevent")
    public ResponseEntity<Event> createNewEvent(@RequestBody Event event) {
        Event newEvent = eventRepo.save(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }
}
