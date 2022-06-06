package dwfms.framework.collaboration;

import dwfms.framework.collaboration.network.Acknowledgement;
import dwfms.framework.log.Event;
import dwfms.framework.log.Log;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class CandidateLog extends Log {

    private static int timestamp;

    @Getter
    Map<String, Integer> numberOfAcknowledgements;

    public CandidateLog() {
        super();
        this.numberOfAcknowledgements = new HashMap<>();
    }

    @Override
    public void addEvent(Event event) {
        event.setTimestamp(++timestamp);
        super.addEvent(event);
    }

    public void addAcknowledgementToEvent(String task) {
        if(numberOfAcknowledgements.containsKey(task)) {
            numberOfAcknowledgements.put(task, numberOfAcknowledgements.get(task)+1);
        }
        else {
            this.numberOfAcknowledgements.put(task, 1);
        }



    }


}
