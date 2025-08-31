package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.util.calculator.constant.BFRCalculatorConstants;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.util.convertor.UnitConvertor;
import com.github.existedname.healthcalculatorv3.util.validator.BasicBodyParameterValidator;
import com.github.existedname.healthcalculatorv3.util.validator.HealthMetricValidator;

/**
 * BFR( Body Fat Rate, 体脂率 )计算器工具类, 提供多种 BFR 的计算公式
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateTime 2025/8/27 9:40
 */
public final class BFRCalculator {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private BFRCalculator(){ }


    // ==================== 公有方法 ====================
    /**
     * 使用 Deurenberg 公式计算 BFR, <B>单位: %</B>
     * <pre>
     *     成人( ≥ 16 岁 )BFR = 1.20 * BMI + 0.23 * age - 10.8 * gender( 女0 男1 ) - 5.4
     *     儿童( ≤ 15 岁 )BFR = 1.51 * BMI - 0.70 * age - 3.6 * gender( 女0 男1 ) + 1.4
     * </pre>
     * Deurenberg 公式( 常见的 BMI 体脂率公式 ), 适用于 7-83 岁 & BMI 13.9-40.9 kg/m², 误差范围约 ± 4.1%, 
     * 儿童公式需结合皮褶厚度验证
     *
     * @param bmi BMI 数值
     * @param age 年龄
     * @param gender 性别字符串
     * @return BFR 计算结果, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal( double percentage ) } 转小数
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 age 或 bmi 不在有效范围时
     *
     * @see <a href="https://metaso.cn/s/dMSlZGl"> 参考 Deurenberg 成人、儿童公式( 见 P1 摘要部分 ) </a>
     * @see <a href="https://metaso.cn/s/ZiSbsAj"> 参考成人、儿童年龄区分( 见 P4 表 5 下面第 1 行 ) </a>
     */
    public static double calculateBFRByDeurenbergEquation( double bmi, int age, String gender ){
        validateBasicParameters( bmi, age, gender );
        if ( age >= 16 ){
            return calculateBFRByGeneralFormula(
                    BFRCalculatorConstants.DeurenbergEquation.Adult.BMI_COEFFICIENT, bmi,
                    BFRCalculatorConstants.DeurenbergEquation.Adult.AGE_COEFFICIENT, age,
                    BFRCalculatorConstants.DeurenbergEquation.Adult.GENDER_COEFFICIENT, gender,
                    BFRCalculatorConstants.DeurenbergEquation.Adult.CONST_TERM );
        } else {
            return calculateBFRByGeneralFormula(
                    BFRCalculatorConstants.DeurenbergEquation.Child.BMI_COEFFICIENT, bmi,
                    BFRCalculatorConstants.DeurenbergEquation.Child.AGE_COEFFICIENT, age,
                    BFRCalculatorConstants.DeurenbergEquation.Child.GENDER_COEFFICIENT, gender,
                    BFRCalculatorConstants.DeurenbergEquation.Child.CONST_TERM );
        }
    }

    /*
     *              Gallagher 公式
     *  适用于 18-61岁,BMI 18-35 kg/m²,误差范围约±5.4%
     */
    /**
     * 使用 Gallagher 公式版本Ⅰ( 基于 BMI、年龄、性别 )计算 BFR, <B>单位: %</B>
     * <pre>
     *     BFR = 1.46 * BMI + 0.12 * age - 11.61 * gender( 女0 男1 ) - 10.02
     * </pre>
     *
     * @param bmi BMI 数值
     * @param age 年龄
     * @param gender 性别字符串
     * @return BFR 计算结果, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal( double percentage ) } 转小数
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 age 或 bmi 不在有效范围时
     *
     * @see <a href="https://metaso.cn/s/9YJwhkv"> 参考 Gallagher 公式版本Ⅰ( 见 P5 表4 ) </a>
     */
    public static double calculateBFRByGallagherEquation1( double bmi, int age, String gender ){
        validateBasicParameters( bmi, age, gender );
        return calculateBFRByGeneralFormula( 
                BFRCalculatorConstants.GallagherEquation.Version1.BMI_COEFFICIENT, bmi,
                BFRCalculatorConstants.GallagherEquation.Version1.AGE_COEFFICIENT, age,
                BFRCalculatorConstants.GallagherEquation.Version1.GENDER_COEFFICIENT, gender,
                BFRCalculatorConstants.GallagherEquation.Version1.CONST_TERM );
    }

