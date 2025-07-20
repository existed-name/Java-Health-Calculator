package com.github.existedname.healthcalculatorv2.util;


import com.github.existedname.healthcalculatorv2.service.Service;

public class BMICalculator {
    /*      1. 私有化构造器     */
    // 避免(无需)创建实例(对象)
    private BMICalculator() {
        System.out.println( "不用创建对象,直接使用即可!" );
    }



    /*      2. 计算公式( 唯一 )       */
    public static double getBMI( double weightKg, double heightMetre ){
        // 检查单位 cm 化 m
        if ( heightMetre / 10 > 0 ) heightMetre = Service.getMetreByCm( heightMetre );
        return ( weightKg / heightMetre / heightMetre );
    }



    /*      3. 其他公有静态方法     */
    // 3.1 用 BMI、身高 倒推 体重( 用于根据理想 BMI 计算理想体重 )
    public static double getWeightKgByBMI( double heightCm, double bmi ){
        // BMI = W(kg) / H(m)² => W = BMI * H²
        double heightMetre = Service.getMetreByCm( heightCm ); // 注意换单位
        return bmi * heightMetre * heightMetre;
    }

    // 3.2 用 BMI、体重 倒推 身高
    public static double getHeightCmByBMI( double weightKg, double bmi ){
        // BMI = W(kg) / H(m)² => H = ( W / BMI )^( 1/2 )
        double heightMetre = Math.sqrt( weightKg / bmi );
        return Service.getCmByMetre( heightMetre ); // 注意换单位
    }
}