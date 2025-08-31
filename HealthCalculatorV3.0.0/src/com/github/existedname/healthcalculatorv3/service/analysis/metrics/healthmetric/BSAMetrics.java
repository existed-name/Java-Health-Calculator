package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * BSA 指标集, 对多个 BSA 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:34
 */
@AllArgsConstructor
public enum BSAMetrics implements HealthMetricsProvider {
    VERY_LOW(
            new HealthMetricIntervalAssessment( HealthMetric.BSA.getMinValue(), 1.4, "体表面积过低", "发育不良或严重消瘦", "儿科/内分泌科就诊,增加高热量密度食物摄入" )
    ),
    LOW(
            new HealthMetricIntervalAssessment( 1.4, 1.6, "体表面积偏低", "偏瘦体型", "增加蛋白质摄入( 1.2g/kg 体重 ),适量力量训练" )
    ),
    NORMAL(
            new HealthMetricIntervalAssessment( 1.6, 1.9, "体表面积正常", "体表面积适中", "无特殊风险,维持健康生活方式" )
    ),
    HIGH(
            new HealthMetricIntervalAssessment( 1.9, 2.2, "体表面积偏高", "偏胖体型或肌肉发达", "若肥胖 : 控制热量摄入;若肌肉发达 : 维持当前训练" )
    ),
    VERY_HIGH(
            new HealthMetricIntervalAssessment( 2.2, 2.5, "体表面积过高", "肥胖或巨人症", "排查内分泌疾病,制定减重计划( 每日热量缺口 200-400 大卡 )" )
    ),
    EXTREMELY_HIGH(
            new HealthMetricIntervalAssessment( 2.5, HealthMetric.BSA.getMaxValue(), "体表面积病态过高", "严重肥胖或肢端肥大症", "立即就医,考虑药物或手术干预" )
    );

    /** 该指标集枚举类对应的健康指标枚举成员( BSA ) */
    public static final HealthMetric HEALTH_METRIC = HealthMetric.BSA;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override
    public HealthMetricIntervalAssessment getAssessment(){
        return assessment;
    }
}