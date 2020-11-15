package showtime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import showtime.service.BudgetSpecBuilder;
import showtime.service.DiarySpecBuilder;
import showtime.service.DurationEventSpecBuilder;
import showtime.service.EventSpecBuilder;
import showtime.service.ReminderSpecBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

/*    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private DurationEventRepository durationEventRepo;
    @Autowired
    private ReminderRepository reminderRepo;
    @Autowired
    private DiaryRepository diaryRepo;
    @Autowired
    private BudgetRepository budgetRepo;*/

    // Because of constraint on JpaSpecificationExecutor, currently used as raw type
    @SuppressWarnings("rawtypes")
    private final Map<String, EventBaseRepository> repos;
    @SuppressWarnings("rawtypes")
    private final Map<String, EventSpecBuilder> specs;

    @Autowired
    public EventController(EventRepository er,
                           DurationEventRepository der,
                           ReminderRepository rr,
                           DiaryRepository dr,
                           BudgetRepository br) {
        repos = new HashMap<>();
        repos.put("/api/event/rawevent", er);
        repos.put("/api/event/durationevent", der);
        repos.put("/api/event/reminder", rr);
        repos.put("/api/event/diary", dr);
        repos.put("/api/event/budget", br);

        specs = new HashMap<>();
        specs.put("/api/event/rawevent", EventSpecBuilder.createBuilder());
        specs.put("/api/event/durationevent", DurationEventSpecBuilder.createBuilder());
        specs.put("/api/event/reminder", ReminderSpecBuilder.createBuilder());
        specs.put("/api/event/diary", DiarySpecBuilder.createBuilder());
        specs.put("/api/event/budget", BudgetSpecBuilder.createBuilder());
    }

    // TODO: Security checks

    @GetMapping({"/rawevent", "/durationevent", "/reminder", "/diary", "/budget"})
    public ResponseEntity<?> getEventByCriteria(
            HttpServletRequest request,
            @RequestParam MultiValueMap<String, String> params) {

        // "/api/event/rawevent"
        String requestURI = request.getRequestURI();

        @SuppressWarnings("unchecked")
        List<Event> eventList = repos.get(requestURI).findAll(
                specs.get(requestURI).fromMultiValueMap(params).build()
        );

        return new ResponseEntity<>(eventList, HttpStatus.OK);

/*        if(requestURI.equals("/api/event/rawevent")) {
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
        }*/
    }

    @PostMapping({"/durationevent", "/reminder", "/diary", "/budget"})
    public ResponseEntity<Event> createNewEvent(
            HttpServletRequest request,
            @RequestBody Event event) {

        // "/api/event/rawevent"
        String requestURI = request.getRequestURI();

        Event savedEvent = (Event) repos.get(requestURI).save(event);

/*        Event savedEvent = null;
        if(requestURI.equals("/api/event/durationevent")) {
            DurationEvent durationEvent = (DurationEvent) event;
            savedEvent = durationEventRepo.save(durationEvent);
        }
        else if(requestURI.equals("/api/event/reminder")) {
            Reminder reminder = (Reminder) event;
            savedEvent = reminderRepo.save(reminder);
        }
        else if(requestURI.equals("/api/event/diary")) {
            Diary diary = (Diary) event;
            savedEvent = diaryRepo.save(diary);
        }
        else if(requestURI.equals("/api/event/budget")) {
            Budget budget = (Budget) event;
            savedEvent = budgetRepo.save(budget);
        }*/

        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }
}
