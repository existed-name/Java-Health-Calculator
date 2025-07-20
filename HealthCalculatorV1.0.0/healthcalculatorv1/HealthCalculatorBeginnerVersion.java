package com.github.existedname.healthcalculatorv1;

/*         健康计算器
 *     开发一个简单的健康计算器应用程序,它可以接受用户的输入(如年龄、性别、体重、身高),并
 *  计算出用户的BMI(身体质量指数)和基础代谢率(BMR)
 *          需求分析
 *  1. 让用户输入个人信息:年龄、性别、体重、身高
 *  2. 设计方法计算 BMI
 *      BMI = 体重(kg) / 身高(m)²
 *  3. 设计方法计算 BMR
 *      Mifflin-St Jeor公式(较新,更精准)
 *      男: BMR = ( 10 * 体重kg ) + ( 6.25 * 身高cm ) - ( 5 * 年龄 ) + 5
 *      女: BMR = ( 10 * 体重kg ) + ( 6.25 * 身高cm ) - ( 5 * 年龄 ) - 161
 *  4. 分析用户 BMI 情况
 *  5. 分析用户 BMR 情况
 */

import java.util.Scanner;

public class HealthCalculatorBeginnerVersion {
    /*
     *          Java 中的"全局变量"/宏定义
     *      在类中直接创建的成员变量,不加 static 表示类及其实例
     *  (也就是对象)都有 1 份成员变量,加上 static 则只能在类中使
     *  用该成员变量(也就是总共只有 1 份)
     */
    static String tabKey = "\t";
    static String tabKey2 = "\t\t";
    static String tabKey3 = "\t\t\t";
    static String horizonalBar4 = "----";
    static String doubleHorizonalBar4 = "====";
    static String doubleHorizonalBar8 = "====" + "====";
    static String doubleHorizonalBar12 = "====" + "====" + "====";

    public static void main(String[] args) {
        mainMethod();
    }

    public static void mainMethod() {
        Scanner scanner = new Scanner( System.in );

        int ageYear;
        String gender;
        double weightKg, heightCm;

        calculatorIntro();

        // 1. 让用户输入个人信息:年龄、性别、体重、身高
        System.out.print( "请输入你的年龄(岁):\t" );
        ageYear = scanner.nextInt();
        System.out.print( "请输入你的性别(男/女):\t" );
        gender = scanner.next();
        System.out.print( "请输入你的体重(kg):\t" );
        weightKg = scanner.nextDouble();
        System.out.print( "请输入你的身高(cm):\t" );
        heightCm = scanner.nextDouble();
        newLine();

        // 2. 设计方法计算 BMI
        /*
            如果类里面的方法没加 static => 这个类及其对象都有一份 => 需要创建对象再进行调用
            HealthCalculatorStarterEdition calc = new HealthCalculatorStarterEdition();
            double BMI = calc.getBMI();
         */
        double BMI = getBMI( weightKg, heightCm );

        // 3. 设计方法计算 BMR
        double BMR = getBMR( ageYear, gender, weightKg, heightCm );

        // 4. 分析用户 BMI 情况
        tempPause();
        analyzeBMI( BMI );

        // 5. 分析用户 BMR 情况
        tempPause();
        analyzeBMR( BMR );

        scanner.close();
    }

    // 介绍健康计算器
    public static void calculatorIntro(){
        System.out.println( doubleHorizonalBar12 + "健康计算器" + doubleHorizonalBar12 );
        System.out.println( tabKey + horizonalBar4 + "通过获取用户的身体数据" + horizonalBar4 );
        System.out.println( horizonalBar4 + "计算 BMI(body mass index,身体质量指数)" + horizonalBar4 );
        System.out.println( horizonalBar4 + "以及 BMR(basal metabolic rate,基础代谢率)" + horizonalBar4 );
        System.out.println( tabKey + horizonalBar4 + "并给出相应的评估" + horizonalBar4 );
        newLine();
        tempPause();
    }

    // 计算 BMI
    public static double getBMI( double weightKg, double heightCm ){
        /*      BMI = 体重(kg) / 身高(m)²       */
        heightCm /= 100.0; // cm → m
        return ( weightKg / heightCm / heightCm );
    }

    // 计算 BMR
    public static double getBMR( int ageYear, String gender, double weightKg, double heightCm ){
        /*
            男: BMR = ( 10 * 体重kg ) + ( 6.25 * 身高cm ) - ( 5 * 年龄 ) + 5
            女: BMR = ( 10 * 体重kg ) + ( 6.25 * 身高cm ) - ( 5 * 年龄 ) - 161
         */
        double BMR = ( 10 * weightKg ) + ( 6.25 * heightCm ) - ( 5 * ageYear );
        switch ( gender ){
            case "男" : // if ( gender.equals( "男" )
                BMR += 5;
                break;
            case "女" :
                BMR -= 161;
                break;
            default :
                BMR = 0;
                System.out.println( "性别错误!请重新输入!" );
                break;
        }

        double reviseRate = 0.95;
        BMR *= reviseRate; // 亚洲人群建议用修正公式(WHO 推荐值 × 0.95)
        return BMR;
    }

