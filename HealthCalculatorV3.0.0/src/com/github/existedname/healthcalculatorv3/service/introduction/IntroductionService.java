package com.github.existedname.healthcalculatorv3.service.introduction;

import java.util.Objects;

/**
 * 介绍服务类, 详解健康指标、部分基本身体参数( 腰围 )的定义、公式/测量、功能、局限性
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/13 16:01
 */
public final class IntroductionService {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================
    private static IntroductionService introductionService = null;

    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private IntroductionService(){ }

    // ==================== 公有方法 ====================
    public static IntroductionService getInstance(){
        if ( Objects.isNull( introductionService ) ){
            introductionService = new IntroductionService();
        }
        return introductionService;
    }


    /*  详解体态评估类健康指标 */
    /**
     * 详解 BMI 的定义、公式、功能、局限性
     * @since 2.0.0
     */
    public void introduceBMI(){
        HealthMetricIntroduction.BMI.printIntroduction();
    }

    /**
     * 详解 腰围 的定义、测量、功能、局限性
     * @since 2.0.0
     */
    public void introduceWaistCircumference(){
        HealthMetricIntroduction.WAIST_CIRCUMFERENCE.printIntroduction();
    }

    /**
     * 详解 WHR 的定义、公式、功能、局限性
     * @since 2.0.0
     */
    public void introduceWHR(){
        HealthMetricIntroduction.WHR.printIntroduction();
    }

    /**
     * 详解 BFR 的定义、公式、功能、局限性
     * @since 2.0.0
     */
    public void introduceBFR(){
        HealthMetricIntroduction.BFR.printIntroduction();
    }

    /**
     * 详解 BRI 的定义、公式、功能、局限性
     * @since 2.0.0
     */
    public void introduceBRI(){
        HealthMetricIntroduction.BRI.printIntroduction();
    }

    /*  详解能量代谢类健康指标 */
    /**
     * 详解 BMR 的定义、公式、功能、局限性
     * @since 2.0.0
     */
    public void introduceBMR(){
        HealthMetricIntroduction.BMR.printIntroduction();
    }

    /**
     * 详解 TDEE 的定义、公式、功能、局限性
     * @since 2.0.0
     */
    public void introduceTDEE(){
        HealthMetricIntroduction.TDEE.printIntroduction();
    }

    /*  详解生理特征类健康指标 */
    /**
     * 详解 BSA 的定义、公式、功能、局限性
     * @since 2.0.0
     */
    public void introduceBSA(){
        HealthMetricIntroduction.BSA.printIntroduction();
    }


    // ==================== 私有辅助方法 ====================
}
