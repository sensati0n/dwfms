package dwfms.framework;

import dwfms.ExampleDataFactory;
import dwfms.framework.references.InstanceReference;
import dwfms.model.bpmn.BPMNModel;

public class Database {

    public static IModel getModelByInstanceReference(InstanceReference instance) {

        return ExampleDataFactory.exampleBPMNModel();

    }
}
