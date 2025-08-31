package com.github.existedname.healthcalculatorv3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户身体参数档案:
 * <p>
 *     <pre>
 *     1. 基本身体数据
 *      基础数据( 性别, 年龄, 体重, 身高 )、围度( 腰围, 臀围, 肩宽, 臂围 )、活动系数
 *     2. 计算得到的健康指标
 *      (1)体态评估类: BMI, WHR, BFR, BRI
 *      (2)能量代谢类: BMR, TDEE
 *      (3)生理特征类: BSA
 *     </pre>
 * </p>
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateTime 2025/8/26 14:06
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBodyProfile {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================
    /*      基本身体数据      */
    /** 性别 */
    private String gender;
    /** 年龄 */
    private int age;
    /** 体重( 单位: kg ) */
    private double weight;
    /** 身高( 单位: cm ) */
    private double height;
    /** 腰围( 单位: cm ) */
    private double waistCircumference;
    /** 臀围( 单位: cm ) */
    private double hipCircumference;
    /** 颈围( 单位: cm ) */
    private double neckCircumference;
    /** 臂围( 单位: cm ) */
    private double armCircumference;
    /** 活动系数( 无单位 ) */
    private double activityCoefficient;


    /*      计算得出的健康指标       */
    // 体态评估类健康指标
    /** BMI( Body Mass Index ): 身体质量指数( 单位: kg/m² ) */
    private double bmi;
    /** WHR( Waist-Hip Ratio ): 腰臀比( 无单位, 但注意转为 % 需要乘以 100 ) */
    private double whr;
    /** BFR( Body Fat Rate ): 体脂率( 无单位, 但注意转为 % 需要乘以 100 ) */
    private double bfr;
    /** BRI( Body Roundness Index ): 身体圆度指数( 无单位 ) */
    private double bri; // body roundness index 身体圆度指数

    // 能量代谢类健康指标
    /**
     * BMR( Basal Metabolic Rate ): 基础代谢率( 单位: kcal/day ) <br>
     * 注意: 通俗说的 BMR 是以 day 为单位时间,即 1 天的基础代谢
     */
    private double bmr;
    /** TDEE( Total Daily Energy Expedite ): 每日总能量消耗( 单位: kcal/day ) */
    private double tdee;

    // 生理特征类健康指标
    /** BSA( Body Surface Area ): 体表面积( 单位: m² ) */
    private double bsa;



    // ==================== 构造器 ====================
    public UserBodyProfile( String gender, int age ){
        this.gender = gender;
        this.age = age;
    }

    public UserBodyProfile( double weight, double height ){
        this.weight = weight;
        this.height = height;
    }

    public UserBodyProfile( String gender, int age, double weight, double height ){
        this( gender, age );
        this.weight = weight;
        this.height = height;
    }


    // ==================== 公有方法 ====================
    /*      核心业务方法      */
    /*
     *      getter/setter
     *      lombok 不好处理缩略词, 会出现 getBmi、setBmi 的情况,
     *  跟实际期望的 getBMI、setBMI 不一致, 所以这里为缩略词手动添加
     *  getter/setter
     */
    // 体态评估类健康指标
    public double getBMI() {
        return bmi;
    }
    public double getWHR() {
        return whr;
    }
    public double getBFR() {
        return bfr;
    }
    public double getBRI() {
        return bri;
    }

    // 能量代谢类健康指标
    public double getBMR() {
        return bmr;
    }
    public double getTDEE() {
        return tdee;
    }

    // 生理特征类健康指标
    public double getBSA() {
        return bsa;
    }


    // 体态评估类健康指标
    public void setBMI(double BMI) {
        this.bmi = BMI;
    }
    public void setWHR( double whr ) {
        this.whr = whr;
    }
    public void setBFR( double bfr ) {
        this.bfr = bfr;
    }
    public void setBRI( double bri ) {
        this.bri = bri;
    }

    // 能量代谢类健康指标
    public void setBMR( double bmr ) {
        this.bmr = bmr;
    }
    public void setTDEE( double tdee ) {
        this.tdee = tdee;
    }

    // 生理特征类健康指标
    public void setBSA( double bsa ) {
        this.bsa = bsa;
    }


    /*      重写方法        */



    // ==================== 私有辅助方法 ====================

}
