package com.github.existedname.healthcalculatorv3.service.introduction;

import com.github.existedname.healthcalculatorv3.util.convertor.TypeConvertor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 健康指标、部分基本身体参数( 腰围 )的详细介绍
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/20
 */
@Getter
@AllArgsConstructor
public enum HealthMetricIntroduction {
    BMI( "身体质量指数( Body Mass Index )是体重(公斤)除以身高(米)的平方得出的数值,用于评估体重与身高之间的比例关系",
            "BMI = 体重(kg) / 身高(m)^2",
            "初步判断个体是否处于健康体重范围,是评估肥胖程度的常用指标之一",
            "不能区分脂肪和肌肉,对于肌肉发达或运动员等人群可能不够准确" ),

    WAIST_CIRCUMFERENCE( "腰围( Waist Circumference )是腹部最细处的周长,通常在肚脐周围或肋骨下方与髂前上棘之间的中点处测量",
            "使用软尺在腹部最细处水平环绕一周",
            "用于评估腹部脂肪堆积情况,腹部脂肪过多与多种慢性疾病风险增加相关",
            "单独使用腰围无法全面评估全身脂肪分布" ),

    WHR( "腰臀比( Waist-to-Hip Ratio )是腰围与臀围的比值,用于评估腹部脂肪与臀部脂肪的比例",
            "WHR = 腰围(cm)/ 臀围(cm)",
            "能更精准地反映内脏脂肪含量及分布对健康的潜在影响",
            "对于臀部脂肪分布异常的人群可能不够准确" ),

    BFR( "体脂率( Body Fate Rate )是身体脂肪占总体重的比例,反映身体成分组成",
            "包括皮褶厚度测量法、生物电阻抗法、双能 X 线吸收法(DXA)等",
            "帮助评估身体成分是否合理,过高的体脂率可能增加患病风险",
            "不同测量方法的准确性差异较大" ),

    BRI( "身体圆度指数( Body Roundness Index )是基于身体不同部位的围度、直径等测量数据,通过特定计算公式或模型确定的身体圆润程度指标",
            "BRI = 364.2 - 365.5 * 根号下( 1 - 分式² ), 分式 = ( 腰围 / 2PI ) / ( 0.5 * 身高 ) = 腰围 / ( PI * 身高 )",
            "辅助评估身体的肥胖状况及脂肪分布情况",
            "计算较为复杂,可能需要专业设备和人员" ),

    BMR( "基础代谢率( Basal Metabolic Rate )是指人体在清醒而又极端安静的状态下,不受肌肉活动、环境温度、食物及精神紧张等影响时的能量代谢率",
            "主要有公式法(如哈里斯 - 本尼迪克特方程)和测量法(通过专业设备测定氧气消耗量或二氧化碳产生量)",
            "反映人体维持基本生命活动所需的最低能量消耗",
            "公式法估算可能存在误差,测量法需要专业设备" ),

    TDEE( "每日总能量消耗( Total Daily Energy Expenditure )是在基础代谢率基础上,结合日常活动、食物生热效应等因素计算得出的能量消耗",
            "TDEE = BMR × 活动系数 + 食物生热效应",
            "用于制定饮食计划和运动方案,以达到能量平衡或控制体重目标",
            "活动系数的估算可能存在误差" ),

    BSA( "体表面积( Body Surface Area )是人体外表面积的大小,用于衡量人体的生理特征",
            "杜布瓦/杜博伊斯公式: BSA = 0.007184 × 身高(cm)^0.725 × 体重(kg)^0.425",
            "在医学领域用于药物剂量计算、评估热量散失等",
            "计算公式较多,不同公式结果可能存在差异" );

    private final String definition;
    private final String formula;
    private final String function;
    private final String limit;

    public String getIntroduction(){
        StringBuilder introduction = new StringBuilder();
        introduction.append( "定义: " ).append( getDefinition() ).append( "\n" )
                .append( "公式: " ).append( getFormula() ).append( "\n" )
                .append( "功能: " ).append( getFunction() ).append( "\n" )
                .append( "局限性: " ).append( getLimit() ).append( "\n" );
        return TypeConvertor.toString( introduction );
    }

    /**
     * 打印当前健康指标枚举成员的详细介绍
     * @since 2.0.0
     */
    public void printIntroduction(){
        System.out.println( getIntroduction() );
    }
}