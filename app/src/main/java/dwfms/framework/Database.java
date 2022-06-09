package dwfms.framework;

import dwfms.ExampleDataFactory;
import dwfms.framework.bpm.model.BaseModel;
import dwfms.framework.bpm.execution.Instance;

public class Database {

    public static BaseModel getModelByInstanceReference(Instance instance) {

        return ExampleDataFactory.exampleBPMNModel();

    }
}
