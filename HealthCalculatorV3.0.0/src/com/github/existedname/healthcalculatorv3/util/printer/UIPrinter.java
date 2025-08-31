package com.github.existedname.healthcalculatorv3.util.printer;

import java.util.Objects;

/**
 * UI 打印工具类, 提供控制台打印相关的常量( 主要是字符 )、工具方法( 模拟打字机、打点、打印分隔线等 )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/19 9:25
 */
public final class UIPrinter {
    // ==================== 常量 ====================

    
    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private UIPrinter(){}

    // ==================== 公有方法 ====================
    /**
     * 模拟打字机, 打印字符串的每个字符时随机延迟 50-150 ms
     *
     * @param str 要打印的字符串
     *
     * @throws NullPointerException 当 str 为 null 时
     * @since 2.0.0
     */
    public static void typewriter( String str ){
        Objects.requireNonNull( str, "参数 str( 字符串 )不能为 null" );
        for ( int i = 0; i < str.length(); i++ ){
            System.out.print( str.charAt( i ) );
            // 每打印 1 个字符就暂停微小时间( 毫秒 ),营造出打字机的视觉效果
            threadSleep( PrinterConstants.TimeConstants.CharDelay.getRandomTypewriterCharDelay() );
        }
    }

    /**
     * 模拟打字机, 调用 {@link #typewriter( String ) } 打印字符串数组
     *
     * @param strings 要打印的字符串数组, 当 strings 为空数组时, 不进行打印
     *
     * @throws NullPointerException 当 str 为 null 时
     * @since 2.0.0
     */
    public static void typewriter( String[] strings ){
        Objects.requireNonNull( strings, "参数 strings( 字符串数组 )不能为 null" );
        for ( String str : strings ){
            typewriter( str );
        }
    }

    /**
     * 模拟打字机, 自定义打印字符串的每个字符时的延迟时间
     *
     * @param str 要打印的字符串
     * @param delay 延迟时间, <b>单位: 毫秒</b>
     *
     * @throws NullPointerException 当 str 为 null 时
     * @throws IllegalArgumentException 当 delay < 0 时
     * @since 2.0.0
     */
    public static void typewriter( String str, int delay ){
        Objects.requireNonNull( str, "参数 str( 字符串 )不能为 null" );
        if ( delay < 0 ) throw new IllegalArgumentException( "参数 delay( 延迟时间 )不能为负数" );
        for ( int i = 0; i < str.length(); i++ ){
            System.out.print( str.charAt( i ) );
            threadSleep( delay );
        }
    }

    /**
     * 让当前执行的线程休眠( 暂停执行 )指定的时间
     *
     * @param delay 暂停/休眠时间, <b>单位: 毫秒</b>
     * @throws IllegalArgumentException 当 delay < 0 时
     * @since 2.0.0
     */
    public static void threadSleep( int delay ){
        if ( delay < 0 ) throw new IllegalArgumentException( "参数 delay( 延迟时间 )不能为负数" );
        try {
            Thread.sleep( delay );
        } catch ( InterruptedException e ){
            // 必须捕获 InterruptedException,否则编译报错( 编译时异常 )
            Thread.currentThread().interrupt(); // 在捕获后恢复中断状态
        }
    }

    /**
     * 模拟加载时的打点操作<br>
     * 比如: <pre>正在计算 BMR...</pre>
     *
     * @param numOfDots 打出的点的数量( 为 0 时不会打点 )
     * @throws IllegalArgumentException 当 numOfDots < 0 时
     * @since 2.0.0
     */
    public static void printLoadingDots( int numOfDots ){
        if ( numOfDots < 0 ) throw new IllegalArgumentException( "参数 numOfDots( 点的数量 )不能为负数" );
        
        int executeTimes = PrinterConstants.LoadingAnimation.getRandomCycles(); // "加载"[ 2, 5 ]次
        for ( int i = 0; i < executeTimes; ++i ){
            for ( int j = 0; j < numOfDots; ++j ){
                // 打印 . 前后都进行 sleep 操作,保证看清楚每一个点的产生、删除
                threadSleep( PrinterConstants.TimeConstants.CharDelay.LOADING_DOT_DELAY );
                System.out.print( PrinterConstants.LoadingAnimation.DOT );
                threadSleep( PrinterConstants.TimeConstants.CharDelay.LOADING_DOT_DELAY );
            }

            for ( int j = 0; j < numOfDots; ++j ){
                // 打印 \b 前后都进行 sleep 操作,防止太快清理前面的点,运行效果还可
                threadSleep( PrinterConstants.TimeConstants.CharDelay.LOADING_DOT_DELAY );
                System.out.print( "\b" ); // 退格符 \b,用于将光标向左移动一格,模拟退格
                threadSleep( PrinterConstants.TimeConstants.CharDelay.LOADING_DOT_DELAY );
            }
        }
    }

    /**
     * 打印给定数量的某种字符
     *
     * @param count 打印字符的个数, <b>≤ 0 时不进行打印操作</b>
     * @param ch 要打印的字符
     * @since 2.0.0
     */
    public static void printNChars( int count, char ch ){
        for ( int i = 0; i < count; ++i ){
            System.out.print( ch );
        }
    }

    /**
     * 打印水平线( 有换行 )<br>
     * 细节由 {@link #printLine( char ) } } 实现
     *
     * @since 2.0.0
     */
    public static void printHorizontalLine(){
        printLine( PrinterConstants.Separators.HORIZONTAL_SEPARATOR_CHAR );
    }

    /**
     * 打印等号线( 有换行 )<br>
     * 细节由 {@link #printLine( char ) } } 实现
     *
     * @since 2.0.0
     */
    public static void printEqualsLine(){
        printLine( PrinterConstants.Separators.EQUALS_SEPARATOR_CHAR );
    }

    /**
     * 打印星号线( 有换行 )<br>
     * 细节由 {@link #printLine( char ) } } 实现
     *
     * @since 2.0.0
     */
    public static void printAsteriskLine(){
        printLine( PrinterConstants.Separators.ASTERISK_SEPARATOR_CHAR );
    }

    /**
     * 打印井号线( 有换行 )<br>
     * 细节由 {@link #printLine( char ) } } 实现
     *
     * @since 2.0.0
     */
    public static void printHashSignLine(){
        printLine( PrinterConstants.Separators.HASH_SEPARATOR_CHAR );
    }

    /**
     * 打印波浪线( 有换行 )<br>
     * 细节由 {@link #printLine( char ) } } 实现
     *
     * @since 2.0.0
     */
    public static void printTildeLine(){
        printLine( PrinterConstants.Separators.TILDE_SEPARATOR_CHAR );
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 打印默认长度( {@link PrinterConstants#DEFAULT_LINE_LENGTH } ) 的指定字符线( 有换行 )<p>
     * 打印操作由 {@link #printNChars( int count, char ) } 实现
     *
     * @param ch 要打印的字符
     */
    private static void printLine( char ch ){
        UIPrinter.printNChars( PrinterConstants.DEFAULT_LINE_LENGTH, ch );
        System.out.println();
    }
}
