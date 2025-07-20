package com.github.existedname.healthcalculatorv2.util;

public class BSACalculator {
    /*      1. 私有化构造器      */
    private BSACalculator(){
        System.out.println( "不用创建对象,直接使用即可!" );
    }

    /*      2. 计算 BSA 公有静态方法(核心功能)(按照公式名称首字母升序排列)      */
    /* 参考网址1(知乎): https://zhuanlan.zhihu.com/p/26465848 ( 提供 1 种不一样的 BSA 计算公式 )
     * 参考网址2(CSDN/三贝计算器): https://blog.csdn.net/qq_15560295/article/details/105025073 或者
     *        https://www.23bei.com/tool/298.html ( 都提供相同的 12 种公式 )
     * 参考网址3(豆包): https://www.doubao.com/chat/9220767740041730 ( 提供筛选后的经典通用/优化/补充了解公式)
     */

    /*  2.1 公式以 A 开头    */
    /*  2.2 公式以 B 开头    */
    /*  2.3 公式以 C 开头    */


    /*  2.4 公式以 D 开头    */
    // 2.4.1 杜博伊斯公式( 最经典、应用最广 )
    public static double calcByDuBoisEquation( double weightKg, double heightCm ){
        /*
         *          Du Bois Formula 杜博伊斯公式
         *  1. 原理: 基于身高、体重的幂函数拟合,是医学领域最经典、应用最广的公式,1916 年提出后长期作为标准
         *  2. 注意事项: 
         *   (1)对成年人适配性最佳,儿童 / 特殊体型(如极端肥胖)误差稍大
         *   (2)需严格用 "厘米(身高)、千克(体重)" 单位,否则结果错误
         *  3. 使用建议: 
         *   (1)临床药物剂量计算(如化疗药)、基础代谢率(BMR)估算的首选公式
         *   (2)科研数据统计、跨研究对比时,因 "经典性" 优先选它保证一致性
         */
        double constCoeff = 0.007184, heightExpon = 0.725, weightExpon = 0.425;
        return getResultByGeneralFormula( constCoeff, heightCm, heightExpon, weightKg, weightExpon );
    }


    /*  2.5 公式以 E 开头    */
    /*  2.6 公式以 F 开头    */
    /*  2.7 公式以 G 开头    */


    /*  2.8 公式以 H 开头    */
    // 2.8.1 海科克公式( 儿童 / 青少年医疗 )
    /*
     *          Haycock Formula 海科克公式
     *  1. 原理: 针对儿童群体优化,拟合儿科患者身高 - 体重 - 体表面积关系,更适配成长发育阶段的代谢特点
     *  2. 注意事项:
     *   (1)仅推荐 2-18 岁儿童 / 青少年,成年人用此公式误差大
     *   (2)需准确记录年龄(判断是否适用),且体重需排除 "儿童肥胖" 导致的极端值
     *  3. 使用建议:
     *   (1)儿科临床(如儿童化疗、营养支持)、儿童生长发育研究
     *   (2)配合儿童身高体重百分位曲线,综合评估体表面积与发育水平的关系
     */
    public static double calcByHaycockEquation( double weightKg, double heightCm ){
        double constCoeff = 0.024265, heightExpon = 0.3964, weightExpon = 0.5378;
        return getResultByGeneralFormula( constCoeff, heightCm, heightExpon, weightKg, weightExpon );
    }


    /*  2.9 公式以 I 开头    */
    /*  2.10 公式以 J 开头   */
    /*  2.11 公式以 K 开头   */
    /*  2.12 公式以 L 开头   */


    /*  2.13 公式以 M 开头   */
    // 2.13.1  莫斯特勒公式( 日常快速手动计算 )
    /*
     *          Mosteller Formula 莫斯特勒公式
     *  1. 原理: 简化版幂函数,将复杂指数运算转为开平方,更易手动计算
     *  2. 注意事项:
     *   (1)精度略低于杜博伊斯公式(误差约 ±5%),但日常估算足够
     *   (2)同样依赖身高、体重的准确测量,极端值(过高 / 过矮、过胖 / 过瘦)误差放大
     *  3. 使用建议:
     *   (1)快速估算场景(如急诊初步给药、健身人群日常参考)
     *   (2)配合体脂秤、健康 APP 使用,输入身高体重自动计算时,很多默认用此公式
     */
    public static double calcByMostellerEquation( double weightKg, double heightCm ){
        // BSA = 二次根号下[ ( 身高cm * 体重kg ) / 3600 ]
        double constCoeff = 1.0 / 60, heightExpon = 0.5, weightExpon = 0.5;
        return getResultByGeneralFormula( constCoeff, heightCm, heightExpon, weightKg, weightExpon );
    }


    /*  2.14 公式以 N 开头   */
    /*  2.15 公式以 O 开头   */
    /*  2.16 公式以 P 开头   */
    /*  2.17 公式以 Q 开头   */
    /*  2.18 公式以 R 开头   */


    /*  2.19 公式以 S 开头   */
    // 2.19.1 施利希公式( 区分性别 )
    /*
     *          Schlich Formula 施利希公式
     *  1. 原理: 区分性别拟合,考虑男女身体脂肪、肌肉分布差异对体表面积的影响
     *  2. 注意事项:
     *   (1)性别判定需准确(生理性别),否则系数错配导致误差
     *   (2)公式复杂度高,手动计算易出错,建议用工具 / APP 辅助
     *  3. 使用建议:
     *   (1)性别差异对结果影响显著的场景(如激素治疗、性别相关代谢研究)
     *   (2)科研中需精细区分性别因素时,搭配杜博伊斯公式对比验证
     */
    public static double calcBySchlichEquation( String gender, double weightKg, double heightCm ){
        // 默认女性
        double constCoeff = 0.000975482, heightExpon = 1.08, weightExpon = 0.46;
        if ( gender.equals( "男" ) ){
            constCoeff = 0.000579479; heightExpon = 1.24; weightExpon = 0.38;
        }
        
        return getResultByGeneralFormula( constCoeff, heightCm, heightExpon, weightKg, weightExpon );
    }


    /*  2.20 公式以 T 开头   */
    /*  2.21 公式以 U 开头   */
    /*  2.22 公式以 V 开头   */
    /*  2.23 公式以 W 开头   */
    /*  2.24 公式以 X 开头   */
    /*  2.25 公式以 Y 开头   */
    /*  2.26 公式以 Z 开头   */



    /*      3. 私有静态方法(内部辅助)     */
    // 1.3 BSA 计算通式--符合多数公式的形式,只需传入参数(自变量、系数、计算单位)即可
    private static double getResultByGeneralFormula( double constCoeff, double heightCm, double heightExpon, double weightKg, double weightExpon ){
        // 大部分公式都是这个格式: 常数项 * 身高(cm)的指数次方 * 体重(kg)的指数次方
        double bsa = constCoeff * Math.pow( heightCm, heightExpon ) * Math.pow( weightKg, weightExpon );
        return bsa;
    }
}