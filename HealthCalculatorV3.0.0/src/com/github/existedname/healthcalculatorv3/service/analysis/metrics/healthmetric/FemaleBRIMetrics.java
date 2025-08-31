package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * 女性 BRI 指标集, 对多个 BRI 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:34
 */
@AllArgsConstructor
public enum FemaleBRIMetrics implements HealthMetricsProvider {
    EXTREMELY_LOW(
            new HealthMetricIntervalAssessment( HealthMetric.BRI.getMinValue(), 1.5, "极瘦", "雌激素水平可能过低,月经不调风险", "立即就医检查激素水平,增加健康脂肪摄入( 每日 30-40g ),停止减脂")
    ),
    LOW(
            new HealthMetricIntervalAssessment(1.5, 2.5, "偏瘦", "肌肉量不足,骨密度下降风险", "增加蛋白质摄入( 1.2-1.5g/kg 体重 ),进行力量训练( 如哑铃、深蹲 )")
    ),
    IDEAL(
            new HealthMetricIntervalAssessment(2.5, 4.0, "理想", "沙漏型身材,雌激素水平正常", "维持现有训练,保证钙和 维生素D 摄入,定期监测骨密度")
    ),
    SLIGHTLY_OBESE(
            new HealthMetricIntervalAssessment(4.0, 5.5, "轻度中心性肥胖", "腹部脂肪开始堆积,代谢风险上升", "减少含糖饮料和零食,增加核心训练( 如平板支撑、卷腹 )")
    ),
    MEDIUM_OBESE(
            new HealthMetricIntervalAssessment(5.5, 7.0, "中度中心性肥胖", "内脏脂肪过多,多囊卵巢综合征风险", "采用 低GI 饮食,增加有氧运动( 如快走、骑自行车 ),补充肌醇")
    ),
    VERY_OBESE(
            new HealthMetricIntervalAssessment(7.0, 8.5, "重度中心性肥胖", "糖尿病、心血管疾病高风险", "立即就医检查血糖、胰岛素水平,严格控制碳水摄入,每日运动 60 分钟")
    ),
    EXTREMELY_OBESE(
            new HealthMetricIntervalAssessment(8.5, Double.MAX_VALUE, "病态肥胖", "多器官功能受损风险", "需医疗团队介入,考虑药物治疗,配合低碳水高蛋白饮食( 蛋白质占比 30% )")
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