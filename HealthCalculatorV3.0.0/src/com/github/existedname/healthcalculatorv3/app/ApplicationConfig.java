package com.github.existedname.healthcalculatorv3.app;

import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.service.analysis.AnalysisService;
import com.github.existedname.healthcalculatorv3.service.calculation.CalculationService;
import com.github.existedname.healthcalculatorv3.service.comparison.ComparisonService;
import com.github.existedname.healthcalculatorv3.service.introduction.IntroductionService;
import com.github.existedname.healthcalculatorv3.service.reference.ReferenceService;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * 应用程序配置类, 负责管理系统中所有功能的映射关系<br>
 * 通过此类可以查找和执行指定 ID 对应的功能方法
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/25
 */
public final class ApplicationConfig {
    // ==================== 常量 ====================
    /**
     *      功能编号-功能名称对应表: 容纳所有编号及对应功能名称
     *      将所有合法编号作为键存进哈希表中,只需要检查哈希表是否包含
     *  用户输入的编号,即可判断输入是否合法
     */
    private static final Map< String, String > ID_TO_FUNCTION_NAME_MAP = Map.ofEntries(
            // 〖〗〘〙〚〛〔〕⁅∃〈〉『』《》＜＞ ⫷⫸ ⫹⫺ ⫕⫖ ⪡⪢ ⋘⋙ ⋐⋑ ⊏⊐ ≪≫ ∈∋ ≪≫
            // put( "1", "健康指标详解" );
            // put( "1.1", "详解体态评估类健康指标" );
            Map.entry( "1.1.1", "〖 详解 BMI 〗" ),
            Map.entry( "1.1.2", "〖 详解 WC 〗" ),
            Map.entry( "1.1.3", "〖 详解 WHR 〗" ),
            Map.entry( "1.1.4", "〖 详解 BFR 〗" ),
            Map.entry( "1.1.5", "〖 详解 BRI 〗" ),
            // put( "1.2", "详解能量代谢类健康指标" );
            Map.entry( "1.2.1", "〖 详解 BMR 〗" ),
            Map.entry( "1.2.2", "〖 详解 TDEE 〗" ),
            // put( "1.3", "计算生理特征类健康指标" ),
            Map.entry( "1.3.1", "〖 详解 BSA 〗" ),

            // put( "2", "计算健康指标" );
            // put( "2.1", "计算体态评估类健康指标" );
            Map.entry( "2.1.1", "〘 计算 BMI 〙" ),
            Map.entry( "2.1.2", "〘 计算 WHR 〙" ),
            Map.entry( "2.1.3", "〘 计算 BFR 〙" ),
            Map.entry( "2.1.4", "〘 计算 BRI 〙" ),
            // put( "2.2", "计算能量代谢类健康指标" );
            Map.entry( "2.2.1", "〘 计算 BMR 〙" ),
            Map.entry( "2.2.2", "〘 计算 TDEE 〙" ),
            // put( "2.3", "计算生理特征类健康指标" );
            Map.entry( "2.3.1", "〘 计算 BSA 〙" ),

            // put( "3", "健康指标分析" );
            // put( "3.1", "分析体态评估类健康指标" );
            Map.entry( "3.1.1", "⫷ 分析 BMI ⫸" ),
            Map.entry( "3.1.2", "⫷ 分析 WC ⫸" ),
            Map.entry( "3.1.3", "⫷ 分析 WHR ⫸" ),
            Map.entry( "3.1.4", "⫷ 分析 BFR ⫸" ),
            Map.entry( "3.1.5", "⫷ 分析 BRI ⫸" ),
            // put( "3.2", "分析能量代谢类健康指标" );
            Map.entry( "3.2.1", "⫷ 分析 BMR ⫸" ),
            Map.entry( "3.2.2", "⫷ 分析 TDEE ⫸" ),
            // put( "3.3", "分析生理特征类健康指标" );
            Map.entry( "3.3.1", "⫷ 分析 BSA ⫸" ),

            // put( "4", "对比不同公式得到的健康指标值" );
            // put( "4.1", "对比不同公式得到的体态评估类健康指标值" );
            Map.entry( "4.1.1", "⫷ 对比不同公式得到的 BFR ⫸" ),
            // put( "4.2", "对比不同公式得到的能量代谢类健康指标值" );
            Map.entry( "4.2.1", "⫷ 对比不同公式得到的 BMR ⫸" ),
            // put( "4.3", "对比不同公式得到的生理特征类健康指标值" );
            Map.entry( "4.3.1", "⪡ 对比不同公式得到的 BSA ⪢" ),

            // put( "5", "查看健康指标理想值" );
            // put( "5.1", "查看体态评估类健康指标理想值" );
            Map.entry( "5.1.1", "⋘ 查看 体重 理想值 ⋙" ),
            Map.entry( "5.1.2", "⋘ 查看 BMI 理想值 ⋙" ),
            Map.entry( "5.1.3", "⋘ 查看 WC 理想值 ⋙" ),
            Map.entry( "5.1.4", "⋘ 查看 WHR 理想值 ⋙" ),
            Map.entry( "5.1.5", "⋘ 查看 BFR 理想值 ⋙" ),
            Map.entry( "5.1.6", "⋘ 查看 BRI 理想值 ⋙" ),
            // put( "5.2", "查看能量代谢类健康指标理想值" );
            Map.entry( "5.2.1", "⋘ 查看 BMR 理想值 ⋙" ),
            Map.entry( "5.2.2", "⋘ 查看 TDEE 理想值 ⋙" ),
            // put( "5.3", "查看生理特征类健康指标理想值" );
            Map.entry( "5.3.1", "⋘ 查看 BSA 理想值 ⋙" )

            // put( "6", "更新中,未完待续..." );
    );

