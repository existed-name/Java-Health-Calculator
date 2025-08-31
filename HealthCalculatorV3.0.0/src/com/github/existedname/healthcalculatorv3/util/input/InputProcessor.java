package com.github.existedname.healthcalculatorv3.util.input;

import java.util.Objects;

/**
 * 输入处理器, 用于对输入进行特定处理, 比如去除首尾空字符{@link #toTrimmedOrEmptyStr( String input ) }
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/19 15:28
 */
public final class InputProcessor {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================


    // ==================== 公有方法 ====================

    /**
     * 将输入进行去首尾空字符处理 {@link String#trim() }
     *
     * @param input 输入/普通字符串
     * @return input 不为 null: 去除首尾空字符后的字符串; 否则: 空字符串 ""
     */
    public static String toTrimmedOrEmptyStr( String input ){
        if ( Objects.isNull( input ) ){
            return "";
        }
        return input.trim();
    }


    // ==================== 私有辅助方法 ====================

}
