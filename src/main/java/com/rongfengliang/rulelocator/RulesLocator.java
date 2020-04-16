package com.rongfengliang.rulelocator;

import com.rongfengliang.RuleFileType;
import com.rongfengliang.RuleExpressionType;
import org.jeasy.rules.api.Rules;

import java.util.Map;

public interface RulesLocator {

    /**
     * 加载规则列表
     * @param expressionType rules 表达式语言
     * @param fileType rules 内容格式
     * @return
     */
    Map<String,Rules> loadRules(RuleExpressionType expressionType, RuleFileType fileType);

}

