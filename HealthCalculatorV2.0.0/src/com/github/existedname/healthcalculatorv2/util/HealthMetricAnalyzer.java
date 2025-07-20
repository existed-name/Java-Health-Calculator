package com.github.existedname.healthcalculatorv2.util;

import com.github.existedname.healthcalculatorv2.model.HealthMetric;
import com.github.existedname.healthcalculatorv2.service.Service;

import java.text.DecimalFormat;

public class HealthMetricAnalyzer {
    /*      1. 私有静态成员变量     */
    // 1.1 metrics 指标集,先定义为空,要用的时候再创建,避免创建后用户不用,造成空间浪费
    private static HealthMetric[] bmiMetrics = null; // 1.1.1 BMI 指标集
    private static HealthMetric[] bmrMetrics = null; // 1.1.2 BMR 指标集
    private static HealthMetric[][] bfrMetrics = null; // 1.1.3 BFR 指标集( 女/男 )
    private static HealthMetric[] bsaMetrics = null; // 1.1.4 BSA 指标集
    private static HealthMetric[][] briMetrics = null; // 1.1.5 BRI 指标集
    private static HealthMetric[][] waistCircumMetrics = null; // 1.1.6 腰围指标集( 女/男 )
    private static HealthMetric[][] whrMetrics = null; // 1.1.7 WHR 指标集( 女/男 )
    private static HealthMetric[] tdeeMetrics = null; // 1.1.8 TDEE 指标集



    /*      2. 私有化构造器     */
    private HealthMetricAnalyzer() { }



    /*      3. 分析健康指标的公有静态方法(核心功能)     */
    // 3.1 分析 BMI
    public static void analyzeBMI( double bmi ) {
        if ( bmiMetrics == null ){
            // 当然,由于 init 函数中设置了判断是否初始化,直接调用 init 即可
            initializeBMIMetrics();
        }
        analysisTemplate( bmiMetrics, "BMI", bmi );
    }

    // 3.2 分析 BMR
    public static void analyzeBMR( double bmr ) {
        if ( bmrMetrics == null ){
            initializeBMRMetrics();
        }
        analysisTemplate( bmrMetrics, "BMR", bmr );
    }

    // 3.3 分析 BFR
    public static void analyzeBFR( String gender, double bfr ) {
        if ( bfrMetrics == null ) initializeBFRMetrics();
        int intIsMan = Service.intIsMan( gender );
        analysisTemplate( bfrMetrics[ intIsMan ], "BFR", bfr );
    }

    // 3.4 分析 BSA
    public static void analyzeBSA( double bsa ) {
        if ( bsaMetrics == null ) initializeBSAMetrics();
        analysisTemplate( bsaMetrics, "BSA", bsa );
    }

    // 3.5 分析 BRI
    public static void analyzeBRI( String gender, double bri ) {
        if ( briMetrics == null ) initializeBRIMetrics();
        int intIsMan = Service.intIsMan( gender );
        analysisTemplate( briMetrics[ intIsMan ], "BRI", bri );
    }

    // 3.6 分析 腰围
    public static void analyzeWaistCircum( String gender, double waistCircum ) {
        if ( waistCircumMetrics == null ) initializeWaistCircumMetrics();
        int intIsMan = Service.intIsMan( gender );
        analysisTemplate( waistCircumMetrics[ intIsMan ], "腰围", waistCircum );
    }

    // 3.7 分析 腰臀比
    public static void analyzeWHR( String gender, double whr ) {
        if ( whrMetrics == null ) initializeWhrMetrics();
        int intIsMan = Service.intIsMan( gender );
        analysisTemplate( whrMetrics[ intIsMan ], "腰臀比", whr );
    }

    // 3.8 分析 TDEE
    public static void analyzeTDEE( double tdee ) {
        if ( tdeeMetrics == null ) initializeTDEEMetrics();
        analysisTemplate( tdeeMetrics, "TDEE", tdee );
    }



