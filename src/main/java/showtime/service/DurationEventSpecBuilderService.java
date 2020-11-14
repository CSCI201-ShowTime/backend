package showtime.service;

import org.springframework.util.MultiValueMap;
import showtime.model.DurationEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DurationEventSpecBuilderService extends EventSpecBuilderService<DurationEvent> {

/*    protected DurationEventSpecBuilderService() { }

    public static DurationEventSpecBuilderService newDurationEventBuilder() {
        return new DurationEventSpecBuilderService();
    }*/

    public DurationEventSpecBuilderService byRemindTime(List<LocalDateTime> remindTime) {
        spec = spec.and(new SpecBetweenTwoEqualOneFrom<>("remindTime", remindTime));
        return this;
    }

    @Override
    public DurationEventSpecBuilderService fromMultiValueMap(MultiValueMap<String, String> params) {
        super.fromMultiValueMap(params);
        return this;
    }
}
