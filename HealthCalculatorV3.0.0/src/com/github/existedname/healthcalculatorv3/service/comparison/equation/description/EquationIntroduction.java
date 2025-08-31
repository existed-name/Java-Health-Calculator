package com.github.existedname.healthcalculatorv3.service.comparison.equation.description;

/**
 * 公式介绍接口, 提供公式的简短介绍功能<br>
 * 实现该接口的类需要提供公式名称、描述、打印介绍信息的功能
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/20
 */
public interface EquationIntroduction {
    /** @return 公式名称 */
    String getEquationName();
    /** @return 公式的简单描述 */
    String getDescription();
    /** 打印公式简介( 名称 + 描述 ) */
    void printEquationIntroduction();
}