    /*      4. 私有静态方法(内部辅助)     */
    /*  4.1 初始化健康指标集    */
    /*
     *  参考网址1( 秘塔 ): https://metaso.cn/search/8623862089653755904 ( 提供全面表格、详细但含有部分错误数据的对象数组 )
     *  参考网址2( 豆包 ): https://www.doubao.com/chat/9371982938908162 ( 提供较详细表格以及对象数组,修正秘塔的数组数据 )
     */
    // 4.1.1 初始化 BMI 指标集
    private static void initializeBMIMetrics() {
        if ( bmiMetrics != null ) return; // 避免额外开销

        bmiMetrics = new HealthMetric[]{
                // 对象数组 => 以对象为元素,静态初始化的时候要多次 new 对象
                new HealthMetric( 0.0, 18.5, "体重较轻", "营养不良、代谢紊乱、免疫力下降", "增加营养摄入,补充优质蛋白质、维生素、矿物质;避免过度节食;定期监测体重和营养状况" ),
                new HealthMetric( 18.5, 24.9, "正常体重", "健康风险较低", "保持健康生活方式: 定期运动,避免久坐,饮食均衡,定期体检" ),
                new HealthMetric( 25.0, 29.9, "超重", "高血压、心血管疾病、糖尿病、脂肪肝风险增加", "关注热量摄入,减少精制碳水和饱和脂肪摄入;每周 150 分钟中等强度运动;监测体重变化" ),
                new HealthMetric( 30.0, 34.9, "肥胖I级(轻度肥胖症)", "胰岛素抵抗、非酒精性脂肪肝、代谢综合征、关节负担、睡眠呼吸暂停", "限制热量摄入(每日减少 500-750 大卡);结合饮食与运动,每周 5 天中等强度运动;必要时咨询医生" ),
                new HealthMetric( 35.0, 39.9, "肥胖II级(中度肥胖症)", "心血管疾病、II型糖尿病、关节问题、脂肪肝、心理压力", "专业医疗监督下的减重计划;考虑药物辅助治疗或手术干预;物理治疗缓解关节压力" ),
                new HealthMetric( 40.0, 49.9, "肥胖III级(重度肥胖症)", "心血管疾病、慢性肾病、睡眠呼吸暂停、某些癌症", "多学科团队干预(医生、营养师、心理医生);优先考虑代谢手术" ),
                new HealthMetric( 50.0, Double.MAX_VALUE, "肥胖IV级(极重度/超级肥胖)", "预期寿命缩短10-20年、严重心血管疾病、器官衰竭、极高死亡风险", "紧急医疗干预;代谢手术为一线治疗;终身健康监测和营养支持" ),
        };
    }

    // 4.1.2 初始化 BMR 指标集
    private static void initializeBMRMetrics() {
        if ( bmrMetrics != null ) return;

        bmrMetrics = new HealthMetric[]{
                // description: 范围[ a,b )之间, 基础代谢极低/偏低/...,  健康风险..., 健康建议
                new HealthMetric( 0.0, 1000.0, "基础代谢极低", "甲状腺功能减退、营养不良", "就医检查甲状腺功能,每日热量摄入 ≥ 1200 大卡" ),
                new HealthMetric( 1000.0, 1300.0, "基础代谢偏低", "久坐生活方式、肌肉量少", "增加日常活动量( 如步行 ),每日力量训练 20 分钟" ),
                new HealthMetric( 1300.0, 1600.0, "基础代谢正常( 女性 )", "健康代谢水平", "维持现有饮食,每周 3 次有氧运动" ),
                new HealthMetric( 1600.0, 1900.0, "基础代谢正常( 男性 )", "健康代谢水平", "维持现有饮食,每周 3 次抗阻训练" ),
                new HealthMetric( 1900.0, 2200.0, "基础代谢偏高", "肌肉量高或甲亢倾向", "若肌肉发达 : 维持;若消瘦 : 检查甲状腺功能" ),
                new HealthMetric( 2200.0, 2500.0, "基础代谢很高", "高强度训练者或甲亢", "保证每日热量摄入( 每公斤体重 30-35kcal ),定期休息" ),
                new HealthMetric( 2500.0, Double.MAX_VALUE, "基础代谢异常高", "甲亢或其他内分泌疾病", "立即就医,排查甲亢等疾病" ),
        };
    }

