package com.github.existedname.healthcalculatorv2.service;

import com.github.existedname.healthcalculatorv2.model.*;
import com.github.existedname.healthcalculatorv2.util.*;

import java.util.Scanner;
import java.util.Arrays;
import java.io.Console;

public class Service {
    /*      1. 成员变量     */
    /*  1.1 静态成员变量   */

    // 1.1.1 普通变量

    // 1.1.2 数组( 使用自写的方法进行初始化 )
    private static HealthMetricIntroduction[] introductionArr = null; // "身体指标介绍"数组
    private static HealthMetricIdealRange[] idealRangeArr = null; // "身体指标理想值/范围"数组

    // 1.1.3 枚举类型( 注: 默认 static => 只有 1 份,由类自己享有 )
    // 1.1.3.1 用枚举类型代表数组下标
    private enum IntroductionIdx { // 枚举类型名称大驼峰命名
        /*  1. 枚举成员的声明    */
        /*
         *  1.1 枚举成员 = 枚举常量 = 枚举实例/枚举对象
         *  1.2 必须在枚举类型的所有字段和方法之前
         *  1.3 同 C/C++ 用逗号隔开枚举成员,不过要在最后一个成员后面打分号
         */
        BMI( 0 ), WC( 1 ), WHR( 2 ), BFR( 3 ), BRI( 4 ), BMR( 5 ), TDEE( 6 ), BSA( 7 ), ;

        /*  2. 字段初始化————必须加 final    */
        /*
         *      字段 = 枚举类的成员变量( 枚举是 1 种类 )
         *      在枚举中,字段通常声明为 final,因为枚举成员是不可变的,一旦枚举成员
         *  被创建,其字段的值不应该被修改,使用 final 可以确保字段的值在初始化后不会
         *  被改变,从而保证枚举成员的不可变性
         */
        private final int idx;

        /*  3. 定义构造器    */
        /*
         *      如果定义的枚举成员有参数,那么不能定义无参数构造器,只能定义有参数构造器
         *      构造器默认 private
         */
        IntroductionIdx( int idx ){
            this.idx = idx;
        }

        /*  4. 方法被所有枚举成员共享( 除了静态方法 )    */
        public int getIdx(){
            return this.idx;
        }
    }

    private enum IdealRangeIdx{
        // 1. 枚举成员
        WEIGHT( 0 ), BMI( 1 ), WC( 2 ), WHR( 3 ), BFR( 4 ), BRI( 5 ), BMR( 6 ), TDEE( 7 ), BSA( 8 ), ;

        // 2. 字段
        private final int idx;

        // 3. 有参数构造器
        IdealRangeIdx( int idx ){
            this.idx = idx;
        }

        // 4. 方法
        public int getIdx(){
            return this.idx;
        }
    }

    // 1.1.3.2 用枚举类型代表身体指标的单位
    public enum HealthMetricUnit {
        /*      1. 枚举成员     */
        AGE( "年" ), WEIGHT( "kg" ), HEIGHT( "cm" ), BMI( "kg/m²" ), WC( "cm" ), WHR( "" ),
        BFR( "%%" ), BRI( "" ), BMR( "Kcal/day" ), TDEE( "Kcal/day" ), BSA( "m²" ), ;
            /*
             *      BFR 的 unit 若设为 "%",计算 BFR 时会报错 java.util.UnknownFormatConversionException:
             *  Conversion = '%',因为拼接 number + BFR.getUnit() 等价于 number + "%",而遇到单个 % 时,Java
             *  会尝试格式化输出,也就是同 C 语言一样用 %d,%f,%s 等格式符进行输出,然而这个 % 后面并没有 d、f、s,不能
             *  进行格式化输出,于是报错
             *      解决方法之一是将 BFR 的 unit 设为 "%%",就能正确拼接并输出想要的单个 % 了
             */

        /*      2. 字段       */
        private final String unit;

        /*      3. 有参数构造器       */
        // 枚举成员可以有不同参数值,但参数类型、数量必须一样 => 要么都有参数,要么都无参数
        HealthMetricUnit( String unit ){
            this.unit = unit;
        }

        /*      4. 公开方法     */
        // 4.1 通过访问枚举成员变量来获取单位
        public String getUnit(){
            return this.unit;
        }

        // 4.2 通过传入某种指标的名称获取对应单位
        public static String getUnitByName( String healthMetricName ){
            /*
             *      Java 12 及以上可以使用 switch 新特性
             *  1. 箭头语法 (->) 替代 ":", 无需 break, 避免 case 穿透
             *  2. switch 可以作为表达式,把自己的值赋给左边的变量
             *  3. 多条语句使用 {} 包裹, 执行完这些语句后用 yield 返回 switch 表达式的值
             *  4. 多值匹配: 单个 case 可以对应多个值( 逗号分隔 )
             */
            return switch ( healthMetricName ){
                case "age", "年龄" -> "年";
                case "weight", "体重" -> "kg";
                case "height", "身高", "WC", "waistCircum", "腰围", "AC", "armCircum", "臂围",
                     "NC", "neckCircum", "颈围", "HC", "hipCircumCm", "臀围" -> "cm";
                case "BMI", "bmi", "身体质量指数" -> "kg/m²";
                case "BFR", "bfr", "体脂率" -> "%";
                case "BMR", "bmr", "基础代谢率", "TDEE", "tdee", "每日总消耗热量" -> "Kcal/day";
                case "BSA", "bsa", "体表面积" -> "m²";
                default -> "";
            };
        }
    }


    /*  1.2 非静态成员变量 => 类及其实例都有 1 份  */
    // 1.2.1 普通变量

    // 1.2.2 数组
    // 1.2.2.1 标记"身体指数计算"方法是否被访问
        // 各个"身体指数计算"方法的名称( 描述方法名字时通常不加括号 )
    private final String[] namesOfCalculationMethods = new String[] {
        "calculateBMI", "calculateWHR", "calculateBFR", "calculateBRI",
        "calculateBMR", "calculateTDEE", "calculateBSA",
    };

        // 各个"身体指数计算"方法的访问标记
    private final int numOfCalculationMethod = namesOfCalculationMethods.length;
    private boolean[] accessFlagsOfCalculationMethods = null;
    /*
     *      定义以上 2 个数组是为了形成 pair => 相同下标对应同一方法的名称和访问标记
     *      不过可能改为 map 要更整齐一点
     */



    /*      2. 公有构造器     */
    /*  2.1 无参数构造器  */
    public Service(){ }



    /*      3. 公有非静态方法————关于各种身体指数的功能     */
    /*  3.1 身体指数详解    */

