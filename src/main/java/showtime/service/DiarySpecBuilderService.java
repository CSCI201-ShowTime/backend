package showtime.service;

import org.springframework.util.MultiValueMap;
import showtime.model.Diary;

// diary has no additional fields;
// however, user may call a concrete class instead of EventSpecBuilderService<Diary>
public class DiarySpecBuilderService extends EventSpecBuilderService<Diary> {

/*    protected DiarySpecBuilderService() { }

    public static DiarySpecBuilderService newDiaryBuilder() {
        return new DiarySpecBuilderService();
    }*/

    @Override
    public DiarySpecBuilderService fromMultiValueMap(MultiValueMap<String, String> params) {
        super.fromMultiValueMap(params);
        return this;
    }
}
