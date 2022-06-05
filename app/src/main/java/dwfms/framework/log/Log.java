package dwfms.framework.log;

import dwfms.framework.log.Event;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Log {

    List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        this.events.add(event);

    }

}


