package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.util.calculator.constant.BSACalculatorConstants;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.util.convertor.UnitConvertor;
import com.github.existedname.healthcalculatorv3.util.validator.BasicBodyParameterValidator;

import java.util.Objects;

/**
 * BSA( Body Surface Area, 体表面积 )计算器工具类, 提供多种 BMR 的计算公式
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateTime 2025/8/28 7:46
 */
public final class BSACalculator {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private BSACalculator(){ }


    // ==================== 公有方法 ====================
    /*
     *          Du Bois Formula 杜博伊斯公式( 最经典、应用最广 )
     *  1. 原理: 基于身高、体重的幂函数拟合,是医学领域最经典、应用最广的公式,1916 年提出后长期作为标准
     *  2. 注意事项:
     *   (1)对成年人适配性最佳,儿童 / 特殊体型(如极端肥胖)误差稍大
     *   (2)需严格用 "厘米(身高)、千克(体重)" 单位,否则结果错误
     *  3. 使用建议:
     *   (1)临床药物剂量计算(如化疗药)、基础代谢率(BMR)估算的首选公式
     *   (2)科研数据统计、跨研究对比时,因 "经典性" 优先选它保证一致性
     */
    /**
     * 计算 BSA 的 Du Bois 公式, <B>单位: m²</B>
     * <pre>
     *     BSA = 0.007184 * Height^0.725 * Weight^0.425
     * </pre>
     *
     * @param height 身高, 单位: cm
     * @param weight 体重, 单位: kg
     *
     * @return BSA, <B>单位: m²</B>
     * @throws IllegalArgumentException 当 height 或 weight 不在有效范围时
     *
     * @see <a href="https://www.23bei.com/tool/298.html"> 参考 "12）DuBois 公式" </a>
     */
    public static double calculateBSAByDuBoisEquation( double height, double weight ){
        validateBasicParameters( height, weight );
        return calculateBSAByGeneralFormula(
                BSACalculatorConstants.DuBoisEquation.POWER_FUNCTION_COEFFICIENT,
                height, BSACalculatorConstants.DuBoisEquation.HEIGHT_EXPONENT,
                weight, BSACalculatorConstants.DuBoisEquation.WEIGHT_EXPONENT );
    }

    /*
     *          Haycock Formula 海科克公式( 儿童 / 青少年医疗 )
     *  1. 原理: 针对儿童群体优化,拟合儿科患者身高 - 体重 - 体表面积关系,更适配成长发育阶段的代谢特点
     *  2. 注意事项:
     *   (1)仅推荐 2-18 岁儿童 / 青少年,成年人用此公式误差大
     *   (2)需准确记录年龄(判断是否适用),且体重需排除 "儿童肥胖" 导致的极端值
     *  3. 使用建议:
     *   (1)儿科临床(如儿童化疗、营养支持)、儿童生长发育研究
     *   (2)配合儿童身高体重百分位曲线,综合评估体表面积与发育水平的关系
     */
    /**
     * 计算 BSA 的 Haycock 公式, <B>单位: m²</B>
     * <pre>
     *     BSA = 0.024265 * Height^0.3964 * Weight^0.5378
     * </pre>
     *
     * @param height 身高, 单位: cm
     * @param weight 体重, 单位: kg
     *
     * @return BSA, <B>单位: m²</B>
     * @throws IllegalArgumentException 当 height 或 weight 不在有效范围时
     *
     * @see <a href="https://www.23bei.com/tool/298.html"> 参考 "3）Haycock公式" </a>
     */
    public static double calculateBSAByHaycockEquation( double height, double weight ){
        validateBasicParameters( height, weight );
        return calculateBSAByGeneralFormula(
                BSACalculatorConstants.HaycockEquation.POWER_FUNCTION_COEFFICIENT,
                height, BSACalculatorConstants.HaycockEquation.HEIGHT_EXPONENT,
                weight, BSACalculatorConstants.HaycockEquation.WEIGHT_EXPONENT );
    }

    /*
     *          Mosteller Formula 莫斯特勒公式( 日常快速手动计算 )
     *  1. 原理: 简化版幂函数,将复杂指数运算转为开平方,更易手动计算
     *  2. 注意事项:
     *   (1)精度略低于杜博伊斯公式(误差约 ±5%),但日常估算足够
     *   (2)同样依赖身高、体重的准确测量,极端值(过高 / 过矮、过胖 / 过瘦)误差放大
     *  3. 使用建议:
     *   (1)快速估算场景(如急诊初步给药、健身人群日常参考)
     *   (2)配合体脂秤、健康 APP 使用,输入身高体重自动计算时,很多默认用此公式
     */
    /**
     * 计算 BSA 的 Mosteller 公式, <B>单位: m²</B>
     * <pre>
     *     BSA = 二次根号下[ ( 身高 * 体重 ) / 3600 ]
     *          => 1/60 * Height^0.5 * Weight^0.5
     * </pre>
     *
     * @param height 身高, 单位: cm
     * @param weight 体重, 单位: kg
     *
     * @return BSA, <B>单位: m²</B>
     * @throws IllegalArgumentException 当 height 或 weight 不在有效范围时
     *
     * @see <a href="https://www.23bei.com/tool/298.html"> 参考 "1）Mosteller 公式" </a>
     */
    public static double calculateBSAByMostellerEquation( double height, double weight ){
        validateBasicParameters( height, weight );
        return calculateBSAByGeneralFormula(
                BSACalculatorConstants.MostellerEquation.POWER_FUNCTION_COEFFICIENT,
                height, BSACalculatorConstants.MostellerEquation.HEIGHT_EXPONENT,
                weight, BSACalculatorConstants.MostellerEquation.WEIGHT_EXPONENT );
    }

