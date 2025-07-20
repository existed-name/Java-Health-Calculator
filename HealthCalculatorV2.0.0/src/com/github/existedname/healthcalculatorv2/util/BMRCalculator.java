package com.github.existedname.healthcalculatorv2.util;

import com.github.existedname.healthcalculatorv2.service.Service;
/**             类注释模板
 *      简述类的核心功能,可以详细描述设计思路、使用场景、内部结构
 *  @author 作者名( 多个作者需要多个标签 )/组织名/邮箱/超链接    // 可选
 *  @since 创建该类的时间或者项目版本号
 *  @version 当前项目版本号
 *  @see 内部相关类的名称( 父类、实现接口 )/外部文档链接     // 可选
 *  @deprecated 弃用说明    // 可选,用于在该类过时的时候给出替代方案
 *      // 使用 @deprecated 再调用相关方法时,会警告该类已经被弃用
 */

/**
 *      使用多种公式计算 BMR
 *  @author <a href="https://github.com/existed-name" > existed-name </a>
 *  @since 2025-6-16( v2.0.0 )
 *  @version 2.0.0
 */

public class BMRCalculator {
    /*      1. 私有静态成员变量       */
    // 1.1 热量计算单位,最终要化为 Kcal
    private static String heatUnitKcal = "Kcal";
    private static String heatUnitcal = "cal";
    private static String heatUnitKJ = "KJ";
    private static String heatUnitJoule = "J";



    /*      2. 计算 BMR 公有静态方法(核心功能)(按照公式名称首字母升序排列)     */
    /*  2.1 公式以 A 开头    */


    /*  2.2 公式以 B 开头    */
    // 2.2.1 BSA( 体表面积 )计算法
    /*
     *                  BSA: body surface area,体表面积
     *  参考网址1(知乎): https://zhuanlan.zhihu.com/p/26465848 ( 提供 BSA 计算公式、基础代谢率对照表 )
     *  参考网址2(豆包): https://www.doubao.com/chat/9220767740041730 ( 提供由 BSA 求每日 BMR 公式 )
     *      BMR( Basal Metabolic Rate,基础代谢率 ): 人体在基础代谢状态下,每小时每平方米体表面积(
     *  或每公斤体重)的能量消耗,通常以每日( kcal/day )或每小时( kcal/h )表示，但实际应用中多以每日
     *  为单位,于是就相当于 BM( Basal Metabolic,基础代谢 )
     */
    public static double calcByBSA( String gender, int age, double bsa ){
        // 每小时的基础代谢率,单位 Kcal/( h * m² )
        double[][] hourlyBMRs = new double[][] {
                {   // women
                        53.0, 51.2, 48.4, 45.4, 42.8, // [ 0,11 )岁 对应年龄1、3、5、7、9岁
                        42.0, 40.3, 37.9, 36.3, 35.5, // [ 11,20 )岁 对应年龄11、13、15、17、19岁
                        35.3, 35.2, 35.1, 35.0, 34.9, // [ 20,40 )岁 对应年龄20、25、30、35、40岁
                        34.5, 33.9, 33.3, 32.7, 32.2, // [ 40,65 )岁 对应年龄45、50、55、60、65岁
                        31.7, 31.3, 30.9, // [ 65,80+ )岁 对应年龄70、75、80岁
                },
                {   // men
                        53.0, 51.3, 49.3, 47.3, 45.2, // [ 0,11 )岁  对应年龄1、3、5、7、9岁
                        43.0, 42.3, 41.8, 40.8, 39.2, // [ 11,20 )岁 对应年龄11、13、15、17、19岁
                        38.6, 37.5, 36.8, 36.5, 36.3, // [ 20,40 )岁 对应年龄20、25、30、35、40岁
                        36.2, 35.8, 35.4, 34.9, 34.4, // [ 40,65 )岁 对应年龄45、50、55、60、65岁
                        33.8, 33.2, 33.0, // [ 65,80+ )岁 对应年龄70、75、80岁
                },
        };

        // 根据性别、年龄查找对应的每小时基础代谢率
        int ageIdx = 10; // 默认 20 岁
            // 找规律: [ 0,20 ) 以 2 为间隔, [ 20,80+ ) 以 5 为间隔
        if ( age >= 20 ){
            if ( age >= 80 ){
                ageIdx = 22;
            } else {
                ageIdx = age / 5 + 6;
            }
        } else {
            ageIdx = age / 2;
        }

        int isMan = Service.intIsMan( gender );
        double hourlyBMR = hourlyBMRs[ isMan ][ ageIdx ];

        // 一段时间的基础代谢 = 每小时(单位时间)基础代谢率 * 体表面积 * 时间
        double dailyBMR = hourlyBMR * bsa * 24; // 24 hour
        return dailyBMR;
    }


