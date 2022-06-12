package dwfms.collaboration.example.consensus;

import com.google.common.collect.Sets;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;
import dwfms.framework.collaboration.consensus.Acknowledgement;

import java.util.List;

public class RoundRobinConsensus extends BaseConsensusEngine {

    private String leader;

    @Override
    public boolean isAgreementReached(Acknowledgement acknowledgement) {

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
