package com.github.existedname.healthcalculatorv3.service.analysis.metrics.basicbodyparameter;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * 女性腰围指标集, 提供多个腰围数值范围的评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:34
 */
@AllArgsConstructor
public enum FemaleWaistCircumferenceMetrics implements HealthMetricsProvider {
    EXTREMELY_LOW(
            new HealthMetricIntervalAssessment(0.0, 65.0, "腰部过细", "可能肌肉量不足或雌激素偏低", "增加蛋白质摄入,监测月经周期")
    ),
    HEALTHY(
            new HealthMetricIntervalAssessment(65.0, 75.0, "健康", "低代谢风险", "维持现有生活方式,保持核心训练")
    ),
    SLIGHTLY_HIGH(
            new HealthMetricIntervalAssessment(75.0, 80.0, "腰围轻度超标", "代谢风险开始增加", "减少含糖饮料摄入,每周 3 次瑜伽")
    ),
    HIGH(
            new HealthMetricIntervalAssessment(80.0, 88.0, "腰围超标", "胰岛素抵抗风险", "采用 低GI 饮食,每周 4 次 30 分钟有氧")
    ),
    VERY_HIGH(
            new HealthMetricIntervalAssessment(88.0, 95.0, "高危", "糖尿病、心血管疾病高风险", "立即控制饮食( 每日热量 1200-1500 大卡 ),增加力量训练")
    ),
    EXTREME_HIGH(
            new HealthMetricIntervalAssessment(95.0, Double.MAX_VALUE, "病态", "严重代谢综合征", "立即就医,药物干预配合饮食运动")
    );

    /** 该指标集枚举类对应的基本身体参数枚举成员( 腰围 ) */
    public static final BasicBodyParameter BODY_PARAMETER = BasicBodyParameter.WAIST_CIRCUMFERENCE;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override public HealthMetricIntervalAssessment getAssessment() { return assessment; }
}