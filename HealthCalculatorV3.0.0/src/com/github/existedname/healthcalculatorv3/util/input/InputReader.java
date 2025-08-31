package com.github.existedname.healthcalculatorv3.util.input;

import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * 输入读取器, 提供读取指定名称( "年龄"、"体重"、"性别" )的有效数据( int、double、String 类型 )的方法<br>
 * ( 通过打印提示信息、输入验证循环保证数据有效 )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/17 11:30
 */
public final class InputReader {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private InputReader(){ }

    // ==================== 公有方法 ====================
    /**
     * 读取并返回有效的 double 类型数据
     * <p>
     * 内部通过打印提示信息、输入验证循环、{@link Scanner#hasNextDouble() } 保证数据有效, <br>
     * 使用 try-catch 块保证输入异常时程序能正常运行
     * </p>
     *
     * @param scanner 扫描器
     * @param dataName 数据名称( String ), 比如"身高"、"体重"
     * @param unit 单位( String ), 比如"kg"、"cm", 可为空字符串
     * @param min 数据有效范围下限
     * @param max 数据有效范围上限
     *
     * @return 有效的 double 类型数据
     * @throws NullPointerException 当 scanner 或 dataName 或 unit 为 null 时
     * @throws IllegalArgumentException 当 min ≥ max 时
     * @since 2.0.0
     */
    public static double readValidDouble( Scanner scanner, String dataName, String unit, double min, double max ){
        MethodParameterValidator.validateScanner( scanner );
        Objects.requireNonNull( dataName, "参数 dataName( 数据名称 )不能为 null" );
        Objects.requireNonNull( unit, "参数 unit( 单位 )不能为 null" );
        MethodParameterValidator.validateRange( min, max );
        
        printPromptMessage( dataName, unit, min, max );

        double input;
        while ( true ){
            try {
                if ( scanner.hasNextDouble() ){
                    input = scanner.nextDouble();
                    scanner.nextLine(); // 清理换行符, 避免干扰 readValidString()
                    if ( InputValidator.isInRange( input, min, max ) ){
                        return input;
                    } else {
                        System.out.print( "❌你输入的数据不在有效范围内! 请重新输入并按下 Enter:\t" );
                    }
                } else {
                    System.out.print( "❌输入格式错误! 请输入小数:\t" );
                    scanner.nextLine(); // 清理非法输入及换行符
                }
            } catch ( Exception e ){
                System.out.print( "⚠输入出现异常, 请重新输入:\t" );
                scanner.nextLine();
            }
        }
    }

    /**
     * 读取并返回有效的 int 类型数据
     * <p>
     * 内部通过打印提示信息、输入验证循环、{@link Scanner#hasNextInt() } 保证数据有效, <br>
     * 使用 try-catch 块保证输入异常时程序能正常运行
     * </p>
     *
     * @param scanner 扫描器
     * @param dataName 数据名称( String ), 比如"年龄"
     * @param unit 单位( String ), 比如"岁/年", 可为空字符串
     * @param min 数据有效范围下限
     * @param max 数据有效范围上限
     *
     * @return 有效的 int 类型数据
     * @throws NullPointerException 当 scanner 或 dataName 为 null 时
     * @throws IllegalArgumentException 当 min ≥ max 时
     * @since 2.0.0
     */
    public static int readValidInt( Scanner scanner, String dataName, String unit, int min, int max ){
        MethodParameterValidator.validateScanner( scanner );
        Objects.requireNonNull( dataName, "参数 dataName( 数据名称 )不能为 null" );
        Objects.requireNonNull( unit, "参数 unit( 单位 )不能为 null" );
        MethodParameterValidator.validateRange( min, max );
        
        printPromptMessage( dataName, unit, min, max );

        int input;
        while ( true ){
            try {
                if ( scanner.hasNextInt() ){
                    input = scanner.nextInt();
                    scanner.nextLine(); // 清理换行符, 避免干扰 readValidString()
                    if ( InputValidator.isInRange( input, min, max ) ){
                        return input;
                    } else {
                        System.out.print( "❌你输入的数据不在有效范围内! 请重新输入并按下 Enter:\t" );
                    }
                } else {
                    System.out.print( "❌输入格式错误! 请输入整数:\t" );
                    scanner.nextLine(); // 清理非法输入及换行符
                }
            } catch ( Exception e ){
                System.out.print( "⚠输入出现异常, 请重新输入:\t" );
                scanner.nextLine();
            }
        }
    }

