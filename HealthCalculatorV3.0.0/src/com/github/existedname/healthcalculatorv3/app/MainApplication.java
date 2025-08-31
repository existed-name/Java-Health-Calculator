package com.github.existedname.healthcalculatorv3.app;

import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.service.FileService;
import com.github.existedname.healthcalculatorv3.service.GameService;
import com.github.existedname.healthcalculatorv3.service.UIService;

import java.util.Scanner;

/**
 * 应用程序主类, 负责启动健康计算器应用
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateTime 2025/8/30
 */
public final class MainApplication {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================
    /** 当前用户, 之后可以拓展为 UserManager 集中管理用户 */
    private static User user = new User();


    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private MainApplication(){}


    // ==================== 公有方法 ====================
    /**
     * 启动应用程序入口点
     * @param args 命令行参数
     */
    public static void main( String[] args ){
        startApplication( args );
    }


    // ==================== 私有辅助方法 ====================
    /**
     * 应用程序核心执行流程<br>
     * 包含: UI 展示、启动游戏循环、数据保存
     * @param args 启动参数
     */
    private static void startApplication( String[] args ){
        try ( Scanner scanner = new Scanner( System.in ) ){
            /*      1. 前言      */
            // 1.1 展示引言
            UIService.getInstance().showCalculatorForeword( scanner );
            // 1.2 展示功能及对应编号
            UIService.getInstance().showCalculatorFunctionList( scanner );

            /*      2. 游戏循环       */
            GameService.getInstance().startGameLoop( user, scanner );

            /*      3. 将用户身体指标信息写入文档保存      */
            // 该步骤移入 GameService#getEndOptionInput 中, 在"退出"之前保存
        } catch ( RuntimeException e ){
            System.err.println( "\t捕获运行时异常: " + e.getMessage() );
            System.err.println( "\t请重新启动程序" );
        } catch ( Exception e ){
            System.err.println( "\t捕获未知异常: " + e.getMessage() );
            System.err.println( "\t请重新启动程序" );
        }
    }
}