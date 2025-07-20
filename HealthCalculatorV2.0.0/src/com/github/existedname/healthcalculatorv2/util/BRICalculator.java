package com.github.existedname.healthcalculatorv2.util;

public class BRICalculator {
    /*      1. 私有化构造器      */
    private BRICalculator(){
        System.out.println( "不用创建对象,直接使用即可!" );
    }



    /*      2. 计算 BRI 公有静态方法(核心功能)      */
    /*
     *  参考网址1( Calculator Lib ): https://calculatorlib.com/zh/bri-calculator ( 提供 BRI 直观计算公式 )
     *  参考网址2( 知乎 ): https://zhuanlan.zhihu.com/p/15483371250 ( 提供不太直观的 BRI 公式 )
     *  参考网址3( BRI-Calculator.net ): https://bri-calculator.net/zh/ ( 提供 BRI 直观计算公式、相关数据及分析 )
     *  形式: BRI = 364.2 - 365.5 * 根号下( 1 - 分式² ),
     *       分式 = ( 腰围 / 2PI ) / ( 0.5 * 身高 ) = 腰围 / ( PI * 身高 )
     *  注意腰围、身高单位 cm
     */
    public static double getBRI( double waistCircumCm, double heightCm ){
        double fraction = waistCircumCm / Math.PI / heightCm; // PI = Math.PI = Math.acos( -1 )
        return (  364.2 - 365.5 * Math.sqrt( 1 - Math.pow( fraction, 2 ) )  );
    }
}
