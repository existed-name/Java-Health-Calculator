package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * 女性 BFR 指标集, 对多个 BFR 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:28
 */
@AllArgsConstructor
public enum FemaleBFRMetrics implements HealthMetricsProvider {
    EXTREMELY_LOW(        // 极低(病态)
            new HealthMetricIntervalAssessment( HealthMetric.BFR.getMinValue(), 10.0, "体脂率极低(病态)", "月经不调、生育能力下降", "立即就医,增加健康脂肪摄入,停止减脂")
    ),
    ATHLETE_LEVEL(        // 运动员水平
            new HealthMetricIntervalAssessment(10.0, 18.0, "体脂率处于运动员水平", "适合专业运动员", "维持高强度训练,保证蛋白质摄入(1.8g/kg体重)")
    ),
    GOOD(                 // 良好
            new HealthMetricIntervalAssessment(18.0, 22.0, "体脂率良好", "肌肉线条明显", "保持当前训练计划,注意铁元素补充")
    ),
    NORMAL(               // 正常
            new HealthMetricIntervalAssessment(22.0, 28.0, "体脂率正常", "健康体脂", "每周2次臀部训练+3次有氧,保证膳食纤维摄入")
    ),
    HIGH(        // 偏高
            new HealthMetricIntervalAssessment(28.0, 33.0, "体脂率偏高", "腹部脂肪堆积", "减少精制碳水摄入,增加核心训练(如平板支撑)")
    ),
    VERY_HIGH(                 // 高
            new HealthMetricIntervalAssessment(33.0, 38.0, "肥胖", "代谢风险增加", "采用高蛋白饮食(1.6g/kg体重),每日快走60分钟")
    ),
    EXTREMELY_HIGH(            // 病态肥胖
            new HealthMetricIntervalAssessment(38.0, HealthMetric.BFR.getMaxValue(), "病态肥胖", "糖尿病、心血管疾病高风险", "立即就医,考虑药物或手术干预")
    );

    /** 该指标集枚举类对应的健康指标枚举成员( BSA ) */
    public static final HealthMetric HEALTH_METRIC = HealthMetric.BFR;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override
    public HealthMetricIntervalAssessment getAssessment(){
        return assessment;
    }
}
