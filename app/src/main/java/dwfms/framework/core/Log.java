package dwfms.framework.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class Log {

    @Getter
    List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        this.events.add(event);

    }

}


