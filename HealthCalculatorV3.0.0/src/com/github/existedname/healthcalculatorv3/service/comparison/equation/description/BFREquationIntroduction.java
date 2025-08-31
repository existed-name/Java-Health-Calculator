package com.github.existedname.healthcalculatorv3.service.comparison.equation.description;

import lombok.AllArgsConstructor;

/**
 * BFR 公式介绍枚举类( 名称 + 简短描述 ), 实现 {@link EquationIntroduction } 接口
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/20
 */
@AllArgsConstructor
public enum BFREquationIntroduction implements EquationIntroduction {
        DEURENBERG_EQUATION( "1. Deurenberg 公式", "用 BMI 预测体脂率最经典的公式,适用于 7-83 岁的成人和儿童" ),
        GALLAGHER_EQUATION( "2. Gallagher 公式", "适用于普通成人" ),
        USN_EQUATION( "3. 美国海军体脂测量法( 美国海军公式 )", "军事和健身领域常用" ),
        JACKSON_POLLOCK_EQUATION( "4. Jackson-Pollock 公式", "提供皮褶与 BMI 双路径计算" );

        private final String equationName;
        private final String description;

        @Override
        public String getEquationName(){
            return equationName;
        }

        @Override
        public String getDescription(){
            return description;
        }

        @Override
        public void printEquationIntroduction(){
            System.out.println( equationName + "\n\t————" + description );
        }

    }