    // 4.1.3 初始化 BFR 指标集( 女/男 )
    private static void initializeBFRMetrics() {
        if ( bfrMetrics != null ) return;

        bfrMetrics = new HealthMetric[][]{
                {   // 女性体脂率范围
                    new HealthMetric( 0.0, 10.0, "体脂率极低( 病态 )", "月经不调、生育能力下降", "立即就医,增加健康脂肪摄入,停止减脂" ),
                    new HealthMetric( 10.0, 18.0, "体脂率处于运动员水平", "适合专业运动员", "维持高强度训练,保证蛋白质摄入( 1.8g/kg 体重 )" ),
                    new HealthMetric( 18.0, 22.0, "体脂率良好", "肌肉线条明显", "保持当前训练计划,注意铁元素补充" ),
                    new HealthMetric( 22.0, 28.0, "体脂率正常", "健康体脂", "每周 2 次臀部训练 +3 次有氧,保证膳食纤维摄入" ),
                    new HealthMetric( 28.0, 33.0, "体脂率偏高", "腹部脂肪堆积", "减少精制碳水摄入,增加核心训练( 如平板支撑 )" ),
                    new HealthMetric( 33.0, 38.0, "肥胖", "代谢风险增加", "采用高蛋白饮食( 1.6g/kg 体重 ),每日快走 60 分钟" ),
                    new HealthMetric( 38.0, Double.MAX_VALUE, "病态肥胖", "糖尿病、心血管疾病高风险", "立即就医,考虑药物或手术干预" ),
                },
                {   // 男性体脂率范围
                    new HealthMetric( 0.0, 5.0, "体脂率极低( 病态 )", "激素失衡、器官保护不足", "立即就医,增加健康脂肪摄入,停止减脂" ),
                    new HealthMetric( 5.0, 10.0, "体脂率处于运动员水平", "适合专业运动员", "维持高强度训练,保证蛋白质摄入( 2.0g/kg 体重 )" ),
                    new HealthMetric( 10.0, 15.0, "体脂率良好", "肌肉线条明显", "保持当前训练计划,均衡饮食( 碳水:蛋白质:脂肪 = 5:3:2 )" ),
                    new HealthMetric( 15.0, 20.0, "体脂率正常", "健康体脂", "每周 3 次抗阻训练 +2 次有氧,控制精制糖摄入" ),
                    new HealthMetric( 20.0, 25.0, "体脂率偏高", "腹部脂肪堆积", "减少糖类、油脂摄入,增加 HIIT 训练( 每周 3 次 )" ),
                    new HealthMetric( 25.0, 30.0, "肥胖", "代谢风险增加", "断绝不健康脂肪、糖类、油糖混合物摄入,每日热量缺口 200-400 大卡,配合力量训练" ),
                    new HealthMetric( 30.0, Double.MAX_VALUE, "病态肥胖", "糖尿病、心血管疾病高风险", "立即就医,考虑药物或手术干预" ),
                },
        };
    }

    // 4.1.4 初始化 BSA 指标集
    private static void initializeBSAMetrics() {
        if ( bsaMetrics != null ) return;

        bsaMetrics = new HealthMetric[]{
                new HealthMetric( 0.0, 1.4, "体表面积过低", "发育不良或严重消瘦", "儿科/内分泌科就诊,增加高热量密度食物摄入" ),
                new HealthMetric( 1.4, 1.6, "体表面积偏低", "偏瘦体型", "增加蛋白质摄入( 1.2g/kg 体重 ),适量力量训练" ),
                new HealthMetric( 1.6, 1.9, "体表面积正常", "体表面积适中", "无特殊风险,维持健康生活方式" ),
                new HealthMetric( 1.9, 2.2, "体表面积偏高", "偏胖体型或肌肉发达", "若肥胖 : 控制热量摄入;若肌肉发达 : 维持当前训练" ),
                new HealthMetric( 2.2, 2.5, "体表面积过高", "肥胖或巨人症", "排查内分泌疾病,制定减重计划( 每日热量缺口 200-400 大卡 )" ),
                new HealthMetric( 2.5, Double.MAX_VALUE, "体表面积病态过高", "严重肥胖或肢端肥大症", "立即就医,考虑药物或手术干预" ),
        };
    }

