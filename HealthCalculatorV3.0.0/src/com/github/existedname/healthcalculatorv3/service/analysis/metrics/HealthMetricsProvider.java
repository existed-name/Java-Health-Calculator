package com.github.existedname.healthcalculatorv3.service.analysis.metrics;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import com.github.existedname.healthcalculatorv3.util.ValueFormatter;

import java.util.Objects;

/**
 *
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/23
 */
public interface HealthMetricsProvider {
    /**
     * 根据健康指标的数值查找其所在区间对应的评估
     *
     * @param metricsProviders 健康指标集的所有枚举成员
     * @param healthMetric 该健康指标对应的枚举成员
     * @param value 该健康指标的数值
     * @return 对这个健康指标的评估
     *
     * @throws NullPointerException 当 metricsProviders 为 null 时
     * @throws NullPointerException 当 healthMetric 为 null 时
     * @throws IllegalArgumentException 当 value 不在该健康指标的有效范围内时
     */
    static HealthMetricIntervalAssessment findByValue( HealthMetricsProvider[] metricsProviders, HealthMetric healthMetric, double value ){
        Objects.requireNonNull( metricsProviders, "参数 metricsProviders( 健康指标集的枚举成员数组 )不能为 null" );
        Objects.requireNonNull( healthMetric, "参数 healthMetric( 健康指标枚举成员 )不能为 null" );

        String abbreviation = healthMetric.getAbbreviation();
        String formattedClosedRange = healthMetric.getFormattedClosedRange();
        return findByValue( metricsProviders, abbreviation, formattedClosedRange, value );
    }

    /**
     * 根据基本身体参数的数值查找其所在区间对应的评估
     *
     * @param metricsProviders 基本身体参数指标集的所有枚举成员
     * @param bodyParameter 该基本身体参数对应的枚举成员
     * @param value 该基本身体参数的数值
     * @return 对这个基本身体参数的评估
     *
     * @throws NullPointerException 当 metricsProviders 为 null 时
     * @throws NullPointerException 当 healthMetric 为 null 时
     * @throws IllegalArgumentException 当 value 不在该基本身体参数的有效范围内时
     */
    static HealthMetricIntervalAssessment findByValue( HealthMetricsProvider[] metricsProviders, BasicBodyParameter bodyParameter, double value ){
        Objects.requireNonNull( metricsProviders, "参数 metricsProviders( 基本身体参数指标集的枚举成员数组 )不能为 null" );
        Objects.requireNonNull( bodyParameter, "参数 bodyParameter( 基本身体参数枚举成员 )不能为 null" );

        String abbreviation = bodyParameter.getAbbreviation();
        String formattedClosedRange = bodyParameter.getFormattedClosedRange();
        return findByValue( metricsProviders, abbreviation, formattedClosedRange, value );
    }

    /**
     * 根据身体参数的数值查找其所在区间对应的评估
     *
     * @param metricsProviders 身体参数指标集的所有枚举成员
     * @param nameAbbreviation 该身体参数的简称
     * @param formattedClosedRange 该身体参数的有效范围闭区间字符串
     * @param value 该身体参数的数值
     * @return 对这个身体参数的评估
     *
     * @throws NullPointerException 当 metricsProviders 为 null 时
     * @throws NullPointerException 当 nameAbbreviation 为 null 时
     * @throws NullPointerException 当 formattedClosedRange 为 null 时
     * @throws IllegalArgumentException 当 value 不在该基本身体参数的有效范围内时
     */
    static HealthMetricIntervalAssessment findByValue( HealthMetricsProvider[] metricsProviders, String nameAbbreviation, String formattedClosedRange, double value ){
        Objects.requireNonNull( metricsProviders, "参数 metricsProviders( 健康指标数据集的枚举成员数组 )不能为 null" );
        Objects.requireNonNull( nameAbbreviation, "参数 nameAbbreviation( 健康指标的简称 )不能为 null" );
        Objects.requireNonNull( formattedClosedRange, "参数 formattedClosedRange( 健康指标的格式化有效范围 )不能为 null" );

        for ( HealthMetricsProvider metricsProvider : metricsProviders ){
            if ( metricsProvider.getAssessment().isInRange( value ) ){
                return metricsProvider.getAssessment();
            }
        }
        throw new IllegalArgumentException(
                String.format( "参数 value = %s, 不能超过 %s 的有效范围%s",
                            ValueFormatter.formatToOneDecimal( value ),
                            nameAbbreviation, formattedClosedRange )
        );
    }


    /**
     * 用于被指标集枚举类重写, 返回对当前枚举成员的数值范围的评估
     * @return 当前指标集枚举成员对应的健康评估
     */
    HealthMetricIntervalAssessment getAssessment();
}
