# 身体指标介绍
对 [V2.0.0 版本的身体指标介绍](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/health-metric-intro.md) 进行更正, 也方便看懂项目里面对健康方面的一些命名

---

## 基本概念
1. 我们知道有一些身体数据可以直接看出来或者测量得到, 比如性别、身高、体重等等, 这类基础的身体数据在项目中命名为[基本身体参数](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/enums/bodymetric/BasicBodyParameter.java)
( Basic Body Parameter );  
2. 而另一些通过这些基本数据计算得到的身体数据, 比如 BMI、BMR 等等, 在项目中命名为[健康指标](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/enums/bodymetric/HealthMetric.java)( Health Metric )
;  
3. 基本身体参数、健康指标在项目中统称为[身体指标](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/enums/bodymetric)
( Body Metric )


4. 把评估某种状况的相关指标( Metric )整合在一起, 可以形成[指标集](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/analysis)( Metrics ), 用来系统地分析某个方面。  
在本项目中, 每一个指标集由同一种身体指标组成, 但针对不同的数据范围, 提供对不同数值的身体指标的分析评估

---

## 基本身体参数
性别( Gender )、年龄、身高、体重、腰围( Waist Circumference )、臀围( Hip Circumference )、颈围( Neck... )、臂围( Arm... )、活动系数( Activity Coefficient )......
这里不多赘述, 可以参考[性别枚举类](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/enums/bodymetric/Gender.java)、[基本身体参数枚举类](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/enums/bodymetric/BasicBodyParameter.java)

---

## 健康指标
详见[健康指标枚举类](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/enums/bodymetric/HealthMetric.java)  
备注: V2.0.0 把腰围当作了健康指标, 实际上应该是基本身体参数, 无伤大雅


#### 1. 体态评估类身体指数
1.1 **BMI**    
   身体质量指数( Body Mass Index )是体重( kg )除以身高( m )的平方得出的数值,用于评估体重与身高之间的比例关系
   
1.2 **WC**    
   腰围( Waist Circumference )是腹部最细处的周长,通常在肚脐周围或肋骨下方与髂前上棘之间的中点处测量
   
1.3 **WHR**    
   腰臀比( Waist-to-Hip Ratio )是腰围与臀围的比值,用于评估腹部脂肪与臀部脂肪的比例

1.4 **BFR**    
   体脂率( Body Fate Rate )是身体脂肪占总体重的比例,反映身体成分组成

1.5 **BRI**    
   身体圆度指数( Body Roundness Index )是基于身体不同部位的围度、直径等测量数据,通过特定计算公式或模型确定的身体圆润程度指标

#### 2. 能量代谢类身体指数
2.1 **BMR**    
   基础代谢率( Basal Metabolic Rate )是指人体在清醒而又极端安静的状态下,不受肌肉活动、环境温度、食物及精神紧张等影响时的能量代谢率

2.2 **TDEE**    
   总每日能量消耗( Total Daily Energy Expenditure )是在基础代谢率的基础上,结合日常活动、食物生热效应等因素计算得出的能量消耗

#### 3. 生理特征类身体指数
3.1 **BSA**    
   体表面积( Body Surface Area )是人体外表面积的大小,用于衡量人体的生理特征

---

## 指标集
分为[基本身体参数指标集](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/analysis/metrics/basicbodyparameter)
、[健康指标集](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/analysis/metrics/healthmetric)
, 在本项目中提供对某种身体指标的[某段数值区间的全面评估](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/analysis/metrics/HealthMetricIntervalAssessment.java)

