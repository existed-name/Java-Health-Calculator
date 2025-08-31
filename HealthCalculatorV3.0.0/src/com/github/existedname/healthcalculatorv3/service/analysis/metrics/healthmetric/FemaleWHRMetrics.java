package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * 女性 WHR 指标集, 对多个 WHR 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:34
 */
@AllArgsConstructor
public enum FemaleWHRMetrics implements HealthMetricsProvider {
    EXTREMELY_LOW(
            new HealthMetricIntervalAssessment(HealthMetric.WHR.getMinValue(), 0.70, "腰臀比过低", "可能肌肉分布不均", "加强臀部训练( 如深蹲 ),平衡肌肉发展")
    ),
    EXCELLENT(
            new HealthMetricIntervalAssessment(0.70, 0.75, "腰臀比优秀", "沙漏型身材,雌激素水平理想", "维持现有训练计划,保证钙摄入")
    ),
    GOOD(
            new HealthMetricIntervalAssessment(0.75, 0.80, "腰臀比良好", "健康范围", "继续当前生活方式,定期监测")
    ),
    HIGH(
            new HealthMetricIntervalAssessment(0.80, 0.85, "腰臀比偏高", "腹部脂肪堆积", "减少精制碳水摄入,增加核心训练")
    ),
    VERY_HIGH(
            new HealthMetricIntervalAssessment(0.85, 0.90, "高危", "内脏脂肪过多", "采用高蛋白饮食( 1.6g/kg 体重 ),每周 5 次运动")
    ),
    EXTREMELY_HIGH(
            new HealthMetricIntervalAssessment(0.90, HealthMetric.WHR.getMaxValue(), "病态", "严重中心性肥胖", "立即就医,考虑药物或手术减脂")
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