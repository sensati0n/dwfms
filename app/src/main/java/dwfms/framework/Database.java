package dwfms.framework;

import dwfms.ExampleDataFactory;
import dwfms.framework.core.BaseModel;
import dwfms.framework.references.Instance;

public class Database {

    public static BaseModel getModelByInstanceReference(Instance instance) {

        return ExampleDataFactory.exampleBPMNModel();

    }
}
