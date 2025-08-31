package com.github.existedname.healthcalculatorv3.service.calculation;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;

import java.util.Arrays;

/**
 * 健康指标计算方法枚举
 * <p>
 *     每个枚举常量代表一种健康指标的计算方法, 关联了对应的方法名称、健康指标枚举成员<br>
 *     该枚举还提供访问标记功能, 用于跟踪哪些计算方法已被使用( 哪些健康指标已经计算 )
 * </p>
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/24 8:16
 */
// 注意此处不能添加 @AllArgsConstructor, 除非在创建枚举成员时初始化 visited
public enum CalculationMethod {
    CALC_BMI( "showBMICalculation", HealthMetric.BMI ),
    CALC_WHR( "showWHRCalculation", HealthMetric.WHR ),
    CALC_BFR( "showBFRCalculation", HealthMetric.BFR ),
    CALC_BRI( "showBRICalculation", HealthMetric.BRI ),
    CALC_BMR( "showBMRCalculation", HealthMetric.BMR ),
    CALC_TDEE( "showTDEECalculation", HealthMetric.TDEE ),
    CALC_BSA( "showBSACalculation", HealthMetric.BSA );

    private final String methodName;
    private final HealthMetric healthMetric;
    private boolean visited = false;

    CalculationMethod( String methodName, HealthMetric healthMetric ){
        this.methodName = methodName;
        this.healthMetric = healthMetric;
    }

    public static void resetAllAccessFlags(){
        Arrays.stream( CalculationMethod.values() ).forEach( CalculationMethod::resetAccessFlag );
    }

    public String getMethodName(){
        return methodName;
    }

    public HealthMetric getHealthMetric(){
        return healthMetric;
    }

    public boolean isVisited(){
        return visited;
    }

    public void markAsVisited(){
        this.visited = true;
    }

    public void resetAccessFlag(){
        this.visited = false;
    }
}