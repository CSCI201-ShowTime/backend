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
import org.springframework.web.bind.annotation.PutMapping;
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
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/*
As of current, Jackson is unable to automatically deserialize JSON into
polymorphic entities. The workarounds are:

1. Assign a different Controller for each polymorphic entity type
+ validation and implementation is the easiest
- duplicated code and violation of OOP

2. Read raw JSON strings into the Controller and deserialize based on type
+ information is directly available in the Controller,
  validation and logic can be implemented easily
- Spring's existing functionalities are ignored

3. Customize Jackson's JSONDeserializer and annotations
+ Spring's existing functionalities are utilized
- Many separated DTOs may be required. e.g. If using @JsonTypeInfo,
  a "type" information will be required for all JSON,
  but polymorphism is required for POST not PUT

4. Intercept the request before it reaches the Controller
+ Spring's existing functionalities are utilized
- Complicated code. JSON validation logic is spread among classes.
 */
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

    // Because of constraint on JpaSpecificationExecutor, currently used as raw type
    @SuppressWarnings("rawtypes")
    private final Map<String, EventBaseRepository> repos;
    @SuppressWarnings("rawtypes")
    private final Map<String, Supplier<EventSpecBuilder>> specs;

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
        specs.put("/api/event/rawevent", EventSpecBuilder::createBuilder);
        specs.put("/api/event/durationevent", DurationEventSpecBuilder::createBuilder);
        specs.put("/api/event/reminder", ReminderSpecBuilder::createBuilder);
        specs.put("/api/event/diary", DiarySpecBuilder::createBuilder);
        specs.put("/api/event/budget", BudgetSpecBuilder::createBuilder);
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
                specs.get(requestURI).get().fromMultiValueMap(params).build()
        );

        if (eventList.size() > 10) {
            eventList = eventList.subList(0, 10);
        }
        
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

    @GetMapping({"/topEvent"})
    public ResponseEntity<?> getTop10EventByCriteria(
            HttpServletRequest request,
            @RequestParam MultiValueMap<String, String> params) {
        // userid = 101, 
        // userid != 101, visibility 
        // "/api/event/rawevent"
        String requestURI = request.getRequestURI();
        
        // Reminder Test
        if (requestURI.equals("/api/event/reminder")) {
            //List<Reminder> reminderList = reminderRepo.readTop3ByOrderByPriorityDesc();
            //return new ResponseEntity<>(reminderList, HttpStatus.OK);
        }
        
        
        List<String> repoURIs = Arrays.asList("/api/event/durationevent", "/api/event/diary", "/api/event/budget");
        @SuppressWarnings("unchecked")
        List<Event> eventList = repos.get("/api/event/reminder").findAll(
                specs.get("/api/event/reminder").get().fromMultiValueMap(params).build()
                ,Sort.by(Sort.Direction.ASC, "start")
        );
        for (String repoURI : repoURIs) {
            @SuppressWarnings("unchecked")
            List<Event> childEventList = repos.get(repoURI).findAll(
                   specs.get(repoURI).get().fromMultiValueMap(params).build()
                   ,Sort.by(Sort.Direction.ASC, "start")
            );
            eventList.addAll(childEventList);
        }
        

        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
    
    @PostMapping({"/durationevent", "/reminder", "/diary", "/budget"})
    public ResponseEntity<Event> createEventTest(
            HttpServletRequest request,
            @RequestBody Event event) {

        String requestURI = request.getRequestURI();

        logger.debug("Request to " + requestURI + "/POST with RequestBody=" + event);
        // because of auto increment, no duplicates will exist
        Event saved = (Event) repos.get(requestURI).save(event);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

/*    @PostMapping("/durationevent")
    public ResponseEntity<DurationEvent> createDurationEvent(@RequestBody DurationEvent dEvent) {

        DurationEvent saved = (DurationEvent) repos.get("/api/event/durationevent").save(dEvent);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/reminder")
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {

        Reminder saved = (Reminder) repos.get("/api/event/reminder").save(reminder);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/diary")
    public ResponseEntity<Diary> createDiary(@RequestBody Diary diary) {

        Diary saved = (Diary) repos.get("/api/event/diary").save(diary);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/budget")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {

        Budget saved = (Budget) repos.get("/api/event/budget").save(budget);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }*/

    @PutMapping("/durationevent")
    public ResponseEntity<DurationEvent> updateDurationEvent(@RequestBody DurationEvent dEvent) {

        Optional<DurationEvent> found = repos.get("/api/event/durationevent").findById(dEvent.getEventid());
        if(found.isPresent()) {
            DurationEvent original = found.get();
            original.setStart(dEvent.getStart());
            original.setEnd(dEvent.getEnd());
            original.setTitle(dEvent.getTitle());
            original.setDescription(dEvent.getDescription());
            original.setVisibility(dEvent.getVisibility());
            original.setLocation(dEvent.getLocation());
            original.setRemindTime(dEvent.getRemindTime());

            DurationEvent saved = (DurationEvent) repos.get("/api/event/durationevent").save(original);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/reminder")
    public ResponseEntity<Reminder> updateReminder(@RequestBody Reminder reminder) {

        Optional<Reminder> found = repos.get("/api/event/reminder").findById(reminder.getEventid());
        if(found.isPresent()) {
            Reminder original = found.get();
            original.setStart(reminder.getStart());
            original.setEnd(reminder.getEnd());
            original.setTitle(reminder.getTitle());
            original.setDescription(reminder.getDescription());
            original.setVisibility(reminder.getVisibility());
            original.setLocation(reminder.getLocation());
            original.setRemindTime(reminder.getRemindTime());
            original.setPriority(reminder.getPriority());

            Reminder saved = (Reminder) repos.get("/api/event/reminder").save(original);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/diary")
    public ResponseEntity<Diary> updateDiary(@RequestBody Diary diary) {

        Optional<Diary> found = repos.get("/api/event/diary").findById(diary.getEventid());
        if(found.isPresent()) {
            Diary original = found.get();
            original.setStart(diary.getStart());
            original.setEnd(diary.getEnd());
            original.setTitle(diary.getTitle());
            original.setDescription(diary.getDescription());
            original.setVisibility(diary.getVisibility());
            original.setLocation(diary.getLocation());

            Diary saved = (Diary) repos.get("/api/event/diary").save(original);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/budget")
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget) {

        Optional<Budget> found = repos.get("/api/event/budget").findById(budget.getEventid());
        if(found.isPresent()) {
            Budget original = found.get();
            original.setStart(budget.getStart());
            original.setEnd(budget.getEnd());
            original.setTitle(budget.getTitle());
            original.setDescription(budget.getDescription());
            original.setVisibility(budget.getVisibility());
            original.setLocation(budget.getLocation());
            original.setAmount(budget.getAmount());
            original.setCategory(budget.getCategory());
            original.setEbudTransactionUserid(budget.getEbudTransactionUserid());

            Budget saved = (Budget) repos.get("/api/event/budget").save(original);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
