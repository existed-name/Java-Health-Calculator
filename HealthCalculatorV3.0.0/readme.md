# Java 健康计算器 v3.0.0
肝了 20 天( 2025/8/11 ~ 2025/8/30 ), 终于重构了 [v2.0.0](https://github.com/existed-name/Java-Health-Calculator/tree/main/HealthCalculatorV1.0.0) 这坨屎山, 现在看起来清爽多了( 虽然说没做什么新功能... )

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
2. 进入 [app 包]()的 [MainApplication] 主程序类, 编译运行
3. 在控制台打印信息( 比如各种表格 )时, 请耐心阅读并等待程序提示进行输入; 如果感觉打印慢, 可以在 [util.printer 包]()的 `PrinterConstants.TimeConstants` 类中调整打印延迟时间
4. 如果保存数据到项目文件时报错( 比如找不到指定路径 ), 请确保 `com.exitedname.healthcalculatorv3` 包放在项目的 src 文件夹下

---

## 子文件说明
关于项目的文件结构见[子文件说明](https://github.com/existed-name/Java-Health-Calculator/blob/main/HealthCalculatorV3.0.0/subfile-desciption.md)

---

## 知识清单


---

## 使用展示


---
