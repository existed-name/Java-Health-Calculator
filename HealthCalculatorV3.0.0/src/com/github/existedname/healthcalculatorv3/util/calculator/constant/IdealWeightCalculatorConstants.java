package com.github.existedname.healthcalculatorv3.util.calculator.constant;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.IdealWeightCalculator;

/**
 * 存储 {@link IdealWeightCalculator } 工具类的各个公式的常量( 采用嵌套类分类管理 )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/27
 */
public final class IdealWeightCalculatorConstants {
    // ==================== 常量 ====================
    /**
     * 基于身高计算理想体重的相关系数
     */
    public static final class BasedOnHeight {
        /** 理想体重 → 理想体重下限 */
        public static final double TO_MIN_IDEAL_WEIGHT = 0.9;
        /** 理想体重 → 理想体重上限 */
        public static final double TO_MAX_IDEAL_WEIGHT = 1.1;

        /** 女性常数 */
        public static final class Female {
            /** 常数项 */
            public static final double CONST_TERM = 100;

            private Female(){ }
        }

        /** 男性常数 */
        public static final class Male {
            /** 常数项 */
            public static final double CONST_TERM = 105;

            private Male(){ }
        }

        private BasedOnHeight(){ }

        /**
         * 获取性别对应的常数项
         *
         * @param gender 性别
         *
         * @return 常数项
         * @throws NullPointerException 当 gender 为 null 时
         * @throws IllegalArgumentException 当 gender 不在有效范围时
         */
        public static double getConstTerm( String gender ){
            return ( Gender.isMale( gender ) ?
                    IdealWeightCalculatorConstants.BasedOnHeight.Male.CONST_TERM :
                    IdealWeightCalculatorConstants.BasedOnHeight.Female.CONST_TERM );
        }
    }

    /**
     * 基于 BMI 计算理想体重的相关系数
     */
    public static final class BasedOnBMI {
        /** 理想 BMI 下限 */
        public static final double MIN_IDEAL_BMI = 18.5;
        /** 理想 BMI 上限 */
        public static final double MAX_IDEAL_BMI = 23.9;

        private BasedOnBMI(){ }
    }


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================
    private IdealWeightCalculatorConstants(){ }

    // ==================== 公有方法 ====================


    // ==================== 私有辅助方法 ====================

}
