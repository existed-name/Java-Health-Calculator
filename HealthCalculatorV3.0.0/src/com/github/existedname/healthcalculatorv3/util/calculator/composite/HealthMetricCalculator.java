package com.github.existedname.healthcalculatorv3.util.calculator.composite;

import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BFRCalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BMICalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BMRCalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BRICalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BSACalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.TDEECalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.WHRCalculator;
import com.github.existedname.healthcalculatorv3.util.input.BodyDataReader;
import com.github.existedname.healthcalculatorv3.util.convertor.UnitConvertor;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Objects;
import java.util.Scanner;

/**
 * 健康指标综合计算器工具类, 作为外界计算各个身体指标和底层工具方法的中间桥梁
 * <pre>
 *     内部调用 {@link BodyDataReader }、{@link com.github.existedname.healthcalculatorv3.util.calculator }、{@link User },
 *     将读入基本身体参数、计算健康指标、保存读取以及计算的数据到用户对象等一系列操作封装进每个方法, 提高便利性
 *     缺点是为了表意清晰, 方法命名很长( readBodyDataCalculateXxxAndUpdateUser )
 * </pre>
 *
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a> 
 * @since 3.0.0
 * @CreateTime 2025/8/22 10:55
 */
public final class HealthMetricCalculator {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================