    /*  3.1.1 详解体态评估类身体指数    */
    // 3.1.1.1 详解 BMI
    public void introduceBMI(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.BMI.getIdx() );
    }

    // 3.1.1.2 详解 WC
    public void introduceWaistCircum(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.WC.getIdx() );
    }

    // 3.1.1.3 详解 WHR
    public void introduceWHR(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.WHR.getIdx() );
    }

    // 3.1.1.4 详解 BFR
    public void introduceBFR(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.BFR.getIdx() );
    }

    // 3.1.1.5 详解 BRI
    public void introduceBRI(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.BRI.getIdx() );
    }

    /*  3.1.2 详解能量代谢类身体指数    */
    // 3.1.2.1 详解 BMR
    public void introduceBMR(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.BMR.getIdx() );
    }

    // 3.1.2.2 详解 TDEE
    public void introduceTDEE(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.TDEE.getIdx() );
    }

    /*  3.1.3 详解生理特征类身体指数    */
    // 3.1.3.1 详解 BSA
    public void introduceBSA(){
        if ( introductionArr == null ) initializeIntroductionArr();
        printIntroByIdx( IntroductionIdx.BSA.getIdx() );
    }


    /*  3.2 身体指数计算  */

    /*  3.2.1 计算体态评估类身体指数    */
    // 3.2.1.1 计算 BMI
    public void calculateBMI( User user ){
        try {
            // 1. 规定体重、身高有效范围
            double weightStart = 0.24, weightEnd = 730; // 最轻的婴儿: 243.8 g, 最重的人: 727 kg
            double heightStart = 25, heightEnd = 320; // 最矮的婴儿: 25 cm, 最高的人: 3.19 m

            // 2. 检查并获取有效体重、身高
                // 先调用 user 的成员变量作为初始值,如果该值有误则需要重新输入
                // ( 比如还没有初始化,或者之前输入了错误数据但是没有检查出来 )
            double weightKg = getValidDoubleInput( "体重", user.getWeightKg(), weightStart, weightEnd );
            double heightCm = getValidDoubleInput( "身高( cm )", user.getHeightCm(), heightStart, heightEnd );

            // 3. 得到目标结果
                // 3.1 调用相应的工具类以及计算方法
            double BMI = BMICalculator.getBMI( weightKg, heightCm );
                // 3.2 更新 user 成员变量
            user.setBMI( BMI ); user.setWeightKg( weightKg ); user.setHeightCm( heightCm );
                // 3.3 输出结果( 保留 3 位小数 )
            Service.printLoadingProgress( "正在计算 BMI", "BMI 计算完毕!" );
            System.out.printf( "你的 BMI 数值为:\t%.3f" + HealthMetricUnit.BMI.getUnit() + "\n", BMI );
                // 备注: printf 不带换行,需要自己加( 同 C 语言 )

            // 4. 标记 calculateBMI 方法已经被访问过
                // 4.1 检查访问标记数组是否被初始化
            if ( accessFlagsOfCalculationMethods == null ) initializeAccessFlagArr();
                // 4.2 通过方法名称( 不带括号 )获取对应下标
            int idx = getIdxOfTargetStr( "calculateBMI", namesOfCalculationMethods );
                // 4.3 通过下标修改访问标记数组
            accessFlagsOfCalculationMethods[ idx ] = true;
        } catch ( NullPointerException e ){
            /*
             *      Exception: 例外,异常
             *      在 Java 中,如果传入的参数( 比如对象 )为空( null ),并且你的
             *  代码尝试访问这个空对象的成员变量或方法,那么会抛出一个 NullPointerException
             *      这种异常是 Java 中的一种运行时异常( RuntimeException ),表示你试图在
             *  null 引用上调用方法或访问字段
             */
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.2.1.2 计算 WHR
    public void calculateWHR( User user ){
        try {
            // 1. 规定腰围、臀围有效范围
            double waistCircumStart = 0.3, waistCircumEnd = 310; // 婴儿: 最小约 30 cm, 最大: Walter Hudson 302 cm
            double hipCircumStart = 35, hipCircumEnd = 250; // 婴儿: 最小约 35cm, 最大: Mikel Ruffinelli 244 cm

            // 2. 检查并获取有效腰围、臀围
            double waistCircumCm = getValidDoubleInput( "腰围", user.getWaistCircumCm(), waistCircumStart, waistCircumEnd );
            double hipCircumCm = getValidDoubleInput( "臀围", user.getHipCircumCm(), hipCircumStart, hipCircumEnd );

            // 3. 得到目标结果
                // 3.1 调用相应的工具类以及计算方法
            double WHR = WHRCalculator.getWHR( waistCircumCm, hipCircumCm );
                // 3.2 更新 user 成员变量
            user.setWHR( WHR ); user.setWaistCircumCm( waistCircumCm ); user.setHipCircumCm( hipCircumCm );
                // 3.3 输出结果( 保留 3 位小数 )
            Service.printLoadingProgress( "正在计算 WHR", "WHR 计算完毕!" );
            System.out.printf( "你的 WHR 数值为:\t%.3f" + HealthMetricUnit.WHR.getUnit() + "\n", WHR );

            // 4. 标记 calculateWHR 方法已经被访问过
                // 4.1 检查访问标记数组是否被初始化
            if ( accessFlagsOfCalculationMethods == null ) initializeAccessFlagArr();
                // 4.2 通过方法名称( 不带括号 )获取对应下标
            int idx = getIdxOfTargetStr( "calculateWHR", namesOfCalculationMethods );
                // 4.3 通过下标修改访问标记数组
            accessFlagsOfCalculationMethods[ idx ] = true;
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.2.1.3 计算 BFR
    public void calculateBFR( User user ){
        try {
            // 1. 规定性别、年龄、BMI 有效范围
            String[] validGenders = new String[] { "女", "男" };
            int ageStart = 1, ageEnd = 150; // 姆巴·戈多: 印度尼西亚,1870.12.31 - 2017.4.30,享年 146 岁
            double bmiStart = 0.1, bmiEnd = Double.MAX_VALUE;
            // 备注: 区间起点不能取 0,因为 user 中未初始化的成员变量默认值为 0

            // 2. 检查并获取有效性别、年龄、BMI
            String gender = getValidStringInput( "性别", user.getGender(), validGenders );
            int age = getValidIntInput( "年龄", user.getAge(), ageStart, ageEnd );
                // 正确顺序: 先检查 user.BMI,再把 user.BMI 赋值给 BMI,保证 BMI 为最终的有效值
            if ( ! isDataWithinInterval( user.getBMI(), bmiStart, bmiEnd ) ){
                calculateBMI( user ); // 非静态方法可以不创建对象直接调用其他非静态方法
                System.out.println(); // 换行,避免与新输出连在一起
            }
            double BMI = user.getBMI();

            // 3. 得到目标结果
                // 3.1 调用相应的工具类以及计算方法
                // 默认使用最常见的 Deurenberg 公式
            double BFR = BFRCalculator.calcByDeurenbergEquation( gender, age, BMI );
                // 3.2 更新 user 成员变量
            user.setBFR( BFR ); user.setBMI( BMI ); user.setGender( gender ); user.setAge( age );
                // 3.3 输出结果( 保留 3 位小数 )
            Service.printLoadingProgress( "正在计算 BFR", "BFR 计算完毕!" );
            System.out.printf( "你的 BFR 数值为:\t%.3f" + HealthMetricUnit.BFR.getUnit() + "\n", BFR );

            // 4. 标记 calculateBFR 方法已经被访问过
                // 4.1 检查访问标记数组是否被初始化
            if ( accessFlagsOfCalculationMethods == null ) initializeAccessFlagArr();
                // 4.2 通过方法名称( 不带括号 )获取对应下标
            int idx = getIdxOfTargetStr( "calculateBFR", namesOfCalculationMethods );
                // 4.3 通过下标修改访问标记数组
            accessFlagsOfCalculationMethods[ idx ] = true;
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.2.1.4 计算 BRI
    public void calculateBRI( User user ){
        try {
            // 1. 规定腰围、身高有效范围
            double waistCircumStart = 0.3, waistCircumEnd = 310; // 最小: 婴儿最小约 30 cm, 最大: Walter Hudson 302 cm
            double heightStart = 25, heightEnd = 320; // 最矮的婴儿: 25 cm, 最高的人: 3.19 m

            // 2. 检查并获取有效腰围、身高
            double waistCircumCm = getValidDoubleInput( "腰围", user.getWaistCircumCm(), waistCircumStart, waistCircumEnd );
            double heightCm = getValidDoubleInput( "身高( cm )", user.getHeightCm(), heightStart, heightEnd );

            // 3. 得到目标结果
                // 3.1 调用相应的工具类以及计算方法
            double BRI = BRICalculator.getBRI( waistCircumCm, heightCm );
                // 3.2 更新 user 成员变量
            user.setBRI( BRI ); user.setWaistCircumCm( waistCircumCm ); user.setHeightCm( heightCm );
                // 3.3 输出结果( 保留 3 位小数 )
            Service.printLoadingProgress( "正在计算 BRI", "BRI 计算完毕!" );
            System.out.printf( "你的 BRI 数值为:\t%.3f" + HealthMetricUnit.BRI.getUnit() + "\n", BRI );

            // 4. 标记 calculateBRI 方法已经被访问过
                // 4.1 检查访问标记数组是否被初始化
            if ( accessFlagsOfCalculationMethods == null ) initializeAccessFlagArr();
                // 4.2 通过方法名称( 不带括号 )获取对应下标
            int idx = getIdxOfTargetStr( "calculateBRI", namesOfCalculationMethods );
                // 4.3 通过下标修改访问标记数组
            accessFlagsOfCalculationMethods[ idx ] = true;
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    /*  3.2.2 计算能量代谢类身体指数    */
    // 3.2.2.1 计算 BMR
    public void calculateBMR( User user ){
        try {
            // 1. 规定性别、年龄、体重、身高有效范围
            String[] validGenders = new String[] { "女", "男" };
            int ageStart = 0, ageEnd = 150; // 姆巴·戈多: 印度尼西亚,1870.12.31 - 2017.4.30,享年 146 岁
            double weightStart = 0.24, weightEnd = 730; // 最轻的婴儿: 243.8 g, 最重的人: 727 kg
            double heightStart = 25, heightEnd = 320; // 最矮的婴儿: 25 cm, 最高的人: 3.19 m

            // 2. 检查并获取有效性别、年龄、体重、身高
            String gender = getValidStringInput( "性别", user.getGender(), validGenders );
            int age = getValidIntInput( "年龄", user.getAge(), ageStart, ageEnd );
            double weightKg = getValidDoubleInput( "体重", user.getWeightKg(), weightStart, weightEnd );
            double heightCm = getValidDoubleInput( "身高( cm )", user.getHeightCm(), heightStart, heightEnd );

            // 3. 得到目标结果
                // 3.1 调用相应的工具类以及计算方法
                // 选用相对准确的 Mifflin-St Jeor( MSJ ) 公式
            double BMR = BMRCalculator.calcByMSJEquation( gender, age, weightKg, heightCm );
                // 3.2 更新 user 成员变量
            user.setBMR( BMR ); user.setGender( gender ); user.setAge( age );
            user.setWeightKg( weightKg ); user.setHeightCm( heightCm );
                // 3.3 输出结果( 保留 3 位小数 )
            Service.printLoadingProgress( "正在计算 BMR", "BMR 计算完毕!" );
            System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", BMR );

            // 4. 标记 calculateBMR 方法已经被访问过
                // 4.1 检查访问标记数组是否被初始化
            if ( accessFlagsOfCalculationMethods == null ) initializeAccessFlagArr();
                // 4.2 通过方法名称( 不带括号 )获取对应下标
            int idx = getIdxOfTargetStr( "calculateBMR", namesOfCalculationMethods );
                // 4.3 通过下标修改访问标记数组
            accessFlagsOfCalculationMethods[ idx ] = true;
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.2.2.2 计算 TDEE
    public void calculateTDEE( User user ){
        try {
            // 1. 规定 BMR、活动系数 有效范围
            double bmrStart = 300, bmrEnd = Double.MAX_VALUE; // BMR 最小值极端情况大概 300 Kcal/day
            double activityCoeffStart = 1.0, activityCoeffEnd = 2.6; // 马拉松可能 2.5 左右

            // 2. 检查并获取有效 BMR、活动系数
                // 2.1 BMR
            if ( ! isDataWithinInterval( user.getBMR(), bmrStart, bmrEnd ) ){
                calculateBMR( user ); // 非静态方法可以不创建对象直接调用其他非静态方法
                System.out.println(); // 换行,避免与新输出连在一起
            }
            double BMR = user.getBMR();
                // 2.2 活动系数
            System.out.println( "请阅读以下活动系数对照表,找到属于你的活动系数👆" );
            Service.threadSleep( 1000 );
            TDEECalculator.printCoeffTable();

            System.out.print( "请根据该表输入你的活动系数估计值:\t" );
            Scanner scanner = new Scanner( System.in );
            double activityCoeff = getValidDoubleInput( "活动系数", scanner.nextDouble(), activityCoeffStart, activityCoeffEnd );

            // 3. 得到目标结果
                //  3.1 调用相应的工具类以及计算方法
            double TDEE = TDEECalculator.getTDEE( BMR, activityCoeff );
                // 3.2 更新 user 成员变量
            user.setTDEE( TDEE ); user.setActivityCoeff( activityCoeff );
                // 3.3 输出结果( 保留 3 位小数 )
            Service.printLoadingProgress( "正在计算 TDEE", "TDEE 计算完毕!" );
            System.out.printf( "你的 TDEE 数值为:\t%.3f" + HealthMetricUnit.TDEE.getUnit() + "\n", TDEE );

            // 4. 标记 calculateTDEE 方法已经被访问过
                // 4.1 检查访问标记数组是否被初始化
            if ( accessFlagsOfCalculationMethods == null ) initializeAccessFlagArr();
                // 4.2 通过方法名称( 不带括号 )获取对应下标
            int idx = getIdxOfTargetStr( "calculateTDEE", namesOfCalculationMethods );
                // 4.3 通过下标修改访问标记数组
            accessFlagsOfCalculationMethods[ idx ] = true;
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    /*  3.2.3 计算生理特征类身体指数    */
    // 3.2.3.1 计算 BSA
    public void calculateBSA( User user ){
        try {
            // 1. 规定体重、身高有效范围
            double weightStart = 0.24, weightEnd = 730; // 最轻的婴儿: 243.8 g, 最重的人: 727 kg
            double heightStart = 25, heightEnd = 320; // 最矮的婴儿: 25 cm, 最高的人: 3.19 m

            // 2. 检查并获取有效体重、身高
            double weightKg = getValidDoubleInput( "体重", user.getWeightKg(), weightStart, weightEnd );
            double heightCm = getValidDoubleInput( "身高( cm )", user.getHeightCm(), heightStart, heightEnd );

            // 3. 得到目标结果
                //  3.1 调用相应的工具类以及计算方法
                // 选择最经典、应用最广泛的 Du Bois Formula 杜博伊斯公式
            double BSA = BSACalculator.calcByDuBoisEquation( weightKg, heightCm );
                // 3.2 更新 user 成员变量
            user.setBSA( BSA ); user.setWeightKg( weightKg ); user.setHeightCm( heightCm );
                // 3.3 输出结果( 保留 3 位小数 )
            Service.printLoadingProgress( "正在计算 BSA", "BSA 计算完毕!" );
            System.out.printf( "你的 BSA 数值为:\t%.3f" + HealthMetricUnit.BSA.getUnit() + "\n", BSA );

            // 4. 标记 calculateBSA 方法已经被访问过
                // 4.1 检查访问标记数组是否被初始化
            if ( accessFlagsOfCalculationMethods == null ) initializeAccessFlagArr();
                // 4.2 通过方法名称( 不带括号 )获取对应下标
            int idx = getIdxOfTargetStr( "calculateBSA", namesOfCalculationMethods );
                // 4.3 通过下标修改访问标记数组
            accessFlagsOfCalculationMethods[ idx ] = true;
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }


    /*  3.3 身体指数分析    */

    /*  3.3.1 分析体态评估类身体指数    */
    // 3.3.1.1 分析 BMI
    public void analyzeBMI( User user ){
        try {
            // 1. 检查 calculateBMI 方法是否被访问
            boolean isCalculateBMIMethodVisited = false;
            if ( accessFlagsOfCalculationMethods != null ){
                // 由于需要访问数组,需要提前判断数组是否初始化
                int idx = getIdxOfTargetStr( "calculateBMI", namesOfCalculationMethods );
                isCalculateBMIMethodVisited = accessFlagsOfCalculationMethods[ idx ];
            }

            // 2. 根据访问标签判断是否需要调用 calculateBMI 方法计算 BMI
            if ( ! isCalculateBMIMethodVisited ) calculateBMI( user );

            // 3. 调用相应工具类进行分析
            HealthMetricAnalyzer.analyzeBMI( user.getBMI() );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.3.1.2 分析 WC
    public void analyzeWaistCircum( User user ){
        try {
            // 1. 规定 性别、WC 有效范围
            String[] validGenders = new String[] { "女", "男" };
            double waistCircumStart = 0.3, waistCircumEnd = 310; // 婴儿: 最小约 30 cm, 最大: Walter Hudson 302 cm

            // 2. 检查并获取有效的 性别、WC
            String gender = getValidStringInput( "性别", user.getGender(), validGenders );
            double waistCircumCm = getValidDoubleInput( "腰围", user.getWaistCircumCm(), waistCircumStart, waistCircumEnd );

            // 3. 调用相应的工具类进行分析
            HealthMetricAnalyzer.analyzeWaistCircum( gender, waistCircumCm );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.3.1.3 分析 WHR
    public void analyzeWHR( User user ){
        try {
            // 1. 检查 calculateWHR 方法是否被访问
            boolean isCalculateWHRMethodVisited = false;
            if ( accessFlagsOfCalculationMethods != null ){
                int idx = getIdxOfTargetStr( "calculateWHR", namesOfCalculationMethods );
                isCalculateWHRMethodVisited = accessFlagsOfCalculationMethods[ idx ];
            }

            // 2. 根据访问标签判断是否需要调用 calculateWHR 方法计算 WHR
            if ( ! isCalculateWHRMethodVisited ) calculateWHR( user );

            // 3. 调用相应工具类进行分析
            HealthMetricAnalyzer.analyzeWHR( user.getGender(), user.getWHR() );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.3.1.4 分析 BFR
    public void analyzeBFR( User user ){
        try {
            // 1. 检查 calculateBFR 方法是否被访问
            boolean isCalculateBFRMethodVisited = false;
            if ( accessFlagsOfCalculationMethods != null ){
                int idx = getIdxOfTargetStr( "calculateBFR", namesOfCalculationMethods );
                isCalculateBFRMethodVisited = accessFlagsOfCalculationMethods[ idx ];
            }

            // 2. 根据访问标签判断是否需要调用 calculateBFR 方法计算 BFR
            if ( ! isCalculateBFRMethodVisited ) calculateBFR( user );

            // 3. 调用相应工具类进行分析
            HealthMetricAnalyzer.analyzeBFR( user.getGender(), user.getBFR() );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.3.1.5 分析 BRI
    public void analyzeBRI( User user ){
        try {
            // 1. 检查 calculateBRI 方法是否被访问
            boolean isCalculateBRIMethodVisited = false;
            if ( accessFlagsOfCalculationMethods != null ){
                int idx = getIdxOfTargetStr( "calculateBRI", namesOfCalculationMethods );
                isCalculateBRIMethodVisited = accessFlagsOfCalculationMethods[ idx ];
            }

            // 2. 根据访问标签判断是否需要调用 calculateBRI 方法计算 BRI
            if ( ! isCalculateBRIMethodVisited ) calculateBRI( user );

            // 3. 调用相应工具类进行分析
            HealthMetricAnalyzer.analyzeBRI( user.getGender(), user.getBRI() );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    /*  3.3.2 分析能量代谢类身体指数    */
    // 3.3.2.1 分析 BMR
    public void analyzeBMR( User user ){
        try {
            // 1. 检查 calculateBMR 方法是否被访问
            boolean isCalculateBMRMethodVisited = false;
            if ( accessFlagsOfCalculationMethods != null ){
                int idx = getIdxOfTargetStr( "calculateBMR", namesOfCalculationMethods );
                isCalculateBMRMethodVisited = accessFlagsOfCalculationMethods[ idx ];
            }

            // 2. 根据访问标签判断是否需要调用 calculateBMR 方法计算 BMR
            if ( ! isCalculateBMRMethodVisited ) calculateBMR( user );

            // 3. 调用相应工具类进行分析
            HealthMetricAnalyzer.analyzeBMR( user.getBMR() );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    // 3.3.2.2 分析 TDEE
    public void analyzeTDEE( User user ){
        try {
            // 1. 检查 calculateTDEE 方法是否被访问
            boolean isCalculateTDEEMethodVisited = false;
            if ( accessFlagsOfCalculationMethods != null ){
                int idx = getIdxOfTargetStr( "calculateTDEE", namesOfCalculationMethods );
                isCalculateTDEEMethodVisited = accessFlagsOfCalculationMethods[ idx ];
            }

            // 2. 根据访问标签判断是否需要调用 calculateTDEE 方法计算 TDEE
            if ( ! isCalculateTDEEMethodVisited ) calculateTDEE( user );

            // 3. 调用相应工具类进行分析
            HealthMetricAnalyzer.analyzeTDEE( user.getTDEE() );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }

    /*  3.3.3 分析生理特征类身体指数    */
    // 3.3.3.1 分析 BSA
    public void analyzeBSA( User user ){
        try {
            // 1. 检查 calculateBSA 方法是否被访问
            boolean isCalculateBSAMethodVisited = false;
            if ( accessFlagsOfCalculationMethods != null ){
                int idx = getIdxOfTargetStr( "calculateBSA", namesOfCalculationMethods );
                isCalculateBSAMethodVisited = accessFlagsOfCalculationMethods[ idx ];
            }

            // 2. 根据访问标签判断是否需要调用 calculateBSA 方法计算 BSA
            if ( ! isCalculateBSAMethodVisited ) calculateBSA( user );

            // 3. 调用相应工具类进行分析
            HealthMetricAnalyzer.analyzeBSA( user.getBSA() );
        } catch ( NullPointerException e ){
            System.out.println( "User 对象为空!" );
        } finally { }
    }


    /*  3.4 对比不同公式得到的身体指数值    */

    /*  3.4.1 对比不同公式得到的体态评估类身体指数    */
    // 3.4.1.1 对比不同公式得到的 BFR
    public void cmpBFRofVariousEquations( User user ){
        int timeInterval = 3000; // 3s 间隔
        /*      1. Deurenberg 公式        */
        System.out.println( "1. Deurenberg 公式" + "\n\t————" + "用 BMI 预测体脂率最经典的公式,适用于 7-83 岁的成人和儿童" );
        calculateBFR( user ); // calculateBFR 方法默认使用 Deurenberg 公式

            // 额外功能: 用 mx、mn 记录用各个公式计算出的最大最小 BFR 值,作为一个参考范围
        double tempBFR = user.getBFR(); // 之后还要用 tempBFR 记录各个公式计算结果
        double maxBFR = tempBFR, minBFR = tempBFR; // 先得到 Deurenberg 公式算出来的 BFR

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      2. Gallagher 公式     */
        // 上一步调用 calculateBFR 方法必定会获得有效的 性别、年龄、BMI( 体重、身高 ),现在直接访问 user 成员变量即可
        System.out.println( "2. Gallagher 公式" + "\n\t————" + "适用于普通成人" );

        /*  2.1 Gallagher 公式版本Ⅰ */
        System.out.println( "2.1 Gallagher 公式版本Ⅰ: 基于 BMI、年龄、性别" );
            // 记录公式结果 & 更新最大最小值
        tempBFR = BFRCalculator.calcByGallagherEquation1( user.getGender(), user.getAge(),
                    user.getBMI() );
        maxBFR = ( tempBFR > maxBFR ? tempBFR : maxBFR );
        minBFR = Math.min( tempBFR, minBFR );
        System.out.printf( "你的 BFR 数值为:\t%.3f" + HealthMetricUnit.BFR.getUnit() + "\n", tempBFR );

        /*  2.2 Gallagher 公式版本Ⅱ */
        System.out.println( "2.2 Gallagher 公式版本Ⅱ: 基于 BMI 的倒数( 1/BMI )、年龄、性别" );
        tempBFR = BFRCalculator.calcByGallagherEquation2( user.getGender(), user.getAge(),
                    user.getBMI() );
        maxBFR = Math.max( tempBFR, maxBFR );
        minBFR = Math.min( tempBFR, minBFR );
        System.out.printf( "你的 BFR 数值为:\t%.3f" + HealthMetricUnit.BFR.getUnit() + "\n", tempBFR );

        Service.threadSleep( timeInterval );

        /*      3. 美国海军公式       */
        System.out.println();
        System.out.println( "3. 美国海军体脂测量法( 美国海军公式 )" + "\n\t————" + "军事和健身领域常用" );
        System.out.println( "Question: 该方法需要测量腰围、颈围、臀围,确定要体验该方法?" );
        System.out.print( "Input( yes 或者 no ) >> " );
        Scanner scanner = new Scanner( System.in );
        if ( scanner.next().equals( "yes" )  ){
            // 1. 规定 腰围、臀围、颈围 有效范围
            double waistCircumStart = 15, waistCircumEnd = 310; // 最小: 婴儿 15 cm,最大 : Walter Hudson, 302 cm
            double hipCircumStart = 20, hipCircumEnd = 250; // 最小: 婴儿 20 cm,最大 : Mikel Ruffinelli, 244 cm
            double neckCircumStart = 10, neckCircumEnd = 60;

            // 2. 检查并获取有效 腰围、臀围、颈围
            double waistCircumCm = getValidDoubleInput( "腰围", user.getWaistCircumCm(), waistCircumStart, waistCircumEnd );
            double hipCircumCm = getValidDoubleInput( "臀围", user.getHipCircumCm(), hipCircumStart, hipCircumEnd );
            double neckCircumCm = getValidDoubleInput( "颈围", user.getNeckCircumCm(), neckCircumStart, neckCircumEnd );

            // 3. 得到目标结果
                // 3.1 调用相应的工具类以及计算方法
                // 3.2 更新 user 成员变量
            user.setWaistCircumCm( waistCircumCm );
            user.setHipCircumCm( hipCircumCm );
            user.setNeckCircumCm( neckCircumCm );
                // 3.3 输出结果( 保留 3 位小数 )
            tempBFR = BFRCalculator.calcByUSNEquation( user.getGender(), user.getWaistCircumCm(),
                        user.getHipCircumCm(), user.getNeckCircumCm(), user.getHeightCm() );
            maxBFR = Math.max( tempBFR, maxBFR );
            minBFR = Math.min( tempBFR, minBFR );
            System.out.printf( "你的 BFR 数值为:\t%.3f"+ HealthMetricUnit.BFR.getUnit() + "\n", tempBFR );
        }

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      4. Jackson-Pollock 公式       */
        System.out.println();
        System.out.println( "4. Jackson-Pollock 公式" + "\n\t————" + "提供皮褶与 BMI 双路径计算 " );

        /*  4.1 Jackson-Pollock 公式版本Ⅰ   */
        System.out.println( "4.1 Jackson-Pollock 公式版本Ⅰ: 基于 皮褶厚度,广泛用于学术和临床" );
        System.out.println( "由于需要用皮脂钳测量多处皮褶厚度,较为麻烦,此处略过~~~" );

        /*  4.2 Jackson-Pollock 公式版本Ⅱ   */
        System.out.println( "4.2 Jackson-Pollock 公式版本Ⅱ( 简化版本 ): 基于 BMI、年龄、性别" );
        tempBFR = BFRCalculator.calcByJacksonPollockSimplifiedEquation( user.getGender(),
                    user.getAge(), user.getBMI() );
        maxBFR = Math.max( tempBFR, maxBFR );
        minBFR = Math.min( tempBFR, minBFR );
        System.out.printf( "你的 BFR 数值为:\t%.3f" + HealthMetricUnit.BFR.getUnit() + "\n", tempBFR );

        /*      最后输出计算结果范围      */
        Service.threadSleep( timeInterval );
        System.out.println();
        System.out.printf( "\t结合以上公式结果,你的 BFR 所在范围大致为[ %.3f, %.3f ]" +
                            HealthMetricUnit.BFR.getUnit() + "\n", minBFR, maxBFR );
    }

    /*  3.4.2 对比不同公式得到的能量代谢类身体指数    */
    // 3.4.2.1 对比不同公式得到的 BMR
    public void cmpBMRofVariousEquations( User user ){
        int timeInterval = 3000;
        /*      1. Mifflin-St Jeor( MSJ ) 公式        */
        System.out.println( "1. Mifflin-St Jeor( MSJ ) 公式" + "\n\t————" + "当前最常用、最权威的 BMR 估算公式,对中国人群适用" );
        calculateBMR( user ); // calculateBMR 方法默认使用 MSJ 公式
            // 用 mx、mn 记录用各个公式计算出的最大最小 BMR 值,作为一个参考范围
        double tempBMR = user.getBMR(); // 之后还要用 tempBMR 记录各个公式计算结果
        double maxBMR = tempBMR, minBMR = tempBMR; // 先得到 Mifflin-St Jeor( MSJ ) 公式算出来的 BMR

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      2. Harris-Benedict( H-B ) 公式        */
        // 上一步调用 calculateBMR 方法必定会获得有效的 性别、年龄、体重、身高,现在直接访问 user 成员变量即可
        System.out.println( "2. Harris-Benedict( H-B ) 公式" + "\n\t————" + "经典传统公式,曾被广泛使用" );
        tempBMR = BMRCalculator.calcByHBEquation( user.getGender(), user.getAge(),
                    user.getWeightKg(), user.getHeightCm() );
        maxBMR = Math.max( tempBMR, maxBMR );
        minBMR = Math.min( tempBMR, minBMR );
        System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", tempBMR );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      3. Henry 公式     */
        System.out.println( "3. Henry 公式" + "\n\t————" + "欧洲食品安全局( EFSA )推荐,但更适合欧洲人群" );
        tempBMR = BMRCalculator.calcByHenryEquation( user.getGender(), user.getAge(),
                    user.getWeightKg() );
        maxBMR = Math.max( tempBMR, maxBMR );
        minBMR = Math.min( tempBMR, minBMR );
        System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", tempBMR );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      4. Schofield 公式     */
        System.out.println( "4. Schofield 公式" + "\n\t————" + "WHO 推荐的多年龄段公式" );
        tempBMR = BMRCalculator.calcBySchofieldEquation( user.getGender(), user.getAge(),
                    user.getWeightKg(), user.getHeightCm() );
        maxBMR = Math.max( tempBMR, maxBMR );
        minBMR = Math.min( tempBMR, minBMR );
        System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", tempBMR );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      5. Karch-McArdle 公式( 依据瘦体重 ) 公式     */
        System.out.println( "5. Karch-McArdle 公式( 需要测体脂率噢~ )" + "\n\t————" + "基于瘦体重( FFM )计算,适用于健身人群" );
        calculateBFR( user ); // 由于要计算瘦体重,需要先计算 BFR( 体脂率 )
        tempBMR = BMRCalculator.calcByKarchMcArdleEquation( user.getWeightKg(), user.getBFR() );
        maxBMR = Math.max( tempBMR, maxBMR );
        minBMR = Math.min( tempBMR, minBMR );
        System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", tempBMR );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      6. Shizgal-Rosa 公式      */
        System.out.println( "6. Shizgal-Rosa 公式" + "\n\t————" + "考虑身高、体重、年龄的多元回归公式" );
        tempBMR = BMRCalculator.calcByShizgalRosaEquation( user.getGender(), user.getAge(),
                    user.getWeightKg(), user.getHeightCm() );
        maxBMR = Math.max( tempBMR, maxBMR );
        minBMR = Math.min( tempBMR, minBMR );
        System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", tempBMR );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      7. 毛德倩公式        */
        System.out.println( "7. 毛德倩 公式" + "\n\t————" + "基于中国人数据推导,适用于 20-45 岁的中国人" );
        tempBMR = BMRCalculator.calcByMaoEquation( user.getGender(), user.getWeightKg() );
        maxBMR = Math.max( tempBMR, maxBMR );
        minBMR = Math.min( tempBMR, minBMR );
        System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", tempBMR );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      8. BSA( 体表面积 )计算法       */
        System.out.println( "8. BSA( 体表面积 )计算法( 需要计算体表面积噢~ )" + "\n\t————" + "适用于需要结合体表面积的临床或科研场景" );
        calculateBSA( user ); // 需要先求出 BSA
        tempBMR = BMRCalculator.calcByBSA( user.getGender(), user.getAge(), user.getBSA() );
        maxBMR = Math.max( tempBMR, maxBMR );
        minBMR = Math.min( tempBMR, minBMR );
        System.out.printf( "你的 BMR 数值为:\t%.3f" + HealthMetricUnit.BMR.getUnit() + "\n", tempBMR );

        /*      最后输出计算结果范围      */
        Service.threadSleep( timeInterval );
        System.out.println();
        System.out.printf( "\t结合以上公式结果,你的 BMR 所在范围大致为[ %.3f, %.3f ]" +
                            HealthMetricUnit.BMR.getUnit() + "\n", minBMR, maxBMR );
    }

    /*  3.4.3 对比不同公式得到的生理特征类身体指数    */
    // 3.4.3.1 对比不同公式得到的 BSA
    public void cmpBSAofVariousEquations( User user ){
        int timeInterval = 3000;

        /*      1. Du Bois( 杜博伊斯 )公式        */
        System.out.println( "1. Du Bois( 杜博伊斯 )公式" + "\n\t————" + "最经典、应用最广的 BSA 预测公式" );
        calculateBSA( user ); // calculateBSA 方法默认使用 Du Bois 公式
            // 用 mx、mn 记录用各个公式计算出的最大最小 BSA 值,作为一个参考范围
        double tempBSA = user.getBSA(); // 之后还要用 tempBSA 记录各个公式计算结果
        double maxBSA = tempBSA, minBSA = tempBSA; // 先得到 Du Bois 公式算出来的 BSA

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      2. Schlich( 施利希 )公式     */
        System.out.println( "2. Schlich( 施利希 )公式" + "\n\t————" + "具有性别区分度" );
        tempBSA = BSACalculator.calcBySchlichEquation( user.getGender(), user.getWeightKg(),
                    user.getHeightCm() );
        maxBSA = Math.max( tempBSA, maxBSA );
        minBSA = Math.min( tempBSA, minBSA );
        System.out.printf( "你的 BSA 数值为:\t%.3f" + HealthMetricUnit.BSA.getUnit() + "\n", tempBSA );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      3. Mosteller( 莫斯特勒 )公式      */
        System.out.println( "3. Mosteller( 莫斯特勒 )公式" + "\n\t————" + "将指数运算化简为开平方,适合日常快速估算" );
        tempBSA = BSACalculator.calcByMostellerEquation( user.getWeightKg(), user.getHeightCm() );
        maxBSA = Math.max( tempBSA, maxBSA );
        minBSA = Math.min( tempBSA, minBSA );
        System.out.printf( "你的 BSA 数值为:\t%.3f" + HealthMetricUnit.BSA.getUnit() + "\n", tempBSA );

        Service.threadSleep( timeInterval );
        System.out.println();

        /*      4. Haycock( 海科克 )公式     */
        System.out.println( "4. Haycock( 海科克 )公式" + "\n\t————" + "用于儿童 / 青少年医疗,推荐 2-18 岁儿童 / 青少年" );
        tempBSA = BSACalculator.calcByHaycockEquation( user.getWeightKg(), user.getHeightCm() );
        maxBSA = Math.max( tempBSA, maxBSA );
        minBSA = Math.min( tempBSA, minBSA );
        System.out.printf( "你的 BSA 数值为:\t%.3f" + HealthMetricUnit.BSA.getUnit() + "\n", tempBSA );

        /*      最后输出计算结果范围      */
        Service.threadSleep( timeInterval );
        System.out.println();
        System.out.printf( "\t结合以上公式结果,你的 BSA 所在范围大致为[ %.3f, %.3f ]" +
                            HealthMetricUnit.BSA.getUnit() + "\n", minBSA, maxBSA );
    }


    /*  3.5 查看身体指数理想值/范围    */

    /*  3.5.1 查看体态评估类身体指数理想值/范围    */
    // 3.5.1.1 查看体重理想值/范围
    public void checkIdealWeight( User user ){
        if ( idealRangeArr == null ) initializeIdealRangeArr();

        boolean isSuccess = printIdealRangeByIdx( IdealRangeIdx.WEIGHT.getIdx() );
        if ( isSuccess ){
            // 成功打印理想范围 => 接下来可以具体计算相应的理想体重
            /*  方法① 根据身高确定理想体重范围    */
            System.out.println( "\t方法① 根据身高确定理想体重范围" );

            // 1. 规定性别、身高 有效范围
            String[] validGenders = new String[] { "女", "男" };
            double heightStart = 25, heightEnd = 320; // 最矮的婴儿: 25 cm, 最高的人: 3.19 m

            // 2. 检查并获取有效性别、身高
            String gender = getValidStringInput( "性别", user.getGender(), validGenders );
            double heightCm = getValidDoubleInput( "身高( cm )", user.getHeightCm(), heightStart, heightEnd );

            // 3. 定义要减掉的常数项( 男 105 女 100 )
            double constTerm = ( Service.isMan( gender ) ? 105 : 100 );

            // 4. 得到目标结果
                // 4.1 计算理想体重( 范围 )
            double idealWeightKg1 = heightCm - constTerm;
            double idealWeightKgStart1 = idealWeightKg1 * 0.9; // 区间左端
            double idealWeightKgEnd1 = idealWeightKg1 * 1.1; // 区间右端
                // 4.2 更新 user 成员变量
            user.setGender( gender ); user.setHeightCm( heightCm );
                // 4.3 输出结果( 保留 3 位小数 )
            System.out.printf( "你当前身高对应的理想体重为 %.3f" + HealthMetricUnit.WEIGHT.getUnit() +
                                ",理想体重范围[ %.3f, %.3f ]" + HealthMetricUnit.WEIGHT.getUnit() +
                                "\n", idealWeightKg1, idealWeightKgStart1, idealWeightKgEnd1    );

            /*  方法② 根据 BMI 倒推理想体重范围    */
            System.out.println( "\t方法② 根据 BMI 倒推理想体重范围 " );

            // 1. 定义 BMI 理想范围
            double idealBMIStart = 18.5, idealBMIEnd = 23.9;

            // 2. 计算 BMI 对应的体重
            double idealWeightKgStart2 = BMICalculator.getWeightKgByBMI( heightCm, idealBMIStart ); // 区间左端
            double idealWeightKgEnd2 = BMICalculator.getWeightKgByBMI( heightCm, idealBMIEnd ); // 区间右端

            // 3 输出结果( 保留 3 位小数 )
            System.out.printf( "理想体重范围[ %.3f, %.3f ]" + HealthMetricUnit.WEIGHT.getUnit() +
                               "\n", idealWeightKgStart2, idealWeightKgEnd2     );
        }
    }

    // 3.5.1.2 查看 BMI 理想值/范围
    public void checkIdealBMI(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.BMI.getIdx() );
    }

    // 3.5.1.3 查看 WC 理想值/范围
    public void checkIdealWaistCircum(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.WC.getIdx() );
    }

    // 3.5.1.4 查看 WHR 理想值/范围
    public void checkIdealWHR(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.WHR.getIdx() );
    }

    // 3.5.1.5 查看 BFR 理想值/范围
    public void checkIdealBFR(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.BFR.getIdx() );
    }

    // 3.5.1.6 查看 BRI 理想值/范围
    public void checkIdealBRI(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.BRI.getIdx() );
    }


    /*  3.5.2 查看能量代谢类身体指数理想值/范围    */
    // 3.5.2.1 查看 BMR 理想值/范围
    public void checkIdealBMR(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.BMR.getIdx() );
    }

    // 3.5.2.2 查看 TDEE 理想值/范围
    public void checkIdealTDEE(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.TDEE.getIdx() );
    }


    /*  3.5.3 查看生理特征类身体指数理想值/范围    */
    // 3.5.3.1 查看 BSA 理想值/范围
    public void checkIdealBSA(){
        if ( idealRangeArr == null ) initializeIdealRangeArr();
        printIdealRangeByIdx( IdealRangeIdx.BSA.getIdx() );
    }


    /*  3.6 根据需求要求用户输入合法数据  */
    /*  3.6.1 输入 double 类型数据   */
    // 3.6.1.1 循环输入直到数据合法
    public double inputValidationLoop( double rangeStart, double rangeEnd ){
        /*
         *      在编程中,通过循环来保证用户输入正确数据,直到输入满足特定条件才放行的操作通常
         *  被称为输入验证循环( Input Validation/Verification Loop )或输入校验循环
         *      这种技术广泛应用于各种需要用户输入的场景,用于确保用户输入的数据符合预期格式或
         *  条件,通过循环检查用户输入,并在输入无效时提示用户重新输入,可以有效防止因无效输入导
         *  致的程序错误,提高程序的健壮性和用户体验
         */
        Scanner scanner = new Scanner( System.in );
        double input = scanner.nextDouble();
        while ( ! isDataWithinInterval( input, rangeStart, rangeEnd ) ){
            System.out.print( "你输入的数据有误,请重新输入并按下 Enter:\t" );
            input = scanner.nextDouble();
        }

        return input;
    }

    // 3.6.1.2 判断数据是否在要求的闭区间内
    public boolean isDataWithinInterval( double data, double rangeStart, double rangeEnd ){
        return ( rangeStart <= data && data <= rangeEnd );
    }

    /*  3.6.2 输入 int 类型数据   */
    // 3.6.2.1 循环输入直到数据合法
    public int inputValidationLoop( int rangeStart, int rangeEnd ){
        Scanner scanner = new Scanner( System.in );
        int input = scanner.nextInt();
        while ( ! isDataWithinInterval( input, rangeStart, rangeEnd ) ){
            System.out.print( "你输入的数据有误,请重新输入并按下 Enter:\t" );
            input = scanner.nextInt();
        }

        return input;
    }

    // 3.6.2.2 判断数据是否在目标区间
    public boolean isDataWithinInterval( int data, int rangeStart, int rangeEnd ){
        return ( rangeStart <= data && data <= rangeEnd );
    }

    /*  3.6.3 输入 String 类型数据    */
    // 3.6.3.1 循环输入直到字符串合法( )
    public String inputValidationLoop( String[] validStrs ){
        Scanner scanner = new Scanner( System.in );
        String inputStr = scanner.next();
        while ( !hasTargetStr( inputStr, validStrs ) ){
            System.out.print( "你输入的数据有误,请重新输入并按下 Enter:\t" );
            inputStr = scanner.next();
        }

        return inputStr;
    }

    // 3.6.3.2 查找字符串数组中是否存在目标字符串
    public boolean hasTargetStr( String targetStr, String[] strArr ){
        Service service = new Service();
        int idx = service.getIdxOfTargetStr( targetStr, strArr );
        // 返回值: 字符串数组含有目标字符串 => 返回下标 ≠ -1
        return ( idx != -1 );
    }

    // 3.6.3.3 查找目标字符串在字符串数组中的下标
    public int getIdxOfTargetStr( String targetStr, String[] strArr ){
        int idx = strArr.length - 1;
        for ( ; idx >= 0; --idx ) {
            if ( strArr[ idx ].equals( targetStr ) ){
                break;
            }
        }
        return idx; // 没找到 OR 数组为空 => 返回 -1
    }



    /*      4. 私有静态方法       */
    /*  4.1 初始化数组   */
    // 4.1.1 初始化"身体指标介绍"数组
    private static void initializeIntroductionArr(){
        if ( introductionArr != null ) return;

        introductionArr = new HealthMetricIntroduction[] {
                // arr[ 0 ] 详解 BMI
                new HealthMetricIntroduction(
                        "BMI",
                        "身体质量指数( Body Mass Index )是体重(公斤)除以身高(米)的平方得出的数值,用于评估体重与身高之间的比例关系",
                        "BMI = 体重(kg) / 身高(m)^2",
                        "初步判断个体是否处于健康体重范围,是评估肥胖程度的常用指标之一",
                        "不能区分脂肪和肌肉,对于肌肉发达或运动员等人群可能不够准确"
                ),
                // arr[ 1 ] 详解 WC
                new HealthMetricIntroduction(
                        "Waist Circumference",
                        "腰围( Waist Circumference )是腹部最细处的周长,通常在肚脐周围或肋骨下方与髂前上棘之间的中点处测量",
                        "使用软尺在腹部最细处水平环绕一周",
                        "用于评估腹部脂肪堆积情况,腹部脂肪过多与多种慢性疾病风险增加相关",
                        "单独使用腰围无法全面评估全身脂肪分布"
                ),
                // arr[ 2 ] 详解 WHR
                new HealthMetricIntroduction(
                        "WHR",
                        "腰臀比( Waist-to-Hip Ratio )是腰围与臀围的比值,用于评估腹部脂肪与臀部脂肪的比例",
                        "WHR = 腰围(cm)/ 臀围(cm)",
                        "能更精准地反映内脏脂肪含量及分布对健康的潜在影响",
                        "对于臀部脂肪分布异常的人群可能不够准确"
                ),
                // arr[ 3 ] 详解 BFR
                new HealthMetricIntroduction(
                        "BFR",
                        "体脂率( Body Fate Rate )是身体脂肪占总体重的比例,反映身体成分组成",
                        "包括皮褶厚度测量法、生物电阻抗法、双能 X 线吸收法(DXA)等",
                        "帮助评估身体成分是否合理,过高的体脂率可能增加患病风险",
                        "不同测量方法的准确性差异较大"
                ),
                // arr[ 4 ] 详解 BRI
                new HealthMetricIntroduction(
                        "BRI",
                        "身体圆度指数( Body Roundness Index )是基于身体不同部位的围度、直径等测量数据,通过特定计算公式或模型确定的身体圆润程度指标",
                        "BRI = 364.2 - 365.5 * 根号下( 1 - 分式² ), 分式 = ( 腰围 / 2PI ) / ( 0.5 * 身高 ) = 腰围 / ( PI * 身高 )",
                        "辅助评估身体的肥胖状况及脂肪分布情况",
                        "计算较为复杂,可能需要专业设备和人员"
                ),
                // arr[ 5 ] 详解 BMR
                new HealthMetricIntroduction(
                        "BMR",
                        "基础代谢率( Basal Metabolic Rate )是指人体在清醒而又极端安静的状态下,不受肌肉活动、环境温度、食物及精神紧张等影响时的能量代谢率",
                        "主要有公式法(如哈里斯 - 本尼迪克特方程)和测量法(通过专业设备测定氧气消耗量或二氧化碳产生量)",
                        "反映人体维持基本生命活动所需的最低能量消耗",
                        "公式法估算可能存在误差,测量法需要专业设备"
                ),
                // arr[ 6 ] 详解 TDEE
                new HealthMetricIntroduction(
                        "TDEE",
                        "总每日能量消耗( Total Daily Energy Expenditure )是在基础代谢率的基础上,结合日常活动、食物生热效应等因素计算得出的能量消耗",
                        "TDEE = BMR × 活动系数 + 食物生热效应",
                        "用于制定饮食计划和运动方案,以达到能量平衡或控制体重目标",
                        "活动系数的估算可能存在误差"
                ),
                // arr[ 7 ] 详解 BSA
                new HealthMetricIntroduction(
                        "BSA",
                        "体表面积( Body Surface Area )是人体外表面积的大小,用于衡量人体的生理特征",
                        "杜布瓦/杜博伊斯公式: BSA = 0.007184 × 身高(cm)^0.725 × 体重(kg)^0.425",
                        "在医学领域用于药物剂量计算、评估热量散失等",
                        "计算公式较多,不同公式结果可能存在差异"
                ),
        };
    }

    // 4.1.2 初始化"身体指标理想值/范围"数组
    private static void initializeIdealRangeArr(){
        if ( idealRangeArr != null ) return;

        idealRangeArr = new HealthMetricIdealRange[] {
                // arr[ 0 ] 查看 weight 理想值/范围
                new HealthMetricIdealRange( "Weight", "男性: ( 身高(cm) - 105 )浮动 ±10%, 女性: ( 身高(cm) - 100 )浮动 ±10% \nOR 根据 BMI 理想范围( 18.5-23.9 ),反推出理想体重范围" ),
                // arr[ 1 ] 查看 BMI 理想值/范围
                new HealthMetricIdealRange( "BMI", "国际: 18.5-24.9, 亚洲: 18.5-22.9, 中国: 18.5-23.9" ),
                // arr[ 2 ] 查看 Waist Circumference 理想值/范围
                new HealthMetricIdealRange( "WC", "男性: < 90cm ( 亚洲 < 85cm ), 女性: < 80cm ( 亚洲 < 80cm )" ),
                // arr[ 3 ] 查看 WHR 理想值/范围
                new HealthMetricIdealRange( "WHR", "男性: < 0.9, 女性: < 0.85" ),
                // arr[ 4 ] 查看 BFR 理想值/范围
                new HealthMetricIdealRange( "BFR", "男性: 10%-20%; 女性: 18%-28%" ),
                // arr[ 5 ] 查看 BRI 理想值/范围
                new HealthMetricIdealRange( "BRI", "男性: < 85, 女性: < 76" ),
                // arr[ 6 ] 查看 BMR 理想值/范围
                new HealthMetricIdealRange( "BMR", "男性: 1500-2000 Kcal/天; 女性: 1200-1500 Kcal/天" ),
                // arr[ 7 ] 查看 TDEE 理想值/范围
                new HealthMetricIdealRange( "TDEE", "男性: 2000-3500 kcal/天, 女性: 1500-2500 kcal/天" ),
                // arr[ 8 ] 查看 BSA 理想值/范围
                new HealthMetricIdealRange( "BSA", "成人: 1.5-2.0 m²; 儿童: 0.5-1.5 m²" ),
        };
    }


    // 4.2 根据传入的下标访问数组
    // 4.2.1 根据下标打印身体指标介绍
    private static boolean printIntroByIdx( int idx ){
        int len = introductionArr.length;
        boolean isSuccess = true;

        if ( 0 <= idx && idx < len ){ // [ 0, len - 1 ]
            HealthMetricIntroduction introObject = introductionArr[ idx ];
            System.out.println( "定义: " + introObject.getDefinition() );
            System.out.println( "公式: " + introObject.getFormula() );
            System.out.println( "功能: " + introObject.getFunction() );
            System.out.println( "局限性: " + introObject.getLimit() );
        } else {
            isSuccess = false;
            System.out.printf( "请传入[ 0, %d ]范围内的下标来获得身体指数详解内容!\n", len - 1 );
        }

        return isSuccess;
    }

    // 4.2.2 根据下标打印身体指标理想值/范围 => 相当于模板
    private static boolean printIdealRangeByIdx( int idx ){
        int len = idealRangeArr.length;
        boolean isSuccess = true;
        try {
            // 可能抛出异常的代码 => 可能会抛出数组越界异常
            HealthMetricIdealRange idealRangeObject = idealRangeArr[ idx ];
            System.out.println( "理想范围: " + idealRangeObject.getIdealRange() );
            /*
             *      try 块中的代码一旦抛出异常,程序将不会继续执行 try 块中剩余的代码,
             *  而是跳转到匹配的 catch 块,如果没有匹配的 catch 块,程序将终止执行
             */
        } catch ( ArrayIndexOutOfBoundsException exception ){
            // 处理数组越界异常
            isSuccess = false;
            System.out.printf( "请传入[ 0, %d ]范围内的下标来获得身体指数详解内容!\n", len - 1 );
            /*
             *      catch 块应该从最具体的异常开始捕获,逐渐到更一般的异常
             *      应该避免空的 catch 块,即使捕获异常也应该有适当的处理或至少记录异常信息
             */
        } finally {
            /*
             *      无论是否发生异常,finally 块中的代码都会执行
             *      finally 块是可选的,但它提供了一个保证执行的代码区域--即使在异常发生后,
             *  这对于确保资源被适当释放非常有用
             *      在 finally 块中释放资源,确保即使在异常发生时也能释放资源
             */
            // System.out.println();
        }
        return isSuccess;
    }


    /*  4.3 通过输入验证循环检查并返回有效的目标数据    */
    // 4.3.1 检查并返回有效的 double 类型数据
    private static double getValidDoubleInput( String nameOfData, double data, double rangeStart, double rangeEnd ){
        // 1. 创建对象以便调用输入验证循环方法
        Service service = new Service();

        // 2. 检查参数是否合法
            // 注意: ! 在区间内
        if ( ! service.isDataWithinInterval( data, rangeStart, rangeEnd ) ){
            System.out.print( "你的<" + nameOfData + ">数据出错了,需要输入新数据噢:\t" );
            // 调用输入验证循环方法
            data = service.inputValidationLoop( rangeStart, rangeEnd );
        }

        // 3. 返回数据
        return data;
    }

    // 4.3.2 检查并返回有效的 int 类型数据
    private static int getValidIntInput( String nameOfData, int data, int rangeStart, int rangeEnd ){
        // 1. 创建对象以便调用输入验证循环方法
        Service service = new Service();

        // 2. 检查参数是否合法
        if ( !service.isDataWithinInterval( data, rangeStart, rangeEnd ) ){
            System.out.print( "你的<" + nameOfData + ">数据出错了,需要输入新数据噢:\t" );
            data = service.inputValidationLoop( rangeStart, rangeEnd );
        }

        // 3. 返回数据
        return data;
    }

    // 4.3.3 检查并返回有效的 String 类型数据
    private static String getValidStringInput( String nameOfStr, String str, String[] validStrs ){
        // 1. 创建对象以便调用输入验证循环方法
        Service service = new Service();

        // 2. 检查参数是否合法
        if ( !service.hasTargetStr( str, validStrs) ){
            System.out.print( "你的<" + nameOfStr + ">数据出错了,需要输入新数据噢:\t" );
            str = service.inputValidationLoop( validStrs );
        }

        // 3. 返回数据
        return str;
    }


    /*  4.4 打印操作    */
    // 4.4.1 打印给定数量的某种字符
    private static void printSpecificNumberOfChar( int len, char ch ){
        for ( int i = 0; i < len; ++i ){
            System.out.print( ch );
        }
    }

    // 4.4.2 模拟打字机
        // 4.4.2.1 随机延迟 50-150 ms 打印字符串的每个字符
    private static void typewriter( String str ){
        int len = str.length();
        for ( int j = 0; j < len; ++j ){
            // String 不能像数组那样通过 arr[ 下标 ] 访问字符,而是使用 charAt( 下标 )
            System.out.print( str.charAt( j ) );
            int delay = ( int )( Math.random() * 100 ) + 50; // 50-150 ms 随机延迟,模拟打字
            /*
             *      Math.random() 返回[ 0, 1 )的小数,乘以 range,再总体强转为[ 0, range )的整数
             *      而 Random 类需要创建对象 Random random = ...,通过对象获取随机数,
             *  random.nextInt( range ) 获得[ 0, range )的整数
             */
            // 每打印 1 个字符就暂停微小时间( 毫秒 ),营造出打字机的视觉效果
            Service.threadSleep( delay );
        }
    }
        // 4.4.2.2 打印字符串数组
    private static void typewriter( String[] strings ){
        for ( String str : strings ){
            Service.typewriter( str );
        }
    }
        // 4.4.2.3 打印字符串( 自定义延长的毫秒数 )
    private static void typewriter( String str, int delay ){
        for ( char ch : str.toCharArray() ){
            // 不可以对 String 进行增强 for 遍历,需要先转换为字符数组
            System.out.print( ch );
            Service.threadSleep( delay );
        }
    }
        // 4.4.2.4 让当前执行的线程休眠( 暂停执行 )一段时间
    private static void threadSleep( int delay ){
        try {
            Thread.sleep( delay );
        } catch ( InterruptedException e ){
            // 必须捕获 InterruptedException,否则编译报错
            Thread.currentThread().interrupt(); // 在捕获后恢复中断状态
        } finally { }
    }
        // 4.4.2.5 模拟加载时的打点操作
    private static void printLoadingDots( int numOfDot ){
        // 1. 变量声明
        char dot = '.';
        int delay = 100; // 延迟 100 ms 比较合适
        int executeTimes = ( int )( Math.random() * 4 ) + 2; // "加载"[ 2, 5 ]次

        // 2. 打点
        for ( int i = 0; i < executeTimes; ++i ){
            for ( int j = 0; j < numOfDot; ++j ){
                // 打印 . 前后都进行 sleep 操作,保证看清楚每一个点的产生、删除
                Service.threadSleep( delay );
                System.out.print( dot );
                Service.threadSleep( delay );
            }

            for ( int j = 0; j < numOfDot; ++j ){
                // 打印 \b 前后都进行 sleep 操作,防止太快清理前面的点,运行效果还可
                Service.threadSleep( delay );
                System.out.print( "\b" ); //  退格符 \b,用于将光标向左移动一格,模拟退格
                Service.threadSleep( delay );
            }
        }
    }



    /*      5. 私有非静态方法      */
    // 5.1 初始化数组
    // 5.1.1 初始化"身体指数计算"方法的访问标记数组
    private void initializeAccessFlagArr(){
        accessFlagsOfCalculationMethods = new boolean[ numOfCalculationMethod ];
        Arrays.fill( accessFlagsOfCalculationMethods, false );
        // Java 的 Arrays 类提供了一个 fill 方法,可以用来将数组的所有元素设置为特定值
    }



    /*      6. 公有静态方法       */
    /*  6.1  main 方法测试  */
    public static void main( String[] args ){
        Service.pressAnyKeyToContinue();
    }


    /*  6.2 打印操作  */
    // 6.2.1 打印前言
    public static void printCalculatorForeword(){
        // ※※※⁜⌂→→→▶▶▶▷▷▷▲△►▻◆◯■■⇶⇶⇶⇢⇰⇉¬⌉⌋⌈⌉⌋⌊«»⟦⟧⟭⟬‖▣▦◀▶◀◁▷▱△◊◇◆◈◢◤◥◣■↩↩↩↲↲↲↵↵↵⇦⇚▧▤▦▣▩▥⇲⇱
        Service.printAsteriskLine();
        String[] Foreword = new String[] {
                "▤ 引言: 健康指数知多少? ⇲\n",
                "→ 日常生活我们可以使用 BMI( 身体质量指数 )粗略衡量胖瘦\n",
                "  然而如果想要比较准确地估计胖瘦,还可以选择 WC( 腰围 )或者 WHR( 腰臀比 ),\n",
                "  甚至进阶到 BFR( 体脂率 )以及 BRI( 身体圆度指数 ) ↵↵↵\n",
                "⇉ 为什么别人怎么吃都不胖?或许我们可以算一算 BMR( 基础代谢率 ) 以及 \n",
                "   TDEE( 每日总能量消耗 ),去理解\"整天赖床也能燃烧热量\" ↲↲↲\n",
                "⇶ 万物皆有表面积,人也不例外————BSA( 体表面积 ),\n",
                "   但你是否好奇这个陌生的缩略词和健康状态之间的联系? ↩↩↩\n",
                "▥ 那么,请带着疑惑与求知的问号,体验以下这款小程序 ⇱\n",
        };
        typewriter( Foreword );
        Service.printAsteriskLine();
        System.out.print( "\n\n" );

        Foreword = null; // 释放对象引用
    }

    // 6.2.2 打印功能清单
    public static void printCalculatorFunctionList(){
        Service.printEqualsLine();
        System.out.println( "【*{*[*(*<——_«_健康计算器_»_——>*)*]*}*】" );
        System.out.println( "  ◤‾请过目———功能清单‾◥  " );

        StringBuilder functionList = new StringBuilder();
        functionList.append( "< Ⅰ >健康百科: 详解各种身体指数[ 涨知识啦ヾ(≧▽≦*) ]\n" )
                .append( "\t1.1 详解体态评估类身体指数\n" )
                .append( "1.1.1 详解 BMI\t" ).append( "1.1.2 详解 WC\t" ).append( "1.1.3 详解 WHR\t" ).append( "1.1.4 详解 BFR\t" ).append( "1.1.5 详解 BRI\n" )
                .append( "\t1.2 详解能量代谢类身体指数\n" )
                .append( "1.2.1 详解 BMR\t" ).append( "1.2.2 详解 TDEE\n" )
                .append( "\t1.3 详解生理特征类身体指数\n" )
                .append( "1.3.1 详解 BSA\n\n" );
        functionList.append( "< Ⅱ >招牌功能: 计算你的各种身体指数[ 需要测量身体数据哦(⊙o⊙) ]\n" )
                .append( "\t2.1 计算体态评估类身体指数\n" )
                .append( "2.1.1 计算 BMI\t" ).append( "2.1.2 计算 WHR\t" ).append( "2.1.3 计算 BFR\t" ).append( "2.1.4 计算 BRI\n" )
                .append( "\t2.2 计算能量代谢类身体指数\n" )
                .append( "2.2.1 计算 BMR\t" ).append( "2.2.2 计算 TDEE\n" )
                .append( "\t2.3 计算生理特征类身体指数\n" )
                .append( "2.3.1 计算 BSA\n" )
                .append( "注意: 计算结果存在误差,请以专业测量结果为准!!!\n\n" );
        functionList.append( "< Ⅲ >科学评估: 分析你的各种身体指数[ 🎇前方高能😎 ]\n" )
                .append( "\t3.1 分析体态评估类身体指数\n" )
                .append( "3.1.1 分析 BMI\t" ).append( "3.1.2 分析 WC\t" ).append( "3.1.3 分析 WHR\t" ).append( "3.1.4 分析 BFR\t" ).append( "3.1.5 分析 BRI\n" )
                .append( "\t3.2 分析能量代谢类身体指数\n" )
                .append( "3.2.1 分析 BMR\t" ).append( "3.2.2 分析 TDEE\n" )
                .append( "\t3.3 分析生理特征类身体指数\n" )
                .append( "3.3.1 分析 BSA\n\n" );
        functionList.append( "< Ⅳ >数据对比: 看看不同公式算出的身体指数相差几何😶🤨🤔\n" )
                .append( "\t4.1 对比不同公式得到的体态评估类身体指数\n" )
                .append( "4.1.1 对比不同公式得到的 BFR\n" )
                .append( "\t4.2 对比不同公式得到的能量代谢类身体指数\n" )
                .append( "4.2.1 对比不同公式得到的 BMR\n" )
                .append( "\t4.3 对比不同公式得到的生理特征类身体指数\n" )
                .append( "4.3.1 对比不同公式得到的 BSA\n\n" );
        functionList.append( "< Ⅴ >健康对标: 查看身体指数理想值[ ┏(゜ω゜)=👉仅供参考~ ]\n" )
                .append( "\t5.1 查看体态评估类身体指数理想值/范围\n" )
                .append( "5.1.1 查看体重理想值/范围\t" ).append( "5.1.2 查看 BMI 理想值/范围\n" ).append( "5.1.3 查看 WC 理想值/范围\t" ).append( "5.1.4 查看 WHR 理想值/范围\n" ).append( "5.1.5 查看 BFR 理想值/范围\t" ).append( "5.1.6 查看 BRI 理想值/范围\n" )
                .append( "\t5.2 查看能量代谢类身体指数理想值/范围\n" )
                .append( "5.2.1 查看 BMR 理想值/范围\t" ).append( "5.2.2 查看 TDEE 理想值/范围\n" )
                .append( "\t5.3 查看生理特征类身体指数理想值/范围\n" )
                .append( "5.3.1 查看 BSA 理想值/范围\n\n" );
        functionList.append( "< Ⅵ >更多功能,敬请期待: to be updated...\n" );
        typewriter( getStrByStrBuilder( functionList ), 40 );

        System.out.println( "  ◣_Function———List_◢  " );
        Service.printEqualsLine();
        functionList = null; // 释放对象引用
        System.out.print( "\n\n" );
    }

    // 6.2.3 打印分隔线
        // 6.2.3.1 打印水平线( 有换行 )
    public static void printHorizontalLine(){
        int len = 36; char ch = '-';
        Service.printSpecificNumberOfChar( len, ch );
        System.out.println();
    }
        // 6.2.3.2 打印等号线( 有换行 )
    public static void printEqualsLine(){
        int len = 36; char ch = '=';
        Service.printSpecificNumberOfChar( len, ch );
        System.out.println();
    }
        // 6.2.3.3 打印星号线( 有换行 )
    public static void printAsteriskLine(){
        int len = 36; char ch = '*';
        Service.printSpecificNumberOfChar( len, ch );
        System.out.println();
    }
        // 6.2.3.4 打印井号线( 有换行 )
    public static void printHashSignLine(){
        int len = 36; char ch = '#';
        Service.printSpecificNumberOfChar( len, ch );
        System.out.println();
    }
        // 6.2.3.5 打印波浪线( 有换行 )
    public static void printTildeLine(){
        int len = 36; char ch = '~';
        Service.printSpecificNumberOfChar( len, ch );
        System.out.println();
    }


    /*  6.3 不同数据类型进行转换    */
    // 6.3.1 String 转 StringBuilder
    public static StringBuilder getStrBuilderByStr( String srcStr ){
        return ( new StringBuilder( srcStr ) );
    }

    // 6.3.2 StringBuilder 转 String
    public static String getStrByStrBuilder( StringBuilder strBuilder ){
        /*
         *      String substring( int start, int end )
         *      返回一个新的 String,它包含此序列当前所包含的字符子序列
         *  也就是 StringBuilder 的 [ start, end ) 部分( 左闭右开 )
         */
        return strBuilder.substring( 0, strBuilder.length() );
    }

    // 6.3.3 将 boolean 型的 isMan 换为 int 型,作为访问数组的下标或者用来计算
    public static int intIsMan( String gender ){
        return ( isMan( gender ) ? 1 : 0 );
    }

    // 6.3.4 boolean 型 isMan
    public static boolean isMan( String gender ){
        return gender.equals( "男" );
    }


    /*  6.4 单位转换    */
    // 6.4.1 厘米化英寸
    public static double getInchByCm( double cm ){
        double conversion = 2.54; // 1 inch = 2.54 cm
        return cm / conversion;
    }

    // 6.4.2 米化厘米
    public static double getMetreByCm( double cm ){
        return cm / 100;
    }

    // 6.4.3 厘米化米
    public static double getCmByMetre( double metre ){
        return metre * 100;
    }


    /*  6.5 用户交互    */
    // 6.5.1 模拟各种加载操作( 正在计算 BMR... 计算完毕! )
    public static void printLoadingProgress( String operation, String result ){
        System.out.print( "\t" + operation ); // 注意不换行,后面要跟上"加载点"
        Service.printLoadingDots( 3 );
        System.out.println();
        System.out.println( "\t" + result );
        Service.threadSleep( 1200 ); // 1.2 s 延迟
    }

    // 6.5.2 按任意键继续
    public static void pressAnyKeyToContinue(){
        /*
         *      System.console() 方法返回 null 是一个常见问题,大多数集成开发环境( 如 Eclipse、
         *  IntelliJ IDEA、VS Code )不提供真正的控制台( Terminal ),而是使用自己的"控制台"面板
         *  模拟输入输出,这种环境下,System.console() 无法获取实际的控制台对象,因此返回 null
         */
        // 需要 import java.io.Console, Java 6+
        Console console = System.console();
        if ( console != null ){
            // 对于控制台应用,推荐使用 Console.readPassword()
            System.out.print( "\t请按任意键继续..." );
            console.readPassword(); // 无需按 Enter,直接捕获按键
        } else {
            // Scanner 作为备选
            System.out.print( "\t请按 Enter 键以继续..." );
            Scanner scanner = new Scanner( System.in );
            scanner.nextLine();
            scanner = null; // 释放对象引用
        }
        System.out.print( "\n\n" );
        /*
            方法 Ⅲ
        try {
            // 需要 import java.io.IOException,需要按 enter 键
            System.in.read();
        } catch ( IOException e ){
            e.printStackTrace();
        } finally { }
         */
    }
}
