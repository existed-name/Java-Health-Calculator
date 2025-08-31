package com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import lombok.AllArgsConstructor;

/**
 * BMI 指标集, 对多个 BMI 数值范围提供评估<br>
 * 实现 {@link HealthMetricsProvider }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23 20:27
 */
@AllArgsConstructor
public enum BMIMetrics implements HealthMetricsProvider {
    LOW(
            new HealthMetricIntervalAssessment( HealthMetric.BMI.getMinValue(), 18.5, "体重较轻", "营养不良、代谢紊乱、免疫力下降", "增加营养摄入,补充优质蛋白质、维生素、矿物质;避免过度节食;定期监测体重和营养状况" )
    ),
    NORMAL(
            new HealthMetricIntervalAssessment( 18.5, 24.9, "正常体重", "健康风险较低", "保持健康生活方式: 定期运动,避免久坐,饮食均衡,定期体检" )
    ),
    OVERWEIGHT(
            new HealthMetricIntervalAssessment( 25.0, 29.9, "超重", "高血压、心血管疾病、糖尿病、脂肪肝风险增加", "关注热量摄入,减少精制碳水和饱和脂肪摄入;每周 150 分钟中等强度运动;监测体重变化" )
    ),
    OBESITY_LEVEL_I(
            new HealthMetricIntervalAssessment( 30.0, 34.9, "肥胖I级(轻度肥胖症)", "胰岛素抵抗、非酒精性脂肪肝、代谢综合征、关节负担、睡眠呼吸暂停", "限制热量摄入(每日减少 500-750 大卡);结合饮食与运动,每周 5 天中等强度运动;必要时咨询医生" )
    ),
    OBESITY_LEVEL_II(
            new HealthMetricIntervalAssessment( 35.0, 39.9, "肥胖II级(中度肥胖症)", "心血管疾病、II型糖尿病、关节问题、脂肪肝、心理压力", "专业医疗监督下的减重计划;考虑药物辅助治疗或手术干预;物理治疗缓解关节压力" )
    ),
    OBESITY_LEVEL_III(
            new HealthMetricIntervalAssessment( 40.0, 49.9, "肥胖III级(重度肥胖症)", "心血管疾病、慢性肾病、睡眠呼吸暂停、某些癌症", "多学科团队干预(医生、营养师、心理医生);优先考虑代谢手术" )
    ),
    OBESITY_LEVEL_IV(
            new HealthMetricIntervalAssessment( 50.0, HealthMetric.BMI.getMaxValue(), "肥胖IV级(极重度/超级肥胖)", "预期寿命缩短10-20年、严重心血管疾病、器官衰竭、极高死亡风险", "紧急医疗干预;代谢手术为一线治疗;终身健康监测和营养支持" )
    );

    /** 该指标集枚举类对应的健康指标枚举成员( BMI ) */
    public static final HealthMetric HEALTH_METRIC = HealthMetric.BMI;
    /** 当前指标集枚举成员对应的评估对象 */
    private final HealthMetricIntervalAssessment assessment;

    @Override
    public HealthMetricIntervalAssessment getAssessment(){
        return assessment;
    }
}
