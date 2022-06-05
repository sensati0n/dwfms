package dwfms.execution.ruleengine;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.support.composite.ActivationRuleGroup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EasyRuleEngineTest {

    @Test
    public void test() {

        // define rules
        Rule directAllocationOfA = new RuleBuilder()
                .name("WRP1-R-DA: Direct Allocation")
                .description("Task A must be executed by hans")
                .when(f -> f.get("exec(A)") != null && f.get("exec(A)").equals("hans"))
                .then(f -> f.put("A", true))
                .build();

        Rule dualControlPrinciple = new RuleBuilder()
                .name("WRP5-R-SOD: Separation of Duties")
                .description("Tasks A and C must not be executed by the same resource.")
                .when(f -> f.get("exec(A)") != null && f.get("exec(C)") != null && !(f.get("exec(A)").equals("exec(C)")))
                .then(f -> f.put("C", true))
                .build();

        Rule noRuleForStart = new RuleBuilder()
                .name("no rule for start")
                .description("Everyone can instantiate the process.")
                .when(f -> f.get("exec(Start)") != null)
                .then(f -> f.put("Start", true))
                .build();


        Rules rules = new Rules();
        rules.register(directAllocationOfA);
        rules.register(dualControlPrinciple);
        rules.register(noRuleForStart);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        // define facts
        Facts facts = new Facts();

        facts.put("exec(Start)", "peter");
        facts.put("Start", false);
        rulesEngine.fire(rules, facts);
        assertTrue((boolean) facts.get("Start"));

        facts.put("exec(A)", "peter");
        facts.put("A", false);
        rulesEngine.fire(rules, facts);
        assertFalse((boolean) facts.get("A"));

        facts.put("exec(A)", "hans");
        facts.put("A", false);
        rulesEngine.fire(rules, facts);
        assertTrue((boolean) facts.get("A"));

        facts.put("exec(C)", "sepp");
        facts.put("C", false);
        rulesEngine.fire(rules, facts);
        assertTrue((boolean) facts.get("C"));

    }
}
