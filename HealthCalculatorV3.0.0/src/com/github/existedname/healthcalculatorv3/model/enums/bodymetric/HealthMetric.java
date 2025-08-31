package com.github.existedname.healthcalculatorv3.model.enums.bodymetric;

import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 健康指标枚举类, <br>
 * 储存由基本身体参数( {@link BasicBodyParameter } )计算得到的健康指标的名称、单位、有效值范围<br>
 * 包括体态评估类、能量代谢类、生理特征类 3 类身体指标
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/19 19:55
 */
@Getter
@AllArgsConstructor
public enum HealthMetric implements BodyMetric {
    // 体态评估类指标
    BMI("身体质量指数", "Body Mass Index", "BMI", "kg/m²", 10.0, 180.0,
            "最小值10（极端瘦弱但存活），最大值180（极端肥胖情况）"),

    WHR("腰臀比", "Waist-to-Hip Ratio", "WHR", "", 0.5, 1.5,
            "最小值0.5（极端梨形身材），最大值1.5（极端苹果形身材）"),

    BFR("体脂率", "Body Fat Rate", "BFR", "%", 3.0, 60.0,
            "最小值3%（男性必需脂肪），最大值60%（极端肥胖情况）"),

    BRI("身体圆度指数", "Body Roundness Index", "BRI", "", 0.0, 100.0,
            "最小值0（理想体型），最大值100（极端不健康体型）"),

    // 能量代谢类指标
    BMR("基础代谢率", "Basal Metabolic Rate", "BMR", "kcal/day", 500.0, 10000.0,
            "最小值500（极低代谢状态），最大值10000（极端高代谢状态）"),

    TDEE("每日总能量消耗", "Total Daily Energy Expenditure", "TDEE", "kcal/day", 800.0, 15000.0,
            "最小值800（极低活动状态），最大值15000（极端高活动状态）"),

    // 生理特征类指标
    BSA("体表面积", "Body Surface Area", "BSA", "m²", 0.2, 3.0,
            "最小值0.2m²（新生儿），最大值3.0m²（极端肥胖成人）");

    // 字段定义
    private final String chineseName;
    private final String englishName;
    private final String abbreviation;
    private final String unit;
    private final double minValue;
    private final double maxValue;
    private final String rangeDescription;

    @Override
    public String getFormattedClosedRange(){
        return ValueFormatter.formatClosedRange( minValue, maxValue );
    }

    @Override
    public boolean isValid( double value ){
        return ( minValue <= value && value <= maxValue );
    }
}
