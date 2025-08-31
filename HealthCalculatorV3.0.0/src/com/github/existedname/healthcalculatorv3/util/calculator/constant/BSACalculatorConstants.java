package com.github.existedname.healthcalculatorv3.util.calculator.constant;

import com.github.existedname.healthcalculatorv3.util.calculator.basic.BSACalculator;

/**
 * 存储 {@link BSACalculator } 工具类的各个公式的常量( 采用嵌套类分类管理 )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/24 13:09
 */
public final class BSACalculatorConstants {
    // ==================== 常量 ====================

    /**
     * 杜博伊斯公式系数
     */
    public static final class DuBoisEquation {
        /** 常数项 */
        public static final double POWER_FUNCTION_COEFFICIENT = 0.007184;
        /** 身高指数 */
        public static final double HEIGHT_EXPONENT = 0.725;
        /** 体重指数 */
        public static final double WEIGHT_EXPONENT = 0.425;

        private DuBoisEquation() {}
    }

    /**
     * 海科克公式系数
     */
    public static final class HaycockEquation {
        /** 常数项 */
        public static final double POWER_FUNCTION_COEFFICIENT = 0.024265;
        /** 身高指数 */
        public static final double HEIGHT_EXPONENT = 0.3964;
        /** 体重指数 */
        public static final double WEIGHT_EXPONENT = 0.5378;

        private HaycockEquation() {}
    }

    /**
     * 莫斯特勒公式系数
     */
    public static final class MostellerEquation {
        /** 常数项 */
        public static final double POWER_FUNCTION_COEFFICIENT = 1.0 / 60;
        /** 身高指数 */
        public static final double HEIGHT_EXPONENT = 0.5;
        /** 体重指数 */
        public static final double WEIGHT_EXPONENT = 0.5;

        private MostellerEquation() {}
    }

    /**
     * 施利希公式系数
     */
    public static final class SchlichEquation {
        /**
         * 女性公式系数
         */
        public static final class Female {
            /** 常数项 */
            public static final double POWER_FUNCTION_COEFFICIENT = 0.000975482 ; // 0.000975482
            /** 身高指数 */
            public static final double HEIGHT_EXPONENT = 1.08;
            /** 体重指数 */
            public static final double WEIGHT_EXPONENT = 0.46;

            private Female() {}
        }

        /**
         * 男性公式系数
         */
        public static final class Male {
            /** 常数项 */
            public static final double POWER_FUNCTION_COEFFICIENT = 0.000579479;
            /** 身高指数 */
            public static final double HEIGHT_EXPONENT = 1.24;
            /** 体重指数 */
            public static final double WEIGHT_EXPONENT = 0.38;

            private Male() {}
        }

        private SchlichEquation() {}
    }

    // ==================== 构造器 ====================
    private BSACalculatorConstants() { }

    // ==================== 公有方法 ====================


    // ==================== 私有辅助方法 ====================
}
