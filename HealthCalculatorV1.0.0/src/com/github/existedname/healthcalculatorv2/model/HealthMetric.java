package com.github.existedname.healthcalculatorv2.model;

/**
 *      编写这个类的想法来自 AI,用这个类创建对象并初始化,让对象拥有某种健康指标(比如 BMI )
 *  的某个数值区间范围 & 这个范围对应的身体健康状况、潜在风险、建议,之后定义 *对象数组*
 *  --类似 C/C++ 中的结构体数组,每个对象就代表了一种健康指标的不同方面,方便以表格形式展示
 *  <p>
 *  Metric: 指标/度量--可量化的测量标准(如性能、效率等)
 *  <p>
 *  HealthMetric: 健康指标--特指衡量健康状况的量化数据,常见于医疗、运动监测等领域
 *  <p>
 *  Health Metrics: 健康指标集
 */

public class HealthMetric {
    /*      1. 私有非静态成员变量        */
    private double rangeStart; // 区间起点( 闭区间 )
    private double rangeEnd; // 区间终点( 闭区间 )
    private String healthStatus; // 身体健康状况
    private String healthRisk; // 潜在健康风险
    private String healthAdvice; // 建议事项



    /*      2. 构造器        */
    /*  2.1 无参数构造器  */
    public HealthMetric() { }

    /*  2.2 有参数构造器  */
    // 全参
    public HealthMetric(double rangeStart, double rangeEnd, String healthStatus, String healthRisk, String healthAdvice) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.healthStatus = healthStatus;
        this.healthRisk = healthRisk;
        this.healthAdvice = healthAdvice;
    }



    /*      3. setter       */
    public void setRangeStart( double rangeStart ) {
        this.rangeStart = rangeStart;
    }
    public void setRangeEnd( double rangeEnd ) { this.rangeEnd = rangeEnd; }
    public void setHealthStatus( String healthStatus ) { this.healthStatus = healthStatus; }
    public void setHealthRisk( String healthRisk ) { this.healthRisk = healthRisk; }
    public void setHealthAdvice( String healthAdvice ) { this.healthAdvice = healthAdvice; }



    /*      4. getter       */
    public double getRangeStart() { return rangeStart; }
    public double getRangeEnd() { return rangeEnd; }
    public String getHealthStatus() { return healthStatus; }
    public String getHealthRisk() { return healthRisk; }
    public String getHealthAdvice() { return healthAdvice; }
}
