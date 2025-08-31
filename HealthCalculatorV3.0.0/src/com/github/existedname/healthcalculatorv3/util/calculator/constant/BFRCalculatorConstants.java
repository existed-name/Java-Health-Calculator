package com.github.existedname.healthcalculatorv3.util.calculator.constant;

import com.github.existedname.healthcalculatorv3.util.calculator.basic.BFRCalculator;

/**
 * 存储 {@link BFRCalculator } 工具类的各个公式的常量( 采用嵌套类分类管理 )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/24 9:57
 */
public final class BFRCalculatorConstants {
    // ==================== 常量 ====================

    /**
     * Deurenberg公式系数
     */
    public static final class DeurenbergEquation {
        /**
         * 成人公式系数 (年龄≥16岁)
         */
        public static final class Adult {
            /** BMI系数 */
            public static final double BMI_COEFFICIENT = 1.20;
            /** 年龄系数 */
            public static final double AGE_COEFFICIENT = 0.23;
            /** 性别系数 */
            public static final double GENDER_COEFFICIENT = -10.8;
            /** 常数项 */
            public static final double CONST_TERM = -5.4;

            private Adult() {}
        }

        /**
         * 儿童公式系数 (年龄≤15岁)
         */
        public static final class Child {
            /** BMI系数 */
            public static final double BMI_COEFFICIENT = 1.51;
            /** 年龄系数 */
            public static final double AGE_COEFFICIENT = -0.70;
            /** 性别系数 */
            public static final double GENDER_COEFFICIENT = -3.6;
            /** 常数项 */
            public static final double CONST_TERM = 1.4;

            private Child() {}
        }

        private DeurenbergEquation() {}
    }

    /**
     * Gallagher公式系数
     */
    public static final class GallagherEquation {
        /**
         * 版本1：基于BMI、年龄、性别
         */
        public static final class Version1 {
            /** BMI系数 */
            public static final double BMI_COEFFICIENT = 1.46;
            /** 年龄系数 */
            public static final double AGE_COEFFICIENT = 0.12;
            /** 性别系数 */
            public static final double GENDER_COEFFICIENT = -11.61;
            /** 常数项 */
            public static final double CONST_TERM = -10.02;

            private Version1() {}
        }

        /**
         * 版本2：基于BMI的倒数、年龄、性别
         */
        public static final class Version2 {
            /** BMI系数 */
            public static final double BMI_COEFFICIENT = -848;
            /** 年龄系数 */
            public static final double AGE_COEFFICIENT = 0.079;
            /** 性别系数 */
            public static final double GENDER_COEFFICIENT = -16.4;
            /** 性别与年龄交互系数 */
            public static final double GENDER_AGE_INTERACTION = 0.05;
            /** 性别与BMI交互系数 */
            public static final double GENDER_BMI_INTERACTION = 39.0;
            /** 常数项 */
            public static final double CONST_TERM = 64.5;

            private Version2() {}
        }

        private GallagherEquation() {}
    }

    /**
     * Jackson-Pollock公式系数
     */
    public static final class JacksonPollockEquation {
        /**
         * 版本1: 完整公式系数( 基于 皮褶厚度 )
         */
        public static final class CompleteVersion {
            private CompleteVersion() {}
        }

        /**
         * 版本2: 简化公式系数
         */
        public static final class SimplifiedVersion {
            /** BMI系数 */
            public static final double BMI_COEFFICIENT = 1.61;
            /** 年龄系数 */
            public static final double AGE_COEFFICIENT = 0.13;
            /** 性别系数 */
            public static final double GENDER_COEFFICIENT = -12.1;
            /** 常数项 */
            public static final double CONST_TERM = -13.9;

            private SimplifiedVersion() {}
        }

        private JacksonPollockEquation() {}
    }



    /**
     * 美国海军公式系数
     */
    public static final class USNEquation {
        /**
         * 男性公式系数
         */
        public static final class Male {
            /** 对数项1系数 */
            public static final double LOGARITHM_TERM_COEFFICIENT1 = 86.010;
            /** 对数项2系数 */
            public static final double LOGARITHM_TERM_COEFFICIENT2 = -70.041;
            /** 常数项 */
            public static final double CONST_TERM = 36.76;

            private Male() {}
        }

        /**
         * 女性公式系数
         */
        public static final class Female {
            /** 对数项1系数 */
            public static final double LOGARITHM_TERM_COEFFICIENT1 = 163.205;
            /** 对数项2系数 */
            public static final double LOGARITHM_TERM_COEFFICIENT2 = -97.684;
            /** 常数项 */
            public static final double CONST_TERM = -78.387;

            private Female() {}
        }

        private USNEquation() {}
    }

    // ==================== 构造器 ====================
    private BFRCalculatorConstants() { }

    // ==================== 公有方法 ====================


    // ==================== 私有辅助方法 ====================

}