    /** 功能编号-Runnable 类型的功能( 无参数,无返回值 )对应表 */
    private static final Map< String, Runnable > ID_TO_RUNNABLE_MAP = Map.ofEntries(
            // 初始化 map: key 为编号,value 为无参数无返回值方法
            // put( "1", "健康指标详解" );
            // put( "1.1", "详解体态评估类健康指标" );
            Map.entry( "1.1.1", IntroductionService.getInstance()::introduceBMI ),
            Map.entry( "1.1.2", IntroductionService.getInstance()::introduceWaistCircumference ),
            Map.entry( "1.1.3", IntroductionService.getInstance()::introduceWHR ),
            Map.entry( "1.1.4", IntroductionService.getInstance()::introduceBFR ),
            Map.entry( "1.1.5", IntroductionService.getInstance()::introduceBRI ),
            // put( "1.2", "详解能量代谢类健康指标" );
            Map.entry( "1.2.1", IntroductionService.getInstance()::introduceBMR ),
            Map.entry( "1.2.2", IntroductionService.getInstance()::introduceTDEE ),
            // put( "1.3", "详解生理特征类健康指标" );
            Map.entry( "1.3.1", IntroductionService.getInstance()::introduceBSA ),

            // 由于功能 2.1.1 - 5.1.1 无返回值、含有 2 个参数,单独拿出来成立 idToBiConsumerMap
            // put( "5", "查看健康指标理想值" );
            // put( "5.1", "查看体态评估类健康指标理想值" );
            Map.entry( "5.1.2", ReferenceService.getInstance()::checkIdealBMI ),
            Map.entry( "5.1.3", ReferenceService.getInstance()::checkIdealWaistCircumference ),
            Map.entry( "5.1.4", ReferenceService.getInstance()::checkIdealWHR ),
            Map.entry( "5.1.5", ReferenceService.getInstance()::checkIdealBFR ),
            Map.entry( "5.1.6", ReferenceService.getInstance()::checkIdealBRI ),
            // put( "5.2", "查看能量代谢类健康指标理想值" );
            Map.entry( "5.2.1", ReferenceService.getInstance()::checkIdealBMR ),
            Map.entry( "5.2.2", ReferenceService.getInstance()::checkIdealTDEE ),
            // put( "5.3", "查看生理特征类健康指标理想值" );
            Map.entry( "5.3.1", ReferenceService.getInstance()::checkIdealBSA )

            // put( "6", "更新中,未完待续..." );
    );

    /** 功能编号-BiConsumer 型的功能( 2 参数,无返回值 )对应表 */
    private static final Map< String, BiConsumer< User, Scanner > > ID_TO_BICONSUMER_MAP = Map.ofEntries(
            // put( "2", "健康指标计算" );
            // put( "2.1", "计算体态评估类健康指标" );
            Map.entry( "2.1.1", CalculationService.getInstance()::showBMICalculation ),
            Map.entry( "2.1.2", CalculationService.getInstance()::showWHRCalculation ),
            Map.entry( "2.1.3", CalculationService.getInstance()::showBFRCalculation ),
            Map.entry( "2.1.4", CalculationService.getInstance()::showBRICalculation ),
            // put( "2.2", "计算能量代谢类健康指标" );
            Map.entry( "2.2.1", CalculationService.getInstance()::showBMRCalculation ),
            Map.entry( "2.2.2", CalculationService.getInstance()::showTDEECalculation ),
            // put( "2.3", "计算生理特征类健康指标" );
            Map.entry( "2.3.1", CalculationService.getInstance()::showBSACalculation ),

            // put( "3", "健康指标分析" );
            // put( "3.1", "分析体态评估类健康指标" );
            Map.entry( "3.1.1", AnalysisService.getInstance()::analyzeBMI ),
            Map.entry( "3.1.2", AnalysisService.getInstance()::analyzeWaistCircumference ),
            Map.entry( "3.1.3", AnalysisService.getInstance()::analyzeWHR ),
            Map.entry( "3.1.4", AnalysisService.getInstance()::analyzeBFR ),
            Map.entry( "3.1.5", AnalysisService.getInstance()::analyzeBRI ),
            // put( "3.2", "分析能量代谢类健康指标" );
            Map.entry( "3.2.1", AnalysisService.getInstance()::analyzeBMR ),
            Map.entry( "3.2.2", AnalysisService.getInstance()::analyzeTDEE ),
            // put( "3.3", "分析生理特征类健康指标" );
            Map.entry( "3.3.1", AnalysisService.getInstance()::analyzeBSA ),

            // put( "4", "对比不同公式得到的健康指标值" );
            // put( "4.1", "对比不同公式得到的体态评估类健康指标值" );
            Map.entry( "4.1.1", ComparisonService.getInstance()::compareVariousEquationsOfBFR ),
            // put( "4.2", "对比不同公式得到的能量代谢类健康指标值" );
            Map.entry( "4.2.1", ComparisonService.getInstance()::compareVariousEquationsOfBMR ),
            // put( "4.3", "对比不同公式得到的生理特征类健康指标值" );
            Map.entry( "4.3.1", ComparisonService.getInstance()::compareVariousEquationsOfBSA ),

            // put( "5", "查看健康指标理想值" );
            // put( "5.1", "查看体态评估类健康指标理想值" );
            Map.entry( "5.1.1", ReferenceService.getInstance()::checkIdealWeight )
    );


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private ApplicationConfig() { }

