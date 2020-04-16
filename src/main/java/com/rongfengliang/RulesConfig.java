package com.rongfengliang;


/**
 * @author dalong
 * rules文件配置信息
 */
public class RulesConfig {

    private String rulesId;
    private String rulesLocation;
    private RuleFileType contentType;

    public String getRulesId() {
        return rulesId;
    }

    public void setRulesId(String rulesId) {
        this.rulesId = rulesId;
    }

    public String getRulesLocation() {
        return rulesLocation;
    }

    public void setRulesLocation(String rulesLocation) {
        this.rulesLocation = rulesLocation;
    }

    public RuleFileType getContentType() {
        return contentType;
    }

    public void setContentType(RuleFileType contentType) {
        this.contentType = contentType;
    }
}
