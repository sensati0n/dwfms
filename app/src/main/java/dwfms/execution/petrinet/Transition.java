package dwfms.execution.petrinet;

import dwfms.model.bpmn.Activity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

public class Transition {

    Collection<Arc> incoming = new ArrayList<>();
    Collection<Arc> outgoing = new ArrayList<>();

    //Extended Transition
    @Getter
    private String activity;

    protected Transition() {

    }

    Transition(String activity) {
        this.activity = activity;
    }

    public void fire() {
        for (Arc arc : incoming) {
            arc.fire();
        }

        for (Arc arc : outgoing) {
            arc.fire();
        }
    };

    public boolean canFire() {
        boolean canFire = true;

        for (Arc arc : incoming) {
            canFire = canFire & arc.canFire();
        }

        return canFire;
    }

    public void addIncoming(Arc arc) {
        this.incoming.add(arc);
    }

    public void addOutgoing(Arc arc) {
        this.outgoing.add(arc);
    }

}
