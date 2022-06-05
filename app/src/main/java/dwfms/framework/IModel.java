package dwfms.framework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface IModel {
    List<String> getParticipants();
}
