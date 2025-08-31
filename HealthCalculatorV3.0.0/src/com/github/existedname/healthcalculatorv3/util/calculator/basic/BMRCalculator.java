package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.util.calculator.constant.BMRCalculatorConstants;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.util.convertor.UnitConvertor;
import com.github.existedname.healthcalculatorv3.util.validator.BasicBodyParameterValidator;
import com.github.existedname.healthcalculatorv3.util.validator.HealthMetricValidator;

/**
 *  BMR( Basal Metabolic Rate, 基础代谢率 )计算器工具类, 提供多种 BMR 的计算公式
 *
 *  @author <a href="https://github.com/existed-name" > existed-name </a>
 *  @since 2.0.0
 *  @CreateTime 2025-6-16
 */
public final class BMRCalculator {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private BMRCalculator(){ }


    // ==================== 公有方法 ====================
    /**
     * 基于 BSA( Body Surface Area, 体表面积 ) 的 BMR 计算方法
     * <pre>
     *     一段时间的基础代谢 = 每小时每平方米体表面积的基础代谢率( kcal/( hour * m² ) ) * 体表面积 * 该段时间
     *     => 每日 BMR = 每小时 BMR * BSA * 24hours
     *
     *     其中每小时 BMR 可由性别、年龄估计
     * </pre>
     *
     * @param gender 性别字符串
     * @param age 年龄
     * @param bsa 体表面积, 单位: m²
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 age 或 bsa 不在有效范围时
     *
     * @see <a href="https://zhuanlan.zhihu.com/p/26465848"> 参考年龄、性别--每平方米体表面积基础代谢率对照表 </a>
     */
    public static double calculateBSABasedBMR( String gender, int age, double bsa ){
        BasicBodyParameterValidator.validateAge( age );
        BasicBodyParameterValidator.validateAge( age );
        HealthMetricValidator.validateBSA( bsa );
        double hourlyBMR = BMRCalculatorConstants.BSABasedBMR.getHourlyBMR( gender, age );
        double dailyBMR = hourlyBMR * bsa * BMRCalculatorConstants.BSABasedBMR.HOURS;
        return dailyBMR;
    }

    /**
     * 计算 BMR 的 Henry 公式, <B>单位: KJ</B>
     * <p>
     *     适用于 18-60 岁,对中国女性个体和北方个体误差更小,对青少年、老年人误差较大
     * </p>
     * <pre>
     *     女: BMR = 48Weight + 2562( 18-30 岁), 48Weight + 2448( 30-60 岁)
     *     男: BMR = 56Weight + 2800( 18-30 岁), 46Weight + 3160( 30-60 岁)
     *     计算结果将使用 {@link UnitConvertor#jouleToCalorie( double joule ) } 转为 Kcal
     * </pre>
     *
     * @param gender 性别字符串
     * @param weight 体重, 单位: kg
     * @param age 年龄
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 或 age 不在有效范围时
     *
     * @see <a href="https://metaso.cn/s/j9nt7Nv"> 参考 P3 表1 的 Henry and Rees 公式 </a>
     * @see <a href="https://www.sohu.com/a/221960937_678883"> 备用公式参考 </a>
     */
    public static double calculateBMRByHenryEquation( String gender, double weight, int age ) {
        validateBasicParameters( gender, weight, age );
        double weightCoefficient = BMRCalculatorConstants.HenryEquation.getWeightCoefficient( gender, age ); // 单位: KJ
        double constTerm = BMRCalculatorConstants.HenryEquation.getConstTerm( gender, age ); // 单位: KJ
        return UnitConvertor.jouleToCalorie( // KJ → Kcal
                calculateBMRByGeneralFormula(
                        weightCoefficient, weight,
                        BMRCalculatorConstants.ZeroConstants.HEIGHT_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.HEIGHT_VALUE_ZERO,
                        BMRCalculatorConstants.ZeroConstants.AGE_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.AGE_VALUE_ZERO,
                        constTerm )
        );
    }

