package dwfms.collaboration.example.consensus;

import dwfms.framework.action.Action;
import dwfms.framework.action.TaskExecution;
import dwfms.framework.collaboration.consensus.BaseConsensusEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ThresholdConsensus is parametrized with the number of agreements that are required
 * to assume that agreement is reached on a certain event.
 *
 * The number of agreements may be equal to the number of participants.
 *
 */
public class ThresholdConsensus extends BaseConsensusEngine {

    private static final Logger logger = LogManager.getLogger(ThresholdConsensus.class);

    private final int numberOfAgreementsRequired;

    public ThresholdConsensus(int numberOfAgreementsRequired) {
        this.numberOfAgreementsRequired = numberOfAgreementsRequired;
    }

    @Override
    public boolean checkAgreement(Action a) {
        TaskExecution taskExecution = (TaskExecution) a;
        logger.trace("Check for agreement...");
        if(super.getCollaboration().getCandidateLog().getNumberOfAcknowledgements().get(taskExecution.getTask()) > this.numberOfAgreementsRequired) {
            logger.debug("Agreement reached.");
            this.getCollaboration().atAgreementReached(null, taskExecution);
            return true;
        }
        else {
            logger.debug("Agreement not reached yet.");
        }
        return false;
    }
}
