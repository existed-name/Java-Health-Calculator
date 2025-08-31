package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * 男性 BFR 指标集, 对多个 BFR 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:29
 */
@AllArgsConstructor
public enum MaleBFRMetrics implements HealthMetricsProvider {
        EXTREMELY_LOW(        // 极低(病态)
                new HealthMetricIntervalAssessment( HealthMetric.BFR.getMinValue(), 5.0, "体脂率极低(病态)", "激素失衡、器官保护不足", "立即就医,增加健康脂肪摄入,停止减脂")
        ),
        ATHLETE_LEVEL(        // 运动员水平
                new HealthMetricIntervalAssessment(5.0, 10.0, "体脂率处于运动员水平", "适合专业运动员", "维持高强度训练,保证蛋白质摄入(2.0g/kg体重)")
        ),
        GOOD(                 // 良好
                new HealthMetricIntervalAssessment(10.0, 15.0, "体脂率良好", "肌肉线条明显", "保持当前训练计划,均衡饮食(碳水:蛋白质:脂肪=5:3:2)")
        ),
        NORMAL(               // 正常
                new HealthMetricIntervalAssessment(15.0, 20.0, "体脂率正常", "健康体脂", "每周3次抗阻训练+2次有氧,控制精制糖摄入")
        ),
        HIGH(                 // 偏高
                new HealthMetricIntervalAssessment(20.0, 25.0, "体脂率偏高", "腹部脂肪堆积", "减少糖类、油脂摄入,增加HIIT训练(每周3次)")
        ),
        VERY_HIGH(            // 高
                new HealthMetricIntervalAssessment(25.0, 30.0, "肥胖", "代谢风险增加", "断绝不健康脂肪、糖类、油糖混合物摄入,每日热量缺口200-400大卡,配合力量训练")
        ),
        EXTREMELY_HIGH(         // 病态肥胖
                new HealthMetricIntervalAssessment(30.0, HealthMetric.BFR.getMaxValue(), "病态肥胖", "糖尿病、心血管疾病高风险", "立即就医,考虑药物或手术干预")
        );

        /** 该指标集枚举类对应的健康指标枚举成员( BFR ) */
        public static final HealthMetric HEALTH_METRIC = HealthMetric.BFR;
        /** 当前指标集枚举成员对应的评估对象 */
        private final HealthMetricIntervalAssessment assessment;

        @Override
        public HealthMetricIntervalAssessment getAssessment() {
            return assessment;
        }
}