package dwfms.execution.ruleengine;

import dwfms.framework.action.TaskExecution;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

@Data
public class EasyRuleEngine {

    private static final Logger logger = LogManager.getLogger(EasyRuleEngine.class);

    private RulesEngine rulesEngine = new DefaultRulesEngine();
    private Facts facts = new Facts();
    private Rules rules;

    public boolean isConform(TaskExecution a) {

        facts.put("exec("+a.getTask()+")", a.getUser().getUserReference().getName());
        facts.put(a.getTask(), false);
        rulesEngine.fire(rules, facts);

        boolean isConform = facts.get(a.getTask());

        facts.remove("exec("+a.getTask()+")");
        facts.remove(a.getTask());

        return isConform;
    }

    public void addFact(TaskExecution a) {
        this.facts.put("exec("+a.getTask()+")", a.getUser().getUserReference().getName());
    }

}
