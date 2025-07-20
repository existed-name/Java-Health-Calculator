package com.github.existedname.healthcalculatorv2.model;

public class HealthMetricIntroduction {
    /*      1. 私有化非静态成员变量      */
    private String metricName; // 某种健康指标的名称
    private String definition; // 某种健康指标的定义
    private String formula; // 某种健康指标的计算公式
    private String function; // 某种健康指标的作用
    private String limit; // 某种健康指标的局限



    /*      2. 公有构造器      */
    // 2.1 无参数构造器
    public HealthMetricIntroduction() {
    }

    // 2.2 有参数构造器
    public HealthMetricIntroduction( String metricName, String definition, String formula, String function, String limit ) {
        this.metricName = metricName;
        this.definition = definition;
        this.formula = formula;
        this.function = function;
        this.limit = limit;
    }



    /*      3. setter       */
    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }
    public void setFormula(String formula) {
        this.formula = formula;
    }
    public void setFunction(String function) {
        this.function = function;
    }
    public void setLimit(String limit) {
        this.limit = limit;
    }



    /*      4. getter       */
    public String getMetricName() {
        return metricName;
    }
    public String getDefinition() {
        return definition;
    }
    public String getFormula() {
        return formula;
    }
    public String getFunction() {
        return function;
    }
    public String getLimit() {
        return limit;
    }
}
