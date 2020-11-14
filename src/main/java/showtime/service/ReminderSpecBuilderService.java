package showtime.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import showtime.model.Reminder;

import java.time.LocalDateTime;

public class ReminderSpecBuilderService extends EventSpecBuilderService<Reminder> {

    Logger logger = LoggerFactory.getLogger(ReminderSpecBuilderService.class);

/*    protected ReminderSpecBuilderService() { }

    public static ReminderSpecBuilderService newReminderBuilder() {
        return new ReminderSpecBuilderService();
    }*/

    /**
     * Constrains remind time. If one value is given, finds exact matches;
     * if two values are given, finds matches between range.
     */
    public ReminderSpecBuilderService byRemindTime(LocalDateTime... remindTime) {
        if(remindTime.length >= 2) {
            spec = spec.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.between(root.get("remind_time"), remindTime[0], remindTime[1])
            );
        }
        else if(remindTime.length >= 1) {
            spec = spec.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("remind_time"), remindTime[0])
            );
        }
        return this;
    }

    /**
     * Constrains priority.
     */
    public ReminderSpecBuilderService byPriority(int... priority) {
        spec = priority.length > 0
                ? spec.and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("priority"), priority[0]))
                : spec;
        return this;
    }

    @Override
    public ReminderSpecBuilderService fromMultiValueMap(MultiValueMap<String, String> params) {
        super.fromMultiValueMap(params);
        byRemindTime( MVPUtil.parseDateTimeToArray(params.get("remindtime")) );
        byPriority( MVPUtil.parseIntToArray(params.get("priority")) );
        return this;
    }
}