    // 4.1.5 初始化 BRI 指标集( 女/男 )
    private static void initializeBRIMetrics() {
        if ( briMetrics != null ) return;

        briMetrics = new HealthMetric[][]{
                {   // 女性 BRI
                    new HealthMetric( 0.0, 1.5, "极瘦", "雌激素水平可能过低,月经不调风险", "立即就医检查激素水平,增加健康脂肪摄入( 每日 30-40g ),停止减脂" ),
                    new HealthMetric( 1.5, 2.5, "偏瘦", "肌肉量不足,骨密度下降风险", "增加蛋白质摄入( 1.2-1.5g/kg 体重 ),进行力量训练( 如哑铃、深蹲 )" ),
                    new HealthMetric( 2.5, 4.0, "理想", "沙漏型身材,雌激素水平正常", "维持现有训练,保证钙和 维生素D 摄入,定期监测骨密度" ),
                    new HealthMetric( 4.0, 5.5, "轻度中心性肥胖", "腹部脂肪开始堆积,代谢风险上升", "减少含糖饮料和零食,增加核心训练( 如平板支撑、卷腹 )" ),
                    new HealthMetric( 5.5, 7.0, "中度中心性肥胖", "内脏脂肪过多,多囊卵巢综合征风险", "采用 低GI 饮食,增加有氧运动( 如快走、骑自行车 ),补充肌醇" ),
                    new HealthMetric( 7.0, 8.5, "重度中心性肥胖", "糖尿病、心血管疾病高风险", "立即就医检查血糖、胰岛素水平,严格控制碳水摄入,每日运动 60 分钟" ),
                    new HealthMetric( 8.5, Double.MAX_VALUE, "病态肥胖", "多器官功能受损风险", "需医疗团队介入,考虑药物治疗,配合低碳水高蛋白饮食( 蛋白质占比 30% )" ),
                },
                {  // 男性 BRI
                    new HealthMetric( 0.0, 2.0, "极瘦", "肌肉量严重不足,营养不良风险", "立即就医检查,增加高蛋白饮食( 2.0g/kg 体重 ),从轻度抗阻训练开始" ),
                    new HealthMetric( 2.0, 3.0, "偏瘦", "肌肉量不足,基础代谢率低", "增加热量摄入( 每日额外 100-300 大卡 ),进行全身抗阻训练( 每周 3 次 )" ),
                    new HealthMetric( 3.0, 4.5, "理想", "肌肉与脂肪比例均衡,健康体型", "维持均衡饮食,定期力量训练保持肌肉量" ),
                    new HealthMetric( 4.5, 6.0, "轻度中心性肥胖", "腹部脂肪开始堆积,代谢风险上升", "减少精制碳水摄入,增加有氧运动( 每周 4 次,每次 40 分钟 )" ),
                    new HealthMetric( 6.0, 7.5, "中度中心性肥胖", "内脏脂肪过多,胰岛素抵抗风险", "采用低碳水化合物饮食,每日热量缺口 200-400 大卡,增加 HIIT 训练" ),
                    new HealthMetric( 7.5, 9.0, "重度中心性肥胖", "糖尿病、心血管疾病高风险", "立即就医检查血糖、血脂,严格控制饮食( 每日 1500-1800 大卡 ),每日运动60分钟" ),
                    new HealthMetric( 9.0, Double.MAX_VALUE, "病态肥胖", "多器官功能受损风险", "需医疗团队介入,考虑代谢手术,配合极低热量饮食( 800-1200 大卡/天 )" ),
                },
        };
    }

