package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * BMR 指标集, 对多个 BMR 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:27
 */
@AllArgsConstructor
public enum BMRMetrics implements HealthMetricsProvider {
    VERY_LOW(
            new HealthMetricIntervalAssessment( HealthMetric.BMR.getMinValue(), 1000.0, "基础代谢极低", "甲状腺功能减退、营养不良", "就医检查甲状腺功能,每日热量摄入 ≥ 1200 大卡" )
    ),
    LOW(
            new HealthMetricIntervalAssessment( 1000.0, 1300.0, "基础代谢偏低", "久坐生活方式、肌肉量少", "增加日常活动量( 如步行 ),每日力量训练 20 分钟" )
    ),
    NORMAL_FEMALE(
            new HealthMetricIntervalAssessment( 1300.0, 1600.0, "基础代谢正常( 女性 )", "健康代谢水平", "维持现有饮食,每周 3 次有氧运动" )
    ),
    NORMAL_MALE(
            new HealthMetricIntervalAssessment( 1600.0, 1900.0, "基础代谢正常( 男性 )", "健康代谢水平", "维持现有饮食,每周 3 次抗阻训练" )
    ),
    HIGH(
            new HealthMetricIntervalAssessment( 1900.0, 2200.0, "基础代谢偏高", "肌肉量高或甲亢倾向", "若肌肉发达则继续保持;若消瘦则检查甲状腺功能" )
    ),
    VERY_HIGH(
            new HealthMetricIntervalAssessment( 2200.0, 2500.0, "基础代谢很高", "高强度训练者或甲亢", "保证每日热量摄入( 每公斤体重 30-35kcal ),定期休息" )
    ),
    EXTREMELY_HIGH(
            new HealthMetricIntervalAssessment( 2500.0, HealthMetric.BMR.getMaxValue(), "基础代谢异常高", "甲亢或其他内分泌疾病", "立即就医,排查甲亢等疾病" )
    );

    /** 该指标集枚举类对应的健康指标枚举成员( BMR ) */
    public static final HealthMetric HEALTH_METRIC = HealthMetric.BMR;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override
    public HealthMetricIntervalAssessment getAssessment(){
        return assessment;
    }
}