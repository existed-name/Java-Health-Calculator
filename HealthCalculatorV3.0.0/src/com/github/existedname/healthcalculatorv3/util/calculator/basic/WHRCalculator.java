package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.util.validator.BasicBodyParameterValidator;

/**
 * WHR( Waist-to-Hip Ratio, 腰臀比 )计算器工具类, 提供 WHR 的计算公式
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateteTime 2025/8/28 10:27
 */
public final class WHRCalculator {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private WHRCalculator(){ }


    // ==================== 公有方法 ====================

    /**
     * 计算并返回腰臀比 WHR, <B>单位: 无</B>
     * <pre>
     *     WHR = waistCircumference / hipCircumference
     * </pre>
     *
     * @param wasitCircumference 腰围, <B>单位: cm</B>
     * @param hipCircumference 臀围, <B>单位: cm</B>
     *
     * @return 腰臀比 WHR, <B>单位: 无</B>
     * @throws IllegalArgumentException 当 waistCircumference 或 hipCircumference 不在有效范围时
     */
    public static double calculateWHR( double wasitCircumference, double hipCircumference ){
        validateBasicParameters( wasitCircumference, hipCircumference );
        return wasitCircumference / hipCircumference;
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 检查方法参数
     *
     * @param wasitCircumference 腰围, <B>单位: cm</B>
     * @param hipCircumference 臀围, <B>单位: cm</B>
     *
     * @throws IllegalArgumentException 当 waistCircumference 或 hipCircumference 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( double wasitCircumference, double hipCircumference ){
        BasicBodyParameterValidator.validateWaistCircumference( wasitCircumference );
        BasicBodyParameterValidator.validateHipCircumference( hipCircumference );
    }
}
