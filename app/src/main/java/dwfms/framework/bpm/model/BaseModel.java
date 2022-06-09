package dwfms.framework.bpm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * This is a core class that must be extended for any model class that is used with the dWFMS concept.
 */
public abstract class BaseModel {

    /**
     * A reference to the participants' dWFMSs is a mandatory information for any model.
     * This is used in the Collaboration tier to detect the recipients of any message.
     * @return a set of the participants of the model
     */
    @Getter
    @Setter
    private Set<String> participants;

    /**
     * This is the hash of the model on which the participants have agreed upon.
     * When receiving the hash of the model, each participant can map the hash to their locally available model.
     * @return the hash of a certain model
     */
    public abstract ModelReference getModelReference();

}
