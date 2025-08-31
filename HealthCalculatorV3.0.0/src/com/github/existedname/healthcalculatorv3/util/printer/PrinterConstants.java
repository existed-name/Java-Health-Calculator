package com.github.existedname.healthcalculatorv3.util.printer;

import com.github.existedname.healthcalculatorv3.service.UIService;
import com.github.existedname.healthcalculatorv3.service.comparison.ComparisonService;
import java.util.Random;

/**
 * 控制台打印( {@link UIPrinter } )相关常量类: 分隔符、加载动画、延迟/暂停时间
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/29
 */
public final class PrinterConstants {
    // ==================== 常量 ====================
    /** 默认分隔线长度 */
    public static final int DEFAULT_LINE_LENGTH = 36;

    /** 分隔符常量类 */
    public static final class Separators {
        /** 横线分隔符 */
        public static final char HORIZONTAL_SEPARATOR_CHAR = '-';
        /** 等号分隔符 */
        public static final char EQUALS_SEPARATOR_CHAR = '=';
        /** 星号分隔符 */
        public static final char ASTERISK_SEPARATOR_CHAR = '*';
        /** 井号分隔符 */
        public static final char HASH_SEPARATOR_CHAR = '#';
        /** 波浪线分隔符 */
        public static final char TILDE_SEPARATOR_CHAR = '~';

        private Separators(){}
    }

    /** 加载动画常量类 */
    public static final class LoadingAnimation {
        /** 加载动画点的数量 */
        public static final int DOT_COUNT = 3;
        /** 点符号, 主要用于 {@link UIPrinter#printLoadingDots } */
        public static final char DOT = '.';
        /** 加载动画最小执行次数 */
        public static final int MIN_CYCLES = 2;
        /** 加载动画最大执行次数 */
        public static final int MAX_CYCLES = 5;

        private LoadingAnimation(){}

        /**
         * 获取随机加载动画执行次数, [ 2, 5 ]次
         * <p>
         *     比如随机执行 3 次, 就会在控制台看到 3 次几个点的打印、回退
         * </p>
         *
         * @return 加载动画执行次数
         */
        public static int getRandomCycles(){
            Random random = new Random();
            return ( random.nextInt(
                    LoadingAnimation.MIN_CYCLES, LoadingAnimation.MAX_CYCLES + 1 ) // [ origin, bound )
            );
        }
    }

    /** 延迟时间常量类 */
    public static final class TimeConstants {
        /** 字符级别延迟时间常量类 */
        public static final class CharDelay {
            /** 打字机每个字符间的最小延迟( ms ) */
            public static final int TYPEWRITER_CHAR_MIN_DELAY = 50;
            /** 打字机每个字符间最大的延迟( ms ) */
            public static final int TYPEWRITER_CHAR_MAX_DELAY = 150;
            /** 加载动画点与点之间的延迟( ms ) */
            public static final int LOADING_DOT_DELAY = 100; // 延迟 100 ms 比较合适
            /** 打印功能列表每个字符的延迟时间( ms ) */
            public static final int FUNCTION_LIST_CHAR_DELAY = 30;

            private CharDelay(){}

            /**
             * 获取打字机( {@link UIPrinter#typewriter( String ) } )每个字符间的延迟时间( ms )
             *
             * @return [ 50, 150 ] ms 随机延迟
             */
            public static int getRandomTypewriterCharDelay(){
                Random random = new Random();
                return ( random.nextInt(
                        TimeConstants.CharDelay.TYPEWRITER_CHAR_MIN_DELAY,
                        TimeConstants.CharDelay.TYPEWRITER_CHAR_MAX_DELAY + 1 ) // [ origin, bound )
                );
                /*
                 *      int delay = ( int )( Math.random() * 100 ) + 50; // 50-150 ms 随机延迟,模拟打字
                 *      Math.random() 返回[ 0, 1 )的小数,乘以 range,再总体强转为[ 0, range )的整数
                 *      而 Random 类需要创建对象 Random random = ...,通过对象获取随机数,
                 *  random.nextInt( range ) 获得[ 0, range )的整数
                 */
            }
        }

        /** 界面展示停顿时间常量类 */
        public static final class DisplayPause {
            /** 打印表格后的停顿时间( ms ), 主要用于<code>BodyDataReader</code> 读取活动系数时展示表格后停顿 */
            public static final int TABLE_DISPLAY_PAUSE = 7000;
            /** 进度结束后的停顿时间( ms ), 主要用于 {@link UIService#printLoadingProgress } 进度完成后停顿 */
            public static final int PROGRESS_END_PAUSE = 1200;
            /** 展示公式后的停顿时间( ms ), 主要用于 {@link ComparisonService } 展示每个公式后停顿 */
            public static final int EQUATION_DISPLAY_PAUSE = 3000;
            /** 打印功能清单后的停顿时间( ms ), 主要用于 {@link UIService#showCalculatorFunctionList } 展示功能清单停顿 */
            public static final int FUNCTION_LIST_DISPLAY_PAUSE = 5000;
            /** 阅读准备时间( ms ), 用于展示详细内容之前停顿, 给用户准备时间 */
            public static final int READING_PREPARATION_PAUSE = 2500;

            private DisplayPause(){}
        }

        private TimeConstants() { }
    }



    // ==================== 静态变量 ====================



    // ==================== 实例变量 ====================



    // ==================== 构造器 ====================
    private PrinterConstants(){}



    // ==================== 公有方法 ====================



    // ==================== 私有辅助方法 ====================

}