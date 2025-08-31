package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.util.validator.BasicBodyParameterValidator;
import com.github.existedname.healthcalculatorv3.util.validator.HealthMetricValidator;

/**
 * TDEE( Total Daily Energy Expenditure, 每日总能量消耗 )计算器工具类, 提供 TDEE 的计算公式
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateteTime 2025/8/28 10:15
 */
public final class TDEECalculator {
    // ==================== 常量 ====================
    /** TDEE 活动系数对照表 */
    private static final String[] ACTIVITY_COEFFICIENT_COMPARISON_TABLE = new String[] {
            "|活动系数|活动强度级别|    适用人群特征        |                日常活动举例                       |                运动训练举例                  |",
            "| 1.2  |  久坐不动  |几乎无日常活动,居家办公为主| 长时间阅读、看电视、伏案工作;日均步数 ＜ 3000 步       |                 无规律运动                  |",
            "| 1.375|  轻度活动  |每周 1-3 次低强度运动    |散步(30分钟/天)、轻松骑行、家务清洁;日均步数3000-6000步 |瑜伽、太极、低强度健身操( ≤ 2 次/周)             |",
            "| 1.55 |  中度活动  |每周 3-5 次中强度训练    |快走(5km/h)、慢跑、游泳(非竞技);日均步数6000-10000步  |健身房器械训练(45分钟/次)、羽毛球、篮球(3-5次/周)  |",
            "| 1.725|  高度活动  |每日高强度训练或体力劳动  |建筑工人、快递员、舞蹈演员;日均步数 ＞ 10000 步         |长跑(10km/次)、重量训练(1小时/天)、HIIT(≥5次/周) |",
            "| 1.9  |  超高强度  |职业运动员/重体力劳动者   |铁人三项训练、矿山作业、竞技体育集训;日均能耗 ＞ 3000 大卡| 马拉松备赛(每日 20km+ )、职业力量举( 2 小时/天)  |",
    };


    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private TDEECalculator(){ }


    // ==================== 公有方法 ====================
    /**
     * 展示 TDEE 系数对照表( {@link #ACTIVITY_COEFFICIENT_COMPARISON_TABLE } )
     * 用于比对选择活动系数, 进而计算 TDEE
     */
    public static void printCoefficientTable(){
        for ( String row : ACTIVITY_COEFFICIENT_COMPARISON_TABLE ){
            System.out.println( row );
        }
    }

    /**
     * 计算 TDEE, <B>单位: Kcal</B>
     * <pre>
     *     TDEE = BMR * 活动系数
     * </pre>
     *
     * @param bmr 基础代谢率 BMR, <B>单位: Kcal</B>
     * @param activityCoefficient 活动系数
     *
     * @return 每日总能量消耗 TDEE, <B>单位: Kcal</B>
     * @throws IllegalArgumentException 当 bmr 或 activityCoefficient 不在有效范围时
     */
    public static double calculateTDEE( double bmr, double activityCoefficient ){
        validateBasicParameters( bmr, activityCoefficient );
        return bmr * activityCoefficient;
    }


    // ==================== 私有辅助方法 ====================
    /** main 方法用于测试( 方便对照修改表格格式 ) */
    private static void main( String[] args ){
        printCoefficientTable();
    }

    /**
     * 检查方法参数
     *
     * @param bmr 基础代谢率 BMR, <B>单位: Kcal</B>
     * @param activityCoefficient 活动系数
     *
     * @throws IllegalArgumentException 当 bmr 或 activityCoefficient 不在有效范围时
     * @since 3.0.0
     */
    private static void validateBasicParameters( double bmr, double activityCoefficient ){
        HealthMetricValidator.validateBMR( bmr );
        BasicBodyParameterValidator.validateActivityCoefficient( activityCoefficient );
    }
}