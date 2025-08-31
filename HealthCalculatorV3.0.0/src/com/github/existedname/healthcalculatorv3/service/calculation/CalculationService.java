package com.github.existedname.healthcalculatorv3.service.calculation;

import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.UIService;
import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import com.github.existedname.healthcalculatorv3.util.calculator.composite.HealthMetricCalculator;
import com.github.existedname.healthcalculatorv3.util.input.InputProcessor;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 健康指标计算服务
 * <p>
 *     获取用户输入的参数, 调用健康指标计算器工具类进行计算, 并将输入参数及结果保存到用户对象中
 * </p>
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 */
public final class CalculationService {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================
    private static CalculationService calculationService = null;


    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private CalculationService(){ }



    // ==================== 公有方法 ====================
    public static CalculationService getInstance(){
        if ( Objects.isNull( calculationService ) ){
            calculationService = new CalculationService();
        }
        return calculationService;
    }


    /*  计算体态评估类健康指标 */
    /**
     * 计算 BMI 并模拟计算过程、展示结果
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void showBMICalculation( User user, Scanner scanner ){
        showHealthMetricCalculation( user, scanner,
                CalculationMethod.CALC_BMI, HealthMetric.BMI,
                HealthMetricCalculator::readBodyDataCalculateBMIAndUpdateUser,
                User::getBMI );
    }

    /**
     * 计算 WHR 并模拟计算过程、展示结果
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void showWHRCalculation( User user, Scanner scanner ){
        showHealthMetricCalculation( user, scanner,
                CalculationMethod.CALC_WHR, HealthMetric.WHR,
                HealthMetricCalculator::readBodyDataCalculateWHRAndUpdateUser,
                User::getWHR );
    }

    /**
     * 计算 BFR 并模拟计算过程、展示结果
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void showBFRCalculation( User user, Scanner scanner ){
        showHealthMetricCalculation( user, scanner,
                CalculationMethod.CALC_BFR, HealthMetric.BFR,
                HealthMetricCalculator::readBodyDataCalculateBFRAndUpdateUser,
                User::getBFR );
    }

    /**
     * 计算 BRI 并模拟计算过程、展示结果
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void showBRICalculation( User user, Scanner scanner ){
        showHealthMetricCalculation( user, scanner,
                CalculationMethod.CALC_BRI, HealthMetric.BRI,
                HealthMetricCalculator::readBodyDataCalculateBRIAndUpdateUser,
                User::getBRI );
    }

    /*  计算能量代谢类健康指标 */
    /**
     * 计算 BMR 并模拟计算过程、展示结果
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void showBMRCalculation( User user, Scanner scanner ){
        showHealthMetricCalculation( user, scanner,
                CalculationMethod.CALC_BMR, HealthMetric.BMR,
                HealthMetricCalculator::readBodyDataCalculateBMRAndUpdateUser,
                User::getBMR );
    }

    /**
     * 计算 TDEE 并模拟计算过程、展示结果
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void showTDEECalculation( User user, Scanner scanner ){
        showHealthMetricCalculation( user, scanner,
                CalculationMethod.CALC_TDEE, HealthMetric.TDEE,
                HealthMetricCalculator::readBodyDataCalculateTDEEAndUpdateUser,
                User::getTDEE );
    }

    /*  计算生理特征类健康指标 */
    /**
     * 计算 BSA 并模拟计算过程、展示结果
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void showBSACalculation( User user, Scanner scanner ){
        showHealthMetricCalculation( user, scanner,
                CalculationMethod.CALC_BSA, HealthMetric.BSA,
                HealthMetricCalculator::readBodyDataCalculateBSAAndUpdateUser,
                User::getBSA );
    }



    // ==================== 私有辅助方法 ====================

    /**
     * 健康指标计算、展示的通用模板, 封装了计算、打印进度、打印结果<br>
     * <p>
     *     <pre>
     * 应用函数式编程:
     * 1. 使用 BiFunction 函数式接口处理具体的计算逻辑( readBodyDataCalculateMetricAndUpdateUser 方法 )
     * 2. 使用 Function 函数式接口获取已计算的结果( healthMetricGetter 方法 )
     *     </pre>
     * </p>
     *
     * @param user 用户对象
     * @param scanner 输入流
     * @param calculationMethod 计算方法枚举成员
     * @param healthMetric 健康指标枚举成员
     * @param readBodyDataCalculateMetricAndUpdateUser {@link HealthMetricCalculator } 工具类中读入基本身体参数、计算健康指标、保存读取以及计算的数据到用户对象中的方法
     * @param healthMetricGetter {@link com.github.existedname.healthcalculatorv3.model.entity.UserBodyProfile } 中获取指定健康指标的 getter 方法
     *
     * @throws NullPointerException 当 user 或 scanner 或 calculationMethod 或 healthMetric 或 healthMetricGetter 为 null 时
     */
    private void showHealthMetricCalculation( User user, Scanner scanner,
                                              CalculationMethod calculationMethod, HealthMetric healthMetric,
                                              BiFunction< User, Scanner, Double > readBodyDataCalculateMetricAndUpdateUser,
                                              Function< User, Double > healthMetricGetter ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );
        Objects.requireNonNull( calculationMethod, "参数 calculationMethod( 计算方法枚举成员 )不能为 null" );
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );
        Objects.requireNonNull( readBodyDataCalculateMetricAndUpdateUser, "参数 readBodyDataCalculateMetricAndUpdateUser( 计算并保存指定健康指标的二元函数方法 )不能为 null" );
        Objects.requireNonNull( healthMetricGetter, "参数 healthMetricGetter( 获取用户健康指标数值的一元函数方法 )不能为 null" );
        // 1. 是否需要重新计算
        if ( calculationMethod.isVisited() ){
            System.out.println( "检测到你之前计算过 " + healthMetric.getAbbreviation() + ", 是否需要重新计算?" );
            System.out.print( "重新计算请输入 1 或 yes, 继续使用上次的计算结果请输入 0 或 no:\t" );
            while ( true ){
                String trimmedInput = InputProcessor.toTrimmedOrEmptyStr( scanner.nextLine() );
                if ( "0".equals( trimmedInput ) || "no".equalsIgnoreCase( trimmedInput ) ){
                    // 直接打印上次计算结果
                    printCalculationResult( healthMetric, healthMetricGetter.apply( user ) );
                    return;
                } else if ( "1".equals( trimmedInput ) || "yes".equalsIgnoreCase( trimmedInput ) ){
                    break;
                } else {
                    System.out.print( "输入有误, 请重新输入:\t" );
                }
            }
        }
        // 2. 计算健康指标
        double value = readBodyDataCalculateMetricAndUpdateUser.apply( user, scanner );
        // 3. 标记 showXxxCalculation 方法已访问
        calculationMethod.markAsVisited();
        // 4. 展示计算进程、结果
        printCalculationProgress( healthMetric );
        printCalculationResult( healthMetric, value );
    }

    /**
     * 打印健康指标计算进度
     *
     * @param healthMetric 被计算的健康指标对应的枚举成员
     * @throws NullPointerException 当 healthMetric 为 null 时
     */
    private void printCalculationProgress( HealthMetric healthMetric ){
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );
        String nameAbbreviation = healthMetric.getAbbreviation();
        String operation = "正在计算 " + nameAbbreviation, result = nameAbbreviation + " 计算完毕!";
        UIService.getInstance().printLoadingProgress( operation, result );
    }

    /**
     * 打印健康指标计算结果
     *
     * @param healthMetric 健康指标枚举成员
     * @param value 计算结果
     * @throws NullPointerException 当 healthMetric 为 null 时
     */
    private void printCalculationResult( HealthMetric healthMetric, double value ){
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );
        System.out.println( String.format( "你的<%s>数值为:\t%s%s", healthMetric.getAbbreviation(),
                ValueFormatter.formatToOneDecimal( value ), healthMetric.getUnit() ) );
    }
}
