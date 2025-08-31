package com.github.existedname.healthcalculatorv3.service.comparison.equation.description;

import lombok.AllArgsConstructor;

/**
 * BSA 公式介绍枚举类( 名称 + 简短描述 ), 实现 {@link EquationIntroduction } 接口
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/22 10:12
 */
@AllArgsConstructor
public enum BSAEquationIntroduction implements EquationIntroduction {
        DUBOIS_EQUATION( "1. Du Bois( 杜博伊斯 )公式", "最经典、应用最广的 BSA 预测公式" ),
        SCHLICH_EQUATION( "2. Schlich( 施利希 )公式", "具有性别区分度" ),
        MOSTELLER_EQUATION( "3. Mosteller( 莫斯特勒 )公式", "将指数运算化简为开平方,适合日常快速估算" ),
        HAYCOCK_EQUATION( "4. Haycock( 海科克 )公式", "用于儿童 / 青少年医疗,推荐 2-18 岁儿童 / 青少年" );

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