    /*  2.3 公式以 C 开头    */
    /*  2.4 公式以 D 开头    */
    /*  2.5 公式以 E 开头    */
    /*  2.6 公式以 F 开头    */
    /*  2.7 公式以 G 开头    */


    /*  2.8 公式以 H 开头    */
    // 2.8.1 Henry 公式
    /*
     *                          Henry 公式
     *      适用于 18-60 岁,对中国女性个体和北方个体误差更小,对青少年、老年人误差较大
     *  参考网址1(秘塔): https://metaso.cn/s/DADUjhT ( 见 P3 表 1,提供 18-60 岁不常见版本,MJ )
     *      此版本在文献表格中叫"Henry and Rees 公式",算出来比常见 Henry 公式可能低 5% 以上
     *      女: 48W + 2562( 18-30 岁),48W + 2448( 30-60 岁)
     *      男: 56W + 2800( 18-30 岁),46W + 3160( 30-60 岁)
     *      这里采用常见版本
     *  参考网址2(搜狐): https://www.sohu.com/a/221960937_678883 ( 提供 18-60 岁常见版本,KJ )
     *  参考网址3(知乎): https://zhuanlan.zhihu.com/p/344064435 ( 提供 18-60 岁常见版本,KJ,
     *      并进行了 11 种公式结果与实际 BMR 对比 )
     *      由于只有 18-60 岁对应的公式,所以分为 [ 0,30 ),[ 30,75+ ) 2 部分处理,建议 [ 0,18 ) 以及
     *  60+ 岁优先考虑其他公式
     */
    public static double calcByHenryEquation( String gender, int age, double weightKg ) {
        // 体重系数(千焦)
        double[][] weightCoeffs = new double[][]{
                {   // women, [ 18,30 )岁为 47,[ 30,60 )岁为 39
                        47, // 0-3 year
                        47, // 3-10 year
                        47, // 10-18 year
                        47, // 18-30 year
                        39, // 30-60 year
                        39, // >= 60 year
                        39, // 75+
                },
                {   // men, [ 18,30 ) 51,[ 30,60 ) 53
                        51, // 0-3 year
                        51, // 3-10 year
                        51, // 10-18 year
                        51, // 18-30 year
                        53, // 30-60 year
                        53, // >= 60 year
                        53, // 75+
                },
        };

        // 常量系数(KJ)
        double[][] constCoeffs = new double[][]{
                {   // women, [ 18,30 ) 2880,[ 30,60 ) 3070
                        2880, // 0-3 year
                        2880, // 3-10 year
                        2880, // 10-18 year
                        2880, // 18-30 year
                        3070, // 30-60 year
                        3070, // >= 60 year
                        3070, // 75+
                },
                {   // men, [ 18,30 ) 3500,[ 30,60 ) 3070
                        3500, // 0-3 year
                        3500, // 3-10 year
                        3500, // 10-18 year
                        3500, // 18-30 year
                        3070, // 30-60 year
                        3070, // >= 60 year
                        3070, // 75+
                },
        };

        // 根据性别、年龄查找对应系数
        int isMan = Service.intIsMan( gender );
        int ageIdx = getAgeIdx( age );

        double weightCoeff = weightCoeffs[ isMan ][ ageIdx ];
        double constCoeff = constCoeffs[ isMan ][ ageIdx ];

        // 往通式中传入参数: 变量、系数、单位
        double bmr = getResultByGeneralFormula( weightKg, weightCoeff, 0, 0,
                    0, 0, constCoeff, heatUnitKJ );
        return bmr;
    }