    /**
     * 计算 BMR 的 Harris-Benedict( H-B ) 公式, <B>单位: KJ</B><br>
     * 适用于 18-60 岁,对老年人误差较大
     * <pre>
     *     女: BMR = 9.5634 * Weight + 1.8496 * Height - 4.6756 * Age + 655.0955
     *     男: BMR = 13.7516 * Weight + 5.0033 * Height - 6.7550 * Age + 66.4730
     *     计算结果将使用 {@link UnitConvertor#jouleToCalorie( double joule ) } 转为 Kcal
     * </pre>
     *
     * @param gender 性别字符串
     * @param weight 体重, 单位: kg
     * @param age 年龄
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 或 height 或 age 不在有效范围时
     *
     * @see <a href="https://metaso.cn/s/hwCau6P"> 参考 P3 表1 的 Henry and Rees 公式 </a>
     * @see <a href="https://www.sohu.com/a/221960937_678883"> 备用公式参考 </a>
     */
    public static double calculateBMRByHBEquation(String gender, double weight, double height, int age) {
        validateBasicParameters( gender, weight, height, age );
        // 单位: Kcal
        if ( Gender.isMale( gender ) ){
            return calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.HBEquation.Male.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.HBEquation.Male.HEIGHT_COEFFICIENT, height,
                    BMRCalculatorConstants.HBEquation.Male.AGE_COEFFICIENT, age,
                    BMRCalculatorConstants.HBEquation.Male.CONST_TERM
            );
        } else {
            return calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.HBEquation.Female.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.HBEquation.Female.HEIGHT_COEFFICIENT, height,
                    BMRCalculatorConstants.HBEquation.Female.AGE_COEFFICIENT, age,
                    BMRCalculatorConstants.HBEquation.Female.CONST_TERM
            );
        }
    }
    
    /**
     * 计算 BMR 的 Katch-McArdle 公式, <B>单位: Kcal</B><br>
     * 需结合身体成分数据, 适用于肌肉量异常者( 运动员、老年人、肥胖/消瘦者 )
     * <pre>
     *     BMR = 370 + 21.6 * FFM( Fat-Free Mass, 去脂体重 )<br>
     *     既然是去脂体重, 也就是去掉脂肪 = 体重 - 脂肪 = Weight( 1 - 体脂率 BFR )
     *     => FFM = Weight( 1 - BFR )
     * </pre>
     *
     * @param bfr 体脂率 BFR, <B>单位: %</B>, 由内部调用 {@link UnitConvertor#percentageToDecimal( double percentage ) } 转为小数
     * @param weight 体重, 单位: kg
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws IllegalArgumentException 当 bfr 或 weight 不在有效范围时
     *
     * @see <a href="https://www.sohu.com/a/221960937_678883"> 参考 6、Karch-McArdle 公式 </a>
     * @see <a href="https://zhuanlan.zhihu.com/p/76241592"> 参考 FFM 计算公式 </a>
     * @see <a href="https://metaso.cn/s/QX7SQwF"> 备用公式参考( P4 表1 ) </a>
     */
    public static double calculateBMRByKatchMcArdleEquation( double bfr, double weight ){
        HealthMetricValidator.validateBFR( bfr );
        BasicBodyParameterValidator.validateWeight( weight );
        double ffm = weight * ( 1 - UnitConvertor.percentageToDecimal( bfr ) );
        return 370 + 21.6 * ffm;
    }

    /**
     * 计算 BMR 的 Mifflin-St Jeor( MSJ ) 公式, <B>单位: kcal</B><br>
     * 适用于所有成人( ≥18岁 ),误差最小,被 ADA 和 ASCN 推荐
     * <pre>
     *     女: BMR = 9.99 * Weight + 6.25 * Height - 4.92 * Age - 161
     *     男: BMR = 9.99 * Weight + 6.25 * Height - 4.92 * Age + 5
     * </pre>
     *
     * @param gender 性别字符串
     * @param weight 体重, 单位: kg
     * @param height 身高, 单位: cm
     * @param age 年龄
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 或 height 或 age 不在有效范围时
     *
     * @see <a href="https://www.sohu.com/a/221960937_678883"> 参考 5、Mifflin-St Jeor Equations </a> ( 系数比常见版本更精确 )
     */
    public static double calculateBMRByMSJEquation( String gender, double weight, double height, int age ){
        validateBasicParameters( gender, weight, height, age );
        if ( Gender.isMale( gender ) ){
            return calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.MSJEquation.Male.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.MSJEquation.Male.HEIGHT_COEFFICIENT, height,
                    BMRCalculatorConstants.MSJEquation.Male.AGE_COEFFICIENT, age,
                    BMRCalculatorConstants.MSJEquation.Male.CONST_TERM
            );
        } else {
            return calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.MSJEquation.Female.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.MSJEquation.Female.HEIGHT_COEFFICIENT, height,
                    BMRCalculatorConstants.MSJEquation.Female.AGE_COEFFICIENT, age,
                    BMRCalculatorConstants.MSJEquation.Female.CONST_TERM
            );
        }
    }

    /**
     * 计算 BMR 的 毛德倩 公式, <B>单位: KJ</B><br>
     *     适用于20-45岁中国人群,误差较小
     * <pre>
     *     女: BMR = 41.9 * Weight + 2869.1
     *     男: BMR = 48.5 * Weight + 2954.7
     *     计算结果将使用 {@link UnitConvertor#jouleToCalorie( double joule ) } 转为 Kcal
     * </pre>
     *
     * @param gender 性别字符串
     * @param weight 体重, 单位: kg
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 不在有效范围时
     *
     * @see <a href="https://www.zhihu.com/question/551650901/answer/2662411450"> 参考 毛德倩 公式 </a>
     */
    public static double calculateBMRByMaoEquation( String gender, double weight ){
        validateBasicParameters( gender, weight );
        // 原单位: KJ
        if ( Gender.isMale( gender ) ){
            return UnitConvertor.jouleToCalorie( calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.MaoEquation.Male.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.ZeroConstants.HEIGHT_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.HEIGHT_VALUE_ZERO,
                    BMRCalculatorConstants.ZeroConstants.AGE_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.AGE_VALUE_ZERO,
                    BMRCalculatorConstants.MaoEquation.Male.CONST_TERM )
            );
        } else {
            return UnitConvertor.jouleToCalorie( calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.MaoEquation.Female.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.ZeroConstants.HEIGHT_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.HEIGHT_VALUE_ZERO,
                    BMRCalculatorConstants.ZeroConstants.AGE_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.AGE_VALUE_ZERO,
                    BMRCalculatorConstants.MaoEquation.Female.CONST_TERM )
            );
        }
    }

    /**
     * 计算 BMR 的 Schofield 公式, <B>单位: Kcal</B><br>
     * 推荐: 18-60岁人群, 尤其适合中国健康成人; 修订版( WHO暂用标准 )提供儿童青少年专用公式
     * <pre>
     *     公式太多, 只展示常用的 18-60 岁
     *     女: BMR = 14.8 * Weight + 487( 18-30 岁), 8.3 * Weight + 846( 30-60 岁)
     *     男: BMR = 15.1 * Weight + 692( 18-30 岁), 11.5 * Weight + 873( 30-60 岁)
     * </pre>
     *
     * @param gender 性别字符串
     * @param weight 体重, 单位: kg
     * @param age 年龄
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 或 age 不在有效范围时
     *
     * @see <a href="https://metaso.cn/s/vNaT2cH"> 参考 0-18 岁对应的公式( 见 P3 表 2 ) </a> 
     * @see <a href="https://metaso.cn/s/hGmPhbm"> 参考 18-75+ 岁对应的公式( 见 P38 ) </a> 
     * @see <a href="https://zhuanlan.zhihu.com/p/26465848"> 备用公式参考( 0-60+ 岁 ) </a>
     */
    public static double calculateBMRBySchofieldEquation(String gender, double weight, int age) {
        validateBasicParameters( gender, weight, age );
        // 单位: Kcal
        double weightCoefficient = BMRCalculatorConstants.SchofieldEquation.getWeightCoefficient( gender, age );
        double constTerm = BMRCalculatorConstants.SchofieldEquation.getConstantTerm( gender, age );
        return calculateBMRByGeneralFormula( weightCoefficient, weight,
                BMRCalculatorConstants.ZeroConstants.HEIGHT_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.HEIGHT_VALUE_ZERO,
                BMRCalculatorConstants.ZeroConstants.AGE_COEFFICIENT_NONE, BMRCalculatorConstants.ZeroConstants.AGE_VALUE_ZERO,
                constTerm );
    }

    /**
     * 计算 BMR 的 Shizgal-Rosa 公式, <B>单位: KJ</B>
     * <pre>
     *     女: BMR = 39 * Weight + 13 * Height - 18 * Age + 1873
     *     男: BMR = 52 * Weight + 20 * Height - 25 * Age + 370
     *     计算结果将使用 {@link UnitConvertor#jouleToCalorie( double joule ) } 转为 Kcal
     * </pre>
     *
     * @param gender 性别字符串
     * @param weight 体重, 单位: kg
     * @param height 身高, 单位: cm
     * @param age 年龄
     *
     * @return BMR, <B>单位: kcal</B>
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 或 height 或 age 不在有效范围时
     *
     * @see <a href="https://www.sohu.com/a/221960937_678883"> 参考 9、Shizgal -Rosa Equation </a>
     */
    public static double calculateBMRByShizgalRosaEquation( String gender, double weight, double height, int age ){
        validateBasicParameters( gender, weight, height, age );
        // 原单位: KJ
        if ( Gender.isMale( gender ) ){
            return UnitConvertor.jouleToCalorie( calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.ShizgalRosaEquation.Male.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.ShizgalRosaEquation.Male.HEIGHT_COEFFICIENT, height,
                    BMRCalculatorConstants.ShizgalRosaEquation.Male.AGE_COEFFICIENT, age,
                    BMRCalculatorConstants.ShizgalRosaEquation.Male.CONST_TERM )
            );
        } else {
            return UnitConvertor.jouleToCalorie( calculateBMRByGeneralFormula(
                    BMRCalculatorConstants.ShizgalRosaEquation.Female.WEIGHT_COEFFICIENT, weight,
                    BMRCalculatorConstants.ShizgalRosaEquation.Female.HEIGHT_COEFFICIENT, height,
                    BMRCalculatorConstants.ShizgalRosaEquation.Female.AGE_COEFFICIENT, age,
                    BMRCalculatorConstants.ShizgalRosaEquation.Female.CONST_TERM )
            );
        }
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 检验身体参数
     *
     * @param gender 性别字符串
     * @param weight 体重, <B>单位: kg</B>
     * @param height 身高, <B>单位: cm</B>
     * @param age 年龄
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 或 height 或 age 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( String gender, double weight, double height, int age ){
        BasicBodyParameterValidator.validateHeight( height );
        validateBasicParameters( gender, weight, age );
    }

    /**
     * 检验身体参数
     *
     * @param gender 性别字符串
     * @param weight 体重, <B>单位: kg</B>
     * @param age 年龄
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 或 age 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( String gender, double weight, int age ){
        validateBasicParameters( gender, weight );
        BasicBodyParameterValidator.validateAge( age );
    }

    /**
     * 检验身体参数
     *
     * @param gender 性别字符串
     * @param weight 体重, <B>单位: kg</B>
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 weight 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( String gender, double weight ){
        BasicBodyParameterValidator.validateGender( gender );
        BasicBodyParameterValidator.validateWeight( weight );
    }

    /**
     * BMR 计算通式--符合多数公式的形式, 只需传入参数( 自变量、系数 )即可, 无非就是某些项可能为 0( 不存在 )
     * <pre>
     *     体重系数 * 体重(kg) + 身高系数 * 身高(cm) + 年龄系数 * 年龄(岁) + 常数项
     * </pre>
     * <p>
     *     <B>由于某些项可能为不存在 => 参数可能为 0, 所以此处不对参数进行检查</B>
     * </p>
     *
     * @param weightCoefficient 体重系数
     * @param weight 体重, 单位: kg
     * @param heightCoefficient 身高系数
     * @param height 身高, 单位: cm
     * @param ageCoefficient 年龄系数
     * @param age 年龄, 单位: 岁
     * @param constTerm 常数项
     * @return BMR, <B>单位: 调用方使用的公式的热量单位</B>
     */
    private static double calculateBMRByGeneralFormula( double weightCoefficient, double weight, double heightCoefficient, double height, double ageCoefficient, int age, double constTerm ){
        return weightCoefficient * weight + heightCoefficient * height + ageCoefficient * age + constTerm;
    }
}