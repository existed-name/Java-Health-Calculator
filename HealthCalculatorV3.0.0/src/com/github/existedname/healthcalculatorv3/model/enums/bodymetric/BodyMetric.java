package com.github.existedname.healthcalculatorv3.model.enums.bodymetric;

/**
 * 身体指标接口
 * <p>
 * 提供方法: 获取有效范围区间 {@link #getFormattedClosedRange() }、检查身体指标的数值是否有效 {@link #isValid(double) } 等
 * </p><p>
 *     由基本身体参数枚举类 {@link BasicBodyParameter }、健康指标枚举类 {@link HealthMetric } 实现
 * </p>
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/19 20:24
 */
public interface BodyMetric {
    /** 获取有效范围区间字符串 */
    String getFormattedClosedRange();
    /** 检查身体指标的数值是否有效 */
    boolean isValid( double value );
}
