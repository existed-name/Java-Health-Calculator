package com.github.existedname.healthcalculatorv3.util.convertor;

/**
 * 单位转换器, 封装常用单位转换的常量、方法
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @UpdateTime 2025/8/28 20:27
 */
public final class UnitConvertor {
    // ==================== 常量 ====================
    /** 单位转换常量类 */
    public static final class Constants {
        /** 长度转换常量类 */
        public static final class Length {
            /** 1英寸 = 2.54厘米 */
            public static final double INCH_TO_CM = 2.54;
            /** 1厘米 ≈ 0.39370079英寸 */
            public static final double CM_TO_INCH = 1 / INCH_TO_CM; //  0.39370078740157477
            /** 1米 = 100厘米 */
            public static final int METRE_TO_CM = 100;
            /** 1厘米 = 0.01米 */
            public static final double CM_TO_METRE = 0.01;

            private Length(){ }
        }

        /** 热量转换常量类 */
        public static final class HeatQuantity {
            /** 1卡路里 = 4.184焦耳 */
            public static final double CALORIE_TO_JOULE = 4.184;
            /** 1焦耳 ≈ 0.23900574卡路里 */
            public static final double JOULE_TO_CALORIE = 1 / CALORIE_TO_JOULE; // 0.2390057361376673
            /** 1千焦 = 1000 焦耳 */
            public static final int KJ_TO_JOULE = 1000;
            /** 1焦耳 = 0.001千焦 */
            public static final double JOULE_TO_KJ = 0.001;
            /** 1千卡 = 1000 卡路里 */
            public static final int KCAL_TO_CALORIE = 1000;
            /** 1卡路里 = 0.001千卡 */
            public static final double CALORIE_TO_KCAL = 0.001;

            private HeatQuantity(){ }
        }

        /** 百分比/小数转换常量类 */
        public static final class Percentage {
            /** 小数乘以 100, 变成百分比 */
            public static final int DECIMAL_TO_PERCENTAGE = 100;
            /** 百分比乘以 0.01, 变成小数 */
            public static final double PERCENTAGE_TO_DECIMAL = 0.01;

            private Percentage(){ }
        }

        private Constants(){}
    }


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================


    // ==================== 公有方法 ====================
    /*      核心业务方法      */
    /*  6.4 单位转换    */
    // 6.4.1

    /**
     * 厘米转英寸, {@link Constants.Length#CM_TO_INCH }
     *
     * @param cm 厘米
     * @return 英寸( inch )
     */
    public static double cmToInch( double cm ){
        return cm * Constants.Length.CM_TO_INCH;
    }

    /**
     * 英寸转厘米, {@link Constants.Length#INCH_TO_CM }
     *
     * @param inch 英寸
     * @return 厘米( cm )
     */
    public static double inchToCm( double inch ){
        return inch * Constants.Length.INCH_TO_CM;
    }

    /**
     * 厘米转米, {@link Constants.Length#CM_TO_METRE }
     *
     * @param cm 厘米
     * @return 米( metre )
     */
    public static double cmToMetre( double cm ){
        return cm * Constants.Length.CM_TO_METRE;
    }

    /**
     * 米转厘米, {@link Constants.Length#METRE_TO_CM }
     *
     * @param metre 米
     * @return 厘米( cm )
     */
    public static double metreToCm( double metre ){
        return metre * Constants.Length.METRE_TO_CM;
    }

    /**
     * 焦耳( Joule )转卡路里( calorie ) 或者 千焦( KJ )转千卡( kcal )<br>
     * {@link Constants.HeatQuantity#JOULE_TO_CALORIE }
     *
     * @param joule 焦耳( 或者 千焦 )
     * @return 卡路里( calorie )( 或者 千卡 )
     *
     * @see <a href="https://baike.baidu.com/item/%E5%8D%A1%E8%B7%AF%E9%87%8C/284236"> 参考卡路里定义 </a> :
     *          ①1 cal ≈ 4.184 J ② 1 cal ≈ 4.186 J(4.1858518), 此处取 ①
     */
    public static double jouleToCalorie( double joule ){
        return joule * Constants.HeatQuantity.JOULE_TO_CALORIE;
    }

    /**
     * 卡路里( calorie )转焦耳( Joule ) 或者 千卡( kcal )转千焦( KJ )<br>
     * {@link Constants.HeatQuantity#CALORIE_TO_JOULE }
     *
     * @param calorie 卡路里( 或者 千卡 )
     * @return 焦耳( 或者 千焦 )
     */
    public static double calorieToJoule( double calorie ){
        return calorie * Constants.HeatQuantity.CALORIE_TO_JOULE;
    }

    /**
     * 焦耳( Joule )转千焦( KJ ), {@link Constants.HeatQuantity#JOULE_TO_KJ }
     *
     * @param joule 焦耳
     * @return 千焦
     */
    public static double jouleToKJ( double joule ){
        return joule * Constants.HeatQuantity.JOULE_TO_KJ;
    }

    /**
     * 卡路里( cal )转千卡( kcal ), {@link Constants.HeatQuantity#CALORIE_TO_KCAL }
     *
     * @param calorie 卡路里
     * @return 千卡/大卡
     */
    public static double calorieToKcal( double calorie ){
        return calorie * Constants.HeatQuantity.CALORIE_TO_KCAL;
    }

    /**
     * 百分比转小数, {@link Constants.Percentage#PERCENTAGE_TO_DECIMAL }
     *
     * @param percentage 百分比, 形如 xx%
     * @return 小数
     */
    public static double percentageToDecimal( double percentage ){
        return percentage * Constants.Percentage.PERCENTAGE_TO_DECIMAL;
    }


    // ==================== 私有辅助方法 ====================

}
