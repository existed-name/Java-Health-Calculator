package com.github.existedname.healthcalculatorv3.util.validator;

import com.github.existedname.healthcalculatorv3.model.entity.User;

import java.util.Objects;
import java.util.Scanner;

/**
 * 方法参数检验器, 用于检验广泛、通用的方法参数( User、Scanner、区间等 )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/26 14:18
 */
public final class MethodParameterValidator {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private MethodParameterValidator() { }

    // ==================== 公有方法 ====================
    /**
     * 检验用户和扫描器
     *
     * @param user 用户对象
     * @param scanner 扫描器对象
     *
     * @throws NullPointerException 当 user 或 scanner 为 null 时
     */
    public static void validateUserAndScanner( User user, Scanner scanner ){
        validateUser( user );
        validateScanner( scanner );
    }

    /**
     * 检验用户
     *
     * @param user 用户对象
     * @throws NullPointerException 当 user 为 null 时
     */
    public static void validateUser( User user ){
        Objects.requireNonNull( user, "参数 user( 用户对象 )不能为 null" );
    }

    /**
     * 检验扫描器
     *
     * @param scanner 扫描器
     * @throws NullPointerException 当 scanner 为 null 时
     */
    public static void validateScanner( Scanner scanner ){
        Objects.requireNonNull( scanner, "参数 scanner( 扫描器对象 )不能为 null" );
    }

    /**
     * 检验数值范围是否有效
     * <pre>
     *     lowerBound、upperBound 可为任意数值类型, 但必须为 Number 的子类
     *     ( Integer、Double、Long等所有数值包装类 ),
     *     由于可以自动装箱, 也支持基本数据类型( int、double... )
     * </pre>
     * 该方法实现了 {@link Comparable } 接口, 可以让 lowerBound、upperBound 调用 compareTo 方法与同类型对象比较大小
     *
     * @param lowerBound 区间起始值
     * @param upperBound 区间结束值
     * @param <T> 泛型参数, Number 的子类
     *
     * @throws NullPointerException 当 lowerBound 或 upperBound 为 null 时
     * @throws IllegalArgumentException 当 lowerBound ≥ upperBound 时( 区间左端只能 < 右端 )
     */
    public static < T extends Number & Comparable< T > > void validateRange( T lowerBound, T upperBound ){
        Objects.requireNonNull( lowerBound, "参数 lowerBound( 区间起始值 )不能为 null" );
        Objects.requireNonNull( upperBound, "参数 upperBound( 区间结束值 )不能为 null" );
        if ( lowerBound.compareTo( upperBound ) >= 0 ){
            String message = String.format( "参数错误: lowerBound( %s )不能大于 upperBound( %s )", lowerBound, upperBound );
            throw new IllegalArgumentException( message );
        }
    }


    // ==================== 私有辅助方法 ====================

}