    // 2.8.2 Harris-Benedict( H-B ) 公式
    /*
     *                  Harris-Benedict( H-B ) 哈里斯-本尼迪克特公式
     *  适用于 18-60 岁,对老年人误差较大
     *  参考网址1(智医汇): https://www.doctor-network.com/Public/LittleTools/3.html ( 提供比常见版本精确的系数 )
     *  参考网址2(秘塔): https://metaso.cn/s/DADUjhT ( 见 P3 表 1,提供精确到小数点后 4 位的系数 )
     *  参考网址3(搜狐): https://www.sohu.com/a/221960937_678883 ( 提供旧版、修订版常见公式)
     */
    public static double calcByHBEquation(String gender, int age, double weightKg, double heightCm) {
        // 单位: Kcal
        double weightCoeff = 0, heightCoeff = 0, ageCoeff = 0, constCoeff = 0;

        switch ( gender ) {
            case "女" : // if ( gender.equals( "女" )
                constCoeff = 665.0955;
                weightCoeff = 9.5634;
                heightCoeff = 1.8496;
                ageCoeff = -4.6756;
                break;
            case "男" :
                constCoeff = 66.4730;
                weightCoeff = 13.7516;
                heightCoeff = 5.0033;
                ageCoeff = -6.7750;
                break;
            default :
                System.out.println( "性别错误!请重新输入!" );
                return 0;
        }

        // 往通式中传入参数: 变量、系数、单位
        double bmr = getResultByGeneralFormula( weightKg, weightCoeff, heightCm, heightCoeff, age, ageCoeff, constCoeff, heatUnitKcal );
        return bmr;
    }


    /*  2.9 公式以 I 开头    */
    /*  2.10 公式以 J 开头   */


    /*  2.11 公式以 K 开头   */
    // 2.11.1 Karch-McArdle 公式( 依据瘦体重 )
    /*
     *          Karch-McArdle 卡奇-麦卡德尔公式( Inbody 体成分仪内置公式--类似 Cunningham 公式)
     *  需结合身体成分数据,适用: 肌肉量异常者(运动员、老年人、肥胖/消瘦者)
     *  参考网址1(搜狐): https://www.sohu.com/a/221960937_678883 ( 提供原公式以及 Cunningham )
     *  参考网址2(知乎): https://zhuanlan.zhihu.com/p/344064435 ( 提供 2 种公式 )
     *  参考网址3(秘塔): https://metaso.cn/s/SO1ao2B ( P4 表 1,提供 2 种公式 )
     *  参考网址4(知乎): https://zhuanlan.zhihu.com/p/76241592 (提供 FFM 计算方法 )
     */
    public static double calcByKarchMcArdleEquation( double weightKg, double bfr ){
        /*
         *  BFR: body fate rate 体脂率 = 身体脂肪总重量 ÷ 体重 = ( 1 - 去脂体重 ) ➗ 体重)
         *  去掉体脂 → 去脂体重( FFM,Fat-Free Mass ) = 体重 * ( 1 - 体脂率 )
         *  注意: 公式算出的 BFR 自带 %,也就是 ∈ [ 0, 100 ],需要除以 100 转换为小数的体脂率
         */
        double ffm = weightKg * ( 1 - bfr / 100.0 );
        double bmr = 370 + 21.6 * ffm;
        return bmr;
    }


    /*  2.12 公式以 L 开头   */

    /*  2.13 公式以 M 开头   */
    // 2.13.1 Mifflin-St Jeor( MSJ ) 公式
    /*
     *          Mifflin-St Jeor( MSJ ) 米夫林-圣杰奥尔公式
     *  适用于所有成人(>=18岁),误差最小,被 ADA 和 ASCN 推荐
     *  参考网址1(澎湃新闻): https://m.thepaper.cn/newsDetail_forward_25496967 ( 提供精确系数 )
     *  参考网址2(知乎): https://www.zhihu.com/question/551650901/answer/2662411450 ( 提供常见版本 )
     */
    public static double calcByMSJEquation(String gender, int age, double weightKg, double heightCm) {
        double weightCoeff = 9.99, heightCoeff = 6.25, ageCoeff = -4.92;
        double constCoeff = 0;

        switch (gender) {
            case "男": // if ( gender.equals( "男" )
                constCoeff = 5;
                break;
            case "女":
                constCoeff = -161;
                break;
            default:
                System.out.println( "性别错误!请重新输入!" );
                return 0;
        }

        // 往通式中传入参数: 变量、系数、单位
        double bmr = getResultByGeneralFormula( weightKg, weightCoeff, heightCm, heightCoeff, age, ageCoeff, constCoeff, "Kcal" );
        return bmr;
    }

    // 2.13.2 毛德倩公式
    /*
     *          Mao: MaoDeqian 毛德倩公式
     *  适用于20-45岁中国人群,误差较小
     *  参考网址(知乎): https://www.zhihu.com/question/551650901/answer/2662411450 ( 提供原公式 )
     */
    public static double calcByMaoEquation( String gender, double weightKg ){
        // 单位: KJ
        double weightCoeff = 0, constCoeff = 0;

        switch ( gender ) {
            case "女" : // if ( gender.equals( "女" )
                constCoeff = 2269.1;
                weightCoeff = 41.9;
                break;
            case "男" :
                constCoeff = 2954.7;
                weightCoeff = 48.5;
                break;
            default :
                System.out.println( "性别错误!请重新输入!" );
                return 0;
        }

        // 往通式中传入参数: 变量、系数、单位
        double bmr = getResultByGeneralFormula( weightKg, weightCoeff, 0, 0, 0, 0, constCoeff, heatUnitKJ );
        return bmr;
    }


