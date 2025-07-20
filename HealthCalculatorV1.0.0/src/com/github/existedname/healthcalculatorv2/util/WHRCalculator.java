package com.github.existedname.healthcalculatorv2.util;

public class WHRCalculator {
    /*      1. 私有化构造器      */
    private WHRCalculator(){
        System.out.println( "不用创建对象,直接使用即可!" );
    }



    /*      2. 计算 WHR 公有静态方法( 唯一公式 )     */
    public static double getWHR( double wasitCircumCm, double hipCircumCm ){
        return wasitCircumCm / hipCircumCm; // 腰臀比 = 腰围 / 臀围
    }
}
