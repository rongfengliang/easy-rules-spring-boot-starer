package com.rongfengliang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineListener;

/**
 * @author dalong
 * 默认EngineListener
 */
public class DefaultRuleEngineListener implements RulesEngineListener {
        Log log = LogFactory.getLog(DefaultRuleEngineListener.class);

        @Override
        public void beforeEvaluate(Rules rules, Facts facts) {
            log.info("-----------------beforeEvaluate-----------------");
            log.info(" DefaultRuleEngineListener "+rules.toString() +" "+facts.toString());
        }

        @Override
        public void afterExecute(Rules rules, Facts facts) {
            log.info("-----------------afterExecute-----------------");
            log.info(" DefaultRuleEngineListener "+rules.toString()+"   "+facts.toString());
        }
}