    // ==================== 公有方法 ====================
    /**
     * 检查功能编号是否合法<br>
     * 对于 null 参数, 返回 false
     *
     * @param functionId 功能编号
     * @return true: 合法, false: 非法
     */
    public static boolean isValidFunctionId( String functionId ){
        return ID_TO_FUNCTION_NAME_MAP.containsKey( functionId );
    }

    /**
     * 检查功能编号是否对应 Runnable 类型功能方法
     * @param functionId 功能编号
     * @return true: 存在 Runnable 功能与该编号对应, false: 不存在 Runnable 功能与该编号对应
     */
    public static boolean hasRunnableFunction( String functionId ){
        return ID_TO_RUNNABLE_MAP.containsKey( functionId );
    }

    /**
     * 检查功能编号是否对应 BiConsumer 类型功能方法
     * @param functionId 功能编号
     * @return true: 存在 BiConsumer 功能与该编号对应, false: 不存在 BiConsumer 功能与该编号对应
     */
    public static boolean hasBiConsumerFunction( String functionId ){
        return ID_TO_BICONSUMER_MAP.containsKey( functionId );
    }

    /**
     * 获取功能编号对应的功能名称
     * @param functionId 功能编号
     * @return 编号存在则返回功能名称, 否则返回 null
     */
    public static String getFunctionName( String functionId ){
        return ID_TO_FUNCTION_NAME_MAP.get( functionId );
    }

    /**
     * 获取功能编号对应的 Runnable 类型功能方法
     * @param functionId 功能编号
     * @return 编号存在则返回 Runnable 类型功能方法, 否则返回 null
     */
    public static Runnable getRunnableById( String functionId ){
        return ID_TO_RUNNABLE_MAP.get( functionId );
    }

    /**
     * 获取功能编号对应的 BiConsumer 类型功能方法
     * @param functionId 功能编号
     * @return 编号存在则返回 BiConsumer 类型功能方法, 否则返回 null
     */
    public static BiConsumer< User, Scanner > getBiConsumerById( String functionId ){
        return ID_TO_BICONSUMER_MAP.get( functionId );
    }

    /**
     * 根据功能编号执行对应的 Runnable 类型功能方法
     * @param functionId 功能编号
     * @throws NullPointerException 当功能编号为 null 时
     * @throws IllegalArgumentException 当功能编号对应的功能方法不存在时
     */
    public static void executeRunnableById( String functionId ){
        Objects.requireNonNull( functionId, "参数 functionId( 功能编号 )不能为 null" );

        Runnable runnable = getRunnableById( functionId );
        if ( Objects.isNull( runnable ) ){
            throw new IllegalArgumentException( String.format( "编号 \"%s\" 对应的方法不存在", functionId ) );
        }

        runnable.run();
    }

    /**
     * 执行指定编号的 BiConsumer 方法
     *
     * @param functionId 功能编号
     * @param user 当前用户
     * @param scanner 输入流
     *
     * @throws NullPointerException 当功能编号为 null 时
     * @throws NullPointerException 当用户对象为 null 时
     * @throws NullPointerException 当输入流为 null 时
     * @throws IllegalArgumentException 当功能编号对应的功能方法不存在时
     */
    public static void executeBiConsumerById( String functionId, User user, Scanner scanner ){
        Objects.requireNonNull( functionId, "参数 functionId( 功能编号 )不能为 null" );
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        BiConsumer< User, Scanner > biConsumer = getBiConsumerById( functionId );
        if ( Objects.isNull( biConsumer ) ){
            throw new IllegalArgumentException( String.format( "编号 \"%s\" 对应的方法不存在", functionId ) );
        }

        biConsumer.accept( user, scanner );
    }


    // ==================== 私有辅助方法 ====================

}