    // 4.1.6 初始化 腰围指标集( 女/男 )
    private static void initializeWaistCircumMetrics() {
        if ( waistCircumMetrics != null ) return;

        waistCircumMetrics = new HealthMetric[][]{
                {   // 女性腰围
                    new HealthMetric( 0.0, 65.0, "腰部过细", "可能肌肉量不足或雌激素偏低", "增加蛋白质摄入,监测月经周期" ),
                    new HealthMetric( 65.0, 75.0, "健康", "低代谢风险", "维持现有生活方式,保持核心训练" ),
                    new HealthMetric( 75.0, 80.0, "腰围轻度超标", "代谢风险开始增加", "减少含糖饮料摄入,每周 3 次瑜伽" ),
                    new HealthMetric( 80.0, 88.0, "腰围超标", "胰岛素抵抗风险", "采用 低GI 饮食,每周 4 次 30 分钟有氧" ),
                    new HealthMetric( 88.0, 95.0, "高危", "糖尿病、心血管疾病高风险", "立即控制饮食( 每日热量 1200-1500 大卡 ),增加力量训练" ),
                    new HealthMetric( 95.0, Double.MAX_VALUE, "病态", "严重代谢综合征", "立即就医,药物干预配合饮食运动" ),
                },
                {  // 男性腰围
                    new HealthMetric( 0.0, 75.0, "腰部过细", "可能肌肉量不足", "增加蛋白质摄入,进行抗阻训练" ),
                    new HealthMetric( 75.0, 85.0, "健康", "低代谢风险", "维持现有生活方式,避免久坐" ),
                    new HealthMetric( 85.0, 90.0, "腰围轻度超标", "代谢风险开始增加", "减少精制碳水摄入,每日步行 10000 步" ),
                    new HealthMetric( 90.0, 100.0, "腰围超标", "胰岛素抵抗风险", "采用地中海饮食,每周 5 次 30 分钟有氧运动" ),
                    new HealthMetric( 100.0, 110.0, "高危", "糖尿病、心血管疾病高风险", "立即控制饮食( 每日热量 1500-1800 大卡 ),增加高强度运动" ),
                    new HealthMetric( 110.0, Double.MAX_VALUE, "病态", "严重代谢综合征", "立即就医,药物干预配合饮食运动" ),
                },
        };
    }

    // 4.1.7 初始化 WHR 指标集( 女/男 )
    private static void initializeWhrMetrics() {
        if ( whrMetrics != null ) return;

        whrMetrics = new HealthMetric[][]{
                {   // 女性腰臀比
                    new HealthMetric( 0.0, 0.70, "腰臀比过低", "可能肌肉分布不均", "加强臀部训练( 如深蹲 ),平衡肌肉发展" ),
                    new HealthMetric( 0.70, 0.75, "腰臀比优秀", "沙漏型身材,雌激素水平理想", "维持现有训练计划,保证钙摄入" ),
                    new HealthMetric( 0.75, 0.80, "腰臀比良好", "健康范围", "继续当前生活方式,定期监测" ),
                    new HealthMetric( 0.80, 0.85, "腰臀比偏高", "腹部脂肪堆积", "减少精制碳水摄入,增加核心训练" ),
                    new HealthMetric( 0.85, 0.90, "高危", "内脏脂肪过多", "采用高蛋白饮食( 1.6g/kg 体重 ),每周 5 次运动" ),
                    new HealthMetric( 0.90, Double.MAX_VALUE, "病态", "严重中心性肥胖", "立即就医,考虑药物或手术减脂" ),
                },
                {   // 男性腰臀比
                    new HealthMetric( 0.0, 0.80, "腰臀比过低", "可能肌肉分布不均", "加强臀部训练( 如硬拉 ),平衡肌肉发展" ),
                    new HealthMetric( 0.80, 0.85, "腰臀比优秀", "脂肪分布理想", "维持现有训练计划,保持饮食均衡" ),
                    new HealthMetric( 0.85, 0.90, "腰臀比良好", "健康范围", "继续当前生活方式,定期监测" ),
                    new HealthMetric( 0.90, 0.95, "腰臀比偏高", "腹部脂肪堆积", "减少酒精摄入,增加有氧运动( 如游泳 )" ),
                    new HealthMetric( 0.95, 1.00, "高危", "内脏脂肪过多", "采用间歇性禁食( 如 16:8 饮食法 ),每日 HIIT 训练" ),
                    new HealthMetric( 1.00, Double.MAX_VALUE, "病态", "严重中心性肥胖", "立即就医,考虑药物或手术减脂" ),
                },
        };
    }

