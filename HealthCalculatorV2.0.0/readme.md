# Java 健康计算器 v2.0.0
代码量相对 [v1.0.0](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV1.0.0) 较大( 3k+行 ), 但思路直接、仅涉及少量进阶知识点, 可以作为初学者的提高训练

## 文件下载


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
   1.2 `thread.sleep` 结合 `增强 for 循环`( 遍历输出字符 )模拟打字机  
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
