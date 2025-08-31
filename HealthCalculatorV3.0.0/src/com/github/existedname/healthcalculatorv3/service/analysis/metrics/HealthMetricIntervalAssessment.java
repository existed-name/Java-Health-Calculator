package com.github.existedname.healthcalculatorv3.service.analysis.metrics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 对健康指标某段数值区间的全面评估<br>
 * 包含: 区间范围, 该范围反应的身体状况、健康风险及建议
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateTime 2025/8/26 14:54
 */
@Getter
// @Setter 不设置 setter, 迎合指标集枚举类, 避免修改内部数据
@AllArgsConstructor
@NoArgsConstructor
public class HealthMetricIntervalAssessment {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================
    /** 闭区间起点 */
    private double lowerBound;
    /** 闭区间终点 */
    private double upperBound;
    /** 身体健康状态 */
    private String healthStatus;
    /** 潜在健康风险 */
    private String healthRisk;
    /** 健康建议 */
    private String healthAdvice;


    // ==================== 构造器 ====================



    // ==================== 公有方法 ====================

    /**
     * 判断给定值是否在当前评估对象对应的闭区间内
     * @param value 待判断的值
     * @return true: 待判断的值在闭区间内, 否则 false
     * @since 3.0.0
     */
    public boolean isInRange( double value ){
        return ( lowerBound <= value && value <= upperBound );
    }


    // ==================== 私有辅助方法 ====================

}
