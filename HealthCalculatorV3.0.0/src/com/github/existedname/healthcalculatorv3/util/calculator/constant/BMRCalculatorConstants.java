package com.github.existedname.healthcalculatorv3.util.calculator.constant;

import com.github.existedname.healthcalculatorv3.model.enums.bodymetric.Gender;
import com.github.existedname.healthcalculatorv3.util.calculator.basic.BMRCalculator;

/**
 * 存储 {@link BMRCalculator } 工具类的各个公式的常量( 采用嵌套类分类管理 ), <br>
 * 并封装了一些常量获取方法, 根据性别、年龄从数组中获取匹配常量
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0
 * @CreateTime 2025/8/24 15:49
 */
public final class BMRCalculatorConstants {
    // ==================== 常量 ====================



    // ==================== 内部类 ====================
    /**
     * 公式中"无此项"的常量定义( 值为0, 但具有明确业务语义 )
     * <pre>
     * 1. 系数为0: 表示公式中不包含该系数对应的项
     * 2. 参数值为0: 表示公式中虽包含该项, 但该参数的值固定为0
     * </pre>
     */
    public static final class ZeroConstants {
        /** 体重项系数为0 → 公式中不存在体重项 */
        public static final double WEIGHT_COEFFICIENT_NONE = 0;
        /** 身高项系数为0 → 公式中不存在身高项 */
        public static final double HEIGHT_COEFFICIENT_NONE = 0;
        /** 年龄项系数为0 → 公式中不存在年龄项 */
        public static final double AGE_COEFFICIENT_NONE = 0;
        /** 体重参数值为0( 用于必须传入体重但实际不存在体重项 ) */
        public static final double WEIGHT_VALUE_ZERO = 0;
        /** 身高参数值为0( 用于必须传入身高但实际不存在身高项 ) */
        public static final double HEIGHT_VALUE_ZERO = 0;
        /** 年龄参数值为0( 用于必须传入年龄但实际不存在年龄项 ) */
        public static final int AGE_VALUE_ZERO = 0;
        
        private ZeroConstants() {}
    }


    /**
     * 基于BSA计算BMR的相关常量
     * 根据性别和年龄分段的每小时基础代谢率表 (单位: Kcal/(hour*m²))
     */
    public static final class BSABasedBMR {
        /** 每天的小时数, 对应每天的基础代谢 */
        public static final int HOURS = 24;

        /**
         * 女性常量
         */
        public static final class Female {
            /**
             * 女性每小时基础代谢率表, 单位 Kcal/( hour * m² )<br>
             * 索引对应年龄分段:
             * <pre>
             * 0: 1岁, 1: 3岁, 2: 5岁, 3: 7岁, 4: 9岁
             * 5: 11岁, 6: 13岁, 7: 15岁, 8: 17岁, 9: 19岁
             * 10: 20岁, 11: 25岁, 12: 30岁, 13: 35岁, 14: 40岁
             * 15: 45岁, 16: 50岁, 17: 55岁, 18: 60岁, 19: 65岁
             * 20: 70岁, 21: 75岁, 22: 80岁及以上
             * </pre>
             */
            public static final double[] HOURLY_BMR_ARR = {
                    53.0, 51.2, 48.4, 45.4, 42.8, // [ 0,11 )岁 对应年龄1、3、5、7、9岁
                    42.0, 40.3, 37.9, 36.3, 35.5, // [ 11,20 )岁 对应年龄11、13、15、17、19岁
                    35.3, 35.2, 35.1, 35.0, 34.9, // [ 20,40 )岁 对应年龄20、25、30、35、40岁
                    34.5, 33.9, 33.3, 32.7, 32.2, // [ 40,65 )岁 对应年龄45、50、55、60、65岁
                    31.7, 31.3, 30.9, // [ 65,80+ )岁 对应年龄70、75、80岁
            };

            private Female() {}
        }

        /**
         * 男性常量
         */
        public static final class Male {
            /**
             * 男性每小时基础代谢率表, 单位 Kcal/( hour * m² )<br>
             * 索引对应年龄分段:
             * <pre>
             * 0: 1岁, 1: 3岁, 2: 5岁, 3: 7岁, 4: 9岁
             * 5: 11岁, 6: 13岁, 7: 15岁, 8: 17岁, 9: 19岁
             * 10: 20岁, 11: 25岁, 12: 30岁, 13: 35岁, 14: 40岁
             * 15: 45岁, 16: 50岁, 17: 55岁, 18: 60岁, 19: 65岁
             * 20: 70岁, 21: 75岁, 22: 80岁及以上
             * </pre>
             */
            public static final double[] HOURLY_BMR_ARR = {
                    53.0, 51.3, 49.3, 47.3, 45.2, // [ 0,11 )岁  对应年龄1、3、5、7、9岁
                    43.0, 42.3, 41.8, 40.8, 39.2, // [ 11,20 )岁 对应年龄11、13、15、17、19岁
                    38.6, 37.5, 36.8, 36.5, 36.3, // [ 20,40 )岁 对应年龄20、25、30、35、40岁
                    36.2, 35.8, 35.4, 34.9, 34.4, // [ 40,65 )岁 对应年龄45、50、55、60、65岁
                    33.8, 33.2, 33.0, // [ 65,80+ )岁 对应年龄70、75、80岁
            };

