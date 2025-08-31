package com.github.existedname.healthcalculatorv3.service;

import com.github.existedname.healthcalculatorv3.app.ApplicationConfig;
import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.util.input.InputProcessor;
import com.github.existedname.healthcalculatorv3.util.input.InputReader;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * 健康计算器游戏服务类, 提供作为程序核心的游戏循环
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/25
 */
public final class GameService {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================
    private static GameService gameService = null;


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private GameService() { }


    // ==================== 公有方法 ====================
    public static GameService getInstance(){
        if ( Objects.isNull( gameService ) )
            gameService = new GameService();
        return gameService;
    }

    /**
     * 启动健康计算器的主游戏循环
     *
     * @param currentUser 当前用户
     * @param scanner 输入扫描器
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public void startGameLoop( User currentUser, Scanner scanner ) throws IOException{
        MethodParameterValidator.validateUserAndScanner( currentUser, scanner );
        // 1. 输入提示
        System.out.println( "接下来,请按照 \"数字.数字.数字\" 的格式进行输入" );
        System.out.println( "Example: 输入 2.2.1 并按下 Enter 键即可跳转到〘 计算 BMR 〙" );
        System.out.println();

        boolean isContinue = true;
        while ( isContinue ){
            try {
                // 2. 获取合法编号输入
                UIService.getInstance().printHorizontalLine(); // 分割线
                String functionId = getFunctionIdInput( scanner );
                // 3. 跳转到对应功能
                jumpToFunction( functionId, currentUser, scanner );
                // 4. 结束选项: 继续/退出 + 保存数据
                isContinue = getEndOptionInput( currentUser, scanner );
            } catch ( IllegalArgumentException e ){
                System.err.println( "\t非法参数异常: " + e.getMessage() );
                System.out.println();
            } catch ( NullPointerException e ){
                System.err.println( "\t空指针异常: " + e.getMessage() );
                System.out.println();
            } catch ( ArithmeticException e ){
                System.err.println( "\t数学运算异常: " + e.getMessage() );
                System.out.println();
            } catch ( ArrayIndexOutOfBoundsException e ){
                System.err.println( "\t数组越界异常: " + e.getMessage() );
                System.out.println();
            } catch ( FileNotFoundException e ){
                System.err.println( "\t未找到文件: " + e.getMessage() );
                System.out.println();
            } catch ( IOException e ){
                System.err.println( "\tIO 异常: " + e.getMessage() );
                System.out.println();
            }
        }
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 获取用户输入的有效功能编号
     *
     * @param scanner 扫描器
     * @return 有效的功能编号
     *
     * @throws NullPointerException 当 scanner 为 null 时
     */
    private String getFunctionIdInput( Scanner scanner ){
        MethodParameterValidator.validateScanner( scanner );
        System.out.print( "请输入你想使用的功能对应的编号 & 按下 Enter >> \t" );
        String functionId = InputReader.readTrimmedLine( scanner );
        while ( ! ApplicationConfig.isValidFunctionId( functionId ) ){
            System.out.print( "你输入的编号有误! 请输入功能清单中的有效编号:\t" );
            functionId = InputReader.readTrimmedLine( scanner );
        }
        return functionId;
    }

    /**
     * 根据功能编号跳转到对应的功能
     *
     * @param functionId 功能编号
     * @param currentUser 当前用户
     * @param scanner 输入流
     *
     * @throws NullPointerException 当 functionId 或 currentUser 或 scanner 为 null 时
     * @throws IllegalArgumentException 当 functionId 无效时
     */
    private void jumpToFunction( String functionId, User currentUser, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( currentUser, scanner );
        System.out.println( String.format(
                "\n\t跳转到功能: %s %s", functionId, ApplicationConfig.getFunctionName( functionId ) )
        );

        if ( ApplicationConfig.hasRunnableFunction( functionId ) ){
            // 2.2.1 情况Ⅰ: 编号对应的方法无参数无返回值( idToRunnableMap )
            ApplicationConfig.executeRunnableById( functionId );
        } else if ( ApplicationConfig.hasBiConsumerFunction( functionId ) ){
            // 2.2.2 情况Ⅱ: 编号对应的方法有 2 个参数无返回值( idToBiConsumerMap )
            ApplicationConfig.executeBiConsumerById( functionId, currentUser, scanner );
        } else {
            throw new IllegalArgumentException( "功能编号 " + functionId + " 无效" );
        }
    }

    /**
     * 获取用户结束选项输入( 继续/退出 )
     *
     * @param scanner
     * @return true: 继续游戏, false: 退出游戏并<B>保存用户数据到项目文件</B>
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    private boolean getEndOptionInput( User user, Scanner scanner ) throws IOException{
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        System.out.println();
        UIService.getInstance().printHashSignLine();

        boolean isContinue = false;
        System.out.println( "是否继续使用健康计算器?" );
        System.out.print( "继续请按 1 或 yes, 退出请按 0 或 no, Input >>\t" );

        while ( true ){
            String trimmedInput = InputProcessor.toTrimmedOrEmptyStr( scanner.nextLine() );
            if ( "1".equals( trimmedInput ) || "yes".equalsIgnoreCase( trimmedInput ) ){
                System.out.println();
                return ( isContinue = true );
            } else if ( "0".equals( trimmedInput ) || "no".equalsIgnoreCase( trimmedInput ) ){
                UIService.getInstance().printTildeLine(); // 波浪线分隔线
                FileService.getInstance().saveUserToFile( user );

                String operation = "正在退出健康计算器", result = "成功退出程序!";
                UIService.getInstance().printLoadingProgress( operation, result );
                System.out.println( "感谢你的体验,欢迎下次再来!" );

                return ( isContinue = false );
            } else {
                System.out.print( "输入有误, 请重新输入:\t" );
            }
        }
    }

}

