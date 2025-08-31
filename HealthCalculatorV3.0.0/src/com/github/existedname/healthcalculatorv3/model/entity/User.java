package com.github.existedname.healthcalculatorv3.model.entity;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.BasicBodyParameter;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.HealthMetric;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * 用户类
 * <p>
 *     <pre>
 * 代表系统中的一个用户, 包含用户的基本信息( 姓名、ID )、身体数据
 * 继承自 {@link UserBodyProfile}, 能够储存身体数据
 * 实现 {@link Comparable} 接口, 可通过 ID 进行排序
 *     </pre>
 * </p>
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 2.0.0
 * @UpdateTime 2025/8/26 14:03
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends UserBodyProfile implements Comparable< User > {
    // ==================== 常量 ====================



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================
    /** 用户名 */
    private String name;
    /** 用户编号【唯一】 */
    private int id;


    // ==================== 构造器 ====================

    

    // ==================== 公有方法 ====================
    /*      核心业务方法      */
    /*      getter/setter       */

    
    /*      重写方法        */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append( "user { ")
                .append( "\n\t==== 身份信息 ====" )
                .append( "\n\tName = " ).append( this.name )
                .append( "\n\tId = " ).append( this.id )
                .append( "\n\t==== 基本身体参数 ====" )
                .append( "\n\t" ).append( Gender.Category.ENGLISH_NAME ).append( " = " ).append( super.getGender() )
                .append( "\n\t" ).append( BasicBodyParameter.AGE.getEnglishName() ).append( " = " ).append( super.getAge() )
                .append( "\n\t" ).append( BasicBodyParameter.WEIGHT.getEnglishName() ).append( " = " ).append( super.getWeight( ) )
                .append( "\n\t" ).append( BasicBodyParameter.HEIGHT.getEnglishName() ).append( " = " ).append( super.getHeight( ) )
                .append( "\n\t" ).append( BasicBodyParameter.WAIST_CIRCUMFERENCE.getEnglishName() ).append( " = " ).append( super.getWaistCircumference( ) )
                .append( "\n\t" ).append( BasicBodyParameter.HIP_CIRCUMFERENCE.getEnglishName() ).append( " = " ).append( super.getHipCircumference() )
                .append( "\n\t" ).append( BasicBodyParameter.NECK_CIRCUMFERENCE.getEnglishName() ).append( " = " ).append( super.getNeckCircumference() )
                .append( "\n\t" ).append( BasicBodyParameter.ARM_CIRCUMFERENCE.getEnglishName() ).append( " = " ).append( super.getArmCircumference() )
                .append( "\n\t" ).append( BasicBodyParameter.ACTIVITY_COEFFICIENT.getEnglishName() ).append( " = " ).append( super.getActivityCoefficient() )
                .append( "\n\t==== 体态评估类身体指标 ====" )
                .append( "\n\t" ).append( HealthMetric.BMI.getAbbreviation() ).append( " = " ).append( super.getBMI() )
                .append( "\n\t" ).append( HealthMetric.WHR.getAbbreviation() ).append( " = " ).append( super.getWHR() )
                .append( "\n\t" ).append( HealthMetric.BFR.getAbbreviation() ).append( " = " ).append( super.getBFR() )
                .append( "\n\t" ).append( HealthMetric.BRI.getAbbreviation() ).append( " = " ).append( super.getBRI() )
                .append( "\n\t==== 能量代谢类身体指标 ====" )
                .append( "\n\t" ).append( HealthMetric.BMR.getAbbreviation() ).append( " = " ).append( super.getBMR() )
                .append( "\n\t" ).append( HealthMetric.TDEE.getAbbreviation() ).append( " = " ).append( super.getTDEE() )
                .append( "\n\t==== 生理特征类身体指标 ====" )
                .append( "\n\t" ).append( HealthMetric.BSA.getAbbreviation() ).append( " = " ).append( super.getBSA() )
                .append( "\n}" );
        return sb.toString();
    }

    @Override
    public int compareTo( User another ){
        Objects.requireNonNull( another, "参数 another( 另一个用户对象 )不能为 null" );
        return this.id - another.id;
        // return Integer.compare( this.id, another.id );
    }

    @Override
    public int hashCode(){
        return Objects.hash( this.id );
    }

    @Override
    public boolean equals( Object o ){
        if ( this == o ) return true;
        if ( Objects.isNull( o ) || ! Objects.equals( this.getClass(), o.getClass() ) ) return false;
        User another = ( User ) o;
        return this.id == another.id;
    }


    // ==================== 私有辅助方法 ====================
    
}
