package com.github.existedname.healthcalculatorv2.util;

import com.github.existedname.healthcalculatorv2.service.Service;

public class BFRCalculator {
    /*      1. 私有化构造器      */
    private BFRCalculator(){
        System.out.println( "不用创建对象,直接使用即可!" );
    }


    /*      2. 计算 BFR 公有静态方法(核心功能)(按照公式名称首字母升序排列)      */
    /*  2.1 公式以 A 开头    */
    /*  2.2 公式以 B 开头    */
    /*  2.3 公式以 C 开头    */


    /*  2.4 公式以 D 开头    */
    // 2.4.1 Deurenberg 公式
    /*
     *                  Deurenberg Ⅰ式( 常见的 BMI 体脂率公式 )
     *  适用于 7-83 岁 & BMI 13.9-40.9 kg/m²,误差范围约 ± 4.1%,儿童公式需结合皮褶厚度验证
     *  参考网址1(豆瓣): https://www.douban.com/note/143202793/?_i=0229754BEbbOL4 ( 提供 Deurenberg 公式 )
     *  参考网址2(秘塔): https://metaso.cn/s/PN9Dosz ( P1 摘要部分,提供研究对象信息以及成人、儿童公式 )
     *  参考网址3(秘塔): https://metaso.cn/s/nSjfvfK ( P2 表 1,提供 Deurenberg Ⅰ、Ⅱ式等 4 种公式 )
     *  参考网址4(美豆芽养生网): https://www.meidouya.com/tool/tizhilv/ ( 提供 Deurenberg 公式、
     *          七点皮脂法公式、美国海军公式其他版本 )
     */
    public static double calcByDeurenbergEquation( String gender, int age, double bmi ){
        double bmiCoeff = 1.20, ageCoeff = 0.23, genderCoeff = -10.8, constTerm = -5.4;

        if ( age <= 15 ){
            // 参考网址2: 论文中表示,儿童( Children ) ≤ 15 岁( P4 表 5 下面第 1 行 )
                // By the way,成人( Adults ) ≥ 16 岁
            bmiCoeff = 1.51; ageCoeff = -0.70; genderCoeff = -3.6; constTerm = 1.4;
        }

        return getResultByGeneralFormula( gender, bmiCoeff, bmi, ageCoeff, age, genderCoeff, constTerm );
    }


    /*  2.5 公式以 E 开头    */
    /*  2.6 公式以 F 开头    */


    /*  2.7 公式以 G 开头    */
    // 2.7.1 Gallagher 公式
    /*
     *              Gallagher 公式
     *  适用于 18-61岁,BMI 18-35 kg/m²,误差范围约±5.4%
     *  参考网址1(秘塔): https://metaso.cn/s/nSjfvfK ( P2 表 1,提供 Gallagher 公式等 4 种公式 )
     *  参考网址2(秘塔): https://metaso.cn/s/JybElyp ( P74 表 2,提供 Gallagher 公式等 9 种公式 )
     *  参考网址3(秘塔): https://www.sciencedirect.com/science/article/pii/S0002916523067606?via%3Dihub
     *          ( 见 Dual-energy X-ray absorptiometry models 和 Four-compartment models 之间,提供不同的版本
     *            --以 1/BMI 为自变量之一,之后还有变式 )
     *  参考网址4(秘塔): https://metaso.cn/s/cRxPucJ ( P5 表 4,提供 Gallagher 公式等 7 种公式,其中 Gallagher
     *                 公式以 BMI 为自变量之一,并且系数精确到 2 位小数 )
     *  参考网址5(秘塔): https://metaso.cn/s/PvlbEUq ( P3 表 1,提供 Gallagher 公式等 6 种公式,其中 Gallagher
     *                 公式基于 1/BMI )
     *  参考网址6(秘塔): https://metaso.cn/s/Q0oeeW3 ( P5 表 2 下面第 10-11 行,提供基于 1/BMI 的公式 )
     */

    // 2.7.1.1 Gallagher 公式版本Ⅰ: 基于 BMI、年龄、性别
    public static double calcByGallagherEquation1( String gender, int age, double bmi ){
        double bmiCoeff = 1.46, ageCoeff = 0.14, genderCoeff = -11.61, constTerm = -10.02;
        return getResultByGeneralFormula( gender, bmiCoeff, bmi, ageCoeff, age, genderCoeff, constTerm );
    }