    /**
     * 使用 Gallagher 公式版本Ⅱ( 基于 BMI 的倒数、年龄、性别 )计算 BFR, <B>单位: %</B>
     * <pre>
     *     BFR = ( -848 / BMI + 0.079 * age - 16.4 * gender + 64.5 ) + ( 0.05 * gender * age + 39 * gender / BMI )
     * </pre>
     *
     * @param bmi BMI 数值
     * @param age 年龄
     * @param gender 性别字符串
     * @return BFR 计算结果, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal( double percentage ) } 转小数
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 age 或 bmi 不在有效范围时
     *
     * @see <a href="https://metaso.cn/s/EXCtMXF"> 参考 Gallagher 公式版本Ⅱ( 见 P5 表4 ) </a>
     */
    public static double calculateBFRByGallagherEquation2( double bmi, int age, String gender ){
        validateBasicParameters( bmi, age, gender );
        int intIsMale = Gender.intIsMale( gender );
        double reciprocalOfBMI = 1.0 / bmi;
        return // calculateBFRByGeneralFormula
                (  // 前半部分: 形式同计算通式, 但是由于 BMI 倒数不在 BMI 有效范围内, 不能传入通式, 否则抛出异常
                BFRCalculatorConstants.GallagherEquation.Version2.BMI_COEFFICIENT * reciprocalOfBMI +
                BFRCalculatorConstants.GallagherEquation.Version2.AGE_COEFFICIENT * age +
                BFRCalculatorConstants.GallagherEquation.Version2.GENDER_COEFFICIENT * Gender.intIsMale( gender ) +
                BFRCalculatorConstants.GallagherEquation.Version2.CONST_TERM ) // 再补上后半部分
                + BFRCalculatorConstants.GallagherEquation.Version2.GENDER_AGE_INTERACTION * intIsMale * age
                + BFRCalculatorConstants.GallagherEquation.Version2.GENDER_BMI_INTERACTION * intIsMale * reciprocalOfBMI;
    }


    /**
     * 使用 Jackson-Pollock 公式( 基于 皮褶厚度 )计算 BFR, <B>单位: %</B>
     * <p>
     *     Jackson-Pollock 公式实际上是计算身体密度的公式, 还需要将得到的身体密度代入其他公式<br>
     *     ( 如 Siri 公式 )才能得到 BFR
     * </p>
     *
     * @return Not a Number
     *
     * @see <a href="https://metaso.cn/s/RMeg2Bp"> 参考计算身体密度的 Jackson-Pollock 公式 2 种版本( 见 P3 表1 ) </a>
     * @see <a href="https://metaso.cn/s/8pMfP82"> 参考使用身体密度计算体脂率的 Siri 公式( 见 P4 中间部分 ) </a>
     * @see  <a href="https://www.calculatorultra.com/zh/tool/jackson-pollock-equation-calculator.html"> 参考 Jackson-Pollock、Siri 公式及示例 </a>
     * @deprecated 由于要测多处皮褶厚度, 太麻烦, 暂时弃用, 以后可拓展
     */
    private static double calculateBFRByJacksonPollockEquation(){
        return Double.NaN;
    }

