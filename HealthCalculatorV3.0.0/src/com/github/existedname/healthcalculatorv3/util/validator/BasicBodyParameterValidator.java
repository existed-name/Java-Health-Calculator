package com.github.existedname.healthcalculatorv3.util.validator;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;

import java.util.Objects;

/**
 * 基本身体参数检验器, 封装对基本身体参数( {@link BasicBodyParameter } )的检验方法
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/26 14:18
 */
public final class BasicBodyParameterValidator {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private BasicBodyParameterValidator() { }

    // ==================== 公有方法 ====================
    /*      基本身体参数      */
    /**
     * 检验性别是否合法
     *
     * @param gender 性别字符串
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 不在有效范围时
     */
    public static void validateGender( String gender ){
        Objects.requireNonNull( gender, "参数 gender( 性别 )不能为 null" );
        if ( ! Gender.isValidGender( gender ) ){
            throw new IllegalArgumentException(
                   String.format( "参数 gender 不在有效范围%s", Gender.getValidGenderOptions() )
            );
        }
    }

    /**
     * 检验年龄是否合法
     *
     * @param age 年龄<B>单位: 岁/年</B>
     * @throws IllegalArgumentException 当 age 不在有效范围时
     */
    public static void validateAge( int age ){
        if ( ! BasicBodyParameter.AGE.isValid( age ) ){
            throw new IllegalArgumentException(
                   String.format( "参数 age( 年龄 )不在有效范围%s", BasicBodyParameter.AGE.getFormattedClosedRange() )
            );
        }
    }

    /**
     * 检验体重是否合法
     *
     * @param weight 体重, <B>单位: kg</B>
     * @throws IllegalArgumentException 当 weight 不在有效范围时
     */
    public static void validateWeight( double weight ){
        validateBasicBodyParameter( BasicBodyParameter.WEIGHT, weight );
    }

    /**
     * 检验身高是否合法
     *
     * @param height 体重, <B>单位: cm</B>
     * @throws IllegalArgumentException 当 height 不在有效范围时
     */
    public static void validateHeight( double height ){
        validateBasicBodyParameter( BasicBodyParameter.HEIGHT, height );
    }

    /**
     * 检验腰围是否合法
     *
     * @param waistCircumference 体重, <B>单位: cm</B>
     * @throws IllegalArgumentException 当 waistCircumference 不在有效范围时
     */
    public static void validateWaistCircumference( double waistCircumference ){
        validateBasicBodyParameter( BasicBodyParameter.WAIST_CIRCUMFERENCE, waistCircumference );
    }

    /**
     * 检验臀围是否合法
     *
     * @param hipCircumference 臀围, <B>单位: cm</B>
     * @throws IllegalArgumentException 当 hipCircumference 不在有效范围时
     */
    public static void validateHipCircumference( double hipCircumference ){
        validateBasicBodyParameter( BasicBodyParameter.HIP_CIRCUMFERENCE, hipCircumference );
    }

    /**
     * 检验颈围是否合法
     *
     * @param neckCircumference 颈围, <B>单位: cm</B>
     * @throws IllegalArgumentException 当 neckCircumference 不在有效范围时
     */
    public static void validateNeckCircumference( double neckCircumference ){
        validateBasicBodyParameter( BasicBodyParameter.NECK_CIRCUMFERENCE, neckCircumference );
    }

    /**
     * 检验臂围是否合法
     *
     * @param armCircumference 臂围, <B>单位: cm</B>
     * @throws IllegalArgumentException 当 armCircumference 不在有效范围时
     */
    public static void validateArmCircumference( double armCircumference ){
        validateBasicBodyParameter( BasicBodyParameter.ARM_CIRCUMFERENCE, armCircumference );
    }

    /**
     * 检验活动系数是否合法
     *
     * @param activityCoefficient 活动系数, <B>单位: 无</B>
     * @throws IllegalArgumentException 当 activityCoefficient 不在有效范围时
     */
    public static void validateActivityCoefficient( double activityCoefficient ){
        validateBasicBodyParameter( BasicBodyParameter.ACTIVITY_COEFFICIENT, activityCoefficient );
    }



    // ==================== 私有辅助方法 ====================
    /**
     * 检验基本身体参数( {@link BasicBodyParameter } )的数值是否合法
     *
     * @param basicBodyParameter 基本身体参数枚举成员
     * @param value 基本身体参数的数值
     *
     * @throws NullPointerException 当 basicBodyParameter 为 null 时
     * @throws IllegalArgumentException 当 value 不在有效范围时
     */
    private static void validateBasicBodyParameter( BasicBodyParameter basicBodyParameter, double value ){
        Objects.requireNonNull( basicBodyParameter, "参数 basicBodyParameter( 基本身体参数枚举成员 )不能为 null" );
        if( ! basicBodyParameter.isValid( value ) ){
            throw new IllegalArgumentException(
                    String.format( "参数 %s 不在有效范围%s%s", basicBodyParameter.getAbbreviation(), basicBodyParameter.getFormattedClosedRange(), basicBodyParameter.getUnit() )
            );
        }
    }


}
