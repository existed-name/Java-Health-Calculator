package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.util.validator.BasicBodyParameterValidator;

/**
 * BRI( Body Roundness Index, 身体圆度指数 )计算器工具类, 提供 BRI 的计算公式
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateTime 2025/8/28 7:32
 */
public final class BRICalculator {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private BRICalculator(){ }


    // ==================== 公有方法 ====================
    /**
     * BRI 计算公式, <B>单位: 无</B>
     * <pre>
     *     BRI = 364.2 - 365.5 * 二次根号下( 1 - 分式² )
     *     分式 = ( 腰围 / 2PI ) / ( 0.5 * 身高 ) => 腰围 / ( PI * 身高 )
     * </pre>
     *
     * @param waistCircumference 腰围, <B>单位: cm</B>
     * @param height 身高, <B>单位: cm</B>
     *
     * @return BRI, <B>单位: 无</B>
     * @throws IllegalArgumentException 当 waistCircumference 或 height 不在有效范围时
     *
     * @see <a href="https://bri-calculator.net/zh/"> 参考直观的 BRI 计算公式 </a>
     */
    public static double calculateBRI( double waistCircumference, double height ){
        validateBasicParameters( waistCircumference, height );
        double fraction = waistCircumference / Math.PI / height; // PI = Math.PI = Math.acos( -1 )
        return (  364.2 - 365.5 * Math.sqrt( 1 - Math.pow( fraction, 2 ) )  );
    }



    // ==================== 私有辅助方法 ====================
    /**
     * 检验身体参数
     *
     * @param waistCircumference 腰围, <B>单位: cm</B>
     * @param height 身高, <B>单位: cm</B>
     *
     * @throws IllegalArgumentException 当 waistCircumference 或 height 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( double waistCircumference, double height ){
        BasicBodyParameterValidator.validateWaistCircumference( waistCircumference );
        BasicBodyParameterValidator.validateHeight( height );
    }
}
