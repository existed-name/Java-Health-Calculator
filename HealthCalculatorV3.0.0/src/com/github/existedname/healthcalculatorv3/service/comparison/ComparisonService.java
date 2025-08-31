package com.github.existedname.healthcalculatorv3.service.comparison;

import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.comparison.equation.description.BFREquationIntroduction;
import com.github.existedname.healthcalculatorv3.service.comparison.equation.description.BMREquationIntroduction;
import com.github.existedname.healthcalculatorv3.service.comparison.equation.description.BSAEquationIntroduction;
import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BFRCalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BMRCalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BSACalculator;
import com.github.existedname.healthcalculatorv3.util.calculator.composite.HealthMetricCalculator;
import com.github.existedname.healthcalculatorv3.util.input.BodyDataReader;
import com.github.existedname.healthcalculatorv3.util.input.InputProcessor;
import com.github.existedname.healthcalculatorv3.util.printer.PrinterConstants;
import com.github.existedname.healthcalculatorv3.util.printer.UIPrinter;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * 健康指标计算公式对比服务类
 * <p>
 * 该类用于对比不同公式的计算结果, 支持体脂率(BFR)、基础代谢率(BMR)和体表面积(BSA)等健康指标的多种计算公式<br>
 * 通过此类可以查看同一健康指标使用不同公式得出的结果差异, 帮助用户选择最适合的计算方法
 * </p>
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/14 15:05
 */
public final class ComparisonService {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================
    private static ComparisonService comparisonService = null;

    // ==================== 实例变量 ====================
    /** 
     * 各种 BFR 公式展示方法集合 
     */
    private final List< Consumer< User > > bfrEquationDisplayers = List.of(
            // 不超过 10 个 → List.of, 超过 10 个 → List.copyOf
            this::showDeurenbergEquation,
            this::showGallagherEquation,
            this::showUSNEquation,
            this::showJacksonPollockEquation
    );
    /** 
     * 各种 BMR 公式展示方法集合 
     */
    private final List< Consumer< User > > bmrEquationDisplayers = List.of(
            this::showMSJEquation,
            this::showHBEquation,
            this::showHenryEquation,
            this::showSchofieldEquation,
            this::showKatchMcArdleEquation,
            this::showShizgalRosaEquation,
            this::showMaoEquation,
            this::showBMRCalculationBasedOnBSA
    );
    /** 
     * 各种 BSA 公式展示方法集合 
     */
    private final List< Consumer< User > > bsaEquationDisplayers = List.of(
            this::showDuBoisEquation,
            this::showSchlichEquation,
            this::showMostellerEquation,
            this::showHaycockEquation
    );
    /** 
     * 存储同一健康指标的各种方程的计算结果<br>
     * 用于 {@link #printEquationResultsRange(HealthMetric) } 方法展示该健康指标的计算结果范围
     */
    private final List< Double > equationResults = new ArrayList<>( 10 );


    // ==================== 构造器 ====================
    private ComparisonService(){ }


