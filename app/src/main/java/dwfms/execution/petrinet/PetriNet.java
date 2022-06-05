package dwfms.execution.petrinet;

import dwfms.framework.IExecutionMachine;
import dwfms.model.bpmn.Activity;
import jnr.a64asm.Ext;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class PetriNet {

    Collection<Place> places = new ArrayList<>();
    @Getter
    Collection<Transition> transitions = new ArrayList<>();
    Collection<Arc> arcs = new ArrayList<>();

    public Collection<Transition> getActiveTransitions() {
        return transitions.stream().filter(Transition::canFire).collect(Collectors.toList());
    };

    public Place place(int tokens) {
        Place p = new Place(tokens);
        places.add(p);
        return p;
    }

    public Transition transition(String activity) {
        Transition t = new Transition(activity);
        transitions.add(t);
        return t;
    }

    public Arc arc(Place p, Transition t) {
        Arc a = new Arc(p, t);
        arcs.add(a);
        return a;
    }

    public Arc arc(Transition t, Place p) {
        Arc a = new Arc(t, p);
        arcs.add(a);
        return a;
    }


}
