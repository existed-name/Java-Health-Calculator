package com.github.existedname.healthcalculatorv3.service;

import com.github.existedname.healthcalculatorv3.util.convertor.TypeConvertor;
import com.github.existedname.healthcalculatorv3.util.input.InputProcessor;
import com.github.existedname.healthcalculatorv3.util.printer.PrinterConstants;
import com.github.existedname.healthcalculatorv3.util.printer.UIPrinter;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Objects;
import java.util.Scanner;

/**
 * 编译器( IDEA )控制台用户交互服务
 * <p>
 * 提供以下主要功能:
 * <pre>
 *     1. 显示计算器引言和功能清单
 *     2. 模拟加载进度动画
 *     3. 打印各种分隔线
 *     4. 按任意键以继续
 * </pre>
 * </p>
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/19 9:14
 */
public final class UIService {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================
    private static UIService uiService = null;

    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private UIService() { }

    // ==================== 公有方法 ====================
    public static UIService getInstance(){
        if( Objects.isNull( uiService ) ){
            uiService = new UIService();
        }
        return uiService;
    }

    /**
     * 展示计算器引言部分( 会询问用户是否需要 )
     *
     * @param scanner 扫描器
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public void showCalculatorForeword( Scanner scanner ){
        MethodParameterValidator.validateScanner( scanner );
        System.out.println( "\t是否阅读引言部分?" );
        promptForYesNoChoice( scanner, this::printCalculatorForeword, this::pressAnyKeyToContinue );
    }

    /**
     * 展示计算器功能清单( 功能编号及对应功能名称 )( 会询问用户是否需要 )
     *
     * @param scanner 扫描器
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public void showCalculatorFunctionList( Scanner scanner ){
        MethodParameterValidator.validateScanner( scanner );
        System.out.println( "\t是否阅读功能清单?" );
        promptForYesNoChoice( scanner, this::printCalculatorFunctionList, this::pressAnyKeyToContinue );
    }

    /**
     * 模拟加载进度
     *
     * @param operation 操作名称( 如: 正在计算 BMI )
     * @param result 操作结果( 如: BMI 计算完毕 )
     *
     * @throws NullPointerException 当 operation 或 result 为 null 时
     * @since 2.0.0
     */
    public void printLoadingProgress( String operation, String result ){
        Objects.requireNonNull( operation, "参数 operation( 操作名称 )不能为 null" );
        Objects.requireNonNull( result, "参数 result( 操作结果 )不能为 null" );

        System.out.print( "\t" + operation ); // 注意不换行,后面要跟上"加载点"
        UIPrinter.printLoadingDots( 3 ); // 正在计算 BMI...
        System.out.println();
        System.out.println( "\t" + result ); // BMI 计算完毕
        UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.PROGRESS_END_PAUSE ); // 1.2 s 延迟
    }

    /**
     * 提示用户按任意键继续<p>
     * 由于实际上必须等待用户按下 Enter 键才能提交输入, 所以其实是按 Enter 键继续
     *
     * @since 2.0.0
     */
    public void pressAnyKeyToContinue(){
        System.out.print( "\t请按 Enter 键以继续..." );
        Scanner scanner = new Scanner( System.in );
        scanner.nextLine(); // 读取一行
        System.out.print( "\n\n" );
    }

    /**
     * 打印水平线( 有换行 )
     */
    public void printHorizontalLine(){
        UIPrinter.printHorizontalLine();
    }

    /**
     * 打印等号线( 有换行 )
     */
    public void printEqualsLine(){
        UIPrinter.printEqualsLine();
    }

    /**
     * 打印星号线( 有换行 )
     */
    public void printAsteriskLine(){
        UIPrinter.printAsteriskLine();
    }

    /**
     * 打印井号线( 有换行 )
     */
    public void printHashSignLine(){
        UIPrinter.printHashSignLine();
    }

    /**
     * 打印波浪线( 有换行 )
     */
    public void printTildeLine(){
        UIPrinter.printTildeLine();
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 打印计算器引言
     *
     * @since 2.0.0
     */
    private void printCalculatorForeword(){
        // ※※※⁜⌂→→→▶▶▶▷▷▷▲△►▻◆◯■■⇶⇶⇶⇢⇰⇉¬⌉⌋⌈⌉⌋⌊«»⟦⟧⟭⟬‖▣▦◀▶◀◁▷▱△◊◇◆◈◢◤◥◣■↩↩↩↲↲↲↵↵↵⇦⇚▧▤▦▣▩▥⇲⇱
        printAsteriskLine(); // 星号分隔线
        String[] foreword = new String[] {
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
        UIPrinter.typewriter( foreword );
        printAsteriskLine(); // 星号分隔线
        System.out.print( "\n\n" );
    }

    /**
     * 打印计算器功能清单( 类似目录, 功能编号--功能名称 )
     *
     * @since 2.0.0
     */
    private void printCalculatorFunctionList(){
        printEqualsLine(); // 等号分隔线
        StringBuilder functionList = new StringBuilder();
        functionList.append( "【*{*[*(*<——_«_健康计算器_»_——>*)*]*}*】\n" )
                .append( "  ◤‾请过目———功能清单‾◥  \n" )
                .append( "< Ⅰ >健康百科: 详解各种健康指标[ 涨知识啦ヾ(≧▽≦*) ]\n" )
                .append( "\t1.1 详解体态评估类健康指标\n" )
                .append( "1.1.1 详解 BMI\t" ).append( "1.1.2 详解 WC\t" ).append( "1.1.3 详解 WHR\t" ).append( "1.1.4 详解 BFR\t" ).append( "1.1.5 详解 BRI\n" )
                .append( "\t1.2 详解能量代谢类健康指标\n" )
                .append( "1.2.1 详解 BMR\t" ).append( "1.2.2 详解 TDEE\n" )
                .append( "\t1.3 详解生理特征类健康指标\n" )
                .append( "1.3.1 详解 BSA\n\n" );
        functionList.append( "< Ⅱ >招牌功能: 计算你的各种健康指标[ 需要测量身体数据哦(⊙o⊙) ]\n" )
                .append( "\t2.1 计算体态评估类健康指标\n" )
                .append( "2.1.1 计算 BMI\t" ).append( "2.1.2 计算 WHR\t" ).append( "2.1.3 计算 BFR\t" ).append( "2.1.4 计算 BRI\n" )
                .append( "\t2.2 计算能量代谢类健康指标\n" )
                .append( "2.2.1 计算 BMR\t" ).append( "2.2.2 计算 TDEE\n" )
                .append( "\t2.3 计算生理特征类健康指标\n" )
                .append( "2.3.1 计算 BSA\n" )
                .append( "注意: 计算结果存在误差,请以专业测量结果为准!!!\n\n" );
        functionList.append( "< Ⅲ >科学评估: 分析你的各种健康指标[ 🎇前方高能😎 ]\n" )
                .append( "\t3.1 分析体态评估类健康指标\n" )
                .append( "3.1.1 分析 BMI\t" ).append( "3.1.2 分析 WC\t" ).append( "3.1.3 分析 WHR\t" ).append( "3.1.4 分析 BFR\t" ).append( "3.1.5 分析 BRI\n" )
                .append( "\t3.2 分析能量代谢类健康指标\n" )
                .append( "3.2.1 分析 BMR\t" ).append( "3.2.2 分析 TDEE\n" )
                .append( "\t3.3 分析生理特征类健康指标\n" )
                .append( "3.3.1 分析 BSA\n\n" );
        functionList.append( "< Ⅳ >数据对比: 看看不同公式算出的健康指标相差几何😶🤨🤔\n" )
                .append( "\t4.1 对比不同公式得到的体态评估类健康指标\n" )
                .append( "4.1.1 对比不同公式得到的 BFR\n" )
                .append( "\t4.2 对比不同公式得到的能量代谢类健康指标\n" )
                .append( "4.2.1 对比不同公式得到的 BMR\n" )
                .append( "\t4.3 对比不同公式得到的生理特征类健康指标\n" )
                .append( "4.3.1 对比不同公式得到的 BSA\n\n" );
        functionList.append( "< Ⅴ >健康对标: 查看健康指标理想值[ ┏(゜ω゜)=👉仅供参考~ ]\n" )
                .append( "\t5.1 查看体态评估类健康指标理想值/范围\n" )
                .append( "5.1.1 查看体重理想值/范围\t" ).append( "5.1.2 查看 BMI 理想值/范围\n" ).append( "5.1.3 查看 WC 理想值/范围\t" ).append( "5.1.4 查看 WHR 理想值/范围\n" ).append( "5.1.5 查看 BFR 理想值/范围\t" ).append( "5.1.6 查看 BRI 理想值/范围\n" )
                .append( "\t5.2 查看能量代谢类健康指标理想值/范围\n" )
                .append( "5.2.1 查看 BMR 理想值/范围\t" ).append( "5.2.2 查看 TDEE 理想值/范围\n" )
                .append( "\t5.3 查看生理特征类健康指标理想值/范围\n" )
                .append( "5.3.1 查看 BSA 理想值/范围\n\n" );
        functionList.append( "< Ⅵ >更多功能,敬请期待: to be updated...\n" )
                .append( "  ◣_Function———List_◢  \n" );
        UIPrinter.typewriter( TypeConvertor.toString( functionList ), PrinterConstants.TimeConstants.CharDelay.FUNCTION_LIST_CHAR_DELAY );
        printEqualsLine(); // 等号分隔线
        UIPrinter.threadSleep( PrinterConstants.TimeConstants.DisplayPause.FUNCTION_LIST_DISPLAY_PAUSE );
        System.out.print( "\n\n" );
    }

    /**
     * 提示用户进行"是否"选择
     *
     * @param scanner 扫描器对象
     * @param actionsOnYes 可变参数: 用户输入为 "是" 后执行的行为<p>
     *                     并且为 Runnable 类型( 无参数, 无返回值 )<br>
     *                     允许不含元素( 不进行任何行为 )
     * </p>
     *
     * @throws NullPointerException 当 scanner 或 actionsOnYes 为 null 时
     * @throws IllegalArgumentException 当 actionsOnYes 中存在为 null 的元素时
     */
    private void promptForYesNoChoice( Scanner scanner, Runnable... actionsOnYes ){
        MethodParameterValidator.validateScanner( scanner );
        Objects.requireNonNull( actionsOnYes, "参数 actionsOnYes( 用户\"确认\"后采取的行为 )不能为 null" );
        System.out.print( "是请输入 1 或 yes, 否则输入 0 或 no, Input >>\t" );
        while ( true ){
            String trimmedInput = InputProcessor.toTrimmedOrEmptyStr( scanner.nextLine() );
            if ( "0".equals( trimmedInput ) || "no".equalsIgnoreCase( trimmedInput ) ){
                return;
            } else if ( "1".equals( trimmedInput ) || "yes".equalsIgnoreCase( trimmedInput ) ){
                for ( Runnable action: actionsOnYes ){
                    if ( ! Objects.isNull( action ) ){
                        action.run();
                    } else {
                        throw new IllegalArgumentException( "可变参数 actionsOnYes( 用户\"确认\"后采取的行为 )中存在为 null 的元素" );
                    }
                }
                return;
            } else {
                System.out.print( "输入有误, 请重新输入:\t" );
            }
        }
    }
}
