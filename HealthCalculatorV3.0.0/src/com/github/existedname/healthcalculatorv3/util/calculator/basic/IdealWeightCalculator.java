package com.github.existedname.healthcalculatorv3.util.calculator.basic;

import com.github.existedname.healthcalculatorv3.model.entity.User;
import com.github.existedname.healthcalculatorv3.util.ValueFormatter;
import com.github.existedname.healthcalculatorv3.util.calculator.constant.IdealWeightCalculatorConstants;
import com.github.existedname.healthcalculatorv3.util.input.BodyDataReader;

import java.util.Scanner;

/**
 * 理想体重计算器工具类, 提供多种 理想体重 的计算方法, 目前用于 {@link com.github.existedname.healthcalculatorv3.service.reference.ReferenceService }
 * 中的理想体重计算
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/27 7:44
 */
public final class IdealWeightCalculator {
    // ==================== 常量 ====================


    // ==================== 静态变量 ====================


    // ==================== 实例变量 ====================


    // ==================== 构造器 ====================


    // ==================== 公有方法 ====================
    /**
     * 基于身高估计理想体重
     */
    public static final class BasedOnHeight {
        /**
         * 获取计算好的理想体重, <B>单位: kg</B>
         * <p>
         *     内部封装读取、计算操作:
         *     <pre>
         *     调用 {@link BodyDataReader } 读取用户性别和身高数据
         *     调用{@link BasedOnHeight#calculateIdealWeight( String gender, double height ) }计算理想体重
         *     </pre>
         * </p>
         *
         * @param user 用户对象
         * @param scanner 扫描器
         *
         * @return 理想体重, <B>单位: kg</B>
         * @throws NullPointerException 当 user 或 scanner 为 null 时
         */
        public static double getCalculatedIdealWeight( User user, Scanner scanner ){
            // 1. 读取、保存有效性别、身高
            String gender = BodyDataReader.readGender( scanner );
            double height = BodyDataReader.readHeight( scanner );
            user.setGender( gender ); user.setHeight( height );
            // 2. 计算理想体重
            return calculateIdealWeight( gender, height );
        }

        /**
         * 格式化理想体重范围为字符串
         * <p>
         *     内部封装计算、格式化操作:
         *     <pre>
         *     调用 {@link BasedOnHeight#calculateMinIdealWeight }、{@link BasedOnHeight#calculateMaxIdealWeight } 计算理想体重上下限
         *     调用 {@link ValueFormatter } 将理想体重上下限格式化为字符串
         *     </pre>
         * </p>
         *
         * @param idealWeight 理想体重, <B>单位: kg</B>
         * @return 理想体重范围, 形如 [ xx, xx ]
         */
        public static String formatIdealWeightRange( double idealWeight ){
            return ValueFormatter.formatClosedRange(
                    calculateMinIdealWeight( idealWeight ), calculateMaxIdealWeight( idealWeight )
            );
        }

        /**
         * 计算理想体重, <B>单位: kg</B>
         * <pre>
         *     理想体重 = 身高 - 常数( 女100, 男105 )
         *     并且允许波动 ±10%
         * </pre>
         *
         * @param gender 性别
         * @param height 身高, <B>单位: cm</B>
         *
         * @return 理想体重, <B>单位: kg</B>
         * @throws NullPointerException 当 gender 为 null 时
         * @throws IllegalArgumentException 当 gender 或 height 不在有效范围时
         */
        public static double calculateIdealWeight( String gender, double height ){
            double constTerm = IdealWeightCalculatorConstants.BasedOnHeight.getConstTerm( gender ); // 需要减掉的常数项( 男 105 女 100 )
            double idealWeight = height - constTerm;
            return idealWeight;
        }

        /**
         * 计算理想体重下限, <B>单位: kg</B>
         * <pre>
         *     理想体重下限 = 理想体重 * 0.9( 往下波动 10% )
         * </pre>
         *
         * @param idealWeight 理想体重, <B>单位: kg</B>
         * @return 理想体重下限, <B>单位: kg</B>
         */
        public static double calculateMinIdealWeight( double idealWeight ){
            return idealWeight * IdealWeightCalculatorConstants.BasedOnHeight.TO_MIN_IDEAL_WEIGHT;
        }

        /**
         * 计算理想体重上限, <B>单位: kg</B>
         * <pre>
         *     理想体重上限 = 理想体重 * 1.1( 往上波动 10% )
         * </pre>
         *
         * @param idealWeight 理想体重, <B>单位: kg</B>
         * @return 理想体重上限, <B>单位: kg</B>
         */
        public static double calculateMaxIdealWeight( double idealWeight ){
            return idealWeight * IdealWeightCalculatorConstants.BasedOnHeight.TO_MAX_IDEAL_WEIGHT;
        }

        private BasedOnHeight(){ }
    }

    /**
     * 基于 BMI 估计理想体重
     */
    public static final class BasedOnBMI {
        /**
         * 格式化理想体重范围为字符串
         * <p>
         *     内部封装计算、格式化操作:
         *     <pre>
         *     调用 {@link BasedOnBMI#calculateMinIdealWeight }、{@link BasedOnBMI#calculateMaxIdealWeight } 计算理想体重上下限
         *     调用 {@link ValueFormatter } 将理想体重上下限格式化为字符串
         *     </pre>
         * </p>
         *
         * @param userHeight 用户身高, <B>单位: cm</B>
         * @return 理想体重范围, 形如 [ xx, xx ]
         * @throws IllegalArgumentException 当 userHeight 不在有效范围时
         */
        public static String formateIdealWeightRange( double userHeight ){
            return ValueFormatter.formatClosedRange(
                    calculateMinIdealWeight( userHeight ), calculateMaxIdealWeight( userHeight )
            );
        }

        /**
         * 计算理想体重下限, <B>单位: kg</B>
         * <pre>
         *     调用 {@link BMICalculator#calculateWeightByBMI( double bmi, double height ) } 实现内部计算
         * </pre>
         *
         * @param userHeight 用户身高, <B>单位: cm</B>
         * @return 理想体重下限, <B>单位: kg</B>
         * @throws IllegalArgumentException 当 userHeight 不在有效范围时
         */
        public static double calculateMinIdealWeight( double userHeight ){
            return BMICalculator.calculateWeightByBMI( IdealWeightCalculatorConstants.BasedOnBMI.MIN_IDEAL_BMI, userHeight );
        }

        /**
         * 计算理想体重上限, <B>单位: kg</B>
         * <pre>
         *     调用 {@link BMICalculator#calculateWeightByBMI( double bmi, double height ) } 实现内部计算
         * </pre>
         *
         * @param userHeight 用户身高, <B>单位: cm</B>
         * @return 理想体重上限, <B>单位: kg</B>
         * @throws IllegalArgumentException 当 userHeight 不在有效范围时
         */
        public static double calculateMaxIdealWeight( double userHeight ){
            return BMICalculator.calculateWeightByBMI( IdealWeightCalculatorConstants.BasedOnBMI.MAX_IDEAL_BMI, userHeight );
        }

        private BasedOnBMI(){ }
    }


    // ==================== 私有辅助方法 ====================

}
