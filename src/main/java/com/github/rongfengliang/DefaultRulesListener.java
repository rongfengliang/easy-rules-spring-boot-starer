package com.github.rongfengliang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

/**
 * @author  dalong
 * 默认DefaultRulesListener实现
 */
public class DefaultRulesListener implements RuleListener {
    Log log = LogFactory.getLog(DefaultRulesListener.class);
    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        return true;
    }

    @Override
    public void afterEvaluate(Rule rule, Facts facts, boolean b) {
        log.info("-----------------afterEvaluate-----------------");
        log.info("DefaultRulesListener: "+"rule name: "+rule.getName()+"rule desc: "+rule.getDescription()+facts.toString());
    }

    @Override
    public void beforeExecute(Rule rule, Facts facts) {
        log.info("-----------------beforeExecute-----------------");
        log.info("DefaultRulesListener: "+"rule name: "+rule.getName()+"rule desc: "+rule.getDescription()+facts.toString());
    }

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        log.info("-----------------onSuccess-----------------");
        log.info("DefaultRulesListener: "+"rule name: "+rule.getName()+"rule desc: "+rule.getDescription()+facts.toString());
    }

    @Override
    public void onFailure(Rule rule, Facts facts, Exception e) {
        log.info("-----------------onFailure-----------------");
        log.info("DefaultRulesListener: "+"rule name: "+rule.getName()+"rule desc: "+rule.getDescription()+facts.toString());
    }
}