    // ==================== 公有方法 ====================
    public static ComparisonService getInstance(){
        if ( Objects.isNull( comparisonService ) ){
            comparisonService = new ComparisonService();
        }
        return comparisonService;
    }
    
    
    /*  对比不同公式得到的体态评估类健康指标  */
    /**
     * 对比 BFR 的各种计算公式<br>
     * 该方法调用 {@link HealthMetricCalculator } 工具类读取体重、身高并计算保存 BMI, 同时涉及性别、年龄的读取和保存
     * 
     * @param user 用户对象
     * @param scanner 输入流
     *                
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void compareVariousEquationsOfBFR( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        user.setGender( BodyDataReader.readGender( scanner ) );
        user.setAge( BodyDataReader.readAge( scanner ) );
        user.setBMI( HealthMetricCalculator.readBodyDataCalculateBMIAndUpdateUser( user, scanner ) );

        clearEquationResults(); // 清空 equationResults, 避免上次结果未删除干扰本次比较
        bfrEquationDisplayers.forEach( equationDisplayer -> {
            equationDisplayer.accept( user );
            UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.EQUATION_DISPLAY_PAUSE );
            System.out.println();
        } );

        /*      最后输出计算结果范围      */
        printEquationResultsRange( HealthMetric.BFR );
        clearEquationResults(); // 清空 equationResults, 防止干扰下次比较
    }

    /*  对比不同公式得到的能量代谢类健康指标  */
    /**
     * 对比 BMR 的各种计算公式<br>
     * 该方法涉及性别、年龄、体重、身高的读取和保存
     *
     * @param user 用户对象
     * @param scanner 输入流
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void compareVariousEquationsOfBMR( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        user.setGender( BodyDataReader.readGender( scanner ) );
        user.setAge( BodyDataReader.readAge( scanner ) );
        user.setWeight( BodyDataReader.readWeight( scanner ) );
        user.setHeight( BodyDataReader.readHeight( scanner ) );

        clearEquationResults(); // 清空 equationResults, 避免上次结果未删除干扰本次比较
        bmrEquationDisplayers.forEach( equationDisplayer -> {
            equationDisplayer.accept( user );
            UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.EQUATION_DISPLAY_PAUSE );
            System.out.println();
        } );

        /*      最后输出计算结果范围      */
        printEquationResultsRange( HealthMetric.BMR );
        clearEquationResults(); // 清空 equationResults, 防止干扰下次比较
    }

    /*  对比不同公式得到的生理特征类健康指标  */
    /**
     * 对比 BSA 的各种计算公式<br>
     * 该方法涉及性别、体重、身高的读取和保存
     *
     * @param user 用户对象
     * @param scanner 输入流
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void compareVariousEquationsOfBSA( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        user.setGender( BodyDataReader.readGender( scanner ) );
        user.setWeight( BodyDataReader.readWeight( scanner ) );
        user.setHeight( BodyDataReader.readHeight( scanner ) );

        clearEquationResults(); // 清空 equationResults, 避免上次结果未删除干扰本次比较
        bsaEquationDisplayers.forEach( equationDisplayer -> {
            equationDisplayer.accept( user );
            UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.EQUATION_DISPLAY_PAUSE );
            System.out.println();
        } );

        /*      最后输出计算结果范围      */
        printEquationResultsRange( HealthMetric.BSA );
        clearEquationResults(); // 清空 equationResults, 防止干扰下次比较
    }


    // ==================== 私有辅助方法 ====================
    /*      对比不同公式得到的 BFR       */
    /**
     * 展示计算 BFR 的 Deurenberg 公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     * 
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showDeurenbergEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      1. Deurenberg 公式        */
        BFREquationIntroduction.DEURENBERG_EQUATION.printEquationIntroduction();
        double tempBFR = BFRCalculator.calculateBFRByDeurenbergEquation( user.getBMI(), user.getAge(), user.getGender() );
        printFormattedValue( HealthMetric.BFR, tempBFR );
        equationResults.add( tempBFR );
    }

    /**
     * 展示计算 BFR 的 Gallagher 公式的 2 个版本( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showGallagherEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      2. Gallagher 公式     */
        BFREquationIntroduction.GALLAGHER_EQUATION.printEquationIntroduction();

        /*  2.1 Gallagher 公式版本Ⅰ */
        System.out.println( "2.1 Gallagher 公式版本Ⅰ: 基于 BMI、年龄、性别" );
        double tempBFR1 = BFRCalculator.calculateBFRByGallagherEquation1( user.getBMI(), user.getAge(), user.getGender() );
        printFormattedValue( HealthMetric.BFR, tempBFR1 );

        /*  2.2 Gallagher 公式版本Ⅱ */
        System.out.println( "2.2 Gallagher 公式版本Ⅱ: 基于 BMI 的倒数( 1/BMI )、年龄、性别" );
        double tempBFR2 = BFRCalculator.calculateBFRByGallagherEquation2( user.getBMI(), user.getAge(), user.getGender() );
        printFormattedValue( HealthMetric.BFR, tempBFR2 );
        
        Collections.addAll( equationResults, tempBFR1, tempBFR2 );
    }

    /**
     * 展示计算 BFR 的 美国海军( USE )公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showUSNEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      3. 美国海军公式       */
        BFREquationIntroduction.USN_EQUATION.printEquationIntroduction();
        // 1. 提示用户
        System.out.println( "Question: 该方法需要测量腰围、颈围、臀围,确定要体验该方法?" );
        System.out.print( "Input( yes 或者 no ) >>\t" );
        Scanner scanner = new Scanner( System.in );

        while ( true ){
            String trimmedInput = InputProcessor.toTrimmedOrEmptyStr( scanner.nextLine() );
            if ( "yes".equalsIgnoreCase( trimmedInput )  ){
                // 2. 检查并获取有效 腰围、臀围、颈围
                double waistCircumferenceCm = BodyDataReader.readWaistCircumference( scanner );
                double hipCircumferenceCm = BodyDataReader.readHipCircumference( scanner );
                double neckCircumferenceCm = BodyDataReader.readNeckCircumference( scanner );
                // 3. 得到目标结果
                // 3.1 调用相应的工具类以及计算方法
                double tempBFR = BFRCalculator.calculateBFRByUSNEquation( user.getGender(), waistCircumferenceCm,
                        hipCircumferenceCm, neckCircumferenceCm, user.getHeight() );
                // 3.2 更新 user 成员变量
                user.setWaistCircumference( waistCircumferenceCm );
                user.setHipCircumference( hipCircumferenceCm );
                user.setNeckCircumference( neckCircumferenceCm );
                // 3.3 输出结果
                printFormattedValue( HealthMetric.BFR, tempBFR );
                equationResults.add( tempBFR );
            } else if ( "no".equalsIgnoreCase( trimmedInput ) ){
                break;
            } else {
                System.out.print( "输入有误, 请重新输入:\t" );
            }
        }
    }

    /**
     * 展示计算 BFR 的 Jackson-Pollock 公式的 2 个版本( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showJacksonPollockEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      4. Jackson-Pollock 公式       */
        BFREquationIntroduction.JACKSON_POLLOCK_EQUATION.printEquationIntroduction();

        /*  4.1 Jackson-Pollock 公式版本Ⅰ   */
        System.out.println( "4.1 Jackson-Pollock 公式版本Ⅰ: 基于 皮褶厚度,广泛用于学术和临床" );
        System.out.println( "由于需要用皮脂钳测量多处皮褶厚度,较为麻烦,此处略过~~~" );

        /*  4.2 Jackson-Pollock 公式版本Ⅱ   */
        System.out.println( "4.2 Jackson-Pollock 公式版本Ⅱ( 简化版本 ): 基于 BMI、年龄、性别" );
        double tempBFR = BFRCalculator.calculateBFRByJacksonPollockSimplifiedEquation( user.getBMI(),
                user.getAge(), user.getGender() );
        printFormattedValue( HealthMetric.BFR, tempBFR );
        equationResults.add( tempBFR );
    }


    /*      对比不同公式得到的 BMR       */
    /**
     * 展示计算 BMR 的 Mifflin-St Jeor( MSJ )公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showMSJEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      1. Mifflin-St Jeor( MSJ ) 公式        */
        BMREquationIntroduction.MSJ_EQUATION.printEquationIntroduction();
        double tempBMR = BMRCalculator.calculateBMRByMSJEquation( user.getGender(), user.getWeight(), user.getHeight(), user.getAge() );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    /**
     * 展示计算 BMR 的 Harris-Benedict( H-B ) 公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showHBEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      2. Harris-Benedict( H-B ) 公式        */
        BMREquationIntroduction.HB_EQUATION.printEquationIntroduction();
        double tempBMR = BMRCalculator.calculateBMRByHBEquation( user.getGender(), user.getWeight(), user.getHeight(), user.getAge() );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    /**
     * 展示计算 BMR 的 Henry 公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showHenryEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      3. Henry 公式     */
        BMREquationIntroduction.HENRY_EQUATION.printEquationIntroduction();
        double tempBMR = BMRCalculator.calculateBMRByHenryEquation( user.getGender(), user.getWeight(), user.getAge() );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    /**
     * 展示计算 BMR 的 Schofield 公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showSchofieldEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      4. Schofield 公式     */
        BMREquationIntroduction.SCHOFIELD_EQUATION.printEquationIntroduction();
        double tempBMR = BMRCalculator.calculateBMRBySchofieldEquation( user.getGender(), user.getWeight(), user.getAge() );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    /**
     * 展示计算 BMR 的 Katch-McArdle 公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showKatchMcArdleEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      5. Katch-McArdle 公式( 依据瘦体重 ) 公式     */
        BMREquationIntroduction.KATCH_MCARDLE_EQUATION.printEquationIntroduction();
        // 由于要计算瘦体重,需要先计算 BFR( 体脂率 )
        double bfr = BFRCalculator.calculateBFRByDeurenbergEquation( user.getBMI(), user.getAge(), user.getGender() );
        double tempBMR = BMRCalculator.calculateBMRByKatchMcArdleEquation( bfr, user.getWeight() );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    /**
     * 展示计算 BMR 的 Shizgal-Rosa 公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showShizgalRosaEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      6. Shizgal-Rosa 公式      */
        BMREquationIntroduction.SHIZGAL_ROSA_EQUATION.printEquationIntroduction();
        double tempBMR = BMRCalculator.calculateBMRByShizgalRosaEquation( user.getGender(), user.getWeight(), user.getHeight(), user.getAge() );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    /**
     * 展示计算 BMR 的 毛德倩 公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showMaoEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      7. 毛德倩公式        */
        BMREquationIntroduction.MAO_EQUATION.printEquationIntroduction();
        double tempBMR = BMRCalculator.calculateBMRByMaoEquation( user.getGender(), user.getWeight() );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    /**
     * 展示基于 BSA 计算 BMR 的公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showBMRCalculationBasedOnBSA( User user ){
        MethodParameterValidator.validateUser( user );
        /*      8. BSA( 体表面积 )计算法       */
        BMREquationIntroduction.BSA_CALCULATION_METHOD.printEquationIntroduction();
        double bsa = BSACalculator.calculateBSAByDuBoisEquation( user.getHeight(), user.getWeight() );// 需要先求出 BSA
        double tempBMR = BMRCalculator.calculateBSABasedBMR( user.getGender(), user.getAge(), bsa );
        printFormattedValue( HealthMetric.BMR, tempBMR );
        equationResults.add( tempBMR );
    }

    
    /*      对比不同公式得到的 BSA       */
    /**
     * 展示计算 BSA 的 Du Bois( 杜博伊斯 )公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showDuBoisEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      1. Du Bois( 杜博伊斯 )公式        */
        BSAEquationIntroduction.DUBOIS_EQUATION.printEquationIntroduction();
        double tempBSA = BSACalculator.calculateBSAByDuBoisEquation( user.getHeight(), user.getWeight() );
        printFormattedValue( HealthMetric.BSA, tempBSA );
        equationResults.add( tempBSA );
    }

    /**
     * 展示计算 BSA 的 Schlich( 施利希 )公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showSchlichEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      2. Schlich( 施利希 )公式     */
        BSAEquationIntroduction.SCHLICH_EQUATION.printEquationIntroduction();
        double tempBSA = BSACalculator.calculateBSABySchlichEquation( user.getGender(), user.getWeight(), user.getHeight() );
        printFormattedValue( HealthMetric.BSA, tempBSA );
        equationResults.add( tempBSA );
    }

    /**
     * 展示计算 BSA 的 Mosteller( 莫斯特勒 )公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showMostellerEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      3. Mosteller( 莫斯特勒 )公式      */
        BSAEquationIntroduction.MOSTELLER_EQUATION.printEquationIntroduction();
        double tempBSA = BSACalculator.calculateBSAByMostellerEquation( user.getWeight(), user.getHeight() );
        printFormattedValue( HealthMetric.BSA, tempBSA );
        equationResults.add( tempBSA );
    }

    /**
     * 展示计算 BSA 的 Haycock( 海科克 )公式( 简介及计算结果 )<br>
     * 同时储存计算结果到 {@link ComparisonService#equationResults } 集合中
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    private void showHaycockEquation( User user ){
        MethodParameterValidator.validateUser( user );
        /*      4. Haycock( 海科克 )公式     */
        BSAEquationIntroduction.HAYCOCK_EQUATION.printEquationIntroduction();
        double tempBSA = BSACalculator.calculateBSAByHaycockEquation( user.getWeight(), user.getHeight() );
        printFormattedValue( HealthMetric.BSA, tempBSA );
        equationResults.add( tempBSA );
    }


    /**
     * 清除 {@link ComparisonService#equationResults } 集合中的所有计算结果<br>
     * 该方法可应对 {@link ComparisonService#equationResults } 为 null 或 空集合的情况
     */
    private void clearEquationResults(){
        if ( Objects.isNull( equationResults ) || equationResults.isEmpty() ) return;
        equationResults.clear();
    }

    /**
     * 打印指定健康指标的计算结果( 名称 + 数值 )
     *
     * @param healthMetric {@link HealthMetric } 健康指标枚举成员
     * @throws NullPointerException 当 healthMetric 为 null 时
     */
    private void printFormattedValue( HealthMetric healthMetric, double value ){
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );
        System.out.println( String.format( "你的 %s 数值为:\t%s%s", healthMetric.getAbbreviation(),
                ValueFormatter.formatToTwoDecimal( value ), healthMetric.getUnit() ) );
    }

    /**
     * 打印指定健康指标的各个公式计算结果所在范围[ 最小值, 最大值 ]
     *
     * @param healthMetric {@link HealthMetric } 健康指标枚举成员
     * @throws NullPointerException 当 healthMetric 为 null 时
     * @throws IllegalStateException 当 {@link ComparisonService#equationResults } 为 null 或 空集合时
     */
    private void printEquationResultsRange( HealthMetric healthMetric ){
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );
        if ( Objects.isNull( equationResults ) ) throw new IllegalStateException( "equationResults 为 null, 可能初始化失败" );
        if ( equationResults.isEmpty() ) throw new IllegalStateException( "equationResults 不含元素, 可能被误删" );
        double minValue = Collections.min( equationResults );
        double maxValue = Collections.max( equationResults );
        System.out.println( String.format( "\t结合以上公式结果,你的 %s 所在范围大致为%s%s", healthMetric.getAbbreviation(),
                ValueFormatter.formatToTwoDecimalClosedRange( minValue, maxValue ), healthMetric.getUnit() ) );
    }


}
