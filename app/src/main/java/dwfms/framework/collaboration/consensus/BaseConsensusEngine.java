package dwfms.framework.collaboration.consensus;

import dwfms.framework.collaboration.BaseCollaboration;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseConsensusEngine {

    @Getter
    @Setter
    private BaseCollaboration collaboration;


    public abstract boolean isAgreementReached(Acknowledgement a);

}
