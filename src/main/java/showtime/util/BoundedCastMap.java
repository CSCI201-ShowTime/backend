package showtime.util;

import java.util.HashMap;
import java.util.Map;

public class BoundedCastMap<T> {

    private final Map<Class<? extends T>, T> container = new HashMap<>();

    public <U extends T> void put(Class<U> type, U instance) {
        if(type == null) {
            throw new NullPointerException();
        }
        container.put(type, instance);
    }

    public <U extends T> U get(Class<U> type) {
        return type.cast(container.get(type));
    }
}