package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.util.convertor.UnitConvertor;
import com.github.existedname.healthcalculatorv3.util.validator.BasicBodyParameterValidator;
import com.github.existedname.healthcalculatorv3.util.validator.HealthMetricValidator;

/**
 * BMI( Body Mass Index, 身体质量指数 )计算器工具类, 提供 BMI、已知 BMI 以及体重或身高求另一方的计算
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateteTime 2025/8/27 19:34
 */
public final class BMICalculator {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private BMICalculator() { }


    // ==================== 公有方法 ====================
    /**
     * 计算 BMI, 单位: kg/m²
     * <pre>
     *     BMI = weight( kg ) / height( m )²
     * </pre>
     *
     * @param weight 体重( 单位: kg )
     * @param height 身高( <B>单位: cm</B>, 由内部调用 {@link UnitConvertor#cmToMetre( double cm ) } 转换为 m )
     *
     * @return BMI 计算结果, 单位: kg/m²
     * @throws IllegalArgumentException 当 weight 或 height 不在有效范围时
     */
    public static double calculateBMI( double weight, double height ){
        validateBasicParameters( weight, height );
        double heightInMetre = UnitConvertor.cmToMetre( height );
        return ( weight / heightInMetre / heightInMetre );
    }

    /**
     * 用 BMI、身高 倒推 体重
     * <pre>
     *     BMI = W(kg) / H(m)² => W = BMI * H²
     * </pre>
     *
     * @param bmi 身体质量指数( BMI ), 单位: kg/m²
     * @param height 身高, <B>单位: cm</B>, 由内部调用 {@link UnitConvertor#cmToMetre(double) } 转换为 m
     *
     * @return 体重, 单位: kg
     * @throws IllegalArgumentException 当 bmi 或 height 不在有效范围时
     */
    public static double calculateWeightByBMI( double bmi, double height ){
        HealthMetricValidator.validateBMI( bmi );
        BasicBodyParameterValidator.validateHeight( height );
        double heightInMetre = UnitConvertor.cmToMetre( height ); // 注意换单位
        return bmi * heightInMetre * heightInMetre;
    }

    /**
     * 用 BMI、体重 倒推 身高
     * <pre>
     *     BMI = W(kg) / H(m)² => H = ( W / BMI )^( 1/2 )
     * </pre>
     *
     * @param bmi 身体质量指数 BMI, 单位: kg/m²
     * @param weight 体重, 单位: kg
     *
     * @return 身高, <B>单位: cm</B>
     * @throws IllegalArgumentException 当 bmi 或 weight 不在有效范围时
     */
    public static double calculateHeightByBMI( double bmi, double weight ){
        HealthMetricValidator.validateBMI( bmi );
        BasicBodyParameterValidator.validateWeight( weight );
        double heightInMetre = Math.sqrt( weight / bmi );
        return UnitConvertor.metreToCm( heightInMetre ); // 注意换单位
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 检验方法参数
     *
     * @param weight 体重, 单位: kg
     * @param height 身高, 单位: cm
     *
     * @throws IllegalArgumentException 当 weight 或 height 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( double weight, double height ){
        BasicBodyParameterValidator.validateWeight( weight );
        BasicBodyParameterValidator.validateHeight( height );
    }
}