package com.github.existedname.healthcalculatorv3.util;

import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Objects;

/**
 * 数值格式化工具类, 提供将数值格式化为字符串或闭区间等功能
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/16 14:27
 */
public final class ValueFormatter {
    // ==================== 常量 ====================
    /** 格式常量类 */
    public static final class Constants {
        /** 基础数值格式常量类 */
        public static class ValueFormats {
            /** 整数格式常量 */
            public static final String INTEGER_FORMAT = "%d";
            /** 1 位小数格式常量 */
            public static final String ONE_DECIMAL_FORMAT = "%.1f";
            /** 2 位小数格式常量 */
            public static final String TWO_DECIMAL_FORMAT = "%.2f";
            /** 3 位小数格式常量 */
            public static final String THREE_DECIMAL_FORMAT = "%.3f";
            
            private ValueFormats(){ }
        }

        /** 区间格式常量类 */
        public static class RangeFormats {
            /** 整数闭区间格式常量 */
            public static final String INTEGER_CLOSED_RANGE_FORMAT = "[ %d, %d ]";
            /** 1 位小数闭区间格式常量 */
            public static final String ONE_DECIMAL_CLOSED_RANGE_FORMAT = "[ %.1f, %.1f ]";
            /** 2 位小数闭区间格式常量 */
            public static final String TWO_DECIMAL_CLOSED_RANGE_FORMAT = "[ %.2f, %.2f ]";
            /** 3 位小数闭区间格式常量 */
            public static final String THREE_DECIMAL_CLOSED_RANGE_FORMAT = "[ %.3f, %.3f ]";
            
            private RangeFormats(){ }
        }
        
        private Constants(){ }
    }


    // ==================== 构造器 ====================
    private ValueFormatter(){ }

    // ==================== 公有方法 ====================
    /**
     * 把 int 格式化为字符串
     *
     * @param value 待格式化的 int 数值
     * @return 格式化后的字符串
     */
    public static String formatToInteger( int value ){
        return String.format( Constants.ValueFormats.INTEGER_FORMAT, value );
    }

    /**
     * 把 double 格式化为 1 位小数字符串
     *
     * @param value 待格式化的 double 数值
     * @return 格式化后的字符串( 1 位小数 )
     */
    public static String formatToOneDecimal( double value ){
        return String.format( Constants.ValueFormats.ONE_DECIMAL_FORMAT, value );
    }

    /**
     * 把 double 格式化为 2 位小数字符串
     *
     * @param value 待格式化的 double 数值
     * @return 格式化后的字符串( 2 位小数 )
     */
    public static String formatToTwoDecimal( double value ){
        return String.format( Constants.ValueFormats.TWO_DECIMAL_FORMAT, value );
    }

    /**
     * 把 double 格式化为 3 位小数字符串
     *
     * @param value 待格式化的 double 数值
     * @return 格式化后的字符串( 3 位小数 )
     */
    public static String formatToThreeDecimal( double value ){
        return String.format( Constants.ValueFormats.THREE_DECIMAL_FORMAT, value );
    }

    /**
     * 把 int 数值范围格式化为闭区间字符串
     *
     * @param lowerBound 闭区间起始值
     * @param upperBound 闭区间结束值
     *
     * @return 格式化后的闭区间: [ lowerBound, upperBound ]
     * @throws IllegalArgumentException 当 lowerBound >= upperBound 时( 区间必须满足左端 < 右端 )
     */
    public static String formatClosedRange( int lowerBound, int upperBound ){
        MethodParameterValidator.validateRange( lowerBound, upperBound );
        return String.format( Constants.RangeFormats.INTEGER_CLOSED_RANGE_FORMAT, lowerBound, upperBound );
    }

    /**
     * 把 double 数值范围格式化为 1 位小数闭区间字符串
     *
     * @param lowerBound 闭区间起始值
     * @param upperBound 闭区间结束值
     *
     * @return 格式化后的闭区间: [ 保留 1 位小数, 保留 1 位小数 ]
     * @throws IllegalArgumentException 当 lowerBound >= upperBound 时( 区间必须满足左端 < 右端 )
     */
    public static String formatClosedRange( double lowerBound, double upperBound ){
        MethodParameterValidator.validateRange( lowerBound, upperBound );
        return String.format( Constants.RangeFormats.ONE_DECIMAL_CLOSED_RANGE_FORMAT, lowerBound, upperBound );
    }

    /**
     * 把 double 数值范围格式化为 2 位小数闭区间字符串
     *
     * @param lowerBound 闭区间起始值
     * @param upperBound 闭区间结束值
     *
     * @return 格式化后的闭区间: [ 保留 2 位小数, 保留 2 位小数 ]
     * @throws IllegalArgumentException 当 lowerBound >= upperBound 时( 区间必须满足左端 < 右端 )
     */
    public static String formatToTwoDecimalClosedRange( double lowerBound, double upperBound ){
        MethodParameterValidator.validateRange( lowerBound, upperBound );
        return String.format( Constants.RangeFormats.TWO_DECIMAL_CLOSED_RANGE_FORMAT, lowerBound, upperBound );
    }

    /**
     * 把 Number 的子类数值范围格式化为闭区间字符串
     * <pre>
     *     <strong>该版本仅支持 Integer、Double</strong>
     * </pre>
     *
     * @param lowerBound 闭区间起始值
     * @param upperBound 闭区间结束值
     *
     * @return 格式化后的闭区间: [ lowerBound, upperBound ]
     * @throws NullPointerException 当 lowerBound 或 upperBound 为 null 时
     * @throws IllegalArgumentException 当满足下列条件时: <pre>
     *              1. lowerBound 和 upperBound 的类型不同或者不支持<br>
     *              2. lowerBound >= upperBound 时( 区间必须满足左端 < 右端 )</pre>
     */
    public static String formatClosedRange( Number lowerBound, Number upperBound ){
        Objects.requireNonNull( lowerBound, "参数 lowerBound( 起始值 )不能为 null" );
        Objects.requireNonNull( upperBound, "参数 upperBound( 结束值 )不能为 null" );
        
        if ( lowerBound instanceof Integer && upperBound instanceof Integer ){
            return formatClosedRange( ( int )lowerBound, ( int )upperBound );
        } else if ( lowerBound instanceof Double && upperBound instanceof Double ){
            return formatClosedRange( ( double )lowerBound, ( double )upperBound );
        } else {
            throw new IllegalArgumentException( "参数 lowerBound( 起始值 )和 upperBound( 结束值 )的类型可能不同或者不支持" );
        }
    }

    // ==================== 私有辅助方法 ====================
}
