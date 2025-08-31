package com.github.existedname.healthcalculatorv3.util.convertor;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import java.util.Objects;

/**
 * 类型转换器, 用于对不同数据类型进行转换
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/19 20:42
 */
public final class TypeConvertor {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private TypeConvertor(){}

    // ==================== 公有方法 ====================
    /**
     * String 转 StringBuilder
     *
     * @param srcStr 待转换的字符串
     * @return 源字符串对应的 StringBuilder
     *
     * @throws NullPointerException 当 srcStr 为 null 时
     * @since 2.0.0
     */
    public static StringBuilder toStringBuilder( String srcStr ){
        Objects.requireNonNull( srcStr, "参数 srcStr 不能为 null" );
        return ( new StringBuilder( srcStr ) );
    }

    /**
     * StringBuilder 转 String
     *
     * @param strBuilder 待转换的 StringBuilder
     * @return StringBuilder 对应的 String
     *
     * @throws NullPointerException 当 strBuilder 为 null 时
     * @since 2.0.0
     */
    public static String toString( StringBuilder strBuilder ){
        Objects.requireNonNull( strBuilder, "参数 strBuilder 不能为 null" );
        // return strBuilder.substring( 0, strBuilder.length() ); // [ start, end ) 左闭右开
        return strBuilder.toString();
    }

    /**
     * boolean 转 int
     * <p>
     *     曾用于 {@link Gender#intIsMale( String gender ) } 方法将 boolean 型的 isMale 转换为 int
     * </p>
     *
     * @param isTrue 待转换的 boolean 类型变量
     * @return true 1, false 0
     */
    public static int toInt( boolean isTrue ){
        return ( isTrue ? 1 : 0 );
    }

    /**
     * double 转 int, 主要用于将 {@link BasicBodyParameter#AGE } 的 double 有效范围转为 int
     *
     * @param value 待转换的 double 数值
     * @return int 值( 向 0 取整 )
     */
    public static int toInt( double value ){
        return ( int ) value;
    }


    // ==================== 私有辅助方法 ====================

}
