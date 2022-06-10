package dwfms.execution.petrinet;

import dwfms.bpm.execution.petrinet.PetriNet;
import dwfms.bpm.execution.petrinet.Place;
import dwfms.bpm.execution.petrinet.Transition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PetriNetTest {

    @Test
    public void pnTest() {

        PetriNet pn = new PetriNet();

        Place p1 = pn.place(1);
        Place p2 = pn.place(0);
        Place p3 = pn.place(0);
        Place p4 = pn.place(0);
        Place p5 = pn.place(0);
        Place p6 = pn.place(0);
        Place p7 = pn.place(0);
        Place p8 = pn.place(0);

        Transition t1 = pn.transition("");
        Transition t2 = pn.transition("");
        Transition t3 = pn.transition("");
        Transition t4 = pn.transition("");
        Transition t5 = pn.transition("");
        Transition t6 = pn.transition("");
        Transition t7 = pn.transition("");
        Transition t8 = pn.transition("");

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

        assertTrue(t1.canFire());
        assertFalse(t2.canFire());

        t1.fire();

        assertEquals(0, p1.getTokens());
        assertEquals(1, p2.getTokens());

        assertFalse(t1.canFire());
        assertTrue(t2.canFire());

        t2.fire();

        assertFalse(t2.canFire());
        assertTrue(t3.canFire());
        assertFalse(t4.canFire());
        assertTrue(t5.canFire());

        t5.fire();

        assertFalse(t3.canFire());
        assertFalse(t5.canFire());
        assertTrue(t6.canFire());

        t6.fire();

        assertTrue(t7.canFire());

        t7.fire();
        t8.fire();

        assertEquals(1, p8.getTokens());

    }

}
