package dwfms.framework.collaboration;

import dwfms.framework.log.Event;
import dwfms.framework.log.Log;

public class CandidateLog extends Log {

    private static int timestamp;

    @Override
    public void addEvent(Event event) {
        event.setTimestamp(++timestamp);
        super.addEvent(event);
    }



}