            private Male() {}

        }

        private BSABasedBMR() {}

        /**
         * 根据性别和年龄获取每小时基础代谢率
         * @param gender 性别
         * @param age 年龄
         * @return 每小时基础代谢率
         */
        public static double getHourlyBMR( String gender, int age ){

            int bmrIdx = getBMRIdx( age );
            return ( Gender.isMale( gender ) ?
                    BSABasedBMR.Male.HOURLY_BMR_ARR[ bmrIdx ] :
                    BSABasedBMR.Female.HOURLY_BMR_ARR[ bmrIdx ] );
        }

        /**
         * 根据年龄获取在 HOURLY_BMR_ARR 中对应的下标
         * @param age 年龄
         * @return 在每小时基础代谢率数组中对应的下标
         */
        private static int getBMRIdx( int age ){
            int bmrIdx;
            if ( age >= 20 ){
                if ( age >= 80 ){
                    bmrIdx = 22; // 80岁及以上默认数组最后一个元素索引
                } else {
                    bmrIdx = age / 5 + 6; // [ 20,80+ )岁以 5 为间隔
                }
            } else {
                bmrIdx = age / 2; // [ 0,20 )岁以 2 为间隔,
            }
            return bmrIdx;
        }
    }

    /**
     * Henry公式系数
     * 适用于 18-60 岁, 对中国女性个体和北方个体误差更小
     */
    public static final class HenryEquation {
        /**
         * 女性系数
         */
        public static final class Female {
            /** 体重系数(千焦) */
            public static final class WeightCoefficient {
                /** 18-30岁 */
                public static final double AGE_18_TO_30 = 47;
                /** 30-60岁 */
                public static final double AGE_30_TO_60 = 39;
            }

            /** 常量系数(千焦) */
            public static final class ConstTerm {
                /** 18-30岁 */
                public static final double AGE_18_TO_30 = 2880;
                /** 30-60岁 */
                public static final double AGE_30_TO_60 = 3070;
            }

            private Female() {}
        }

        /**
         * 男性系数
         */
        public static final class Male {
            /** 体重系数(千焦) */
            public static final class WeightCoefficient {
                /** 18-30岁 */
                public static final double AGE_18_TO_30 = 51;
                /** 30-60岁 */
                public static final double AGE_30_TO_60 = 53;
            }

            /** 常量系数(千焦) */
            public static final class ConstTerm {
                /** 18-30岁 */
                public static final double AGE_18_TO_30 = 3500;
                /** 30-60岁 */
                public static final double AGE_30_TO_60 = 3070;
            }

            private Male() {}
        }

        private HenryEquation() {}

        /**
         * 根据性别和年龄获取 Henry 公式的体重系数( 单位: 千焦 )
         * <pre>
         *     women, [ 18,30 )岁为 3500,[ 30,60 )岁为 3070,
         *     men, [ 18,30 )岁为 3500,[ 30,60 )岁为 3070
         *     年龄 < 18 岁算作[ 18, 30 ), >= 60 岁算作[ 30, 60 )
         * </pre>
         * @param gender  性别
         * @param age 年龄
         * @return 体重系数
         */
        public static double getWeightCoefficient( String gender, int age ){
            return ( Gender.isMale( gender ) ?
                    ( age < 30 ? HenryEquation.Male.WeightCoefficient.AGE_18_TO_30 : HenryEquation.Male.WeightCoefficient.AGE_30_TO_60 ) :
                    ( age < 30 ? HenryEquation.Female.WeightCoefficient.AGE_18_TO_30 : HenryEquation.Female.WeightCoefficient.AGE_30_TO_60)
            );
        }

        /**
         * 根据性别和年龄获取 Henry 公式的常数项( 单位: 千焦 )
         * <pre>
         *     women, [ 18,30 )岁为 3500,[ 30,60 )岁为 3070,
         *     men, [ 18,30 )岁为 3500,[ 30,60 )岁为 3070
         *     年龄 < 18 岁算作[ 18, 30 ), >= 60 岁算作[ 30, 60 )
         * </pre>
         * @param gender  性别
         * @param age 年龄
         * @return 常数项
         */
        public static double getConstTerm( String gender, int age ){
            return ( Gender.isMale( gender ) ?
                    ( age < 30 ? HenryEquation.Male.ConstTerm.AGE_18_TO_30 : HenryEquation.Male.ConstTerm.AGE_30_TO_60 ) :
                    ( age < 30 ? HenryEquation.Female.ConstTerm.AGE_18_TO_30 : HenryEquation.Female.ConstTerm.AGE_30_TO_60)
            );
        }
    }