    /**
     * 读取并返回有效的 String 类型数据
     * <p>
     * 内部通过打印提示信息、输入验证循环保证数据有效, <br>
     * 使用 try-catch 块保证输入异常时程序能正常运行<br>
     * 由于输入字符串一定会成功, 于是取消了 {@link Scanner#hasNextLine() }
     * </p>
     *
     * @param scanner 扫描器
     * @param dataName 数据名称( String ), 比如"性别"
     * @param validOptions 有效字符串集合( 比如"男"、"女" )
     *
     * @return 有效的 String 类型数据( 已去除首尾空字符 {@link String#trim() } )
     * @throws NullPointerException 当 scanner 或 dataName 为 null 时
     * @throws IllegalArgumentException 当 validOptions 为空链表时
     */
    public static String readValidString( Scanner scanner, String dataName, List< String > validOptions ){
        MethodParameterValidator.validateScanner( scanner );
        Objects.requireNonNull( dataName, "参数 dataName( 数据名称 )不能为 null" );
        Objects.requireNonNull( validOptions, "参数 validOptions( 允许输入的选项 )不能为 null" );
        if ( validOptions.isEmpty() ) throw new IllegalArgumentException( "参数 validOptions( 允许输入的选项 )不能为空链表" );

        printPromptMessage( dataName, validOptions );
        String trimmedInput = null;
        while ( true ){
            try {
                trimmedInput = readTrimmedLine( scanner );
                if ( InputValidator.contains( validOptions, trimmedInput ) ){
                    return trimmedInput;
                } else {
                    System.out.print( "❌你输入的数据不是有效字符串! 请重新输入并按下 Enter:\t" );
                }
            } catch ( Exception e ){
                System.out.print( "⚠输入出现异常, 请重新输入:\t" );
                scanner.nextLine();
            }
        }
    }

    /**
     * 读取并返回有效的 String 类型数据
     * <p>
     *     数组转链表, 由重载方法 {@link #readValidString( Scanner, String dataName, List validOptions ) } 实现具体细节
     * </p>
     *
     * @param scanner 扫描器
     * @param dataName 数据名称( String ), 比如"性别"
     * @param validOptions 有效字符串数组( 比如"男"、"女" )
     *
     * @return 有效的 String 类型数据( 已去除首尾空字符 {@link String#trim() } )
     * @throws NullPointerException 当 scanner 或 dataName 为 null 时
     * @throws IllegalArgumentException 当 validOptions 为空数组时
     * @since 2.0.0
     */
    public static String readValidString( Scanner scanner, String dataName, String[] validOptions ){
        Objects.requireNonNull( validOptions, "参数 validOptions( 有效字符串数组 )不能为 null" );
        if ( validOptions.length == 0 ) throw new IllegalArgumentException( "参数 validOptions( 有效字符串数组 )不能为空数组" );
        return readValidString( scanner, dataName, Arrays.stream( validOptions ).toList() );
    }

    /**
     * 读取并返回有效的 String 类型数据
     * <pre>
     *     只要输入的字符串( 去掉首尾空字符 )包含在有效字符串文本中即可
     *     <B>BUG</B>: 有效文本的子串( 包括自身 )也可能是无效的字符串
     * </pre>
     * <p>
     * 内部通过打印提示信息、输入验证循环保证数据有效, <br>
     * 使用 try-catch 块保证输入异常时程序能正常运行<br>
     * 由于输入字符串一定会成功, 于是取消了 {@link Scanner#hasNextLine() }
     * </p>
     *
     * @param scanner 扫描器
     * @param dataName 数据名称( String ), 比如"性别"
     * @param validText 有效字符串文本, 装载各个有效字符串的拼接( 比如"男女" )
     *
     * @return 有效的 String 类型数据( 已去除首尾空字符 {@link String#trim() } )
     * @throws NullPointerException 当 scanner 或 dataName 或 validText 为 null 时
     * @throws IllegalArgumentException 当 validText 为空字符串时
     */
    public static String readValidString( Scanner scanner, String dataName, String validText ){
        MethodParameterValidator.validateScanner( scanner );
        Objects.requireNonNull( dataName, "参数 dataName( 数据名称 )不能为 null" );
        Objects.requireNonNull( validText, "参数 validText( 有效字符串文本 )不能为 null" );
        if ( validText.isEmpty() ) throw new IllegalArgumentException( "参数 validText( 有效字符串文本 )不能为空字符串" );

        printPromptMessage( dataName, validText );
        String input = null;
        while ( true ){
            try {
                input = readTrimmedLine( scanner );
                if ( InputValidator.containsSubstring( validText, input ) ){
                    return input;
                } else {
                    System.out.print( "❌你输入的数据不是有效字符串! 请重新输入并按下 Enter:\t" );
                }
            } catch ( Exception e ){
                System.out.print( "⚠输入出现异常, 请重新输入:\t" );
                scanner.nextLine();
            }
        }
    }