    // 分析 BMI
    public static void analyzeBMI( double BMI ){
        System.out.println( tabKey + horizonalBar4 + "BMI 分析" + horizonalBar4 );
        System.out.println( "你的 BMI 为:\t" + BMI );

        String[] healthStatus = new String[] {
          "体重过低", // BMI < 18.5
          "正常范围", // 18.5 ~ 23.9
          "超重", // 24 ~ 27.9
          "肥胖", // ≥ 28
        };

        String[] healthRisk = new String[] {
          "营养不良风险", // BMI < 18.5
          "健康", // 18.5 ~ 23.9
          "心血管疾病风险增加", // 24 ~ 27.9
          "糖尿病、高血压等高风险", // ≥ 28
        };

        enum healthType {
            LOW , NORMAL, OVER, OBESE,
        }

        int idx;
        if ( BMI >= 28 ){
            idx = healthType.OBESE.ordinal(); // 3
                // 枚举类型名称.枚举成员名称.ordinal() → 获得对应的序号(ordinal)
        } else if ( BMI >= 24 ){
            idx = healthType.OVER.ordinal();
        } else if ( BMI >= 18.5 ){
            idx = healthType.NORMAL.ordinal();
        } else {
            idx = healthType.LOW.ordinal();
        }

        System.out.println( "健康分类为:\t" + healthStatus[ idx ] );
        System.out.println( "健康风险为:\t" + healthRisk[ idx ] );
        newLine();
    }

    // 分析 BMR
    public static void analyzeBMR( double BMR ){
        System.out.println( tabKey + horizonalBar4 + "BMR 分析" + horizonalBar4 );
        System.out.println( "你的 BMR 为:\t" + BMR + "kcal/day" );
        System.out.println("你的 TDEE(total daily energy expenditure,每日总能量消耗) 表格如下:" );

        double[] activityCoefficient = new double[] {
                0, // 占位置
                1.2, // 久坐
                1.375, // 轻度
                1.55, // 中度
                1.725, // 高度
                1.9, // 超级
        };

        int typeNum = activityCoefficient.length;
        double[] tdeeArr = new double[ typeNum ];
        for (int i = 0; i < typeNum; ++i ) {
            tdeeArr[i] = BMR * activityCoefficient[i];
        }

        tempPause();
        printTDEETable( tdeeArr );
        newLine();
    }

    // 打印 TDEE 表格
    public static void printTDEETable( double[] tdeeArr ){
        String[] activityTable = new String[] {
                "活动强度级别    适用人群特征                          日常活动举例                                       运动训练举例             TDEE = BMR * 活动系数",
                "久坐不动   几乎无日常活动,居家办公为主  长时间阅读、看电视、伏案工作;日均步数＜3000步                            无规律运动                   ",
                "轻度活动   每周1-3次低强度运动        散步(30分钟/天)、轻松骑行、家务清洁;日均步数3000-6000步  瑜伽、太极、低强度健身操(≤2次/周)                ",
                "中度活动   每周3-5次中强度训练        快走(5km/h)、慢跑、游泳(非竞技);日均步数6000-10000步   健身房器械训练(45分钟/次)、羽毛球、篮球(3-5次/周)  ",
                "高度活动   每日高强度训练或体力劳动    建筑工人、快递员、舞蹈演员;日均步数＞10000步             长跑(10km/次)、重量训练(1小时/天)、HIIT(≥5次/周) ",
                "超高强度   职业运动员/重体力劳动者     铁人三项训练、矿山作业、竞技体育集训;日均能耗＞3000大卡    马拉松备赛(每日20km+)、职业力量举(2小时/天)       ",
        };

        int typeNum = tdeeArr.length;
        System.out.println( activityTable[ 0 ] );
        for (int i = 1; i < typeNum; ++i ) {
            System.out.println( activityTable[ i ] + tdeeArr[ i ] );
        }
    }

    // 换行
    public static void newLine(){
        System.out.println();
    }

    // 暂停一段时间
    public static void tempPause() {
        // 用 Thread.sleep() 模拟等待加载过程
        int delay = 2500; // 2500ms

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // 恢复中断状态(重要!)
            Thread.currentThread().interrupt();
            // 打印异常信息或进行其他处理
            System.err.println("暂停被中断: " + e.getMessage());
        }
    }
}
