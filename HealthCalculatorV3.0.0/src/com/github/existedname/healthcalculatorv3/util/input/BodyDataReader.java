package com.github.existedname.healthcalculatorv3.util.input;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.TDEECalculator;
import com.github.existedname.healthcalculatorv3.util.convertor.TypeConvertor;
import com.github.existedname.healthcalculatorv3.util.printer.PrinterConstants;
import com.github.existedname.healthcalculatorv3.util.printer.UIPrinter;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Objects;
import java.util.Scanner;

/**
 * 身体数据读取器, 封装了底层 {@link InputReader }, <br>
 * 用于读取并返回基本身体参数( {@link BasicBodyParameter }、{@link Gender } )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/19
 */
public final class BodyDataReader {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private BodyDataReader(){ }

    // ==================== 公有方法 ====================
    /**
     * 读取并返回有效性别
     *
     * @param scanner 扫描器
     * @return 有效性别( 类型: String )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static String readGender( Scanner scanner ){
        MethodParameterValidator.validateScanner( scanner );
        String gender = InputReader.readValidString( scanner, Gender.Category.CHINESE_NAME, Gender.getValidGenderOptions() );
        return InputProcessor.toTrimmedOrEmptyStr( gender ); // 确保去除首尾空字符
    }

    /**
     * 读取并返回有效年龄
     *
     * @param scanner 扫描器
     * @return 有效年龄( 类型: int, 单位: 年/岁 )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static int readAge( Scanner scanner ){
        return readIntBodyParameter( scanner, BasicBodyParameter.AGE );
    }

    /**
     * 读取并返回有效体重
     *
     * @param scanner 扫描器
     * @return 有效体重( 类型: double, 单位: kg )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static double readWeight( Scanner scanner ){
        return readDoubleBodyParameter( scanner, BasicBodyParameter.WEIGHT );
    }

    /**
     * 读取并返回有效身高
     *
     * @param scanner 扫描器
     * @return 有效身高( 类型: double, 单位: cm )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static double readHeight( Scanner scanner ){
        return readDoubleBodyParameter( scanner, BasicBodyParameter.HEIGHT );
    }

    /**
     * 读取并返回有效腰围
     *
     * @param scanner 扫描器
     * @return 有效腰围( 类型: double, 单位: cm )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static double readWaistCircumference( Scanner scanner ){
        return readDoubleBodyParameter( scanner, BasicBodyParameter.WAIST_CIRCUMFERENCE );
    }

    /**
     * 读取并返回有效臀围
     *
     * @param scanner 扫描器
     * @return 有效臀围( 类型: double, 单位: cm )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static double readHipCircumference( Scanner scanner ){
        return readDoubleBodyParameter( scanner, BasicBodyParameter.HIP_CIRCUMFERENCE );
    }

    /**
     * 读取并返回有效颈围
     *
     * @param scanner 扫描器
     * @return 有效颈围( 类型: double, 单位: cm )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static double readNeckCircumference( Scanner scanner ){
        return readDoubleBodyParameter( scanner, BasicBodyParameter.NECK_CIRCUMFERENCE );
    }

    /**
     * 读取并返回有效活动系数
     *
     * @param scanner 扫描器
     * @return 有效活动系数( 类型: double, 单位: 无 )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static double readActivityCoefficient( Scanner scanner ){
        System.out.println( "请阅读以下活动系数对照表, 找到属于你的活动系数👆" );
        UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.READING_PREPARATION_PAUSE );
        TDEECalculator.printCoefficientTable();
//        System.out.print( "请根据该表输入你的活动系数估计值:\t" );
        UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.TABLE_DISPLAY_PAUSE );
        return readDoubleBodyParameter( scanner, BasicBodyParameter.ACTIVITY_COEFFICIENT );
    }


    // ==================== 私有辅助方法 ====================
    /**
     * main 方法测试
     *
     * @param args 测试参数
     */
    public static void main( String[] args ){
        Scanner scanner = new Scanner( System.in );
        System.out.println( "你的活动系数为: " + readActivityCoefficient( scanner ) );
    }

    /**
     * 读取并返回有效的 int 类型基本身体参数( 主要是 {@link BasicBodyParameter#AGE }
     *
     * @param scanner 扫描器
     * @param basicBodyParameter 基本身体参数枚举成员( {@link BasicBodyParameter } )
     *
     * @return 有效 int 类型基本身体参数
     * @throws NullPointerException 当 scanner 或 basicBodyParameter 为 null 时
     */
    private static int readIntBodyParameter( Scanner scanner, BasicBodyParameter basicBodyParameter ){
        MethodParameterValidator.validateScanner( scanner );
        Objects.requireNonNull( basicBodyParameter, "参数 basicBodyParameter( 基本身体参数 )不能为 null" );
        return InputReader.readValidInt(
                scanner,
                basicBodyParameter.getChineseName(),
                basicBodyParameter.getUnit(),
                TypeConvertor.toInt( basicBodyParameter.getMinValue() ),
                TypeConvertor.toInt( basicBodyParameter.getMaxValue() )
        );
    }

    /**
     * 读取并返回有效的 double 类型基本身体参数
     *
     * @param scanner 扫描器
     * @param basicBodyParameter 基本身体参数枚举成员( {@link BasicBodyParameter } )
     *
     * @return 有效 double 类型基本身体参数
     * @throws NullPointerException 当 scanner 或 basicBodyParameter 为 null 时
     */
    private static double readDoubleBodyParameter( Scanner scanner, BasicBodyParameter basicBodyParameter ){
        MethodParameterValidator.validateScanner( scanner );
        Objects.requireNonNull( basicBodyParameter, "参数 basicBodyParameter( 基本身体参数 )不能为 null" );
        return InputReader.readValidDouble(
                scanner,
                basicBodyParameter.getChineseName(),
                basicBodyParameter.getUnit(),
                basicBodyParameter.getMinValue(),
                basicBodyParameter.getMaxValue()
        );
    }
}
