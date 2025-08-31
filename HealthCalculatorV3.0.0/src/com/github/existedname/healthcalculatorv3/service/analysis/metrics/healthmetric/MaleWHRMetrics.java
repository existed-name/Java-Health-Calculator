package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * 男性 WHR 指标集, 对多个 WHR 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:33
 */
@AllArgsConstructor
public enum MaleWHRMetrics implements HealthMetricsProvider {
    EXTREMELY_LOW(
            new HealthMetricIntervalAssessment( HealthMetric.WHR.getMinValue(), 0.80, "腰臀比过低", "可能肌肉分布不均", "加强臀部训练( 如硬拉 ),平衡肌肉发展")
    ),
    EXCELLENT(
            new HealthMetricIntervalAssessment(0.80, 0.85, "腰臀比优秀", "脂肪分布理想", "维持现有训练计划,保持饮食均衡")
    ),
    GOOD(
            new HealthMetricIntervalAssessment(0.85, 0.90, "腰臀比良好", "健康范围", "继续当前生活方式,定期监测")
    ),
    HIGH(
            new HealthMetricIntervalAssessment(0.90, 0.95, "腰臀比偏高", "腹部脂肪堆积", "减少酒精摄入,增加有氧运动( 如游泳 )")
    ),
    VERY_HIGH(
            new HealthMetricIntervalAssessment(0.95, 1.00, "高危", "内脏脂肪过多", "采用间歇性禁食( 如 16:8 饮食法 ),适量 HIIT 训练")
    ),
    EXTREMELY_HIGH(
            new HealthMetricIntervalAssessment(1.00, HealthMetric.WHR.getMaxValue(), "病态", "严重中心性肥胖", "立即就医,考虑药物或手术减脂")
    );

    /** 该指标集枚举类对应的健康指标枚举成员( WHR ) */
    public static final HealthMetric HEALTH_METRIC = HealthMetric.WHR;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override
    public HealthMetricIntervalAssessment getAssessment() {
        return assessment;
    }
}