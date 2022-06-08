package dwfms.execution;

import dwfms.ExampleDataFactory;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.action.User;
import dwfms.framework.action.UserAction;
import dwfms.framework.error.NonCompliantExecutionException;
import dwfms.framework.references.Instance;
import dwfms.framework.references.UserReference;
import dwfms.model.BPMNToHybridExecutionMachineTransformer;
import dwfms.model.bpmn.BPMNModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HybridExecutionEngineTest {

    BPMNModel model;
    HybridExecutionMachine hem;
    Instance instance;

    User hans = new User(new UserReference("hans"), null, null);
    User peter = new User(new UserReference("peter"), null, null);


    @BeforeEach
    public void setup() {
        model = ExampleDataFactory.exampleBPMNModel();
        hem = new BPMNToHybridExecutionMachineTransformer().transform(model);
        instance = new Instance("instanceRef", model.getHash());
    }


    @Test
    public void isConformTest() {


        // everybody (including hans) is allowed to execute Start
        UserAction start = new TaskExecution(instance, "Start");
        start.setUser(hans);
        assertTrue(hem.isConform(instance, start));

        // A is not executable, because Start was not executed yet
        UserAction a = new TaskExecution(instance, "A");
        a.setUser(hans);
        assertFalse(hem.isConform(instance, a));

        // let's execute Start
        hem.execute(instance, start);

        // now, A is executable (with user hans)
        assertTrue(hem.isConform(instance, a));

        // a wrong user (peter) is not allowed to execute A
        a.setUser(peter);
        assertFalse(hem.isConform(instance, a));

    }

    @Test
    public void checkWrongResource() {

        // Start
        UserAction start = new TaskExecution(instance, "Start");
        start.setUser(hans);
        hem.execute(instance, start);

        // A
        UserAction a = new TaskExecution(instance, "A");
        a.setUser(hans);
        hem.execute(instance, a);

        // C
        UserAction c = new TaskExecution(instance, "C");
        c.setUser(hans);
        assertFalse(hem.isConform(null, c));
        Assertions.assertThrows(NonCompliantExecutionException.class, () -> hem.execute(instance, c));

    }

    @Test
    public void executeATrace() {

        // Start
        UserAction start = new TaskExecution(instance, "Start");
        start.setUser(hans);
        hem.execute(instance, start);

        // A
        UserAction a = new TaskExecution(instance, "A");
        a.setUser(hans);
        hem.execute(instance, a);

        // B
        UserAction b = new TaskExecution(instance, "B");
        b.setUser(hans);
        hem.execute(instance, b);

        // D
        UserAction d = new TaskExecution(instance, "D");
        d.setUser(peter);
        hem.execute(instance, d);

    }

    @Test
    public void executeAnotherTrace() {

        // Start
        UserAction start = new TaskExecution(instance, "Start");
        start.setUser(hans);
        hem.execute(instance, start);

        // A
        UserAction a = new TaskExecution(instance, "A");
        a.setUser(hans);
        hem.execute(instance, a);

        // C
        UserAction c = new TaskExecution(instance, "C");
        c.setUser(peter);
        hem.execute(instance, c);

        // D
        UserAction d = new TaskExecution(instance, "D");
        d.setUser(peter);
        hem.execute(instance, d);


    }

    @Test
    public void aNonCompliantExecutionAttemptThrows() {
        // everybody (including hans) is allowed to execute Start
        UserAction start = new TaskExecution(instance, "Start");
        start.setUser(hans);
        assertTrue(hem.isConform(instance, start));

        // A is not executable, because Start was not executed yet
        UserAction a = new TaskExecution(instance, "A");
        a.setUser(hans);
        assertFalse(hem.isConform(instance, a));

        // let's execute Start
        hem.execute(instance, start);

        // start cannot be executed twice
        Assertions.assertThrows(NonCompliantExecutionException.class, () -> hem.execute(instance, start));

        // D is not executable, because B or D was not executed yet
        UserAction d = new TaskExecution(instance, "D");
        d.setUser(hans);

        // start cannot be executed twice
        Assertions.assertThrows(NonCompliantExecutionException.class, () -> hem.execute(instance, d));

        // start cannot be executed twice
        Assertions.assertThrows(NonCompliantExecutionException.class, () -> hem.execute(instance, start));

    }

    @Test
    public void getWorkListTest() {

        hem.getWorkList(instance, hans.getUserReference());

        UserAction start = new TaskExecution(instance, "Start");
        start.setUser(hans);
        UserAction a = new TaskExecution(instance, "A");
        a.setUser(hans);


        assertTrue(hem.isConform(instance, start));
        assertFalse(hem.isConform(instance, a));
    }
}
