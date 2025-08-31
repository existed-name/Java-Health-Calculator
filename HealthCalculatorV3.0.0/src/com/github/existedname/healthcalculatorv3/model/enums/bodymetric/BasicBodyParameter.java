package com.github.existedname.healthcalculatorv3.model.enums.bodymetric;

import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import com.github.existedname.healthcalculatorv3.util.convertor.TypeConvertor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基本身体参数枚举类<br>
 * 储存测量得到的身体参数( 年龄、性别、体重、身高、围度、活动系数 )的名称、单位、有效值范围<br>
 * 实现 {@link BodyMetric } 接口, 提供 getter、isValid 等方法
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/18
 */
@Getter
@AllArgsConstructor
public enum BasicBodyParameter implements BodyMetric {
    // 基础身体数据
    AGE("年龄", "Age", "AGE", "岁", 1, 122,
        "最小值1岁（新生儿通常不参与此类计算），最大值122岁（人类最长寿命记录）"),

    WEIGHT("体重", "Weight", "WT", "kg", 2.0, 635.0,
        "最小值2kg（极低体重婴儿），最大值635kg（世界最重人记录）"),
    
    HEIGHT("身高", "Height", "HT", "cm", 50.0, 272.0,
        "最小值50cm（新生儿平均身高），最大值272cm（世界最高人记录）"),
    
    WAIST_CIRCUMFERENCE("腰围", "Waist Circumference", "WC", "cm", 40.0, 302.0,
        "最小值40cm（幼儿测量起点），最大值302cm（世界记录保持者）"),
    
    HIP_CIRCUMFERENCE("臀围", "Hip Circumference", "HC", "cm", 50.0, 244.0,
        "最小值50cm（幼儿测量起点），最大值244cm（世界记录保持者）"),
    
    NECK_CIRCUMFERENCE("颈围", "Neck Circumference", "NC", "cm", 20.0, 60.0,
        "最小值20cm（幼儿测量起点），最大值60cm（极端肥胖情况）"),

    ARM_CIRCUMFERENCE("臂围", "Arm Circumference", "AC", "cm", 15.0, 45.0,
            "最小值15cm（幼儿上臂围），最大值45cm（成年男性运动员极限值）"),
    
    ACTIVITY_COEFFICIENT("活动系数", "Activity Coefficient", "AC", "", 1.0, 2.6,
        "最小值1.0（卧床不起），最大值2.6（高强度运动员）"),
    ;

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
        if ( this != AGE ){
            return ValueFormatter.formatClosedRange( minValue, maxValue );
        } else {
            return ValueFormatter.formatClosedRange( TypeConvertor.toInt( minValue ), TypeConvertor.toInt( maxValue ) );
        }
    }

    @Override
    public boolean isValid( double value ){
        return ( minValue <= value && value <= maxValue );
    }

    /** 用于 int 类型的身体参数( 如 AGE )的数值合法判断 */
    public boolean isValid( int value ){
        return ( TypeConvertor.toInt( minValue ) <= value && value <= TypeConvertor.toInt( maxValue ) );
    }
}
