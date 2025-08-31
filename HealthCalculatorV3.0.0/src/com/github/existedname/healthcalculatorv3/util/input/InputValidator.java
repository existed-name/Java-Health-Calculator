package com.github.existedname.healthcalculatorv3.util.input;

import com.github.existedname.healthcalculatorv3.util.validator.MethodParameterValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 输入验证器, 提供对输入数据的验证方法
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/13 15:43
 */
public final class InputValidator {
    private static final boolean ALLOW_NULL_TARGET_STRING = false;
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================


    // ==================== 公有方法 ====================
    /**
     * 判断数据是否在要求的<b>闭区间</b>内
     *
     * @param value 待判断的数据
     * @param lowerBound 闭区间左端
     * @param upperBound 闭区间右端
     *
     * @return true: 数据在闭区间内, 否则 false
     * @throws IllegalArgumentException 当 lowerBound > upperBound 时
     * @since 2.0.0
     */
    public static boolean isInRange( double value, double lowerBound, double upperBound ){
        MethodParameterValidator.validateRange( lowerBound, upperBound );
        return ( lowerBound <= value && value <= upperBound );
    }

    /**
     * 判断数据是否在要求的<b>闭区间</b>内
     *
     * @param value 待判断的数据
     * @param lowerBound 闭区间左端
     * @param upperBound 闭区间右端
     *
     * @return true: 数据在闭区间内, 否则 false
     * @throws IllegalArgumentException 当 lowerBound > upperBound 时
     * @since 2.0.0
     */
    public static boolean isInRange( int value, int lowerBound, int upperBound ){
        if ( lowerBound > upperBound ) throw new IllegalArgumentException( "参数 lowerBound 不能大于 upperBound" );
        return ( lowerBound <= value && value <= upperBound );
    }

    /**
     * 查找字符串数组中是否存在目标字符串
     *
     * @param strArr 字符串数组, 可为 null、空数组
     * @param targetStr 目标字符串
     * @return false: 字符串数组中不存在目标字符串 或者 strArr 为 null/空数组 或者 targetStr 为 null; 否则 true
     * @since 2.0.0
     */
    public static boolean contains( String[] strArr, String targetStr ){
        if ( strArr == null || strArr.length == 0 ){
            return false;
        }
        // 判断是否允许 target 为 null
        if ( ! ALLOW_NULL_TARGET_STRING && targetStr == null ){
            return false;
        }
        /*
        for ( String string : strArr ){
            if ( Objects.equals( string, targetStr ) ){
                // 使用 Objects.equals 避免 2 个对象都为 null
                return true;
            }
        }
        return false;
         */
        return Arrays.stream( strArr ).anyMatch( string -> Objects.equals( string, targetStr ) );
    }

    /**
     * 查找字符串链表中是否存在目标字符串
     *
     * @param stringList 字符串链表, 可为 null、空数组
     * @param targetStr 目标字符串
     * @return false: 字符串数组中不存在目标字符串  或者  strArr 为 null/空链表  或者  targetStr 为 null; 否则 true
     */
    public static boolean contains( List< String> stringList, String targetStr ){
        if ( stringList == null || stringList.isEmpty() ){
            return false;
        }
        if ( ! ALLOW_NULL_TARGET_STRING && targetStr == null ){
            return false;
        }
        return stringList.contains( targetStr );
    }

    /**
     * 判断字符串文本中是否包含子串
     *
     * @param text 字符串文本
     * @param targetStr 目标字符串
     * @return false: 字符串文本中不存在目标字符串  或者  text 为 null/空字符串  或者  targetStr 为 null; 否则 true
     */
    public static boolean containsSubstring( String text, String targetStr ){
        if ( text == null || targetStr == null ) return false;
        return text.contains( targetStr ); // 允许两者为空字符串""
    }


    // ==================== 私有辅助方法 ====================


}