    /*
     *          Schlich Formula 施利希公式( 区分性别 )
     *  1. 原理: 区分性别拟合,考虑男女身体脂肪、肌肉分布差异对体表面积的影响
     *  2. 注意事项:
     *   (1)性别判定需准确(生理性别),否则系数错配导致误差
     *   (2)公式复杂度高,手动计算易出错,建议用工具 / APP 辅助
     *  3. 使用建议:
     *   (1)性别差异对结果影响显著的场景(如激素治疗、性别相关代谢研究)
     *   (2)科研中需精细区分性别因素时,搭配杜博伊斯公式对比验证
     */
    /**
     * 计算 BSA 的 Schlich 公式, <B>单位: m²</B>
     * <pre>
     *     <b>注意</b>: 该公式计算结果与其他公式相差太大( 本人误差 1m² ), 谨慎看待
     *     女: BSA = 0.000975482 * Height^1.08 * Weight^0.46
     *     男: BSA = 0.000579479 * Height^1.24 * Weight^0.38
     * </pre>
     *
     * @param gender 性别
     * @param height 身高, 单位: cm
     * @param weight 体重, 单位: kg
     *
     * @return BSA, <B>单位: m²</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 height 或 weight 不在有效范围时
     *
     * @see <a href="https://www.bchrt.com/tools/bsa-calculator/"> 参考"BSA体表面积计算器" </a>
     */
    public static double calculateBSABySchlichEquation( String gender, double height, double weight ){
        validateBasicParameters( gender, height, weight );
        if ( Gender.isMale( gender ) ){
            return calculateBSAByGeneralFormula(
                    BSACalculatorConstants.SchlichEquation.Male.POWER_FUNCTION_COEFFICIENT,
                    height, BSACalculatorConstants.SchlichEquation.Male.HEIGHT_EXPONENT,
                    weight, BSACalculatorConstants.SchlichEquation.Male.WEIGHT_EXPONENT );
        } else {
            return calculateBSAByGeneralFormula(
                    BSACalculatorConstants.SchlichEquation.Female.POWER_FUNCTION_COEFFICIENT,
                    height, BSACalculatorConstants.SchlichEquation.Female.HEIGHT_EXPONENT,
                    weight, BSACalculatorConstants.SchlichEquation.Female.WEIGHT_EXPONENT );
        }
    }



    // ==================== 私有辅助方法 ====================
    /**
     * BSA 计算通式--符合多数公式的形式,只需传入参数( 自变量、系数 )即可
     * <pre>
     *     BSA( <B>单位: m²</B> ) = 系数 * 身高的指数次方 * 体重的指数次方
     * </pre>
     *
     * @param powerFunctionCoefficient 幂函数系数
     * @param height 身高, <B>单位: cm</B>
     * @param heightExponent 身高对应的指数
     * @param weight 体重, <B>单位: kg</B>
     * @param weightExponent 体重对应的指数
     *
     * @return 体表面积 BSA, <B>单位: m²</B>
     * @throws IllegalArgumentException 当 height 或 weight 不在有效范围时
     */
    private static double calculateBSAByGeneralFormula( double powerFunctionCoefficient, double height, double heightExponent, double weight, double weightExponent ){
        validateBasicParameters( height, weight );
        return powerFunctionCoefficient * Math.pow( height, heightExponent ) * Math.pow( weight, weightExponent );
    }

    /**
     * 检验方法参数
     *
     * @param height 身高, <B>单位: cm</B>
     * @param weight 体重, <B>单位: kg</B>
     *
     * @throws IllegalArgumentException 当 height 或 weight 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( double height, double weight ){
        BasicBodyParameterValidator.validateHeight( height );
        BasicBodyParameterValidator.validateWeight( weight );
    }

    /**
     * 检验方法参数
     *
     * @param gender 性别
     * @param height 身高, <B>单位: cm</B>
     * @param weight 体重, <B>单位: kg</B>
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 height 或 weight 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( String gender, double height, double weight ){
        BasicBodyParameterValidator.validateGender( gender );
        validateBasicParameters( height, weight );
    }
}