    // 4.1.8 初始化 TDEE 指标集
    private static void initializeTDEEMetrics() {
        if ( tdeeMetrics != null ) return;

        tdeeMetrics = new HealthMetric[]{
                // 极低活动水平( 久坐办公、极少运动 )
                new HealthMetric( 0.0, 1200, "能量摄入不足", "代谢减缓、肌肉流失、月经不调( 女性 )、免疫力下降", "逐步增加热量摄入至基础代谢水平;每日摄入至少 1.2g/kg 体重蛋白质" ),
                new HealthMetric( 1200, 1600, "极低活动水平", "维持基本生理功能,难以支持日常活动", "增加轻度活动( 如散步 );每日热量摄入不低于 BMR × 1.1" ),
                // 低活动水平( 轻度运动 1-3 天/周 )
                new HealthMetric( 1600, 2000, "低活动水平", "适合轻度办公室工作者,可能有体重增加风险", "每周 3 次 30 分钟中等强度运动;保持碳水 : 蛋白质 : 脂肪 = 5:3:2 比例" ),
                // 中等活动水平( 中等运动 3-5 天/周 )
                new HealthMetric( 2000, 2400, "中等活动水平", "适合大多数健康成年人,维持当前体重", "每日摄入热量 = BMR × 1.55;保证足够水分和电解质" ),
                // 高活动水平( 高强度运动 6-7 天/周 )
                new HealthMetric( 2400, 3000, "高活动水平", "适合运动员或体力劳动者,需注意营养恢复", "运动后 30 分钟内补充碳水和蛋白质( 比例 4:1 );每日钠摄入不超过 2300mg" ),
                // 超高活动水平( 专业运动员、极重体力劳动 )
                new HealthMetric( 3000, 4000, "极高活动水平", "需严格规划营养摄入,存在过度训练风险", "分阶段碳水摄入策略;补充支链氨基酸( BCAA );定期监测血睾酮水平" ),
                // 极高活动水平( 精英运动员、极端体力活动 )
                new HealthMetric( 4000, Double.MAX_VALUE, "超高水平", "可能需要专业营养师指导,需警惕心脏负荷", "个体化营养方案;定期心脏功能评估;保证 8 小时睡眠" ),
        };
    }


    /*  4.2 查找某个指标在数组中的下标   */
    // 4.2.1 一维数组查找
    private static int getIdx( HealthMetric[] oneDimensionalMetrics, double targetData ){
        int idx = oneDimensionalMetrics.length - 1;
        for ( ; idx >= 0; --idx ){
            if ( targetData >= oneDimensionalMetrics[ idx ].getRangeStart() ){
                // 从高到低查找 => 只要 targetData ≥ 区间起点 => 找到所在区间
                break;
            }
        }
        return idx; // 没找到最终会返回 -1
    }

    // 4.2.2 二维数组查找
    private static int getIdx( HealthMetric[][] twoDimensionalMetrics, int intIsMan, double targetData ){
        // intIsMan: 女 0 男 1,对应二维数组的第 0 行(女)、第 1 行(男)
        HealthMetric[] oneDimensionalMetrics = twoDimensionalMetrics[ intIsMan ];
        return getIdx( oneDimensionalMetrics, targetData );
    }


