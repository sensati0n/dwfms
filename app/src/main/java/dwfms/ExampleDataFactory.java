package dwfms;

import dwfms.execution.petrinet.PetriNet;
import dwfms.execution.petrinet.Place;
import dwfms.execution.petrinet.Transition;
import dwfms.model.bpmn.*;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.RuleBuilder;

import java.util.Set;

public class ExampleDataFactory {

    public static PetriNet exampleNet () {

        PetriNet pn = new PetriNet();

        Place p1 = pn.place(1);
        Place p2 = pn.place(0);
        Place p3 = pn.place(0);
        Place p4 = pn.place(0);
        Place p5 = pn.place(0);
        Place p6 = pn.place(0);

        Transition t1 = pn.transition("Start");
        Transition t2 = pn.transition("A");
        Transition t3 = pn.transition("B");
        Transition t4 = pn.transition("C");
        Transition t5 = pn.transition("D");
        Transition t6 = pn.transition("End");

        pn.arc(p1, t1);
        pn.arc(t1, p2);
        pn.arc(p2, t2);
        pn.arc(t2, p3);

        pn.arc(p3, t3);
        pn.arc(t3, p4);

        pn.arc(p3, t4);
        pn.arc(t4, p4);
        pn.arc(p4, t5);
        pn.arc(t5, p5);
        pn.arc(p5, t6);
        pn.arc(t6, p6);

        return pn;

    }

    public static PetriNet exampleNetWithSilentTransitions() {

        PetriNet pn = new PetriNet();

        Place p1 = pn.place(1);
        Place p2 = pn.place(0);
        Place p3 = pn.place(0);
        Place p4 = pn.place(0);
        Place p5 = pn.place(0);
        Place p6 = pn.place(0);
        Place p7 = pn.place(0);
        Place p8 = pn.place(0);

        Transition t1 = pn.transition("Start");
        Transition t2 = pn.transition("A");
        Transition t3 = pn.transition("B");
        Transition t4 = pn.transition("silent B");
        Transition t5 = pn.transition("C");
        Transition t6 = pn.transition("silent C");
        Transition t7 = pn.transition("D");
        Transition t8 = pn.transition("End");

        pn.arc(p1, t1);
        pn.arc(t1, p2);
        pn.arc(p2, t2);
        pn.arc(t2, p3);
        pn.arc(p3, t3);
        pn.arc(t3, p4);
        pn.arc(p4, t4);
        pn.arc(t4, p6);

        pn.arc(p3, t5);
        pn.arc(t5, p5);
        pn.arc(p5, t6);
        pn.arc(t6, p6);
        pn.arc(t6, p7);

        pn.arc(p6, t7);
        pn.arc(t7, p7);
        pn.arc(p7, t8);
        pn.arc(t8, p8);

        return pn;

    }

    public static Rules exampleRules() {

        // define rules
        Rule directAllocationOfA = new RuleBuilder()
                .name("WRP1-R-DA: Direct Allocation")
                .description("Task A must be executed by hans")
                .when(f -> f.get("exec(A)") != null && f.get("exec(A)").equals("hans"))
                .then(f -> f.put("A", true))
                .build();

        Rule dualControlPrinciple = new RuleBuilder()
                .name("WRP5-R-SOD: Separation of Duties")
                .description("Tasks A and C must not be executed by the same resource.")
                .when(f -> f.get("exec(A)") != null && f.get("exec(C)") != null && !(f.get("exec(A)").equals("exec(C)")))
                .then(f -> f.put("C", true))
                .build();

        Rule noRuleForStart = new RuleBuilder()
                .name("no rule for start")
                .description("Everyone can instantiate the process.")
                .when(f -> f.get("exec(Start)") != null)
                .then(f -> f.put("Start", true))
                .build();

        Rule noRuleForB = new RuleBuilder()
                .name("no rule for B")
                .description("Everyone can execute B.")
                .when(f -> f.get("exec(B)") != null)
                .then(f -> f.put("B", true))
                .build();

        Rule noRuleForD = new RuleBuilder()
                .name("no rule for D")
                .description("Everyone can execute D.")
                .when(f -> f.get("exec(D)") != null)
                .then(f -> f.put("D", true))
                .build();



        Rules rules = new Rules();

        rules.register(directAllocationOfA);
        rules.register(dualControlPrinciple);
        rules.register(noRuleForStart);
        rules.register(noRuleForB);
        rules.register(noRuleForD);

        return rules;

    }

    public static BPMNModel exampleBPMNModel() {

        SequenceFlow sfsA = new SequenceFlow();
        SequenceFlow sfAg1 = new SequenceFlow();
        SequenceFlow sfg1B = new SequenceFlow();
        SequenceFlow sfg1C = new SequenceFlow();
        SequenceFlow sfBg2 = new SequenceFlow();
        SequenceFlow sfCg2 = new SequenceFlow();
        SequenceFlow sfg2D = new SequenceFlow();
        SequenceFlow sfDe = new SequenceFlow();

        StartEvent s = new StartEvent(sfsA);
        Activity a = new Activity("A", sfsA, sfAg1);

        sfsA.setSourceRef(s);
        sfsA.setTargetRef(a);

        Gateway g1 = new Gateway(Set.of(sfAg1), Set.of(sfg1B, sfg1C));

        sfAg1.setSourceRef(a);
        sfAg1.setTargetRef(g1);

        Activity b = new Activity("B", sfg1B, sfBg2);

        sfg1B.setSourceRef(g1);
        sfg1B.setTargetRef(b);

        Activity c = new Activity("C", sfg1C, sfCg2);

        sfg1C.setSourceRef(g1);
        sfg1C.setTargetRef(c);

        Gateway g2 = new Gateway(Set.of(sfBg2, sfCg2), Set.of(sfg2D));

        sfBg2.setSourceRef(b);
        sfBg2.setTargetRef(g2);

        sfCg2.setSourceRef(c);
        sfCg2.setTargetRef(g2);

        Activity d = new Activity("D", sfg2D, sfDe);

        sfg2D.setSourceRef(g2);
        sfg2D.setTargetRef(d);

        EndEvent e = new EndEvent(sfDe);

        sfDe.setSourceRef(d);
        sfDe.setTargetRef(e);

        BPMNModel exampleModel = new BPMNModel();
        exampleModel.setStart(s);
        exampleModel.setEnd(e);
        exampleModel.setActivities(Set.of(a, b, c, d));
        exampleModel.setGateways(Set.of(g1, g2));
        exampleModel.setSequenceFlows(Set.of(sfsA, sfAg1, sfg1B, sfg1C, sfBg2, sfCg2, sfg2D, sfDe));

        return exampleModel;


    }

}