    // 2.7.1.1 Gallagher 公式版本Ⅱ: 基于 BMI 的倒数、年龄、性别
    public static double calcByGallagherEquation2( String gender, int age, double bmi ){
        /*                              参考网址5
        double bmiCoeff = -1097.8, ageCoeff = 0.053, genderCoeff = -20.6, constTerm = 95;// + 76;
        double reciprocalOfBMI = 1.0 / bmi; // 注意传参传 BMI 的倒数( reciprocal )
        return getResultByGeneralFormula( gender, bmiCoeff, reciprocalOfBMI, ageCoeff, age, genderCoeff, constTerm );
        */

        /*                                  选用参考网址6 提供的公式
         *  64.5 - 848 / BMI + 0.079 * age - 16.4 * gender + 0.05 * gender * age + 39 * gender / BMI
         */
        double bmiCoeff = -848, ageCoeff = 0.079, genderCoeff = -16.4, constTerm = 64.5;
        int intIsMan = Service.intIsMan( gender );
        return ( constTerm + bmiCoeff / bmi + ageCoeff * age + genderCoeff * intIsMan // 前半部分: 形式同计算模板
                 + 0.05 * intIsMan * age + 39.0 * intIsMan / bmi ); // 再补上后半部分
    }



    /*  2.8 公式以 H 开头    */
    /*  2.9 公式以 I 开头    */


    /*  2.10 公式以 J 开头   */
    // 2.10.1 Jackson-Pollock 公式
    /*
     *              Jackson-Pollock 公式
     *  备注: 这里又出现了公式冲突--参考网址1、2、5( 基于 BMI )与 3、4( 基于皮褶厚度 ),这里选用基于 BMI 的版本
     *  参考网址1(秘塔): https://metaso.cn/s/nSjfvfK ( P2 表 1,提供 Jackson-Pollock 公式等 4 种公式 )
     *  参考网址2(秘塔): https://metaso.cn/s/JybElyp ( P74 表 2,提供 Jackson-Pollock 公式等 9 种公式 )
     *  参考网址3(秘塔): https://metaso.cn/s/1xrJVp0 ( P3 表 1 提供基于 3 处皮褶厚度的 Jackson-Pollock 公式 )
     *  参考网址4(秘塔): https://metaso.cn/s/cRxPucJ ( P5 表 4,提供 Jackson-Pollock 公式等 7 种公式,其中 Jackson-Pollock
     *                 公式基于 BMI、年龄、性别 )
     *  参考网址5( 99 健康网 ): https://m.99.com.cn/a/1030198/ ( 提供基于 3 处皮褶厚度的 Jackson-Pollock 公式
     *              以及 BMI 公式、美国海军体脂率公式 )
     */

    // 2.10.1.1 Jackson-Pollock 公式版本Ⅰ: 基于 皮褶厚度
        // 由于要测多处皮褶厚度,太麻烦,弃用( 甚至皮褶公式都有 3 个版本: 3 点、4 点、7 点 )

    // 2.10.1.2 Jackson-Pollock 公式版本Ⅱ( 简化版本 ): 基于 BMI、年龄、性别
    public static double calcByJacksonPollockSimplifiedEquation( String gender, int age, double bmi ){
        double bmiCoeff = 1.61, ageCoeff = 0.13, genderCoeff = -12.1, constTerm = -13.9;
        return getResultByGeneralFormula( gender, bmiCoeff, bmi, ageCoeff, age, genderCoeff, constTerm );
    }


    /*  2.11 公式以 K 开头   */
    /*  2.12 公式以 L 开头   */
    /*  2.13 公式以 M 开头   */
    /*  2.14 公式以 N 开头   */
    /*  2.15 公式以 O 开头   */
    /*  2.16 公式以 P 开头   */
    /*  2.17 公式以 Q 开头   */
    /*  2.18 公式以 R 开头   */
    /*  2.19 公式以 S 开头   */
    /*  2.20 公式以 T 开头   */