    /*  4.3 健康指标分析模板    */
    // 类似于通式(通用公式),只需要代入特定参数即可,格式都一样
    private static void analysisTemplate( HealthMetric[] oneDimensionalMetrics, String healthMetricName, double healthMetricValue ){
        try {
            // 1. 根据身体指标的数值找到该指标在数组中的对应位置
            int idxInMetrics = getIdx( oneDimensionalMetrics, healthMetricValue );
            HealthMetric healthMetricObject = oneDimensionalMetrics[ idxInMetrics ];

            /*
             *                              多次输出 VS 将字符串拼接后输出
             *  1. 性能考量: 如果要输出的内容非常大,使用 StringBuilder 或 StringBuffer( 在多线程环境下 )进行字符
             *      串拼接会更高效,因为它们避免了每次 + 操作时可能产生的新字符串对象,减少了内存分配和垃圾回收的压力
             *  2. 可读性: 如果内容简单,直接使用多个 System.out.println() 语句可以使代码更直观易懂;对于复杂的输出
             *      格式,使用 StringBuilder 进行构建,然后一次性输出,可以保持代码的整洁
             *  3. 多次输出与一次性输出: 如果需要多次调用输出函数,尤其是循环中,使用 StringBuilder 收集所有输出内容,
             *      最后一次性输出可以减少输出操作的次数,提高效率
             *  4. 动态构建: 当输出内容需要动态生成,比如拼接变量值或根据条件决定输出内容时,StringBuilder 的 append
             *      方法提供了灵活性
             */

            // 2. 创建 SB & 将身体指标数值保留 3 位小数( 避免浮点数太长,方便观看 )
            StringBuilder healthReport = new StringBuilder();
            DecimalFormat decimalFormat = new DecimalFormat( "0.000" );
                /*
                 *      Q: 如果 value = 0.011,那么为什么打印 formattedValue 得到".011"而不是"0.011"?
                 *  模式 #.000: 整数部分是可选的,如果整数部分为 0,则不会显示
                 *  模式 0.000: 整数部分是必须显示的,即使它是 0,也会显示
                 *  根据需求选择合适的模式,如果希望整数部分即使是 0 也要显示,应该使用 0.000,否则 #.000
                 */
            String formattedValue = decimalFormat.format( healthMetricValue );

            // 3. 拼接健康报告并输出
            /*
             *              局内效果展示
             *  正在生成 xxx 报告...
             *  报告生产完毕!
             *  你的 xxx 所在区间为:\t start ≤ value < end
             *  你的健康状况为:\t
             *  你的健康风险为:\t
             *  你的健康建议为:\t
             */
                // 3.1 通过 Service 类中的 HealthMetricUnit 枚举类,获得身体指标对应单位
            String unit = Service.HealthMetricUnit.getUnitByName( healthMetricName );
                // 3.2 拼接报告
            healthReport.append( "你的 " ).append( healthMetricName ).append( " 所在区间为:\t" )
                    .append( healthMetricObject.getRangeStart() ).append( unit ).append( " ≤ " )
                    .append( formattedValue ).append( unit ).append( " < " )
                    .append( healthMetricObject.getRangeEnd() ).append( unit ).append( "\n" );
            healthReport.append( "你的健康状况为:\t" ).append( healthMetricObject.getHealthStatus() )
                    .append( "\n你的健康风险为:\t" ).append( healthMetricObject.getHealthRisk() )
                    .append( "\n你的健康建议为:\t" ).append( healthMetricObject.getHealthAdvice() );
                // 3.3 输出报告
            Service.printLoadingProgress( "正在生成 " + healthMetricName + " 报告",
                                                    healthMetricName + " 分析报告生成完毕!" );
            System.out.println( healthReport );
        } catch ( NullPointerException e ){
            System.out.println( "健康指标数据集为空!请进行初始化!" );
        } finally { }
    }
}