    /**
     * Harris-Benedict公式系数
     * 适用于 18-60 岁, 对老年人误差较大
     */
    public static final class HBEquation {
        /** 女性系数 */
        public static final class Female {
            /** 体重系数( 单位 Kcal ) */
            public static final double WEIGHT_COEFFICIENT = 9.5634;
            /** 身高系数( 单位: Kcal ) */
            public static final double HEIGHT_COEFFICIENT = 1.8496;
            /** 年龄系数( 单位: Kcal ) */
            public static final double AGE_COEFFICIENT = -4.6756;
            /** 常数项( 单位: Kcal ) */
            public static final double CONST_TERM = 655.0955;

            private Female() {}
        }

        /** 男性系数 */
        public static final class Male {
            /** 体重系数( 单位: Kcal ) */
            public static final double WEIGHT_COEFFICIENT = 13.7516;
            /** 身高系数( 单位: Kcal ) */
            public static final double HEIGHT_COEFFICIENT = 5.0033;
            /** 年龄系数( 单位: Kcal ) */
            public static final double AGE_COEFFICIENT = -6.7550;
            /** 常数项( 单位: Kcal ) */
            public static final double CONST_TERM = 66.4730;

            private Male() {}
        }

        private HBEquation() {}
    }

    /**
     * Mifflin-St Jeor公式系数
     * 适用于所有成人(>=18岁), 误差最小, 被ADA和ASCN推荐
     */
    public static final class MSJEquation {
        /** 女性系数 */
        public static final class Female {
            /** 体重系数( 单位: Kcal ) */
            public static final double WEIGHT_COEFFICIENT = 9.99;
            /** 身高系数( 单位: Kcal ) */
            public static final double HEIGHT_COEFFICIENT = 6.25;
            /** 年龄系数( 单位: Kcal ) */
            public static final double AGE_COEFFICIENT = -4.92;
            /** 常数项( 单位: Kcal ) */
            public static final double CONST_TERM = -161;

            private Female() {}
        }

        /** 男性系数 */
        public static final class Male {
            /** 体重系数( 单位: Kcal ) */
            public static final double WEIGHT_COEFFICIENT = 9.99;
            /** 身高系数( 单位: Kcal ) */
            public static final double HEIGHT_COEFFICIENT = 6.25;
            /** 年龄系数( 单位: Kcal ) */
            public static final double AGE_COEFFICIENT = -4.92;
            /** 常数项( 单位: Kcal ) */
            public static final double CONST_TERM = 5;

            private Male() {}
        }

        private MSJEquation() {}
    }

    /**
     * 毛德倩公式系数
     * 适用于20-45岁中国人群, 误差较小
     */
    public static final class MaoEquation {
        /** 女性系数 */
        public static final class Female {
            /** 体重系数(千焦) */
            public static final double WEIGHT_COEFFICIENT = 41.9;
            /** 常数项(千焦) */
            public static final double CONST_TERM = 2269.1;

            private Female() {}
        }

        /** 男性系数 */
        public static final class Male {
            /** 体重系数(千焦) */
            public static final double WEIGHT_COEFFICIENT = 48.5;
            /** 常数项(千焦) */
            public static final double CONST_TERM = 2954.7;

            private Male() {}
        }

        private MaoEquation() {}
    }

    /**
     * Schofield公式系数
     * 推荐: 18-60岁人群, 尤其适合中国健康成人
     */
    public static final class SchofieldEquation {
        /** 女性系数 */
        public static final class Female {
            /**
             * 体重系数(千卡/大卡)<br>
             * 索引对应年龄分段:
             * <pre>
             * 0: 0-3岁, 1: 3-10岁, 2: 10-18岁, 3: 18-30岁, 4: 30-60岁, 5: 60-75岁, 6: 75+岁
             * </pre>
             */
            public static final double[] WEIGHT_COEFFICIENT_ARR = {
                    60.1,  // 0-3 year
                    22.5,  // 3-10 year
                    12.2,  // 10-18 year
                    14.8,  // 18-30 year
                    8.3,   // 30-60 year
                    9.2,   // 60-75 year
                    9.8    // 75+
            };