    /*  2.21 公式以 U 开头   */
    // 2.21.1 USN 公式
    /**     方法注释模板
     *      描述方法功能,可以给出清晰公式
     * @param 参数注释,说明参数意义并强调参数注意事项
     * @return 描述方法的返回值
     * @throws IllegalArgumentException 当...时抛出...( 具体异常 )
     * @author // 可选
     * @since 创建该方法的时间或者项目版本号   // 可选
     * @deprecated 弃用说明     // 可选,用于标记废弃方法并给出替代方案
     */

    /**
     *  使用美国海军体脂率公式求体脂率
     *  <pre>
     *      男: 86.010 * lg( 腰围 - 颈围 ) - 70.041 * lg( 身高inch ) + 36.76
     *
     *      女: 163.205 * lg( 腰围 + 臀围 - 颈围 ) - 97.684 * lg( 身高cm ) - 78.387
     *  </pre>
     *      ❗注意: 该公式基于英寸,身高、各种围度均为英寸( 1 inch = 2.54 cm ),这里的处理办法是要求传入
     *            cm,之后转换为 inch
     *  @param gender 性别
     *  @param waistCircumCm 腰围( cm )
     *  @param hipCircumCm 臀围( cm )
     *  @param neckCircumCm 颈围( cm )
     *  @param heightCm 身高( cm )
     *
     *  @return 返回体脂率( 默认带百分号 % )
     *  @throws IllegalArgumentException 无
     *
     *  @author <a href="https://github.com/existed-name"> existed-name </a>
     */
    public static double calcByUSNEquation( String gender, double waistCircumCm, double hipCircumCm, double neckCircumCm, double heightCm ){
        /*
         *              USN: the United State Navy 美国海军公式/美国海军体脂测量法
         *  适合普通成年男性，误差约±3%,使用对数计算，需测量腰围、颈围、臀围和身高
         *  参考网址1(秘塔): https://metaso.cn/s/WgDeQZt ( P16, 3.1.3 Body Fat Calculator Activity
         *          第 3 段 4-7 行,提供男女公式 )
         *  参考网址2(百度健康): https://health.baidu.com/m/detail/ar_6339450521987342204 ( 提供男性公式 )
         *  参考网址3(豆包): https://www.doubao.com/chat/9220767740041730 ( 提供男女公式、注意单位 )
         */

        // 默认男性
        double coeff1 = 86.010, coeff2 = -70.041, constTerm = 36.76; // 系数1, 系数2, 常数项
        double circumferenceCm = waistCircumCm - neckCircumCm; // 系数 1 对应的对数的底数

        if ( gender.equals( "女" ) ){
            coeff1 = 163.205; coeff2 = 97.684; constTerm = -78.387;
            circumferenceCm += hipCircumCm; // 女生需要补上臀围
        }
        return (  coeff1 * Math.log10( Service.getInchByCm( circumferenceCm ) ) +
                coeff2 * Math.log10( Service.getInchByCm( heightCm ) ) + constTerm  );
    }


    /*  2.22 公式以 V 开头   */
    /*  2.23 公式以 W 开头   */
    /*  2.24 公式以 X 开头   */
    /*  2.25 公式以 Y 开头   */
    /*  2.26 公式以 Z 开头   */



    /*      3. 其他公有静态方法     */
    // 3.1 main 方法测试
    public static void main( String[] args ){
        String gender = "男";
        int age = 19;
        double bmi = 18;
        System.out.println( BFRCalculator.calcByDeurenbergEquation( gender, age, bmi ) );
        System.out.println( BFRCalculator.calcByGallagherEquation1( gender, age, bmi ) );
        System.out.println( BFRCalculator.calcByGallagherEquation2( gender, age, bmi ) );
        System.out.println( BFRCalculator.calcByJacksonPollockSimplifiedEquation( gender, age, bmi ) );
    }



    /*      4. 私有静态方法(内部辅助)     */
    // 4.1 BFR 计算通式--符合多数公式的形式,只需传入参数(自变量、系数、计算单位)即可
    private static double getResultByGeneralFormula( String gender, double bmiCoeff, double bmi, double ageCoeff, double age, double genderCoeff, double constTerm ){
        return ( bmiCoeff * bmi + ageCoeff * age + genderCoeff * Service.intIsMan( gender ) + constTerm );
                    // 除了创建对象(类的实例),同一个包,可以直接 类.非私有方法
    }
}