    /**
     * 使用 Jackson-Pollock 公式简化版本( 基于 BMI、年龄、性别 )计算 BFR, <B>单位: %</B>
     * <pre>
     *     BFR = 1.61 * BMI + 0.13 * age - 12.1 * gender - 13.9
     * </pre>
     *
     * @param bmi BMI 数值
     * @param age 年龄
     * @param gender 性别字符串
     * @return BFR 计算结果, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal( double percentage ) } 转小数
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 age 或 bmi 不在有效范围时
     *
     * @see <a href="https://metaso.cn/s/3DB8PEH"> 参考 Jackson-Pollock 公式基于 BMI、年龄、性别的版本( 见 P5 表4 ) </a>
     */
    public static double calculateBFRByJacksonPollockSimplifiedEquation( double bmi, int age, String gender ){
        validateBasicParameters( bmi, age, gender );
        return calculateBFRByGeneralFormula(
                BFRCalculatorConstants.JacksonPollockEquation.SimplifiedVersion.BMI_COEFFICIENT, bmi,
                BFRCalculatorConstants.JacksonPollockEquation.SimplifiedVersion.AGE_COEFFICIENT, age,
                BFRCalculatorConstants.JacksonPollockEquation.SimplifiedVersion.GENDER_COEFFICIENT, gender,
                BFRCalculatorConstants.JacksonPollockEquation.SimplifiedVersion.CONST_TERM );
    }

    /**
     *  使用美国海军体脂率公式计算 BFR, <B>单位: %</B><br>
     *  美国海军: USN, the United State Navy, 该公式适合普通成年男性, 误差约±3%,使用对数计算,
     *  需测量腰围、颈围、臀围和身高
     *  <pre>
     *      男: 86.010 * lg( 腰围 - 颈围 ) - 70.041 * lg( 身高 ) + 36.76
     *      女: 163.205 * lg( 腰围 + 臀围 - 颈围 ) - 97.684 * lg( 身高 ) - 78.387
     *  </pre>
     *      <B>❗注意</B>: 该公式基于英寸, 身高、各种围度均为英寸( 1 inch = 2.54 cm ), 这里的处理办法是传入
     *            cm, 在内部调用 {@link UnitConvertor#cmToInch(double) }转换为 inch
     *
     *  @param gender 性别
     *  @param waistCircumference 腰围( cm )
     *  @param hipCircumference 臀围( cm )
     *  @param neckCircumference 颈围( cm )
     *  @param height 身高( cm )
     *
     *  @return BFR 计算结果, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal( double percentage ) } 转小数
     *  @throws NullPointerException 当 gender 为 null 时
     *  @throws IllegalArgumentException 当 gender 或 waistCircumference 或 hipCircumference 或 neckCircumference 或 height 不在有效范围时
     *
     *  @see <a href="https://metaso.cn/s/qwkkvqN"> 参考男女性公式 </a>
     *      ( 见 P16, 3.1.3 Body Fat Calculator Activity 第 3 段 4-7 行 )
     *
     */
    public static double calculateBFRByUSNEquation( String gender, double waistCircumference, double hipCircumference, double neckCircumference, double height ){
        validateUSNEquationParameters( gender, waistCircumference, hipCircumference, neckCircumference, height );
        if ( Gender.isMale( gender ) ){
            return calculateBFRByUSNEquation(
                    BFRCalculatorConstants.USNEquation.Male.LOGARITHM_TERM_COEFFICIENT1, // 对数项系数1
                    BFRCalculatorConstants.USNEquation.Male.LOGARITHM_TERM_COEFFICIENT2, // 对数项系数2
                    UnitConvertor.cmToInch( waistCircumference - neckCircumference ), // 对数项1的底数
                    UnitConvertor.cmToInch( height ), // 对数项2的底数
                    BFRCalculatorConstants.USNEquation.Male.CONST_TERM );
        } else {
            return calculateBFRByUSNEquation(
                    BFRCalculatorConstants.USNEquation.Female.LOGARITHM_TERM_COEFFICIENT1, // 对数项系数1
                    BFRCalculatorConstants.USNEquation.Female.LOGARITHM_TERM_COEFFICIENT2, // 对数项系数2
                    UnitConvertor.cmToInch( waistCircumference + hipCircumference - neckCircumference ), // 对数项1的底数( 女生需要补上臀围 )
                    UnitConvertor.cmToInch( height ), // 对数项2的底数
                    BFRCalculatorConstants.USNEquation.Female.CONST_TERM );
        }
    }