            /**
             * 常数项(Kcal)<br>
             * 索引对应年龄分段:
             * <pre>
             * 0: 0-3岁, 1: 3-10岁, 2: 10-18岁, 3: 18-30岁, 4: 30-60岁, 5: 60-75岁, 6: 75+岁
             * </pre>
             */
            public static final double[] CONST_TERM_ARR = {
                    -51,   // 0-3 year
                    499,   // 3-10 year
                    746,   // 10-18 year
                    487,   // 18-30 year
                    846,   // 30-60 year
                    687,   // 60-75 year
                    624    // 75+
            };

            private Female() {}
        }

        /** 男性系数 */
        public static final class Male {
            /**
             * 体重系数(千卡/大卡)<br>
             * 索引对应年龄分段:
             * <pre>
             * 0: 0-3岁, 1: 3-10岁, 2: 10-18岁, 3: 18-30岁, 4: 30-60岁, 5: 60-75岁, 6: 75+岁
             * </pre>
             */
            public static final double[] WEIGHT_COEFFICIENT_ARR = {
                    60.9,  // 0-3 year
                    22.7,  // 3-10 year
                    17.5,  // 10-18 year
                    15.1,  // 18-30 year
                    11.5,  // 30-60 year
                    11.9,  // 60-75 year
                    8.4    // 75+
            };

            /**
             * 常数项(Kcal)<br>
             * 索引对应年龄分段:
             * <pre>
             * 0: 0-3岁, 1: 3-10岁, 2: 10-18岁, 3: 18-30岁, 4: 30-60岁, 5: 60-75岁, 6: 75+岁
             * </pre>
             */
            public static final double[] CONST_TERM_ARR = {
                    -54,   // 0-3 year
                    495,   // 3-10 year
                    651,   // 10-18 year
                    692,   // 18-30 year
                    873,   // 30-60 year
                    700,   // 60-75 year
                    821    // 75+
            };

            private Male() {}
        }

        private SchofieldEquation() {}

        public static double getWeightCoefficient( String gender, int age ){
            int weightCoefficientIdx = getIdx( age );
            return ( Gender.isMale( gender ) ?
                    SchofieldEquation.Male.WEIGHT_COEFFICIENT_ARR[ weightCoefficientIdx ] :
                    SchofieldEquation.Female.WEIGHT_COEFFICIENT_ARR[ weightCoefficientIdx ] );
        }

        public static double getConstantTerm( String gender, int age ){
            int constantTermIdx = getIdx( age );
            return ( Gender.isMale( gender ) ?
                    SchofieldEquation.Male.CONST_TERM_ARR[ constantTermIdx ] :
                    SchofieldEquation.Female.CONST_TERM_ARR[ constantTermIdx ] );
        }

        /**
         * 根据年龄查找体重系数或者常数项在相应数组中的下标
         *
         * @param age 年龄
         * @return 体重系数/常数项在数组中的下标
         */
        private static int getIdx( int age ){
            /*
             *  注: a-b 通常表示左闭右开 [ a,b )
             *   idx -- 年龄
             *    0     0-3 year
             *    1     3-10 year
             *    2     10-18 year
             *    3     18-30 year
             *    4     30-60 year
             *    5     60-75 year
             *    6     75+
             */
            int idx = 3; // 默认 18-30 岁( 最常用 )
            if (age >= 30) {
                if (age >= 75) {
                    idx = 6;
                } else if (age >= 60) {
                    idx = 5;
                } else { // [ 30, 60 )
                    idx = 4;
                }
            } else if (age < 18) {
                if (age >= 10) {
                    idx = 2;
                } else if (age >= 3) {
                    idx = 1;
                } else { // [ 0, 3 )
                    idx = 0;
                }
            }
            return idx;
        }
    }

    /**
     * Shizgal-Rosa公式系数
     */
    public static final class ShizgalRosaEquation {
        /** 女性系数 */
        public static final class Female {
            /** 体重系数(千焦) */
            public static final double WEIGHT_COEFFICIENT = 39;
            /** 身高系数(千焦) */
            public static final double HEIGHT_COEFFICIENT = 13;
            /** 年龄系数(千焦) */
            public static final double AGE_COEFFICIENT = -18;
            /** 常数项(千焦) */
            public static final double CONST_TERM = 1873;

            private Female() {}
        }

        /** 男性系数 */
        public static final class Male {
            /** 体重系数(千焦) */
            public static final double WEIGHT_COEFFICIENT = 52;
            /** 身高系数(千焦) */
            public static final double HEIGHT_COEFFICIENT = 20;
            /** 年龄系数(千焦) */
            public static final double AGE_COEFFICIENT = -25;
            /** 常数项(千焦) */
            public static final double CONST_TERM = 370;

            private Male() {}
        }

        private ShizgalRosaEquation() {}
    }

    // ==================== 构造器 ====================
    private BMRCalculatorConstants() { }

    // ==================== 公有方法 ====================


    // ==================== 私有辅助方法 ====================
}
