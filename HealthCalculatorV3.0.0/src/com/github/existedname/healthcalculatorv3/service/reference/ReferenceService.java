package com.github.existedname.healthcalculatorv3.service.reference;

import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.IdealWeightCalculator;
import com.github.existedname.healthcalculatorv3.util.printer.PrinterConstants;
import com.github.existedname.healthcalculatorv3.util.printer.UIPrinter;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Objects;
import java.util.Scanner;

/**
 * 提供身体各项健康指标参考值查询服务
 * <p>
 * 该类主要用于查询各类健康指标的理想值或参考范围, 包括:
 * <pre>
 *     1. 部分基本身体参数( 体重、腰围 )
 *     2. 体态评估类健康指标( BMI、WHR、BFR、BRI )
 *     3. 能量代谢类健康指标( BMR、TDEE )
 *     4. 生理特征类健康指标( BSA )
 * </pre>
 * </p> 
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/16 10:08
 */
public final class ReferenceService {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================
    private static ReferenceService referenceService = null;

    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private ReferenceService(){ }

    // ==================== 公有方法 ====================
    public static ReferenceService getInstance(){
        if ( Objects.isNull( referenceService ) ){
            referenceService = new ReferenceService();
        }
        return referenceService;
    }

    
    /*  查看体态评估类健康指标理想值/范围   */
    /**
     * 查看 体重 的理想范围<br>
     * 内部调用 {@link #calculateAndShowIdealWeight(User, Scanner) } 方法计算、展示相应的理想体重
     * 
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     * 
     * @since 2.0.0
     */
    public void checkIdealWeight( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );
        HealthMetricReference.WEIGHT.printIdealRange();
        calculateAndShowIdealWeight( user, scanner ); // 成功打印理想范围后具体计算相应的理想体重
    }

    /**
     * 查看 BMI 的理想范围
     * @since 2.0.0
     */
    public void checkIdealBMI(){
        HealthMetricReference.BMI.printIdealRange();
    }

    /**
     * 查看 腰围 的理想范围
     * @since 2.0.0
     */
    public void checkIdealWaistCircumference(){
        HealthMetricReference.WAIST_CIRCUMFERENCE.printIdealRange();
    }

    /**
     * 查看 WHR 的理想范围
     * @since 2.0.0
     */
    public void checkIdealWHR(){
        HealthMetricReference.WHR.printIdealRange();
    }

    /**
     * 查看 BFR 的理想范围
     * @since 2.0.0
     */
    public void checkIdealBFR(){
        HealthMetricReference.BFR.printIdealRange();
    }

    /**
     * 查看 BRI 的理想范围
     * @since 2.0.0
     */
    public void checkIdealBRI(){
        HealthMetricReference.BRI.printIdealRange();
    }


    /*  查看能量代谢类健康指标理想值/范围   */
    /**
     * 查看 BMR 的理想范围
     * @since 2.0.0
     */
    public void checkIdealBMR(){
        HealthMetricReference.BMR.printIdealRange();
    }

    /**
     * 查看 TDEE 的理想范围
     * @since 2.0.0
     */
    public void checkIdealTDEE(){
        HealthMetricReference.TDEE.printIdealRange();
    }


    /*  查看生理特征类健康指标理想值/范围   */
    /**
     * 查看 BSA 的理想范围
     * @since 2.0.0
     */
    public void checkIdealBSA(){
        HealthMetricReference.BSA.printIdealRange();
    }


    // ==================== 私有辅助方法 ====================

    /**
     * 计算、展示理想体重, 内部逻辑已拆分成 {@link #showIdealWeightBasedOnHeight(User, Scanner) }、
     * {@link #showIdealWeightBasedOnBMI(double) } 2 个方法
     * 
     * @param user 用户对象
     * @param scanner 扫描器
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    private void calculateAndShowIdealWeight( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );
        /*  方法① 根据身高确定理想体重范围    */
        UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.EQUATION_DISPLAY_PAUSE );
        System.out.println( "\t方法① 根据身高确定理想体重范围" );
        showIdealWeightBasedOnHeight( user, scanner );
        /*  方法② 根据 BMI 倒推理想体重范围    */
        UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.EQUATION_DISPLAY_PAUSE );
        System.out.println( "\t方法② 根据 BMI 倒推理想体重范围" );
        showIdealWeightBasedOnBMI( user.getHeight() ); // 方法①已经获取有效身高, 这里直接传身高而不是 user 即可
    }

    /**
     * 展示根据身高计算理想体重<br>
     * 计算公式:
     * <pre>
     *     理想体重 = 身高 - 100( 女 )或 105( 男 )
     *     理想体重范围在该计算值上波动 ±10%:
     *          理想体重下限 = 理想体重 * 0.9
     *          理想体重上限 = 理想体重 * 1.1
     * </pre>
     * @param user 用户对象
     * @param scanner 输入扫描器
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    private void showIdealWeightBasedOnHeight( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        double idealWeight = IdealWeightCalculator.BasedOnHeight.getCalculatedIdealWeight( user, scanner );
        String idealWeightRange = IdealWeightCalculator.BasedOnHeight.formatIdealWeightRange( idealWeight );
        String unit = BasicBodyParameter.WEIGHT.getUnit();

        System.out.println( String.format( "你当前身高对应的理想体重为 %s%s, 理想体重范围为%s%s",
                ValueFormatter.formatToOneDecimal( idealWeight ), unit,
                idealWeightRange, unit )
        );
    }

    /**
     * 展示根据 身高、BMI 计算理想体重<br>
     * 计算公式:
     * <pre>
     *     BMI = Weight(kg) / Height(m)² => Weight = BMI * Height²
     * </pre>
     * @param height 身高( cm, 由内部转换成 m )
     * @throws IllegalArgumentException 当 height 不在有效范围时
     */
    private void showIdealWeightBasedOnBMI( double height ){
        System.out.println( String.format( "理想体重范围为%s%s",
                IdealWeightCalculator.BasedOnBMI.formateIdealWeightRange( height ),
                BasicBodyParameter.WEIGHT.getUnit() ) );
    }
}
