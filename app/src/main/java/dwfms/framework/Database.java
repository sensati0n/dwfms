package dwfms.framework;

import dwfms.ExampleDataFactory;
import dwfms.framework.references.Instance;

public class Database {

    public static IModel getModelByInstanceReference(Instance instance) {

        return ExampleDataFactory.exampleBPMNModel();

    }
}
