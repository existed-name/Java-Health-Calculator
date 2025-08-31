package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * 男性 BRI 指标集, 对多个 BRI 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:34
 */
@AllArgsConstructor
public enum MaleBRIMetrics implements HealthMetricsProvider {
    EXTREMELY_LOW(
            new HealthMetricIntervalAssessment( HealthMetric.BRI.getMinValue(), 2.0, "极瘦", "肌肉量严重不足,营养不良风险", "立即就医检查,增加高蛋白饮食( 2.0g/kg 体重 ),从轻度抗阻训练开始")
    ),
    LOW(
            new HealthMetricIntervalAssessment(2.0, 3.0, "偏瘦", "肌肉量不足,基础代谢率低", "增加热量摄入( 每日额外 100-300 大卡 ),进行全身抗阻训练( 每周 3 次 )")
    ),
    IDEAL(
            new HealthMetricIntervalAssessment(3.0, 4.5, "理想", "肌肉与脂肪比例均衡,健康体型", "维持均衡饮食,定期力量训练保持肌肉量")
    ),
    SLIGHTLY_OBESE(
            new HealthMetricIntervalAssessment(4.5, 6.0, "轻度中心性肥胖", "腹部脂肪开始堆积,代谢风险上升", "减少精制碳水摄入,增加有氧运动( 每周 4 次,每次 40 分钟 )")
    ),
    MEDIUM_OBESE(
            new HealthMetricIntervalAssessment(6.0, 7.5, "中度中心性肥胖", "内脏脂肪过多,胰岛素抵抗风险", "采用低碳水化合物饮食,每日热量缺口 200-400 大卡,增加 HIIT 训练")
    ),
    VERY_OBESE(
            new HealthMetricIntervalAssessment(7.5, 9.0, "重度中心性肥胖", "糖尿病、心血管疾病高风险", "立即就医检查血糖、血脂,严格控制饮食( 每日 1500-1800 大卡 ),每日运动60分钟")
    ),
    EXTREMELY_OBESE(
            new HealthMetricIntervalAssessment(9.0, HealthMetric.BRI.getMaxValue(), "病态肥胖", "多器官功能受损风险", "需医疗团队介入,考虑代谢手术,配合极低热量饮食( 800-1200 大卡/天 )")
    );

    /** 该指标集枚举类对应的健康指标枚举成员( BRI ) */
    public static final HealthMetric HEALTH_METRIC = HealthMetric.BRI;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override
    public HealthMetricIntervalAssessment getAssessment() {
        return assessment;
    }
}
