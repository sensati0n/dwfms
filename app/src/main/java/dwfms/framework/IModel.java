package dwfms.framework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface IModel {

    List<String> getParticipants();

    /**
     * This is the hash of the model on which the participants have agreed upon.
     * When receiving the hash of the model, each participant can map the hash to their locally available model.
     * @return
     */
    String getHash();

}
