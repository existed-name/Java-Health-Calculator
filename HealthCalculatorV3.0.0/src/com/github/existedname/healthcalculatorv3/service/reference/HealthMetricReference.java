package com.github.existedname.healthcalculatorv3.service.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 健康指标参考枚举类, 存储健康指标及部分基本身体参数( 腰围 )的参考范围
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/24 8:22
 */
@Getter
@AllArgsConstructor
public enum HealthMetricReference {
    WEIGHT( "男性: ( 身高(cm) - 105 )浮动 ±10%, 女性: ( 身高(cm) - 100 )浮动 ±10%\n" +
            "OR 根据 BMI 理想范围( 18.5-23.9 ),反推出理想体重范围" ),
    BMI( "国际: 18.5-24.9, 亚洲: 18.5-22.9, 中国: 18.5-23.9" ),
    WAIST_CIRCUMFERENCE( "男性: < 90cm ( 亚洲 < 85cm ), 女性: < 80cm ( 亚洲 < 80cm )" ),
    WHR( "男性: < 0.9, 女性: < 0.85" ),
    BFR( "男性: 10%-20%; 女性: 18%-28%" ),
    BRI( "男性: < 85, 女性: < 76" ),
    BMR( "男性: 1500-2000 Kcal/天; 女性: 1200-1500 Kcal/天" ),
    TDEE( "男性: 2000-3500 kcal/天, 女性: 1500-2500 kcal/天" ),
    BSA( "成人: 1.5-2.0 m²; 儿童: 0.5-1.5 m²" ), ;

    private final String idealRange;

    public void printIdealRange(){
        System.out.println( "理想范围: " + this.idealRange );
    }
}