    /*  2.14 公式以 N 开头   */
    /*  2.15 公式以 O 开头   */
    /*  2.16 公式以 P 开头   */
    /*  2.17 公式以 Q 开头   */
    /*  2.18 公式以 R 开头   */

    /*  2.19 公式以 S 开头   */
    // 2.19.1 Schofield 公式
    /*
     *                      Schofield 公式
     *  推荐: 18-60岁人群,尤其适合中国健康成人;修订版(WHO暂用标准)提供儿童青少年专用公式
     *  参考网址1(知乎): https://zhuanlan.zhihu.com/p/26465848 ( 提供 0-75+ 岁 )
     *  参考网址2(秘塔): https://metaso.cn/s/71zYdzU ( 见 P38,提供 10-75+ 岁 )
     *  参考网站3(Kimi): https://www.kimi.com/chat/d180ubnf2endcoak5glg ( 提供 10-60+ 岁)
     *  参考网址4(秘塔): https://metaso.cn/s/ztGisdG ( 见 P5 表 1,提供 18-60 岁 )
     *  参考网址5(秘塔): https://metaso.cn/s/mylWMKi ( 见 P3 表 2,提供 0-18 岁 )
     */
    public static double calcBySchofieldEquation(String gender, int age, double weightKg, double heightCm) {
        // 体重系数(千卡/大卡)
        double[][] weightCoeffs = new double[][]{
                { // women
                    60.1, // 0-3 year
                    22.5, // 3-10 year
                    12.2, // 10-18 year
                    14.8, // 18-30 year
                    8.3, // 30-60 year
                    9.2, // 60-75 year
                    9.8, // 75+
                },
                { // men
                    60.9, // 0-3 year
                    22.7, // 3-10 year
                    17.5, // 10-18 year
                    15.1, // 18-30 year
                    11.5, // 30-60 year
                    11.9, // 60-75 year
                    8.4, // 75+
                },
        };

        // 常量系数(Kcal)
        double[][] constCoeffs = new double[][]{
                { // women
                    -51, // 0-3 year
                    499, // 3-10 year
                    746, // 10-18 year
                    487, // 18-30 year
                    846, // 30-60 year
                    687, // 60-75 year
                    624, // 75+
                },
                { // men
                    -54, // 0-3 year
                    495, // 3-10 year
                    651, // 10-18 year
                    692, // 18-30 year
                    873, // 30-60 year
                    700, // 60-75 year
                    821, // 75+
                },
        };

        // 根据性别、年龄查找对应系数
        int isMan = Service.intIsMan( gender );
        int ageIdx = getAgeIdx( age );

        double weightCoeff = weightCoeffs[ isMan ][ ageIdx ];
        double constCoeff = constCoeffs[ isMan ][ ageIdx ];

        // 往通式中传入参数: 变量、系数、单位
        double bmr = getResultByGeneralFormula( weightKg, weightCoeff, 0, 0, 0, 0, constCoeff, heatUnitKcal );
        return bmr;
    }

    // 2.19.2 Shizgal-Rosa 公式
    /*
     *                      Shizgal-Rosa 公式
     *  参考网址1(搜狐): https://www.sohu.com/a/221960937_678883 ( 提供原公式 )
     *  参考网址2(知乎): https://zhuanlan.zhihu.com/p/344064435 ( 提供原公式 )
     */
    public static double calcByShizgalRosaEquation( String gender, int age, double weightKg, double heightCm ){
        // 单位: KJ
        double weightCoeff = 0, heightCoeff = 0, ageCoeff = 0, constCoeff = 0;

        switch ( gender ) {
            case "女" : // if ( gender.equals( "女" )
                constCoeff = 1873;
                weightCoeff = 39;
                heightCoeff = 13;
                ageCoeff = -18;
                break;
            case "男" :
                constCoeff = 370;
                weightCoeff = 52;
                heightCoeff = 20;
                ageCoeff = -25;
                break;
            default :
                System.out.println( "性别错误!请重新输入!" );
                return 0;
        }

        // 往通式中传入参数: 变量、系数、单位
        double bmr = getResultByGeneralFormula( weightKg, weightCoeff, heightCm, heightCoeff, age, ageCoeff, constCoeff, heatUnitKJ );
        return bmr;
    }


