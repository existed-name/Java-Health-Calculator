package com.github.existedname.healthcalculatorv2.model;

public class HealthMetricIdealRange {
    /*      1. 私有化非静态成员变量      */
    private String metricName; // 某种健康指标的名称
    private String idealRange; // 某种健康指标的理想值/范围



    /*      2. 公有构造器        */
    // 2.1 无参数构造器
    public HealthMetricIdealRange() {

    }

    // 2.2 有参数构造器
    public HealthMetricIdealRange(String metricName, String idealRange) {
        this.metricName = metricName;
        this.idealRange = idealRange;
    }



    /*      3. setter       */
    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
    public void setIdealRange(String idealRange) {
        this.idealRange = idealRange;
    }



    /*      4. getter       */
    public String getMetricName() {
        return metricName;
    }
    public String getIdealRange() {
        return idealRange;
    }
}
