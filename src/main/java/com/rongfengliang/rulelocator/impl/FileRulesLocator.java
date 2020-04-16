package com.rongfengliang.rulelocator.impl;

import com.rongfengliang.RuleFileType;
import com.rongfengliang.RuleExpressionType;
import com.rongfengliang.rulelocator.RulesLocator;
import org.jeasy.rules.api.Rules;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.expression.BeanResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dalong
 */
@Component
public class FileRulesLocator implements RulesLocator {

    @ConditionalOnBean(BeanResolver.class)
    @Override
    public Map<String, Rules> loadRules(RuleExpressionType expressionType, RuleFileType fileType) {
        // todo 提取支持mvel以及spel的通用模式
        return null;
    }
}
