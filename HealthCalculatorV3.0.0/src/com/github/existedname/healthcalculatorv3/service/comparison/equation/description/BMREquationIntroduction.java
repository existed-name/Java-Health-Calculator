package com.github.existedname.healthcalculatorv3.service.comparison.equation.description;

import lombok.AllArgsConstructor;

/**
 * BMR 各种公式介绍( 名称 + 简短描述 )
 *
 * @author <a href="https://github.com/existed-name"> existed-name </a>
 * @since 3.0.0
 * @CreateTime 2025/8/22 10:12
 */
@AllArgsConstructor
public enum BMREquationIntroduction implements EquationIntroduction {
        MSJ_EQUATION( "1. Mifflin-St Jeor( MSJ ) 公式", "当前最常用、最权威的 BMR 估算公式,对中国人群适用" ),
        HB_EQUATION( "2. Harris-Benedict( H-B ) 公式", "经典传统公式,曾被广泛使用" ),
        HENRY_EQUATION( "3. Henry 公式", "欧洲食品安全局( EFSA )推荐,但更适合欧洲人群" ),
        SCHOFIELD_EQUATION( "4. Schofield 公式", "WHO 推荐的多年龄段公式" ),
        KATCH_MCARDLE_EQUATION( "5. Katch-McArdle 公式", "需要测体脂率,基于瘦体重( FFM )计算,适用于健身人群" ),
        SHIZGAL_ROSA_EQUATION( "6. Shizgal-Rosa 公式", "考虑身高、体重、年龄的多元回归公式" ),
        MAO_EQUATION( "7. 毛德倩 公式", "基于中国人数据推导,适用于 20-45 岁的中国人" ),
        BSA_CALCULATION_METHOD( "8. BSA( 体表面积 )计算法", "需要计算体表面积,适用于需要结合体表面积的临床或科研场景" );

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