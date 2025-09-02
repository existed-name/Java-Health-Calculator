package com.github.existedname.healthcalculatorv2.app;

import com.github.existedname.healthcalculatorv2.model.User;
import com.github.existedname.healthcalculatorv2.service.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class MainApplication {
    /*      1. 成员变量     */
    // 比较占位置, 所以放最下面了



    /*      2. 公有静态方法       */
    // 2.1 main 方法
    public static void main( String[] args ){
        // main 仅作为程序入口, 不包含具体业务逻辑
        startApplication( args );
    }



    /*      3. 私有静态方法       */
    // 3.1 程序启动入口
    private static void startApplication( String[] args ){
        // 提前创建对象
        Scanner scanner = new Scanner( System.in );
        User user = new User(); // 注: 不用提前输入身体信息, 在调用方法之后再输入

        try {
            // 主逻辑
            /*      1. 前言      */
            // 1.1 引言
            System.out.println( "\t是否阅读引言部分?" );
            System.out.print( "是请按 1,否请按 0,Input >>\t" );
            if ( ! scanner.nextLine().equals( "0" ) ){
                Service.printCalculatorForeword();
                Service.pressAnyKeyToContinue();
            }

            // 1.2 功能介绍
            System.out.println( "\t是否阅读功能介绍?" );
            System.out.print( "是请按 1,否请按 0,Input >>\t" );
            if ( ! scanner.nextLine().equals( "0" ) ){
                Service.printCalculatorFunctionList();
                Service.pressAnyKeyToContinue();
            }

            // 1.3 输入提示
            System.out.println( "接下来,请按照 \"数字.数字.数字\" 的格式进行输入" );
            System.out.println( "Example: 输入 2.2.1 并按下 Enter 键即可跳转到〘 计算 BMR 〙" );
            System.out.println();

            /*      2. 循环输入编号        */
            boolean isContinue = true;
            while ( isContinue ){
                // 2.1 验证输入的编号是否正确
                Service.printHorizontalLine(); // 分割线
                System.out.print( "请输入你想使用的功能对应的编号 & 按下 Enter >> \t" );
                String functionIdentifierStr = scanner.nextLine();
                while ( ! idToFunctionNameMap.containsKey( functionIdentifierStr ) ){
                    /*
                     *      这里将所有合法编号作为键存在哈希表中,只需要检查哈希表是否包含用户输入
                     *  的编号,即可判断输入是否合法
                     */
                    System.out.print( "你输入的格式有误( 数字.数字.数字 ),请重新输入:\t" );
                    functionIdentifierStr = scanner.nextLine();
                }

                // 2.2 跳转到对应功能
                System.out.println( "\n\t" + functionIdentifierStr + " " +
                                    idToFunctionNameMap.get( functionIdentifierStr ) );
                    // 2.2.1 情况Ⅰ: 编号对应的方法无参数无返回值( idToRunnableMap )
                if ( idToRunnableMap.containsKey( functionIdentifierStr ) ){
                    try {
                        // 访问方式① 代码更简洁,但需要确保 map.get( key ) 不会返回 null
                        idToRunnableMap.get( functionIdentifierStr ).run();
                    } catch ( NullPointerException e ){
                        System.out.println( "编号 \"" + functionIdentifierStr + "\" " + "对应的方法为空!" );
                    } finally { }
                }
                    // 2.2.2 情况Ⅱ: 编号对应的方法有 1 个参数无返回值( idToConsumerMap )
                else {
                    // 访问方式② 显式检查 method 是否为null,代码更清晰、易于维护,并且更容易扩展
                    Consumer< User > method = idToConsumerMap.get( functionIdentifierStr );
                    if ( method != null ){
                        method.accept( user );
                    } else {
                        System.out.println( "编号 \"" + functionIdentifierStr + "\" " + "对应的方法为空!" );
                    }
                }

                // 2.3 结束选项: 继续/退出
                System.out.print( "\n" );
                Service.printHashSignLine();
                System.out.println( "是否继续使用健康计算器?" );
                System.out.print( "继续请按 1,退出请按 0,Input >>\t" );
                if ( scanner.nextLine().equals( "0" ) ){
                    isContinue = false;
                    Service.printTildeLine();
                    String operation = "正在退出健康计算器", result = "成功退出程序!";
                    Service.printLoadingProgress( operation, result );
                    System.out.println( "感谢你的体验,欢迎下次再来!" );
                } else {
                    System.out.println();
                }
            }
        } catch ( Exception e ){
            // 统一异常处理
            handleException( e ); // 自行编写方法
        } finally {
            // 资源释放
            scanner = null; // 手动置空对象,释放对象引用
            user = null;
            idToFunctionNameMap.clear();
            idToRunnableMap.clear();
            idToConsumerMap.clear();
        }
    }

    // 3.2 处理异常
    private static void handleException( Exception e ){
        System.out.println( "Exception Warning in startApplication method:\n\t" + e );
    }



    /*      1. 成员变量     */
    /*  1.1 私有成员变量  */
    // 1.1.1 编号-功能对应表: 容纳所有编号及对应功能名称
    private static Map< String, String > idToFunctionNameMap = new HashMap< String, String > ();
    static {
        // 〖〗〘〙〚〛〔〕⁅∃〈〉『』《》＜＞ ⫷⫸ ⫹⫺ ⫕⫖ ⪡⪢ ⋘⋙ ⋐⋑ ⊏⊐ ≪≫ ∈∋ ≪≫
        // put( "1", "身体指数详解" );
            // put( "1.1", "详解体态评估类身体指数" );
        idToFunctionNameMap.put( "1.1.1", "〖 详解 BMI 〗" );
        idToFunctionNameMap.put( "1.1.2", "〖 详解 WC 〗" );
        idToFunctionNameMap.put( "1.1.3", "〖 详解 WHR 〗" );
        idToFunctionNameMap.put( "1.1.4", "〖 详解 BFR 〗" );
        idToFunctionNameMap.put( "1.1.5", "〖 详解 BRI 〗" );
            // put( "1.2", "详解能量代谢类身体指数" );
        idToFunctionNameMap.put( "1.2.1", "〖 详解 BMR 〗" );
        idToFunctionNameMap.put( "1.2.2", "〖 详解 TDEE 〗" );
            // put( "1.3", "详解生理特征类身体指数" );
        idToFunctionNameMap.put( "1.3.1", "〖 详解 BSA 〗" );

        // put( "2", "身体指数计算" );
            // put( "2.1", "计算体态评估类身体指数" );
        idToFunctionNameMap.put( "2.1.1", "〘 计算 BMI 〙" );
        idToFunctionNameMap.put( "2.1.2", "〘 计算 WHR 〙" );
        idToFunctionNameMap.put( "2.1.3", "〘 计算 BFR 〙" );
        idToFunctionNameMap.put( "2.1.4", "〘 计算 BRI 〙" );
            // put( "2.2", "计算能量代谢类身体指数" );
        idToFunctionNameMap.put( "2.2.1", "〘 计算 BMR 〙" );
        idToFunctionNameMap.put( "2.2.2", "〘 计算 TDEE 〙" );
            // put( "2.3", "计算生理特征类身体指数" );
        idToFunctionNameMap.put( "2.3.1", "〘 计算 BSA 〙" );

        // put( "3", "身体指数分析" );
            // put( "3.1", "分析体态评估类身体指数" );
        idToFunctionNameMap.put( "3.1.1", "⫷ 分析 BMI ⫸" );
        idToFunctionNameMap.put( "3.1.2", "⫷ 分析 WC ⫸" );
        idToFunctionNameMap.put( "3.1.3", "⫷ 分析 WHR ⫸" );
        idToFunctionNameMap.put( "3.1.4", "⫷ 分析 BFR ⫸" );
        idToFunctionNameMap.put( "3.1.5", "⫷ 分析 BRI ⫸" );
            // put( "3.2", "分析能量代谢类身体指数" );
        idToFunctionNameMap.put( "3.2.1", "⫷ 分析 BMR ⫸" );
        idToFunctionNameMap.put( "3.2.2", "⫷ 分析 TDEE ⫸" );
            // put( "3.3", "分析生理特征类身体指数" );
        idToFunctionNameMap.put( "3.3.1", "⫷ 分析 BSA ⫸" );

        // put( "4", "对比不同公式得到的身体指数值" );
            // put( "4.1", "对比不同公式得到的体态评估类身体指数值" );
        idToFunctionNameMap.put( "4.1.1", "⪡ 对比不同公式得到的 BFR ⪢" );
            // put( "4.2", "对比不同公式得到的能量代谢类身体指数值" );
        idToFunctionNameMap.put( "4.2.1", "⪡ 对比不同公式得到的 BMR ⪢" );
            // put( "4.3", "对比不同公式得到的生理特征类身体指数值" );
        idToFunctionNameMap.put( "4.3.1", "⪡ 对比不同公式得到的 BSA ⪢" );

        // put( "5", "查看身体指数理想值" );
            // put( "5.1", "查看体态评估类身体指数理想值" );
        idToFunctionNameMap.put( "5.1.1", "⋘ 查看 体重 理想值 ⋙" );
        idToFunctionNameMap.put( "5.1.2", "⋘ 查看 BMI 理想值 ⋙" );
        idToFunctionNameMap.put( "5.1.3", "⋘ 查看 WC 理想值 ⋙" );
        idToFunctionNameMap.put( "5.1.4", "⋘ 查看 WHR 理想值 ⋙" );
        idToFunctionNameMap.put( "5.1.5", "⋘ 查看 BFR 理想值 ⋙" );
        idToFunctionNameMap.put( "5.1.6", "⋘ 查看 BRI 理想值 ⋙" );
            // put( "5.2", "查看能量代谢类身体指数理想值" );
        idToFunctionNameMap.put( "5.2.1", "⋘ 查看 BMR 理想值 ⋙" );
        idToFunctionNameMap.put( "5.2.2", "⋘ 查看 TDEE 理想值 ⋙" );
            // put( "5.3", "查看生理特征类身体指数理想值" );
        idToFunctionNameMap.put( "5.3.1", "⋘ 查看 BSA 理想值 ⋙" );

        // put( "6", "更新中,未完待续..." );
    }

    // 1.1.2 字符串-方法( 无参数, 无返回值 )对应表
    private static Map< String, Runnable > idToRunnableMap = new HashMap< String, Runnable > ();
    static {
        // 1. 创建对象实例
        Service service = new Service();

        // 2.初始化 map: key 为编号,value 为无参数无返回值方法
        // put( "1", "身体指数详解" );
            // put( "1.1", "详解体态评估类身体指数" );
        idToRunnableMap.put( "1.1.1", service::introduceBMI );
        idToRunnableMap.put( "1.1.2", service::introduceWaistCircum );
        idToRunnableMap.put( "1.1.3", service::introduceWHR );
        idToRunnableMap.put( "1.1.4", service::introduceBFR );
        idToRunnableMap.put( "1.1.5", service::introduceBRI );
        // put( "1.2", "详解能量代谢类身体指数" );
        idToRunnableMap.put( "1.2.1", service::introduceBMR );
        idToRunnableMap.put( "1.2.2", service::introduceTDEE );
        // put( "1.3", "详解生理特征类身体指数" );
        idToRunnableMap.put( "1.3.1", service::introduceBSA );

        // 由于功能 2.1.1 - 5.1.1 涉及参数,单独拿出来成立 idToConsumerMap
        // put( "5", "查看身体指数理想值" );
            // put( "5.1", "查看体态评估类身体指数理想值" );
        idToRunnableMap.put( "5.1.2", service::checkIdealBMI );
        idToRunnableMap.put( "5.1.3", service::checkIdealWaistCircum );
        idToRunnableMap.put( "5.1.4", service::checkIdealWHR );
        idToRunnableMap.put( "5.1.5", service::checkIdealBFR );
        idToRunnableMap.put( "5.1.6", service::checkIdealBRI );
            // put( "5.2", "查看能量代谢类身体指数理想值" );
        idToRunnableMap.put( "5.2.1", service::checkIdealBMR );
        idToRunnableMap.put( "5.2.2", service::checkIdealTDEE );
            // put( "5.3", "查看生理特征类身体指数理想值" );
        idToRunnableMap.put( "5.3.1", service::checkIdealBSA );

        // put( "6", "更新中,未完待续..." );
        /*
         *      static 块中的成员的生命周期 = 类的生命周期 => 在类的运行结束后,
         *  static 块中的变量、对象才会消失并被垃圾回收器回收
         *      那么可以考虑在 static 块结束时置空对象,提前释放资源
         */
        service = null;
    }

    // 1.1.3 字符串-方法( 1 参数,无返回值 )对应表
    private static Map< String, Consumer< User > > idToConsumerMap = new HashMap< String, Consumer< User > > ();
    static {
        // 1. 创建对象实例
        Service service = new Service();

        // 2. 填充 Map
        //  Key 为字符串编号, Value 为含有 1 个参数( User 类对象 )无返回值的方法
        // put( "2", "身体指数计算" );
            // put( "2.1", "计算体态评估类身体指数" );
        idToConsumerMap.put( "2.1.1", service::calculateBMI );
        idToConsumerMap.put( "2.1.2", service::calculateWHR );
        idToConsumerMap.put( "2.1.3", service::calculateBFR );
        idToConsumerMap.put( "2.1.4", service::calculateBRI );
            // put( "2.2", "计算能量代谢类身体指数" );
        idToConsumerMap.put( "2.2.1", service::calculateBMR );
        idToConsumerMap.put( "2.2.2", service::calculateTDEE );
            // put( "2.3", "计算生理特征类身体指数" );
        idToConsumerMap.put( "2.3.1", service::calculateBSA );

        // put( "3", "身体指数分析" );
            // put( "3.1", "分析体态评估类身体指数" );
        idToConsumerMap.put( "3.1.1", service::analyzeBMI );
        idToConsumerMap.put( "3.1.2", service::analyzeWaistCircum );
        idToConsumerMap.put( "3.1.3", service::analyzeWHR );
        idToConsumerMap.put( "3.1.4", service::analyzeBFR );
        idToConsumerMap.put( "3.1.5", service::analyzeBRI );
            // put( "3.2", "分析能量代谢类身体指数" );
        idToConsumerMap.put( "3.2.1", service::analyzeBMR );
        idToConsumerMap.put( "3.2.2", service::analyzeTDEE );
            // put( "3.3", "分析生理特征类身体指数" );
        idToConsumerMap.put( "3.3.1", service::analyzeBSA );

        // put( "4", "对比不同公式得到的身体指数值" );
            // put( "4.1", "对比不同公式得到的体态评估类身体指数值" );
        idToConsumerMap.put( "4.1.1", service::cmpBFRofVariousEquations );
            // put( "4.2", "对比不同公式得到的能量代谢类身体指数值" );
        idToConsumerMap.put( "4.2.1", service::cmpBMRofVariousEquations );
            // put( "4.3", "对比不同公式得到的生理特征类身体指数值" );
        idToConsumerMap.put( "4.3.1", service::cmpBSAofVariousEquations );

        // put( "5", "查看身体指数理想值" );
            // put( "5.1", "查看体态评估类身体指数理想值" );
        idToConsumerMap.put( "5.1.1", service::checkIdealWeight );

        // 3. 置空对象
        service = null;
    }
}

