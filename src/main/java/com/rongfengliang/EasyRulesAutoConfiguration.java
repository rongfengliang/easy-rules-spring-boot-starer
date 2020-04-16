package com.rongfengliang;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineListener;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;
import org.jeasy.rules.spel.SpELRuleFactory;
import org.jeasy.rules.support.JsonRuleDefinitionReader;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.BeanResolver;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
@EnableConfigurationProperties(EasyRulesEngineConfiguration.class)
public class EasyRulesAutoConfiguration {
    Log log = LogFactory.getLog(EasyRulesAutoConfiguration.class);
    private final EasyRulesEngineConfiguration properties;

    EasyRulesAutoConfiguration(EasyRulesEngineConfiguration properties){
        this.properties=properties;
    }
    @Bean
    @ConditionalOnMissingBean
    public RuleListener defaultRulesListener(){
        return  new DefaultRulesListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public RulesEngineListener defaultRuleEngineListener(){
        return  new DefaultRuleEngineListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanResolver defaultedResolver(SpringBeanUtil springBeanUtil){
        return  new SimpleBeanResovler(SpringBeanUtil.getApplicationContext());
    }

    @Bean
    @ConditionalOnMissingBean
    public  SpringBeanUtil springBeanUtil(){
        return  new SpringBeanUtil();
    }

    /**
     * 获取配置额规则列表
     *
     * @param beanResolver spring beanResolver
     * @return Map<String,Rules>
     * @throws Exception
     */

    @Bean
    public Map<String,Rules> configRules(BeanResolver beanResolver) throws Exception {
        Map<String,Rules>  rules = new HashMap<>();
        this.properties.getRules().forEach(new Consumer<RulesConfig>() {
            @Override
            public void accept(RulesConfig rulesConfig) {
              switch (rulesConfig.getContentType()){
                  case JSON:
                      SpELRuleFactory jsonRuleFactory = new SpELRuleFactory(new JsonRuleDefinitionReader(),beanResolver);
                      Rules jsonRules = null;
                      try {
                          jsonRules = jsonRuleFactory.createRules(new FileReader(this.getClass().getClassLoader().getResource(rulesConfig.getRulesLocation()).getFile()));
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                      rules.put(rulesConfig.getRulesId(),jsonRules);
                      break;
                  case YAML:
                       SpELRuleFactory yamlRuleFactory = new SpELRuleFactory(new YamlRuleDefinitionReader(),beanResolver);
                       Rules yamlRules = null;
                      try {
                          yamlRules = yamlRuleFactory.createRules(new FileReader(this.getClass().getClassLoader().getResource(rulesConfig.getRulesLocation()).getFile()));
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                      rules.put(rulesConfig.getRulesId(),yamlRules);
                      break;

                  default:
                      throw new IllegalStateException("Unexpected value: " + rulesConfig.getContentType());
              }
            }
        });
        return rules;

    }
    /**
     * 为了安全使用原型模式
     * @param defaultRulesListener
     * @param defaultRuleEngineListener
     * @return  RulesEngine
     */
    @Bean
    @ConditionalOnMissingBean(RulesEngine.class)
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RulesEngine rulesEngine(RuleListener defaultRulesListener, RulesEngineListener defaultRuleEngineListener) {
        log.info("create rule Engine");
        RulesEngineParameters parameters = new RulesEngineParameters();
        if(this.properties.getPriorityThreshold()>0){
            parameters.setPriorityThreshold(this.properties.getPriorityThreshold());
        }
        if(this.properties.isSkipOnFirstAppliedRule()){
            parameters.setSkipOnFirstAppliedRule(this.properties.isSkipOnFirstAppliedRule());
        }
        if(this.properties.isSkipOnFirstFailedRule()){
            parameters.setSkipOnFirstFailedRule(this.properties.isSkipOnFirstFailedRule());
        }
        if(this.properties.isSkipOnFirstNonTriggeredRule()){
            parameters.setSkipOnFirstNonTriggeredRule(this.properties.isSkipOnFirstNonTriggeredRule());
        }
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);
        rulesEngine.registerRuleListener(defaultRulesListener);
        rulesEngine.registerRulesEngineListener(defaultRuleEngineListener);
        return rulesEngine;
    }

}
