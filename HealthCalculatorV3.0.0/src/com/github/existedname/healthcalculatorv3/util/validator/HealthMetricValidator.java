package com.github.existedname.healthcalculatorv3.util.validator;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;

import java.util.Objects;

/**
 * 健康指标检验器, 封装对健康指标( {@link HealthMetric } )的检验方法
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/26 14:18
 */
public final class HealthMetricValidator {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================


    // ==================== 公有方法 ====================
    /*      体态评估类健康指标       */
    /**
     * 检验 BMI( Body Mass Index )是否合法
     *
     * @param bmi 身体质量指数, <B>单位: kg/m²</B>
     * @throws IllegalArgumentException 当 bmi 不在有效范围时
     */
    public static void validateBMI( double bmi ){
        validateHealthMetric( HealthMetric.BMI, bmi );
    }

    /**
     * 检验 WHR( Waist-to-Hip Ratio )是否合法
     *
     * @param whr 腰臀比, <B>单位: 无</B>
     * @throws IllegalArgumentException 当 whr 不在有效范围时
     */
    public static void validateWHR( double whr ){
        validateHealthMetric( HealthMetric.WHR, whr );
    }

    /**
     * 检验 BFR( Body Fat Rate )是否合法
     *
     * @param bfr 体脂率, <B>单位: %</B>
     * @throws IllegalArgumentException 当 bfr 不在有效范围时
     */
    public static void validateBFR( double bfr ){
        validateHealthMetric( HealthMetric.BFR, bfr );
    }

    /**
     * 检验 BRI( Body Roundness Index )是否合法
     *
     * @param bri 身体圆度指数, <B>单位: 无</B>
     * @throws IllegalArgumentException 当 bri 不在有效范围时
     */
    public static void validateBRI( double bri ){
        validateHealthMetric( HealthMetric.BRI, bri );
    }


    /*      能量代谢类健康指标       */
    /**
     * 检验 BMR( Basal Metabolic Rate )是否合法
     *
     * @param bmr 基础代谢率, <B>单位: Kcal</B>
     * @throws IllegalArgumentException 当 bmr 不在有效范围时
     */
    public static void validateBMR( double bmr ){
        validateHealthMetric( HealthMetric.BMR, bmr );
    }

    /**
     * 检验 TDEE( Total Daily Energy Expenditure )是否合法
     *
     * @param tdee 每日总能量消耗, <B>单位: Kcal</B>
     * @throws IllegalArgumentException 当 tdee 不在有效范围时
     */
    public static void validateTDEE( double tdee ){
        validateHealthMetric( HealthMetric.TDEE, tdee );
    }


    /*      生理特征类健康指标       */
    /**
     * 检验 BSA( Body Surface Area )是否合法
     *
     * @param bsa 体表面积, <B>单位: m²</B>
     * @throws IllegalArgumentException 当 bsa 不在有效范围时
     */
    public static void validateBSA( double bsa ){
        validateHealthMetric( HealthMetric.BSA, bsa );
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 检验健康指标( {@link HealthMetric } )的数值是否合法
     *
     * @param healthMetric 健康指标枚举成员
     * @param value 健康指标的数值
     *
     * @throws NullPointerException 当 healthMetric 为 null 时
     * @throws IllegalArgumentException 当 value 不在有效范围时
     */
    private static void validateHealthMetric( HealthMetric healthMetric, double value ){
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );
        if( ! healthMetric.isValid( value ) ){
            throw new IllegalArgumentException(
                    String.format( "参数 %s 不在有效范围%s%s", healthMetric.getAbbreviation(), healthMetric.getFormattedClosedRange(), healthMetric.getUnit() )
            );
        }
    }

}
