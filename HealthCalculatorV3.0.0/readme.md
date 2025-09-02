# Java 健康计算器 v3.0.0
肝了 20 天( 2025/8/11 ~ 2025/8/30 ), 终于重构了 [v2.0.0](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV1.0.0) 这坨屎山, 现在看起来清爽多了( 虽然说没做什么新功能... )

---

## 使用展示
由于控制台可见的内容改动不大, 可以直接看 [v2.0.0 的使用展示](https://github.com/existed-name/Java-Health-Calculator/edit/main/HealthCalculatorV2.0.0/readme.md#%E4%BD%BF%E7%94%A8%E5%B1%95%E7%A4%BA)

---

## 更新内容
1. 优化确认选项( 是、否 )的输入、判定
   * v2.0.0: "是请按 1, 否请按 0", 然而只有输入 0 才会判定为"否", 其他输入( 包括直接 Enter )会判定为"是"
   * v3.0.0:
     1. 用 1、yes 表示"是", 0、no 表示"否", 提高可选择性
     2. 对输入进行严格判定, 输入 1/yes、0/no 才会放行, 否则提示输入错误
     3. 容错率更高: 允许输入首尾为空格的字符串, 比如"      yes       "会被处理为"yes"
2. 优化程序异常处理
   1. 捕获运行中出现的异常, 保证游戏不被中断
   2. 用红色字体打印关于异常的简短信息以提示玩家程序发生的错误
3. 修复数据输入异常
   * v2.0.0: 输入身高体重等身体数据时, 一输入字母就炸( 包括直接 Enter )
   * v3.0.0:
     1. 检查玩家输入格式是否正确并明确提示( ❌ )
     2. 在提示玩家进行输入时, 展示该身体数据的有效范围( 带单位 ), 并在输入无效数值时进行提醒( ❌ )
     3. 捕获输入过程出现的其他未知异常( ⚠ ), 并提示用户继续输入
4. 调整延迟时间:
   1. 功能清单打印延迟设定从 40ms/字 → 20ms/字, 加快打印速度, 同时尽量保持"打字机"的趣味性
   2. 展示完表格( 活动系数表 )、公式( 公式对比 )等内容后, 停顿几秒, 留给玩家阅读时间
   3. 展示表格( 活动系数表 )前适当停顿, 留给玩家心理准备时间
5. 增加数据保存功能: 退出计算器时将用户输入的数据以及计算的结果写入项目文件, 方便用户查看

---

## 项目功能
对于多种[健康指标](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/body-metric-introduction.md)提供以下功能    
- 详细**介绍**定义、公式/测量方式、功能、局限性
- 读取基本身体参数并**计算**健康指标
- **分析**某一身体指标数据并给出健康状况、风险、建议
- **对比**不同公式算出的某一指标数据
- 提供健康指标的**参考**范围
- 将输入的基本数据以及计算得到的数值**保存**到项目文件中
  
---

## 文件下载
1. 方式一: 只下载 src 文件夹
   1. 将 [src 文件夹对应的网址](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src)复制到 [DownGit](https://tool.mkblog.cn/downgit/#/home) 上进行下载, 或者使用其他工具下载( Git 命令、插件、网站 )
   2. 下载后会得到一个压缩包, 进行解压
   3. 在 IDEA( 或其他编译器 )中创建一个空项目, 或者打开一个项目
   4. 取出解压缩后的 src 文件夹中的 com 文件夹, 拖到项目的 src 文件夹里面
2. 方式二: 下载整个项目文件夹
   1. 将 [ HealthCalculatorV3 对应的网址](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0)复制到 [DownGit](https://tool.mkblog.cn/downgit/#/home) 上进行下载
   2. 解压
   3. 用编译器打开解压后的文件夹里面的项目
3. [更多下载方式](https://github.com/existed-name/Java-Health-Calculator?tab=readme-ov-file#%E4%B8%8B%E8%BD%BD%E8%AF%B4%E6%98%8E)

---

## 使用说明
1. 由于项目使用了 `lombok` 框架, 需要解决依赖问题 → [往项目中添加 lombok 依赖](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/lombok-introduction.md#%E5%AF%BC%E5%85%A5%E6%A1%86%E6%9E%B6)
2. 进入 [app 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/app)的 `MainApplication` 主程序类, 编译运行
3. 在控制台打印信息( 比如各种表格 )时, 请耐心阅读并等待程序提示进行输入; 如果感觉打印慢, 可以在 [util.printer 包](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV3.0.0/src/com/github/existedname/healthcalculatorv3/util/printer)的 `PrinterConstants.TimeConstants` 类中调整打印延迟时间
4. 如果保存数据到项目文件时报错( 比如找不到指定路径 ), 请确保 `com.exitedname.healthcalculatorv3` 包放在项目的 src 文件夹下

---

## 子文件说明
关于项目的文件结构见[子文件说明](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/subfile-desciption.md)

---

## 知识清单 & 项目亮点
在 [v2.0.0 的知识清单](https://github.com/existed-name/Java-Health-Calculator/edit/main/HealthCalculatorV2.0.0/readme.md#%E7%9F%A5%E8%AF%86%E6%B8%85%E5%8D%95)基础上增加以下内容  

1. 枚举类: 使用枚举类替代对象数组来封装数量和内容固定的数据集, 语义更清晰, 管理更便捷
2. 嵌套类: 给部分类( 主要是常量类 )创建一层或多层内部类( 但不超过 2 层 ), 对类成员( 主要是变量 )分组管理
3. IO 流: 结合字节输出流、字符字节转换流、缓冲流将用户数据写入项目文件进行保存
4. 异常处理:
   1. 各类方法严格检验参数, 抛出合理的异常( 绝大多数方法 )或自行处理异常( 读取控制台输入的方法 )
   2. 将 `try-catch` 块应用到游戏循环, 避免异常导致游戏崩溃
   3. 将游戏循环中捕获的异常用 `System.err.println` 打印, 通过醒目的异常信息提示玩家
5. 代码简化:
   1. `lombok 框架`: 应用 [`lombok` 框架](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/lombok-introduction.md#%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)简化实体类( 包括枚举类 )代码
   2. 接口: 为性质、功能相似的类设计接口, 将这些类的相同行为抽象到接口中
   3. 方法引用:
      1. 创建容器封装一系列方法, 便于调用指定的方法
         比如用 `HashMap` 将功能编号和实现功能的方法对应起来, 通过编号调用功能; 将同一身体指标的不同计算公式展示方法封装到 `ArrayList` 中, 通过遍历 `ArrayList` 来进行一系列展示
      2. 将多个逻辑相同、实现极其相似、但内部调用方法不同的方法抽出模板方法, 用函数式接口( `BiFunction`、`Function` )作为参数, 只需传入方法引用、其他参数即可, 大大简化代码
6. 增强内聚:
   1. 密切相关的类放同一个包下, 比如 `UIPrinter` 工具类的附属常量类 `PrinterConstants` 和它一起放 `util.printer` 包下, 而不是拆分到 `constant` 包中
   2. 与常量处理密不可分的方法放进常量类, 避免脱离依赖的数据而空洞
7. 项目规范:  
   1. 合理命名: 除了满足基本的驼峰命名, 类、方法、变量的命名尽量详细明确, 避免模糊命名、不通用的缩写
   2. 合理分包: 根据项目需求分为 app、model、service、util 包, 各个包内又根据功能关联性细分包, 项目结构清晰
   3. 详细注释:
      1. 点明注释对象( 类/方法/变量字段 )的作用, 注明类、某些方法/字段的起始版本号( `@since` )
      2. 详细描述方法参数( 包含单位 )、返回值( 单为 )、抛出异常( 当....时抛出异常 ) 以及 计算公式( `<pre></pre>` )、参考网址( `@see` )
      3. 给部分方法、字段的注释添加链接( `{@link Xxx类#Xxx方法 }`, 便于查找使用场景
   4. 类规范:
      1. 统一用 final 修饰, 除非明确可以被继承
      2. 工具类私有化构造器, 方法用 `static` 修饰
      3. 枚举类用 `final` 修饰字段, 避免添加 `setter`( 包括作为枚举类字段的对象 ), 保证整体的不可变性
      4. 类成员按照合理顺序排列( 常量 → 静态变量 → 实例变量 → 构造器 → 公有方法 → 私有方法 ), 方便源码查找

---

