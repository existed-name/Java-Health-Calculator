# 子文件说明

---

## [app 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/app)
存放与应用程序紧密相关的类
1. MainApplication: 应用程序主类, 负责启动健康计算器应用
2. ApplicationConfig: 应用程序配置类, 负责管理系统中所有功能的映射关系

---

## [model 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model)
### [entity 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/entity)
存放通用的实体类
1. UserBodyProfile: 用户身体参数档案, 储存基本身体参数、身体指标
2. User: 用户类, 继承自 UserBodyProfile, 实现 Comparable 接口, 包含用户的基本信息( 姓名、ID )、身体数据


### [enums.bodymetric 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/model/enums/bodymetric)
存放通用的实体类( 身体指标枚举类 )
1. BodyMetric: 身体指标接口, 提供关于身体指标的抽象实例方法
2. BasicBodyParameter: 基本身体参数枚举类, 储存基本身体参数的名称、单位、有效值范围, 实现 BodyMetric 接口
3. HealthMetric: 健康指标枚举类, 储存健康指标的名称、单位、有效值范围, 实现 BodyMetric 接口
4. Gender: 性别枚举类, 提供男女枚举成员、性别相关的方法( 验证性别合法性、判断是否为男性等 )

---

## [service 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service)
存放各种服务类及其相关类
### [analysis 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/analysis)
1. AnalysisService: 分析服务类, 提供对健康指标、基本身体参数( 腰围 )数值的分析评估
2. [metrics 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/analysis/metrics): 存放和健康指标集相关的类
   * HealthMetricIntervalAssessment: 实体类, 储存对健康指标某段数值区间的全面评估
   * HealthMetricsProvider: 健康指标集提供者接口, 用于根据身体参数值查找对应的评估结果
   * [basicbodyparameter 包](): 存放基本身体参数( 主要是腰围 )指标集枚举类( 2 个 )
   * [healthmetric 包](): 存放健康指标集枚举类( 10 个 )


### [caculation 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/calculation)
1. CalculationService: 健康指标计算服务类, 计算并展示各类身体指标
2. CalculationMethod: 健康指标计算方法枚举, 用于跟踪 CalculationService 中哪些计算方法已被使用


### [comparison 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/comparison)
1. ComparisonService: 健康指标计算公式对比服务类, 用于对比同一健康指标的不同公式的计算结果
2. [equation.description 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/comparison/equation/description): 存放和公式描述相关的类
   * EquationIntroduction: 公式介绍接口, 提供公式的简短介绍功能
   * BFREquationIntroduction: BFR 公式介绍枚举类, 实现 EquationIntroduction 接口
   * BMREquationIntroduction: BMR 公式介绍枚举类, 实现 EquationIntroduction 接口
   * BSAEquationIntroduction: ~~~


### [introduction 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/introduction)
1. IntroductionService: 介绍服务类, 详解健康指标、部分基本身体参数( 腰围 )的定义、公式/测量、功能、局限性
2. HealthMetricIntroduction: 健康指标介绍枚举类

### [reference 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/service/reference)
1. ReferenceService: 健康指标参考查询服务, 用于查询各类健康指标的理想值/参考范围
2. HealthMetricReference: 健康指标参考枚举类, 存储健康指标及部分基本身体参数( 腰围 )的参考范围


### FileService
文件服务类, 提供对文件的操作方法, 目前用于将用户数据保存到项目文件中


### GameService
游戏服务类, 提供作为**程序核心**的游戏循环


### UIService
控制台用户交互服务类

---

## [util 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util)
存放各种工具类及相关常量类
### [calculator 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/calculator)
存放计算器工具类及相关常量类
1. [basic 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/calculator/basic)
   : 存放基础计算器, 作为底层工具类由更高层调用/封装。目前有 7 个健康指标基础计算器、1 个理想体重计算器
2. [composite 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/calculator/composite)
   : 里面是一个复合计算器 HealthMetricCalculator, 封装了 basic 包中的健康指标基础计算器, 作为外界计算各个身体指标和底层工具方法的中间桥梁
3. [constant 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/calculator/constant)
   : 存放计算相关的常量类--BFRCalculatorConstants、BMRCalculatorConstants、BSACalculatorConstants、IdealWeightCalculatorConstants


### [convertor 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/convertor)
1. TypeConvertor: 类型转换器, 用于对不同数据类型进行转换
2. UnitConvertor: 单位转换器, 封装常用单位转换的常量、方法


### [input 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/input)
存放与控制台输入直接相关的工具类
1. InputReader: 输入读取器, 用于读取指定名称( "年龄"、"体重"、"性别" )的有效数据( int、double、String 类型 )
2. BodyDataReader: 身体数据读取器, 封装了 InputReader, 用于读取并返回基本身体参数
3. InputValidator: 输入验证器, 提供对输入数据的验证方法
4. InputProcessor: 输入处理器, 用于对输入进行特定处理, 比如去除首尾空字符


### [Printer 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/printer)
存放控制台输出的工具类及常量类
1. UIPrinter: UI 打印工具类, 提供控制台打印工具方法( 模拟打字机、打点、打印分隔线等 )
2. PrinterConstants: 控制台打印相关常量类--分隔符、加载动画、延迟/暂停时间


### [validator 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/validator)
存放检验方法参数的工具类
1. BasicBodyParameterValidator: 基本身体参数检验器, 封装对基本身体参数的检验方法
2. HealthMetricValidator: 健康指标检验器, 封装对健康指标的检验方法
3. MethodParameterValidator: 方法参数检验器, 用于检验广泛、通用的方法参数( User、Scanner、区间等 )


### ValueFormatter
数值格式化工具类, 用于将数值格式化为字符串或闭区间

---
