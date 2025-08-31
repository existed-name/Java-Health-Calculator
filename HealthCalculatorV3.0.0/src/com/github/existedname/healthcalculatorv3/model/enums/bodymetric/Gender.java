package com.github.existedname.healthcalculatorv3.model.enums.bodymetric;

import com.github.existedname.healthcalculatorv3.util.input.InputProcessor;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 性别枚举类, 用于表示个体的性别信息
 * <p>
 * 包含两种性别选项: {@link #FEMALE 女性} 和 {@link #MALE 男性}<br>
 * 提供了性别相关的操作方法，如验证性别、判断是否为男性等
 * </p>
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @UpdateTime 2025/8/26 14:13
 */
@AllArgsConstructor
public enum Gender {
    // GENDER( "性别", "Gender", -1 ),
    // UNKNOWN("未知", "Unknown", -1),
    FEMALE("女", "Female", 0),
    MALE("男", "Male", 1);

    private final String chineseName;
    private final String englishName;
    private final int code;

    /**
     * "性别"类别, 用于获取"性能"的名称
     */
    public static final class Category {
        /** 中文名称 */
        public static final String CHINESE_NAME = "性别";
        /** 英文名称 */
        public static final String ENGLISH_NAME = "Gender";
    }

    /**
     * 获取所有的有效性别选项( "男"、"女" )
     * @return List< String > 类型的性别选项
     */
    public static List< String > getValidGenderOptions() {
        return Arrays.stream( values() ).map( Gender::getChineseName ).toList();
    }

    /**
     * 判断给定的性别字符串是否有效
     * @param gender 性别字符串
     * @return true: 有效性别, false: 无效性别字符串或者 gender 为 null
     */
    public static boolean isValidGender( String gender ){
        if ( Objects.isNull( gender ) ) return false;
        String trimmedGender = InputProcessor.toTrimmedOrEmptyStr( gender );
        return Arrays.stream( values() ).anyMatch(
                validGender -> validGender.chineseName.equals( trimmedGender ) ||
                        validGender.englishName.equalsIgnoreCase( trimmedGender ) // 以后可以拓展支持英文名
        );
    }

    /**
     * 判断是否是男性
     * @param gender 性别字符串
     * @return true: 是男性( "男" ), false: 不是男性( "女" )
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 为无效性别时
     */
    public static boolean isMale( String gender ){
        Objects.requireNonNull( gender, "参数 gender( 性别 )不能为 null" );
        if ( ! isValidGender( gender ) ){
            throw new IllegalArgumentException( "无效的性别参数: " + gender );
        }
        return InputProcessor.toTrimmedOrEmptyStr( gender ).equals( Gender.MALE.getChineseName() ); // "男"
    }

    /**
     * 将 boolean 型的 isMale 换为 int 型,作为访问数组的下标或者用来计算
     * ( {@link com.github.existedname.healthcalculatorv3.util.calculator.basic.BFRCalculator#calculateBFRByGallagherEquation2 } )
     * @param gender 性别字符串
     * @return 男性: 1, 女性: 0
     *
     * @throws NullPointerException 当 gender 为 null 时
     * @throws IllegalArgumentException 当 gender 为无效性别时
     */
    public static int intIsMale( String gender ){
        return ( isMale( gender ) ? MALE.getCode() : FEMALE.getCode() );
    }

    /** 获取当前性别枚举成员的中文名 */
    public String getChineseName() { return chineseName; }
    /** 获取当前性别枚举成员的英文名 */
    public String getEnglishName() { return englishName; }
    /** 获取当前性别枚举成员的代码( 女0 男1 ) */
    public int getCode() { return code; }
}