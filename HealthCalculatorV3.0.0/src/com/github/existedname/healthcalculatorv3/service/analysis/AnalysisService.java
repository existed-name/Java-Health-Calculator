package com.github.existedname.healthcalculatorv3.service.analysis;

import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricIntervalAssessment;
import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.service.calculation.CalculationService;
import com.github.existedname.healthcalculatorv3.service.UIService;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.HealthMetricsProvider;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.basicbodyparameter.FemaleWaistCircumferenceMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.basicbodyparameter.MaleWaistCircumferenceMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.BMIMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.BMRMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.BSAMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.FemaleBFRMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.FemaleBRIMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.FemaleWHRMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.MaleBFRMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.MaleBRIMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.MaleWHRMetrics;
import com.github.existedname.healthcalculatorv3.service.analysis.metrics.healthmetric.TDEEMetrics;
import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import com.github.existedname.healthcalculatorv3.util.input.BodyDataReader;
import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Objects;
import java.util.Scanner;

/**
 * 分析服务类, 提供对健康指标、基本身体参数( 腰围 )数值的分析评估
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a> 
 * @since 3.0.0
 * @UpdateteTime 2025/8/26 16:03
 */
public final class AnalysisService {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================
    private static AnalysisService analysisService = null;


    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private AnalysisService(){}


    // ==================== 公有方法 ====================
    public static AnalysisService getInstance(){
        if ( Objects.isNull( analysisService ) ){
            analysisService = new AnalysisService();
        }
        return analysisService;
    }

    /*  分析体态评估类健康指标 */
    /**
     * 分析 BMI( 如果没有计算, 则会调用 {@link CalculationService#showBMICalculation(User, Scanner) } 方法计算 BMI )
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeBMI( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        CalculationService.getInstance().showBMICalculation( user, scanner );
        HealthMetricIntervalAssessment assessment = HealthMetricsProvider.findByValue( BMIMetrics.values(), HealthMetric.BMI, user.getBMI() );
        analysisTemplate( assessment, HealthMetric.BMI, user.getBMI() );
    }

    /**
     * 分析 腰围
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeWaistCircumference( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        String gender = BodyDataReader.readGender( scanner );
        double waistCircumferenceCm = BodyDataReader.readWaistCircumference( scanner );
        user.setGender( gender ); user.setWaistCircumference( waistCircumferenceCm );

        HealthMetricIntervalAssessment assessment;
        if ( Gender.isMale( gender ) ){
            assessment = HealthMetricsProvider.findByValue( MaleWaistCircumferenceMetrics.values(), BasicBodyParameter.WAIST_CIRCUMFERENCE, waistCircumferenceCm );
        } else {
            assessment = HealthMetricsProvider.findByValue( FemaleWaistCircumferenceMetrics.values(), BasicBodyParameter.WAIST_CIRCUMFERENCE, waistCircumferenceCm );
        }

        analysisTemplate( assessment, BasicBodyParameter.WAIST_CIRCUMFERENCE, waistCircumferenceCm );
    }

    /**
     * 分析 WHR( 如果没有计算, 则会调用 {@link CalculationService#showWHRCalculation(User, Scanner) } 方法计算 WHR )
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeWHR( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        CalculationService.getInstance().showWHRCalculation( user, scanner );

        HealthMetricIntervalAssessment assessment;
        if ( Gender.isMale( BodyDataReader.readGender( scanner ) ) ){
            assessment = HealthMetricsProvider.findByValue( MaleWHRMetrics.values(), HealthMetric.WHR, user.getWHR() );
        } else {
            assessment = HealthMetricsProvider.findByValue( FemaleWHRMetrics.values(), HealthMetric.WHR, user.getWHR() );
        }

        analysisTemplate( assessment, HealthMetric.WHR, user.getWHR() );
    }

    /**
     * 分析 BFR( 如果没有计算, 则会调用 {@link CalculationService#showBFRCalculation(User, Scanner) } 方法计算 BFR )
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeBFR( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        CalculationService.getInstance().showBFRCalculation( user, scanner );

        HealthMetricIntervalAssessment assessment;
        if ( Gender.isMale( BodyDataReader.readGender( scanner ) ) ){
            assessment = HealthMetricsProvider.findByValue( MaleBFRMetrics.values(), HealthMetric.BFR, user.getBFR() );
        } else {
            assessment = HealthMetricsProvider.findByValue( FemaleBFRMetrics.values(), HealthMetric.BFR, user.getBFR() );
        }

        analysisTemplate( assessment, HealthMetric.BFR, user.getBFR() );
    }

    /**
     * 分析 BRI( 如果没有计算, 则会调用 {@link CalculationService#showBRICalculation(User, Scanner) } 方法计算 BRI )
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeBRI( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        CalculationService.getInstance().showBRICalculation( user, scanner );

        HealthMetricIntervalAssessment assessment;
        if ( Gender.isMale( BodyDataReader.readGender( scanner ) ) ){
            assessment = HealthMetricsProvider.findByValue( MaleBRIMetrics.values(), HealthMetric.BRI, user.getBRI() );
        } else {
            assessment = HealthMetricsProvider.findByValue( FemaleBRIMetrics.values(), HealthMetric.BRI, user.getBRI() );
        }

        analysisTemplate( assessment, HealthMetric.BRI, user.getBRI() );
    }

    /*  分析能量代谢类健康指标 */
    /**
     * 分析 BMR( 如果没有计算, 则会调用 {@link CalculationService#showBMRCalculation(User, Scanner) } 方法计算 BMR )
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeBMR( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        CalculationService.getInstance().showBMRCalculation( user, scanner );
        HealthMetricIntervalAssessment assessment = HealthMetricsProvider.findByValue( BMRMetrics.values(), HealthMetric.BMR, user.getBMR() );
        analysisTemplate( assessment, HealthMetric.BMR, user.getBMR() );
    }

    /**
     * 分析 TDEE( 如果没有计算, 则会调用 {@link CalculationService#showTDEECalculation(User, Scanner) } 方法计算 TDEE )
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeTDEE( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        CalculationService.getInstance().showTDEECalculation( user, scanner );
        HealthMetricIntervalAssessment assessment = HealthMetricsProvider.findByValue( TDEEMetrics.values(), HealthMetric.TDEE, user.getTDEE() );
        analysisTemplate( assessment, HealthMetric.TDEE, user.getTDEE() );
    }

    /*  分析生理特征类健康指标 */
    /**
     * 分析 BSA( 如果没有计算, 则会调用 {@link CalculationService#showBSACalculation(User, Scanner) } 方法计算 BSA )
     *
     * @param user 用户对象
     * @param scanner 扫描器
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     *
     * @since 2.0.0
     */
    public void analyzeBSA( User user, Scanner scanner ){
        MethodParameterValidator.validateUserAndScanner( user, scanner );

        CalculationService.getInstance().showBSACalculation( user, scanner );
        HealthMetricIntervalAssessment assessment = HealthMetricsProvider.findByValue( BSAMetrics.values(), HealthMetric.BSA, user.getBSA() );
        analysisTemplate( assessment, HealthMetric.BSA, user.getBSA() );
    }



