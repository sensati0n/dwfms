package dwfms.collaboration.roundrobin.consensus;

import com.google.common.collect.Sets;
import dwfms.framework.action.Action;
import dwfms.framework.collaboration.BaseCollaboration;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;

import java.util.List;

public class RoundRobinConsensusEngine extends BaseConsensusEngine {

    private String leader;

    public RoundRobinConsensusEngine(BaseCollaboration collaboration) {
    }

    @Override
    public boolean checkAgreement(Action a) {

        if(true) {
//            super.getCollaboration().atAgreementReached();
        }
        return false;
    }

    /**
     * RoundRobin leader election
     */
    private void leaderElection() {
        List<String> list = List.copyOf(Sets.newHashSet(super.getCollaboration().getDwfms().getModel().getParticipants()));
        int index = list.indexOf(leader);
        leader = list.get((index+1)%list.size());
    }
}
