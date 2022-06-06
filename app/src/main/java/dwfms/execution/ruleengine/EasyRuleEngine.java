package dwfms.execution.ruleengine;

import dwfms.framework.Action;
import dwfms.framework.TaskExecution;
import lombok.Data;
import okhttp3.internal.concurrent.Task;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

@Data
public class EasyRuleEngine {

    private RulesEngine rulesEngine = new DefaultRulesEngine();
    private Facts facts = new Facts();
    private Rules rules;

    public boolean isConform(TaskExecution a) {

        facts.put("exec("+a.getTask()+")", a.getUser().getUserReference().getName());
        facts.put(a.getTask(), false);
//        facts.forEach(f -> System.out.println(f.getName() + ", " + f.getValue()));
        rulesEngine.fire(rules, facts);

        boolean isConform = facts.get(a.getTask());

        facts.remove("exec("+a.getTask()+")");
        facts.remove(a.getTask());

        return isConform;
    }

}