    /*  2.20 公式以 T 开头   */
    /*  2.21 公式以 U 开头   */
    /*  2.22 公式以 V 开头   */
    /*  2.23 公式以 W 开头   */
    /*  2.24 公式以 X 开头   */
    /*  2.25 公式以 Y 开头   */
    /*  2.26 公式以 Z 开头   */



    /*      3. 其他公有静态方法     */
    // 3.1 换单位: Joule → calorie
    /*
     *  参考网址(百度百科): https://baike.baidu.com/item/%E5%8D%A1%E8%B7%AF%E9%87%8C/284236
     *  卡路里有 2 种定义,1 种是 1 cal ≈ 4.184 J,1 种是 1 cal ≈ 4.186 J(4.1858518),
     *      这里取前者
     */
    public static double getCalorieByJoule( double joule ){
        // 换算率: 1 cal = 4.184 J, 1 Kcal = 1000 cal, 1 KJ = 1000 J
        double conversionRate = 4.184;
        return ( joule / conversionRate );
    }

    // 3.2 根据输入热量单位统一成 Kcal
    public static double getKcal( double heatQuantity, String heatUnit ){
        switch ( heatUnit ){
            case "calorie" :
            case "cal" :
                heatQuantity /= 1000; // 卡路里 → 千卡
                break;
            case "KiloCalorie" :
            case "KiloCal" :
            case "KCalorie" :
            case "Kcal" :
                break; // 已经是千卡
            case "joule" :
            case "Joule" :
            case "J" :
                heatQuantity /= 1000; // 焦耳 → 千焦
                // break; 利用击穿性跳到下一步,将千焦化为千卡
            case "KJoule" :
            case "KiloJoule" :
            case "KJ" :
                heatQuantity = getCalorieByJoule( heatQuantity ); // 千焦 → 千卡
                break;
            default :
                System.out.println( "你输入的热量单位有误,请重新输入单位!" );
                System.out.println( "比如: Kcal, cal, KJ, J" );
                // break; 之后没有代码块可以执行,于是 switch 在这里就会自然结束,而不需要 break
        }

        return heatQuantity; // Kcal
    }



    /*      4. 私有静态方法(内部辅助)     */
    // 4.1 BMR 计算通式--符合多数公式的形式,只需传入参数(自变量、系数、计算单位)即可
    /*
     *  大部分公式其实就是这个格式: 体重系数 * 体重(kg) + 身高系数 * 身高(cm) + 年龄系数 * 年龄(岁) + 常数项
     *  无非就是可能某些系数为 0,于是只需要往这个"通式"传入相应的参数(系数、变量)即可
     */
    private static double getResultByGeneralFormula( double weightKg, double weightCoeff, double heightCm, double heightCoeff, int age, double ageCoeff, double constCoeff, String heatUnitKcal ){
        double bmr = weightCoeff * weightKg + heightCoeff * heightCm + ageCoeff * age + constCoeff;
        // 根据输入的单位检查是否要换单位 => 统一成 Kcal
        bmr = getKcal( bmr, heatUnitKcal );
        return bmr;
    }

    // 4.2 根据年龄在特有的年龄分段数组中找出该年龄对应下标
    private static int getAgeIdx( int age ) {
        /*
         *  注: a-b 通常表示左闭右开 [ a,b )
         *  ageIdx -- 年龄
         *    0     0-3 year
         *    1     3-10 year
         *    2     10-18 year
         *    3     18-30 year
         *    4     30-60 year
         *    5     60-75 year
         *    6     75+
         */

        // 根据年龄查找对应系数
        int ageIdx = 3; // 默认 18-30 岁(最常用)
        if (age >= 30) {
            if (age >= 75) {
                ageIdx = 6;
            } else if (age >= 60) {
                ageIdx = 5;
            } else { // [ 30, 60 )
                ageIdx = 4;
            }
        } else if (age < 18) {
            if (age >= 10) {
                ageIdx = 2;
            } else if (age >= 3) {
                ageIdx = 1;
            } else { // [ 0, 3 )
                ageIdx = 0;
            }
        }

        return ageIdx;
    }



    /*      5. 私有化构造器,阻止创建对象     */
    private BMRCalculator() {
        System.out.println( "不用创建对象,直接使用即可!" );
    }
}