# Java 健康计算器 v2.0.0
代码量相对 [v1.0.0](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV1.0.0) 较大( 3k+行 ), 但思路直接、仅涉及少量进阶知识点, 可以作为初学者的提高训练

---

## 文件下载及使用
1. 将 [ src 文件夹对应的网址](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV2.0.0/src)复制到 [DownGit](https://tool.mkblog.cn/downgit/#/home) 上进行下载, 或者使用其他方式下载( Git 命令、插件、网站 )
2. 取出 src 文件夹中的 com 文件夹, 拖到 IDEA( 或其他编译器 )的 src 文件夹下面
3. 直接编译运行即可
4. 注意: 由于不同包之间有依赖关系( `import com.github.existedname.某个包.某个类` ), 所以建议下载时目录至少从[ com 文件夹](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV2.0.0/src/com)开始

## 子文件说明
* healthcalculatorv2: 存放源码
  1. [app 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/app): 存放 MainApplication 类, 也就是主程序
  2. [model 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/model): 存放实体类, 用于创建对象/对象数组, 储存相关数据
     - UserBodyInfo 类: 存放各种身体数据
     - User 类: 继承自 UserBodyInfo 类, 存放用户基本信息
     - HealthMetricIntroduction 类: 创建对象数组 introductionArr, 储存各种身体指标的介绍信息
     - HealthMetric 类: 创建对象数组 idealRangeArr, 储存各种身体指标的理想范围
     - HealthMetricIdealRange 类: 储存各种身体指标在不同数值范围下对应的身体情况( 用于给出健康分析 )
  3. [service 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/service): 存放 Service 类
     - 调用 util 包实现计算、分析, 自身具有打印前言 & 功能清单、介绍、给出理想范围等功能
     - 这里确实没设计好, Service 类过于臃肿, 准备拆分重构一下
  5. [util 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/util): 存放用于计算、分析的工具类
     - BMICalculator, WHRCalculator, BFRCalculator, BRICalculator, BMRCalculator, BSACalculator 类: 都是顾名思义, 计算并返回某个值
     - TDEECalculator 类: 除了计算, 还有打印活动系数表
     - HealthMetricAnalyzer 类: 调用 HealthMetric 类, 作用是分析身体指标
* images: 存放用于展示的截图、动图
* health-metric-intro: 身体指标简介文档
* readme: 项目说明

---

## 项目功能
对于多种[身体指标](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/health-metric-intro.md)提供以下功能    
- 详细**介绍**( 定义、公式、功能、局限性 )
- **计算**( 这个就不用多说了 )
- **分析**某一身体指标数据并给出健康状况、风险、建议
- **对比**不同公式算出的某一指标数据
- 给出**理想**的健康指标数值范围
- *只想得出这些了*

## 知识清单
1. 基础语法升级   
   1.1 `switch 表达式`返回特定身体指数的计算单位   
   1.2 `thread.sleep` 结合 `增强 for 循环`( 遍历输出字符 )、`Math.random`( 产生随机延迟时间 )模拟打字机  
   1.3 `StringBuilder` 拼接长段文字: 单次输出代替多次输出
   
2. 面向对象基础   
   2.1 类( 成员变量、成员方法与方法调用、重载, 感觉挺像结构体的 )   
   2.2 创建对象( 有参数/无参数构造器、`this` 关键字、对象数组 )   
   2.3 修饰符( `public`/`private`, `static` )   
   2.4 封装( 实体类 )、继承( `super()`调用父类构造器 )

3. 面向对象进阶   
   3.1 合理分包: 主程序 + 服务类( 业务逻辑类 ) + 实体类 + 工具类    
   3.2 枚举类完整定义: 枚举成员、字段、构造器、方法, 用于清晰替代数组下标、计算单位等常量   
   3.3 `try-catch-finally` 块捕获异常、释放资源( 理论上是这样, 但实际上我的底层方法应该考虑抛出而不是捕获异常 )   
   3.4 结合函数式接口( `Runnable`, `Consumer` )、方法引用, 将方法作为值放入 Map, 通过编号调用对应的方法

## 使用展示
1. [前言部分](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/service/Service.java#L1367-L1376)    
   需要等半分钟, 可以输入"0"跳过该部分或者下载后修改[打字机延迟时间](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/service/Service.java#L1284)    
   ![前言](images/foreword-reading.gif)
   
2. [功能一览](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/service/Service.java#L1392-L1428)    
   动图 50 秒有点长, 就不放出来了~~~    
   可以跳过该部分, 对照[源码](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/app/MainApplication.java#L139-L195)或者[截图](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/images/function-list.png)输入编号, 也可以缩小或者取消[打印每个字符的延迟时间](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/service/Service.java#L1429)

3. 体验一波    
   ![计算TDEE展示](images/tdee-calculation-demo.gif)

4. [继续 OR 退出](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV2.0.0/src/com/github/existedname/healthcalculatorv2/app/MainApplication.java#L97-L106)    
   ![继续/退出程序](images/continue-confirmation.gif)

***