    // ==================== 公有方法 ====================
    /**
     * 读取有效体重、身高, 计算 BMI, 将读取数据和计算结果存入 user, 最后返回 BMI, <B>单位: kg/m²</B>
     *
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @return BMI, <B>单位: kg/m²</B>
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static double readBodyDataCalculateBMIAndUpdateUser( User user, Scanner scanner ){
        validateBasicParameters( user, scanner );
        // 1. 读取有效体重、身高
        double weight = BodyDataReader.readWeight( scanner );
        double height = BodyDataReader.readHeight( scanner );
        // 2. 调用相应的工具类、计算方法
        double bmi = BMICalculator.calculateBMI( weight, height );
        // 3. 更新 user 成员变量
        user.setBMI( bmi ); user.setWeight( weight ); user.setHeight( height );
        return bmi;
    }

    /**
     * 读取有效腰围、臀围, 计算 WHR, 将读取数据和计算结果存入 user, 最后返回 WHR, <B>单位: 无</B>
     *
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @return WHR, <B>单位: 无</B>
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static double readBodyDataCalculateWHRAndUpdateUser( User user, Scanner scanner ){
        validateBasicParameters( user, scanner );
        // 1. 读取有效腰围、臀围
        double waistCircumferenceCm = BodyDataReader.readWaistCircumference( scanner );
        double hipCircumferenceCm = BodyDataReader.readHipCircumference( scanner );
        // 2. 调用相应的工具类以及计算方法
        double whr = WHRCalculator.calculateWHR( waistCircumferenceCm, hipCircumferenceCm );
        // 3. 更新 user 成员变量
        user.setWHR( whr ); user.setWaistCircumference( waistCircumferenceCm ); user.setHipCircumference( hipCircumferenceCm );
        return whr;
    }

    /**
     * 调用 {@link #readBodyDataCalculateBMIAndUpdateUser( User, Scanner ) } 获取 BMI, <br>
     * 再读取有效性别、年龄, 计算 BFR, 将读取数据和计算结果存入 user, 最后返回 BFR, <B>单位: %</B>
     *
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @return BFR, <B>单位: %</B>, 注意使用 BFR 进行计算时需要调用
     *          {@link UnitConvertor#percentageToDecimal(double percentage) } 转小数
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static double readBodyDataCalculateBFRAndUpdateUser( User user, Scanner scanner ){
        validateBasicParameters( user, scanner );
        // 1. 获取有效 BMI、性别、年龄
        double bmi = readBodyDataCalculateBMIAndUpdateUser( user, scanner );
        String gender = BodyDataReader.readGender( scanner );
        int age = BodyDataReader.readAge( scanner );
        // 2. 调用相应的工具类以及计算方法
        // 默认使用最常见的 Deurenberg 公式
        double bfr = BFRCalculator.calculateBFRByDeurenbergEquation( bmi, age, gender );
        // 3. 更新 user 成员变量
        user.setBFR( bfr ); user.setGender( gender ); user.setAge( age );
        return bfr;
    }

    /**
     * 读取有效腰围、身高, 计算 BRI, 将读取数据和计算结果存入 user, 最后返回 BRI, <B>单位: 无</B>
     *
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @return BRI, <B>单位: 无</B>
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static double readBodyDataCalculateBRIAndUpdateUser( User user, Scanner scanner ){
        validateBasicParameters( user, scanner );
        // 1. 读取有效腰围、身高
        double waistCircumferenceCm = BodyDataReader.readWaistCircumference( scanner );
        double height = BodyDataReader.readHeight( scanner );
        // 2. 调用相应的工具类以及计算方法
        double bri = BRICalculator.calculateBRI( waistCircumferenceCm, height );
        // 3. 更新 user 成员变量
        user.setBRI( bri ); user.setWaistCircumference( waistCircumferenceCm ); user.setHeight( height );
        return bri;
    }

    /**
     * 读取有效性别、年龄、体重、身高, 计算 BMR, 将读取数据和计算结果存入 user, 最后返回 BMR, <B>单位: Kcal</B>
     *
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @return BMR, <B>单位: Kcal</B>
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static double readBodyDataCalculateBMRAndUpdateUser( User user, Scanner scanner ){
        validateBasicParameters( user, scanner );
        // 1. 读取有效性别、年龄、体重、身高
        String gender = BodyDataReader.readGender( scanner );
        int age = BodyDataReader.readAge( scanner );
        double weight = BodyDataReader.readWeight( scanner );
        double height = BodyDataReader.readHeight( scanner );
        // 2. 调用相应的工具类以及计算方法
        // 选用相对准确的 Mifflin-St Jeor( MSJ ) 公式
        double bmr = BMRCalculator.calculateBMRByMSJEquation( gender, weight, height, age );
        // 3. 更新 user 成员变量
        user.setBMR( bmr ); user.setGender( gender ); user.setAge( age ); user.setWeight( weight ); user.setHeight( height );
        return bmr;
    }

    /**
     * 调用 {@link #readBodyDataCalculateBMRAndUpdateUser( User, Scanner ) } 获取 TDEE, <br>
     * 再读取有效活动系数, 计算 TDEE, 将读取数据和计算结果存入 user, 最后返回 TDEE, <B>单位: Kcal</B>
     *
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @return TDEE, <B>单位: Kcal</B>
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static double readBodyDataCalculateTDEEAndUpdateUser( User user, Scanner scanner ){
        validateBasicParameters( user, scanner );
        // 1. 获取有效 BMR
        double bmr = readBodyDataCalculateBMRAndUpdateUser( user, scanner );
        // 2. 获取有效 活动系数
        double activityCoefficient = BodyDataReader.readActivityCoefficient( scanner );
        // 3. 调用相应的工具类以及计算方法
        double tdee = TDEECalculator.calculateTDEE( bmr, activityCoefficient );
        // 4. 更新 user 成员变量
        user.setTDEE( tdee ); user.setActivityCoefficient( activityCoefficient );
        return tdee;
    }

    /**
     * 读取有效体重、身高, 计算 BSA, 将读取数据和计算结果存入 user, 最后返回 BSA, <B>单位: m²</B>
     *
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @return BSA, <B>单位: m²</B>
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static double readBodyDataCalculateBSAAndUpdateUser( User user, Scanner scanner ){
        validateBasicParameters( user, scanner );
        // 1. 读取有效体重、身高
        double weight = BodyDataReader.readWeight( scanner );
        double height = BodyDataReader.readHeight( scanner );
        // 2. 调用相应的工具类、计算方法
        // 选择最经典、应用最广泛的 Du Bois Formula 杜博伊斯公式
        double bsa = BSACalculator.calculateBSAByDuBoisEquation( height, weight );
        // 3. 更新 user 成员变量
        user.setBSA( bsa ); user.setWeight( weight ); user.setHeight( height );
        return bsa;
    }



    // ==================== 私有辅助方法 ====================
    /**
     * 检验方法参数
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    private static void validateBasicParameters( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );
    }
}