    // ==================== 私有辅助方法 ====================
    /**
     * 健康指标分析模板
     *
     * @param assessment 健康指标评估对象
     * @param healthMetric 健康指标枚举成员
     * @param value 健康指标数值
     *
     * @throws NullPointerException 当 assessment 或 healthMetric 为 null 时
     */
    private void analysisTemplate( HealthMetricIntervalAssessment assessment, HealthMetric healthMetric, double value ){
        Objects.requireNonNull( assessment, "参数 assessment( 健康指标评估对象 )不能为 null" );
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );

        String nameAbbreviation = healthMetric.getAbbreviation();
        String formattedValue = ValueFormatter.formatToOneDecimal( value );
        String unit = healthMetric.getUnit();
        analysisTemplate( assessment, nameAbbreviation, formattedValue, unit );
    }

    /**
     * 基本身体参数分析模板
     *
     * @param assessment 健康指标评估对象
     * @param bodyParameter 基本身体参数枚举成员
     * @param value 基本身体参数数值
     *
     * @throws NullPointerException 当 assessment 或 healthMetric 为 null 时
     */
    private void analysisTemplate( HealthMetricIntervalAssessment assessment, BasicBodyParameter bodyParameter, double value ){
        Objects.requireNonNull( assessment, "参数 assessment( 健康指标评估对象 )不能为 null" );
        Objects.requireNonNull( bodyParameter, "参数 bodyParameter( 身体参数枚举成员 )不能为 null" );

        String nameAbbreviation = bodyParameter.getAbbreviation();
        String formattedValue = ValueFormatter.formatToOneDecimal( value );
        String unit = bodyParameter.getUnit();
        analysisTemplate( assessment, nameAbbreviation, formattedValue, unit );
    }

    /**
     * 各种健康指标的通用分析模板
     *
     * @param assessment 健康指标评估对象
     * @param metricName 健康指标名称
     * @param formattedValue 格式化( 保留指定小数位数 )的健康指标数值字符串
     * @param unit 健康指标的单位
     *
     * @throws NullPointerException 当 assessment 或 metricName 或 formattedValue 或 unit 为 null 时
     *
     * @since 2.0.0
     */
    private void analysisTemplate( HealthMetricIntervalAssessment assessment, String metricName, String formattedValue, String unit ){
        Objects.requireNonNull( assessment, "参数 assessment( 健康指标评估对象 )不能为 null" );
        Objects.requireNonNull( metricName, "参数 metricName( 健康指标名称 )不能为 null" );
        Objects.requireNonNull( formattedValue, "参数 formattedValue( 格式化后的值 )不能为 null" );
        Objects.requireNonNull( unit, "参数 unit( 单位 )不能为 null" );

        // 1. 拼接报告
        StringBuilder healthReport = new StringBuilder();
        healthReport.append( "你的 " ).append( metricName ).append( " 所在区间为:\t" )
                .append( assessment.getLowerBound() ).append( unit ).append( " ≤ " )
                .append( formattedValue ).append( unit ).append( " < " )
                .append( assessment.getUpperBound() ).append( unit ).append( "\n" );
        healthReport.append( "你的健康状况为:\t" ).append( assessment.getHealthStatus() )
                .append( "\n你的健康风险为:\t" ).append( assessment.getHealthRisk() )
                .append( "\n你的健康建议为:\t" ).append( assessment.getHealthAdvice() );
        // 2. 输出报告
        UIService.getInstance().printLoadingProgress( "正在分析 " + metricName,
                "成功生成 " + metricName + " 分析报告!" );
        System.out.println( healthReport ); // TypeConvertor.toString( healthReport )
    }
}