    /**
     * 读取并返回去除首尾空字符的一行 String
     *
     * @param scanner 扫描器
     * @return 去除首尾空字符的一行 String( {@link InputProcessor#toTrimmedOrEmptyStr( String ) } )
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static String readTrimmedLine( Scanner scanner ){
        MethodParameterValidator.validateScanner( scanner );
        return InputProcessor.toTrimmedOrEmptyStr( scanner.nextLine() );
    }


    // ==================== 私有辅助方法 ====================


    /**
     * 输出提示信息, 提示用户输入特定范围的某个数据
     * <pre>
     *     1. min、max 可为任意数值类型, <B>但必须为 Number 的子类</B>
     *     2. 调用 {@link ValueFormatter#formatClosedRange( Number, Number ) } 格式化有效范围
     * </pre>
     *
     * @param dataName 数据名称( String ), 比如"体重"
     * @param unit 单位( String ), 比如"kg"、"cm"
     * @param min 最小值
     * @param max 最大值
     *
     * @throws NullPointerException 当 dataName 或 min 或 max 为 null 时
     */
    private static void printPromptMessage( String dataName, String unit, Number min, Number max ){
        Objects.requireNonNull( dataName, "参数 dataName( 数据名称 )不能为 null" );
        Objects.requireNonNull( min, "参数 min( 最小值 )不能为 null" );
        Objects.requireNonNull( max, "参数 max( 最大值 )不能为 null" );
        String prompt = String.format( "请输入<%s>, 范围为%s%s:\t", dataName, ValueFormatter.formatClosedRange( min, max ), unit );
        System.out.print( prompt );
    }

    /**
     * 输出提示信息, 提示用户输入特定字符串集合中的某个字符串
     *
     * @param dataName 数据名称( String ), 比如"性别"
     * @param validOptions 允许输入的字符串集合( 比如"男", "女" )
     *
     * @throws NullPointerException 当 dataName 或 validOptions 为 null 时
     * @throws IllegalArgumentException 当 validOptions 为空数组时
     */
    private static void printPromptMessage( String dataName, List< String> validOptions ){
        Objects.requireNonNull( dataName, "参数 dataName( 数据名称 )不能为 null" );
        Objects.requireNonNull( validOptions, "参数 validOptions( 允许输入的选项 )不能为 null" );
        if ( validOptions.isEmpty() ) throw new IllegalArgumentException( "参数 validOptions( 允许输入的选项 )不能为空数组" );
        System.out.print( "有效输入为:" );
        validOptions.forEach( str -> System.out.print( "\t" + str ) );
        System.out.print( String.format("\n请输入有效的<%s>:\t", dataName) );
    }

    /**
     * 输出提示信息, 提示用户输入特定字符串文本中的某个子串
     * <pre>
     *     只要输入的字符串( 去掉首尾空字符 )包含在有效字符串文本中即可
     * </pre>
     * @param dataName 数据名称( String ), 比如"性别"
     * @param validText 允许输入的文本( 比如"男女" )
     *
     * @throws NullPointerException 当 dataName 或 validText 为 null 时
     * @throws IllegalArgumentException 当 validText 为空字符串时
     */
    private static void printPromptMessage( String dataName, String validText ){
        Objects.requireNonNull( dataName, "参数 dataName( 数据名称 )不能为 null" );
        Objects.requireNonNull( validText, "参数 text( 文本 )不能为 null" );
        if ( validText.isEmpty() ) throw new IllegalArgumentException( "参数 validText( 允许输入的文本 )不能为空字符串" );
        System.out.println( "有效输入为文本内容的子串:\t" + validText );
        System.out.print( String.format("请输入有效的<%s>:\t", dataName) );
    }
}

