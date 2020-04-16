package com.github.rongfengliang;


import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

/**
 * 一个内置rule，方便获取rule最后的结果,使用此rule的要求是Facts需要包含biz key
 * @author dalong
 * @param <T> 范型类型
 *
 */

@Rule(name = "final rule for get result", description = "final rule for get result", priority = 10000)
public class FinalRule<T> {

    private boolean executed;

    private T result;

    @Condition
    public boolean when(@Fact("biz") T fact) {
        //my rule conditions
        return true;
    }

    @Action(order = 1)
    public void then(@Fact("biz") T facts) throws Exception {
        try {
            result = (T)facts;
            executed = true;
        } catch (Exception e) {
            throw e;
        }
    }
    public boolean isExecuted() {
        return executed;
    }
    public T getResult() {
        return result;
    }

}