    // ==================== 私有辅助方法 ====================
    /**
     * main 方法测试
     * @param args 测试参数
     */
    private static void main( String[] args ){
        String gender = "男";
        int age = 19;
        double bmi = 18;
        System.out.println( BFRCalculator.calculateBFRByDeurenbergEquation( bmi, age, gender ) );
        System.out.println( BFRCalculator.calculateBFRByGallagherEquation1( bmi, age, gender ) );
        System.out.println( BFRCalculator.calculateBFRByGallagherEquation2( bmi, age, gender ) );
        System.out.println( BFRCalculator.calculateBFRByJacksonPollockSimplifiedEquation( bmi, age, gender ) );
    }

    /**
     * 检验方法参数
     *
     * @param bmi BMI
     * @param age 年龄
     * @param gender 性别
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 bmi 或 age 或 gender 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( double bmi, int age, String gender ){
        BasicBodyParameterValidator.validateGender( gender );
        BasicBodyParameterValidator.validateAge( age );
        HealthMetricValidator.validateBMI( bmi );
    }

    /**
     * 检验方法参数
     *
     * @param gender 性别
     * @param waistCircumference 腰围( cm )
     * @param hipCircumference 臀围( cm )
     * @param neckCircumference 颈围( cm )
     * @param height 身高( cm )
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 或 waistCircumference 或 hipCircumference 或 neckCircumference 或 height 不在有效范围时
     * @since 3.0.0
     */
    private static void validateUSNEquationParameters( String gender, double waistCircumference, double hipCircumference, double neckCircumference, double height ){
        BasicBodyParameterValidator.validateGender( gender );
        BasicBodyParameterValidator.validateWaistCircumference( waistCircumference );
        BasicBodyParameterValidator.validateHipCircumference( hipCircumference );
        BasicBodyParameterValidator.validateNeckCircumference( neckCircumference );
        BasicBodyParameterValidator.validateHeight( height );
    }

    /**
     * BFR 计算通式--符合多数公式的形式, 只需传入参数( 自变量、系数 )即可
     *
     * @param bmiCoefficient BMI 项的系数
     * @param bmi BMI
     * @param ageCoefficient 年龄项的系数
     * @param age 年龄
     * @param genderCoefficient 性别项的系数
     * @param gender  性别
     * @param constTerm 常数项
     *                  
     * @return BFR 计算结果, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal( double percentage ) } 转小数
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 bmi 或 age 或 gender 不再有效范围时
     */
    private static double calculateBFRByGeneralFormula( double bmiCoefficient, double bmi, double ageCoefficient, int age, double genderCoefficient, String gender, double constTerm ){
        validateBasicParameters( bmi, age, gender );
        return ( bmiCoefficient * bmi + ageCoefficient * age + genderCoefficient * Gender.intIsMale( gender ) + constTerm );
    }

    /**
     * 计算 BFR 的美国海军公式计算逻辑
     * <pre>
     *     对数项系数Ⅰ * lg( 围度 ) + 对数项系数Ⅱ * lg( 身高 ) + 常数项
     * </pre>
     *
     * @param logarithmTermCoefficient1 对数项系数Ⅰ
     * @param logarithmTermCoefficient2 对数项系数Ⅱ
     * @param circumference 围度( 腰围、臀围、颈围的加减结果 ), <B>单位: inch</B>
     * @param height 身高, <B>单位: inch</B>
     * @param constTerm 常数项
     *
     * @return BFR 计算结果, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal( double percentage ) } 转小数
     * @since 3.0.0
     */
    private static double calculateBFRByUSNEquation( double logarithmTermCoefficient1, double logarithmTermCoefficient2, double circumference, double height, double constTerm ){
        return ( logarithmTermCoefficient1 * Math.log10( circumference ) + logarithmTermCoefficient2 * Math.log10( height ) + constTerm );
    }
}
