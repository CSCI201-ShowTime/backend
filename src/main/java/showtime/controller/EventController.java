package showtime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import showtime.model.Budget;
import showtime.model.Diary;
import showtime.model.DurationEvent;
import showtime.model.Event;
import showtime.model.Reminder;
import showtime.repository.BudgetRepository;
import showtime.repository.DiaryRepository;
import showtime.repository.DurationEventRepository;
import showtime.repository.EventBaseRepository;
import showtime.repository.EventRepository;
import showtime.repository.ReminderRepository;
import showtime.service.BudgetSpecBuilderService;
import showtime.service.DiarySpecBuilderService;
import showtime.service.DurationEventSpecBuilderService;
import showtime.service.EventSpecBuilderService;
import showtime.service.ReminderSpecBuilderService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private DurationEventRepository durationEventRepo;
    @Autowired
    private ReminderRepository reminderRepo;
    @Autowired
    private DiaryRepository diaryRepo;
    @Autowired
    private BudgetRepository budgetRepo;

    private URIRepoSpecTHC thc = new URIRepoSpecTHC();

    // TODO: dynamic builder
/*    @Autowired
    public EventController(EventRepository er,
                           DurationEventRepository der,
                           ReminderRepository rr,
                           BudgetRepository br) {
        thc.add("/api/event/rawevent",
                Event.class, er, EventSpecBuilderService.newEventBuilder());
        thc.add("/api/event/durationevent",
                DurationEvent.class, der, DurationEventSpecBuilderService.newDurationEventBuilder());
        thc.add("/api/event/reminder",
                Reminder.class, rr, ReminderSpecBuilderService.newReminderBuilder());
        thc.add("/api/event/budget",
                Budget.class, br, BudgetSpecBuilderService.newBudgetBuilder());
    }*/

    @GetMapping({"/rawevent", "/durationevent", "/reminder", "/diary", "/budget"})
    public ResponseEntity<?> getEventByCriteria(
            HttpServletRequest request,
            @RequestParam MultiValueMap<String, String> params) {

        // "/api/event/rawevent"
        String requestURI = request.getRequestURI();

        if(requestURI.equals("/api/event/rawevent")) {
            Specification<Event> specRawEvent = new EventSpecBuilderService<>()
                    .fromMultiValueMap(params)
                    .build();
            List<? extends Event> eventList = eventRepo.findAll(specRawEvent);
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        }
        else if(requestURI.equals("/api/event/durationevent")) {
            Specification<DurationEvent> specDurationEvent = new DurationEventSpecBuilderService()
                    .fromMultiValueMap(params)
                    .build();
            List<DurationEvent> eventList = durationEventRepo.findAll(specDurationEvent);
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        }
        else if(requestURI.equals("/api/event/reminder")) {
            Specification<Reminder> specReminder = new ReminderSpecBuilderService()
                    .fromMultiValueMap(params)
                    .build();
            List<Reminder> eventList = reminderRepo.findAll(specReminder);
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        }
        else if(requestURI.equals("/api/event/diary")) {
            Specification<Diary> specDiary = new DiarySpecBuilderService()
                    .fromMultiValueMap(params)
                    .build();
            List<Diary> eventList = diaryRepo.findAll(specDiary);
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        }
        else if(requestURI.equals("/api/event/budget")) {
            Specification<Budget> specBudget = new BudgetSpecBuilderService()
                    .fromMultiValueMap(params)
                    .build();
            List<Budget> eventList = budgetRepo.findAll(specBudget);
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/rawevent")
    public ResponseEntity<Event> createNewEvent(@RequestBody Event event) {
        Event newEvent = eventRepo.save(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    private static class URIRepoSpecTHC {

        private final Map<String, Class<? extends Event>> keyContainer = new HashMap<>();
        private final Map<Class<? extends Event>, Object> repoContainer = new HashMap<>();
        private final Map<Class<? extends Event>, Object> specContainer = new HashMap<>();

        public <T extends Event> void add(String uri,
                                          Class<T> type,
                                          EventBaseRepository<T> repo,
                                          EventSpecBuilderService<T> svc) {
            keyContainer.put(uri, type);
            repoContainer.put(type, repo);
            specContainer.put(type, svc);
        }

        @SuppressWarnings("unchecked")
        public <T extends Event> EventBaseRepository<T> getRepo(String uri) {
            Class<? extends Event> key = keyContainer.get(uri);
            return (EventBaseRepository<T>) repoContainer.get(key);
        }

        @SuppressWarnings("unchecked")
        public <T extends Event> EventSpecBuilderService<T> getSpec(String uri) {
            Class<? extends Event> key = keyContainer.get(uri);
            return (EventSpecBuilderService<T>) specContainer.get(key);
        }
    }
}
