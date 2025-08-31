package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * TDEE 指标集, 对多个 TDEE 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:35
 */
@AllArgsConstructor
public enum TDEEMetrics implements HealthMetricsProvider {
    // 极低活动水平( 久坐办公、极少运动 )
    EXTREMELY_LOW(
            new HealthMetricIntervalAssessment( HealthMetric.TDEE.getMinValue(), 1200, "能量摄入不足", "代谢减缓、肌肉流失、月经不调( 女性 )、免疫力下降", "逐步增加热量摄入至基础代谢水平;每日摄入至少 1.2g/kg 体重蛋白质" )
    ),
    VERY_LOW(
            new HealthMetricIntervalAssessment( 1200, 1600, "极低活动水平", "维持基本生理功能,难以支持日常活动", "增加轻度活动( 如散步 );每日热量摄入不低于 BMR × 1.1" )
    ),
    // 低活动水平( 轻度运动 1-3 天/周 )
    LOW(
            new HealthMetricIntervalAssessment( 1600, 2000, "低活动水平", "适合轻度办公室工作者,可能有体重增加风险", "每周 3 次 30 分钟中等强度运动;保持碳水 : 蛋白质 : 脂肪 = 5:3:2 比例" )
    ),
    // 中等活动水平( 中等运动 3-5 天/周 )
    MEDIUM(
            new HealthMetricIntervalAssessment( 2000, 2400, "中等活动水平", "适合大多数健康成年人,维持当前体重", "每日摄入热量 = BMR × 1.55;保证足够水分和电解质" )
    ),
    // 高活动水平( 高强度运动 6-7 天/周 )
    HIGH(
            new HealthMetricIntervalAssessment( 2400, 3000, "高活动水平", "适合运动员或体力劳动者,需注意营养恢复", "运动后 30 分钟内补充碳水和蛋白质( 比例 4:1 );每日钠摄入不超过 2300mg" )
    ),
    // 超高活动水平( 专业运动员、极重体力劳动 )
    VERY_HIGH(
            new HealthMetricIntervalAssessment( 3000, 4000, "超高活动水平", "需严格规划营养摄入,存在过度训练风险", "分阶段碳水摄入策略;补充支链氨基酸( BCAA );定期监测血睾酮水平" )
    ),
    // 极高活动水平( 精英运动员、极端体力活动 )
    EXTREMELY_HIGH(
            new HealthMetricIntervalAssessment( 4000, HealthMetric.TDEE.getMaxValue(), "极高活动水平", "可能需要专业营养师指导,需警惕心脏负荷", "个体化营养方案;定期心脏功能评估;保证 8 小时睡眠" )
    );

    /** 该指标集枚举类对应的健康指标枚举成员( TDEE ) */
    public static final HealthMetric healthMetric = HealthMetric.TDEE;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override
    public HealthMetricIntervalAssessment getAssessment(){
        return assessment;
    }
}
